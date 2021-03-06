package com.agree.abt.action.configManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.ContextLoader;

import com.agree.abt.service.confmanager.IProductService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/**
 * 卡种配置
 * <P>
 * 配置发卡卡种，收费标准，关机时间。并对这些配置进行增删改查操作
 * <P>
 * 
 * @author 高艺祥<br>
 *         最后修改日期：2016/11/2
 * @version 0.1
 * 
 */
public class CardTypeAction extends BaseAction {
	private static final Log loger = LogFactory.getLog(ABTComunicateNatp.class);// 日志
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	private static String upLoadFileFace;// 卡种正面图于服务器的地址
	private static String upLoadFileBack;// 卡种背面图于服务器的地址

	/**
	 * 产品信息配置service
	 * <P>
	 * 调用此接口的图片上传功能
	 * </p>
	 */
	private IProductService productService;
	// 页面上传的文件
	private File uploadFiles;
	// 文件名
	private String upload_fileNames;

	/**
	 * 加载页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception {
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());//获取部门集合
		sRet.put(ServiceReturn.FIELD2, super.getLogonUser(true));//获取登录用户信息
		sRet.put(ServiceReturn.FIELD6, ApplicationConstants.RESETPASSWORD);//获取默认密码
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}

	/**
	 * 查询卡种配置
	 * 
	 * @author 高艺祥<br>
	 *         最后修改日期：2016/12/2-15:34
	 * @version 0.1
	 * @throws Exception
	 */
	public String queryCardTypePage() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String jsonString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(jsonString);
		cona.setBMSHeader("ibp.bms.b141_2.01", user);
		cona.set("branch", user.getUnitid());
		String branchParam = obj.getString("branchParam").trim();
		if (branchParam.length() != 9 || branchParam.equals("-请输入子机构号-")) {
			cona.set("branchParam", "0");
		} else {
			cona.set("branchParam", branchParam);
		}
		Map<String, List<String>> map = cona.exchange();

		/*
		 * if(map.get("cmsize").get(0).equals("0")){ throw new
		 * AppException("查询无记录或非法查询"); }
		 */
		String loopNum = (String) map.get("cmsize").get(0);
		int num = Integer.parseInt(loopNum);
		Page pageInfo = this.getPage(obj);

		// 总条数
		pageInfo.setTotal(num);
		// 分页逻辑
		// 获取总页数
		Integer pageNo = (pageInfo.getTotal() % pageInfo.getLimit() == 0) ? pageInfo
				.getTotal() / pageInfo.getLimit()
				: pageInfo.getTotal() / pageInfo.getLimit() + 1;
		// 查询最后一页
		if (pageInfo.getStart() == -1) {
			pageInfo.setRowStart((pageNo - 1) * pageInfo.getLimit());
			pageInfo.setRowEnd(pageInfo.getRowStart()
					+ (pageInfo.getTotal() % pageInfo.getLimit() == 0 ? pageInfo
							.getLimit() : pageInfo.getTotal()
							% pageInfo.getLimit()));
			// 设置总页数
			pageInfo.setPage(pageNo);
			// 设置总条数
			pageInfo.setTotal(pageInfo.getTotal());
		} else {
			pageInfo.setRowStart((pageInfo.getStart() - 1)
					* pageInfo.getLimit());
			// 每页显示条数
			pageInfo.setRowEnd((pageInfo.getRowStart() + pageInfo.getLimit()) <= pageInfo
					.getTotal() ? (pageInfo.getRowStart() + pageInfo.getLimit())
					: pageInfo.getTotal());
			// 当前页
			pageInfo.setPage(pageNo);
			// 总条树
			pageInfo.setTotal(pageInfo.getTotal());
		}

		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 循环得到的当前页所有的值
		for (int i = pageInfo.getRowStart(); i < pageInfo.getRowEnd(); i++) {
			HashMap<String, Object> hld = new HashMap<String, Object>();
			// 卡种id
			hld.put("cardTypeId", map.get("cardtypeid").get(i));
			// 卡种名称
			hld.put("cardTypeName", map.get("cardtypename").get(i));
			// 机构号
			hld.put("branch", map.get("branch").get(i));
			//授权等级
			hld.put("accreditrank", map.get("accreditrank").get(i));
			// 卡种介绍
			hld.put("cardTypeDesc", map.get("cardtypedesc").get(i));
			// 产品代码
			hld.put("producecode", map.get("producecode").get(i));
			//凭证类型
			hld.put("vouchertype", map.get("vouchertype").get(i));
			// 拼接图片访问路径
			// 获得项目访问路径
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort();
			// 卡种正面图,访问路径
			hld.put("cardtypeimgface", basePath
					+ map.get("cardtypeimgface").get(i));
			// 卡种背面图,访问路径
			hld.put("cardtypeimgback", basePath
					+ map.get("cardtypeimgback").get(i));
			// 短息通知收费标准(0:免费;1:包月;2:包年;1,2:用户可选)
			hld.put("messageOutlay", map.get("messageoutlay").get(i));
			// 状态(0:不启用,1:启用)
			hld.put("status", map.get("status").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		// 想页面返回结果
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}

	/**
	 * 添加卡种配置
	 * <p>
	 * 卡种编号,卡种名称,卡种描述,正反面图片,收费标准,关机时间
	 * </p>
	 * <p>
	 * 卡种编号=机构号+hhmmssSSS
	 * </p>
	 * 
	 * @author 高艺祥<br>
	 *         最后修改日期:2016/11/29
	 * @version 0.1
	 * @throws Exception
	 */
	public synchronized String addCardParam() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		String jsonString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(jsonString);

		cona.setBMSHeader("ibp.bms.b141_1.01", user);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		// 卡种ld
		cona.set("cardtypeid", user.getUnitid() + sdf.format(new Date()));
		// 卡种名称
		cona.set("cardtypename", obj.getString("cardTypeName"));
		// 机构号
		cona.set("branch", user.getUnitid());
		// 卡种描述
		cona.set("cardtypedesc", obj.getString("cardTypeDesc"));
		//授权等级
		if(obj.getString("accreditrank").equals("1级以上")){
			cona.set("accreditrank", "1");
		}else{
			cona.set("accreditrank", obj.getString("accreditrank").equals("2级以上") ? "2" : "5");
		}
		
		// 产品代码
		cona.set("producecode", obj.getString("producecode"));
		//凭证种类
		cona.set("vouchertype", obj.getString("vouchertype"));
		// 状态(0:不启用,1:启用)
		cona.set("status", obj.getString("status").equals("on") ? "1" : "0");
		// 卡种正面图于工程的相对路径
		if (upLoadFileFace == null) {
			cona.set("cardtypeimgface", "0");
		} else {
			cona.set("cardtypeimgface", upLoadFileFace);
			
			//设置图片MD5码
			FileInputStream fis = new FileInputStream(path+File.separator+upLoadFileFace.substring(4, upLoadFileFace.length()));
			String picturemd5_face = DigestUtils.md5DigestAsHex(IOUtils.toByteArray(fis));
			cona.set("picturemd5face", picturemd5_face);
		}
		// 卡种背面图于工程的相对路径
		if (upLoadFileBack == null) {
			cona.set("cardtypeimgback", "0");
		} else {
			cona.set("cardtypeimgback", upLoadFileBack);
			
			//设置图片MD5码
			FileInputStream fis = new FileInputStream(path+File.separator+upLoadFileBack.substring(4, upLoadFileBack.length()));
			String picturemd5_back = DigestUtils.md5DigestAsHex(IOUtils.toByteArray(fis));
			cona.set("picturemd5back", picturemd5_back);
		}
		// 短信通知收费标准(0:免费;1:包月;2:包年;1,2:用户可选)
		if (obj.getString("messageOutlay").equals("免费")) {
			cona.set("messageoutlay", "0");
		} else if (obj.getString("messageOutlay").equals("包月")) {
			cona.set("messageoutlay", "1");
		} else {
			String messageoutlay = obj.getString("messageOutlay").equals("包年") ? "2": "1,2";
			cona.set("messageoutlay",messageoutlay);
		}

		cona.exchange();
		ServiceReturn tet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject resultJson = super.convertServiceReturnToJson(tet);
		super.setActionresult(resultJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 修改卡种
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editCardType() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String jsonString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(jsonString);
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");

		cona.setBMSHeader("ibp.bms.b141_3.01", user);
		
		String string = obj.getString("cardTypeId");
		String cardType = string.substring(16, string.length()-3);
		
		// 卡种ld
		cona.set("cardtypeid",cardType);
		// 卡种名称
		cona.set("cardtypename", obj.getString("cardTypeName"));
		// 机构号
		cona.set("branch", user.getUnitid());
		// 卡种描述
		cona.set("cardtypedesc", obj.getString("cardTypeDesc"));
		//授权等级
		if(obj.getString("accreditrank").equals("1级以上")){
			cona.set("accreditrank", "1");
		}else{
			cona.set("accreditrank", obj.getString("accreditrank").equals("2级以上") ? "2" : "5");
		}
		// 产品代码
		cona.set("producecode", obj.getString("producecode"));
		// 状态(0:不启用,1:启用)
		cona.set("status", obj.getString("status").equals("on") ? "1" : "0");
		//凭证类型
		cona.set("vouchertype", obj.getString("vouchertype"));
		// 卡种正面图于工程的相对路径
		if (upLoadFileFace == null) {
			cona.set("cardtypeimgface", "0");
		} else {
			cona.set("cardtypeimgface", upLoadFileFace);
			
			//设置图片MD5码
			FileInputStream fis = new FileInputStream(path+File.separator+upLoadFileFace.substring(4, upLoadFileFace.length()));
			String picturemd5_face = DigestUtils.md5DigestAsHex(IOUtils.toByteArray(fis));
			cona.set("picturemd5face", picturemd5_face);
		}
		// 卡种背面图于工程的相对路径
		if (upLoadFileBack == null) {
			cona.set("cardtypeimgback", "0");
		} else {
			cona.set("cardtypeimgback", upLoadFileBack);
			
			//设置图片MD5码
			FileInputStream fis = new FileInputStream(path+File.separator+upLoadFileBack.substring(4, upLoadFileBack.length()));
			String picturemd5_back = DigestUtils.md5DigestAsHex(IOUtils.toByteArray(fis));
			cona.set("picturemd5back", picturemd5_back);
		}
		// 短信通知收费标准(0:免费;1:包月;2:包年;1,2:用户可选)
		if (obj.getString("messageOutlay").equals("免费")) {
			cona.set("messageoutlay", "0");
		} else if (obj.getString("messageOutlay").equals("包月")) {
			cona.set("messageoutlay", "1");
		} else {
			String messageoutlay = obj.getString("messageOutlay").equals("包年") ? "2": "1,2";
			cona.set("messageoutlay",messageoutlay);
		}
		
		Map<String, List<String>> map = cona.exchange();
		//删除卡种正面图片
		if(!map.get("cardtypeimgface").get(0).equals("0")){
			String imgFaceVisit = map.get("cardtypeimgface").get(0);
			int indexOfFace = imgFaceVisit.indexOf("abt/") + 4;
			String imgPathFace = path + imgFaceVisit.substring(indexOfFace);
			File fileFace = new File(imgPathFace);
			fileFace.delete();
		}
		
		//删除卡种背面图片
		if(!map.get("cardtypeimgback").get(0).equals("0") ){
			String imgBackVisit = map.get("cardtypeimgback").get(0);
			int indexOfBack = imgBackVisit.indexOf("abt/") + 4;
			String imgPathBack = path + imgBackVisit.substring(indexOfBack);
			File fileBack = new File(imgPathBack);
			fileBack.delete();
		}
		
		
		ServiceReturn tet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject resultJson = super.convertServiceReturnToJson(tet);
		super.setActionresult(resultJson.toString());
		
		return AJAX_SUCCESS;
	}

	/**
	 * 删除卡种
	 * <p>
	 * 根据卡种id和机构号删除
	 * </p>
	 * 
	 * @author 高艺祥<br>
	 *         最后修改日期:2016/12/5
	 * @version 0.1
	 */
	public String delCardType() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		//获得页面请求负载
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		
		//获得项目路径
		String path = ContextLoader.getCurrentWebApplicationContext()
				.getServletContext().getRealPath("/");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
			Object obj[] = jsonObj.values().toArray();
			
			String cardtypeid = obj[0].toString();//卡种id
			String branch = obj[1].toString();//机构号
			String imgFace = obj[2].toString();//卡种正面图片访问地址
			String imgBack = obj[3].toString();//卡种背面图片访问地址
			
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b141_4.01", user);
			cona.set("branch", branch);
			cona.set("cardtypeid", cardtypeid);
			cona.exchange();
			
			// 删除卡种正面图片
			int indexOfFace = imgFace.indexOf("abt/") + 4;
			String imgPathFack = path + imgFace.substring(indexOfFace);
			File fileFace = new File(imgPathFack);
			fileFace.delete();
			
			// 删除卡种背面图片
			int indexOfBack = imgBack.indexOf("abt/") + 4;
			String imgPathBack = path + imgBack.substring(indexOfBack);
			File fileBack = new File(imgPathBack);
			fileBack.delete();
		}
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	public void queryCardProduct() throws Exception{
		//获得rquest域
		HttpServletRequest request = ServletActionContext.getRequest();
		//从session域中获取user
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		cona.setBMSHeader("ibp.bms.b141_5.01", user);
		Map<String, List<String>> map = cona.exchange();
		
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		int num = map.get("rm4").size();
		for (int i = 0; i < num; i++) {
			Map<String, String> hld = new HashMap<String, String>();
			hld.put("pdpc", map.get("pdpc").get(i));
			hld.put("rm4", map.get("rm4").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}

	/**
	 * 上传卡种正面图片
	 * 
	 * @throws Exception
	 * @author 高艺祥<br>
	 *         最后修改日期:2016/11/23
	 * @version 0.1
	 */
	public synchronized void uploadImgFace() throws Exception {
		// 从session域中获取当前用户信息
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute(ApplicationConstants.LOGONUSER);
		// 得到HttpServletRequest对象
		HttpServletRequest request = ServletActionContext.getRequest();
		// 设置请求的编码
		request.setCharacterEncoding("utf-8");
		// 得到当前用户的机构号
		String branch = user.getUnitid();
		String result = "{'success':false,'message':'上传失败！'}";
		// 声明一个数组,用作储存文件名
		try {
			String fileName = branch + "-" + UUID.randomUUID().toString()
					+ ".png";
			long fileSize = uploadFiles.length();
			if (fileSize <= 512000) {
				upLoadFileFace = upLoadFile(fileName,
						uploadFiles);
			} else {
				throw new AppException("图片大小不能超过500K");
			}

			result = "{'success':true,'message':'上传成功！','filename':'"
					+ fileName + "'}";
		} catch (Exception e) {
			result = "{'success':false,'message':'图片不能超过500K'}";
		}
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(result);
	}

	/**
	 * 上传卡种背面图片
	 * 
	 * @throws Exception
	 * @author 高艺祥<br>
	 *         最后修改日期:2016/11/23
	 * @version 0.1
	 */
	public synchronized void uploadImgBack() throws Exception {
		// 从session域中获取当前用户信息
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute(ApplicationConstants.LOGONUSER);
		// 得到HttpServletRequest对象
		HttpServletRequest request = ServletActionContext.getRequest();
		// 设置请求的编码
		request.setCharacterEncoding("utf-8");
		// 得到当前用户的机构号
		String branch = user.getUnitid();
		String result = "{'success':false,'message':'上传失败！'}";
		// 声明一个数组,用作储存文件名
		try {
			String fileName = branch + "-" + UUID.randomUUID().toString()
					+ ".png";
			long fileSize = uploadFiles.length();
			if (fileSize <= 512000) {
				upLoadFileBack = upLoadFile(fileName,
						uploadFiles);
			} else {
				throw new AppException("图片不能超过500K");
			}

			result = "{'success':true,'message':'上传成功！','filename':'"
					+ fileName + "'}";
		} catch (Exception e) {
			result = "{'success':false,'message':'图片不能大于500K'}";
		}
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(result);
	}
	
	/**
	 * 上传图片共用方法
	 * @author 高艺祥<br>
	 * 					最后修改日期:2016/12/10
	 *@version 0.1
	 */
	public String upLoadFile(String fileName, File upLoadFile) throws Exception {
		//获得request对象
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//获得项目名称
		String projectName = request.getContextPath();
		
		String visitPath = null;
		try {
			
			//得到项目根目录
			String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
			
			//filePath = path + File.separator + "products"+ File.separator + fileName;
			
			String branch = user.getUnitid();
			//图片磁盘路径
			String filePath = path + File.separator + "cardTypePicture"+ File.separator + branch + File.separator + fileName;
			//图片的访问路径
			visitPath = projectName + "/cardTypePicture" + "/" + branch + "/" + fileName;
			
			FileInputStream stream = new FileInputStream(upLoadFile);
			File outFile=new File(filePath);
			File parent=outFile.getParentFile();
			
			if(!parent.exists()){
				parent.mkdirs();
			}
			File[] files = parent.listFiles();
			if(files != null && files.length>0){
				for(File file : files){
					if (file.getName().indexOf(outFile.getName().substring(0, outFile.getName().indexOf("."))) == -1) continue;
	                file.delete();
				}
			}
			OutputStream out=new FileOutputStream(outFile);
			byte[] temp=new byte[1024];
			int length=0;
			while((length=stream.read(temp))>0){
				out.write(temp, 0, length);
			}
			stream.close();
			out.close();
			//this.convertPdf(fileName, upLoadFile);
			/*
			stream = new FileInputStream(outFile);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1) {
				bos.write(b, 0, n);
				bos.flush();
			}
			buffer = bos.toByteArray();
			stream.close();
			bos.close();
			*/
		} catch (Exception e) {
			loger.error(e.getMessage(), e);
		}
       /*
		this.pdf = new byte[buffer.length];
		System.arraycopy(buffer, 0, pdf, 0, buffer.length);
		*/
		return visitPath;
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

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}

	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
}
