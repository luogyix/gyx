package com.agree.abt.service.confmanager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.transaction.annotation.Transactional;

import com.agree.abt.model.confManager.BtDevParam;
import com.agree.abt.model.confManager.DevParamData;
import com.agree.abt.service.confmanager.IDevParam;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.service.base.BaseService;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DevParamServiceImpl extends BaseService implements IDevParam {
	@Transactional
	public List queryDevParamPage(Page pageInfo) throws Exception {
		String hql = "from BtDevParam";
		HashMap<String, String> map = new HashMap<String, String>();
		List<Object> list = sqlDao_h.queryPage(hql, map, pageInfo, false);
		List<BtDevParam> list1 = new ArrayList<BtDevParam>();
		for (int i = 0; i < list.size(); i++) {
			BtDevParam btDevParam = (BtDevParam) list.get(i);
			JSONObject obj = JSONObject.fromObject(btDevParam.getDev_param_data());
			BtDevParam btDevParam1 = (BtDevParam)JSONObject.toBean(obj,BtDevParam.class);
			if("".equals(btDevParam1.getParameter_id())){
				continue;
			}
			btDevParam.setParameter_id(btDevParam1.getParameter_id());
			list1.add(btDevParam);
		}
		return list1;
	}
	@Transactional
	public List queryDevParam() throws Exception {
		String hql = "from BtDevParam";
		HashMap<String, String> map = new HashMap<String, String>();
		List<Object> list = sqlDao_h.getRecordList(hql, map, false);
		List<BtDevParam> list1 = new ArrayList<BtDevParam>();
		for (int i = 0; i < list.size(); i++) {
			BtDevParam btDevParam = (BtDevParam) list.get(i);
			JSONObject obj = JSONObject.fromObject(btDevParam.getDev_param_data());
			BtDevParam btDevParam1 = (BtDevParam)JSONObject.toBean(obj,BtDevParam.class);
			if("".equals(btDevParam1.getParameter_id())){
				continue;
			}
			btDevParam.setParameter_id(btDevParam1.getParameter_id());
			list1.add(btDevParam);
		}
		return list1;
	}

	@SuppressWarnings("null")
	@Transactional
	public ServiceReturn addDevParam(JSONObject obj) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		BtDevParam btDevParam = (BtDevParam)JSONObject.toBean(obj,BtDevParam.class);
		DevParamData dev = (DevParamData)JSONObject.toBean(obj,DevParamData.class);
		JSONObject devobj = JSONObject.fromObject(dev);
		String branch = obj.getString("branch");
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("dev_param_id", branch);
		String hql = "select max(dev_param_id) from BtDevParam where dev_param_id like :dev_param_id";
		List<Object> list = sqlDao_h.getRecordList(hql, map, false);
		String dev_param_id = branch+"01";
		if (list == null) {
			dev_param_id = String.valueOf(Integer.valueOf(list.get(0).toString())+1);
		}
		btDevParam.setDev_param_id(dev_param_id);
		btDevParam.setDev_param_data(devobj.toString());
		try {
			sqlDao_h.saveRecord(btDevParam);
		} catch (Exception e) {
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("增加失败，已存在相同参数配置");
		}
		return ret;
	}
	@Transactional
	public ServiceReturn editDevParam(JSONObject obj) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		BtDevParam btDevParam = (BtDevParam)JSONObject.toBean(obj,BtDevParam.class);
		DevParamData dev = (DevParamData)JSONObject.toBean(obj,DevParamData.class);
		JSONObject devobj = JSONObject.fromObject(dev);
		btDevParam.setDev_param_data(devobj.toString());
		try {
			sqlDao_h.updateRecord(btDevParam);
		} catch (Exception e) {
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("修改失败，已存在相同参数配置");
		}
		return ret;
	}
	@Transactional
	public ServiceReturn delDevParam(List<BtDevParam> list) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		for(BtDevParam bt : list){
			HashMap<String, String> map = new HashMap<String, String>();
			String hql = "delete from BtDevParam where dev_param_id =:dev_param_id";
			map.put("dev_param_id",bt.getDev_param_id());
			try {
				sqlDao_h.excuteHql(hql, map);
			} catch (Exception e) {
				ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("删除失败");
			}
		}
		
		
		return ret;
	}
	


}
