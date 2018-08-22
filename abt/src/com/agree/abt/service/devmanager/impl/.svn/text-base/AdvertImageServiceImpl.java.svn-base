package com.agree.abt.service.devmanager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.ContextLoader;

import com.agree.abt.model.devmanager.AdvertManageInfo;
import com.agree.abt.service.devmanager.IAdvertImageService;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.base.BaseService;
import com.agree.util.IDictABT;

public class AdvertImageServiceImpl extends BaseService implements IAdvertImageService {

	//private static final Logger logger = LoggerFactory.getLogger(AdvertImageServiceImpl.class);
	
	/*public ServiceReturn addImg(JSONObject json, File file) throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		json.accumulate("contentType", "2");
		AdvertManageInfo advertImage = (AdvertManageInfo)JSONObject.toBean(json, AdvertManageInfo.class);
		FileImageInputStream fiis = null;
		ByteArrayOutputStream bos = null;
		try {
			fiis = new FileImageInputStream(file);
			byte[] b = new byte[1024];
			bos = new ByteArrayOutputStream();
			int n;
			while ((n = fiis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
//			advertImage.setContent(new Base64().encode(bos.toByteArray()));
			advertImage.setContent( bos.toByteArray());
			sqlDao_h.saveRecord(advertImage);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			sRet.setErrmsg("添加设备图片错误,service addImg方法执行失败!");
			sRet.setSuccess(ServiceReturn.FAILURE);
		} finally {
			try {
				if(bos!=null){
					bos.close();
				}
				if(fiis!=null){
					fiis.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return sRet;
	}*/
	private ABTComunicateNatp cona;

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}

	public ServiceReturn delImg(JSONArray objS) throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		//获得项目路径
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		String error = "";
		for (int i = 0;i<objS.size();i++) {
			JSONObject objectI = JSONObject.fromObject(objS.get(i));
			String content_id = objectI.getString("content_id");
			String imgPathS = objectI.getString("content");
			String[] imgPathArray = imgPathS.split(";");
			for(int x = 0; x < imgPathArray.length; x++){
				int imgIndexOf = imgPathArray[x].indexOf("abt/") + 4;
				String imgPath = path + imgPathArray[x].substring(imgIndexOf);
				imgPath = imgPath.replaceAll(Matcher.quoteReplacement("/"), Matcher.quoteReplacement(File.separator));
				imgPath = imgPath.replaceAll(Matcher.quoteReplacement("\\"), Matcher.quoteReplacement(File.separator));
				File fileFace = new File(imgPath);
				fileFace.delete();
			}
			try {
				sqlDao_h.deleteById(AdvertManageInfo.class, content_id);
			} catch (Exception e) {
				sRet.setSuccess(ServiceReturn.FAILURE);
				error += "|编号为:"+content_id+"的图片删除失败|";
			}
		}
		sRet.setErrmsg(error);
		return sRet;
	}
	
	// 获取图片byte数组(该功能已暂停使用)
	/*public byte[] getImgByte(String content) {
		AdvertManageInfo advertImage = sqlDao_h.getRecordById(AdvertManageInfo.class, content);
		return new Base64().decode(advertImage.getContent());
		//return null;
	}*/

	public long queryImgNumByBranch(Map<String, String> map) throws Exception {
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put(IDictABT.BRANCH, map.get(IDictABT.BRANCH));
		queryMap.put("device_type", map.get("device_type"));
		return sqlDao_h.getRecordCount("select count(branch) from AdvertManageInfo where branch = :branch and device_type = :device_type", queryMap);
	}

	/**
	 * 保存广告信息
	 */
	public ServiceReturn addAdvertisingPicture(String obj,User user) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject object = JSONObject.fromObject(obj);
		//过得项目根目录
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		
		String content = object.getString("content");
		
		String hql = "select count(*)  from AdvertManageInfo where branch='"+object.getString("branch")+"'"+ " and device_type="+ "'"+ object.getString("device_type")+ "'";
		Long count = sqlDao_h.getRecordCount(hql);
		if(count >=10){
			throw new AppException("一个机构下，设备广告图片不能多于10张");
		}
		
		//根据不同设备类型，将广告图片放到不同的目录
        String sonFileName = "";
        String deviceType = object.getString("device_type");
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
		
		
		String[] contentArry = content.split(";");
		for(int i = 0;i<contentArry.length;i++){
			int pathIndexOf =  contentArry[i].indexOf("adUpload/")+9;
			String startPath = contentArry[i].substring(0, pathIndexOf) + "AdvertisingPicture" + File.separator;
			String endPath = contentArry[i].substring(pathIndexOf, contentArry[i].length());
			//图片路径
			String newContent = startPath+ sonFileName+ "/"+ endPath;
			newContent = newContent.replaceAll(Matcher.quoteReplacement("/"), Matcher.quoteReplacement(File.separator));
			newContent = newContent.replaceAll(Matcher.quoteReplacement("\\"), Matcher.quoteReplacement(File.separator));
			
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b213_1.01", user);
			//机构号
			cona.set("branch", user.getUnitid());
			//广告文件类型（图片）
			cona.set("content_type","1");
			//广告图片访问地址
			cona.set("content", newContent);
			//图片md5码
			//拼接图片文件地址
			int indexOfPath = newContent.indexOf("abt" + File.separator) + 4;
			String replaceAll = newContent.substring(indexOfPath, newContent.length());
			String filePath = path + File.separator + replaceAll;
			filePath = filePath.replaceAll(Matcher.quoteReplacement("/"), Matcher.quoteReplacement(File.separator));
			filePath = filePath.replaceAll(Matcher.quoteReplacement("\\"), Matcher.quoteReplacement(File.separator));
			//获取图片md5码
			FileInputStream fis = new FileInputStream(filePath);
			String pictureMd5Code = DigestUtils.md5DigestAsHex(IOUtils.toByteArray(fis));
			//存入数据库
			cona.set("picturemd5code", pictureMd5Code);
			
			//添加人
			cona.set("creator", user.getUsercode());
			//添加时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd/hh:mm:ss");
			cona.set("create_date", sdf.format(new Date()));
			//图片id
			cona.set("content_id", UUID.randomUUID().toString().substring(0, 20));
			//图片备注
			cona.set("explain", object.getString("explain"));
			//设备类型
			cona.set("device_type", object.getString("device_type"));
			cona.exchange();
			
		}
		
		return ret;
	}

}
