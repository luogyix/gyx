package com.agree.abt.action.configManager;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.ContextLoader;

import com.agree.abt.service.confmanager.IAdvertParamService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.CheckIP;
import com.agree.util.PropertiesUtils;

/**
 * 自助设备广告管理
 * @ClassName: AdvertInfoAction.java
 * @company 赞同科技
 * @author Hujuqiang
 * @date 2016-8-16
 */

@SuppressWarnings("all")
public class AdvertInfoAction extends BaseAction{

	private static final Log logger = LogFactory.getLog(AdvertInfoAction.class);//日志
	private static final long serialVersionUID = 2141897762615781445L;
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

	public static int getFileNum() {
		return fileNum;
	}

	public static void setFileNum(int filenum) {
		AdvertInfoAction.fileNum = filenum;
	}
	
	private IAdvertParamService advertservice;
	public IAdvertParamService getAdvertservice() {
		return advertservice;
	}

	public void setAdvertservice(IAdvertParamService advertservice) {
		this.advertservice = advertservice;
	}

	public String getUpload_fileNames() {
		return upload_fileNames;
	}

	public void setUpload_fileNames(String upload_fileNames) {
		this.upload_fileNames = upload_fileNames;
	}
	public File getUploadFiles() {
		return uploadFiles;
	}
	public void setUploadFiles(File uploadFiles) {
		this.uploadFiles = uploadFiles;
	}
	
	
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
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
	public static int getUploadFileNum() {
		return uploadFileNum;
	}
	public static void setUploadFileNum(int uploadFileNum) {
		AdvertInfoAction.uploadFileNum = uploadFileNum;
	}

	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
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
	
	public String getCustImg() throws Exception {
		 	HttpServletResponse response = null;
	        HttpServletRequest request = ServletActionContext.getRequest();
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
	        String filePath = request.getParameter("filename");
	        String branch = request.getParameter("branch");
	        /**
	        int index1=filePath.indexOf(request.getContextPath());
	        int index2=filePath.lastIndexOf("/");
	        String ip=filePath.substring(0, index1);
	        String filename=filePath.substring(index2+1);
	        
	        String path =ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+ File.separator + "adUpload"+ File.separator+user.getUnitid()+File.separator+filename;
			*/
	        /**
			if ("999888".equals(branch)||"910020000".equals(branch)) {
				filePath = path+ File.separator+"ad" + File.separator +"pub"+File.separator+filename;			
			}
			*/
	        /*
			File file1 =new File(path);
			if(!file1.exists()){
				//filePath = path+ File.separator+"ad" + File.separator + "custimg" + File.separator + "nophoto.jpg";
				return null;
			}
			File file = new File(path);
			*/
	    	InputStream is = null;
	    	ByteArrayOutputStream bos = null;
	    	byte[] retBytes = null;
	    	try {
	    		is = this.getInputStream(filePath);;
	    		bos = new ByteArrayOutputStream(1000);
	    		byte[] b = new byte[1000];
	    		int n;
	    		while ((n = is.read(b)) != -1) {
	    			bos.write(b, 0, n);
	    		}
	    		retBytes = bos.toByteArray();
	    		is.read(b);
	    	} catch (FileNotFoundException e1) {
	    		e1.printStackTrace();
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}finally{
	    		try {
	    			if(bos!=null){
	    				bos.close();
	    			}
	    			if(is!=null){
	    				is.close();
	    			}
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	    	} 
	        ServletOutputStream out = null;
	        try {
	            response = ServletActionContext.getResponse();
	            response.setContentType("multipart/form-data");
	            out = response.getOutputStream();
	            out.write(retBytes);
	            out.flush();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (out != null) {
	                try {
	                    out.close();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	            if (response != null) {
	                try {
	                	out.close();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        pro.close();
			return null;
    }
	
	@SuppressWarnings("unchecked")
	public String queryAdvertParam() throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String jsonString = super.getJsonString();
		User user = super.getLogonUser(true);
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Page pageInfo = super.getPage(jsonObj);//得到Pae分页对象	
		List <Object> tlist=this.advertservice.getAdvertParam(user, pageInfo);
		
		super.setActionResult(pageInfo, tlist, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 添加广告
	 * */
	public String addAdvertParam() throws Exception{
		String jsonString = super.getJsonString();
		ServiceReturn tet=this.advertservice.addAdvertParam(jsonString);
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 修改广告
	 * @return
	 * @throws Exception
	 */
	public String editAdvertParam() throws Exception{
		String jsonString=super.getJsonString();
		ServiceReturn tet=this.advertservice.editAdvertParam(jsonString);
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	public String deleteAdvertParam() throws Exception {
        String jsonString = BaseAction.getJsonString();
        ServiceReturn tet = this.advertservice.deleteAdvertParam(jsonString);
        JSONObject returnJson = BaseAction.convertServiceReturnToJson((ServiceReturn)tet);
        super.setActionresult(returnJson.toString());
        return "AJAX_SUCCESS";
    }
	
	public void uploadDetailsPic() throws Exception{
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
            String fileName ="0" + (fileNum+1) + fileNames[fileNum].substring(name.indexOf("."), fileNames[fileNum].length());
            if(prephotosnum!=null&&!"".equals(prephotosnum)){
            	fileName ="0" + (Integer.parseInt(prephotosnum)+fileNum+1) + fileNames[fileNum].substring(name.indexOf("."), fileNames[fileNum].length());
            }
            if ("999888".equals(branch) || "910020000".equals(branch)) {
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
            } else {
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
            }
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
            String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+ File.separator+ "adUpload"+ File.separator+ "AdvertisingPicture"+ File.separator+ "CardMachine"+ File.separator+ user.getUnitid();
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
	
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public byte[] getFileBytes(File upload){
		byte[] buffer=null;              
		try{
			 FileInputStream stream=new FileInputStream(upload);
			 ByteArrayOutputStream bos=new ByteArrayOutputStream(1000);
			 byte[] b=new byte[1000];
			 int n;
			 while((n=stream.read(b))!=-1){
				 bos.write(b,0,n);
			 }
			 buffer=bos.toByteArray();
			 bos.close();
			 stream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return buffer;
	}
	
	 public String downloadFiles() throws Exception {
	        this.pro.getFile("/conf.properties");
	        HttpServletRequest request = ServletActionContext.getRequest();
	        HttpServletResponse response = ServletActionContext.getResponse();
	        ServletOutputStream out = response.getOutputStream();
	        String gwq_filepath = this.pro.read("gwq_filepath");
	        String path = "/gwq/asy";
	        String jsonString = request.getParameter("querycondition_str");
	        String filename = null;
	        String name_type = null;
	        JSONObject jsonObj = JSONObject.fromObject((Object)jsonString);
	        ZipOutputStream zipout = new ZipOutputStream((OutputStream)out);
	        String[] filenames = jsonObj.getString("filename").split(";");
	        String branch = jsonObj.getString("branch");
	        if (filenames.length > 1) {
	            String zipname = "IMAGES_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".zip";
	            response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(zipname.getBytes("UTF-8"), "ISO-8859-1") + "\"");
	            response.setContentType("application/zip;charset=ISO8859-1");
	            zipout.setComment("Download selected files \nasone WINRAR file:\ndownloadname.rar");
	            zipout.setLevel(1);
	            for (int i = 0; i < filenames.length; ++i) {
	                File file;
	                filename = filenames[i];
	                name_type = filename.substring(filename.indexOf(".") + 1);
	                if (!"".equals(gwq_filepath)) {
	                    path = gwq_filepath;
	                }
	                if (this.isImageFile(name_type)) {
	                    path = String.valueOf(path) + "/ad";
	                } else if (this.isVideoFile(name_type)) {
	                    path = String.valueOf(path) + "/video";
	                }
	                String filePath = String.valueOf(path) + File.separator + branch + File.separator + filename;
	                if ("999888".equals(branch) || "910020000".equals(branch)) {
	                    filePath = String.valueOf(path) + File.separator + "pub" + File.separator + filename;
	                }
	                if (!(file = new File(filePath)).exists()) {
	                	AdvertInfoAction.logger.error((Object)"\u6240\u4e0b\u8f7d\u7684\u66f4\u65b0\u5305\u4e0d\u5b58\u5728!");
	                    throw new AppException("\u8981\u4e0b\u8f7d\u7684\u6587\u4ef6" + filename + "\u4e0d\u5b58\u5728!");
	                }
	                zipout.putNextEntry(new ZipEntry(filename));
	                FileInputStream fis = new FileInputStream(filePath);
	                BufferedInputStream fr = new BufferedInputStream(fis);
	                int n = 0;
	                while ((n = fr.read()) != -1) {
	                    zipout.write(n);
	                }
	                fr.close();
	            }
	            zipout.closeEntry();
	            zipout.finish();
	        } else {
	            File file;
	            int n;
	            filename = filenames[0];
	            name_type = filename.substring(filename.indexOf(".") + 1);
	            response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"");
	            if (!"".equals(gwq_filepath)) {
	                path = gwq_filepath;
	            }
	            if (this.isImageFile(name_type)) {
	                path = String.valueOf(path) + "/ad";
	            } else if (this.isVideoFile(name_type)) {
	                path = String.valueOf(path) + "/video";
	            }
	            response.setContentType(String.valueOf(this.filetype) + ";charset=ISO8859-1");
	            String filePath = String.valueOf(path) + File.separator + branch + File.separator + filename;
	            if ("999888".equals(branch) || "910020000".equals(branch)) {
	                filePath = String.valueOf(path) + File.separator + "pub" + File.separator + filename;
	            }
	            if (!(file = new File(filePath)).exists()) {
	            	AdvertInfoAction.logger.error((Object)"\u6240\u4e0b\u8f7d\u7684\u66f4\u65b0\u5305\u4e0d\u5b58\u5728!");
	                throw new AppException("\u8981\u4e0b\u8f7d\u7684\u6587\u4ef6" + filename + "\u4e0d\u5b58\u5728!");
	            }
	            FileInputStream fis = new FileInputStream(file);
	            byte[] b = new byte[2048];
	            while ((n = fis.read(b)) != -1) {
	                out.write(b, 0, n);
	            }
	            out.flush();
	            fis.close();
	        }
	        if (out != null) {
	            out.close();
	        }
	        this.pro.close();
	        return "downloadSuccess";
	    }

	    public boolean isImageFile(String filetype) throws Exception {
	        String[] imagetypes = new String[]{"JPG", "jpg", "PNG", "png", "jpeg", "JPEG", "GIF", "gif", "BMP", "bmp"};
	        for (int i = 0; i < imagetypes.length; ++i) {
	            if (!imagetypes[i].equals(filetype)) continue;
	            return true;
	        }
	        return false;
	    }

	    public boolean isVideoFile(String filetype) throws Exception {
	        String[] videotypes = new String[]{"avi", "rmvb", "mp4", "RMVB", "AVI", "MP4", "3gp", "3GP", "swf", "SWF", "wmv", "WMV"};
	        for (int i = 0; i < videotypes.length; ++i) {
	            if (!videotypes[i].equals(filetype)) continue;
	            return true;
	        }
	        return false;
	    }
	    
	 // 从服务器获得一个输入流(本例是指从服务器获得一个image输入流)
	    public static InputStream getInputStream(String URL_PATH) {
	    	InputStream inputStream = null;
	    	HttpURLConnection httpURLConnection = null;
		    try {
			    URL url = new URL(URL_PATH);
			    httpURLConnection = (HttpURLConnection) url.openConnection();
			    // 设置网络连接超时时间
			    httpURLConnection.setConnectTimeout(3000);
			    // 设置应用程序要从网络连接读取数据
			    httpURLConnection.setDoInput(true);
			    httpURLConnection.setRequestMethod("GET");
			    int responseCode = httpURLConnection.getResponseCode();
			    if (responseCode == 200) {
			    // 从服务器返回一个输入流
			    inputStream = httpURLConnection.getInputStream();
			    }
		    } catch (MalformedURLException e) {
		    	e.printStackTrace();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		    return inputStream;
	    }
}
