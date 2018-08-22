package com.agree.abt.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.abt.model.TBpimBranch;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.struts2.webserver.IStartupObject;
import com.agree.util.Constants;
@SuppressWarnings({ "rawtypes" })
public class AbtStartUpObject implements IStartupObject{
	private static final Logger logger = LoggerFactory.getLogger(AbtStartUpObject.class);
	private ABTComunicateNatp cona;
	
	public void initApplicationVariables(ServletContext context) {
		try {
			//加载setNatpsMsg001推送初始信息
			logger.info("------加载NATP推送初始信息------");
			setNatps(context);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	/* (non-Javadoc)
	 * <p>Title: setNatpsMsg001</p> 
	 * <p>Description: </p> 
	 * @param context
	 * @throws AppException 
	 * @see com.agree.framework.struts2.webserver.IStartupObject#setNatpsMsg001(javax.servlet.ServletContext) 
	 */ 
	public void setNatps(ServletContext context) throws AppException {
		List<Map> list = new ArrayList<Map>();
		context.setAttribute(ApplicationConstants.NATP_CACHE,list);
	}
	
	public void setAppDict(ServletContext context) throws AppException {
	}

	/* (non-Javadoc)
	 * <p>Title: setBranchs</p> 
	 * <p>Description: 初始化机构信息树</p> 
	 * @param context
	 * @throws AppException 
	 * @see com.agree.framework.struts2.webserver.IStartupObject#setBranchs(javax.servlet.ServletContext) 
	 */ 
	public void setBranchs(ServletContext context) throws AppException {
//		List<TBpimBranch> branchList = this.sqlDao_h.getRecordList("from TBpimBranch p  order by p.type,p.brno asc",false);
		List<TBpimBranch> branchList = new ArrayList<TBpimBranch>();
		for(TBpimBranch branch:branchList){
			boolean isleaf=true;
			for(TBpimBranch branch1:branchList){
				if(branch1.getSupervbrno().equals(branch.getBrno())||branch.getType().equals(Constants.BRANCHTYPE_TOP)){
					isleaf=false;
					break;
				}
			}
			branch.setLeaf(isleaf);
		}
		context.setAttribute(ApplicationConstants.BRANCH,branchList);
	}

	public void setMouduleList(ServletContext context) throws AppException {
	}

	public void setSystemDict(ServletContext context) throws AppException {
	}

	public void setUnits(ServletContext context) {
	}
	public void initializeContextVariables(ServletContext context) throws AppException {
	}

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}

	public void regInterface() throws Exception {
		
	}

	public void setParameters(ServletContext context) {
		
	}

	@Override
	public void destory() {
		
	}

}
