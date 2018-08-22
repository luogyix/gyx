package com.agree.framework.dao.core;

import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;

/**
 * 
 * @ClassName: AppOracleSequenceForSv 
 * @Description: 获取Sequence类
 * @company agree   
 * @author haoruibing   
 * @date 2011-7-29 下午02:21:31 
 *
 */
public class AppOracleSequenceForSv extends OracleSequenceMaxValueIncrementer {
	private int sequenceLength;

	/**
	 * Description:获取下一个主键值，主键数据类型为String
	 */
	public String nextStringValue(){
		String nextValue = super.nextStringValue();
		int length = sequenceLength - nextValue.length();
		if(length != 0){
			StringBuffer buf = new StringBuffer();
			for(int i=0;i<length;i++){
				buf.append('0');
			}
			return buf.toString() + nextValue;
		}else{
			return nextValue;
		}
	}
	public int getSequenceLength() {
		return sequenceLength;
	}


	public void setSequenceLength(int sequenceLength) {
		this.sequenceLength = sequenceLength;
	}
}
