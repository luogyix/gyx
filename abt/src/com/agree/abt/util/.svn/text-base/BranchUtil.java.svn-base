package com.agree.abt.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.form.administration.Unit;
import com.agree.util.Constants;

public class BranchUtil{
	
	/** 
	 * @Title: getHeadBranch 
	 * @Description: 获取总行信息
	 */ 
	public static Unit getHeadBranch(){
		@SuppressWarnings("unchecked")
		List<Unit> unitList = (List<Unit>)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITS);
		for(Unit unit:unitList){
			if (Constants.BRANCHTYPE_TOP.equals(unit.getBank_level()))
				return unit;
		}
		return null;
	}

	/** 
	 * @Description: 通过id获取下属一级的机构，用于获取树的下一级列表
	 * @param brno
	 * @return    
	 */ 
	public static List<Unit> getNextSubBranch(String brno){
		Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
		Unit localUnit = unit_tree.findNodeById(brno);
		return localUnit.getChildNodes();
	}

	/**
	 * 通过机构id获取机构名称
	 * @param brno
	 * @return
	 */
	public static String getBranchNameById(String brno){
		@SuppressWarnings("unchecked")
		List<Unit> unitList = (List<Unit>)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITS);
		for(Unit unit:unitList){
			if (brno.equals(unit.getUnitid()))
				return unit.getUnitname();
		}
		return null;
	}
	
	/**
	 * 返回本级及上级机构Unit列表
	 * @return
	 */
	public static List<Unit> getParentBranchList(String brno){
		Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
		List<Unit> list = new ArrayList<Unit>();
		Unit localUnit = unit_tree.findNodeById(brno);
		Unit parentUnit = localUnit.getParentNode();
		list.add(localUnit);
		while(parentUnit!=null){
			list.add(parentUnit);
			parentUnit = parentUnit.getParentNode();
		}
		return list;
	}
	
	/**
	 * 返回本级及下级机构Unit列表
	 * @return
	 */
	public static List<Unit> getSubBranchList(String brno){
		Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
		List<Unit> list = new ArrayList<Unit>();
		Unit localUnit = unit_tree.findNodeById(brno);
		list.add(localUnit);
		getSubBranchList(list, localUnit);
		return list;
	}
	
	private static void getSubBranchList(List<Unit> list, Unit localUnit){
		List<Unit> childUnits = localUnit.getChildNodes();
		if(childUnits!=null){
			for (Unit unit : childUnits) {
				list.add(unit);
				getSubBranchList(list, unit);
			}
		}
	}
	
	/**
	 * 返回本级及上级机构Brno列表,用于查本级及上级数据
	 * @return
	 */
	public static List<Object> getParentBrnoList(String brno){
		Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
		List<Object> list = new ArrayList<Object>();
		list.add(brno);
		Unit parentUnit = unit_tree.findNodeById(brno).getParentNode();
		while(parentUnit!=null){
			list.add(parentUnit.getUnitid());
			parentUnit = parentUnit.getParentNode();
		}
		return list;
	}
	
	/**
	 * 返回本级及下级机构Brno列表,用于查本级及下级数据
	 * @return
	 */
	public static List<Object> getSubBrnoList(String brno){
		Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
		List<Object> list = new ArrayList<Object>();
		list.add(brno);
		Unit localUnit = unit_tree.findNodeById(brno);
		getSubBrnoList(list, localUnit);
		return list;
	}
	
	private static void getSubBrnoList(List<Object> list, Unit localUnit){
		List<Unit> childUnits = localUnit.getChildNodes();
		if(childUnits!=null){
			for (Unit unit : childUnits) {
				list.add(unit.getUnitid());
				getSubBrnoList(list, unit);
			}
		}
	}
}
