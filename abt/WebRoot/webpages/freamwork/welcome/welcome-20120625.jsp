<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=7" />
<title><s:text name="welcome.title"/></title>
	<base href="<%=basePath%>">
		
		<link rel="shortcut icon" href="<%=basePath%>images/application/favicon.ico">
		<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="selfcss/login.css" />
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.gzjs"></script>
		<script type="text/javascript" src="selfjs/common/commonajax.js"></script>

		<script type="text/javascript">
	
			//监听回车事件
			document.onkeydown = function(e) {  
    		var theEvent = e || window.event;  
    		var code = theEvent.keyCode || theEvent.which || theEvent.charCode;  
    		var activeElementId = document.activeElement.id;//当前处于焦点的元素的id  
   			if (code == 13 && activeElementId == "username") {  
        	passFocus();//要触发的方法  
        	return false;  
    		}
    		if (code == 13 && activeElementId == "password") {  
        	onuserlogonclicked();//要触发的方法  
        	return false;  
    		}  
   			return true;  
			} 

			function passFocus(){
				var input = document.getElementById("password");
				input.focus();
			}

			
			function onuserlogonclicked(){				
				var submitdata = {};
				submitdata['usercode'] = document.getElementById('username').value;
				submitdata['password'] = document.getElementById('password').value;
			
				requestAjax('<%=basePath%>/welcome/logon_logon',submitdata,function(sRet){
					document.getElementById('userinfo').value = Ext.util.JSON.encode(sRet);
						document.forms[0].submit();
				});
			}
			
			window.onload = function(){
				var input = document.getElementById("username");
				input.focus();
	
			}
			
			
		</script>
	
</head>
<style  type="text/css">
body,td,th {font-size: 12px;}
body {margin: 0px;background-color: #d6e9f9;background-image: url(<%=basePath%>images/login_bg.gif);background-repeat: repeat-x;background-position: left top;}
.login {filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src="<%=basePath%>images/login.png" );background:none;background-repeat: no-repeat;width:821px;height:568px;margin:0 auto 0 auto;}
.login_input{margin-top:460px;margin-left:232px;position:relative;padding-top: 4px;}
.login_input input {
	width: 128px;
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	background-color: #eef4fa;}
.login_but {
	background-image: url(<%=basePath%>images/but.gif);
	background-repeat: no-repeat;
	height: 28px;
	width: 87px;
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;

}

</style>

<body>
<div class="login">
	<div class="login_input"><input name="" id="username" type="text" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <input name="password" type="password" class="shuru" id="password" />
    	&nbsp;&nbsp;&nbsp;<button type="button" onclick="onuserlogonclicked()"  style="cursor:hand" class="login_but"></button>
    	<s:form action="/welcome/logon_redirect" namespace="/system">
						<s:hidden id="userinfo" name="userinfo"> </s:hidden>
					</s:form>
    </div>
</div>
</body>
</html>
