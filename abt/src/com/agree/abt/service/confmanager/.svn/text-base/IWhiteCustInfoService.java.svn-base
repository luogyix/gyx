package com.agree.abt.service.confmanager;

import java.util.List;

import net.sf.json.JSONObject;

import com.agree.abt.model.confManager.WhiteCustInfo;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;

public interface IWhiteCustInfoService {
	/**
	 * 查询客户信息
	 * @param req
	 * @param page
	 * @throws Exception
	 */
	public List<WhiteCustInfo> queryWhiteCustInfo(JSONObject json, Page page) throws Exception;
	/**
	 * 导入客户信息
	 * @param jsonString
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn importWhiteCustInfo(String jsonString) throws Exception;
	/**
	 * 删除白名单客户信息
	 * @param jsonString
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn delWhiteCustInfo(String jsonString) throws Exception;
	/**
	 * 增加白名单客户信息
	 */
	public ServiceReturn addWhiteCustInfo(String jsonString) throws Exception;
	/**
	 * 修改白名单客户信息
	 */
	public ServiceReturn editWhiteCustInfo(String jsonString) throws Exception;
}