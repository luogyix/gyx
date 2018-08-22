package com.agree.abt.action.configManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;

/**
 * 排队机主题配置
 * @ClassName: ThemeinfoConfigAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-8-14
 */
public class ThemeinfoConfigAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private ABTComunicateNatp cona;
	
	//private String busSeq = "";//保存业务队列信息
	//private List<String> busSepList=new ArrayList<String>();
	String btImg = "";
	String bgImg = "";
	String bgImgData="";
	String btImgData="";
	String fontColor="";
	String newColor="";
	int width=38;
	private List<File> upload=new ArrayList<File>();//保存上传的文件
	private List<String> uploadFileName;//保存上传文件的名字
	//private boolean flag1;//判断背景图片是否更改
	//private boolean flag2;//判断按钮图片是够更改

	public List<String> getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(List<String> uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public List<File> getUpload() {
		return upload;
	}

	public void setUpload(List<File> upload) {
		this.upload = upload;
	}

	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		//Set<String> priv=this.getUserInpageFunc();
		//sRet.put(ServiceReturn.FIELD2,priv);//获取登录用户信息 也就是利用这个获取到此部分功能
		
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
//
//	/**
//	 * 
//	 * @Title: queryTheme
//	 * @Description: 查询主题参数信息
//	 * @return String
//	 * @throws Exception
//	 */
//	public String queryTheme() throws Exception {
//		String path=ServletActionContext.getRequest().getRealPath("/upload");//保存的默认路径,注意在Tomecate底下的
//		HttpServletRequest req = ServletActionContext.getRequest();
//		User user = (User) req.getSession().getAttribute(
//				ApplicationConstants.LOGONUSER);
//		 
//		cona.setHeader("B106", user.getUnitid().toString(), user.getUsercode(), "03");
//		cona.set("funcCode", "2");
//		HashMap<String, List<String>> map = (HashMap) cona.exchange();
//		List<Map> list = new ArrayList<Map>();
//		String info=cona.validMap(map);
//		if(info!=null&&info.trim().length()!=0){
//			throw new AppException(info);
//		}else{
//			String loopNum = map.get("loopNum").get(0);
//			int num = Integer.parseInt(loopNum);
//			for (int i = 0; i < num; i++) {
//				Map<String, String> hld = new HashMap<String, String>();
//				hld.put("themeName", map.get("themeName").get(i));
//				bgImg=map.get("bgImg").get(i);
//				hld.put("bgImg",bgImg);
//				bgImgData = map.get("bgImgData").get(i);
//				File backPic = new File(path+ File.separatorChar+"back.png");
//				FileOutputStream fis = new FileOutputStream(backPic);
//				BASE64Decoder decoder = new BASE64Decoder();
//				fis.write(decoder.decodeBuffer(bgImgData));
//				fis.flush();
//				fis.close();
//				btImgData= map.get("btImgData").get(i);
//				File BtPic = new File(path+ File.separatorChar+"button.png");
//				FileOutputStream f = new FileOutputStream(BtPic);
//				f.write(decoder.decodeBuffer(btImgData));
//				f.flush();
//				btImg=map.get("btImg").get(i);
//				hld.put("btImg",btImg );
//				hld.put("textFont", map.get("textFont").get(i));
//				fis.close();
//				String textStyle = map.get("textStyle").get(i);
//				if (textStyle.equals("0")) {
//					textStyle = "常规";
//				} else if (textStyle.equals("1")) {
//					textStyle = "粗体";
//				} else if (textStyle.equals("2")) {
//					textStyle = "斜体";
//				}
//				hld.put("textStyle", textStyle);
//				hld.put("textSize", map.get("textSize").get(i));
//				String color=map.get("textColor").get(i);
//				String[] arr=color.split(",");
//				if(arr.length>1){
//					
//				
//				String r=Integer.toHexString(Integer.parseInt(arr[0]));
//				String g=Integer.toHexString(Integer.parseInt(arr[1]));
//				String b=Integer.toHexString(Integer.parseInt(arr[2]));
//				hld.put("textColor", r+g+b);
//				}else{
//					hld.put("textColor", "");
//				}
//				
//				String start = map.get("startDate").get(i);
//				if (start.trim().length() > 0) {
//					start = start.substring(0, 4) + "-" + start.substring(4, 6) + "-"
//							+ start.substring(6, 8);
//				}
//				hld.put("startDate", start);
//				String end = map.get("endDate").get(i);
//				if (end.trim().length() > 0) {
//					end = end.substring(0, 4) + "-" + end.substring(4, 6) + "-"
//							+ end.substring(6, 8);
//				}
//
//				hld.put("endDate", end);
//				busSepList=map.get("busSeq");
//			    busSeq=map.get("busSeq").get(i);
//			    hld.put("busSeq", busSeq);
//				list.add(hld);
//			}
//
//		}
//		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
//		sRet.put(ServiceReturn.FIELD1,list);
//		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
//		super.setActionresult(returnJson.toString());
//		return AJAX_SUCCESS;
//	}
//	
//	
//	/**
//	 * @Title: recTheme
//	 * @Description: 恢复排队机主题参数信息
//	 * @return String
//	 * @throws Exception
//	 */
//	public String recTheme() throws Exception {
//		String inputJsonStr = super.getJsonString();
//		JSONObject obj = JSONObject.fromObject(inputJsonStr);
//		HttpServletRequest req = ServletActionContext.getRequest();
//		User user = (User) req.getSession().getAttribute(
//				ApplicationConstants.LOGONUSER);
//		 
//		cona.setHeader("B106", user.getUnitid().toString(), user.getUsercode(), "03");
//		cona.set("funcCode", "5");
//		cona.set("themeName", obj.getString("theme"));
//		HashMap map = (HashMap) cona.exchange();
//		String info=cona.validMap(map);
//		if(info!=null&&info.trim().length()!=0){
//			throw new AppException(info);
//		}else{
//			
//		}
//
//		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
//		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
//		super.setActionresult(returnJson.toString());
//		return AJAX_SUCCESS;
//	}
//
//	/**
//	 * 获得业务队列信息
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public String queryQue() throws Exception {
//		HttpServletRequest req = ServletActionContext.getRequest();
//		User user = (User) req.getSession().getAttribute(
//				ApplicationConstants.LOGONUSER);
//		 
//		cona.setHeader("B103", user.getUnitid().toString(), user.getUsercode(), "03");
//		cona.set("funcCode", "2");
//		HashMap<String, List<String>> map = (HashMap) cona.exchange();
//		String info=cona.validMap(map);
//		List<Map> list = new ArrayList<Map>();
//		List<Map> queList = new ArrayList<Map>();
//		if(info!=null&&info.trim().length()!=0){
//			throw new AppException(info);
//		}else{
//			String loopNum = map.get("loopNum").get(0);
//			int num = Integer.parseInt(loopNum);
//			for (int i = 0; i < num; i++) {
//				Map<String, String> hld = new HashMap<String, String>();
//				hld.put("busId", map.get("busId").get(i));
//				hld.put("busNameCh", map.get("busNameCh").get(i));
//				//hld.put("busNameCh2", map.get("busNameCh").get(i));
//				hld.put("busPrefix", map.get("busPrefix").get(i));
//				list.add(hld);
//			}
//			
//			for(int n=0;n<busSepList.size();n++){
//				busSeq =busSepList.get(n);
//				String[] buss = busSeq.split(">>");
//				for (int i = 0; i < buss.length; i++) {
//					String busId = buss[i];
//					for (int j = 0; j < list.size(); j++) {
//						String id = (String) list.get(j).get("busId");
//						if (busId.equals(id)) {
//							Map<String, String> hld = new HashMap<String, String>();
//							hld.put("busId", map.get("busId").get(j));
//						//	if (map.get("busLevel").get(j).equals("1")) {
//								hld.put("busNameCh", map.get("busNameCh").get(j));
//						//	} else if (map.get("busLevel").get(j).equals("2")) {
//							//	hld.put("busNameCh2", map.get("busNameCh").get(j));
//						//	}
//							hld.put("busPrefix", map.get("busPrefix").get(j));
//							queList.add(hld);
//
//						}
//					}
//				}
//			}
//		}
//		
//		
//		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
//		sRet.put(ServiceReturn.FIELD1, queList);
//		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
//		super.setActionresult(returnJson.toString());
//		return AJAX_SUCCESS;
//	}
//
//	/**
//	 * 获取主题名称
//	 * 
//	 * @throws Exception
//	 */
//	public void getTheme() throws Exception {
//		User user = (User) ServletActionContext.getRequest().getSession()
//				.getAttribute(ApplicationConstants.LOGONUSER);
//		 
//		cona.setHeader("B106", user.getUnitid().toString(), user.getUsercode(), "03");
//		cona.set("funcCode", "2");
//		HashMap map = (HashMap) cona.exchange();
//		String info = cona.validMap(map);
//		List<HashMap> tlist = new ArrayList<HashMap>();
//		if (info != null && info.trim().length() != 0) {
//			throw new AppException(info);
//		} else {
//			if (map.get("loopNum") != null) {
//				ArrayList tt = (ArrayList) map.get("loopNum");
//				String loopNum = (String) tt.get(0);
//				ArrayList themeName = (ArrayList) map.get("themeName");
//				int num = 0;
//				for (int i = 0; i < Integer.parseInt(loopNum); i++) {
//					HashMap tmp = new HashMap();
//
//					String theme_Name = (String) themeName.get(i);
//					if (theme_Name.equals("") == false) {
//						tmp.put("theme_Name", theme_Name);
//						tmp.put("key", num + i);
//						tlist.add(tmp);
//					}
//				}
//			}
//		}
//
//		ServiceReturn ret = new ServiceReturn(true, "");
//		ret.put(ServiceReturn.FIELD1, tlist);
//		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
//		ServletActionContext.getResponse().getWriter().print(
//				super.convertServiceReturnToJson(ret));
//	}
//
//	
//	/**
//	 * 
//	 * @Title: editTheme
//	 * @Description: 修改主题参数
//	 * @return String
//	 * @throws Exception
//	 */
//	public String editTheme() throws Exception {
//		File img=null;
//		String path=ServletActionContext.getRequest().getRealPath("/upload");//保存的默认路径,注意在Tomecate底下的
//		if(!ServletActionContext.getRequest().equals("")){
//			
//			if(upload!=null){//有上传文件时所走分支
//				if(upload.size()==1){//只上传一个文件时，判断是哪个域上传的，然后赋值
//					img=upload.get(0);
//					String data=fileEncoder(img);//编码
//				    if(flag1==true){
//						bgImgData=data;
//						bgImg=uploadFileName.get(0);
//					}else if(flag2==true){
//						btImgData=data;
//						btImg=uploadFileName.get(0);
//					}
//				    File imgFile = new File(path + File.separatorChar + uploadFileName.get(0));//创建文件，文件的路径是默认路径和文件名的综合
//					FileOutputStream fis = new FileOutputStream(imgFile);
//					BASE64Decoder decoder = new BASE64Decoder();
//					fis.write(decoder.decodeBuffer(data));//解码，写入文件
//					fis.flush();
//					fis.close();
//				}else if(upload.size()==2){
//					//多文件上传时，所走的分支
//					bgImg=uploadFileName.get(0);
//					btImg=uploadFileName.get(1);
//					for(int i=0;i<upload.size();i++){
//						img=upload.get(i);
//						String data=fileEncoder(img);
//						if(i==0){
//							bgImgData=data;
//						}
//						if(i==1){
//							btImgData=data;
//						}
//						File imgFile = new File(path + File.separatorChar + uploadFileName.get(i));
//						FileOutputStream fis = new FileOutputStream(imgFile);
//						BASE64Decoder decoder = new BASE64Decoder();
//						fis.write(decoder.decodeBuffer(data));
//						fis.flush();
//						fis.close();
//					}
//					
//				}
//		
//			}
//			upload=null;
//			HttpServletRequest req = ServletActionContext.getRequest();
//			User user = (User) req.getSession().getAttribute(
//					ApplicationConstants.LOGONUSER);
//			 
//			cona.setHeader("B106", user.getUnitid().toString(), user.getUsercode(), "03");
//			cona.set("funcCode", "3");
//			
//			cona.set("themeName",ServletActionContext.getRequest().getParameter("themeName"));
//			cona.set("textFont", ServletActionContext.getRequest().getParameter("textFont"));
//			String textStyle =ServletActionContext.getRequest().getParameter("textStyle");
//			if (textStyle.equals("常规")) {
//				textStyle = "0";
//			} else if (textStyle.equals("粗体")) {
//				textStyle = "1";
//			} else if (textStyle.equals("斜体")) {
//				textStyle = "2";
//			}
//			cona.set("textStyle", textStyle);
//			//ServletActionContext.getRequest().getParameter()从前台获取的数据
//			cona.set("textSize", ServletActionContext.getRequest().getParameter("textSize"));
//			//String color=ServletActionContext.getRequest().getParameter("textColor");
//		
//			cona.set("textColor",newColor);
//			String start= ServletActionContext.getRequest().getParameter("startDate");
//			String end= ServletActionContext.getRequest().getParameter("endDate");
//			start=start.replaceAll("-","");
//			end=end.replaceAll("-","");
//			cona.set("startDate",start);
//			cona.set("endDate", end);
//			cona.set("busSeq", busSeq);//试试能不能从界面上获取stroe的
//			cona.set("bgImg", bgImg);
//			cona.set("btImg",btImg);
//		    cona.set("bgImgData",bgImgData);
//		    cona.set("btImgData",btImgData);
//		    HashMap<String, List<String>> map = (HashMap) cona.exchange();
//		    String info = cona.validMap(map);
//		    if (info != null && info.trim().length() != 0) {
//				throw new AppException(info);
//			} else {
//				
//			}
//			
//		}
//		//目的是为了取消上传成功后弹出下载的提示框
//		 HttpServletResponse response = ServletActionContext.getResponse();  
//		 response.setContentType("text/html;charset=UTF-8"); //确保在firefox中正确显示
//	     String msg = "{success:true}";   
//	     response.getWriter().print(msg);   
//	     
//    	ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
//		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
//		super.setActionresult(returnJson.toString());
//		//return AJAX_SUCCESS;
//		return NONE; 
//	}
//	/**
//	 * 用于文件的编码
//	 * @param file 文件名
//	 * @return 编码后的字符串
//	 * @throws Exception
//	 */
//	public static String fileEncoder(File file) throws Exception{
//		byte[] b=new byte[(int)file.length()];
//
//		FileInputStream fis = new FileInputStream(file);
//		String data="";
//		 while(fis.read(b) > 0){
//	            BASE64Encoder encoder=new BASE64Encoder();
//	            data=data+encoder.encode(b);
//	    } 
//		 fis.close();
//		 return data;
//	}
//	/**
//	 * 判断是哪个字段上传的文件
//	 * 以及获取排队机业务队列顺序
//	 * @throws Exception
//	 */
//	public void isFlag() throws Exception{
//		String inputJsonStr = super.getJsonString();
//		JSONObject obj = JSONObject.fromObject(inputJsonStr);
//		flag1=obj.getBoolean("flag1");
//		flag2=obj.getBoolean("flag2");
//		
//		if(obj.getString("busSeq")!=null&&!obj.getString("busSeq").equals("")){
//			if( obj.getString("busSeq").startsWith(">>")){
//				busSeq = obj.getString("busSeq").replaceFirst(">>", "").trim();
//			}
//		}
//		fontColor=obj.getString("textColor");
//		String r=fontColor.substring(0, 2);
//		String g=fontColor.substring(2,4);
//		String b=fontColor.substring(4,6);
//		int c1=Integer.parseInt(r,16);
//		int c2=Integer.parseInt(g,16);
//		int c3=Integer.parseInt(b,16);
//		newColor=c1+","+c2+","+c3;
//		
//	}
//public void getWidth()throws Exception{
//	String inputJsonStr = super.getJsonString();
//	JSONObject obj = JSONObject.fromObject(inputJsonStr);
//	width=obj.getInt("width");
//	
//}
//	
//	/**
//	 * 通过主题名称查询排队机主题信息
//	 * @return
//	 * @throws Exception
//	 */
//	public String getThemeInfo() throws Exception {
//		String path=ServletActionContext.getRequest().getRealPath("/upload");//保存的默认路径,注意在Tomecate底下的
//		String inputJsonStr = super.getJsonString();
//		JSONObject obj = JSONObject.fromObject(inputJsonStr);
//		HttpServletRequest req = ServletActionContext.getRequest();
//		User user = (User) req.getSession().getAttribute(
//				ApplicationConstants.LOGONUSER);
//		 
//		cona.setHeader("B106", user.getUnitid().toString(), user.getUsercode(), "03");
//		cona.set("funcCode", "2");
//		HashMap<String, List<String>> map = (HashMap) cona.exchange();
//		Map<String, String> hld = new HashMap<String, String>();
//		String info = cona.validMap(map);
//		
//		if (info != null && info.trim().length() != 0) {
//			throw new AppException(info);
//		} else {
//			String loopNum = map.get("loopNum").get(0);
//			int num = Integer.parseInt(loopNum);
//			String themeName1=obj.getString("themeName");
//			for (int i = 0; i < num; i++) {
//				String themeName2=map.get("themeName").get(i);
//				if(themeName1.equals(themeName2)){
//					bgImg=map.get("bgImg").get(i);
//					hld.put("bgImg",bgImg);
//					bgImgData = map.get("bgImgData").get(i);
//					File backPic = new File(path+ File.separatorChar+"back.png");
//					FileOutputStream fis = new FileOutputStream(backPic);
//					BASE64Decoder decoder = new BASE64Decoder();
//					fis.write(decoder.decodeBuffer(bgImgData));
//					fis.flush();
//					fis.close();
//					btImgData= map.get("btImgData").get(i);
//					File BtPic = new File(path+ File.separatorChar+"button.png");
//					FileOutputStream f = new FileOutputStream(BtPic);
//					f.write(decoder.decodeBuffer(btImgData));
//					f.flush();
//					btImg=map.get("btImg").get(i);
//					hld.put("btImg",btImg );
//					hld.put("textFont", map.get("textFont").get(i));
//					fis.close();
//					String textStyle = map.get("textStyle").get(i);
//					if (textStyle.equals("0")) {
//						textStyle = "常规";
//					} else if (textStyle.equals("1")) {
//						textStyle = "粗体";
//					} else if (textStyle.equals("2")) {
//						textStyle = "斜体";
//					}
//					hld.put("textStyle", textStyle);
//					hld.put("textSize", map.get("textSize").get(i));
//					hld.put("textColor", map.get("textColor").get(i));
//					String start = map.get("startDate").get(i);
//					if (start.trim().length() > 0) {
//						start = start.substring(0, 4) + "-" + start.substring(4, 6) + "-"
//								+ start.substring(6, 8);
//					}
//					hld.put("startDate", start);
//					String end = map.get("endDate").get(i);
//					if (end.trim().length() > 0) {
//						end = end.substring(0, 4) + "-" + end.substring(4, 6) + "-"
//								+ end.substring(6, 8);
//					}
//	
//					hld.put("endDate", end);
//				    busSeq=map.get("busSeq").get(i);
//				}
//			}
//		}
//		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
//		sRet.put(ServiceReturn.FIELD1, hld);
//		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
//		super.setActionresult(returnJson.toString());
//		return AJAX_SUCCESS;
//	}
//
	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
}		
		
		
