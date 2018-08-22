/**
 * 
 */
package com.agree.framework.web.form.administration;

import java.io.Serializable;
import java.util.Comparator;

public class Dictionary implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String    dictType;

	private String    dictTypeDesc;

	private String   dictValue;

	private String    dictValueDesc;
	
	private String    cleanDesc;
	
	public String getCleanDesc() {
		return cleanDesc;
	}

	public void setCleanDesc(String cleanDesc) {
		this.cleanDesc = cleanDesc;
	}
	private String    locale;

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictTypeDesc() {
		return dictTypeDesc;
	}

	public void setDictTypeDesc(String dictTypeDesc) {
		this.dictTypeDesc = dictTypeDesc;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getDictValueDesc() {
		return dictValueDesc;
	}

	public void setDictValueDesc(String dictValueDesc) {
		this.dictValueDesc = dictValueDesc;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dictValue == null) ? 0 : dictValue.hashCode());
		result = prime * result
				+ ((dictValueDesc == null) ? 0 : dictValueDesc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Dictionary other = (Dictionary) obj;
		if (dictValue == null) {
			if (other.dictValue != null)
				return false;
		} else if (!dictValue.equals(other.dictValue))
			return false;
		if (dictValueDesc == null) {
			if (other.dictValueDesc != null)
				return false;
		} else if (!dictValueDesc.equals(other.dictValueDesc))
			return false;
		return true;
	}
	Comparator<Dictionary> comparator = new Comparator<Dictionary>(){
		   public int compare(Dictionary d1, Dictionary d2) {
				return d1.dictValue.compareTo(d2.dictValue);
		   }
	};

	public Comparator<Dictionary> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<Dictionary> comparator) {
		this.comparator = comparator;
	}

}
