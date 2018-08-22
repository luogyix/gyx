package com.agree.abt.service.confmanager;

import java.util.List;

import net.sf.json.JSONObject;

import com.agree.abt.model.confManager.BtDevParam;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;

/**
 * 设备参数接口
 * @author linyuedong
 */
@SuppressWarnings({ "rawtypes" })
public interface IDevParam {
	/**
	 * 分页查询设备参数
	 * @return
	 * @throws Exception
	 */
	public List queryDevParamPage(Page pageInfo) throws Exception;
	/**
	 * 查询设备参数
	 * @param pageInfo
	 * @return
	 * @throws Exception
	 */
	public List queryDevParam() throws Exception;
	/**
	 * 增加设备参数
	 */
	public ServiceReturn addDevParam(JSONObject obj) throws Exception;
	/**
	 * 修改设备参数
	 */
	public ServiceReturn editDevParam(JSONObject obj) throws Exception;
	/**
	 * 删除设备参数
	 */
	public ServiceReturn delDevParam(List<BtDevParam> list) throws Exception;
}
