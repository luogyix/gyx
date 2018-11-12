<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+
						request.getServerName()+":"+
						request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
<link rel="stylesheet" href="css/login.css" type="text/css">
<script type="text/javascript" src="js/selfjs/common/commonajax.js"></script>
<script type="text/javascript" src="js/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="js/extjs/ext-all.gzjs"></script>
<script type="text/javascript" src="js/jquery/jquery.js"></script>
<script type="text/javascript">
	//监听回车事件
	document.onkeydown = function(e) {  
		var theEvent = e || window.event;  
		var code = theEvent.keyCode || theEvent.which || theEvent.charCode;  
		var activeElementId = document.activeElement.id;// 当前处于焦点的元素的id
			if (code == 13 && activeElementId == "user_name") {  
			passFocus();// 要触发的方法
			return false;  
		}
		if (code == 13 && activeElementId == "user_password") {
			document.getElementById("l_button").Focus();
			logon();
			return false;  
		}  
			return true;  
	}
	function passFocus(){
		var input = document.getElementById("user_password");
		//此元素获得焦点
		input.focus();
	}
	function logon(){
		var user_name = document.getElementById("user_name").value;
		var user_password = document.getElementById("user_password").value;
		if(user_name == "" || user_name == null){
			alert("请输入用户名")
			return;
		}
		if(user_password == "" || user_password == null){
			alert("密码不可为空");
			return;
		}
		//获得XMLHTTP对象		
		function creatXmlHttp(){
			if(typeof window.XMLHttpRequest != "undefined"){
				//火狐及IE7(及以上)
				return new XMLHttpRequest();
			}else if(typeof window.ActiveXObject != "undefined"){
				//IE7以下浏览器
				return new ActiveXObject("Microsoft.XMLHTTP");
			}else{
				throw new Error("您的浏览器不支持XHR")
			}
			
		}
		
		var xhr = creatXmlHttp();
		xhr.onreadystatechange = function(){
			/**
			*xhr.readyState，请求执行阶段
			* 0：未初始化。open()方法未被调用
			* 1：启动。已经调用open()方法，但尚未调用send()方法
			* 2：发送。open()及send()方式都以调用。但未接收到响应
			* 3：接收。已经接收到部分响应
			* 4：完成。响应接收完成
			*/
			if(xhr.readyState == 4){
				if((xhr.status >= 200 && xhr.status <300) || xhr.status == 304){
					//alert(xhr.responseText);
					//动态添加元素document.getElementById("table").innerHTML=xhr.responseText;
				}
			}else{
				//alert("请求执行阶段"+xhr.readyState+"；响应数据类型："+typeof xhr.responseText);
			}
		}
		//手动封装用户信息表单数据
		var formData = new FormData();
		formData.append("userid",document.getElementById("user_name").value);
		formData.append("password",document.getElementById("user_password").value);
		var userid = document.getElementById("user_name").value;
		var password = document.getElementById("user_password").value;
		//param1：请求方式（post or get），param2：请求路径，
		//param3：是否以异步方式发送请求，默认为true
		xhr.open("post","logon/logon",true);
		//post请求的参数是放在请求正文 中，需要设置请求正文类型
		xhr.setRequestHeader("Content-Type","application/json");
		//设置参数
		xhr.send("{usercode:'"+userid+"',password:'"+password+"'}");
	}
</script>
</head>
<body>
	
	<div class="Network">
	
	</div>
	<div class="wrapper">
		<div style="height: 400px"></div>
		<div >
			<div class="logon">
				<table id="table">
					<tr>
						<td id="userNameTile"  class="inputTitle" style="color:BlanchedAlmond">
							用户名:
						</td>
						<td>
							<input id="user_name" class="loginInput" type="text" name="user_name" onBlur="(document.getElementById('user_password').value='')">
						</td>
					</tr>
					<tr height=20/>
					<tr>
						<td class="inputTitle" style="color:BlanchedAlmond">
							密码:
						</td>
						<td>
							<input id="user_password" class="loginInput" type="password" name="user_password">
						</td>
					</tr>
					<tr height=20/>
				</table>
				<input id="l_button" class="loginButton" type="button" value="登陆" onclick="logon()" style="cursor: pointer">
				<input id="z_button" class="loginButton" type="button" value="直接进入" style="cursor: pointer">
			</div>
		</div>
		
	</div>
</body>
</html>