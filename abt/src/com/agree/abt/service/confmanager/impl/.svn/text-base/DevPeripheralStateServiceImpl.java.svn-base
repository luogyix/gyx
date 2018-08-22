/**
 * 
 */
package com.agree.abt.service.confmanager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.transaction.annotation.Transactional;

import com.agree.abt.model.confManager.BtDevDevicePeripheralState;
import com.agree.abt.model.confManager.BtDevPeripheralState;
import com.agree.abt.model.confManager.BtDevPeripheralType;
import com.agree.abt.service.confmanager.IDevicePeripheralState;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.service.base.BaseService;

/**
 * @author linyuedong
 * @date 2015-4-9
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DevPeripheralStateServiceImpl extends BaseService implements IDevicePeripheralState {

	/* (non-Javadoc)
	 * @see com.agree.abt.service.confmanager.IDevicePeripheralState#queryPeripheralType(java.lang.String)
	 */
	@Transactional
	public List queryPeripheralType(String device_type) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("p_type_devicetype", device_type);
		String hql = "from BtDevPeripheralType where p_type_devicetype =:p_type_devicetype";
		List list=  (List) sqlDao_h.getRecordList(hql, map, false);
		return (List<BtDevPeripheralType>)list;
	}

	/* (non-Javadoc)
	 * @see com.agree.abt.service.confmanager.IDevicePeripheralState#queryPeripheralStatus(java.lang.String)
	 */
	@Transactional
	public List queryPeripheralStatus(String p_type_key) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("p_type_key", p_type_key);
		String hql = "select p_state_key from BtDevPeripheralTypeState where p_type_key =:p_type_key";
		String hql1 = "from BtDevPeripheralState where p_state_key =:p_state_key";
		List<String> list= (List<String>) sqlDao_h.getRecordList(hql, map, false); 
		List list1 = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, String> map1 = new HashMap<String, String>();
			map1.put("p_state_key", list.get(i));
			BtDevPeripheralState btDevPeripheralState = (BtDevPeripheralState) sqlDao_h.getRecord(hql1, map1, false);
			list1.add(btDevPeripheralState);
		}
		return (List<BtDevPeripheralState>)list1;
	}

	/* (non-Javadoc)
	 * @see com.agree.abt.service.confmanager.IDevicePeripheralState#queryDevicePeripheralState(java.lang.String)
	 */
	@Transactional
	public List queryDevicePeripheralState(String branch,Page pageinfo) throws Exception {
		String hql = "from BtDevDevicePeripheralState where branch =:branch";
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("branch", branch);
		List<?> list = sqlDao_h.queryPage(hql, map, pageinfo, false);
		return (List<BtDevDevicePeripheralState>)list;
	}

	/* (non-Javadoc)
	 * @see com.agree.abt.service.confmanager.IDevicePeripheralState#addDevicePeripheralState(net.sf.json.JSONObject)
	 */
	@Transactional
	public ServiceReturn addDevicePeripheralState(JSONObject obj)
			throws Exception {
		String peripheral_monitor= null;
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		BtDevDevicePeripheralState btDevDevicePeripheralState = (BtDevDevicePeripheralState) JSONObject.toBean(obj, BtDevDevicePeripheralState.class);
		if(obj.getString("peripheral_monitor").equalsIgnoreCase("on")){
			 peripheral_monitor=obj.getString("peripheral_monitor").replaceAll("on","1");
		}else if(obj.getString("peripheral_monitor").equalsIgnoreCase("off")){
			 peripheral_monitor=obj.getString("peripheral_monitor").replaceAll("off","0");
		}
		btDevDevicePeripheralState.setPeripheral_monitor(peripheral_monitor);
		try {
			sqlDao_h.saveRecord(btDevDevicePeripheralState);
		} catch (Exception e) {
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("添加失败,已有相同设置");
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.agree.abt.service.confmanager.IDevicePeripheralState#editDevicePeripheralState(net.sf.json.JSONObject)
	 */
	@Transactional
	public ServiceReturn editDevicePeripheralState(JSONObject obj)
			throws Exception {
		String peripheral_monitor= null;
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		BtDevDevicePeripheralState btDevDevicePeripheralState = (BtDevDevicePeripheralState) JSONObject.toBean(obj, BtDevDevicePeripheralState.class);
		if(obj.getString("peripheral_monitor").equalsIgnoreCase("on")){
			 peripheral_monitor=obj.getString("peripheral_monitor").replaceAll("on","1");
		}else if(obj.getString("peripheral_monitor").equalsIgnoreCase("off")){
			 peripheral_monitor=obj.getString("peripheral_monitor").replaceAll("off","0");
		}
		btDevDevicePeripheralState.setPeripheral_monitor(peripheral_monitor);
		try {
			sqlDao_h.updateRecord(btDevDevicePeripheralState);
		} catch (Exception e) {
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("修改失败");
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.agree.abt.service.confmanager.IDevicePeripheralState#delDevicePeripheralState(net.sf.json.JSONObject)
	 */
	@Transactional
	public ServiceReturn delDevicePeripheralState(List<BtDevDevicePeripheralState> ids)
			throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		for (BtDevDevicePeripheralState btDev : ids) {
			try {
				sqlDao_h.deleteRecord(btDev);
			} catch (Exception e) {
				ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("删除失败");
			}
		}
		
		return ret;
	}

}
