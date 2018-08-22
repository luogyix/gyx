package com.agree.framework.jasper;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRField;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * JasperReport报表的数据源 User: YAOJING Date: 2005-9-28 Time: 10:03:35
 */
public class JRIBatisDataSource extends JREmptyDataSource {
	/**
   *
   */
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("rawtypes")
	private List data = null;
	@SuppressWarnings("rawtypes")
	private Iterator iterator = null;
	private Object currentBean = null;
	private int filterStart = 0;

	/**
	 * 构造函数
	 * 
	 * @param beanList
	 */
	@SuppressWarnings("rawtypes")
	public JRIBatisDataSource(List beanList) {
		this.data = beanList;
		if (this.data != null) {
			this.iterator = this.data.iterator();
		}
	}

	/**
	 * 构造函数
	 * 
	 * @param object
	 */
	public JRIBatisDataSource(Object object) {
		List<Object> list = null;
		if (object != null) {
			list = new ArrayList<Object>();
			list.add(object);
		}
		this.data = list;
		if (this.data != null) {
			this.iterator = this.data.iterator();
		}
	}

	/**
	 * 构造函数
	 */
	public JRIBatisDataSource() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param count
	 */
	public JRIBatisDataSource(int count) {
		super(count);
	}

	/**
	 * 判断是否有下一条记录
	 */
	public boolean next() {
		if (this.data == null)
			return super.next();
		boolean hasNext = false;
		if (this.iterator != null) {
			hasNext = this.iterator.hasNext();
			if (hasNext) {
				this.currentBean = this.iterator.next();
			}
		}
		return hasNext;
	}

	/**
	 * 获得字段的值
	 * 
	 * @param field
	 */
	@SuppressWarnings("rawtypes")
	public Object getFieldValue(JRField field) {
		if (this.data == null)
			return super.getFieldValue(field);
		Object value = null;
		if (currentBean != null) {
			String propertyName = field.getName();
			if (currentBean instanceof Map) {
				Map map = (Map) currentBean;
				value = map.get(propertyName);
			} else {
				BeanWrapper beanWrapper = new BeanWrapperImpl(currentBean);
				try {
					value = beanWrapper.getPropertyValue(propertyName);
				} catch (BeansException e) {
					log.error(e.getMessage());
				}
			}
		}
		return value;
	}

	/**
	 * 移动到第一条
	 */
	public void moveFirst() {
		if (this.data != null) {
			this.iterator = this.data.iterator();
		} else {
			super.moveFirst();
		}
	}

	/**
	 * 记录集过滤，只支持一个外键
	 * 
	 * @param fieldName
	 * @param value
	 * @return IBatisListDataSource
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JRIBatisDataSource filter(String fieldName, Object value) {
		if (value == null)
			return new JRIBatisDataSource();
		List dateList = new ArrayList();
		Object objectValue = null;
		Object object;
		BeanWrapper beanWrapper;
		boolean start = false;
		if (this.data != null && this.data.size() > 0) {
			for (int i = filterStart; i < data.size(); i++) {
				object = data.get(i);
				if (object != null) {
					if (object instanceof Map) {
						Map map = (Map) object;
						objectValue = map.get(fieldName);
					} else {
						beanWrapper = new BeanWrapperImpl(object);
						try {
							objectValue = beanWrapper
									.getPropertyValue(fieldName);
						} catch (BeansException e) {
							log.error(e.getMessage());
						}
					}
				}

				if (objectValue != null) {
					if (objectValue.equals(value)) {
						dateList.add(object);
						start = true;
					} else {
						if (start) {
							filterStart = i;
							break;
						}
					}
				}
			}
			return (dateList.size() > 0) ? new JRIBatisDataSource(dateList)
					: new JRIBatisDataSource();
		} else {
			return new JRIBatisDataSource();
		}
	}

}