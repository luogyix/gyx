package com.agree.abt.service.dataAnalysis;

import java.io.File;
import java.util.List;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

public interface IVoucherManagerService {
	
	/**
	 * 查询凭证信息
	 * @param user
	 * @param pageinfo
	 * @return
	 * @throws Exception
	 */
	public List<Object> getVoucherInfo(User user,Page pageinfo) throws Exception;
	
	/**
	 * 添加新凭证
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn addVoucherInfo(String json) throws Exception;
	
	/**
	 * 修改凭证
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn editVoucherInfo(String json) throws Exception;
	
	/**
	 * 删除凭证信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn deleteVoucherInfo(String json) throws Exception ;
	/**
	 * 上传文件
	 * @param fileName 文件名
	 * @param upLoadFile 上传文件
	 * @return
	 * @throws Exception
	 */
	public String upLoadFile(String fileName,File upLoadFile)throws Exception;

	public ServiceReturn editConcle(String jsonString) throws Exception;

	public ServiceReturn addConcle(String jsonString) throws Exception;	

}
