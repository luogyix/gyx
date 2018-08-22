package com.agree.framework.natp;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataTransfer {
	private static final Logger logger = LoggerFactory.getLogger(DataTransfer.class);
	private byte[] recvDatas = new byte[0];
	private OutputStream _outputStream;

	private InputStream _inputStream;
	
	private int _protoclVersion;// 协议版本
	
	private int _natpVersion;
	
	private String _tradeCode; //交易号
	
	private String _templateCode;
	
	private int dataLen = 0;

	private String _reserve;
	
	private int packetLengthLimit = 4092;

	public static int LIMIT = 512 * 1024 * 1024;
	
	private String _agentNo;
	
	private String _areaNo;
	
	private String _bankNo;
	
	private String _businessType;
	
	private String _tellerNo;
	
	private String _fileServerName;

	
	public void natpSendDatas(byte datas[]) throws IOException{
		int offset=0;
		if(this.getNatpVersion()==0x10||this.getNatpVersion()==0x30){
			offset=71;
		}else{
			offset=43;
		}
		DataOutputStream dataout=new DataOutputStream(new BufferedOutputStream(this.getOutputStream(),4096));
		byte continueFlag = 0;
		short packNum = 1;
		int sendLength = datas.length + offset;
		int index = datas.length;
		if (sendLength > LIMIT) {
			sendLength = LIMIT;
			continueFlag = 1;
			index = sendLength - offset; // 实际要发送的数据的结束点
		}
		dataout.writeInt(sendLength);
		ByteArrayOutputStream bout=new ByteArrayOutputStream();
		DataOutputStream dataOut = new DataOutputStream(bout);
		
		
		dataOut.write(0x01);
		dataOut.write(0x00);
		dataOut.write(continueFlag);
		dataOut.write(0x00);
		dataOut.writeShort(packNum);
		if(this.getNatpVersion()==0x01||this.getNatpVersion()==0x30){
			dataOut.write(getNatpVersion());
			dataOut.write(StringTool.fill(this.getTradeCode(), ' ', 20, false).getBytes("GBK"));
			
			dataOut.write(StringTool.fill(this.getTemplateCode(), ' ', 20, false).getBytes("GBK"));
			
			dataOut.write(StringTool.fill(this.getReserve(), ' ', 20, false).getBytes("GBK"));
		}else{
			dataOut.write(0x01);
			dataOut.write(StringTool.fill(this.getTradeCode(), ' ', 5, false).getBytes());
			
			dataOut.write(StringTool.fill(this.getBusinessType(), ' ', 3, false).getBytes());
			
			dataOut.write(StringTool.fill(getAgentNo(), ' ', 9, false).getBytes());
			
			dataOut.write(StringTool.fill(getAreaNo(), ' ', 5, false).getBytes());
			
			dataOut.write(StringTool.fill(getBankNo(), ' ', 5, false).getBytes());
			
			dataOut.write(StringTool.fill(getTellerNo(), ' ', 5, false).getBytes());
		}
		dataOut.write(datas, 0, index);
		
		byte[] pureDatas = bout.toByteArray();
		dataout.write(pureDatas);
		dataout.writeInt(Crc32_Agree.crc32_2(pureDatas, 0, pureDatas.length));
		
		dataout.flush();
		while (continueFlag == 1) {
			// );
			//recvProtocolPack(datain);
			if (datas.length <= index + 4096 - 14) {
				continueFlag = 0;
				sendLength = datas.length - index;
			} else {
				continueFlag = 1;
				sendLength = packetLengthLimit - 10;
			}
			bout.reset();
			dataOut.write(0x01);
			dataOut.write(0x00);
			dataOut.write(continueFlag);
			dataOut.write(0x00);
			dataOut.writeShort(++packNum);
			dataOut.write(datas, index, sendLength);
			pureDatas = bout.toByteArray();
			dataout.writeInt(sendLength + 10);
			dataout.write(pureDatas);
			dataout.writeInt(Crc32_Agree.crc32_2(pureDatas, 0, pureDatas.length));
			dataout.flush();
			index += sendLength;
		}
		
	}
	

	/**
	 * @param din
	 */
//	private void recvProtocolPack(DataInputStream din) throws IOException {
//		int len = din.readInt();
//		byte[] protocol = new byte[len];
//		// add by deng-cz
//		din.readFully(protocol);
//	}
	
	/**
	 * 收取nnatp的数据，数据包收取时设置部分全局值，并返回收到数据包
	 * 
	 * @return 数据包
	 * @throws IOException
	 */
	public byte[] natpRecv() throws IOException {
		DataInputStream din = new DataInputStream(getInputStream());
			int totalLength = din.readInt();
		if (totalLength > LIMIT) {
			throw new IOException("报文长度非法,最大支持2MB");
		}
		byte[] allDatas = new byte[totalLength];
		/** 初始化 recvDatas */
		// recvDatas = new byte[totalLength];
		// dataLen = 0;
		readFully(din, allDatas);
		if (Crc32_Agree.crc32_2(allDatas, 0, totalLength - 4) != getCrc(allDatas))
			throw new IOException("crc校验不符");
		byte protoclVersion = allDatas[0];
		setProtoclVersion(protoclVersion);
		// 传输数据流
		if (protoclVersion == 0x01) {
			byte[] datas = natpRecvData(allDatas, din);
			return handleDatas(datas);
		}
		// 传输文件
		else if (protoclVersion == 0x02)
			return natpRecvFile(allDatas, din);
		else
			throw new IOException("无此协议版本号" + protoclVersion);

	}
	
	/**
	 * @param datas
	 * @return byte[]
	 */
	private byte[] handleDatas(byte[] datas) throws IOException {
		int natpVersion = datas[0];
		setNatpVersion(natpVersion);
		if (natpVersion == 0x01 || natpVersion == 0x02) {
			// 交易代码
			byte[] tradeCode = { datas[1], datas[2], datas[3], datas[4],
					datas[5] };
			// 业务类型
			byte[] businessType = { datas[6], datas[7], datas[8] };
			// 代理业务编号
			byte[] agentNo = { datas[9], datas[10], datas[11], datas[12],
					datas[13], datas[14], datas[15], datas[16], datas[17] };
			// 地区号
			byte[] areaNo = { datas[18], datas[19], datas[20], datas[21],
					datas[22] };
			// 网点号
			byte[] bankNo = { datas[23], datas[24], datas[25], datas[26],
					datas[27] };
			// 柜员号
			byte[] tellerNo = { datas[28], datas[29], datas[30], datas[31],
					datas[32] };
			setTradeCode(new String(tradeCode).trim());
			setAgentNo(new String(agentNo).trim());
			setBusinessType(new String(businessType).trim());
			setAreaNo(new String(areaNo).trim());
			setBankNo(new String(bankNo).trim());
			setTellerNo(new String(tellerNo).trim());
			byte[] realDatas = new byte[datas.length - 33];
			System.arraycopy(datas, 33, realDatas, 0, realDatas.length);
			return realDatas;
		} else if (natpVersion == 0x03) {
			byte[] realDatas = new byte[datas.length - 1];
			System.arraycopy(datas, 1, realDatas, 0, realDatas.length);
			return realDatas;
		} else if (natpVersion == 0x10 || natpVersion == 0x30) {
			// 商业银行中间业务报文定义
			// 交易代码
			byte[] tradeCode = new byte[20];
			System.arraycopy(datas, 1, tradeCode, 0, 20);
			// 模板代码
			byte[] templateCode = new byte[20];
			System.arraycopy(datas, 21, templateCode, 0, 20);
			// 预留
			byte[] reserve = new byte[20];
			System.arraycopy(datas, 41, reserve, 0, 20);

			setTradeCode(new String(tradeCode).trim());
			setTemplateCode(new String(templateCode).trim());
			setReserve(new String(reserve).trim());

			byte[] realDatas = new byte[datas.length - 61];
			System.arraycopy(datas, 61, realDatas, 0, realDatas.length);
			return realDatas;
		} else
			throw new IOException("无此NATP版本类型 " + natpVersion);
	}
	
	private void readFully(InputStream din, byte[] rec) throws IOException {
		int readed = din.read(rec);
		try {
			while (readed < rec.length) {

				int next = din.read(rec, readed, rec.length - readed);
				if (next < 0) {
					throw new IOException("对方连接已关闭!");
				}
				readed += next;
			}
		} catch (IOException e1) {
			logger.error(e1.getMessage(),e1);
			throw e1;
		}
	}
	
	/**
	 * @param allDatas
	 * @return byte[]
	 */
	private byte[] natpRecvData(byte[] allDatas, DataInputStream din)
			throws IOException {
		int continueFlag = allDatas[2];
		short packNum = bytesToShort(allDatas, 4);
		appendBytes(allDatas, 6, allDatas.length - 10);
		DataOutputStream dout = new DataOutputStream(getOutputStream());
		while (continueFlag == 0x01) {
			natpSendProtocol(dout, (short) (packNum + 1));
			int len = din.readInt();
			byte[] datas = new byte[len];
			readFully(din, datas);
			// packType = datas[1];
			continueFlag = datas[2];
			// compressFlag = datas[3];
			packNum = bytesToShort(datas, 4);
			if (Crc32_Agree.crc32_2(datas, 0, datas.length - 4) != getCrc(datas))
				throw new IOException("crc校验不符");
			appendBytes(datas, 6, datas.length - 10);
		}
		return getAllDatas();
	}
	
	private byte[] getAllDatas() {
		byte[] ret = new byte[dataLen];
		System.arraycopy(recvDatas, 0, ret, 0, dataLen);
		return ret;
	}
	
	private void natpSendProtocol(DataOutputStream dout, short packNum)throws IOException {
		byte[] datas = new byte[6];
		datas[0] = 1; /* 目前版本为1 */
		datas[1] = 1; /* 协议包类型为1 */
		datas[2] = 0; /* 无后续包 */
		datas[3] = 0; /* 密压标志 */
		datas[4] = (byte) ((packNum >>> 8) & 0xFF); /* 包序号 */
		datas[5] = (byte) ((packNum >>> 0) & 0xFF);
		dout.writeInt(10);
		dout.write(datas);
		dout.writeInt(Crc32_Agree.crc32_2(datas, 0, datas.length));
		dout.flush();
	}
	
	/**
	 * @param allDatas
	 * @param i
	 * @param j
	 */
	private void appendBytes(byte[] allDatas, int i, int j) {
		int rLen = recvDatas.length;// 已收长度
		int len = j + rLen;// 接收后的总长度
		byte[] rDatas = new byte[len];
		System.arraycopy(recvDatas, 0, rDatas, 0, rLen);
		System.arraycopy(allDatas, i, rDatas, rLen, j);
		recvDatas = rDatas;
		dataLen += j;
	}
	
	/**
	 * @param allDatas
	 * @param i
	 * @return short
	 */
	private short bytesToShort(byte[] allDatas, int i) {
		int i1 = allDatas[i] & 0xFF;
		int i2 = allDatas[i + 1] & 0xFF;
		return (short) ((i1 << 8) + (i2 << 0));
	}
	
	/**
	 * @param allDatas
	 * @return int
	 */
	private int getCrc(byte[] allDatas) throws IOException {
		if (allDatas.length < 4)
			throw new IOException("crc校验时数据长度太小");
		int i1 = allDatas[allDatas.length - 4] & 0xFF;
		int i2 = allDatas[allDatas.length - 3] & 0xFF;
		int i3 = allDatas[allDatas.length - 2] & 0xFF;
		int i4 = allDatas[allDatas.length - 1] & 0xFF;
		return ((i1 << 24) + (i2 << 16) + (i3 << 8) + (i4 << 0));
	}
	
	/**
	 * @param allDatas
	 * @return byte[]
	 */
	private byte[] natpRecvFile(byte[] allDatas, DataInputStream din) {
		// 获取主机地址
		byte[] hostName = new byte[15];
		System.arraycopy(allDatas, 7, hostName, 0, hostName.length);
		String host = new String(hostName).trim();
		setFileServerName(host);
		return allDatas;
	}
	
	/**
	 * @return _fileServerName
	 */
	public String getFileServerName() {
		return _fileServerName;
	}

	/**
	 * @param string
	 */
	public void setFileServerName(String string) {
		_fileServerName = string;
	}
	
	public OutputStream getOutputStream(){
		return this._outputStream;
	}
	
	public void setOutputStream(OutputStream outputStream){
		this._outputStream=outputStream;
	}
	
	public InputStream getInputStream(){
		return this._inputStream;
	}
	
	public void setInputStream(InputStream imputstream){
		this._inputStream=imputstream;
	}
	public int getProtoclVersion() {
		return _protoclVersion;
	}
	
	public void setProtoclVersion(int protoclVersion){
		this._protoclVersion=protoclVersion;
	}
	
	/**
	 * <DL>
	 * <DT><B> 读取_natpVersion. </B></DT>
	 * <p>
	 * <DD>读取成员变量_natpVersion</DD>
	 * </DL>
	 * <p>
	 */
	public int getNatpVersion() {
		return _natpVersion;
	}
	/**
	 * <DL>
	 * <DT><B> 设置_natpVersion. </B></DT>
	 * <p>
	 * <DD>设置成员变量_natpVersion</DD>
	 * </DL>
	 * <p>
	 */
	public void setNatpVersion(int version) {
		_natpVersion = version;
	}
	/**
	 * @return _tradeCode
	 */
	public String getTradeCode() {
		return _tradeCode;
	}
	
	/**
	 * @param string
	 */
	public void setTradeCode(String string) {
		_tradeCode = string;
	}
	
	/**
	 * <DL>
	 * <DT><B> 读取_templateCode. </B></DT>
	 * <p>
	 * <DD>读取成员变量_templateCode</DD>
	 * </DL>
	 * <p>
	 */
	public String getTemplateCode() {
		return _templateCode;
	}

	/**
	 * <DL>
	 * <DT><B> 设置_templateCode. </B></DT>
	 * <p>
	 * <DD>设置成员变量_templateCode</DD>
	 * </DL>
	 * <p>
	 */
	public void setTemplateCode(String code) {
		_templateCode = code;
	}
	/**
	 * <DL>
	 * <DT><B> 读取_reserve. </B></DT>
	 * <p>
	 * <DD>读取成员变量_reserve</DD>
	 * </DL>
	 * <p>
	 */
	public String getReserve() {
		return _reserve;
	}

	/**
	 * <DL>
	 * <DT><B> 设置_reserve. </B></DT>
	 * <p>
	 * <DD>设置成员变量_reserve</DD>
	 * </DL>
	 * <p>
	 */
	public void setReserve(String _reserve) {
		this._reserve = _reserve;
	}
	/**
	 * @param string
	 */
	public void setAgentNo(String string) {
		_agentNo = string;
	}

	/**
	 * @param string
	 */
	public void setAreaNo(String string) {
		_areaNo = string;
	}
	/**
	 * @param string
	 */
	public void setBankNo(String string) {
		_bankNo = string;
	}
	public String getAgentNo() {
		return _agentNo;
	}

	/**
	 * @return _areaNo
	 */
	public String getAreaNo() {
		return _areaNo;
	}

	/**
	 * @return _bankNo
	 */
	public String getBankNo() {
		return _bankNo;
	}
	/**
	 * @return _businessType
	 */
	public String getBusinessType() {
		return _businessType;
	}

	/**
	 * @return _tellerNo
	 */
	public String getTellerNo() {
		return _tellerNo;
	}
	
	public void setTellerNo(String string) {
		_tellerNo = string;
	}
	public void setBusinessType(String string) {
		_businessType = string;
	}
}
