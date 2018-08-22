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
	<base href="<%=basePath%>"/>
		
		<link rel="shortcut icon" href="<%=basePath%>images/application/favicon.ico"/>
		<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="selfcss/login.css" />
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.gzjs"></script>
		<script type="text/javascript" src="selfjs2/common/commonajax.js"></script>

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
				submitdata['prcid'] = document.getElementById('sel_project').value;
				if(submitdata['usercode']==null||submitdata['usercode']==''){
					Ext.MessageBox.alert('提示','请输入用户名');
					return;
				}
				if(submitdata['password']==null||submitdata['password']==''){
					Ext.MessageBox.alert('提示','请输入密码');
					return;
				}
				if(submitdata['usercode']=='admin' && submitdata['prcid']==null||submitdata['prcid']=='please choose project'){
					Ext.MessageBox.alert('提示','请选择登入工程');
					return;
				}
				requestAjax('<%=basePath%>welcome/logon_logon',submitdata,function(sRet){
					document.getElementById('userinfo').value = Ext.util.JSON.encode(sRet);
						document.forms[0].submit();
				});
			}
			
			window.onload = function(){
				var input = document.getElementById("username");
				input.focus();
			}
			
			function onusercheckclicked(){				
				var username = document.getElementById('username').value;
				var pwd = document.getElementById('password').value;
				if(!Ext.isEmpty(username) && !Ext.isEmpty(pwd)){
					if(username != 'admin'){
						sel_project.options.length=0;
						sel_project.disabled = true;
					}else{
						var submitdata = {};
						submitdata['usercode'] = username;
						submitdata['password'] = document.getElementById('password').value;
						requestAjax('<%=basePath%>welcome/logon_checkLogon', submitdata, function(sRet) {
						    
							var sel_project = document.getElementById("sel_project");
							if(!Ext.isEmpty(sRet.message)){
								sel_project.options.length=0;
								sel_project.disabled = true;
								Ext.Msg.alert('提示',sRet.message);
							}else{
								sel_project.options.length=0;
								sel_project.disabled = false;
								
								var projectArray = sRet.field2;
								var sel_project = document.getElementById("sel_project");
								sel_project.options.length=0;
								var option = document.createElement("OPTION");
								option.value = 'please choose project';
								option.text = '请选择工程';
								sel_project.options.add(option);
								for(var i=0, j=projectArray.length; i<j; i++){
									var project = projectArray[i];
									var option = document.createElement("OPTION");
									option.value = project.rcid;
									option.text = project.proname;
									sel_project.options.add(option);
								}
								sel_project.disabled = false;
							}
						});
					}
				}else{
					sel_project.options.length=0;
					sel_project.disabled = true;
					return false;
				}
			}
		</script>
	
</head>

<body>
<div class="login">
	<div class="login_input">
		用户名：
	     <input name="" id="username" type="text" onBlur="onusercheckclicked()" class="input1" />
		密码：
		 <input name="password" type="password" class="input2" id="password" onBlur="onusercheckclicked()"/>
    	选择工程：
			<select id="sel_project" style="width:120px; margin-right:5px;" disabled="true"></select>
   		<div class="butdiv">
   			<button type="button" onclick="onuserlogonclicked()"  style="cursor:pointer" class="login_but"></button>
   		</div>
   		<div style="clear:both;"></div>
    	<s:form action="welcome/logon_redirect" namespace="/system">
			<s:hidden id="userinfo" name="userinfo"> </s:hidden>
		</s:form>
    </div>
</div>
</body>
</html>
