<%@ page language="java" import="java.util.*,com.agree.framework.struts2.webserver.ApplicationConstants,com.agree.framework.web.form.administration.User" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<%
User user = (User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
@SuppressWarnings("unchecked")
List userIdCheck = (List)request.getSession().getServletContext().getAttribute(ApplicationConstants.LOGONUSERID);
if(userIdCheck != null && user != null ){
	userIdCheck.remove(request.getSession());
}
request.getSession().getServletContext().setAttribute(ApplicationConstants.LOGONUSERID,userIdCheck);
request.getSession().invalidate();

%>
<script type="text/javascript">
if(${param.si}==0){
window.opener=null;
window.open('','_self');
window.close(); 
}
if(${param.si}==1){
window.opener=null;
window.open('','_self');
var ss = window.location.href;
var sa = ss.substring(0,ss.length-42);
window.location.href(sa);
}
if(${param.si}==2){
	window.opener=null;
	window.open('','_self');
	window.close();
}
</script>
</body>
</html>