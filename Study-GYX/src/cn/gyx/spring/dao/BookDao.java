package cn.gyx.spring.dao;

import org.springframework.stereotype.Repository;

//@Component
//@Controller
//@Service
@Repository
public class BookDao {
	
	public BookDao() {
		System.out.println("���췽������");
	}
	
	public void save(){
		System.out.println("BookDao��save������������");
	}
}
