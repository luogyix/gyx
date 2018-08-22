package cn.gyx.run;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;




public class TestRun {
	
	public static void main(String[] args) {
        BufferedWriter writer = null;
        BufferedWriter interimWriter = null;
        try {
        	writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\root\\Desktop\\abf4a-Test\\outTest.txt"), "utf-8"));
        	interimWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\root\\Desktop\\abf4a-Test\\interi.txt"), "utf-8"));
		} catch (Exception e) {
			System.out.println("异常");
		}
        String inPath = "C:\\Users\\root\\Desktop\\abf4a-Test\\old.abf4a";
        BufferedReader reader = null;
        try {
        	reader = new BufferedReader( new InputStreamReader( new FileInputStream( new File(inPath)),"utf-8"));
		} catch (Exception e) {
			System.out.println("异常");
		}
	    String s=null;
	    int grep2Index = 0;
	    int startBeanNum = 0;
	    int lastBeanNum = 0;
	    if(writer!=null&&reader!=null&&interimWriter!=null){
	 	    try {
				while ((s=reader.readLine())!=null) {
					//loopIndex++;
					if(s.matches(".{0,}<Bean feature=\"Group\">")){
						startBeanNum++;
					}
					if(s.matches("\\s{4}</Bean>") || s.matches("\\t{1}</Bean>")){
						lastBeanNum++;
					}
					if((s.matches("\\t{2}<.{0,1}Bean.{1,}") || s.matches("\\s{8}<.{0,1}Bean.{1,}") || s.matches("\\s{9,}<.{0,1}Entry.{1,}") || s.matches("\\s{9,}<.{0,1}Mapping.{0,1}.{1,}")) &&
							startBeanNum == 2 && lastBeanNum != 2){
						interimWriter.append(s);
						interimWriter.newLine();
					}
					if(startBeanNum != 2 ){
						try {
					    	writer.append(s);
					    	//写入分行
					        writer.newLine();
						} catch (Exception e) {
							System.out.println("异常");
						}
					}
					if(lastBeanNum == 2){
						startBeanNum++;
					}
				    
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	 	    try {
	 	    	reader.close();
		        writer.close();
		        interimWriter.close();
			} catch (Exception e) {
				System.out.println("异常");
			}
	    }
	    merge(6);
	}
	
	/*public static void main(String[] args) {
		merge();
	}*/
	
	private static void merge(int startIndex){
		BufferedWriter writer = null;
        try {
        	writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\root\\Desktop\\abf4a-Test\\last.txt"), "utf-8"));
		} catch (Exception e) {
			System.out.println("异常");
		}
		
		 String outTest = "C:\\Users\\root\\Desktop\\abf4a-Test\\outTest.txt";
		 String interi = "C:\\Users\\root\\Desktop\\abf4a-Test\\interi.txt";
		 
         BufferedReader reader = null;
         BufferedReader reader1 = null;
         try {
        	 reader = new BufferedReader( new InputStreamReader( new FileInputStream( new File(outTest)),"utf-8"));
        	 reader1 = new BufferedReader( new InputStreamReader( new FileInputStream( new File(interi)),"utf-8"));
		 } catch (Exception e) {
			 System.out.println("异常");
		 }
         /*
         BufferedWriter writer = null;
         try {
         	 writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\root\\Desktop\\abf4a-Test\\outTest.txt"), "utf-8"));
 		 } catch (Exception e) {
 			 System.out.println("异常");
 		 }*/
         String s = null;
         String s1 = null;
         String startBeanEnd = "";
         String s2 = null;
         int a = 0;
         if(reader!=null && writer!=null && reader1!=null){
			try {
				while ((s=reader.readLine())!=null) {
					if(a == 1){
						while ((s1=reader1.readLine())!=null) {
							if(s1.matches("\\s{1,}<Entry key=\"constraints#row\".{1,}")){
								int length = s1.length();
					    		int lastIndexOf = s1.lastIndexOf("value=\"");
					    		int kg = s1.lastIndexOf("<");
					    		String substring = s1.substring(lastIndexOf+7, length-3);
					    		String kgS = s1.substring(0, kg);
					    		int nowIndex = startIndex+Integer.parseInt(substring)+1;
					    		s1 = kgS+"<Entry key=\"constraints#row\" type=\"Integer\" value=\""+nowIndex+"\"/>";
							}
							writer.append(s1);
							writer.newLine();
						}
						if(s1==null && s2 == null && !startBeanEnd.equals("")){
							s2 = "reader1停止";
							writer.append(startBeanEnd);
							writer.newLine();
						}
					}
					
					if((s.matches("\\s{4}</Bean>") || s.matches("\\t{1}</Bean>")) && a == 0){
						reader.mark(1024);
						reader.reset();
						startBeanEnd = s;
						a++;
						continue;
					}
					writer.append(s);
					writer.newLine();
				    
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("异常");
			}
			try {
				reader.close();
				reader1.close();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
	}
}
