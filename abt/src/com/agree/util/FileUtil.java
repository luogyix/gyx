package com.agree.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.struts2.webserver.ApplicationConstants;
/**
 * 
 * @ClassName: FileUtil 
 * @company agree   
 * @author
 * @date 2011-8-15 下午04:11:16 
 *
 */
public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);	
	private static final Log loger=LogFactory.getLog(FileUtil.class);//日志
	/**
	 * 
	 * @Title：	BuffWrite
	 * @param	fileList
	 * @param	filePath
	 * @return	boolean
	 * @throws	Exception
	 */
    public static boolean BuffWrite(ArrayList<String> fileList,String filePath){
       try {
    		
    		FileOutputStream fw=new FileOutputStream(filePath);
    		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(fw,"iso8859-1"));
	
	    	for (int i=0;i<fileList.size();i++){
	        	bw.write(fileList.get(i)+"");
	        	bw.write("\n");
	        }       
	        	bw.flush();    
	        	bw.close();  
	        	fw.close();
	        } catch (Exception e) {    
	        	logger.error(e.getMessage(), e); 
	          return false;
	        } finally {  
	        
	        }    
        return true;
    }
    
    /**
     * 删除单个文件
     * @Title：	deleteFile
     * @param 	fileName 	要删除的文件的文件名
     * @return 	boolean		单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
    synchronized (ApplicationConstants.PACKAGEUPDATE_LOCK){
    File file = new File(fileName);
    // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
    if(file.exists() && file.isFile()) {
    if(file.delete()) {

    	loger.info("删除单个文件" + fileName + "成功！");
    return true;
    } else {

    	loger.info("删除单个文件" + fileName + "失败！");
    return false;
    }
    } else {

    	loger.info("删除单个文件失败：" + fileName + "不存在！");
    return false;
    }
    }
    }
    
	/**
	 * 判断文件是否存在
	 * @param fileName 文件名
	 * @param dir 文件路径
	 * @return
	 */
	public static boolean isFileExist(String fileName, String dir) {
		File file = new File(dir + fileName);
		return (file.exists()) ? true : false;
	}
    
    /**
	 * 判断文件名是否已经存在，如果存在则在后面家(n)的形式返回新的文件名，否则返回原始文件名 例如：已经存在文件名 log4j.htm 则返回log4j(1).htm
	 * @param fileName  文件名不包含路径
	 * @return 判断后的文件名
	 */
	public static String checkFileName(String fileName, String dir) {
		boolean isDirectory = new File(dir + fileName).isDirectory();
		if (isFileExist(fileName, dir)) {
			int index = fileName.lastIndexOf(".");
			StringBuffer newFileName = new StringBuffer();
			String name = isDirectory ? fileName : fileName.substring(0, index);
			String exp = isDirectory ? "" : fileName.substring(index);
			int nameNum = 1;
			while (true) {
				newFileName.append(name).append("(").append(nameNum).append(")");
				if (!isDirectory) {
					newFileName.append(exp);
				}
				if (isFileExist(newFileName.toString(), dir)) {
					nameNum++;
					newFileName = new StringBuffer();
					continue;
				}
				return newFileName.toString();
			}
		}
		return fileName;
	}
    
	/**
	 * 上传文件并返回上传后的文件名
	 * @param uploadFileName  被上传的文件名称
	 * @param savePath 文件的保存路径
	 * @param uploadFile 被上传的文件
	 * @return 上传后的文件名
	 * @throws IOException
	 */
	public static String uploadForName(String uploadFileName, String savePath, File uploadFile) throws IOException {
		String newFileName = checkFileName(uploadFileName, savePath);
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(savePath + newFileName);
			fis = new FileInputStream(uploadFile);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
			throw e;
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			throw e;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
				throw e;
			}
		}
		return newFileName;
	}
    
    /**
	 * 根据路径创建一系列的目录
	 * @param path
	 */
	public static boolean mkDirectory(String path) {
		File file = null;
		try {
			file = new File(path);
			File file1 =file.getAbsoluteFile();

			if (!file1.exists()) {
				file1.setWritable(true, false);
				return file1.mkdirs();
			}
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
		} finally {
			file = null;
		}
		return false;
	}
    
    /**
     * 复制文件
     * @Title：	copyFile
     * @param	from 	代表的是复制的源文件
     * @param	to		代表目标文件
     * @return 	void
     * @throws	Exception
     */
    public static void copyFile(String from, String to)  throws Exception {
      try{
        File file_from = new File(from);
        FileInputStream in = new FileInputStream(file_from);
        
        File file_to =new File(to);
        if(!file_to.exists() ){
          String ls_to_parent = file_to.getParent();
          File file_to_parent = new File(ls_to_parent);
          file_to_parent.mkdirs();
        }

        FileOutputStream out = new FileOutputStream(file_to);

        byte[] buf = new byte[128 * 1024];
        int c;

        while (true) {
          c = in.read(buf);
          if (c == -1)
            break;
            out.write(buf, 0, c);
        }
        in.close();
        out.close();
        logger.debug("复制单个文件成功："+file_to);
      }catch(Exception ex){
    	  logger.error("文件复制错误!");
        throw new Exception("文件复制错误!");
       
      }
    }

    /**
     * 移动文件
     * @Title：	moveFile
     * @param	from	代表要移动的源文件
     * @param	to		代表目标文件
     * @return	boolean	移动成功返回true
     * @throws	Exception
     */
    public static boolean moveFile(String from, String to) throws Exception {
      File file_from = new File(from);
      FileInputStream in = new FileInputStream(file_from);
      File file_to = new File(to);
      if (!file_to.exists()) {
        String ls_to_parent = file_to.getParent();
        File file_to_parent = new File(ls_to_parent);
        file_to_parent.mkdirs();
      }

      FileOutputStream out = new FileOutputStream(file_to); 

      byte[] buf = new byte[128 * 1024];
      int c;

      while (true) {
        c = in.read(buf);
        if (c == -1)
          break;
        out.write(buf, 0, c);
      }

      in.close();
      out.close();
      if (file_to.length() >= 0) {
        file_from.delete();
      }
      return true;
    }
     /** 
     * @Title: writeFile 
     * @Description: 写文件
     * @throws Exception     
     */ 
    public static void writeFile(String filename,String content) throws Exception{
    //	filename=java.net.URLDecoder.decode(FileUtil.class.getClassLoader().getResource("/temp").getPath(),"utf-8")+filename;
    	OutputStreamWriter osw = null;
    	FileOutputStream fos =null;
    	
    	try { 
 		     fos = new FileOutputStream(filename); 
 			 osw = new OutputStreamWriter(fos, "UTF-8" ); 
 			osw.write(content); 
 			osw.flush(); 
    	} catch (Exception e) { 
    		logger.error(e.getMessage(), e);
    	}finally{
    		//osw.close();
    		fos.close();
    	}
    	
    	
     }
    public static String readFile(String filename) throws Exception {
    	String result="";
    	char[] tempchars = new char[1];
    	//tempchars.
        Reader  reader = new InputStreamReader(new FileInputStream(filename),"utf-8");
        int tempchar;  
        while ((tempchar = reader.read()) != -1){  
	        //对于windows下，rn这两个字符在一起时，表示一个换行。  
	        //但如果这两个字符分开显示时，会换两次行。  
	        //因此，屏蔽掉r，或者屏蔽n。否则，将会多出很多空行。  
	      //  if (((char)tempchar) != 'r'){  
	        	tempchars[0]=(char)tempchar;  
	        	result=result.concat(new String(tempchars));
	       // }  
        }  
        reader.close();  
		return result;
    }
    
//    public static void main(String[] args) throws Exception{
//    	String file="E:\\bsms\\WebRoot\\temp\\admin_page.jsp";
//    	System.out.println(FileUtil.readFile(file));
//    }
	/**
	 * 重命名文件
	 * @param dir 文件路径
	 * @param oldfilename 原文件名称
	 * @param newfilename 新文件名称
	 * @return void
	 * @throws Exception
	 */
	public static void renameFile(String dir, String oldfilename, String newfilename) throws Exception {
		try {
			File oldfile = new File(dir+oldfilename);
			File onewfile = new File(dir+newfilename);
			oldfile.renameTo(onewfile);
		} catch (Exception e) {
			logger.error("文件重名名错误!", e);
			throw new Exception("文件重命名错误!");
		}
	}
	
	/**
	 * 根据指定字符集将内容写到文本中
	 * @param filePath 要写入到的目标文件路径
	 * @param fileContent 要写入的内容
	 * @param encoding 字符集
	 * @return
	 */
	public static boolean writeFileEncoding(String fileContent, String filePath, String encoding) {
		boolean flag = true;
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(filePath);
			byte[] b = fileContent.getBytes(encoding);
			os.write(b, 0, b.length);
		} catch (IOException e) {
			flag = false;
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				flag = false;
				logger.error(e.getMessage(), e);
			}
		}
		return flag;
	}
	
	/**
	 * 根据文件路径安指定字符集读文件，将文件内容存储到字符串中
	 * @param path
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String readFile(String path, String encoding) throws Exception {
		String fileContent = "";
		FileInputStream fis = new FileInputStream(path);
		try {
			InputStreamReader isr = new InputStreamReader(fis, encoding);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				fileContent += line;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			fis.close();
		}
		return fileContent;
	}
	
	/**
	 * 根据文件对象，将文件内容存储到字符串中
	 * @param file
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static String readFile(File file, String encoding) throws Exception {
		String fileContent = "";
		FileInputStream fis = new FileInputStream(file);
		try {
			InputStreamReader isr = new InputStreamReader(fis, encoding);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				fileContent += line;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			fis.close();
		}
		return fileContent;
	}
	
	
	//删除目录及其下所有文件,若目录下存在文件或子目录，则删除其下所有文件
	public static boolean deleteFileOrDir(String dirOrFilePath){
		File file = new File(dirOrFilePath);
		if(file.exists()){
			if(file.isFile()){
				if(file.delete()){
					loger.info("删除文件或目录" + dirOrFilePath + "成功！");
				}
			}else if(file.isDirectory()){
				try {
					File[] files = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						files[i].delete();
					}
					file.delete();
				} catch (Exception e) {
					loger.error("删除文件或目录" + dirOrFilePath + "失败！",e);
					return false;
				}
			}
		
			return true;
		} else {

	    	loger.info("删除文件或目录" + dirOrFilePath + "失败！");
	    	return false;
		}
		
	}
	
	/**
	 * 吴江新增的文件删除方法
	 * @param delpath
	 * @return
	 * @throws Exception
	 */
	public static boolean deleteDir(String delpath) throws Exception {
        try {
            File file = new File(delpath);
            if (!file.isDirectory()) {
                file.delete();
            } else {
                if (!file.isDirectory()) return true;
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; ++i) {
                    File delfile;
                    if (!(delfile = new File(String.valueOf(delpath) + "\\" + filelist[i])).isDirectory()) {
                        delfile.delete();
                        continue;
                    }
                    if (!delfile.isDirectory()) continue;
                    FileUtil.deleteDir(String.valueOf(delpath) + "\\" + filelist[i]);
                }
                file.delete();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("deletefile() Exception:" + e.getMessage());
            FileUtil.logger.info((String)("deletefile() Exception:" + e.getMessage()));
            return false;
        }
        return true;
    }
}
