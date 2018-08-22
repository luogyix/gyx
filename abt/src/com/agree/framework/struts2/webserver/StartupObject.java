package com.agree.framework.struts2.webserver;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.agree.abt.model.confManager.FunctionCfg;
import com.agree.framework.communication.socket.MessServer;
import com.agree.framework.communication.socket.SocketServer;
import com.agree.framework.dao.core.IHibernateGenericDao;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.web.form.administration.Dictionary;
import com.agree.framework.web.form.administration.Module;
import com.agree.framework.web.form.administration.TBsmsDictinfo;
import com.agree.framework.web.form.administration.Unit;
import com.agree.util.CheckIP;
import com.agree.util.IDictABT;
import com.yongboy.socketio.MainServer;
import com.yongboy.socketio.test.WhiteboardHandler;

/**
 * @ClassName: StartupObject 
 * @company agree   
 * @author haoruibing   
 * @date 2011-7-29 下午02:04:00 
 *
 */
public class StartupObject implements IStartupObject {
	
	private static final Logger logger = LoggerFactory.getLogger(StartupObject.class);
	
	protected IHibernateGenericDao sqlDao_h;
	private SocketServer socket; //afa推送到abt的端口
	private MessServer messSocket;//afa推送到手持的端口
	private MainServer chatServer;//SOCKETIO
	private ABTComunicateNatp cona;
	public MessServer getMessSocket() {
		return messSocket;
	}
	
	public void setMessSocket(MessServer messSocket) {
		this.messSocket = messSocket;
	}
	/**
	 * @Title: initializeContextVariables 
	 * @Description: 初始化系统参数
	 * @param @param ServletContext    参数 
	 * @return void    返回类型 
	 * @throws
	 */
	public void initializeContextVariables(ServletContext context)
	{
		try {
			//加载机构信息
			setUnits(context);
			//将字典加载进内存
			setSystemDict(context);
			//将全部权限信息放入内存，以便校验权限
			setMouduleList(context);
			//注册接入数据
			regInterface();
			//加载参数化开关
			setParameters(context);
			//启动socket监听服务
			logger.info("----启动socket监听服务----");
			//afa向abt推送管理端的消息，IE端使用，启动abt监听
			socket.startServer();
			//afa向abt推送手持端的消息，启动手持推送监听
			messSocket.startServer();
			openSocketIO();//启动socketio推送监听
		logger.info("------加载NATP推送初始信息------");
			setNatps(context);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	/**
	 * 启动socketio,开启手持推送功能
	 */
	private void openSocketIO() {
		try {
			// 调用SOCKETIO的服务方法，运行start程序
			WhiteboardHandler pmb=new WhiteboardHandler();
			String ip = CheckIP.getRealIp();
			pmb.setServerIp(ip);
			String port = System.getProperty("socketio.port");
			if(port == null)
			{
				throw new Exception("socketio.port not config,please connect administrator.");
			}
//			Properties prop = new Properties ();
//			ClassPathResource cr = new ClassPathResource("conf.properties");
//			prop.load(cr.getInputStream());
//			String port = prop.getProperty("socketio.port");
			chatServer = new MainServer(pmb, Integer.parseInt(port));
			chatServer.start();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	/** 
	 * 设置启动参数(软硬件分离)
	 * @Title: setParameters 
	 * @param @param context    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void setParameters(ServletContext context) {
		List<FunctionCfg> unitList=this.sqlDao_h.getRecordList("from FunctionCfg");
		boolean machine_view_flag = false;
		boolean queue_rules_flag = false;
		boolean password_rules_flag = false;
		for (FunctionCfg functionCfg : unitList) {
			if ("01".equals(functionCfg.getFunction_flag())) {
				//此数据是界面配置开关
				if("1".equals(functionCfg.getFunction_status())){
					machine_view_flag = true;
				}
			} else if("02".equals(functionCfg.getFunction_flag())){
				//此数据是叫号规则配置开关
				if("1".equals(functionCfg.getFunction_status())){
					queue_rules_flag = true;
				}
			}else if ("03".equals(functionCfg.getFunction_flag())){
				//此数据是管理台密码控制功能配置开关
				if("1".equals(functionCfg.getFunction_status())){
					password_rules_flag = true;
				}
			}
		}
		context.setAttribute(ApplicationConstants.MACHINEVIEWFLAG, machine_view_flag);
		context.setAttribute(ApplicationConstants.QUEUERULESFLAG, queue_rules_flag);
		context.setAttribute(ApplicationConstants.PASSWORDRULESFLAG,password_rules_flag);
	}
	/**
	 * 接入注册
	 * @throws Exception 
	 */
	public void regInterface() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		
		try {
//			1		oid	设备编号	Max100Text	[1..1]	
//	         2		devicetype	设备类型	Max10Text	[1..1]	与渠道号相同，后期如果两者不同，需要更新此字段
//	         3		deviceip	设备ip	Max16Text	[1..1]	
//	         4		deviceport	设备端口	Max5Text	[1..1]	
//	         5		datacondition	设备过滤条件	Max1000Text	[1..1]	网点号，排队机编号等可以标识设备的字段
//	         6		deviceaddition	设备附加信息	Max1000Text	[0..1]	
//	         7						
			cona.reInit();
			cona.setHeader("ibp.pub.0000.01", "000000", "admin", IDictABT.CHANNEL_BMS);
			//提取配置文件
			Properties prop = new Properties ();
			ClassPathResource cr = new ClassPathResource("conf.properties");
			prop.load(cr.getInputStream());
			String ip = CheckIP.getRealIp();
			if(prop.getProperty("register.webip") != null && !prop.getProperty("register.webip").equals(""))
			{
				ip = prop.getProperty("register.webip");
			}
			
			cona.set("deviceip", ip);
			cona.set("oid", prop.getProperty("register.oid") + "_" + ip);
			cona.set("devicetype", prop.getProperty("register.devicetype"));
			String port = System.getProperty("afa.communication.port");
			if(port == null)
			{
				throw new Exception("afa.communication.port not config,please connect administrator.");
			}
			cona.set("deviceport", port);
			cona.set("datacondition", prop.getProperty("register.datacondition"));
			cona.set("deviceaddition", prop.getProperty("register.deviceaddition"));
			
			logger.info("-->WEB正在注册接入设备 Time:"+sdf.format(new Date())+"<--");
			cona.exchange();
			logger.info("-->WEB注册接入设备:成功  Time:"+sdf.format(new Date())+"<--");
		} catch (Exception e) {
			logger.info("-->WEB注册接入设备:失败 Time:"+sdf.format(new Date())+"<--");
			logger.error(e.getMessage(),e);
		}
	}
	/** 
	 * @Title: setSystemDict 
	 * @Description: 设置系统字典
	 * @param @param context
	 * @param @throws AppException    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void setSystemDict(ServletContext context) throws AppException {
		List<TBsmsDictinfo> dictList = 
			sqlDao_h.getRecordList("from TBsmsDictinfo where enable='"+ApplicationConstants.DICTINFOENABLE+"' order by dictvalue  asc");
		Map<String,List<Dictionary>> dictMap=new HashMap<String,List<Dictionary>>();
		for(TBsmsDictinfo entry : dictList){
			String pubcodeitem=entry.getDicttype();
			List<Dictionary> tempList=new ArrayList<Dictionary>();
			for(TBsmsDictinfo d : dictList){
				if(pubcodeitem.equals(d.getDicttype())){
					Dictionary dict = new Dictionary();
					String code = d.getDictvalue();
					dict.setDictType(d.getDicttype());
					dict.setDictValue(code);
					dict.setCleanDesc(d.getDictvaluedesc());
					dict.setDictValueDesc(code+"-"+d.getDictvaluedesc());
					tempList.add(dict);
				}
			}
			dictMap.put(pubcodeitem, tempList);
		}
		context.setAttribute(ApplicationConstants.SYSTEMDICTIONARY,dictMap);
	}
	/** 
	 * @Title: setUnits 
	 * @Description: 设置部门集合
	 * @param @param context    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void setUnits(ServletContext context) {
		
		//List<Unit> unitList=this.sqlDao_h.getRecordList("from Unit  order by  unitlevel asc,unitid asc,unitorder asc");//,substr(unitid,1,2) asc
		//获取所有机构
		List<Unit> unitList=this.sqlDao_h.getRecordList("from Unit order by  unitlevel");//,substr(unitid,1,2) asc
		
		Unit unitTree = new Unit();
		List<Unit> delList = new ArrayList<Unit>();
		for (Unit unit : unitList) {
			if(unit.getUnitlevel()!=null){
				if (Integer.parseInt(unit.getUnitlevel()) == 0) {//root unit
					unitTree=unit;
				} else {//children
					Unit temp_unit = unitTree.findNodeById(unit.getParentunitid());
					if(temp_unit!=null){
						unit.setParentName(temp_unit.getUnitname());
						unit.setParentNode(temp_unit);//添加父节点 2015-12-6 by xiwang
						temp_unit.addChildNode(unit);
					}
				}
			}else{
				delList.add(unit);
			}
		}
		unitList.removeAll(delList);
		context.setAttribute(ApplicationConstants.SYSTEMUNITTREE, unitTree);
		context.setAttribute(ApplicationConstants.SYSTEMUNITS, unitList);
	}
	/** 
	 * @Title: setMouduleList 
	 * @Description: 设置菜单集合
	 * @param @param context
	 * @param @throws AppException    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void setMouduleList(ServletContext context) throws AppException {
		List<Module> list = sqlDao_h.getRecordList("from Module order by modulelevel,moduleorder asc");
		List<Module> reaultList=new ArrayList<Module>();
		if(list != null && list.size() != 0){
			for(Module module:list){//取出菜单信息
				boolean isleaf=true;
				for(Module module1:list){//循环判断，是否有该菜单的子节点，有的话则修改标志为false，
					if(module1.getParentmoduleid().equals(module.getModuleid())){
						isleaf=false;
						break;
					}
				}
				module.setIsleaf(isleaf);
				reaultList.add(module);
			}
		}
		context.setAttribute(ApplicationConstants.MODULELIST,reaultList);
	}
	
	/**
	 * @Title: setSqlDao_h 
	 * @Description: 
	 * @param @param sqlDao_h    参数 
	 * @return void    返回类型 
	 * @throws
	 */
	public void setSqlDao_h(IHibernateGenericDao sqlDao_h) {
		this.sqlDao_h = sqlDao_h;
	}
	
	@SuppressWarnings("rawtypes")
	public void setNatps(ServletContext context) throws AppException {
		List<Map> list = new ArrayList<Map>();
		context.setAttribute(ApplicationConstants.NATP_CACHE,list);
	}

	public void initApplicationVariables(ServletContext context) throws AppException {
		
	}
	public void setAppDict(ServletContext context) throws AppException {
		
	}
	
	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
	
	public void setSocket(SocketServer socket) {
		this.socket = socket;
	}
	
	@Override
	public void destory() {
		if(socket != null)
		{
			logger.info("destory afa向abt推送管理端的消息监听端口");
			socket.stopServer();
		}
		if(messSocket != null)
		{
			logger.info("destory afa向abt推送手持端的消息监听端口");
			messSocket.stopServer();
		}
		if(chatServer != null)
		{
			logger.info("destory socketio监听端口");
			chatServer.stop();
		}
	}
}
