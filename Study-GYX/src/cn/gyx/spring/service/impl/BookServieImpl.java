package cn.gyx.spring.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gyx.spring.dao.BookDao;

@Service
public class BookServieImpl implements BookService {
	//@Resource(name="bookDao")ָ��Bean��id
	@Resource//����������spring�Ĺ������ҵ�Bean
	private BookDao bookDao;

	public void save() {
		bookDao.save();
	}

}
