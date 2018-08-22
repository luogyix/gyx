package cn.gyx.fanshe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;
@SuppressWarnings({"resource","rawtypes"})
public class ReflectionTest {

	public static void main(String[] args) {
		Object object;
		String name;
		if(args.length > 0){
			name = args[0];
		}else{
			Scanner in = new Scanner(System.in);
			System.out.println("���������·������java.util.Date");
			name = in.next();
		}
		
		try {
			Class cl = Class.forName(name);
			Class supercl = cl.getSuperclass();
			String modifiers = Modifier.toString(cl.getModifiers());
			if(modifiers.length() > 0){
				System.out.println(modifiers = " ");
			} 
			System.out.println("class " + name);
			if(supercl != null && supercl != Object.class){
				System.out.println(" extends " + supercl.getName());
			}
			System.out.println("\n{\n");
			printConstructors(cl);
			System.out.println();
			printMethods(cl);
			System.out.println();
			printFields(cl);
			System.out.println("}");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//�����˳����������������
		System.exit(0);
	}
	
	/**
	 * ��ӡ������й��캯��
	 * @author gaoyixiang
	 */
	public static void printConstructors(Class cl){
		Constructor[] constructors = cl.getDeclaredConstructors();
		for (Constructor c : constructors) {
			String name = c.getName();
			System.out.println("  ");
			String modifiers = Modifier.toString(c.getModifiers());
			if(modifiers.length() > 0){
				System.out.println(modifiers + " ");
			}
			System.out.println(name + "(");
			//��ù��캯���Ĳ�����������
			Class[] paramTypes = c.getParameterTypes();
			for(int j = 0; j < paramTypes.length; j++){
				if(j > 0){
					System.out.println(", ");
				}
				System.out.println(paramTypes[j].getName());
			}
			System.out.println(");");
		}
	}
	
	/**
	 * ��ӡ������г�Ա����
	 * @author gaoyixiang
	 */
	public static void printMethods(Class cl){
		//������Ա����
		Method[] methods = cl.getDeclaredMethods();
		for (Method m : methods) {
			//��÷�������ֵ����
			Class retType = m.getReturnType();
			String name = m.getName();
			System.out.println("  ");
			String modifiers = Modifier.toString(m.getModifiers());
			if(modifiers.length() > 0){
				System.out.println(modifiers + " ");
			}
			System.out.println(retType.getName() + " " + name + "(");
			Class<?>[] paramTypes = m.getParameterTypes();
			for(int j = 0; j < paramTypes.length; j++){
				if(j > 0){
					System.out.println(", ");
				}
				System.out.println(paramTypes[j].getName());
			}
			System.out.println(");");
		}
	}
	
	/**
	 * ��ӡ������г�Ա����
	 * @author gaoyixiang
	 */
	public static void printFields(Class cl){
		//��������г�Ա����
		Field[] fields = cl.getDeclaredFields();
		for (Field f : fields) {
			//��ó�Ա��������
			Class type = f.getType();
			//��ó�Ա��������
			String name = f.getName();
			System.out.println("  ");
			//��ó�Ա�������η�
			String modifiers = Modifier.toString(f.getModifiers());
			if(modifiers.length() > 0){
				System.out.println(modifiers + " ");
			}
			System.out.println(type.getName() + " " + name +";");
		}
	}
}
