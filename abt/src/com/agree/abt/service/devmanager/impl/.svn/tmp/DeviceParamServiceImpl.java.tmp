package com.agree.abt.service.devmanager.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.abt.model.devmanager.DeviceParam;
import com.agree.abt.service.devmanager.IDeviceParamService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.service.base.BaseService;
@SuppressWarnings({ "rawtypes" })
public class DeviceParamServiceImpl extends BaseService implements
		IDeviceParamService {
	private static final Logger logger = LoggerFactory.getLogger(DeviceParamServiceImpl.class);
	public List queryParamList(Map<String,String> map, Page pageInfo) throws Exception {
		if((!map.containsKey("branch"))||
				(!map.containsKey("channel_type"))||
				(!map.containsKey("param_type"))){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("from DeviceParam where 1=1");
		HashMap<String,String> paraMap = new HashMap<String,String>();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String column = it.next();
			if("branch".equals(column)){
				paraMap.put(column, "%" + map.get(column) + "%");
				sb.append(" and branch in ( select unitid  from Unit where unitlist like :branch)");
			}else{
				paraMap.put(column,map.get(column));
				sb.append("and " + column + "=:" + column);
			}
		}
		if (pageInfo==null) {
			return sqlDao_h.getRecordList(sb.toString(), paraMap, false);
		} else {
			return sqlDao_h.queryPage(sb.toString(),paraMap,pageInfo,false);
		}
	}
	
	public ServiceReturn addParam(DeviceParam param) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		if("1".equals(param.getDefault_flag())){
			String hql = "select count(*) from DeviceParam where branch = :branch and default_flag='1' and channel_type=:channel_type and param_type=:param_type";
			Map<String,String> map = new HashMap<String, String>();
			map.put("branch", param.getBranch());
			map.put("param_type", param.getParam_type());
			map.put("channel_type", param.getChannel_type());
			Long count = sqlDao_h.getRecordCount(hql, map, false);
			if(count>0){
				ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("添加失败,本机构已存在默认数据");
			}
		}
		if("".equals(param.getDev_param_id())||param.getDev_param_id()==null){
			String hql = "select max(dev_param_id) from DeviceParam where branch = :branch and channel_type=:channel_type and param_type=:param_type";
			Map<String,String> map = new HashMap<String, String>();
			map.put("branch", param.getBranch());
			map.put("param_type", param.getParam_type());
			map.put("channel_type", param.getChannel_type());
			//添加主键ID
			String dev_param_id = (String)sqlDao_h.getRecord(hql, map, false);
			if("".equals(dev_param_id)||dev_param_id==null){
				dev_param_id = param.getBranch() + "00000001";
			}else{
				dev_param_id = String.valueOf(Long.parseLong(dev_param_id)+1);
			}
			param.setDev_param_id(dev_param_id);
		}
		sqlDao_h.saveRecord(param);
		return ret;
	}

	public ServiceReturn editParam(DeviceParam param) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		if("1".equals(param.getDefault_flag())){
			String hql = "select count(*) from DeviceParam where branch = :branch and default_flag='1' and channel_type=:channel_type and param_type=:param_type and dev_param_id != :dev_param_id";
			Map<String,String> map = new HashMap<String, String>();
			map.put("branch", param.getBranch());
			map.put("param_type", param.getParam_type());
			map.put("channel_type", param.getChannel_type());
			map.put("dev_param_id", param.getDev_param_id());
			Long count = sqlDao_h.getRecordCount(hql, map);
			if(count>0){
				ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("修改失败,本机构已存在默认数据");
			}
		}
		sqlDao_h.updateRecord(param);
		return ret;
	}

	public boolean delParam(String dev_param_id) throws Exception {
		try {
			sqlDao_h.deleteById(DeviceParam.class, dev_param_id);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return false;
		}
		return true;
	}
}
