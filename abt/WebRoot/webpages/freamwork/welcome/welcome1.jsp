<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<link rel="shortcut icon" href="<%=basePath%>images/application/favicon.ico">
		<title>欢迎</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<style type="text/css">
			#welcome * {
				margin:6%;
				padding:0px;
				vertical-align:middle;
			}
			#welcome{
				width:100%;
				height:100%;
				text-align:center;
			}
     	</style>
	</head>

	<!--body style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='<%=basePath%>/images/application/login.jpg', sizingMethod='scale')">
	</body-->
	<body>
    	<div id="welcome">
    		<img src="<%=basePath%>/images/application/web-welcom3.jpg" alt=""/>
    	</div>
	</body>
	
</html>
