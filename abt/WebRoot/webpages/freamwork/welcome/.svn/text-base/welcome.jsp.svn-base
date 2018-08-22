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
		<meta http-equiv="x-ua-compatible" content="ie=7,chrome=1" />
		<base href="<%=basePath%>" />
		<title><s:text name="welcome.title" /></title>
		<style type="text/css">
table td {
	font-size: 14px;
	font-family: '黑体', '宋体', sans-serif;
}
</style>

		<link href="<%=basePath%>images/application/ys.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="extjs/resources/css/ext-all.css" />
		<link rel="shortcut icon"
			href="<%=basePath%>images/application/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="selfcss/login.css" />
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.gzjs"></script>
		<script type="text/javascript" src="selfjs/common/commonajax.js"></script>

		<script type="text/javascript">
			// 监听回车事件
			document.onkeydown = function(e) {  
	    		var theEvent = e || window.event;  
	    		var code = theEvent.keyCode || theEvent.which || theEvent.charCode;  
	    		var activeElementId = document.activeElement.id;// 当前处于焦点的元素的id
	   			if (code == 13 && activeElementId == "username") {  
	        		passFocus();// 要触发的方法
	        		return false;  
	    		}
	    		if (code == 13 && activeElementId == "password") {  
	    			logon();
	        		return false;  
	    		}  
	   			return true;  
			} 

			function passFocus(){
				var input = Ext.getDom("password");
				input.focus();
			}
			function logon(){				
				var submitdata = {};
				submitdata['usercode'] = Ext.getDom('username').value;
				submitdata['password'] = Ext.getDom('password').value;
				if(submitdata['usercode']==null||submitdata['usercode']==''){
					Ext.MessageBox.alert('提示','请输入用户名');
					return;
				}
				if(submitdata['password']==null||submitdata['password']==''){
					Ext.MessageBox.alert('提示','请输入密码');
					return;
				}
				requestAjax('<%=basePath%>/welcome/logon_logon', submitdata, function(
				sRet) {
			var flag = sRet.flag;
			if (flag == "systemuserpass") {
				document.getElementById('userinfo').value = Ext.util.JSON
						.encode(sRet);
				document.forms[1].submit();
			} else {
				document.getElementById('userinfo').value = Ext.util.JSON
						.encode(sRet);
				document.forms[0].submit();
			}
		});
	}
	window.onload = function() {
		var input = Ext.getDom("username");
		input.focus();
	}
</script>

	</head>

	<body>
		<div class="login">
			<div class="login_input">
				<table class="table">
					<tr>
						<td>用户名</td>
						<td>
							<input id="username" class="input1" type="text" onBlur="(document.getElementById('password').value='')" />
						</td>
						<td>密 码</td>
						<td>
							<input name="password" class="input2" type="password" id="password" />
						</td>
						<td>
							<button type="button" onclick="logon()" style="cursor: pointer"	class="login_but"></button>
						</td>
					</tr>
				</table>
				<s:form action="logon_redirect">
					<s:hidden id="userinfo" name="userinfo"></s:hidden>
				</s:form>
				<s:form id="1" action="logon_redirectToChangePwd">
					<s:hidden id="userinfo" name="userinfo"></s:hidden>
				</s:form>
			</div>
		</div>
	</body>
</html>
