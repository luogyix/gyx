package com.agree.framework.communication.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpSentRev {

	public static String GET_URL = "http://ip:port/abt/logon_pdlogin";

	public static String POST_URL = "http://ip:port";
	
	private String jsonConf;
	
	public static String getGET_URL() {
		return GET_URL;
	}

	public static void setGET_URL(String gETURL) {
		GET_URL = gETURL;
	}

	public static String getPOST_URL() {
		return POST_URL;
	}

	public static void setPOST_URL(String pOSTURL) {
		POST_URL = pOSTURL;
	}

	private static final Logger logger = LoggerFactory.getLogger(HttpSentRev.class);

	
	@Deprecated
	public String readContentFromPost(JSONObject recvJson,Map<String, String> confHttpAfa) throws IOException{

		// Post请求的url，与get不同的是不需要带参数
		String url = new String("http://" + confHttpAfa.get("ip") + ":" + confHttpAfa.get("port"));
		URL postUrl = new URL(url);
		// 打开连接
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		// 打开读写属性，默认均为false
		connection.setDoOutput(true);
		connection.setDoInput(true);
		// 设置请求方式，默认为GET
		connection.setRequestMethod("POST");
		// Post 请求不能使用缓存
		connection.setUseCaches(false);
		// URLConnection.setFollowRedirects是static 函数，作用于所有的URLConnection对象。
		// connection.setFollowRedirects(true);
		// URLConnection.setInstanceFollowRedirects 是成员函数，仅作用于当前函数
		connection.setInstanceFollowRedirects(true);
		// 配置连接的Content-type，配置为application/x-
		// www-form-urlencoded的意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码
		connection.setRequestProperty(" Content-Type "," application/x-www-form-urlencoded ");
		// 连接，从postUrl.openConnection()至此的配置必须要在 connect之前完成，
		// 要注意的是connection.getOutputStream()会隐含的进行调用 connect()，所以这里可以省略
		// connection.connect();
		StringBuffer sf = new StringBuffer();
		DataOutputStream out = null;
		InputStreamReader in = null;
		BufferedReader reader = null;
		try {
			out = new DataOutputStream(connection.getOutputStream());
			// DataOutputStream.writeBytes将字符串中的16位的 unicode字符以8位的字符形式写道流里面
			out.write(recvJson.toString().getBytes());
			out.flush();
			 // flush and close
			in = new InputStreamReader(connection.getInputStream());
			reader = new BufferedReader(in);
			String line;
			logger.info(" ============================= ");
			logger.info(" Contents of post request ");
			logger.info(" ============================= ");
			while ((line = reader.readLine()) != null) {
				sf.append(line);
				logger.info(line);
			}
			logger.info(" ============================= ");
			logger.info(" Contents of post request ends ");
			logger.info(" ============================= ");
		} catch (Exception e) {
			logger.error("abt发送http请求出错",e);
			JSONObject retJson = new JSONObject();
			retJson.element("error", "abt-->afa http通讯异常");
			return retJson.toString();
		}finally{
			if(reader != null){
				reader.close();
			}
			if(in != null){
				in.close();
			}
			if(out != null){
				out.close();
			}
		}
		connection.disconnect();
		return sf.toString();
	}
	
	public String readContentFromPost(JSONObject recvJson) throws IOException {
		
		List<String> list = new Vector<String>();
		//afa.json.cfg=10.55.80.67:9308,10.55.80.64:9308
		String[] servers = jsonConf.split(",");
		for (int i = 0; i < servers.length; i++) {
			list.add(servers[i]);
		}
		List<String> serverList = new Vector<String>(list.size());
		do {
			int num = Math.abs(new Random().nextInt(list.size()));
			serverList.add(list.remove(num));
		} while (list.size()>0);
		StringBuffer sb = new StringBuffer();
		
		while (serverList.size()>0) {
			logger.info("send to server :" + serverList.get(0));
			String[] server = serverList.get(0).split(":");
			if(server.length<3){
				logger.error("conf.properties中配置文件出错,格式应为   IP地址:端口号:超时时间,服务器2 IP地址:服务器2端口号:超时时间");
				//{"H_ret_desc":"账号或密码输入错误!","H_ret_code":"IQD010","H_ret_status":"F"}
				JSONObject retJson = new JSONObject();
				retJson.element("error", "abt-->afa http通讯异常");
				return retJson.toString();
			}
			// Post请求的url，与get不同的是不需要带参数
			String url = new String("http://" + server[0] + ":" + server[1]);
			URL postUrl = new URL(url);
			// 打开连接
			logger.info(" ======== Contents of post request ========== ");
			long stime = System.currentTimeMillis();
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			// 打开读写属性，默认均为false
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 设置请求方式，默认为GET
			connection.setRequestMethod("POST");
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			connection.setConnectTimeout(Integer.parseInt(server[2]));
			// URLConnection.setFollowRedirects是static 函数，作用于所有的URLConnection对象。
			// connection.setFollowRedirects(true);
			// URLConnection.setInstanceFollowRedirects 是成员函数，仅作用于当前函数
			connection.setInstanceFollowRedirects(true);
			// 配置连接的Content-type，配置为application/x-
			// www-form-urlencoded的意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码
			connection.setRequestProperty(" Content-Type "," application/x-www-form-urlencoded ;charset=UTF-8");
			// 连接，从postUrl.openConnection()至此的配置必须要在 connect之前完成，
			// 要注意的是connection.getOutputStream()会隐含的进行调用 connect()，所以这里可以省略
			sb = new StringBuffer();
			DataOutputStream out = null;
			InputStreamReader in = null;
			BufferedReader reader = null;
			try {
				out = new DataOutputStream(connection.getOutputStream());

				if(logger.isInfoEnabled()){
					logger.info("发送的Json报文内容：" + recvJson.toString() );
				}
				
				out.write(recvJson.toString().getBytes());
				out.flush();
				in = new InputStreamReader(connection.getInputStream());
				reader = new BufferedReader(in);
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				
				if(logger.isInfoEnabled()){
					logger.info("收到的Json报文内容：" + sb.toString());
				}
				logger.info(" =====  Contents of post request ends  ======= ");
				logger.info("http service timing "+(System.currentTimeMillis() - stime)+" ms");
				break;
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				sb.append("{\"error\":\"通讯失败,向服务器发送http请求出错\"}");
				if (serverList.size()==1) {
					StringBuffer sblog = new StringBuffer();
					sblog.append("abt-->afa http通讯异常,节点全部无法连接").append("\n");
					for(int i =0;i<serverList.size();i++)
					{
						sblog.append(serverList.get(i)).append("\n");
					}
					logger.error(sblog.toString());
					JSONObject retJson = new JSONObject();
					retJson.element("error", "abt-->afa http通讯异常");
					return retJson.toString();
				} else {
					logger.error("abt-->afa http通讯异常,地址:" + serverList.get(0));
				}
				serverList.remove(0);
			}finally{
				if(reader != null){
					reader.close();
				}
				if(in != null){
					in.close();
				}
				if(out != null){
					out.close();
				}
			}
		}
		return sb.toString();
	}

	public String getJsonConf() {
		return jsonConf;
	}

	public void setJsonConf(String jsonConf) {
		this.jsonConf = jsonConf;
	}
}