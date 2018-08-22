package cn.gyx.spring.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gyx.spring.dao.BookDao;

@Service
public class BookServieImpl implements BookService {
	//@Resource(name="bookDao")指定Bean的id
	@Resource//根据类型在spring的工厂中找到Bean
	private BookDao bookDao;

	public void save() {
		bookDao.save();
	}

}
