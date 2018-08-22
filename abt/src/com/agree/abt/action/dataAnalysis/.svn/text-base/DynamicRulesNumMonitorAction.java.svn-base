package com.agree.abt.action.dataAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.dataAnalysis.DynamicRulesNum;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.DateUtil;

/**
 * 动态排队号流水监控
 * @ClassName: DynamicRulesNumMonitorAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-7-7
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DynamicRulesNumMonitorAction extends BaseAction {
	
	private static final long serialVersionUID = 2141897762615781445L;
	private ABTComunicateNatp cona;
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());//获取部门集合
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 查询动态排队号流水
	 */
	public String queryDynamicRulesNum() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Page pageInfo = new Page();
		User user = super.getLogonUser(false);

		//正常使用时将一下注释的打开
		 
		
		cona.setBMSHeader("ibp.bms.b203_4.01", user);
		
		cona.set("branch",jsonObj.getString("branch"));
		
		//分页专用
		cona.set("needpage","1");
		cona.set("selflag","2");
		cona.set("pageflag",pageInfo.getPageflag().toString());//分页标识
		String currentpage = "";
		switch (pageInfo.getPageflag()) {
		case 0:
			currentpage = "1";
			pageInfo.setStart(1);
			break;
		case 1:
			currentpage = "1";
			break;
		case 2:
			currentpage = String.valueOf(pageInfo.getStart()+1);
			break;
		case 3:
			currentpage = String.valueOf(pageInfo.getStart()-1);
			break;
		case 4:
			currentpage = String.valueOf(pageInfo.getStart());
			pageInfo.setStart(pageInfo.getStart());
			break;
		}
		cona.set("currentpage",currentpage);//当前页码
		cona.set("count",pageInfo.getLimit().toString());//每页记录数
		
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List list = new ArrayList();//结果list
		
		if(!"0".equals(map.get("queuesize").get(0))){
//			pageInfo.setTotal(Integer.parseInt(map.get("rcdsumnum").get(0)));//总条数	
//			Integer pageNo = Integer.parseInt(map.get("totalpage").get(0));//总页数
//			if (pageInfo.getStart() == -1) {// 查询最后一页
//				pageInfo.setRowStart((pageNo - 1) * pageInfo.getLimit());
//				pageInfo.setRowEnd(pageInfo.getRowStart()
//						+ (pageInfo.getTotal() % pageInfo.getLimit() == 0 ? pageInfo.getLimit() : pageInfo.getTotal() % pageInfo.getLimit()));
//				pageInfo.setPage(pageNo);
//				pageInfo.setTotal(pageInfo.getTotal());
//			} else {
//				pageInfo.setRowStart((pageInfo.getStart() - 1) * pageInfo.getLimit() );
//				pageInfo.setRowEnd( (pageInfo.getRowStart() + pageInfo.getLimit()) <= pageInfo.getTotal() ? (pageInfo.getRowStart() + pageInfo.getLimit())
//						: pageInfo.getTotal() );
//				pageInfo.setPage(pageNo);
//				pageInfo.setTotal(pageInfo.getTotal());
//			}
			String loopNum = (String) map.get("queuesize").get(0);
			int num = Integer.parseInt(loopNum);
			
			for (int i = 0; i < num; i++) {
				DynamicRulesNum numflow = new DynamicRulesNum();
				//正常使用时将一下注释的打开
				numflow.setWork_date(map.get("work_date").get(i).toString());
				numflow.setQueue_num(map.get("queue_num").get(i).toString());
				numflow.setBranch((String) map.get("branch").get(0));
				numflow.setBranch_name(map.get("branch_name").get(i).toString());
				numflow.setBs_name_ch(map.get("bs_name_ch").get(i).toString());
				numflow.setQueuetype_name(map.get("queuetype_name").get(i).toString());
				numflow.setCusttype_name(map.get("custtype_name").get(i).toString());
				String en_queue_time = map.get("en_queue_time").get(i).toString();
				numflow.setEn_queue_time("".equals(en_queue_time)?"":DateUtil.formatTime(en_queue_time));
				int waittime = Integer.parseInt(map.get("waittime").get(i).toString());
				numflow.setWait_time(String.valueOf(waittime/60==0?1:waittime/60)+"分钟");
				list.add(numflow);
			}
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);

		return AJAX_SUCCESS;
	}

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
}
