package com.agree.framework.web.form.base;

import java.io.Serializable;
import java.util.Comparator;

import com.agree.framework.web.form.administration.Module;
@SuppressWarnings({"rawtypes"})
public class FlowInfoComparator implements Comparator,Serializable {

	private static final long serialVersionUID = -5046042464197820707L;

	public int compare(Object o1, Object o2) {
		Module mo1=(Module)o1;
		Module mo2=(Module)o2;
		if(mo1.getModulelevel()>mo2.getModulelevel()){
			return 1;
		}else{
			return -1;
		}
	}

}
