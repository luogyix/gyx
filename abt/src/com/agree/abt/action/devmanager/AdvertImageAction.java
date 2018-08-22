package com.agree.abt.action.devmanager;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.ContextLoader;

import com.agree.abt.service.confmanager.IAdvertParamService;
import com.agree.abt.service.devmanager.IAdvertImageService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.CheckIP;
import com.agree.util.IDictABT;
import com.agree.util.PropertiesUtils;

/**
 * 设备图片资源管理action
 * 
 * @ClassName: DeviceImageAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2015-12-3
 */
public class AdvertImageAction extends BaseAction {
	
	
	private static final long serialVersionUID = 1L;
	
	private ABTComunicateNatp cona;
	
	private IAdvertParamService advertservice;
	


	private IAdvertImageService advertImageService;
	private File content;
	
	private File upload;
	private File upload1;
	private File upload2;
	private File upload3;
	private String uploadFileName;
	private String upload1FileName;
	private String upload2FileName;
	private String upload3FileName;
	private File uploadFiles;
	private String upload_fileNames;
    private String upload_productid;
	private String uploaddetails_lableid;
	PropertiesUtils pro = new PropertiesUtils();
	private FileInputStream fileInputStream;
	private String downloadFileName;
	private String filetype;
    private static int uploadFileNum = 1;
    private static int fileNum=0;
    
    



	public String loadPage() throws Exception{
		this.pro.getFile("/conf.properties");
        String file_limit = String.valueOf(this.pro.read("imagefile_limit")) + "," + this.pro.read("videofile_limit");
        String sub_imagefile_num= String.valueOf(this.pro.read("sub_imagefile_num"));
        String branch_imagefile_num=String.valueOf(this.pro.read("branch_imagefile_num"));
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put("file_limit", (Object)file_limit);
		sRet.put("sub_imagefile_num", (Object)sub_imagefile_num);
		sRet.put("branch_imagefile_num", (Object)branch_imagefile_num);
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		this.pro.close();
		return SUCCESS;
	}
	
	
	
	/**
	 * 查询设备图片信息列表
	 * 由于可能要查询机构上级信息，特此编写交易
	 */
	public String queryAdvertList() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona = new ABTComunicateNatp();
		
		cona.setBMSHeader("ibp.bms.b213.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("device_type", obj.getString("device_type"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum;
		try {
			loopNum = (String) map.get("adInfoNum").get(0);
		} catch (Exception e) {
			loopNum = "0";
		}
		int num = Integer.parseInt(loopNum);
		Page pageInfo = this.getPage(obj);
		pageInfo.setTotal(num);//总条数		
		Integer pageNo = (pageInfo.getTotal() % pageInfo.getLimit() == 0) ? pageInfo.getTotal() / pageInfo.getLimit() : pageInfo.getTotal()
				/ pageInfo.getLimit() + 1;// 得到总页数
		if (pageInfo.getStart() == -1) {// 查询最后一页
			pageInfo.setRowStart((pageNo - 1) * pageInfo.getLimit());
			pageInfo.setRowEnd(pageInfo.getRowStart()
					+ (pageInfo.getTotal() % pageInfo.getLimit() == 0 ? pageInfo.getLimit() : pageInfo.getTotal() % pageInfo.getLimit()));
			pageInfo.setPage(pageNo);
			pageInfo.setTotal(pageInfo.getTotal());
		} else {
			pageInfo.setRowStart((pageInfo.getStart() - 1) * pageInfo.getLimit() );
			pageInfo.setRowEnd( (pageInfo.getRowStart() + pageInfo.getLimit()) <= pageInfo.getTotal() ? (pageInfo.getRowStart() + pageInfo.getLimit())
					: pageInfo.getTotal() );
			pageInfo.setPage(pageNo);
			pageInfo.setTotal(pageInfo.getTotal());
		}
		for (int i=pageInfo.getRowStart();i<pageInfo.getRowEnd();i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			//branch,device_type,content_id,creator,create_date,note
			hld.put("branch", map.get("branch").get(i));
			hld.put("device_type", map.get("device_type").get(i));
			hld.put("content_id", map.get("content_id").get(i));
			hld.put("creator", map.get("creator").get(i));
			hld.put("create_date", map.get("create_date").get(i));
			hld.put("explain", map.get("explain").get(i));
			hld.put("content", map.get("content").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	public String addAdvertisingPicture() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String jsonString = super.getJsonString();
		ServiceReturn tet = advertImageService.addAdvertisingPicture(jsonString,user);
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 广告多图片上传，限制为10
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	public void uploadAdvertisingPicture() throws Exception{
		this.pro.getFile("/conf.properties");
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
        User user = (User)ServletActionContext.getRequest().getSession().getAttribute("logonuser");
        String name = request.getParameter("filename");
        String prephotosnum = request.getParameter("prephotosnum");
        
        
        String[] fileNames=this.upload_fileNames.split(",");
        int sub_imagefile_num = Integer.parseInt(this.pro.read("sub_imagefile_num"));
        int sub_videofile_num = Integer.parseInt(this.pro.read("sub_videofile_num"));
        int branch_imagefile_num = Integer.parseInt(this.pro.read("branch_imagefile_num"));
        int branch_videofile_num = Integer.parseInt(this.pro.read("branch_videofile_num"));
        double video_file_size = Double.parseDouble(this.pro.read("video_file_size"));
        double image_file_size = Double.parseDouble(this.pro.read("image_file_size"));
        //String gwq_filepath = this.pro.read("gwq_filepath");
        request.setCharacterEncoding("utf-8");
        String branch = user.getUnitid();
        String result = "{'success':false,'message':'\u4e0a\u4f20\u5931\u8d25\uff01'}";
        String message = null;
        try {
        	
            int filenum = 0;
            int videofilenum = 0;
            int imagefilenum = 0;
            name=fileNames[fileNum];
            String name_type = fileNames[fileNum].substring(name.indexOf(".")+1, fileNames[fileNum].length());
            for (int i = 0; i < fileNames.length; ++i) {
                String name_type1;
                if ("".equals(fileNames[i])) break;
                if (fileNames[i] == null) break;
                if (this.isImageFile(name_type1 = fileNames[i].substring(fileNames[i].indexOf(".") + 1, fileNames[i].length()))) {
                    ++imagefilenum;
                    continue;
                }
                if(this.isVideoFile(name_type1 = fileNames[i].substring(fileNames[i].indexOf(".") + 1, fileNames[i].length()))){
                	++videofilenum;
                }
            }
            //filenum = this.isImageFile(name_type) ? imagefilenum + 1 : videofilenum + 1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String format = sdf.format(new Date());
            String fileName =format+"-0" + (fileNum+1) + fileNames[fileNum].substring(name.indexOf("."), fileNames[fileNum].length());
            /*if ("999888".equals(branch) || "910020000".equals(branch)) {
            	if(prephotosnum==null||"".equals(prephotosnum)){
	                if (imagefilenum > sub_imagefile_num && this.isImageFile(name_type)) {
	                    message = "\u65e0\u6cd5\u4e0a\u4f20\u6587\u4ef6" + fileNames[fileNum] + ";\u603b\u884c\u53ea\u80fd\u4e0a\u4f20" + sub_imagefile_num + "\u5f20\u56fe\u7247";
	                    fileNum=0;
	                    throw new AppException(message);
	                }
            	}else{
	            	if (imagefilenum+Integer.parseInt(prephotosnum) > sub_imagefile_num && this.isImageFile(name_type)) {
		                message = "\u65e0\u6cd5\u4e0a\u4f20\u6587\u4ef6" + fileNames[fileNum] + ";\u603b\u884c\u53ea\u80fd\u4e0a\u4f20" + sub_imagefile_num + "\u5f20\u56fe\u7247";
		                fileNum=0;
		                throw new AppException(message);
		            }
            	}
            	
                if (videofilenum > sub_videofile_num && this.isVideoFile(name_type)) {
                    message = "\u65e0\u6cd5\u4e0a\u4f20\u6587\u4ef6" + fileNames[fileNum] + ";\u603b\u884c\u53ea\u80fd\u4e0a\u4f20" + sub_videofile_num + "\u4e2a\u89c6\u9891";
                    fileNum=0;
                    throw new AppException(message);
                }
           } else {*/
            	if(null==prephotosnum||"".equals(prephotosnum)){
            		if (imagefilenum > branch_imagefile_num && this.isImageFile(name_type)) {
                        message = "\u65e0\u6cd5\u4e0a\u4f20\u6587\u4ef6" + fileNames[fileNum] + ";\u672c\u673a\u6784\u53ea\u80fd\u4e0a\u4f20" + branch_imagefile_num + "\u5f20\u56fe\u7247";
                        fileNum=0;
                        throw new AppException(message);
                    }
            	}else{
		            if (imagefilenum+Integer.parseInt(prephotosnum) > sub_imagefile_num && this.isImageFile(name_type)) {
		                message = "\u65e0\u6cd5\u4e0a\u4f20\u6587\u4ef6" + fileNames[fileNum] + ";\u603b\u884c\u53ea\u80fd\u4e0a\u4f20" + sub_imagefile_num + "\u5f20\u56fe\u7247";
		                fileNum=0;
		                throw new AppException(message);
		            }
            	}
                
                if (videofilenum > branch_videofile_num && this.isVideoFile(name_type)) {
                    message = "\u65e0\u6cd5\u4e0a\u4f20\u6587\u4ef6" + fileNames[fileNum] + ";\u672c\u673a\u6784\u53ea\u80fd\u4e0a\u4f20" + branch_videofile_num + "\u4e2a\u89c6\u9891";
                    fileNum=0;
                    throw new AppException(message);
                }
          //  }
            if (this.isImageFile(name_type)) {
                if ((double)this.uploadFiles.length() > image_file_size * 1024.0 * 1024.0) {
                    message = "\u65e0\u6cd5\u4e0a\u4f20\u6587\u4ef6" + fileNames[fileNum] + ";\u6587\u4ef6\u8fc7\u5927,\u56fe\u7247\u6587\u4ef6\u4e0d\u5f97\u8d85\u8fc7" + image_file_size + "M";
                    throw new AppException(message);
                }
            } else if (this.isVideoFile(name_type)) {
                if ((double)this.uploadFiles.length() > video_file_size * 1024.0 * 1024.0) {
                    message = "\u65e0\u6cd5\u4e0a\u4f20\u6587\u4ef6" + fileNames[fileNum] + ";\u6587\u4ef6\u8fc7\u5927,\u89c6\u9891\u6587\u4ef6\u4e0d\u5f97\u8d85\u8fc7" + video_file_size + "M";
                    throw new AppException(message);
                }
            }
            //根据不同设备类型，将广告图片放到不同的目录
            String sonFileName = "";
            String deviceType = request.getParameter("device_type");
            if(deviceType.equals("04")){
            	//手持广告图片存放路径
            	sonFileName = "HandMachine";
            }else if(deviceType.equals("10")){
            	//填单机广告存放路径
            	sonFileName = "FillInMachine";
            }else if(deviceType.equals("11")){
            	//发卡机广告图片存放路径
            	sonFileName = "CardMachine";
            }
            String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+ File.separator + "adUpload"+ File.separator+ "AdvertisingPicture"+ File.separator+ sonFileName + File.separator+ user.getUnitid();
            //图片上传到机构号的子目录下
            String filePath = path + File.separator + fileName;
            /*
            if ("999888".equals(user.getUnitid()) || "910020000".equals(user.getUnitid())) {
                filePath = String.valueOf(path) + File.separator + "pub" + File.separator + fileName;
            }
            */
            this.advertservice.upLoadFile(filePath, this.uploadFiles);
            
            
            String hostIp = request.getScheme() + "://"+ CheckIP.getRealIp() + ":" + request.getServerPort()+request.getContextPath()+ "/"+"adUpload"+ "/"+user.getUnitid()+"/"+fileName;
            result = "{'success':true,'message':'\u4e0a\u4f20\u6210\u529f\uff01','filename':'" + hostIp + "'}";
            fileNum++;
            if(fileNum>=fileNames.length){
            	fileNum=0;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            result = "{'success':false,'message':'" + message + "'}";
            fileNum=0;
        }
       
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(result);
        this.pro.close();
	}
	
	public boolean isVideoFile(String filetype) throws Exception {
        String[] videotypes = new String[]{"avi", "rmvb", "mp4", "RMVB", "AVI", "MP4", "3gp", "3GP", "swf", "SWF", "wmv", "WMV"};
        for (int i = 0; i < videotypes.length; ++i) {
            if (!videotypes[i].equals(filetype)) continue;
            return true;
        }
        return false;
    }
	
	public boolean isImageFile(String filetype) throws Exception {
        String[] imagetypes = new String[]{"JPG", "jpg", "PNG", "png", "jpeg", "JPEG", "GIF", "gif", "BMP", "bmp"};
        for (int i = 0; i < imagetypes.length; ++i) {
            if (!imagetypes[i].equals(filetype)) continue;
            return true;
        }
        return false;
    }
	
	
	
	/**
	 * 添加图片
	 *//*
	public void addAdvertImage(){
		try {
			User user = (User) getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
			JSONObject json = JSONObject.fromObject(getRequest().getParameter("strJson"));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String NowDate = dateFormat.format(new Date());
			json.accumulate("create_date", NowDate);
			json.accumulate("creator", user.getUsercode());
			ServiceReturn sRet = null;
			@SuppressWarnings("unchecked")
			Integer num = (int) advertImageService.queryImgNumByBranch(json);
			//判断是否可以继续添加
			boolean flag = true;
				if(num>=5){
					sRet = new ServiceReturn(ServiceReturn.FAILURE, "机构不可添加超过5张宣传图,请确认!");
					flag = false;
				}
				//判断图片大小
				long fileLength = content.length();
				if(fileLength>500*1024){
					sRet = new ServiceReturn(ServiceReturn.FAILURE, "添加图片大小不可超过500K,请确认!");
					flag = false;
				}
			if(flag){
				sRet = advertImageService.addImg(json, content);
			}
			JSONObject retJson = convertServiceReturnToJson(sRet);
			retJson.accumulate(SUCCESS, sRet.getSuccess());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html; charset=utf-8");
			ServletActionContext.getResponse().getWriter().print(retJson.toString());
		} catch (Exception e) {
			logger.error("添加设备图片失败,异常原因:",e);
			try {
				ServiceReturn sRet = new ServiceReturn(ServiceReturn.FAILURE, "添加设备图片失败,系统发生异常!");
				JSONObject retJson = convertServiceReturnToJson(sRet);
				retJson.accumulate(SUCCESS, sRet.getSuccess());
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html; charset=utf-8");
				ServletActionContext.getResponse().getWriter().print(retJson.toString());
			} catch (Exception e1) {
				logger.error(e1.getMessage(),e1);
			}
		}
	}*/
	
	/**
	 * 删除广告
	 */
	public String delAdvertImage() throws Exception{
		JSONArray objS = JSONArray.fromObject(super.getJsonString());
		ServiceReturn sRet = advertImageService.delImg(objS);
		String retJson = convertServiceReturnToJson(sRet).toString();
		setActionresult(retJson);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 通过图片id获取图片byte数组(该功能已暂停使用)
	 */
	/*public void queryImgByteById() throws Exception{
		HttpServletResponse response = null;
        HttpServletRequest request = ServletActionContext.getRequest();
        String content = request.getParameter("singleContent");
        ServletOutputStream out = null;
        
        try {
        	byte[] img = advertImageService.getImgByte(content);
            response = ServletActionContext.getResponse();
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            out.write(img);
            out.flush();
		} catch (Exception e) {
			//throw new AppException("获取图片数据错误");
			logger.error("获取设备图片数据错误:" + e);
		} finally{
			if(out!=null){
				out.close();
			}
		}
	}*/

	public File getContent() {
		return content;
	}

	public void setContent(File content) {
		this.content = content;
	}

	public IAdvertImageService getadvertImageService() {
		return advertImageService;
	}

	public void setadvertImageService(IAdvertImageService advertImageService) {
		this.advertImageService = advertImageService;
	}
	

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
	
	public IAdvertParamService getAdvertservice() {
		return advertservice;
	}

	public void setAdvertservice(IAdvertParamService advertservice) {
		this.advertservice = advertservice;
	}
	
	public static int getUploadFileNum() {
		return uploadFileNum;
	}



	public static void setUploadFileNum(int uploadFileNum) {
		AdvertImageAction.uploadFileNum = uploadFileNum;
	}



	public IAdvertImageService getAdvertImageService() {
		return advertImageService;
	}



	public void setAdvertImageService(IAdvertImageService advertImageService) {
		this.advertImageService = advertImageService;
	}



	public File getUpload() {
		return upload;
	}



	public void setUpload(File upload) {
		this.upload = upload;
	}



	public File getUpload1() {
		return upload1;
	}



	public void setUpload1(File upload1) {
		this.upload1 = upload1;
	}



	public File getUpload2() {
		return upload2;
	}



	public void setUpload2(File upload2) {
		this.upload2 = upload2;
	}



	public File getUpload3() {
		return upload3;
	}



	public void setUpload3(File upload3) {
		this.upload3 = upload3;
	}



	public String getUploadFileName() {
		return uploadFileName;
	}



	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}



	public String getUpload1FileName() {
		return upload1FileName;
	}



	public void setUpload1FileName(String upload1FileName) {
		this.upload1FileName = upload1FileName;
	}



	public String getUpload2FileName() {
		return upload2FileName;
	}



	public void setUpload2FileName(String upload2FileName) {
		this.upload2FileName = upload2FileName;
	}



	public String getUpload3FileName() {
		return upload3FileName;
	}



	public void setUpload3FileName(String upload3FileName) {
		this.upload3FileName = upload3FileName;
	}



	public File getUploadFiles() {
		return uploadFiles;
	}



	public void setUploadFiles(File uploadFiles) {
		this.uploadFiles = uploadFiles;
	}



	public String getUpload_fileNames() {
		return upload_fileNames;
	}



	public void setUpload_fileNames(String upload_fileNames) {
		this.upload_fileNames = upload_fileNames;
	}



	public String getUpload_productid() {
		return upload_productid;
	}



	public void setUpload_productid(String upload_productid) {
		this.upload_productid = upload_productid;
	}



	public String getUploaddetails_lableid() {
		return uploaddetails_lableid;
	}



	public void setUploaddetails_lableid(String uploaddetails_lableid) {
		this.uploaddetails_lableid = uploaddetails_lableid;
	}



	public FileInputStream getFileInputStream() {
		return fileInputStream;
	}



	public void setFileInputStream(FileInputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}



	public String getDownloadFileName() {
		return downloadFileName;
	}



	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}



	public String getFiletype() {
		return filetype;
	}



	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
}
