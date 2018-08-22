package com.agree.framework.communication.natp;

/*
 * Copyright(C) 2006 Agree Tech, All rights reserved.
 * 
 * Created on 2006-2-13   by Xu Haibo
 */


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <DL>
 * <DT><B> Natp数据分析类 </B></DT>
 * <p>
 * <DD>
 * 
 * 用于natp协议中包数据中自定义字段的数据处理，具体功能包括： 1、拼包 2、设置字段值 3、拆包 4、读取字段值
 * 5、读取一组同名字段值，排列顺序按照数据流的顺序
 * 
 * <p>
 * 重名字段补充说明： 1、读取重名字段的时候可以使用脚标，格式如'name[1]', 'name[2]'；也可以使用get(String name, int
 * index) 2、读取单一字段也可以使用[1]脚标，但是重名字段不能通过无脚标形式读取 3、脚标从1开始计算
 * 4、设置重名字段不需要加脚标，否则按照不同的字段处理 5、拼包后的内容不包含脚标
 * 
 * </DD>
 * </DL>
 * <p>
 * 
 * <DL>
 * <DT><B>使用范例</B></DT>
 * <p>
 * <DD>见单元测试代码</DD>
 * </DL>
 * <p>
 * 
 * @author 徐海波
 * @author 赞同科技
 * @version $Revision: 1.7.2.1 $ $Date: 2010/04/01 02:29:43 $
 */
public class NatpHelper {
	/** 数据写出流 */
	private ByteArrayOutputStream bout = new ByteArrayOutputStream();

	/** 数据写出流 */
	private DataOutputStream dout = new DataOutputStream(bout);

	/** 变量名－值映射表, 保证输出的节点值按照输入顺序排序 */
	@SuppressWarnings("rawtypes")
	private HashMap vars = new LinkedHashMap();

	@SuppressWarnings("rawtypes")
	public HashMap getVars() {
		return vars;
	}

	@SuppressWarnings("rawtypes")
	public void setVars(HashMap vars) {
		this.vars = vars;
	}

	/** 重复变量名－个数映射表 */
	@SuppressWarnings("rawtypes")
	private HashMap repeatVars = new HashMap();

	/**
	 * 获取指定名称的NATP值
	 * 
	 * @param name
	 *            指定名称，如果有重名，可以使用'name[1]', 'name[2]'的表示，下标从1开始
	 * @return 指定名称的NATP变量值
	 * @throws Exception
	 */
	public byte[] get(String name) throws Exception {
		if (vars.containsKey(name)) {
			return (byte[]) vars.get(name);
		} else if (name.endsWith("[1]")) {
			String realName = name.substring(0, name.indexOf("[1]"));
			return get(realName);
		}
		throw new Exception("变量'" + name + "'不存在");
	}

	/**
	 * 判断指定的natp变量是否存在
	 * 
	 * @param name
	 * @return true or false
	 */
	public boolean isAvalible(String name) {
		if (vars.containsKey(name))
			return true;
		else if (name.endsWith("[1]")) {
			String realName = name.substring(0, name.indexOf("[1]"));
			return isAvalible(realName);
		}
		return false;
	}

	/**
	 * 获取指定名称，指定索引的NATP值
	 * 
	 * @param name
	 *            指定名称
	 * @param index
	 *            下标从1开始
	 * @return 指定名称的NATP变量值
	 * @throws Exception
	 */
	public byte[] get(String name, int index) throws Exception {
		String realName = name + "[" + index + "]";
		if (vars.containsKey(realName)) {
			return (byte[]) vars.get(realName);
		}
		throw new Exception("变量'" + realName + "'不存在");
	}

	/**
	 * 获取所有的变量名，如果有重名，会返回'name[1]', 'name[2]'
	 * 
	 * @return 所有的变量名，如果有重名，会返回'name[1]', 'name[2]'
	 */
	@SuppressWarnings("rawtypes")
	public String[] getNames() {
		String[] names = new String[vars.size()];
		int i = 0;
		for (Iterator iter = vars.entrySet().iterator(); iter.hasNext();) {
			names[i++] = (String) ((Map.Entry) iter.next()).getKey();
		}
		return names;
	}

	/**
	 * 初始化该帮助类，在拆包和拼包之前需要这个操作
	 */
	public void init() {
		vars.clear();
		repeatVars.clear();
		bout.reset();
	}

	public byte[] packData() throws Exception {
		return packData("2.0");
	}

	/**
	 * 拼包，把已经设置在其中的变量转换为NATP格式数据
	 * 
	 * @return NATP格式数据
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public byte[] packData(String version) throws Exception {

		try {
			for (Iterator iter = vars.entrySet().iterator(); iter.hasNext();) {
				String element = (String) ((Map.Entry) iter.next()).getKey();
				byte[] value = get(element);
				// 2005-01-12 判断是否为同名的natp的节点，然后去处同名节点的后面的'[*]'
				if (element.lastIndexOf("[") > 0
						&& repeatVars.containsKey(element.substring(0, element
								.lastIndexOf("["))))
					element = element.substring(0, element.lastIndexOf("["));
				byte[] name = element.getBytes();
				if ("3.0".equals(version)) {
					/** 名称长度定为一个字节 */
					dout.writeByte(name.length);
					dout.write(name);
					/** 数据长度定为四个字节 */
					dout.writeInt(value.length);
					dout.write(value);
				} else {
					dout.writeShort(name.length);
					dout.write(name);
					dout.writeShort(value.length);
					dout.write(value);
				}

			}
			byte[] ret = bout.toByteArray();
			return ret;
		} catch (IOException e) {
			throw new Exception("转换数据为NATP格式异常", e);
		} finally {
			bout.close();
			dout.close();
		}
	}

	/**
	 * 设置指定名称的变量，重名则设置为'name[1]', 'name[2]'
	 * 
	 * @param name
	 *            变量名
	 * @param value
	 *            变量值
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void set(String name, byte[] value) {
		if (repeatVars.containsKey(name)) {
			int index = ((Integer) repeatVars.get(name)).intValue();
			repeatVars.put(name, new Integer(++index));
			vars.put(name + "[" + index + "]", value);
			return;
		}
		if (vars.containsKey(name)) {
			repeatVars.put(name, new Integer(2));
			byte[] old = (byte[]) vars.get(name);
			// vars.put(name+"[1]", old);
			// vars.put(name+"[2]", value);
			// vars.put(name+"[2]", value); //edited by wang_wang
			// 将第一个重复的name添加上索引，变为 name[1].( or name[0]? )
			HashMap hm = new LinkedHashMap();
			String key;
			for (Iterator iter = vars.entrySet().iterator(); iter.hasNext();) {
				key = (String) ((Map.Entry) iter.next()).getKey();
				if (key.equals(name)) {
					hm.put(name + "[1]", old);
				} else {
					hm.put(key, vars.get(key));
				}
			}
			vars.clear();
			vars.putAll(hm);
			vars.put(name + "[2]", value);
			hm.clear();
		} else
			vars.put(name, value);
	}

	/**
	 * 解包，把NATP数据中的数据转换为内部数据
	 * 
	 * @param data
	 *            NATP数据
	 * @param start
	 *            起始位置，应该>=0
	 * @param len
	 *            长度，为0则不限长度
	 * @throws Exception
	 */
	public void unpackData(byte[] data, int start, int len, String version)
			throws Exception {
		// 为了不影响解包结果,清理结果的容器
		vars.clear();
		repeatVars.clear();

		int startPos = start >= 0 ? start : 0;
		// byte[] tmp = new byte[len>0?len:(data.length-startPos)];
		// System.arraycopy(data, startPos, tmp, 0,
		// len>0?len:(data.length-startPos));
		ByteArrayInputStream bin = new ByteArrayInputStream(data, startPos,
				len > 0 ? len : (data.length - startPos));
		DataInputStream din = new DataInputStream(bin);
		try {
			while (din.available() > 0) {
				if ("3.0".equals(version)) {
					/** 名称长度定为一个字节 */
					int nLen = din.readByte();
					byte[] byName = new byte[nLen];
					din.read(byName);
					String sName = new String(byName);
					/** 数据长度定为四个字节 */
					nLen = din.readInt();
					byte[] byValue = new byte[nLen];
					din.read(byValue);
					set(sName, byValue);
				} else {
					int nLen = din.readShort();
					byte[] byName = new byte[nLen];
					din.read(byName);
					String sName = new String(byName);
					nLen = din.readShort();
					if (nLen < 0) {
						nLen = nLen + 65536;
					}
					byte[] byValue = new byte[nLen];
					din.read(byValue);
					
					set(sName, byValue);
				}
			}
			bin.close();
			din.close();
		} catch (IOException e) {
			// 捕获异常
			throw new Exception(e);
		}
	}

	/**
	 * 解包，把NATP数据中的数据转换为内部数据
	 * 
	 * @param data
	 *            NATP数据
	 * @throws Exception
	 */
	public void unpackData(byte[] data) throws Exception {
		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream(bin);
		try {
			while (din.available() > 0) {
				int nLen = din.readShort();
				byte[] byName = new byte[nLen];
				din.read(byName);
				String sName = new String(byName);
				nLen = din.readShort();
				if (nLen < 0) {
					nLen = nLen + 65536;
				}
				byte[] byValue = new byte[nLen];
				din.read(byValue);
				set(sName, byValue);
			}
		} catch (IOException e) {
			// 捕获异常
			throw new Exception(e);
		} finally {
			bin.close();
			din.close();
		}
	}

//	public static  void main(String[] arg) throws Exception{
//		NatpHelper helper = new NatpHelper();
//		byte [] b = {0, 17, 72, 95, 115, 116, 97, 114, 116, 95, 116, 105, 109, 101, 115, 116, 97, 109, 112, 0, 23, 50, 48, 49, 49, 45, 48, 52, 45, 48, 56, 32, 48, 48, 58, 49, 57, 58, 48, 50, 46, 52, 49, 48, 0, 11, 72, 95, 83, 101, 114, 118, 105, 99, 101, 73, 100, 0, 4, 116, 48, 48, 49, 0, 12, 72, 95, 67, 104, 97, 110, 110, 101, 108, 83, 101, 113, 0, 8, 50, 48, 49, 50, 48, 51, 49, 50, 0, 13, 72, 95, 67, 104, 97, 110, 110, 101, 108, 68, 97, 116, 101, 0, 8, 50, 48, 49, 50, 48, 51, 49, 50, 0, 9, 116, 114, 97, 110, 115, 99, 111, 100, 101, 0, 4, 116, 48, 48, 49, 0, 7, 115, 121, 115, 116, 105, 109, 101, 0, 10, 50, 48, 49, 49, 45, 48, 52, 45, 48, 56, 0, 6, 72, 95, 78, 111, 100, 101, 0, 9, 49, 50, 51, 52, 53, 54, 55, 56, 57, 0, 8, 83, 69, 81, 85, 69, 78, 67, 69, 0, 8, 48, 48, 48, 48, 48, 54, 52, 57, 0, 11, 72, 95, 67, 104, 97, 110, 110, 101, 108, 73, 100, 0, 2, 48, 50, 0, 13, 72, 95, 67, 104, 97, 110, 110, 101, 108, 84, 105, 109, 101, 0, 6, 49, 48, 48, 50, 50, 53, 0, 8, 115, 114, 118, 95, 105, 110, 102, 111, 0, -20, 123, 39, 84, 82, 65, 78, 83, 95, 70, 76, 65, 71, 39, 58, 32, 39, 48, 48, 39, 44, 32, 39, 83, 69, 82, 86, 73, 67, 69, 95, 68, 69, 83, 67, 39, 58, 32, 39, 116, 116, 116, 39, 44, 32, 39, 73, 78, 84, 69, 82, 70, 65, 67, 69, 95, 75, 69, 89, 39, 58, 32, 39, 116, 48, 48, 49, 39, 44, 32, 39, 77, 79, 68, 95, 67, 79, 68, 69, 39, 58, 32, 39, 97, 98, 116, 109, 39, 44, 32, 39, 83, 69, 82, 86, 73, 67, 69, 95, 77, 79, 68, 69, 39, 58, 32, 39, 48, 48, 39, 44, 32, 39, 83, 69, 82, 86, 73, 67, 69, 95, 83, 84, 65, 84, 85, 83, 39, 58, 32, 39, 48, 48, 39, 44, 32, 39, 84, 82, 65, 68, 69, 95, 67, 79, 68, 69, 39, 58, 32, 39, 116, 48, 48, 49, 39, 44, 32, 39, 84, 82, 65, 78, 83, 95, 68, 69, 83, 84, 95, 67, 72, 65, 78, 78, 69, 76, 39, 58, 32, 78, 111, 110, 101, 44, 32, 39, 83, 69, 82, 86, 73, 67, 69, 95, 73, 68, 39, 58, 32, 39, 116, 48, 48, 49, 39, 44, 32, 39, 73, 78, 84, 69, 82, 70, 65, 67, 69, 95, 67, 72, 69, 67, 75, 95, 70, 76, 65, 71, 39, 58, 32, 39, 48, 49, 39, 125, 0, 8, 72, 95, 84, 101, 108, 108, 101, 114, 0, 12, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 0, 12, 116, 101, 109, 112, 108, 97, 116, 101, 99, 111, 100, 101, 0, 4, 97, 98, 116, 109};
//		helper.unpackData(b,0,0,"1.0");
//	}
}
