package com.agree.ts.action;

import com.agree.framework.web.action.base.BaseAction;
import com.agree.ts.service.WorkHandlerService;

/**
 * 手持排队机初始化入口
 * @ClassName: QMInitAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-7-10
 */
public class QMInitAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private WorkHandlerService workHandlerService;
	
	/**
	 * 判断请求sessionId合法
	 * @param msgStr	请求的json报文数据
	 * @return	是否合法
	 * @throws Exception
	 */
//	private boolean checkSession(String msgStr){
//		HttpServletRequest request = super.getRequest();
//		JSONObject recvJson = JSONObject.fromObject(msgStr);
//		Object sessionId = recvJson.get("sessionId");
//		Map<String, HttpSession> sessionMap = (Map<String, HttpSession>)request.getSession().getServletContext().getAttribute(ApplicationConstants.SESSIONMAP);
//		
//		if(sessionMap == null || sessionId == null){
//			return false;
//		}else if(sessionMap.containsKey(sessionId)){
//			HttpSession session = sessionMap.get(sessionId);
//			if(session != null){
//				Long livedTime = session.getLastAccessedTime() - session.getCreationTime();
//				session.setMaxInactiveInterval(new Integer( ((livedTime/1000)+Constants.SESSION_VALIDTIME)+"" ));
//				return true;
//			}else{
//				sessionMap.remove(sessionId);
//				return false;
//			}
//		}else{
//			return false;
//		}
//	}
	
//	/**
//	 * 排队机初始化处理
//	 * @throws UnsupportedEncodingException
//	 * @throws IOException
//	 */
//	public void init()  throws UnsupportedEncodingException, IOException{
//		HttpServletRequest request = super.getRequest();
//		HttpServletResponse response = super.getResponse();
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("UTF-8");
//		
//		JSONObject retJson = new JSONObject();
//		String msgStr = request.getParameter("msg");
//		
//		if(msgStr == null || msgStr.equals("")){
//			retJson.element("H_ret_status", "F");
//			retJson.element("H_ret_code","ACO001");
//			retJson.element("H_ret_desc",Constants.ERRORCODE_MSG.get("ACO001"));
//		}else{
////			if(checkSession(msgStr)){
//				try {
//					retJson = workHandlerService.work(msgStr,getLogonUser(false));
//				} catch (Exception e) {
//					logger.fatal(MobServeAction.class+"业务处理过程报错",e );
//					retJson.element("H_ret_status", "F");
//					retJson.element("H_ret_code","ACO002");
//					retJson.element("H_ret_desc",Constants.ERRORCODE_MSG.get("ACO002")+e.getMessage());
//				}
////			}else{
////				retJson.element("H_ret_status", "F");
////				retJson.element("H_ret_code","ACO004");
////				retJson.element("H_ret_desc",Constants.ERRORCODE_MSG.get("ACO004"));
////			}
//		}
//		PrintWriter pw = super.getResponse().getWriter();
//		pw.print(retJson);
//		pw.flush();
//		pw.close();
//	}

	public WorkHandlerService getWorkHandlerService() {
		return this.workHandlerService;
	}

	public void setWorkHandlerService(WorkHandlerService workHandlerService) {
		this.workHandlerService = workHandlerService;
	}
}
