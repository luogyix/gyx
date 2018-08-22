package com.agree.abt.service.confmanager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.transaction.annotation.Transactional;

import com.agree.abt.model.confManager.BtDevDeviceMenu;
import com.agree.abt.model.confManager.MenuParam;
import com.agree.abt.service.confmanager.ISelfDevMenuService;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.service.base.BaseService;
/**
 * 自助设备菜单管理实现类
 * @author linyuedong
 * @date 2015-4-2
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SelfDevMenuServiceImpl extends BaseService implements ISelfDevMenuService {
	@Transactional
	public List querySelfDevMenu(String inputString) throws Exception {
		List<BtDevDeviceMenu> list = null;
		HashMap<String,String> paramaHashMap = new HashMap<String,String>();
		BtDevDeviceMenu btDevDeviceMenu = new BtDevDeviceMenu();
		String hql ="from BtDevDeviceMenu where parameter_id =:parameter_id order by menu_level,menu_location asc";
		paramaHashMap.put("parameter_id",inputString);
		list = sqlDao_h.getRecordList(hql,paramaHashMap,false);
		//设置根节点
		btDevDeviceMenu.setMenu_id("root");
		btDevDeviceMenu.setMenu_parent_id("-1");
		btDevDeviceMenu.setMenu_location("1");
		btDevDeviceMenu.setMenu_name_ch("自助菜单");
		btDevDeviceMenu.setMenu_type("0");
		btDevDeviceMenu.setMenu_level("1");
		list.add(0,btDevDeviceMenu);
		return list;
	}
	@Transactional
	public ServiceReturn addMenuFolderById(String inputString) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject obj = JSONObject.fromObject(inputString);
		BtDevDeviceMenu menu = (BtDevDeviceMenu) JSONObject.toBean(obj, BtDevDeviceMenu.class);
		List<String> menuNos = sqlDao_h.getRecordList("select menu_id from BtDevDeviceMenu", null, false);
		String menu_id = "1";
		if(menuNos.size()!=0){
			menu_id = String.valueOf(Integer.valueOf(menuNos.get(menuNos.size()-1))+1);
		}
		menu.setMenu_id(menu_id);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("menu_name_ch",menu.getMenu_name_ch());
		map.put("parameter_id", menu.getParameter_id());
		Long count = sqlDao_h.getRecordCount("select count(*) from BtDevDeviceMenu where menu_name_ch =:menu_name_ch and  parameter_id=:parameter_id",map);
		if(count>0){
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("新增目录失败，存在相同目录名称");
		}else{
			sqlDao_h.saveRecord(menu);
		}
		return ret;
	}
	@Transactional
	public ServiceReturn addMenuLeafById(String inputString) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject obj = JSONObject.fromObject(inputString);
		MenuParam menuParam = (MenuParam) JSONObject.toBean(obj, MenuParam.class);
		JSONObject memu_param = JSONObject.fromObject(menuParam);
		BtDevDeviceMenu menu = (BtDevDeviceMenu) JSONObject.toBean(obj, BtDevDeviceMenu.class);
		menu.setMenu_param(memu_param.toString());
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("bs_id",menu.getBs_id());
		map.put("menu_parent_id", menu.getMenu_parent_id());
		Long count = sqlDao_h.getRecordCount("select count(*) from BtDevDeviceMenu where bs_id =:bs_id and menu_parent_id =:menu_parent_id",map);
		if(count>0){
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("该业务已存在！请删除后再添加");
		}else{
			List<String> menuNos = sqlDao_h.getRecordList("select menu_id from BtDevDeviceMenu", null, false);
			String menu_id = "1";
			if(menuNos.size()!=0){
				menu_id = String.valueOf(Integer.valueOf(menuNos.get(menuNos.size()-1))+1);
			}
			menu.setMenu_id(menu_id);
			sqlDao_h.saveRecord(menu);
		}
		return ret;
	}
	@Transactional
	public ServiceReturn editMenuLeafById(String inputString) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject obj = JSONObject.fromObject(inputString);
		MenuParam menuParam = (MenuParam) JSONObject.toBean(obj, MenuParam.class);
		JSONObject memu_param = JSONObject.fromObject(menuParam);
		BtDevDeviceMenu menu = (BtDevDeviceMenu) JSONObject.toBean(obj, BtDevDeviceMenu.class);
		menu.setMenu_param(memu_param.toString());
		String[] param = {menu.getMenu_name_ch(),menu.getMenu_name_en(),menu.getBs_id(),menu.getMenu_param(),menu.getMenu_id()};
		try {
			sqlDao_h.excuteHql("update BtDevDeviceMenu set menu_name_ch = ?,menu_name_en = ?,bs_id=?,menu_param=? where menu_id = ?", param);
		} catch (Exception e) {
		}
		return ret;
	}
	@Transactional
	public ServiceReturn editMenuFolderById(String inputString)
			throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject obj = JSONObject.fromObject(inputString);
		BtDevDeviceMenu menu = (BtDevDeviceMenu) JSONObject.toBean(obj, BtDevDeviceMenu.class);
		String[] param = {menu.getMenu_name_ch(),menu.getMenu_name_en(),menu.getMenu_id(),menu.getParameter_id()};
		String Hql = "update BtDevDeviceMenu set menu_name_ch = ?,menu_name_en = ?where menu_id = ? and parameter_id=?";
		try {
			sqlDao_h.excuteHql(Hql, param);
		} catch (Exception e) {
		}
		return ret;
	}
	@Transactional
	public ServiceReturn deleteLeafById(String inputString) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject obj = JSONObject.fromObject(inputString);
		BtDevDeviceMenu menu = (BtDevDeviceMenu) JSONObject.toBean(obj, BtDevDeviceMenu.class);
		String[] param = {menu.getMenu_id()};
		try {
			sqlDao_h.excuteHql("delete from BtDevDeviceMenu where menu_id = ?", param);
		} catch (Exception e) {
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("删除失败");
		}
		return ret;
	}
	@Transactional
	public ServiceReturn nodeMoveUp(JSONObject json) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String parameter_id= json.getString("parameter_id");
		String Hql = "update BtDevDeviceMenu set menu_location =? where menu_id =? and parameter_id=?";
		String[] param = {json.getString("menu_location"),json.getString("menu_id"),parameter_id};
		String[] param1 = {json.getString("menu_location1"),json.getString("menu_id1"),parameter_id};
		try {
			sqlDao_h.excuteHql(Hql, param);
			sqlDao_h.excuteHql(Hql, param1);
		} catch (Exception e) {
		}
		return ret;
	}
	@Transactional
	public ServiceReturn nodeMoveDown(JSONObject json) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String parameter_id= json.getString("parameter_id");
		String Hql = "update BtDevDeviceMenu set menu_location =? where menu_id =? and parameter_id=?";
		String[] param = {json.getString("menu_location"),json.getString("menu_id"),parameter_id};
		String[] param1 = {json.getString("menu_location1"),json.getString("menu_id1"),parameter_id};
		try {
			sqlDao_h.excuteHql(Hql, param);
			sqlDao_h.excuteHql(Hql, param1);
		} catch (Exception e) {
		}
		return ret;
	}
	@Transactional
	public List queryMenuLeafById(JSONObject json) throws Exception {
		String parameter_id = json.getString("parameter_id");
		String menu_id = json.getString("menu_id");
		HashMap<String,String> paramaHashMap = new HashMap<String,String>();
		String hql ="from BtDevDeviceMenu where parameter_id =:parameter_id and menu_id =:menu_id";
		paramaHashMap.put("parameter_id",parameter_id);
		paramaHashMap.put("menu_id", menu_id);
		List<BtDevDeviceMenu> list=sqlDao_h.getRecordList(hql, paramaHashMap, false);
		JSONObject memu_param = JSONObject.fromObject(list.get(0).getMenu_param());
		MenuParam menu = (MenuParam) JSONObject.toBean(memu_param, MenuParam.class);
		list.get(0).setMenuparam(menu);
		return list;
	}
	@Transactional
	public List queryParameterId() throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		List  list = sqlDao_h.getRecordList("select distinct bt.parameter_id from BtDevDeviceMenu bt",map,false);
		List<BtDevDeviceMenu> list2 = new ArrayList<BtDevDeviceMenu>();
		for (int i = 0; i < list.size(); i++) {
			BtDevDeviceMenu btDevDeviceMenu = new BtDevDeviceMenu();
			btDevDeviceMenu.setParameter_id(list.get(i).toString());
			list2.add(btDevDeviceMenu);
		}
		return list2;
	}

}
