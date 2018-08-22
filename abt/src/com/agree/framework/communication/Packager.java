/**   
 * @Title: Packer.java 
 * @Package com.agree.framework.communication 
 * @Description: TODO 
 * @company agree   
 * @author haoruibing   
 * @date 2011-10-11 下午05:33:44 
 * @version V1.0   
 */ 

package com.agree.framework.communication;


/** 
 * @ClassName: Packer 
 * @Description: TODO
 * @company agree   
 * @author haoruibing   
 * @date 2011-10-11 下午05:33:44 
 *  
 */

public interface Packager {
	public  Message unpack(String msg);
    

	public  String pack(Message paramMessage);
}
