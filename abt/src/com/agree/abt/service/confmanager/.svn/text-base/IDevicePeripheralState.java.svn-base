/**
 * 
 */
package com.agree.abt.service.confmanager;

import java.util.List;
import net.sf.json.JSONObject;

import com.agree.abt.model.confManager.BtDevDevicePeripheralState;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;

/**
 * @author linyuedong
 * @date 2015-4-9
 */
@SuppressWarnings({ "rawtypes" })
public interface IDevicePeripheralState {
	/**
	 * 
	 * @param device_type
	 * @return
	 * @throws Exception
	 */
	public List queryPeripheralType(String device_type) throws Exception;
	/**
	 * 
	 * @param p_type_key
	 * @return
	 * @throws Exception
	 */
	public List queryPeripheralStatus(String p_type_key) throws Exception;
	/**
	 * 
	 * @param branch
	 * @return
	 * @throws Exception
	 */
	public List queryDevicePeripheralState(String branch,Page pageinfo) throws Exception;
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn addDevicePeripheralState(JSONObject obj) throws Exception;
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn editDevicePeripheralState(JSONObject obj) throws Exception;
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn delDevicePeripheralState(List<BtDevDevicePeripheralState> ids) throws Exception;
	
}
