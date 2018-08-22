package com.agree.framework.struts2.webserver;

import javax.servlet.ServletContext;

import com.agree.framework.exception.AppException;
/**
 * 
 * @ClassName: IStartupObject 
 * @Description: 启动初始化类接口
 * @company agree   
 * @author haoruibing   
 * @date 2011-7-29 下午03:43:46 
 *
 */
public interface IStartupObject {
	/**
	 * 
	 * @Title: initializeContextVariables 
	 * @Description: 初始化信息方法
	 * @param  context    上下文 
	 * @return void    返回类型 
	 * @throws
	 */
	public void initializeContextVariables(ServletContext context);
	
	/** 
	 * @Title: initApplicationVariables 
	 * @Description: 初始化应用系统参数
	 * @param @param context
	 * @param @throws AppException    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void initApplicationVariables(ServletContext context)	throws AppException;
	
	/**
	 * 设备接入注册,跟AFA进行NATP通信注册推送
	 * @throws Exception
	 */
	public void regInterface() throws Exception;
	/** 
	 * @Title: setSystemDict 
	 * @Description: 设置系统字典
	 * @param @param context
	 * @param @throws AppException    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void setSystemDict(ServletContext context) throws AppException ;
	
	/** 
	 * @Title: setUnits 
	 * @Description: 设置部门集合
	 * @param @param context    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void setUnits(ServletContext context);
	
	/**
	 * 设置参数化开关
	 * @param context
	 */
	public void setParameters(ServletContext context);
	
	/** 
	 * @Title: setMouduleList 
	 * @Description:设置菜单集合
	 * @param @param context
	 * @param @throws AppException    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void setMouduleList(ServletContext context) throws AppException;
	
	/** 
	 * @Title: setAppDict 
	 * @Description: 设置银行数据标准
	 * @param @param context
	 * @param @throws AppException    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void setAppDict(ServletContext context) throws AppException; 
	
	/** 
	 * @Title: setNatps 
	 * @Description: 为natp接收的报文设置一个缓存位置
	 * @param @param context
	 * @param @throws AppException    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void setNatps(ServletContext context) throws AppException;
	
	/** 
	 * @Title: destory 
	 * @Description: 服务关闭时清理无用资源
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void destory();
}
