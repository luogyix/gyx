package com.agree.framework.serial;


import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * <DL>
 * <DT><B> 流水号产生器异常. </B></DT>
 * <p>
 * <DD>
 * 记录在流水号产生器中发生的异常</DD>
 * </DL>
 * <p>
 * 
 * <DL>
 * <DT><B>使用范例</B></DT>
 * <p>
 * <DD>
 * 使用范例说明</DD>
 * </DL>
 * <p>
 * 
 */
public class KeyException extends Exception {

	/**
	 * 自定义字段serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String _message;

	/**
	 * 构造器
	 * 
	 */
	public KeyException() {
		super();
	}

	/**
	 * 构造器
	 * 
	 * @param message
	 */
	public KeyException(String message) {
		super(message);
	}

	/**
	 * 构造器
	 * 
	 * @param cause
	 */
	public KeyException(Throwable cause) {
		// super(cause);
		_exception = cause;
	}

	/**
	 * 构造器
	 * 
	 * @param message
	 * @param cause
	 */
	public KeyException(String message, Throwable cause) {
		// super(message, cause);
		_message = message;
		_exception = cause;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
	 * 
	 * @pre s != null
	 */
	public void printStackTrace(PrintStream s) {
		synchronized (s) {
			if (_exception != null) {
				s.println(this.getClass().toString() + ":" + _message);
				_exception.printStackTrace(s);
			} else {
				super.printStackTrace(s);
			} // if
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
	 */
	public void printStackTrace(PrintWriter s) {
		synchronized (s) {
			if (_exception != null) {
				s.println(this.getClass().toString() + ":" + _message);
				_exception.printStackTrace(s);
			} else {
				super.printStackTrace(s);
			} // if
		}
	}

	public Throwable getException() {
		return _exception;
	}

	private Throwable _exception;

}