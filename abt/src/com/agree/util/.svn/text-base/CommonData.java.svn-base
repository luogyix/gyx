package com.agree.util;

import java.io.Serializable;

public class CommonData implements Serializable {
	private static final long serialVersionUID = 6412545139551785028L;
	private String dataValue;
	private String dataName;

	public CommonData() {
	}

	public CommonData(String dataValue, String dataName) {
		this.dataValue = dataValue;
		this.dataName = dataName;
	}

	public String getDataValue() {
		return this.dataValue;
	}

	public String getDataName() {
		return this.dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[dataName=").append(this.dataName);
		buffer.append(",dataValue=").append(this.dataValue);
		buffer.append("]");
		return buffer.toString();
	}
}
