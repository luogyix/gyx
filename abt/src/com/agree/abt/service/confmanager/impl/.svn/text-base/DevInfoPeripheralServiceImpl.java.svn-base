package com.agree.abt.service.confmanager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agree.abt.model.confManager.BtDevDevicePeripheralState;
import com.agree.abt.service.confmanager.IDevInfoPeripheralService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.service.base.BaseService;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DevInfoPeripheralServiceImpl extends BaseService implements IDevInfoPeripheralService{
	public List queryDevPeripheral(String branch,String devicetype,String device_num, Page pageInfo) throws Exception{
		String hql = "from BtDevDevicePeripheralState where branch =:branch and devicetype =:devicetype and device_num =:device_num";
		Map<String, String> map = new HashMap<String, String>();
		map.put("branch", branch);
		map.put("devicetype", devicetype);
		map.put("device_num", device_num);
		List<?> list = sqlDao_h.queryPage(hql, map, pageInfo, false);
		return (List<BtDevDevicePeripheralState>)list;
	}
}
