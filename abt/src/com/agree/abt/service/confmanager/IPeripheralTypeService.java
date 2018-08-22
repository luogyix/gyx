package com.agree.abt.service.confmanager;

import java.util.List;
import java.util.Map;

import com.agree.abt.model.confManager.BtDevPeripheralType;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;

/**
 * 设备外设类型接口
 * @classname:IPeripheralTypeService.java
 * @author wangguoqing
 * @company 赞同科技
 * 2015-3-30
 */
@SuppressWarnings({ "rawtypes" })
public interface IPeripheralTypeService {
	/**
	 * 查询外设类型
	 * */
	public List queryPeripheralType(Map<String,String> map, Page pageInfo) throws Exception;
	/**
	 * 添加外设类型信息
	 * */
	public ServiceReturn addPeripheralType(BtDevPeripheralType btDevPeripheralType) throws Exception;
	/**
	 * 修改外设信息
	 * */
	public ServiceReturn editPeripheralType(String jsonString) throws Exception;
	/**
	 * 删除外设类型信息
	 * */
	public ServiceReturn delPeripheralType(List<BtDevPeripheralType> ids) throws Exception;
	

}
