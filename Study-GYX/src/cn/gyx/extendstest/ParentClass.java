package cn.gyx.extendstest;

public class ParentClass {
	private String sex = "1";
	private String name = "parent";
	private String age = "43";
	
	public String allData(){
		sex = sex.equals("1")?"男":"女";
		return "性别-"+sex+
				"\r\n姓名-"+name+
				"\r\n年龄-"+age;
	}
}
