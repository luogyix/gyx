<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'userinfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css"/>
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjs/ext-all.gzjs"></script>
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript">
		//var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		function loadPage(){
		var panel = new Ext.Panel({
			layout : 'absolute',
			frame:true,
			//iconCls : 'x-image-cnapslogin',
			//bodyStyle:'padding:10 10 10 10',			
			applyTo :'panel',
			items:[{	
					xtype:'fieldset', 
					title:'个人密码修改',
					baseCls  : 'x-image-cnapslogin',
					//floating : true,
					shadowOffset : 12,
					width:document.body.clientWidth-30,
					height:300,
					x:10,
					y:10,
					items:[
						{bodyStyle:"padding:10 10 10 35%",layout:'form',items:[{xtype : 'textfield',id:'nowPassword1', name:'nowPassword', allowBlank : false, blankText : '请填写当前密码!', fieldLabel:'当前密码', inputType:'password',width:165}]},
						{bodyStyle:"padding:10 10 10 35%",layout:'form',items:[{xtype : 'textfield',id:'updatePassword1', name:'updatePassword', allowBlank : false, blankText : '请填写修改密码!', fieldLabel:'修改密码', inputType:'password',width:165}]},
						{bodyStyle:"padding:10 10 10 35%",layout:'form',items:[{xtype : 'textfield',id:'reviewPassword1', name:'reviewPassword', allowBlank : false, blankText : '请填写确认密码!', fieldLabel:'确认密码', inputType:'password',width:165}]},
						{bodyStyle:"padding:10 10 10 40%",layout:'table',
							items:[
								{text:'修改密码',xtype : 'button',handler : oneditclicked,width:70},
								{width:20},
								{text:'重置',xtype : 'button',handler : function(){
								Ext.getCmp('nowPassword1').reset();
								Ext.getCmp('updatePassword1').reset();
								Ext.getCmp('reviewPassword1').reset();},width:70}
						]}
					]
			}]
		});
		
		
		
		
		
		/**
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var editwindow = new SelfFormWindow('recordEditWindow', '用户密码修改', 600, 400, 200, 1,
				[
				{colIndex:0, field:{xtype : 'textfield',id:'nowPassword1', name:'nowPassword', allowBlank : false, blankText : '请填写当前密码!', fieldLabel:'当前密码', inputType:'password'}},
				{colIndex:0, field:{xtype : 'textfield',id:'updatePassword1', name:'updatePassword', allowBlank : false, blankText : '请填写修改密码!', fieldLabel:'修改密码', inputType:'password'}},
				{colIndex:0, field:{xtype : 'textfield',id:'reviewPassword1', name:'reviewPassword', allowBlank : false, blankText : '请填写确认密码!', fieldLabel:'确认密码', inputType:'password'}}
				],
				[
					{text:'<s:text name="common.button.edit"/>', formBind:true, handler : oneditclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){editwindow.close();}}
				]
			);
			editwindow.open();
			*/
			function oneditclicked(){
				var submitData = new Map();
				submitData.nowPassword = Ext.getCmp('nowPassword1').getValue();
				submitData.updatePassword = Ext.getCmp('updatePassword1').getValue();
				submitData.reviewPassword = Ext.getCmp('reviewPassword1').getValue();
				if(null==submitData.nowPassword||submitData.nowPassword===undefined||submitData.nowPassword.length==0){
					Ext.MessageBox.alert("提示信息","您输入的原密码不能为空,请重新输入！");
					return;
				}
				if(null==submitData.updatePassword||submitData.updatePassword===undefined||submitData.updatePassword.length==0){
					Ext.MessageBox.alert("提示信息","您输入的修改密码不能为空,请重新输入！");
					return;
				}
				if(submitData.updatePassword == submitData.nowPassword){
					Ext.MessageBox.alert("提示信息","您输入的修改密码和原密码相同,请重新输入！");
					return;
				}
				/* var rC = {
				        lW:'[a-z]',
				        uW:'[A-Z]',
				        nW:'[0-9]',
				        sW:'[\\u0020-\\u002F\\u003A-\\u0040\\u005B-\\u0060\\u007B-\\u007E]'
				    }; */
				    var rC = {
					        nW:'[0-9]{6}'
					    };
				function Reg(str, rStr){
				    var reg = new RegExp(rStr);
				    if(reg.test(str)) return true;
				    else return false;
				}
				if(submitData.updatePassword.length != 6){
					Ext.MessageBox.alert("提示信息","密码长度为6位数字!请重新输入");
					return;
				}else{
			        /* var tR = {
			                l:Reg(submitData.updatePassword, rC.lW),
			                u:Reg(submitData.updatePassword, rC.uW),
			                n:Reg(submitData.updatePassword, rC.nW),
			                s:Reg(submitData.updatePassword, rC.sW)
			            }; */
			            var tR = {
				                n:Reg(submitData.updatePassword, rC.nW)
				            };
			            if(tR.n){
			            	var upwd = Ext.getCmp('updatePassword1').getValue();
			            	var char1 = upwd.charAt(0);
			            	if(char1==upwd.charAt(1)&&char1==upwd.charAt(2)&&char1==upwd.charAt(3)&&char1==upwd.charAt(4)&&char1==upwd.charAt(5)){
			            		Ext.MessageBox.alert("提示信息","您输入的修改密码不能是6位重复数字,请重新输入!");
			            		return;
			            	}
			            	if(parseInt(char1)+1==parseInt(upwd.charAt(1))&&parseInt(char1)+2==parseInt(upwd.charAt(2))&&parseInt(char1)+3==parseInt(upwd.charAt(3))&&parseInt(char1)+4==parseInt(upwd.charAt(4))&&parseInt(char1)+5==parseInt(upwd.charAt(5))){
			            		Ext.MessageBox.alert("提示信息","您输入的修改密码不能是连续数字,请重新输入!");
			            		return;
			            	}
			            	if(Ext.getCmp('updatePassword1').getValue() != Ext.getCmp('reviewPassword1').getValue()){
								Ext.MessageBox.alert("提示信息","您输入的修改密码和确认密码不一致,请重新输入!");
								return;
							}else{
								requestAjax('<%=basePath%>/admin/systemUserPass_updatePass',submitData,function(sRet){
								if(sRet.res){
									Ext.MessageBox.alert("提示信息",sRet.message);
									
								}else{
									Ext.MessageBox.alert("提示信息","修改密码成功");
									Ext.getCmp('nowPassword1').reset();
									Ext.getCmp('updatePassword1').reset();
									Ext.getCmp('reviewPassword1').reset();
								}
								});
							}
			            }else{
							Ext.MessageBox.alert("提示信息","您的密码必须是6位数字,请重新输入!")
			            }
			            <%-- if((tR.l && tR.u && tR.n) || (tR.l && tR.u && tR.s) || (tR.s && tR.u && tR.n) || (tR.s && tR.l && tR.n)){
			            	if(Ext.getCmp('updatePassword1').getValue() != Ext.getCmp('reviewPassword1').getValue()){
								Ext.MessageBox.alert("提示信息","您输入的修改密码和确认密码不一致,请重新输入!");
								return;
							}else{
								requestAjax('<%=basePath%>/admin/systemUserPass_updatePass',submitData,function(sRet){
								if(sRet.res){
									Ext.MessageBox.alert("提示信息",sRet.message);
									
								}else{
									Ext.MessageBox.alert("提示信息","修改密码成功");
									Ext.getCmp('nowPassword1').reset();
									Ext.getCmp('updatePassword1').reset();
									Ext.getCmp('reviewPassword1').reset();
								}
								});
							}
			            }else{
							Ext.MessageBox.alert("提示信息","您的密码必须含有“小写字母”、“大写字母”、“数字”、“特殊符号”中的任意三种,请重新输入!")
			            } --%>
				}
				
			}
		function buildLayout(){
			var viewport = new Ext.Viewport({
				layout:'fit',
				items:[panel]
			});
		}
		buildLayout();
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="panel"></div>
  </body>
</html>
