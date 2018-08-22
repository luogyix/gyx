package com.agree.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.collections.SequencedHashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@SuppressWarnings("deprecation")
public class XmlConfiguration
{
  @SuppressWarnings("rawtypes")
private Map categories = new SequencedHashMap();

  public XmlConfiguration(String configName)
    throws ParserConfigurationException, SAXException, IOException
  {
    InputStream is = XmlConfiguration.class.getResourceAsStream("/resources/pack/" + configName + "_config.xml");
    SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
    parser.parse(is, new ConfigDefaultHandler(this.categories));
  }

  @SuppressWarnings("rawtypes")
public String getProperty(String category, String propName)
  {
    Map values = (Map)this.categories.get(category);
    Object value = values.get(propName);
    return ((value == null) ? "" : (String)value);
  }

  @SuppressWarnings("rawtypes")
public Collection listAllCategories()
  {
    return getListBySet(this.categories.keySet());
  }

  @SuppressWarnings("rawtypes")
public Collection listAllProperties(String categoryName)
  {
    Map values = (Map)this.categories.get(categoryName);
    if (values == null) {
      return new ArrayList();
    }
    return getListBySet(values.keySet());
  }

  @SuppressWarnings("rawtypes")
public Map getPropertiesMap(String categoryName)
  {
    return ((Map)this.categories.get(categoryName));
  }

  @SuppressWarnings("rawtypes")
private List<Object> getListBySet(Set set)
  {
    List<Object> list = new ArrayList<Object>();
    for (Iterator itr = set.iterator(); itr.hasNext(); ) {
      Object o = itr.next();
      list.add(o);
    }
    return list;
  }

  private class ConfigDefaultHandler extends DefaultHandler
  {
    @SuppressWarnings("rawtypes")
	private Map categories;
    @SuppressWarnings("rawtypes")
	private Map currentValues;

    @SuppressWarnings("rawtypes")
	ConfigDefaultHandler(Map categories)
    {
      this.categories = categories;
    }

    @SuppressWarnings({ "unchecked" })
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if (qName.equals("category")) {
        this.currentValues = new SequencedHashMap();
        this.categories.put(attributes.getValue("name"), this.currentValues);
      } else if (qName.equals("property")) {
        String name = attributes.getValue("name");
        String value = attributes.getValue("value");
        this.currentValues.put(name, value);
      }
    }
  }
}
