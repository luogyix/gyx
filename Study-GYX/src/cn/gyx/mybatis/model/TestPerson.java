package cn.gyx.mybatis.model;

import java.util.Date;

public class TestPerson {
	
	private Integer id;
	
	private String name;
	
	private Integer gender;
	
	private String addr;
	
	private Date birthday;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "TestPerson [id=" + id + ", name=" + name + ", gender=" + gender
				+ ", addr=" + addr + ", birthday=" + birthday + "]";
	}
	
	
	
	

}
