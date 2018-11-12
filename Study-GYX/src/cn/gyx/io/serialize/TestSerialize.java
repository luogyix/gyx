package cn.gyx.io.serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class TestSerialize {
	private static String separator = File.separator;
	private static String serializeFilePath = "C:"+separator+"Users"+separator+"root"+separator+"Desktop"+separator+"Student.txt";
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		serializeStudent();
		Student student = deSerializeStudent();
		System.out.println(student.toString());
	}
	
	/**
	 * @author 高艺祥
	 * @Description 序列化Student对象
	 * @throws FileNotFoundException
	 * @return void
	 * @throws IOException 
	 */
	public static void serializeStudent() throws FileNotFoundException, IOException{
		Student student = new Student("李刚", 24, 1, "16-8");
		File file = new File(serializeFilePath);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(student);
		System.out.println("序列化Student类成功");
		oos.close();
	}
	
	/**
	 * @Description 反序列Student对象
	 * @author 高艺祥
	 * @return cn.gyx.io.serialize.Student
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public static Student deSerializeStudent() throws FileNotFoundException, IOException, ClassNotFoundException{
		File file = new File(serializeFilePath);
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		Student student = (Student) ois.readObject();
		System.out.println("Student对象反序列化成功");
		ois.close();
		return student;
		
	}
}
