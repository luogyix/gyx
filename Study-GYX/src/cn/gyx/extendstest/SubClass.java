package cn.gyx.extendstest;


public class SubClass extends ParentClass{
	public String school = "�廪��ѧ";
	public String allData(){
		ParentClass aa = new SubClass();
		return super.allData()+"\r\nѧУ-"+school;
	}
}
