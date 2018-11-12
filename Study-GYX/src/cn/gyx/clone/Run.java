package cn.gyx.clone;

import org.junit.Test;

public class Run {
	@Test
	public void testRun(){
		User user = new User("·öËÕ", "1", "35");
		User user1 = user;
		try {
			user1 = (User) user.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			user1 = new User("", "", "");
			e.printStackTrace();
		}
		user1.setName("ºú°²");
		System.out.println("user£º"+user.toString()+"\r\n"+"user1£º"+user1.toString());
	}
}
