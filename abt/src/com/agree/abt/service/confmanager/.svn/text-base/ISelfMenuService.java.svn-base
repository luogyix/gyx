/**
 * 
 */
package com.agree.abt.service.confmanager;

import java.util.List;

import net.sf.json.JSONObject;

import com.agree.abt.model.confManager.BtSysInterface;
import com.agree.abt.model.confManager.BtTsfMetadata;
import com.agree.framework.web.common.ServiceReturn;

/**自助设备菜单管理接口
 * @author hujuqiang
 * @date 2016-8-23
 */
@SuppressWarnings({"rawtypes" })
public interface ISelfMenuService {
	
	/**
	 * 查询所有元数据菜单，去掉重复的菜单
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn queryAllMetadataMenuToList(String device_type) throws Exception;
	
	/**
	 * 根据metadata_id查询元数据
	 * @return
	 * @throws Exception
	 */
	public List queryMetadataById(JSONObject obj) throws Exception;
	
	/**
	 * 把apply_type和interface_type作为查询条件，查询所有菜单节点
	 * @param apply_type 应用类型,01-排队机 04-手持厅堂 09-手持填单 10-填单机 11-自助机
	 * @param interface_type 界面id
	 * @return List 界面id下的所有菜单节点
	 * @throws Exception
	 */
	public List queryAllNodeToList(String interface_type) throws Exception;

	/**
	 * 添加自助菜单
	 */
	public ServiceReturn addMenu(JSONObject obj) throws Exception;
	
	 /**
     * 根据node_id删除节点及节点菜单
     * @throws Exception
     */
	public ServiceReturn deleteMenuLeafById(JSONObject obj) throws Exception;
	
	/**
     * 修改菜单
     * @return
     * @throws Exception
     */
	public ServiceReturn editMenuLeaf(JSONObject obj) throws Exception;
	public List queryMetadataByInterfaceId(String interface_id) throws Exception;
	public ServiceReturn saveSysInterface(BtSysInterface sysinterface) throws Exception ;
	public ServiceReturn saveMetadata(BtTsfMetadata metadata) throws Exception ;
}
