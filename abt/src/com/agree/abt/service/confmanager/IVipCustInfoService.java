package com.agree.abt.service.confmanager;

import java.util.List;

import net.sf.json.JSONObject;

import com.agree.abt.model.confManager.VipCustInfo;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;

public interface IVipCustInfoService {
	/**
	 * 查询客户信息
	 * @param req
	 * @param page
	 * @throws Exception
	 */
	public List<VipCustInfo> queryVipCustInfo(JSONObject json, Page page) throws Exception;
	/**
	 * 导入客户信息
	 * @param jsonString
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn importVipCustInfo(String jsonString) throws Exception;
	/**
	 * 导出客户信息
	 * @param jsonString
	 * @return
	 * @throws Exception
	 */
	public List<VipCustInfo> exportVipCustInfo(String jsonString) throws Exception;
	/**
	 * 删除VIP客户信息
	 * @param jsonString
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn delVipCustInfo(String jsonString) throws Exception;
}
