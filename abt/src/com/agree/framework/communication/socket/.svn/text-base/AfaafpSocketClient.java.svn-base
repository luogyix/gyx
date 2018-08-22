package com.agree.framework.communication.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.exception.AppException;
import com.agree.util.Constants;
import com.agree.util.StringUtil;

/**
 * 
 * @ClassName: AfaafpSocketClient 
 * @Description: TODO
 * @company agree   
 * @author liwei   
 * @date 2012-3-6 下午03:49:10 
 *
 */
public class AfaafpSocketClient {
	private static final Logger logger = LoggerFactory.getLogger(AfaafpSocketClient.class);

	private String host1;
	private int port1;
	private String host2;
	private int port2;
	private int soTimeout;	

	public AfaafpSocketClient() {
		
	}
	
	
	public String getHost1() {
		return host1;
	}


	public void setHost1(String host1) {
		this.host1 = host1;
	}


	public int getPort1() {
		return port1;
	}


	public void setPort1(int port1) {
		this.port1 = port1;
	}


	public String getHost2() {
		return host2;
	}


	public void setHost2(String host2) {
		this.host2 = host2;
	}


	public int getPort2() {
		return port2;
	}


	public void setPort2(int port2) {
		this.port2 = port2;
	}


	/** 
	 * @Title: send 
	 * @Description: 发送报文
	 * @param msg    准备发送的报文对象
	 * @return int 	 返回发送文件到第几个服务器上，从1开始
	 * @throws Exception    
	 */ 
	public synchronized int send(Map<String,Object> msg) throws Exception {
		Socket socket = null;
		File sendFile=null;
		byte[] fileContext=new byte[0];
		byte mask_falg;
		DataInputStream sendDS=null,in=null;
		DataOutputStream os=null;
		boolean opFalg=false;
		int headLen=4+2+1+1+24+24+128+128+64+4;						//报文头 380
		int index=1;
		try {
//			File sendFile=new File((String)msg.get(Constants.SENDFILE));
//			if(!sendFile.exists()||!sendFile.isFile())
//				throw new AppException("发送文件不存在！");
			
			//操作掩码
			String MASK_FLAG=(String)msg.get(Constants.MASK);
			if (MASK_FLAG.equals(Constants.MASK_P)) {			//上传文件
				//上传的文件
				sendFile=(File)msg.get(Constants.SENDFILE);	
				sendDS=new DataInputStream(new FileInputStream(sendFile));
				fileContext=new byte[(int)sendFile.length()];
				sendDS.readFully(fileContext);	
				mask_falg=(byte)0x50;							//'P'
			}else {
				mask_falg=(byte)0x47;							//'G'
			}
			
			try {
				socket = new Socket(host1, port1);
			}catch(Exception  e){
				logger.error("处理失败,原因：连接服务器1失败，连接ip："+host1+",端口："+port1,e);
				if(StringUtil.isNotEmpty(host2)&&port2!=0){
					try {
						socket = new Socket(host2, port2);
						index=2;
					} catch (ConnectException e1) {
						logger.error("处理失败,原因：连接服务器2失败，连接ip："+host2+",端口："+port2,e);
						throw new AppException("处理失败,原因：连接服务器失败");
					}
				}
			}
			socket.setSoTimeout(soTimeout);
			socket.setKeepAlive(true);				
			
			//发送接口
			os=new DataOutputStream(socket.getOutputStream());
			
			//全包长度
			int PCKLEN=headLen+fileContext.length;
			os.writeInt(PCKLEN);
			//掩码 第一位：0xCE  第二位 ：/’P’/’G’
			byte[] MASK=new byte[]{(byte)0xCE,mask_falg};
			os.write(MASK);
			//目前 0x00
			byte FLAG=(byte)0x00;
			os.write(FLAG);
			//状态
			byte STAT=(byte)0;
			os.write(STAT);
			//应用代码
			byte[] MC=new byte[24];	
			String mcCode=(String)msg.get(Constants.MC);			
			System.arraycopy(mcCode.getBytes("GB2312"), 0, MC, 0, mcCode.getBytes("GB2312").length);
			os.write(MC);
			//交易代码
			byte[] TC=new byte[24];
			System.arraycopy(Constants.TC.getBytes("GB2312"), 0, TC, 0, Constants.TC.getBytes("GB2312").length);			
			os.write(TC);
			//文件名称
			byte[] FNAME=new byte[128];
			String sendFileName=(String)msg.get(Constants.SENDFILENAME);
			//String sendFileName=sendFile.getName();
			System.arraycopy(sendFileName.getBytes("GB2312"), 0, FNAME, 0, sendFileName.getBytes("GB2312").length);
			os.write(FNAME);
			//对方操作目录
			byte[] FPATH=new byte[128];
			String remotePath=(String)(msg.get(Constants.REMOTEPATH)==null?"":msg.get(Constants.REMOTEPATH));
			System.arraycopy(remotePath.getBytes("GB2312"), 0, FPATH, 0, remotePath.getBytes("GB2312").length);
			os.write(FPATH);
			//扩展域
			byte[] EXTEND=new byte[64];
			os.write(EXTEND);
			
			if (MASK_FLAG.equals(Constants.MASK_P)) {			//上传文件
				//文件长度
				os.writeInt((int)sendFile.length());				
				//文件内容
				os.write(fileContext);		
			}else {												//下载文件
				//文件长度
				os.writeInt(0);
			}
			os.flush();
						
			//读取返回消息
			in=new DataInputStream(socket.getInputStream());	
			
			//读取长度
			int retLen=in.readInt();
			logger.info("AFAAFP return PCKLEN is["+retLen+"]");	
			//报文头
			byte[] retHeadB=new byte[headLen-4];
			in.read(retHeadB);
			byte retSTAT=retHeadB[3];			//回执交易状态
			logger.info("AFAAFP return STAT is["+retSTAT+"]");			
			logger.info("AFAAFP return PACK_HEAD is["+new String(retHeadB)+"]");
			//报文体
			int retConLen=retLen-headLen;
			if (retConLen>0) {
				byte[] retConB=new byte[retConLen];
				byte[] temp=new byte[1024];
				int tempLen=0,curindex=0;
				while((tempLen=in.read(temp))>0){
					System.arraycopy(temp, 0, retConB, curindex, tempLen);	
					curindex+=tempLen;					
				}
				//logger.info("AFAAFP return PACK_BODY is["+new String(retConB)+"]");
				//接收报文体（接收文件内容）
				msg.put(Constants.RETFILECONTS, retConB);
			}
			
			//AFAAFP 回执状态
			if ((int)retSTAT==Constants.STAT_SUCCESS){
				opFalg= true;
			}
		}catch (Exception e) {
			throw new AppException("处理失败,原因："+e.getMessage(),e);
		} finally {
			try {
				if (sendDS!=null){
					sendDS.close();
				}
				os.close();
				in.close();
				socket.close();
			} catch (Exception ee) {
				logger.error(ee.getMessage(),ee);
			}
		}
		//操作状态
		msg.put(Constants.OPSTAT, new Boolean(opFalg)) ;
		return index;
	}
	
	
	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}
	
//	public static void main(String[] args) throws Exception{
////		byte[] test=new byte[24];
////		System.arraycopy("UPDS".getBytes("GB2312"), 0, test, 0, "UPDS".getBytes("GB2312").length);
//		
//		AfaafpSocketClient testSend=new AfaafpSocketClient();
//		testSend.setHost("192.9.205.94");
//		testSend.setPort(10001);
//		while (true){
//			Map msg=new HashMap();
//			msg.put(Constants.SENDFILE, "c:\\aa.txt");
//			//msg.put("REMOTEPATH", "/home/upp2/workspace/fdir/");
//			msg.put(Constants.REMOTEPATH, "UPBS\\/TEST");
//			testSend.send(msg);
//		}
//		
//	}
}
