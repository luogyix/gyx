/**
 * 
 */
package com.agree.abt.service.confmanager;

import java.util.List;

import net.sf.json.JSONObject;

import com.agree.framework.web.common.ServiceReturn;

/**自助设备菜单管理接口
 * @author linyuedong
 *
 */
@SuppressWarnings({ "rawtypes" })
public interface ISelfDevMenuService {
	/**
	 * 查询自助设备菜单
	 */
	public List querySelfDevMenu(String inputString) throws Exception;
	/**
	 * 根据节点ID和参数ID查询节点
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public List queryMenuLeafById(JSONObject json) throws Exception;
	/**
	 * 给自助设备菜单树添加目录
	 * @param inputString
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn addMenuFolderById(String inputString)throws Exception;
	/**
	 * 给自助设备菜单树添加节点
	 * @param inputString
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn addMenuLeafById(String inputString) throws Exception;
	/**
	 * 自助设备菜单树修改节点
	 * @param inputString
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn editMenuLeafById(String inputString) throws Exception;
	/**
	 * 自助设备菜单树修改目录
	 * @param inputString
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn editMenuFolderById(String inputString)throws Exception;
	/**
	 * 自助设备菜单树删除节点
	 * @param inputString
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn deleteLeafById(String inputString)throws Exception;
	/**
	 * 上移
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn nodeMoveUp(JSONObject json) throws Exception;
	
	/**
	 * 下移
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn nodeMoveDown(JSONObject json) throws Exception;
	/**
	 * 查询菜单参数ID
	 * @return
	 * @throws Exception
	 */
	public List queryParameterId() throws Exception;
}
