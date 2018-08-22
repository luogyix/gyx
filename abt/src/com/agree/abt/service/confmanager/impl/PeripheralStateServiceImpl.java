package com.agree.abt.service.confmanager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.agree.abt.model.confManager.BtDevPeripheralState;
import com.agree.abt.model.confManager.BtDevPeripheralTypeState;
import com.agree.abt.service.confmanager.IPeripheralStateService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.service.base.BaseService;

/**
 * 设备外设状态接口实现
 * @classname:
 * @author wangguoqing
 * @company 赞同科技
 * 2015-4-3
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PeripheralStateServiceImpl extends BaseService implements IPeripheralStateService{
	private static final Logger logger = LoggerFactory.getLogger(PeripheralStateServiceImpl.class);
	/**
	 * 查询外设状态信息
	 **/
	@Transactional
	public List queryPeripheralState(Map<String,String> map, Page pageInfo) throws Exception{
//		HashMap<String,String> paramaHashMap = new HashMap<String,String>();
		String hql = "select new map(p_state_key,p_state_value) from BtDevPeripheralState";
		List list = sqlDao_h.queryPage(hql,null,pageInfo,false);
		for (int i = 0; i < list.size(); i++) {
			Map tempMap = (Map)list.get(i);
			String typehql = "select p_type_key from BtDevPeripheralTypeState where p_state_key = '" + tempMap.get("0") + "'";
			List typeList = sqlDao_h.getRecordList(typehql);
			String typeStr  = "";
			for(int j = 0 ; j < typeList.size(); j++)
			{
				typeStr += typeList.get(j).toString() + ",";
			}
			if(typeStr.length()>0){
				tempMap.put("p_type_key", typeStr.substring(0, typeStr.length()-1));
			}else{
				tempMap.put("p_type_key","");
			}
			tempMap.put("p_state_key", tempMap.get("0"));
			tempMap.put("p_state_value", tempMap.get("1"));
		}
		
		//String typesql = "select p_state_key, replace(wm_concat(p_type_key), ',', ';') p_type_key from BtDevPeripheralTypeState group by p_State_key";
		
		//+" where BtDevPeripheralTypeState.p_state_key = BtDevPeripheralState.p_state_key";
		//String str = sqlDao_h.queryPage();
//		List list2 = new ArrayList();
//		for (Object obj : list) {
//			Map map2 = (Map)obj;
//			BtDevPeripheralState devps = new BtDevPeripheralState();
//			devps.setP_state_key(map2.get("0").toString());
//			devps.setP_state_value(map2.get("1").toString());
//			devps.setP_type_key(type);
//			list2.add(devps);
//		}
		return list;
		
	}
	/**
	 * 添加外设状态信息
	 * */
	@Transactional
	public ServiceReturn addPeripheralState(BtDevPeripheralState btDevPeripheralState) throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		Map<String,String> map = new HashMap<String,String>();
		map.put("p_state_key",btDevPeripheralState.getP_state_key());
		Long count = sqlDao_h.getRecordCount("select count(*) from BtDevPeripheralState where p_state_key=:p_state_key",map);
		if(count > 0){
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("该外设状态ID已存在！请删除后再添加");
		}else{//添加
			
			sqlDao_h.saveRecord(btDevPeripheralState);
			
			String tStr = btDevPeripheralState.getP_type_key();
			String[] typeStr = tStr.split(";");
			for(int i=0;i<typeStr.length;i++){
				BtDevPeripheralTypeState btDevPeripheralTypeState = new BtDevPeripheralTypeState();
				btDevPeripheralTypeState.setP_state_key(btDevPeripheralState.getP_state_key());
				btDevPeripheralTypeState.setP_type_key(typeStr[i]);
				sqlDao_h.saveRecord(btDevPeripheralTypeState);
			}
		}
		return ret;
	}
	/**
	 * 修改外设状态信息
	 * */
	@Transactional
	public ServiceReturn editPeripheralState(BtDevPeripheralState btDevPeripheralState) throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String typeStr = btDevPeripheralState.getP_type_key();
		String[] typeArr = typeStr.split(";");
		try{
//			String[] param = {btDevPeripheralState.getP_state_value(),btDevPeripheralState.getP_state_key()} ;
//			String sql = "update BT_DEV_PERIPHERALSTATE set P_STATE_VALUE = ? where P_STATE_KEY = ?";
//			sqlDao_h.excuteSql(sql, param);
			//map.put("p_state_key",btDevPeripheralState.getP_state_key());
			//先删除关联表中相关的数据
			Object[] param = {btDevPeripheralState.getP_state_key()};
			sqlDao_h.excuteHql("delete from BtDevPeripheralTypeState where p_state_key = ?", param);
			
			sqlDao_h.updateRecord(btDevPeripheralState);
			
			for(int i=0; i<typeArr.length;i++){
				BtDevPeripheralTypeState btDevPeripheralTypeState = new BtDevPeripheralTypeState();
				btDevPeripheralTypeState.setP_state_key(btDevPeripheralState.getP_state_key());
				btDevPeripheralTypeState.setP_type_key(typeArr[i]);
				sqlDao_h.saveRecord(btDevPeripheralTypeState);
//				String[] paramlink = {typeArr[i],btDevPeripheralState.getP_state_key()};
//			    String sqllink = "update BT_DEV_PERIPHERALTYPESTATE set P_TYPE_KEY = ? where P_STATE_KEY = ?";
//			    sqlDao_h.excuteSql(sqllink, paramlink);
			}
		}catch(Exception e){
			 ret = new ServiceReturn(ServiceReturn.SUCCESS, "外设状态["+btDevPeripheralState.getP_state_value()+"]已存在,请重新输入");
		}
		return ret;
	}
	/**
	 * 删除外设状态信息
	 * */
	@Transactional
	public ServiceReturn delPeripheralState(List<BtDevPeripheralState> ids) throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		for (BtDevPeripheralState devps :ids){
			//删除
			//Object[] paramTypeState = {devps.getP_type_key(),devps.getP_state_key()};
			Object[] param = {devps.getP_state_key()};
//			String typeStr = devps.getP_type_key();
//			String[] typeArr = typeStr.split(",");
			try {
//				for(int i=0;i<typeArr.length;i++){
//					Object[] paramTypeState = {typeArr[i],devps.getP_state_key()};
//					sqlDao_h.excuteHql("delete from BtDevPeripheralTypeState where p_type_key = ? and p_state_key = ?",paramTypeState);
//				}
				Map<String,String> map = new HashMap<String,String>();
				map.put("p_state_key",devps.getP_state_key());
				Long count = sqlDao_h.getRecordCount("select count(*) from BtDevDevicePeripheralState where peripheral_state=:p_state_key",map);
				if(count > 0){
					ret.setSuccess(false);
					ret.setOperaterResult(false);
					ret.setErrmsg("该外设状态ID正在使用，无法删除！");
				}else{
				sqlDao_h.excuteHql("delete from BtDevPeripheralTypeState where p_state_key = ?", param);
				sqlDao_h.excuteHql("delete from BtDevPeripheralState where p_state_key = ?", param);
				}
			} catch (Exception e) {
				ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("外设状态删除失败,外设状态ID:" + devps.getP_state_key());
				logger.error(e.getMessage(),e);
				return ret;
			}
		}
		return ret;
	}
	
}
