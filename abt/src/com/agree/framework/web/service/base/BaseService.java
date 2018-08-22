/**
 * 
 */
package com.agree.framework.web.service.base;

import javax.annotation.Resource;

import com.agree.framework.dao.core.IHibernateGenericDao;
/**
 * @author David
 *
 */
public abstract class BaseService implements IBaseService {
	
	@Resource(name="habernateDao")
	protected IHibernateGenericDao sqlDao_h;

	public IHibernateGenericDao getSqlDao_h() {
		return sqlDao_h;
	}

	public void setSqlDao_h(IHibernateGenericDao sqlDao_h) {
		this.sqlDao_h = sqlDao_h;
	}

	public static final Integer TXNFLAG_FH=1;
	public static final Integer TXNFLAG_SAV=0;
	public static final Integer TXNFLAG_COMM_ISS=2;
	public static final Integer TXNFLAG_TIME_ISS=3;
}
