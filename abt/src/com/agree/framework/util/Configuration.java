package com.agree.framework.util;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {
	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
	private XmlConfiguration conf = null;

	public static Configuration getInstance() {
		return getInstance("arzan");
	}

	public static Configuration getInstance(String configName) {
		return new Configuration(configName);
	}

	private Configuration(String configName) {
		try {
			this.conf = new XmlConfiguration(configName);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	public String getProperty(String categoryName, String propertyName) {
		return this.conf.getProperty(categoryName, propertyName);
	}

	@SuppressWarnings("rawtypes")
	public Collection listAllProperties(String categoryName) {
		return this.conf.listAllProperties(categoryName);
	}

	@SuppressWarnings("rawtypes")
	public Map getPropertiesMap(String categoryName) {
		return this.conf.getPropertiesMap(categoryName);
	}

	@SuppressWarnings("rawtypes")
	public Collection listAllCategories() {
		return this.conf.listAllCategories();
	}
}
