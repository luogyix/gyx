<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String brno = user.getUnitid();
%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>   
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>VIP客户信息配置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css"/>
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjs/ext-all.js"></script>
	<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript">
		//var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
/**
 * @Title: loadPage 
 * @Description: 显示节假日 维护界面
 */
		function loadPage(){
	 		var importWindow;
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', 'VIP客户信息配置', 120, 0,
				[],
				[
				{iconCls: "x-image-query", id:'01', text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-Excel", id:'02',	text:'导入数据'},
				{iconCls: "x-image-Excel", id:'03',	text:'导出生效数据'}//,
				//{iconCls: "x-image-application_form_delete",id:'04', text:'<s:text name="common.button.deleterecord"/>'}//删除
				],
				onButtonClicked
			);
			
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>confManager/vipCust_queryVipCustInfo',
				['custinfo_name','vip_card','custtype','custinfo_tel','start_date'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'VIP客户卡号',dataIndex:'vip_card',width:150},
				{header:'VIP客户名称',dataIndex:'custinfo_name',width:150},
				{header:'VIP客户类型',dataIndex:'custtype',width:100},
				{header:'VIP客户电话',dataIndex:'custinfo_tel',width:150},
				{header:'生效日期' ,dataIndex:'start_date',width:100}//,
				//{header:'失效日期' ,dataIndex:'end_date',width:100}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			 /**
			 * @Title:onButtonClicked
			 * @Description:触发"查询"，"重置"，"添加"，"修改"，"删除"的选择语句
			 */
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
						//query_obj['query_rules'] = '1';
						pagequeryObj.queryPage(query_obj);
						break;
					case 1:
						importWindow.open();
						break;
					case 2:
						if(pagequeryObj.selfPagingTool.totalNum == -1){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.queryneeded"/>');
						}else if(pagequeryObj.selfPagingTool.totalNum == 0 ){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commoninfo"><s:param>'+'查询记录数为0，无数据需要下载！'+'</s:param></s:text>');
						}else if(pagequeryObj.selfPagingTool.totalNum > 10000){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.recordsoverlimit"/>');
						}else{
							requestAjax('<%=basePath%>common_exportXls','',function(sRet){
								if(sRet.res){
									Ext.MessageBox.alert("导出失败",sRet.message);
								}else{
									var query_obj = conditionPanel.getFields();
									document.forms[2].querycondition_str.value = Ext.util.JSON.encode(query_obj);
									document.forms[2].submit();
								}
							});
						}
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['custinfo_name','vip_card','custtype']);
					    if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/vipCust_delVipCustInfo',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										//query_obj['query_rules'] = '1';
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					    break;
				}
			}
			//当选择"添加"时，弹出的窗口
			importWindow = new SelfFormWindow('importWindow', '导入客户名单', 300, 250, 210, 1,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'filename', allowBlank : false, fieldLabel:'文件名称', blankText : '请上传文件',maxLength : 200,maxLengthText : '长度不能大于200位',readOnly:true	 }},
				//{colIndex:0, field:{xtype : 'textfield',fieldLabel : '选择文件',name : 'upload',id : 'upload',inputType : 'file',anchor : '95%'}},
				{colIndex:0, field:{xtype:'datefield',id:'start_date',  name:'start_date', fieldLabel:'生效日期',format:'Y-m-d',allowBlank : false,blankText:'请选择开始时间',editable:false,minValue:new Date(new Date().setDate(new Date().getDate()+1))}}//,
				//{colIndex:0, field:{xtype:'datefield',id:'end_date',  name:'end_date',   fieldLabel:'失效日期',format:'Y-m-d',allowBlank : false,blankText:'请选择结束时间',editable:false}}//new Date(new Date().setDate(new Date().getDate()+1))
				],
				[
					{text:'上传文件', handler: function(){
						var form = new Ext.form.FormPanel({
							baseCls : 'x-plain',  
							//height:120,
							labelWidth : 70,  
							//labelHeight: 150,  
							fileUpload : true,  
							defaultType : 'textfield',  
							items : [{  
								xtype : 'textfield',  
						        fieldLabel : '选择文件',  
						        name : 'upload',  
						        id : 'upload',  
						        inputType : 'file',  
						        anchor : '95%' // anchor width by percentage  
							}]
						});
					    var window = new Ext.Window({
					        //layout : 'fit',//设置window里面的布局  
					    	width :450,  
							height : 100,  
					        //关闭时执行隐藏命令,如果是close就不能再show出来了  
					        closeAction:'close',
					        draggable : false, //不允许窗口拖放  
					        //maximizable : true,//最大化  
					        //minimizable : true,//最小话  
					        //constrain : true,//防止窗口超出浏览器  
					        //constrainHeader : true,//只保证窗口顶部不超出浏览器  
					        //resizble : true,//是否可以改变大小  
					        //resizHandles : true,//设置窗口拖放的方式  
					       	modal : true,//屏蔽其他组件,自动生成一个半透明的div  
					        //animateTarget : 'target',//弹出和缩回的效果  
					        //plain : true,//对窗口进行美化,可以看到整体的边框  
					        
					        buttonAlign : 'right',//按钮的对齐方式  
					        defaultButton : 0,//默认选择哪个按钮  
					        items:form,
					        buttons : [{  
					            text : '确定',  
					            handler : function() {  
							       if (form.form.isValid()) {  
							        if(Ext.getCmp('upload').getValue() == ''){  
							         Ext.Msg.alert('溫馨提示','请选择上传的文件..');  
							         return;  
							        }  
							        Ext.MessageBox.show({  
							           title : '请稍候....',  
							           msg : '文件正在上传中....',  
							           progressText : '',  
							           width : 300,  
							           progress : true,  
							           closable : false,  
							           animEl : 'loding'  
							        });  
							        form.getForm().submit({  
							            url : '<%=basePath%>/confManager/vipCust_upLoadFile',  
							            method : 'POST',  
							            success : function(form, action) {
							            	Ext.MessageBox.hide();//让进度条消失  
							            	//var response = action.response.responseText;
							            	//{"success":"false","result":"Excel文件格式不符,请确认后再次上传!"}
							            	//{"success":"false","result":"上传文件出错"}
							            	//{"success":"true","result":"文件上传成功"}
							            	var response = eval('(' + action.response.responseText + ')');
							            	if(response['success'] == 'true'){//成功
							            		var value = Ext.getCmp('upload').getValue();
									            var val = value.split("\\");
									           	var field = importWindow.getForm().findField('filename');
												field.setValue(val[val.length-1]);
												window.close();
							            	}
										    Ext.Msg.alert('提示',response['result']);  
										    //window.close();
							         	},  
								        failure : function(form, action) {  
								            Ext.Msg.alert('错误','文件上传失败，请重新操作！');  
								        }  
							        })  
							       }  
							     }  
					        },{  
					            text : '取消' , 
					            handler:function(){
					            	window.close();
					            }
					        }]  
					    });  
					    window.show();  
					}},
					{text:'导入',   formBind:true, handler :  function(){
						var submitData = importWindow.getFields();
						requestAjax('<%=basePath%>confManager/vipCust_importVipCustInfo', submitData,
						function(sRet){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','导入成功',function(id){
								var query_obj = conditionPanel.getFields();
								//query_obj['query_rules'] = '1';
								pagequeryObj.queryPage(query_obj);
							});
							importWindow.close();
						});
					}},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){importWindow.close();}}
				]
			);
			
			/**
			* @Title:buildLayout
			* @Description:创建布局函数
			*/
			function buildLayout(){
				var viewport = new Ext.Viewport({
				    layout : "border",
					items : [conditionPanel.toolbarwindow,pagequeryObj.pagingGrid]
				});
			}
      		buildLayout();
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="importWindow"></div>
	
	<s:form action="vipCust_exportVipCustInfo" namespace="confManager" target="REPORTRESULTFRAME">
		<s:hidden name="querycondition_str"></s:hidden>
	</s:form>
	<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
  </body>
</html>
