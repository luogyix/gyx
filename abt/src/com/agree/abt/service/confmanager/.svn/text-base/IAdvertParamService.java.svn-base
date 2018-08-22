package com.agree.abt.service.confmanager;

import java.io.File;
import java.util.List;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

public interface IAdvertParamService {
	
	/**
	 * 查询广告参数
	 * @param user
	 * @param pageinfo
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAdvertParam(User user,Page pageinfo) throws Exception;
	
	/**
	 * 添加新广告
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn addAdvertParam(String json) throws Exception;
	
	/**
	 * 修改广告
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn editAdvertParam(String json) throws Exception;
	
	/**
	 * 删除广告信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn deleteAdvertParam(String json) throws Exception ;
	/**
	 * 上传文件
	 * @param fileName 文件名
	 * @param upLoadFile 上传文件
	 * @return
	 * @throws Exception
	 */
	public String upLoadFile(String fileName,File upLoadFile)throws Exception;	

}
