<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>您未登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		function timeout(){
			var ss = window.location.href;
			var arr = ss.split("/");
		    parent.parent.window.location.href(arr[0]+"/"+arr[1]+"/"+arr[2]+"/"+arr[3]);
	    }
	    
	    window.onload = function(){
	        setTimeout(timeout, '1000');
	    }
	</script>
  </head>
  
  <body>
   您的会话已失效或超时,请重新登录
  </body>
</html>
