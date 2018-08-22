package cn.gyx.run;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class pageUpdata {
	
	public static void main(String[] args) throws IOException{
		int startIndex = 11;
		/*File outFile = new File("C:\\Users\\root\\Desktop\\newRow.abf4a");
		if(!outFile.exists()){
			outFile.mkdir(); 
		}*/
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\root\\Desktop\\快速开发交易测试\\new-t942204.abf4a"), "utf-8"));
        String inPath = "C:\\Users\\root\\Desktop\\快速开发交易测试\\t942204.abf4a";
		BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( new File(inPath)),"utf-8"));
	    String s=null;
	    String grepName=null;
	    int groupStart = 0;
	    int groupEnt = 0;
	    int groupIdIndex = 0;
	    while ((s=reader.readLine())!=null) {
	    	if(s.matches("\\s{4}<Bean feature=\"Group\">")){
	    		groupIdIndex++;
	    		groupStart = 1;
	    	}
	    	if(s.matches("\\s{4}</Bean>")){
	    		groupEnt = 1;
	    	}
	    	if(s.matches("\\s{8}<Entry key=\"text\".{1,}") && !(s.indexOf("退出") > 0 || s.indexOf("提交") > 0)){
	    		int indexOf = s.indexOf("value=\"");
	    		grepName = s.substring(indexOf+7, s.length()-3);
	    		s = s.substring(0,indexOf+7)+"输入项_"+grepName+s.substring(s.length()-3,s.length());
	    	}
	    	if(s.matches("\\s{8}<Entry key=\"id\".{1,}") && !(s.indexOf("退出") > 0 || s.indexOf("提交") > 0)){
	    		int indexOf = s.indexOf("value=\"");
	    		grepName = s.substring(indexOf+7, s.length()-3);
	    		s = s.substring(0,indexOf+7)+"group_输入项"+groupIdIndex+s.substring(s.length()-3,s.length());
	    	}
	    	if(s.matches("\\s{12}<Entry key=\"id\".{1,}")){
	    		int indexOf = s.indexOf("\"/>");
	    		s = s.substring(0,indexOf)+"_"+grepName+s.substring(s.length()-3,s.length());
	    	}
	    	writer.append(s);
	    	writer.newLine();
	    }
	    reader.close();
        writer.close();
    }
	
}
