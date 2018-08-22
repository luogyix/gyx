package com.agree.framework.web.service.administration;

import java.util.List;
import java.util.Map;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.Unit;
import com.agree.framework.web.service.base.IBaseService;

/** 
* @ClassName: IUnitinfoService 
* @Description: 部门信息管理业务接口
* @company agree   
* @author dhs   
* @date 2011-08-26 
* 
*/
@SuppressWarnings({"rawtypes"})
public interface IUnitinfoService extends IBaseService{

	/**
	 * 查询部门信息
	 * 
	 * */
	List<Unit> findUnitinfoAll(Map map, Page pageInfo)throws Exception;
	
	/**
	 * 添加部门信息
	 * 
	 * */
	ServiceReturn addUnit(Unit unit,String default_flag)throws Exception;
	
	/**
	 * 修改部门信息
	 * 
	 * */
	ServiceReturn editUnit(Unit unit,String parentid)throws Exception;
	
	/**
	 * 删除部门信息
	 * 
	 * */
	ServiceReturn delUnit(List<Unit> units)throws Exception;
	
	/**
	 * 获取该部门下的所有部门
	 * 
	 * */
	public List<Integer> getNextUnitAll(String unitid);

	/**
	 * 修改用户状态为禁用
	 */
	public ServiceReturn changeUnitUserState(Unit unit);
}
