package com.agree.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;


public class Scpclient {
	

	private static final Logger logger = LoggerFactory.getLogger(Scpclient.class);
	
	private String ip;
	private int port;
	

	private String username;
	private String password;
	
	
	static private Scpclient instance;
	
	public Scpclient(String IP, int port, String username, String password) {
		this.ip = IP;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	static synchronized public Scpclient getInstance(String IP,int port,String username,String password){
		//if(instance == null){
			instance = new Scpclient(IP,port,username,password);
		//}
		
		return instance;
	}
	
	
	public void getFileFromRemote(String remoteFile,String localTargetDirectory){
		Connection conn = new Connection(ip,port);
		try {
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);
			if(isAuthenticated ==false){
				logger.error("authentication failed");
			}
			SCPClient client = new SCPClient(conn);
			client.get(remoteFile, localTargetDirectory);
			conn.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	public void putFileToRemote(String localFilePath,String remoteTargetDirectory){
		Connection conn = new Connection(ip,port);
		try {
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);
			if(isAuthenticated == false){
				logger.error("authentication failed");
			}
			SCPClient client = new SCPClient(conn);
			client.put(localFilePath, remoteTargetDirectory);
			conn.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	
	public void putFileToRemote(String localFilePath,String remoteTargetDirectory,String remoteFileName,String mode){
		
		Connection conn = new Connection(ip, port);
		try {
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);
			if(isAuthenticated == false){
				logger.error("authentication failed");
			}
			SCPClient client = new SCPClient(conn);
			if((mode == null) || (mode.length() == 0)){
				mode = "0600";
			}
			client.put(localFilePath, remoteTargetDirectory, mode);
			//重命名
			Session sess = conn.openSession();
			String tmpPathName = remoteTargetDirectory+"/"+remoteFileName;
			String newPathName = tmpPathName.substring(0, tmpPathName.lastIndexOf("."));
			sess.execCommand("mv "+remoteFileName+" "+newPathName);//重命名回来
			
			conn.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	public void deletFile(String remoteTargetDirectory,String remoteFileName){
		
		Connection conn = new Connection(ip, port);
		try {
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);
			if(isAuthenticated == false){
				logger.error("authentication failed");
			}
			Session sess = conn.openSession();
			String tmpPathName = remoteTargetDirectory+"/"+remoteFileName;
			sess.execCommand("rm '"+tmpPathName+"'");
			conn.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	public void putFileToRemote(File upload,String remoteTargetDirectory,String remoteFileName){
		Connection conn = new Connection(ip,port);
		try {
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);
			if(isAuthenticated == false){
				logger.error("authentication failed");
			}
			SCPClient client = new SCPClient(conn);
			client.put(getBytes(upload), remoteFileName, remoteTargetDirectory);
			conn.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	private byte[] getBytes(File upload) throws FileNotFoundException {
		byte[] buffer = null;
		try {
			//File file = new File(upload);
			FileInputStream fis = new FileInputStream(upload);
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int i;
			while((i = fis.read(b)) != -1){
				byteArray.write(b, 0, i);
			}
			fis.close();
			byteArray.close();
			buffer = byteArray.toByteArray();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return buffer;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
