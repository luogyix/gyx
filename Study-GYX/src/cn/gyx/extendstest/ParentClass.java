package cn.gyx.extendstest;

public class ParentClass {
	private String sex = "1";
	private String name = "parent";
	private String age = "43";
	
	public String allData(){
		sex = sex.equals("1")?"��":"Ů";
		return "�Ա�-"+sex+
				"\r\n����-"+name+
				"\r\n����-"+age;
	}
}
