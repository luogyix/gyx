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


public class SqlHB {
	
	public static void main(String[] args) throws IOException{
		File outFile = new File("C:\\Users\\root\\Desktop\\row2.txt");
		if(!outFile.exists()){
			outFile.mkdir(); 
		}
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\root\\Desktop\\row2.txt"), "utf-8"));
        String filePath = "C:\\Users\\root\\Desktop\\支付组数据库操作记录\\all";
        List<String> fileList = new ArrayList<String>();
        fileList= getFiles(filePath);
        int forIndex = 0;
        for (String file:fileList) {
        	 forIndex++;
        	 BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( new File(file)),"utf-8"));
             String s=null;
             while ((s=reader.readLine())!=null) {
                writer.append(s);
                writer.newLine();
                if(forIndex == fileList.size()-1){
                	reader.close();
                }
            }
        }
        
        writer.close();
    }
	
	static List<String> getFiles( String filePath )
    {
        List<String> filelist = new ArrayList<String>();
        File root = new File( filePath );
        File[] files = root.listFiles();
        for ( File file : files )
        {
            if ( file.isDirectory() )
            {
                getFiles( file.getAbsolutePath() );
                filelist.add( file.getAbsolutePath() );
                System.out.println("显示" + filePath + "下所有子目录及其文件" + file.getAbsolutePath() );
            }else{
                filelist.add( file.getAbsolutePath() );
                System.out.println("显示" + filePath + "下所有子目录" + file.getAbsolutePath() );
            }
        }
        return filelist;
    }

}
