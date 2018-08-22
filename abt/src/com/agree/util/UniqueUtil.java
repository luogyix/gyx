package com.agree.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UniqueUtil {
	private static  Lock LOCK ;

	private static long lastTime = System.currentTimeMillis();
	private static UniqueUtil uniqueInstance;
	private String primaryKey="";

	/**
	* a time (as returned by {@link System#currentTimeMillis()}) at which
	* the VM that this UID was generated in was alive
	* @serial
	*/
	private  long time;

	/**
	* Generates a UID that is unique over time with
	* respect to the host that it was generated on.
	*/
	public static UniqueUtil getInstance(){
		if(uniqueInstance==null)
			uniqueInstance= new UniqueUtil();
		return uniqueInstance;
		
	}
	public UniqueUtil() {
		
		 LOCK = new ReentrantLock();
	}
	
	
	public String getPrimaryKey(String tableNo){
		LOCK.lock();
		try {
					boolean done = false;
					while (!done) {
						long now = System.currentTimeMillis();
						if (now == lastTime) {
						// pause for a second to wait for time to change
							try {
								Thread.sleep(1);
							}catch (java.lang.InterruptedException e) {
							} // ignore exception
						continue;
							}else {
							lastTime = now;
							done = true;
						}
					}
				
			time = lastTime;
			primaryKey=tableNo+time;
		}finally {
		LOCK.unlock();
		}
		return primaryKey;
	}
	
	public static String getRcid(String shortName){
		return UniqueUtil.getInstance().getPrimaryKey(shortName);
	}

}
