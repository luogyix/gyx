package com.agree.framework.web.form.base;

import java.util.List;
@SuppressWarnings({"rawtypes"})
public interface BusinessModel {
	/**
	 * 用value对像集合构造新的对象，然后放到res里返回
	 * @param value
	 * @param res
	 * @return
	 */
	public  List getObjectList(Object[] value,List res);
}
