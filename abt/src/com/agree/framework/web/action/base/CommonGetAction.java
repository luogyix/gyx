package com.agree.framework.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.util.LoadKeyWord;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.util.CommonData;
@SuppressWarnings({"unchecked","rawtypes"})
public class CommonGetAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	public void getSelectCodes() throws IOException, Exception{
		String item_id=ServletActionContext.getRequest().getParameter("item_id");
		LoadKeyWord lwk=new LoadKeyWord();
		String tt=ServletActionContext.getServletContext().getRealPath("/");
		HashMap<String,ArrayList<CommonData>> reusltmap=null;
		if(ServletActionContext.getRequest().getSession().getAttribute("configKeyMap")!=null){
			 reusltmap=(HashMap)ServletActionContext.getRequest().getSession().getAttribute("configKeyMap");
		}else{
			String serverInfo=ServletActionContext.getServletContext().getServerInfo();
			if(serverInfo.indexOf("IBM WebSphere")!=-1){
				tt=tt+"\\";
			}
			lwk.load(tt);
			reusltmap=(HashMap) lwk.getSelectCodes();
			ServletActionContext.getRequest().getSession().setAttribute("configKeyMap", reusltmap);
		}
	
		ArrayList<CommonData> data=new ArrayList<CommonData>();
		if(reusltmap.get(item_id)!=null){
			data=reusltmap.get(item_id);
		}else{
			data.add(new CommonData());
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, data);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
}
