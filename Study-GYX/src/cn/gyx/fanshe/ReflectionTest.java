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
			System.out.println("输入类相对路径，如java.util.Date");
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
		//正常退出，销毁虚拟机进程
		System.exit(0);
	}
	
	/**
	 * 打印类的所有构造函数
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
			//获得构造函数的参数类型数组
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
	 * 打印类的所有成员方法
	 * @author gaoyixiang
	 */
	public static void printMethods(Class cl){
		//获得类成员方法
		Method[] methods = cl.getDeclaredMethods();
		for (Method m : methods) {
			//获得方法返回值类型
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
	 * 打印类的所有成员变量
	 * @author gaoyixiang
	 */
	public static void printFields(Class cl){
		//获得类所有成员变量
		Field[] fields = cl.getDeclaredFields();
		for (Field f : fields) {
			//获得成员变量类型
			Class type = f.getType();
			//获得成员变量名称
			String name = f.getName();
			System.out.println("  ");
			//获得成员变量修饰符
			String modifiers = Modifier.toString(f.getModifiers());
			if(modifiers.length() > 0){
				System.out.println(modifiers + " ");
			}
			System.out.println(type.getName() + " " + name +";");
		}
	}
}
