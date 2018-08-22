package com.agree.abt.action.configManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;

/**
 * 发卡机基本信息配置
 * <p>设置发卡机基本信息以及管理人员，并对发卡机信息进行增删改查操作<p>
 * @author 高艺祥<br>最后修改日期：2016/11/2
 * @version 0.1
 * 
 */
public class CardMachineAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	
	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
	
	/**
	 * 
	 * 初始化页面
	 * @author 高艺祥<br>最后修改日期：2016/11/2
	 * @version 0.1
	 */
	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 分页查询发卡机信息
	 * @author 高艺祥<br> 最后修改日期：2016/11/3-10:23
	 * @version 0.1
	 */
	public String queryCardMachinePage() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();//获取request域
		//从session域中获取用户信息
		User user = (User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//获得页面请求参数：每页显示条数，当前页码
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		
		//使用工具类ABTComunicateNatp和afa进行通讯
		 
		cona.setBMSHeader("ibp.bms.b140_2.01", user);
		
		String branchParam = obj.getString("branchParam").trim();
		cona.set("branch",user.getUnitid());
		if(branchParam.length() != 9 || branchParam.equals("-请输入子机构号-")){
			cona.set("branchParam","0");
		}else{
			cona.set("branchParam",branchParam);
		}
		Map<String,List<String>> map =  cona.exchange();
		
		if(map.get("cmsize").get(0).equals("0")){
			throw  new AppException("查询无记录或非法查询");
		}
		String loopNum = (String) map.get("cmsize").get(0);
		int num = Integer.parseInt(loopNum);
		Page pageInfo = this.getPage(obj);
		
		
		//总条数
		pageInfo.setTotal(num);
		//分页逻辑
		//获取总页数
		Integer pageNo = (pageInfo.getTotal() % pageInfo.getLimit() == 0) ? pageInfo.getTotal()/pageInfo.getLimit():pageInfo.getTotal()
				/pageInfo.getLimit() +1;
		//查询最后一页
		if(pageInfo.getStart() == -1){
			pageInfo.setRowStart((pageNo-1) * pageInfo.getLimit());
			pageInfo.setRowEnd(pageInfo.getRowStart()
					+ (pageInfo.getTotal() % pageInfo.getLimit() == 0 ? pageInfo.getLimit() : pageInfo.getTotal() % pageInfo.getLimit()));
			//设置总页数
			pageInfo.setPage(pageNo);
			//设置总条数
			pageInfo.setTotal(pageInfo.getTotal());
		} else {
			pageInfo.setRowStart((pageInfo.getStart() - 1) * pageInfo.getLimit() );
			//每页显示条数
			pageInfo.setRowEnd( (pageInfo.getRowStart() + pageInfo.getLimit()) <= pageInfo.getTotal() ? (pageInfo.getRowStart() + pageInfo.getLimit())
					: pageInfo.getTotal() );
			//当前页
			pageInfo.setPage(pageNo);
			//总条树
			pageInfo.setTotal(pageInfo.getTotal());
		}
		
		ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//循环得到的当前页所有的值
		for(int i = pageInfo.getRowStart();i<pageInfo.getRowEnd();i++){
			HashMap<String, Object> hld = new HashMap<String, Object>();
			hld.put("machine_Code",map.get("cardMachinecode").get(i));
			hld.put("machine_name",map.get("cardMachinename").get(i));
			hld.put("branch",map.get("branch").get(i));
			hld.put("status",map.get("status").get(i));
			hld.put("accreditrank", map.get("accreditrank").get(i));
			hld.put("begin_time",map.get("beginTime").get(i));
			hld.put("addUser",map.get("addUser").get(i));
			hld.put("addTime",map.get("addTime").get(i));
			hld.put("editUser",map.get("editUser").get(i));
			hld.put("editTime",map.get("editTime").get(i));
			hld.put("site",map.get("site").get(i));
			hld.put("hardwareNum",map.get("hardwareNum").get(i));
			hld.put("machine_ip",map.get("machineIp").get(i));
			hld.put("machine_control",map.get("machineControl").get(i));
			hld.put("machine_confirmation",map.get("machineConfirmation").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		//想页面返回结果
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 添加发卡机信息
	 * @author 高艺祥<br>最后修改日期：2016/11/4-15:19
	 * @version 0.1
	 * @throws Exception 
	 */
	public String addCardMachine() throws Exception{
		
		//获得request域
		HttpServletRequest req = ServletActionContext.getRequest();
		//从session域中获得当前用户信息
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//获得页面请求负载
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		
		//和afa进行通讯
		 
		
		cona.setBMSHeader("ibp.bms.b140_1.01", user);
		cona.set("branch", user.getUnitid());//机构号
		cona.set("accreditrank", obj.getString("accreditrank"));//授权等级
		cona.set("machinecode" , obj.getString("machine_Code"));//发卡机信息编号
		cona.set("machinename" , obj.getString("machine_name"));//发卡机名称
		cona.set("status" , obj.getString("status").equals("on") ? "1" : "0");
		cona.set("site" , obj.getString("site"));//安装地址
		cona.set("machineControl" , obj.getString("machine_control"));//管理员
		cona.set("machineConfirmation" , obj.getString("machine_confirmation"));//审核员
		cona.set("addUser",user.getUsercode());//添加人
		//添加时间
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd-hh:mm:ss");
		String addTime = sdf.format(new Date());
		cona.set("addTime", addTime);
		cona.exchange();
		ServiceReturn tet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject resultJson = super.convertServiceReturnToJson(tet);
		super.setActionresult(resultJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 *删除发卡机信息配置
	 *@author 高艺祥<br> 最后修改日期：2016/11/6-11:01
	 *@version V0.1 
	 */
	public String delCardMachine() throws Exception{
		//得到servlet的request域
		HttpServletRequest req = ServletActionContext.getRequest();
		//从session域中获取当前用户信息
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//获得页面请求负载
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
			Object obj[] = jsonObj.values().toArray();
			 
			//初始化NATP通讯
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b140_4.01", user);
			cona.set("machine_Code", obj[0].toString());
			//判断是否为删除非本机构
			String machinecode = obj[1].toString();
			if(user.getUnitid().equals(machinecode)==false){
				throw new AppException("不能删除非本机构发卡机信息配置");
			}
			cona.set(IDictABT.BRANCH, machinecode);
			//判断afa的返回结果,是否成功
			cona.exchange();
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 修改排队机信息配置
	 * @author 高艺祥<br> 最后修改日期：2016/11/9-11:23
	 * @version V0.1
	 */
	public String editCardMachine() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		//从session域中的到当前用户信息
		User user =  (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//获得页面请求负载
		String inputJsonString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonString);
		//调用与afa通讯的工具类
		 
		
		cona.setBMSHeader("ibp.bms.b140_3.01", user);
		cona.set_cover("machinecode", obj.getString("machine_Code"));
		cona.set("branch", user.getUnitid());
		cona.set("status",obj.getString("status").equals("on") ? "1" : "0");
		cona.set("accreditrank", obj.getString("accreditrank"));
		cona.set("machinename", obj.getString("machine_name"));
		cona.set("site", obj.getString("site"));
		cona.set("machinecontrol", obj.getString("machine_control"));
		cona.set("machineconfirmation", obj.getString("machine_confirmation"));
		cona.set("edituser", user.getUsercode());
		//修改时间
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd-hh:mm:ss");
		String editTime = sdf.format(new Date());
		cona.set_cover("edittime", editTime);
		
		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn tet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject resultJson = super.convertServiceReturnToJson(tet);
		super.setActionresult(resultJson.toString());
		return AJAX_SUCCESS;
	}
}