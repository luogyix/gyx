package cn.gyx.spring.dao;

import org.springframework.stereotype.Repository;

//@Component
//@Controller
//@Service
@Repository
public class BookDao {
	
	public BookDao() {
		System.out.println("构造方法调用");
	}
	
	public void save(){
		System.out.println("BookDao的save方法被调用了");
	}
}
