package com.agree.abt.action.configManager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.confManager.BtDevDeviceMenu;
import com.agree.abt.service.confmanager.ISelfDevMenuService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
/**
 * 自助设备菜单管理
 * @author linyuedong
 * @date 2015-4-2
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SelfDevMenuAction extends BaseAction {
	private static final long serialVersionUID = -7819569682663265747L;
	private ISelfDevMenuService selfDevMenuService;
	public ISelfDevMenuService getSelfDevMenuService() {
		return selfDevMenuService;
	}
	public void setSelfDevMenuService(ISelfDevMenuService selfDevMenuService) {
		this.selfDevMenuService = selfDevMenuService;
	}
	/**
	 * 加载页面
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	/**
	 * 查询菜单参数ID
	 * @throws Exception
	 */
	public void queryMenuParameterId() throws Exception{
		List<BtDevDeviceMenu> list = selfDevMenuService.queryParameterId();
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	/**
	 * 根据参数ID查询自助设备菜单
	 * @return
	 * @throws Exception
	 */
	public void querySelfDevMenuById() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
    	String inputString = req.getParameter("parameter_id");
		List<BtDevDeviceMenu> list = selfDevMenuService.querySelfDevMenu(inputString);
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	/**
	 *  根据参数ID添加自助设备菜单目录
	 * @return
	 * @throws Exception
	 */
    public String addMenuFolderById() throws Exception{
    	String inputString = super.getJsonString();
        ServiceReturn ret = selfDevMenuService.addMenuFolderById(inputString);
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
    /**
	 *  根据参数ID添加自助设备菜单节点
	 * @return
	 * @throws Exception
	 */
    public String addMenuLeafById() throws Exception{
    	String inputString = super.getJsonString();
        ServiceReturn ret = selfDevMenuService.addMenuLeafById(inputString);
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
    /**
	 *  根据参数ID修改自助设备菜单目录
	 *
	 * @return
	 * @throws Exception
	 */
    public String editMenuFolderById() throws Exception{
    	String inputString = super.getJsonString();
        ServiceReturn ret = selfDevMenuService.editMenuFolderById(inputString);
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
    	List list = selfDevMenuService.queryMenuLeafById(obj);
    	ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
    	super.setActionResult(pageinfo, list, ret);
		return AJAX_SUCCESS;
    }
    /**
	 *  根据参数ID修改自助设备菜单节点
	 * @return
	 * @throws Exception
	 */
    public String editMenuLeafById() throws Exception{
    	String inputString = super.getJsonString();
        ServiceReturn ret = selfDevMenuService.editMenuLeafById(inputString);
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
    public String deleteMenuLeafById() throws Exception{
    	String inputString = super.getJsonString();
        ServiceReturn ret = selfDevMenuService.deleteLeafById(inputString);
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
    public String nodeMoveUpById() throws Exception{
    	String inputString =super.getJsonString();
    	JSONObject obj = JSONObject.fromObject(inputString);
    	ServiceReturn ret = selfDevMenuService.nodeMoveUp(obj);
    	JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
    };
    public String nodeMoveDownById() throws Exception{
    	String inputString =super.getJsonString();
    	JSONObject obj = JSONObject.fromObject(inputString);
    	ServiceReturn ret = selfDevMenuService.nodeMoveDown(obj);
    	JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
    };
    
}
