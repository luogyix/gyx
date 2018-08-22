/**
 * 
 */
package com.agree.framework.exception;

/**
 * 
 * @ClassName: AppException 
 * @Description: 自定义错误代码
 * @company agree   
 * @author haoruibing   
 * @date 2011-7-29 下午02:50:16 
 *
 */
public class AppErrorcode {
	/**
	 * 用户登录(10001——10010)
	 */
	public static final String INVALIDUSER = "exception.invaliduser";//10001用户名或密码错误
	public static final String DUPLICATEUSERCODE = "exception.duplicateusercode";//10002登录账号已存在
	public static final String USERLOGONTIMEOUT = "exception.userlogontimeout";//10003用户登录超时
	public static final String USERCODEALREADYEXIST = "exception.usercodealreadyexist";//10004用户账号或操作员号已存在
	public static final String USERPROHIBITED = "exception.userprohibited";//10005该用户已被禁用或删除
	public static final String USERNOPRIVILIDGE = "exception.usernoprivilidge";//10006该用户没有任何权限
	public static final String DUPLICATEUSERID = "exception.duplicateuserid";//10007用户编号已被使用
	public static final String USERLOGINTOO = "exception.duplicateuserid";//10008用户登陆重复
	public static final String INVALIDTYPE = "exception.invalidType";//连接符类型错误

	/**
	 * 权限错误(10011——10015)
	 */
	public static final String NOPRIVILEGE = "exception.noprivilege";//10011权限不足
	
	/**
	 * 分页查询错误(10016--10020)
	 */
	public static final String PARAMETEROBJECTISNULL = "exception.parameterobjectisnull";//10016分压查询的参数对象为空
	public static final String PARAMETEROBJECTWRONGTYPE = "exception.parameterobjectwrongtype";//10017分页查询的参数对象类型错误
	
	/**
	 * 划帐周期字典错误(10030)
	 */
	public static final String ACCOUNTCYCLEDICTID = "exception.accountcycledictid";//10030划帐周期字典编号已被使用
	
	/**
	 * 导出数据量过大(10040)
	 */
	public static final String DOEXCELDATABOG = "exception.doexceledatabog";//10040导出数据量过大
	
	/**
	 * 文件下载错误(10050)
	 */
	public static final String FILEDOWNLOG = "exception.filedownerror";//10051文件下载路径错误
	
	
	/** 
	 * @Fields DBERROR : 数据库错误
	 */ 
	public static final String DBERROR = "exception.dberror";//11001数据库执行错误
	
	public static final String QUERYERROR = "exception.queryerror";//11001数据库查询错误
	
	public static final String DELERROR = "exception.delerror";//11002数据库删除错误
	
	public static final String SAVEERROR = "exception.saveerror";//11003数据库保存错误
	
	public static final String UPDATEERROR = "exception.updateerror";//11004数据库保存错误
	
	public static final String QUERYPAGEERROR = "exception.querypageerror";//11005分页查询错误
	
	public static final String PARAMETERERROR = "exception.parameterobjecterror";//11006参数错误
	
	
}
