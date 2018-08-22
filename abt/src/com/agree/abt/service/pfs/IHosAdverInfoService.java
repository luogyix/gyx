package com.agree.abt.service.pfs;

import java.io.File;
import java.util.List;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

public interface IHosAdverInfoService 
{
	/**
	 * 查询手持广告信息
	 * @param user
	 * @param pageinfo
	 * @return
	 * @throws Exception
	 */
	public List<Object> queryHosAdvertParam(User user,Page pageinfo) throws Exception;
	
	/**
	 * 手持广告添加信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn addHosAdvertParam(String json) throws Exception;
	
	/**
	 * 修改手持广告信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn editHosAdvertParam(String json) throws Exception;
	
	/**
	 * 删除手持广告信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn deleteHosAdvertParam(String json) throws Exception ;
	/**
	 * 上传文件
	 * @param fileName 文件名
	 * @param upLoadFile 上传文件
	 * @return
	 * @throws Exception
	 */
	public String upLoadFile(String fileName,File upLoadFile)throws Exception;	

}
