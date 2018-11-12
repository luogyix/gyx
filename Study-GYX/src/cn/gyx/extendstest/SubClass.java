package cn.gyx.extendstest;


public class SubClass extends ParentClass{
	public String school = "清华大学";
	public String allData(){
		ParentClass aa = new SubClass();
		return super.allData()+"\r\n学校-"+school;
	}
}
