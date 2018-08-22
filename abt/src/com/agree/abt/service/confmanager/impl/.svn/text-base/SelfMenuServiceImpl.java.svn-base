package com.agree.abt.service.confmanager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.springframework.transaction.annotation.Transactional;

import com.agree.abt.model.confManager.BtSysInterface;
import com.agree.abt.model.confManager.BtTsfMetadata;
import com.agree.abt.service.confmanager.ISelfMenuService;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.service.base.BaseService;
/**
 * 自助设备菜单管理实现类
 * @author hujuqiang
 * @date 2016-8-23
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SelfMenuServiceImpl extends BaseService implements ISelfMenuService {

	
	/**
	 * 查询所有元数据菜单，去掉重复的菜单
	 */
	public ServiceReturn queryAllMetadataMenuToList(String device_type) throws Exception{
		HashMap<String,String> paramaHashMap = new HashMap<String,String>();
		String hql="from BtTsfMetadata where device_type=:device_type";
		paramaHashMap.put("device_type", device_type);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		List<BtTsfMetadata> list = sqlDao_h.getRecordList(hql,paramaHashMap,false);
		sRet.put(ServiceReturn.FIELD1, list);
		return sRet;
	}
	
	@Transactional
	public List<BtSysInterface> queryAllNodeToList(String interface_id)
			throws Exception {
		
		String hql="from BtSysInterface where interface_id=:interface_id order by node_level,node_location asc";
		HashMap<String,String> map = new HashMap<String, String>();
		
		map.put("interface_id",interface_id);
		List<BtSysInterface> list = null;
	    list=sqlDao_h.getRecordList(hql,map,false);
	    BtSysInterface sysInterface=new BtSysInterface();
	    //设置根节点
	    sysInterface.setNode_id("root");
	    sysInterface.setParent_id("-1");
	    sysInterface.setNode_name_ch("设备菜单");
	    sysInterface.setNode_type("0");
	    sysInterface.setNode_level("1");
	    sysInterface.setNode_location("1");
	    sysInterface.setChild_id("");
	    sysInterface.setMetadata_id("");
	  	list.add(0,sysInterface);
		return list;
	}
	
	public List queryMetadataByInterfaceId(String interface_id) throws Exception{
		List<BtTsfMetadata> list=new ArrayList<BtTsfMetadata>();
		HashMap<String,String> paramaHashMap = new HashMap<String,String>();
		String hql="from BtTsfMetadata where interface_id=:interface_id";
		paramaHashMap.put("interface_id", interface_id);
		list=sqlDao_h.getRecordList(hql,paramaHashMap,false);
		return list;
	}
	
	/**
	 * 根据metadata_id查询元数据
	 * @return
	 * @throws Exception
	 */
	
	public List<BtTsfMetadata> queryMetadataById(JSONObject obj) throws Exception{
		String metadata_id = obj.getString("metadata_id");
		List<BtTsfMetadata> list=new ArrayList<BtTsfMetadata>();
		HashMap<String,String> paramaHashMap = new HashMap<String,String>();
		String hql="from BtTsfMetadata where metadata_id=:metadata_id";
		paramaHashMap.put("metadata_id", metadata_id);
		list=sqlDao_h.getRecordList(hql,paramaHashMap,false);
		return list;
	}
	
	/**
	 * 添加自助菜单（目录和业务）</br>
	 * 			最后修改日期：高艺祥-2017-01-16
	 */
	@Transactional
	public ServiceReturn addMenu(JSONObject obj) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		BtSysInterface sysInterface = (BtSysInterface) JSONObject.toBean(obj, BtSysInterface.class);
		BtTsfMetadata menuMetadata = (BtTsfMetadata) JSONObject.toBean(obj, BtTsfMetadata.class);
		
		//获得现最大metadata_id
		int maxId = (Integer) sqlDao_h.getRecord("select  max(cast(METADATA_ID as int)) from BtTsfMetadata");
		//为要添加的数据设置id为数据库中最大Id加1
		Long metadataId = (long) maxId+1;
		sysInterface.setMetadata_id(metadataId.toString());
		menuMetadata.setMetadata_id(metadataId.toString());
		
		String node_id=UUID.randomUUID().toString().substring(0, 8);
		sysInterface.setNode_id(node_id);
		HashMap<String, String> map = new HashMap<String, String>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		map.put("menu_code", menuMetadata.getMenu_code());
		map.put("interface_id", menuMetadata.getInterface_id());
		Long count=sqlDao_h.getRecordCount("select count(*) from BtTsfMetadata where menu_code=:menu_code and  interface_id=:interface_id",map);
		
		
		map1.put("interface_id", menuMetadata.getInterface_id());
		map1.put("menu_name", menuMetadata.getMenu_name());
		String hql="select count(*) from BtTsfMetadata where menu_name=:menu_name and  interface_id=:interface_id";
		ret = this.queryIsSameName(hql, map1);
		if(!ret.getSuccess()){
			return ret;
		}
		if(count>0){
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("该菜单码已存在！请删除后再添加");
			return ret;
		}else{
			sqlDao_h.saveRecord(menuMetadata);
			sysInterface.setMetadata_id(menuMetadata.getMetadata_id());
			sqlDao_h.saveRecord(sysInterface);
		}
		
		return ret;
	}
	
	public ServiceReturn queryIsSameName(String hql,HashMap<String, String> map) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		Long count = sqlDao_h.getRecordCount(hql, map);
		if(count>0){
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("该菜单名称已存在！请删除后再添加");
			return ret;
		}
		return ret;
	}
	
	 /**
     * 根据node_id删除节点及节点菜单
     * @throws Exception
     */
	@Transactional
	public ServiceReturn deleteMenuLeafById(JSONObject obj) throws Exception { 
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String node_id=obj.getString("node_id");
		String metadata_id=obj.getString("metadata_id");
		String[] param = {metadata_id,node_id};
		String[] param1 = {metadata_id};
		try {
			sqlDao_h.excuteHql("delete from BtSysInterface where metadata_id = ? and node_id= ?", param);
			sqlDao_h.excuteHql("delete from BtTsfMetadata where metadata_id = ?", param1);
		} catch (Exception e) {
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("删除失败");
			return ret;
		}
		return ret;
	}
	
	/**
     * 修改菜单
     * @return
     * @throws Exception
     */
	@Transactional
	public ServiceReturn editMenuLeaf(JSONObject obj) throws Exception {
		/*
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String metadata_id=obj.getString("metadata_id");
		String metadatamenu=obj.getString("metadatamenu");
		ret=this.deleteMenuLeafById(obj);
		ret=this.addMenu(obj);
		*/
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		BtSysInterface sysInterface = (BtSysInterface) JSONObject.toBean(obj, BtSysInterface.class);
		BtTsfMetadata menuMetadata = (BtTsfMetadata) JSONObject.toBean(obj, BtTsfMetadata.class);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("menu_code", menuMetadata.getMenu_code());
		map.put("interface_id", menuMetadata.getInterface_id());
		Long count=sqlDao_h.getRecordCount("select count(*) from BtTsfMetadata where menu_code=:menu_code and  interface_id=:interface_id",map);
		
		if(!ret.getSuccess()){
			return ret;
		}
		if(count>1){
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("修改菜单失败！同一界面不能有相同菜单码！");
			return ret;
		}else{
			sqlDao_h.updateRecord(menuMetadata);
			sqlDao_h.updateRecord(sysInterface);
		}
		
		return ret;
	}
	
	
	
	@Transactional
	public ServiceReturn saveSysInterface(BtSysInterface sysinterface) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		try {
			sqlDao_h.saveRecord(sysinterface);
		} catch (Exception e) {
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("保存失败");
			return ret;
		}
		return ret;
	}
	
	public ServiceReturn saveMetadata(BtTsfMetadata metadata) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		try {
			String max =  (String) sqlDao_h.getRecord("select max(metadata_id) from BtTsfMetadata");
			String metadataId = String.valueOf(Long.parseLong(max)+1);
			metadata.setMetadata_id(String.valueOf(metadataId));
			sqlDao_h.saveRecord(metadata);
			String metadata_id=metadata.getMetadata_id();
			ret.put("metadata_id", metadata_id);
		} catch (Exception e) {
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("保存失败");
			return ret;
		}
		return ret;
	}
	
	@Transactional
	public ServiceReturn saveMetadataList(List<BtTsfMetadata> list) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		for(BtTsfMetadata Metadata:list){
			try {
				Metadata.setMetadata_id("");
				sqlDao_h.saveRecord(Metadata);
				Metadata.getMetadata_id();
				
			} catch (Exception e) {
				ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("赋值失败");
				return ret;
			}
		}
		
		return ret;
	}

}
