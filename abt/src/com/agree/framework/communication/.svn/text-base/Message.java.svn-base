package com.agree.framework.communication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;

@SuppressWarnings("rawtypes")
public class Message extends HashMap

{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5916700604618160554L;
	private static final String DefaultCategory = "default";
	private Map categories = new HashMap();

	@SuppressWarnings("unchecked")
	public Message() {
		this.categories.put("Content", new LinkedMap());
	}

	public Object get(Object key) {
		return (Map) this.categories.get(key);
	}

	public String getString(String key) {
		return ((String) get(DefaultCategory, key));
	}

	public Object get(String category, String key) {
		Map dataMap = (Map) this.categories.get(category);
		if (dataMap == null) {
			return null;
		}
		return dataMap.get(key);
	}

	public String getString(String category, String key) {
		Map dataMap = (Map) this.categories.get(category);
		if (dataMap == null) {
			return null;
		}
		return ((String) dataMap.get(key));
	}

	public void put(String key, Object value) {
		put(DefaultCategory, key, value);
	}

	@SuppressWarnings("unchecked")
	public void put(String category, String key, Object value) {
		Map dataMap = (Map) this.categories.get(category);
		if (dataMap == null) {
			dataMap = new LinkedMap();
			this.categories.put(category, dataMap);
		}
		dataMap.put(key, value);
	}

	public void clear() {
		this.categories.clear();
	}

	public Collection findAllCategories() {
		return this.categories.keySet();
	}

	public Collection findAllKeysByCategory(String category) {
		Map dataMap = (Map) this.categories.get(category);
		if (dataMap == null) {
			return new ArrayList();
		}
		return dataMap.keySet();
	}

	public Boolean isElementExist(String category, String key) {
		Collection col = findAllKeysByCategory(category);
		return col.contains(key);
	}

}
