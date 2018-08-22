package com.agree.abt.service.confmanager;

import java.util.List;
import java.util.Map;


import com.agree.abt.model.confManager.BtDevPeripheralState;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;

/**
 * 设备外设状态接口
 * @classname:IPeripheralStateService
 * @author wangguoqing
 * @company 赞同科技
 * 2015-4-3
 */
@SuppressWarnings({ "rawtypes" })
public interface IPeripheralStateService {
	/**
	 * 查询外设状态
	 * */
	public List queryPeripheralState(Map<String,String> map, Page pageInfo) throws Exception;
	/**
	 * 添加外设状态信息
	 **/
	public ServiceReturn addPeripheralState(BtDevPeripheralState btDevPeripheralState) throws Exception;
	/**
	 * 修改外设状态信息
	 * */
	public ServiceReturn editPeripheralState(BtDevPeripheralState btDevPeripheralState) throws Exception;
	/**
	 * 删除外设状态信息
	 * */
	public ServiceReturn delPeripheralState(List<BtDevPeripheralState> ids) throws Exception;
	
}
