package cn.gyx;

public class EverydayTest {

	public static void main(String[] args) {
		String str = "2088=02=02";
		String zz = "";
		boolean matches = str.matches("[0-9]{4}\\.[0-9]{2}\\.[0-9]{2}");
		System.out.println(matches);
	}		

}
