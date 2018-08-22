package com.agree.abt.service.confmanager;

import java.util.List;

import com.agree.framework.dao.entity.Page;
@SuppressWarnings({ "rawtypes" })
public interface IDevInfoPeripheralService {
	public List queryDevPeripheral(String branch,String devicetype,String device_num, Page pageInfo) throws Exception;
}
