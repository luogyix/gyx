<%@page import="com.agree.framework.web.form.administration.User" isELIgnored="false"%>
<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@ page language="java"  pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User) request.getSession().getAttribute(
		ApplicationConstants.LOGONUSER);
%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>   
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>柜员办理效率分析(增加业务类别统计)</title>
    
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
	<script type="text/javascript" src="selfjs/common/TreeFilter.js"></script>
	<script type="text/javascript" src="selfjs/common/PinyinFilter.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagetoafaquery.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		var businessStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>/confManager/business_queryBusinessSmall?query_rules=4',
			autoLoad:true,
			root   : 'field1',
			fields:['bs_id','bs_name_ch']
		});
		var ReportTypeStore= new Ext.data.SimpleStore({
			fields : ['dataValue','dataName'],
			data:[["3","日报表"],["2","月报表"],["4","季报表"],["1","年报表"]]
		});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
/**
 * @Title: loadPage 
 * @Description: 显示数据字典维护界面
 */
 		var	systemUnits=pagereturn.field1;
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'isleaf',nodeLevel:'unitlevel',nodeType:'unitlevel'};

			var treeGenerator_a = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
			var tree_a = treeGenerator_a.generate(false,false,false,false);
			var treepanel_a = new Ext.tree.TreePanel({
				height:280,
				bbar:[new Ext.form.TextField({
			        width:180,
			        emptyText:'快速检索',
			        enableKeyEvents: true,
				    listeners:{
						render: function(f){
			                this.filter = new QM.ux.TreeFilter(treepanel_a,{
								clearAction : 'expand'
							});//初始化TreeFilter 
						},
			            keyup: {//添加键盘点击监听
			                fn:function(t,e){
			                  t.filter.filter(t.getValue());
			                },
			                buffer: 350
			            }
					}
			    }),{xtype:'button',text:''}],
				rootVisible : true, 
				root:tree_a
			});
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '柜员办理效率', 120, 2,
				[
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname', allowUnLeafClick:true,		fieldLabel:'机构号',		passName: 'branch', tree:treepanel_a, width:250,value:<%="'" + user.getUnit().getUnitname().replace("珠海华润银行","").replace("股份有限公司","") + "'"%>}} ,//部门
				{rowIndex:0, field:{xtype:'textfield', 	name:'teller',	    fieldLabel:'柜员号',value:'', width:100}},
				//{rowIndex:0, field:{xtype:'textfield', 	name:'queue_bus_type',	    fieldLabel:'业务编号',value:'', width:100}},
				{rowIndex:0, field:{xtype:'combo', name:'queue_bus_type',	  fieldLabel:'业务名称',value:'',editable:false,hiddenName:'queue_bus_type',store:businessStore,displayField:'bs_name_ch',valueField:'bs_id'}},
				{rowIndex:1, field:{xtype:'combo', name:'reportType',id:'reportTypeCom',fieldLabel:'报表类型',editable:false,hiddenName:'reportType',store:ReportTypeStore,displayField:'dataName',valueField:'dataValue',value:3}},
				{rowIndex:1, field:{xtype:'datefield',editable:false, 	name:'startDate',   format:'Y-m-d',value:new Date(new Date().setDate(new Date().getDate() - 30)),allowBlank : false,	fieldLabel:'开始时间'}},
				{rowIndex:1, field:{xtype:'datefield',editable:false, 	name:'endDate',     format:'Y-m-d',value:new Date(),allowBlank : false,	fieldLabel:'结束时间'}}
				],
				[
				{iconCls: "x-image-query", 			     id:'01',text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-reset", 			text:'<s:text name="common.button.reset"/>',id:'08'},		//重置
				{iconCls: "x-image-reset",id:'07',text:'重新生成统计分析'},
				{iconCls: "x-image-application_form_add",id:'06',text:'导出Excel'}//导出excel
				],
				onButtonClicked
			);
			conditionPanel.open();
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>dataAnalysis/bTellerEfficiency_queryeEfficiency',
				['branch','branch_name','date','teller','teller_name','queue_bus_type','bus_type_name','people_count',
				 'average_waiting_time','average_service_time','average_use_time','time_efficiency','total_waiting_time','total_service_time','total_use_time'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:70},
				{header:'机构名',dataIndex:'branch_name',width:160},
				{header:'日期',dataIndex:'date',width:100,sortable:true},
				{header:'柜员号',dataIndex:'teller',width:100,sortable:true},
				{header:'柜员名',dataIndex:'teller_name',width:100,sortable:true},
				{header:'业务编号',dataIndex:'queue_bus_type',width:160},
				{header:'业务名称',dataIndex:'bus_type_name',width:160},
				{header:'有效服务人数',dataIndex:'people_count',width:100,sortable:true},
				{header:'平均等待时间(分钟)',dataIndex:'average_waiting_time',width:130,sortable:true},
				{header:'平均服务时间(分钟)',dataIndex:'average_service_time',width:130,sortable:true},
				{header:'平均客户用时(分钟)',dataIndex:'average_use_time',width:130,sortable:true},
				{header:'有效时间率',dataIndex:'time_efficiency',width:100,sortable:true},
				{header:'累积等待时间(分钟)',dataIndex:'total_waiting_time',width:130,sortable:true},
				{header:'累积服务时间(分钟)',dataIndex:'total_service_time',width:130,sortable:true},
				{header:'累积客户用时(分钟)',dataIndex:'total_use_time',width:180,sortable:true}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			String.prototype.replaceAll = function(AFindText,ARepText){
				raRegExp = new RegExp(AFindText,"g");
				return this.replace(raRegExp,ARepText);
			}
			
			 /**
			 * @Title:onButtonClicked
			 * @Description:触发"查询"，"重置"，"添加"，"修改"，"删除"的选择语句
			 */
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
						var value=query_obj["reportType"];
						var queue_bus_type=query_obj["queue_bus_type"];
						var startdate=query_obj["startDate"];
						var endate=query_obj["endDate"] ;
						var branch=query_obj["branch"];
						var teller=query_obj["teller"];

						if((null==branch||branch===undefined||branch.length==0)&&(null==teller||teller===undefined||teller.length==0)){
							Ext.MessageBox.alert('信息提示','请选择想查询的机构或输入柜员号!');
							return;
						}
						if(null==value||value===undefined||value.length==0){
							Ext.MessageBox.alert('信息提示','请选择报表类型!');
							return;
						}
						if(startdate=="undefined"||startdate.indexOf("-")==-1||startdate.length==0||startdate.length!=10){
							Ext.MessageBox.alert('信息提示','请输入正确的起始日期!');
									return;
						}
						if(endate=="undefined"||endate.indexOf("-")==-1||endate.length==0||endate.length!=10){
							Ext.MessageBox.alert('信息提示','请输入正确的结束日期!');
									return;
						}
						if(startdate>endate){
							Ext.MessageBox.alert('信息提示','起始日期不能晚于结束日期!');
									return;
						}
						pagequeryObj.queryPage(query_obj);
						break;
					case 1:
						conditionPanel.reset();
						Ext.getCmp('reportTypeCom').setValue('3');
						Ext.getCmp('unitname').setPassValue(<%="'"+user.getUnitid()+"'"%>);
						Ext.getCmp('unitname').setValue(<%="'" + user.getUnit().getUnitname().replace("珠海华润银行","").replace("股份有限公司","") + "'"%>);
						break;
					case 2:
						var query_obj = conditionPanel.getFields();
						var startdate=query_obj["startDate"];
						var endate=query_obj["endDate"] ;
						if(startdate=="undefined"||startdate.indexOf("-")==-1||startdate.length==0||startdate.length!=10){
							Ext.MessageBox.alert('信息提示','请输入正确的起始日期!');
							return;
						}
						if(endate=="undefined"||endate.indexOf("-")==-1||endate.length==0||endate.length!=10){
							Ext.MessageBox.alert('信息提示','请输入正确的结束日期!');
							return;
						}
						if(startdate>endate){
							Ext.MessageBox.alert('信息提示','起始日期不能晚于结束日期!');
							return;
						}
						
						requestAjax('<%=basePath%>dataAnalysis/waitingTime_resetAnalysis',query_obj,function(sRet){
							Ext.MessageBox.alert('信息提示','重新生成成功!');
						});
						break;
					case 3:
						var query_obj = conditionPanel.getFields();
						var value=query_obj["reportType"];
						var queue_bus_type=query_obj["queue_bus_type"];
						var startdate=query_obj["startDate"];
						var endate=query_obj["endDate"] ;
						var branch=query_obj["branch"];
						var teller=query_obj["teller"];
						if((null==branch||branch===undefined||branch.length==0)&&(null==teller||teller===undefined||teller.length==0)){
							Ext.MessageBox.alert('信息提示','请选择想查询的机构或输入柜员号!');
							return;
						}
						if(null==value||value===undefined||value.length==0){
							Ext.MessageBox.alert('信息提示','请选择报表类型!');
							return;
						}
						if(startdate=="undefined"||startdate.indexOf("-")==-1||startdate.length==0||startdate.length!=10){
							Ext.MessageBox.alert('信息提示','请输入正确的起始日期!');
									return;
						}
						if(endate=="undefined"||endate.indexOf("-")==-1||endate.length==0||endate.length!=10){
							Ext.MessageBox.alert('信息提示','请输入正确的结束日期!');
									return;
						}
						if(startdate>endate){
							Ext.MessageBox.alert('信息提示','起始日期不能晚于结束日期!');
									return;
						}
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
									document.forms[1].querycondition_str.value = Ext.util.JSON.encode(query_obj);
									document.forms[1].submit();
								}
							});
						}
						break;
				}
			}
          
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
      		Ext.getCmp('unitname').setPassValue(<%="'"+user.getUnitid()+"'"%>);
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	
	<s:form action="/dataAnalysis/bTellerEfficiency_exportExcel" target="REPORTRESULTFRAME">
		<s:hidden name="querycondition_str"></s:hidden>
	</s:form>
	<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
	
  </body>
</html>
