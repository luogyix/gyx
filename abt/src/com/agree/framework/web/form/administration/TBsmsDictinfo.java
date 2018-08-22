package com.agree.framework.web.form.administration;

import java.io.Serializable;

import com.agree.framework.web.form.base.BaseForm;

public class TBsmsDictinfo extends BaseForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TBsmsDictinfo(){
	}
	
	public TBsmsDictinfo(String item,String dicttype,String dicttypedesc,String dictvalue,String dictvaluedesc,
			String enable,String editable,String remark){
		this.item = item;
		this.dicttype = dicttype;
		this.dicttypedesc = dicttypedesc;
		this.dictvalue = dictvalue;
		this.dictvaluedesc = dictvaluedesc;
		this.enable = enable;
		this.editable = editable;
		this.remark = remark;
	}
	
	private String item;
	private String dicttype;
	private String dicttypedesc;
	private String dictvalue;
	private String dictvaluedesc;
	private String enable;
	private String editable;
	private String remark;
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDicttype() {
		return dicttype;
	}

	public void setDicttype(String dicttype) {
		this.dicttype = dicttype;
	}

	public String getDicttypedesc() {
		return dicttypedesc;
	}

	public void setDicttypedesc(String dicttypedesc) {
		this.dicttypedesc = dicttypedesc;
	}

	public String getDictvalue() {
		return dictvalue;
	}

	public void setDictvalue(String dictvalue) {
		this.dictvalue = dictvalue;
	}

	public String getDictvaluedesc() {
		return dictvaluedesc;
	}

	public void setDictvaluedesc(String dictvaluedesc) {
		this.dictvaluedesc = dictvaluedesc;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
