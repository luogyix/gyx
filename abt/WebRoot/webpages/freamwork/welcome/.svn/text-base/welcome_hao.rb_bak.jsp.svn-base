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
<html>
	<head>
		<base href="<%=basePath%>">
		<title><s:text name="welcome.title"/></title>
	
		<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
		<link rel="shortcut icon" href="<%=basePath%>images/favicon.ico">
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.gzjs"></script>
		<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
		<style type="text/css">  
	      .user{ background:url(<%=basePath%>images/user.gif) no-repeat 1px 2px; }  
	      .key{ background:url(<%=basePath%>images/key.gif) no-repeat 1px 2px; }  
	      .user,.key,.key{background-color:#FFFFFF;padding-left:20px;font-weight:bold;color:#000033;}  
	    </style> 
		<script type="text/javascript">
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.onReady(loadPage);

		function loadPage(){
			var clientHeight = document.body.clientHeight;
			var clientWidth  = document.body.clientWidth;
			var logonWindow = new Ext.Window({
				title : '<s:text name="welcome.title"/>',
				renderTo : 'win', width :420, height:140, layout:'form', closable:false, resizable:false, draggable:false, buttonAlign:'center',
				items:[
					{xtype:'form', height : 100, bodyStyle:'padding-top:6px',defaultType:'textfield',labelAlign:'right',labelWidth:75,  
						labelPad:0,frame:true,defaults:{allowBlank:false,width:280},  
			             items:[
							{cls:'user',id:'username',name:'username',fieldLabel:'<s:text name="welcome.logonAccount"/>',blankText:'用户名不能为空'}, 
							{cls:'key',id:'password',name:'password',fieldLabel:'<s:text name="welcome.logonPassword"/>',blankText:'密码不能为空',inputType:'password',
								   listeners:{   
								           specialkey:function(field,e){   
								                if (e.getKey()==Ext.EventObject.ENTER){   
								                	onuserlogonclicked();   
								               }   
								            }   
								       }  
							}
						]
					}
				],
				buttons:[
					{id:'submit',name:'submit',text:'<s:text name="welcome.logon"/>', handler:onuserlogonclicked}
				]
			});
			logonWindow.setPosition(clientWidth/2 - logonWindow.getSize().width/2, clientHeight/2 - logonWindow.getSize().height/2);
			logonWindow.show();

			function onuserlogonclicked(){
				//Ext.getCmp("submit").hide();//隐藏按钮
				
				var submitdata = {};
				submitdata['usercode'] = Ext.getCmp('username').getValue();   
				submitdata['password'] = Ext.getCmp('password').getValue();
			
				requestAjax('<%=basePath%>/welcome/logon_logon',submitdata,function(sRet){
					document.getElementById('userinfo').value = Ext.util.JSON.encode(sRet);
					document.forms[1].submit();
				});
			}
		}
		</script>
		
		
	</head>

	<body>
		<img src="<%=basePath%>images/login1.jpg" style="width:100%;height:100%"/>
		<div id="win"></div>
		<s:form action="/welcome/logon_redirect" namespace="/system">
			<s:hidden id="userinfo" name="userinfo"></s:hidden>
		</s:form>
	</body>
</html>
