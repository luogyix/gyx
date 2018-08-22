package com.agree.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CheckIP {
	/**
	 * ip地址的校验经常使用，目前我们主要是IPv4的ip地址，下面的校验考虑了首位不能为0，最大不能操作255的规则，代码如下。 检查输入的IP
	 * V4地址是否合法 regex为:
	 * ((25[0-5]|2[0-4]\d|1\d{2}|[1-9]\d|\d)\.){3}(25[0-5]|2[0-4
	 * ]\d|1\d{2}|[1-9]\d|\d) 提示：在java中\需要转移。在正则表达式中(.)需要转义，否则（.）代表任意字符。
	 * 在java.net包中的类：把01.10.151.163和001.10.151.163都正确解析为1.10.151.163。 故regex修正为：
	 * 
	 * @param ipAddress
	 *            ipv4地址
	 * @return
	 */
	static final String regex = "((25[0-5]|2[0-4]\\d|1\\d{2}|0?[1-9]\\d|0?0?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|0?[1-9]\\d|0?0?\\d)";
	static final Pattern pattern = Pattern.compile(regex);
	private static Log logger = LogFactory.getLog(CheckIP.class);	
	public static boolean checkIPV4(String ipAddress) {
		Matcher m = pattern.matcher(ipAddress);
		return m.matches();

	}

	public static boolean checkIPv6(String address) {
		boolean result = false;
		String regHex = "(\\p{XDigit}{1,4})";
		String regIPv6Full = "^(" + regHex + ":){7}" + regHex + "$";
		String regIPv6AbWithColon = "^(" + regHex + "(:|::)){0,6}" + regHex
				+ "$";
		String regIPv6AbStartWithDoubleColon = "^(" + "::(" + regHex
				+ ":){0,5}" + regHex + ")$";
		String regIPv6 = "^(" + regIPv6Full + ")|("
				+ regIPv6AbStartWithDoubleColon + ")|(" + regIPv6AbWithColon
				+ ")$";

		if (address.indexOf(":") != -1) {
			if (address.length() <= 39) {
				String addressTemp = address;
				int doubleColon = 0;
				while (addressTemp.indexOf("::") != -1) {
					addressTemp = addressTemp.substring(addressTemp
							.indexOf("::") + 2, addressTemp.length());
					doubleColon++;
				}
				if (doubleColon <= 1) {
					result = address.matches(regIPv6);
				}
			}
		}

		return result;
	}

	/**
	 * 输入的ip是IPv4和IPv6中的任意一种检查是否有效
	 * 
	 * @param ipAddress
	 * @return
	 */
	public static boolean chekIPV4AndIPV6(String ipAddress) {
		try {
			if (checkIPV4(ipAddress))
				return true;
			if (checkIPv6(ipAddress))
				return true;
		} catch (Throwable e) {

			return false;
		}
		return false;
	}

	/**
	 * 转换IP为标准格式 把01.10.151.163和001.10.151.163都正确解析为1.10.151.163
	 * 
	 * @param ipAddress
	 * @return
	 */
	public static String transfIP(String ipAddress) {
		try {
			InetAddress addressIPv6 = null;
			Inet4Address IPv4 = null;

			addressIPv6 = InetAddress.getByName(ipAddress);

			if (addressIPv6 instanceof Inet6Address) {
				ipAddress = addressIPv6.getHostAddress();

			}
			if (addressIPv6 instanceof Inet4Address) {
				IPv4 = (Inet4Address) addressIPv6;
				ipAddress = IPv4.getHostAddress();
			}
		} catch (Throwable e) {
			return ipAddress;
		}
		return ipAddress;
	}

	private static String add0(String str, int count) {
		for (int i = 0; i < count; i++) {
			str = "0" + str;
		}
		return str;
	}

	/**
	 * 把01.10.151.163和1.10.151.163都正确解析为001.010.151.163
	 * 将ipV4地址转换成xxx.xxx.xxx.xxx格式,少于3位则前面补0
	 * 
	 * @param ipV4
	 * @return
	 */
	public static String transfStandardIPV4(String ipV4) {
		if (ipV4 != null) {
			if (checkIPV4(ipV4)) {
				String[] strs = ipV4.split("\\.");
				String ip = "";
				for (int i = 0; i < strs.length; i++) {
					String tmp = strs[i];
					if (i != 0) {
						ip += ".";
					}
					if (tmp.length() < 3) {
						ip += add0(tmp, 3 - tmp.length());
					} else {
						ip += tmp;
					}
				}
				return ip;
			}
		}
		throw new IllegalArgumentException("无效的IPV4格式");
	}

	/**
	 * 得到本地系统真实IP
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static String getRealIp() throws SocketException {
		String localip = null;
		String netip = null;
		Enumeration<NetworkInterface> nets = NetworkInterface
				.getNetworkInterfaces();
		InetAddress ip = null;
		boolean finded = false;
		while (nets.hasMoreElements() && !finded) {
			NetworkInterface ni = nets.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
						&& ip.getHostAddress().indexOf(":") == -1 && !ip.toString().startsWith("/172")) {
					netip = ip.getHostAddress();
					finded = true;
					break;
				} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
						&& ip.getHostAddress().indexOf(":") == -1) {
					localip = ip.getHostAddress();
				}
			}
		}
		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}

	public static String getRealIP2() throws IOException {
		String os = System.getProperty("os.name");
		String address = "";
		if (os != null && os.startsWith("Windows")) {
			String command = "cmd.exe /c ipconfig /all";
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(p
					.getInputStream()));
			String line = null;
			boolean find = false;
			while ((line = br.readLine()) != null) {
				if (line.indexOf("本地连接:") > 0) {
					find = true;
				}
				if (find == true && line.indexOf("IP Address") > 0) {
					int index = line.indexOf(":");
					index += 2;
					logger.info(line.substring(index));
				}
			}
			br.close();
		}
		return address;
	}

	// /**
	// * 得到本地ip的远程地址，如果没有返回127.0.0.1 注意，如果包含多个将只取第一个，可以添加过滤器显示的过滤无用的IP地址
	// * @param ipFilter 需要过滤的ip地址集合
	// * @return
	// */
	// public static String getRemoteIpv4(String [] ipFilters) {
	// List<String> ls=queryIpv4();
	// for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
	//				
	// String string = (String) iterator.next();
	// if(ipFilters!=null&&ipFilters.length>0){
	// for (int i = 0; i < ipFilters.length; i++) {
	// String filterIp=ipFilters[i];
	// if(string.equalsIgnoreCase(filterIp)){
	// continue;
	// }
	// }
	// }
	// return string;
	// }
	// return "127.0.0.1";
	// }
	//
	// private static List<String> queryIpv4() {
	// List<String> list = new ArrayList<String>();
	// Enumeration<NetworkInterface> netInterfaces = null;
	// try {
	//
	// netInterfaces = NetworkInterface.getNetworkInterfaces();
	// while (netInterfaces.hasMoreElements()) {
	// NetworkInterface ni = netInterfaces.nextElement();
	// // logger.info("DisplayName:" + ni.getDisplayName());
	// // logger.info("Name:" + ni.getName());
	// Enumeration<InetAddress> ips = ni.getInetAddresses();
	// while (ips.hasMoreElements()) {
	// String ip = ips.nextElement().getHostAddress();
	// if (CheckIP.checkIPV4(ip)) {
	// if(!"127.0.0.1".equalsIgnoreCase(ip))
	// list.add(ip);
	// }
	// // logger.info("IP:"
	// // + ips.nextElement().getHostAddress());
	// }
	// }
	//
	// } catch (Exception e) {
	// 		logger.error(e.getMessage(),e);
	// }
	// return list;
	// }
//	public static void main(String[] args) throws SocketException {
//		System.out.println(getRealIp());
//		//System.out.println(transfStandardIPV4("192.168.1.1"));
//	}
}