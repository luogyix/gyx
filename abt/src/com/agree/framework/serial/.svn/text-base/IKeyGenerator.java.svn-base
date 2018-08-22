package com.agree.framework.serial;

import java.io.Serializable;

/**
 * <DL>
 * <DT><B> 流水号发生器. </B></DT>
 * <p>
 * <DD>
 * 用于记录交易流水号
 * <li>业务启动，从序列化文件中反序列化创建流水号发生器，如果不存在则新增流水号发生器。
 * <li>业务运行，从流水号发生器中取用流水号，流水号发生器本身更新序列化文件。
 * <li>业务停止，序列化流水号发生器存入文件或数据库。
 * <li>流水号发生器需要考虑以下问题
 * <li>流水号的永久存储：是存放在文件中还是在数据库中
 * <li>流水号的切换：每日、每周、每月、每年切换或不切换
 * <li>流水号的范围：是否可以自定义流水号范围
 * <li>流水号的格式：是否可以自定义流水号格式</DD>
 * </DL>
 * <p>
 * 
 * <DL>
 * <DT><B>使用范例</B></DT>
 * <p>
 * <DD>
 * 使用范例说明</DD>
 * </DL>
 * <p>
 */
public interface IKeyGenerator extends Serializable{

	/**
	 * 获取下一个流水号，改操作改变当前流水号
	 * 
	 * @return 下一个流水号
	 */
	String getNextKey() throws KeyException;

	/**
	 * 保存流水号到永久介质
	 * 
	 * @throws KeyException
	 */
	void save() throws KeyException;

	/**
	 * 重置流水号
	 * 
	 * @throws KeyException
	 */
	void reset() throws KeyException;

	/**
	 * 获取当前流水号，并不改变当前流水号
	 * 
	 * @return 当前流水号
	 * @throws KeyException
	 */
	String getKey() throws KeyException;

}
