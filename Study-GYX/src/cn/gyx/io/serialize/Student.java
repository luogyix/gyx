package cn.gyx.io.serialize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Student implements Serializable{
	/**
	 * ָ�����л�id
	 */
	private static final long serialVersionUID = -5598338283759115762L;
	private String name;
	private int age;
	private int sex;
	transient private String Class;
	
	public Student(String name, int age, int sex, String Class){
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.Class = Class;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "name="+name+", age="+age+", sex="+sex+", Class="+Class;
	}
	
	//���������������transient���ε��ֶμ������л�
	@SuppressWarnings("unused")
	private void writeAttribute(ObjectOutputStream out) throws IOException{
		out.defaultWriteObject();
		out.writeChars(Class);
	}
	
	@SuppressWarnings("unused")
	private void readAttribute(ObjectInputStream ois) throws ClassNotFoundException, IOException{
		ois.defaultReadObject();
		BufferedReader bdr = new BufferedReader(new InputStreamReader(ois));
		Class = bdr.readLine();
	}
}
