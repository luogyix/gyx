package com.agree.framework.web.service.welcome;



import org.springframework.transaction.annotation.Transactional;

import com.agree.framework.web.service.base.BaseService;


/**
 * 
 * @ClassName: 
 * @company 赞同科技
 * @author 王明哲
 * @date 2017-1-16下午3:40:55
 */
public class F5CheckServiceImpl extends BaseService implements  IF5CheckService
{
	/**
	 * 接口实现类方法，查询数据库当前时间
	 */
	@Transactional
	public Object querySqlTime() throws Exception 
	{
		/**
		 * 获取数据库当前时间
		 */
		String sql = "select sysdate from sysibm.sysdummy1";
		Object date = sqlDao_h.getRecordBySql(sql, String.class);
		return date;
	}
}
