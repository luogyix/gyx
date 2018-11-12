package cn.gyx.clone;

import org.junit.Test;

public class Run {
	@Test
	public void testRun(){
		User user = new User("����", "1", "35");
		User user1 = user;
		try {
			user1 = (User) user.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			user1 = new User("", "", "");
			e.printStackTrace();
		}
		user1.setName("����");
		System.out.println("user��"+user.toString()+"\r\n"+"user1��"+user1.toString());
	}
}
