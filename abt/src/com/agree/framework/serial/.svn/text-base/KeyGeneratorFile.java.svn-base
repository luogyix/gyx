package com.agree.framework.serial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.agree.util.DateUtil;

/**
 * <DL>
 * <DT><B> 文件流水号发生器. </B></DT>
 * <p>
 * <DD>通过序列化流水号到文件中来永久存储流水号， 然后通过反序列化来获得流水号</DD>
 * </DL>
 * <p>
 * 
 * <DL>
 * <DT><B>使用范例</B></DT>
 * <p>
 * <DD>使用范例说明</DD>
 * </DL>
 * <p>
 * 
 */
public class KeyGeneratorFile extends AbsrtactKeyGenerator {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(KeyGeneratorFile.class);

	/**
	 * 自定义字段serialVersionUID
	 */
	private static final long serialVersionUID = 1L;


	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.agree.afe.resource.serial.AfeKeyGenerator#save()
	 */
	public void save() throws KeyException {
		Assert.notNull(_afeKey, "键值对象不能为空");
		ObjectOutput out = null;
		try {
			_afeKey.setKey(Long.toString(nextKey));
			date = new Date();
			_afeKey.setDate(date);
			out = new ObjectOutputStream(new FileOutputStream(_file));
			out.writeObject(_afeKey);
		} catch (FileNotFoundException e) {
			throw new KeyException("保存流水号到文件失败", e);
		} catch (IOException e) {
			throw new KeyException("保存流水号到文件失败", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
	}

	/** 序列化的键值类 */
	protected KeyInfo _afeKey;

	/** 序列化文件 */
	protected File _file;

	/** 序列化文件名 */
	protected String fileName;

	/** 序列化文件路径 */
	protected String filePath;

	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * @param fileName
	 *            The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void doStart() throws Exception {
		try {
			/** config目录不存时,自动创建 */
			com.agree.util.FileUtil.mkDirectory(filePath);
			// 定义序列号文件实例
			_file = new File(filePath+fileName);
			if (formatStr == null || formatStr.length() == 0) {
				formatStr = "'"+DateUtil.formatDate(new Date(), "yyyyMMdd")+"'00000000";
			}
			setFormat(new DecimalFormat(formatStr));
			if (_file.exists()) {
				ObjectInputStream in = null;
				try {
					in = new ObjectInputStream(new FileInputStream(_file));
					_afeKey = (KeyInfo) in.readObject();
					key = Long.parseLong(_afeKey.getKey());
				} catch (FileNotFoundException e) {
					throw new KeyException(fileName + "文件不存在", e);
				} catch (IOException e) {
					// throw new KeyException("初始化流水号发生器配置失败", e);
					// 若文件存在，此处发生异常，则可能是流水号文件大小为0（一般在磁盘没有空间关闭AFE会产生此问题） modified
					Log log = LogFactory.getLog(KeyInfo.class);
					if (log.isErrorEnabled()) {
						log.error("读取流水号文件失败，流水号被重新初始化");
					}
					key = minValue;
					_afeKey = new KeyInfo();
				} catch (ClassNotFoundException e) {
					throw new KeyException("初始化流水号发生器配置失败", e);
				} catch (Exception e) {
					throw new KeyException("初始化流水号发生器配置失败", e);
				} finally {
					if (in != null) {
						in.close();
					}
				}
			} else {
				key = minValue;
				_afeKey = new KeyInfo();
			}
			if (key >= maxValue) { // 增加对值的判断
				key = minValue;
			}
			nextKey = key + interval;
			if (nextKey > maxValue) {
				nextKey = maxValue;
			}
			save();
		} finally {
			// log
		}
	}

	public void doStop() throws Exception {
		nextKey = key;
		save();
	}

	public void doFail() {
		logger.fatal("");
	}

}

