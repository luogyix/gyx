package com.agree.abt.service.devmanager;
import java.util.Map;

import net.sf.json.JSONArray;

import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

public interface IAdvertImageService {
	
	
	/**
	 * 添加图片
	 * @param image 对象
	 * @return
	 *//*
	public ServiceReturn addImg(JSONObject json, File file) throws Exception;*/
	
	/**
	 * 添加广告
	 * @param 
	 */
	public ServiceReturn addAdvertisingPicture(String obj,User user) throws Exception;
	
	/**
	 * 删除图片
	 * @param ids 
	 * @return
	 */
	public ServiceReturn delImg(JSONArray objS) throws Exception;
	
	/**
	 * 获取图片byte数组(该功能已暂停使用)
	 * @param img_id 图片id
	 * @return
	 */
	//public byte[] getImgByte(String content) throws Exception;

	public long queryImgNumByBranch(Map<String, String> map) throws Exception;

}
