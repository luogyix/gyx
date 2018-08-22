package com.agree.util;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

import com.agree.framework.exception.AppErrorcode;
import com.agree.framework.exception.AppException;
/**
 * 
* @ClassName: ActionUtil 
* @Description: TODO 
* @author haoruibing 
* @date 2011-7-29 上午10:30:48
 */
public class ActionUtil {
	private static Log logger = LogFactory.getLog(ActionUtil.class);	
	/**
	 * 把前台来的信息合并到form信息中
	 */
	public static  Object fillFormBean(String classpath,HttpServletRequest request)  
	{
		Object objInstance=null;
		try 
		{
			objInstance = Class.forName(classpath).newInstance();
			Field field;
			String fieldName;
			String fieldValue;
			int len; 
			len = objInstance.getClass().getDeclaredFields().length; 
			for (int i = 0; i < len; i++) 
			{
				field = objInstance.getClass().getDeclaredFields()[i];
				fieldName  =  field.getName();
				fieldValue = (String)request.getParameter(fieldName);
				if (fieldValue != null) 
				{
					try 
					{
						setFieldValue(field, objInstance, fieldValue);
						
					} catch (IllegalAccessException e) 
					{
						logger.info("设置对象字段属性,setFieldValue出错");
					}
				}
			}
		} catch (InstantiationException e) 
		{
			logger.info("无法创造"+classpath+"对象");
		} catch (IllegalAccessException e) 
		{
			logger.info("包权限问题，请尝试public 所以无法创造"+classpath+"对象");
			
		} catch (ClassNotFoundException e) 
		{
			logger.info("无法找到"+classpath+"类");
		}	
		return objInstance;
	}
	 /**
	   * 设置对象字段属性
	   *
	   * @param field      属性字段对象
	   * @param object     对象
	   * @param fieldValue 属性字段值
	   * @throws IllegalAccessException
	   */
	  private static void setFieldValue(Field field, Object object, String fieldValue) throws IllegalAccessException 
	  {
	    String fieldType;
	    fieldType = field.getType().getName();
	    field.setAccessible(true);
	    try 
	    {
	      if (fieldType.equals("java.lang.String")) 
	      {
	        field.set(object, fieldValue);
	      }
	      else if (fieldType.equals("java.lang.Integer")) 
	      {
	        field.set(object, Integer.valueOf(fieldValue));
	      }
	      else if (fieldType.equals("java.lang.Long")) 
	      {
	        field.set(object, Long.valueOf(fieldValue));
	      }
	      else if (fieldType.equals("java.lang.Float")) 
	      {
	        field.set(object, Float.valueOf(fieldValue));
	      }
	      else if (fieldType.equals("java.lang.Double")) 
	      {
	        field.set(object, Double.valueOf(fieldValue));
	      }
	      else if (fieldType.equals("java.math.BigDecimal")) 
	      {
	        field.set(object, new BigDecimal(fieldValue));
	      }
	      else if (fieldType.equals("java.util.Date")) 
	      {
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        formatter.setLenient(false);
	        field.set(object, formatter.parse(fieldValue));
	      }
	      else if (fieldType.equals("java.sql.Date"))
	      {
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        formatter.setLenient(false);
	        Date date = formatter.parse(fieldValue);
	        field.set(object, new java.sql.Date(date.getTime()));
	      }
	      else if (fieldType.equals("java.lang.Boolean")) 
	      {
	        field.set(object, Boolean.valueOf(fieldValue));
	      }
	      else if (fieldType.equals("java.lang.Byte")) 
	      {
	        field.set(object, Byte.valueOf(fieldValue));
	      }
	      else if (fieldType.equals("java.lang.Short"))
	      {
	        field.set(object, Short.valueOf(fieldValue));
	      }
	    }
	    catch (NumberFormatException ex) {
	      field.set(object, null);
	    }
	    catch(Exception e){
	      field.set(object, null);
	    }
	  }	
	  /**
	   * 截取key值
	   * @param jsonObj
	   * @param key
	   */
	  public static void getDictKey(JSONObject jsonObj,String key) {
			String serviceattr=(String) jsonObj.get(key);
			if(!StringUtil.isEmpty(serviceattr)){
				String[] str= serviceattr.split("-");
				jsonObj.put(key, str[0]);
			}
	}
	  /**
	   * 模糊查询
	   * @param jsonObj
	   * @param para
	   */
	 public static void setDimPara(JSONObject jsonObj,String para) {
			String str = (String)jsonObj.get(para);
			if(!StringUtil.isEmpty(str)){
				jsonObj.put(para,"%"+str+"%" );
			}
	}
	 /**
	  * 
	  * @Title: setSpecPara 
	  * @Description:         根据需要设置特殊处理的参数,例如like,!=,>,<,>=,<=,in,not in处理过的参数值为Map,in的值必须为以','分割的字符串，如"a,b,c"
	  * @param @param jsonObj 参数集合
	  * @param @param para    需要处理的参数
	  * @param @param type    连接符，包括like,!=,>,<,>=,<=,in,not in
	  * @return void          返回类型 
	  * @throws AppException 
	  * @throws
	  */
	 public static void setSpecPara(JSONObject jsonObj,String para,String type) throws AppException{
		 if(StringUtil.isStringEmpty(type)||!"like,!=,>,<,>=,<=,in,not in".contains(type)){
			 throw new AppException(AppErrorcode.INVALIDTYPE);
		 }
		 JSONObject paraMap=new JSONObject();
		 String value = (String)jsonObj.get(para);
		 if(!StringUtil.isEmpty(value)){
			 paraMap.put(type, value);
			 jsonObj.put(para,paraMap);
		 }
	 }
	 /**
	  * 
	  * @Title: setSpecPara 
	  * @Description:         根据需要设置特殊处理的参数,针对某些需要替换参数集合的key值的方法，或者需要给参数加上表的别名等等，如begindate,enddate替换为**.date
	  * @param @param jsonObj 参数集合
	  * @param @param para    需要处理的参数
	  * @param @param para    更新的键值
	  * @param @param type    连接符，包括like,!=,>,<,>=,<=,in,not in
	  * @return void          返回类型 
	  * @throws AppException 
	  * @throws
	  */
	 public static void setSpecPara(JSONObject jsonObj,String para,String key,String type) throws AppException{
		 if(StringUtil.isStringEmpty(type)||!"like,!=,>,<,>=,<=,in,not in".contains(type)){
			 throw new AppException(AppErrorcode.INVALIDTYPE);
		 }
		 JSONObject paraMap=new JSONObject();
		 String value = (String)jsonObj.get(para);
		 if(!StringUtil.isEmpty(value)){
			 paraMap.put(type, value);
			 jsonObj.put(key,paraMap);
			 jsonObj.remove(para);
		 }
	 }
	 
	 /**
		 * 
		 * @Title: isKeyUseful 
		 * @Description: 判断参数是否有用
		 * @param @param key
		 * @param @param val
		 * @param @return    参数 
		 * @return boolean    返回类型 
		 * @throws
		 */
		public static boolean isKeyUseful(Object key, Object val) {
			return val!=null&&(!val.equals(""))&&!(key.toString().equals("start"))&&(!key.toString().equals("limit"));
		}
		  /** 
         * 数组转换为List
         * 
         * @param arr 数组 
         * @return List 
         */ 
        public static List<Object> array2List(Object[] arr) { 
                List<Object> list = new ArrayList<Object>(); 
                if (arr == null) return list; 
                list = Arrays.asList(arr); 
                return list; 
        } 

	 
        /**
         * 将实体类转成Map
         * @param obj
         * @return
         */
	public static Map<String,Object> ConvertObjToMap(Object obj) throws Exception{
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (obj == null)
			return null;
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				try {
					Field f = obj.getClass().getDeclaredField(fields[i].getName());
					f.setAccessible(true);
					Object o = f.get(obj);
					reMap.put(fields[i].getName(), o);
				} catch (NoSuchFieldException e) {
					logger.error(e.getMessage(),e);
					throw e;
				} catch (IllegalArgumentException e) {
					logger.error(e.getMessage(),e);
					throw e;
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(),e);
					throw e;
				}
			}
		} catch (SecurityException e) {
			logger.error(e.getMessage(),e);
			throw e;
		}
		return reMap;
	}
	 
	 
}
