<%@ page language="java" import="java.util.*,com.agree.framework.web.form.administration.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)(request.getSession().getAttribute("logonuser"));
String usertype=user.getUsertype();
String username=user.getUsername();
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'loginfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css"/>
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjs/ext-all.gzjs"></script>
	<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript">
		//var pagereturn=${actionresult};
		//var operator = new Ext.data.JsonStore({
			//fields : ['userid','username'],
			//data   : pagereturn.field1
		//});
		// var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		//装载操作记录查询页面
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '系统操作日志', 120, 1,
				[
				{rowIndex:0, field:{xtype:'datefield', id:'startDate',name:'startDate', fieldLabel:'操作日期(从)',format:'Ymd',value:new Date(new Date().setDate(new Date().getDate() - 30))}},
				{rowIndex:0, field:{xtype:'datefield',name:'endDate', fieldLabel:'操作日期(到)',format:'Ymd',value:new Date()}},
				{rowIndex:0, field:{xtype : 'textfield',name:'username',fieldLabel: '用户名称'}},
				{rowIndex:0, field:{xtype:'textfield', 	name:'operation',fieldLabel:'操作内容'}}
				],
				[
				{iconCls: "x-image-query", text:'<s:text name="common.button.query"/>'},
				{iconCls: "x-image-reset", text:'<s:text name="common.button.reset"/>'},
				{iconCls: "x-image-Excel", text:'<s:text name="common.button.exportexcel"/>'}
				
				],
				onButtonClicked
			);
			conditionPanel.open();
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/admin/systemlog_querylog',
				['unitid','unitname','username','logdate','logtime','hostip','operation','result'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'操作日期',dataIndex:'logdate',width:80, renderType:PageQuery.TYPE.DATE},
				{header:'操作时间',dataIndex:'logtime',width:80, renderType:PageQuery.TYPE.TIME},
				{header:'用户名称',dataIndex:'username',width:150},
				{header:'部门名称',dataIndex:'unitname',width:150},
				{header:'登陆Ip',dataIndex:'hostip',width:150},
				{header:'操作内容',dataIndex:'operation',width:150},
				{header:'操作结果',dataIndex:'result',width:80}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
           //查询结果视图
			/**var viewwindow = new SelfFormWindow('viewWindow', ' 查看系统操作日志', 300, 400, 200, 1,
					[
					{colIndex:0, field:{xtype : 'textfield', name:'unitname',  fieldLabel:'所属单位',readOnly:true}},
					{colIndex:0, field:{xtype : 'textfield', name:'username',  fieldLabel:'<s:text name="admin.user.username"/>',readOnly:true}},
					{colIndex:0, field:{xtype : 'textfield', name:'logdate',  fieldLabel:'记录日期',readOnly:true}},
					{colIndex:0, field:{xtype : 'textfield', name:'logtime',  fieldLabel:'记录时间',readOnly:true}},
					{colIndex:0, field:{xtype : 'textfield', name:'hostip',  fieldLabel:'客户端IP',readOnly:true}},
					{colIndex:0, field:{xtype : 'textfield', name:'operation',  fieldLabel:'操作内容',readOnly:true}}
					],
					[
						
						{text:'<s:text name="common.button.close"/>', handler: function(){
								viewwindow.close();
							}}
					]
				);*/
				//从数据库中获取查询结果
			/**pagequeryObj.pagingGrid.on('rowdblclick',function(){
    			var records = pagequeryObj.getSelectedRecords();
    			viewwindow.open();
    			viewwindow.updateFields(records[0]);
    		});*/
           //触发"查询"、"重置"按钮
			function onButtonClicked(btn_index){
				switch(btn_index){
				case 0:
					//获得页面表格中所有的值
					var query_obj = conditionPanel.getFields();
					//用户选择的开始时间条件
					var workdate_start = query_obj["startDate"];
					//用户选择的结束时间条件
					var workdate_end = query_obj["endDate"];
					//获取当前日期,格式为yyyy-MM-dd
					var newDate = new Date();
					var y = newDate.getFullYear();
					var m = newDate.getMonth() + 1;
					m = m < 10 ? '0' + m : m;
					var d = newDate.getDate();
					d = d < 10 ? '0' + d : d;
					var myDate = y+""+ m+"" +""+d;
					/**
					*验证用户选择的日期是否正确
					*/
					if(workdate_start>myDate){
						Ext.MessageBox.alert('信息提示','请选择正确开始日期');
						return;
					}
					if(workdate_end>myDate){
						Ext.MessageBox.alert('信息提示','请选择正确结束日期');
						return;
					}
					if(workdate_end<workdate_start){
						Ext.MessageBox.alert('信息提示','请选择正确日期');
						return;
					}
					if(Ext.getCmp('startDate').getValue()==""){
   						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustwrite"><s:param>起止日期</s:param></s:text >');
   					}else{
   						pagequeryObj.queryPage(query_obj);
   					}
					
					break;
				case 1:
					conditionPanel.reset();
					break;
				case 2:
					if(pagequeryObj.selfPagingTool.totalNum == -1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.queryneeded"/>');
					}else if(pagequeryObj.selfPagingTool.totalNum == 0 ){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commoninfo"><s:param>'+'查询记录数为0，无数据需要下载！'+'</s:param></s:text>');
					}/*else if(pagequeryObj.selfPagingTool.totalNum > 100000){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.recordsoverlimit"/>');
					}*/else{
						requestAjax('<%=basePath%>common_exportXls','',function(sRet){
							if(sRet.res){
								Ext.MessageBox.alert("导出失败",sRet.message);
							}else{
								Ext.MessageBox.alert("温馨提示","导出成功");
								var query_obj = conditionPanel.getFields();
								document.forms[1].querycondition_str.value = Ext.util.JSON.encode(query_obj);
								document.forms[1].submit();
							}
						});
					}
					break;
				}
			}
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
	<s:form action="admin/systemlog_excelLogInfo" target="REPORTRESULTFRAME" namespace="./">
		<s:hidden name="querycondition_str"></s:hidden>
	</s:form>
	<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
  </body>
</html>
