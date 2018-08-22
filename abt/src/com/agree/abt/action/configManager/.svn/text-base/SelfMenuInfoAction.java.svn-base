package com.agree.abt.action.configManager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.common.Logger;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.confManager.BtSysInterface;
import com.agree.abt.model.confManager.BtTsfMetadata;
import com.agree.abt.service.confmanager.ISelfMenuService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;

/**
 * 自助菜单管理
 * @ClassName: SelfMenuInfoAction.java
 * @company 赞同科技
 * @author Hujuqiang
 * @date 2016-8-18
 */

@SuppressWarnings("all")
public class SelfMenuInfoAction extends BaseAction{

	private Logger logger = Logger.getLogger(SelfMenuInfoAction.class);
	private static final long serialVersionUID = 2141897762615781445L;
	private ISelfMenuService selfMenuService;
	public ISelfMenuService getSelfMenuService() {
		return selfMenuService;
	}
	public void setSelfMenuService(ISelfMenuService selfMenuService) {
		this.selfMenuService = selfMenuService;
	}
	
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		//sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());//获取部门集合
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		
		return SUCCESS;
	}
	
	/**
	 * 查询所有元数据菜单，并且去掉菜单名重复的数据 
	 * @return
	 * @throws Exception
	 */
	public void queryAllMetadataMenuToList() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String device_type = request.getParameter("device_type");
		ServiceReturn ret = new ServiceReturn(true, "");
		ret=selfMenuService.queryAllMetadataMenuToList(device_type);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	
	/**
	 * 选择参数后，查询此参数下所有的菜单（节点）
	 */
	public void  queryAllMenuToList() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		String interface_id=req.getParameter("interface_id");
		List<BtSysInterface> list = selfMenuService.queryAllNodeToList(interface_id);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	
	/**
	 * 添加自助菜单（目录和业务）</br>
	 * 			最后修改日期：高艺祥-2017-01-16
	 */
    public String addMenu() throws Exception{
    	HttpServletRequest req = ServletActionContext.getRequest();
    	String inputString = super.getJsonString();
    	JSONObject obj= JSONObject.fromObject(inputString);
        ServiceReturn ret = selfMenuService.addMenu(obj);
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	
	/**
	 *  根据参数ID查询自助设备菜单节点
	 * @return
	 * @throws Exception
	 */
    public String queryMenuLeafById() throws Exception{
    	String inputString = super.getJsonString();
    	JSONObject obj= JSONObject.fromObject(inputString);
    	Page pageinfo = new Page();
    	List<BtTsfMetadata> list = selfMenuService.queryMetadataById(obj);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionResult(pageinfo, list, ret);
		return AJAX_SUCCESS;
    }
    
    /**
     * 根据node_id删除节点及节点菜单
     * @throws Exception
     */
    public String deleteMenuLeaf()throws Exception{
    	String inputString = super.getJsonString();
    	JSONObject obj= JSONObject.fromObject(inputString);
    	Page pageinfo = new Page();
    	ServiceReturn ret = selfMenuService.deleteMenuLeafById(obj);
    	JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
    }
    
    /**
     * 修改菜单
     * @return
     * @throws Exception
     */
    public String editMenuLeaf() throws Exception{
    	String inputString = super.getJsonString();
    	JSONObject obj= JSONObject.fromObject(inputString);
        ServiceReturn ret = selfMenuService.editMenuLeaf(obj);
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
    	return AJAX_SUCCESS;
    }
	
	
}
