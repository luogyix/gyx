package com.agree.abt.service.devmanager;

import java.util.Map;

import com.agree.abt.model.devmanager.DeviceInfo;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
@SuppressWarnings({ "rawtypes" })
public interface IDeviceInfoService {
	
	public ServiceReturn queryDevicePage(Map map, Page pageinfo) throws Exception;

	public ServiceReturn addDevice(DeviceInfo deviceInfo) throws Exception;

	public ServiceReturn editDevice(DeviceInfo deviceInfo) throws Exception;

	public ServiceReturn delDevice(DeviceInfo deviceInfo) throws Exception;
}
