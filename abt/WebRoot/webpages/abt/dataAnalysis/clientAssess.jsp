<%@page import="com.agree.framework.web.form.administration.User" isELIgnored="false"%>
<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@ page language="java"  pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>   
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>客户满意度统计</title>
    
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
		var ReportTypeStore= new Ext.data.SimpleStore({
			fields : ['dataValue','dataName'],
			data:[["3","日报表"],["2","月报表"],["4","季报表"],["1","年报表"]]
		});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.chart.Chart.CHART_URL = '<%=basePath%>extjs/resources/charts.swf';
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
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '客户评价统计', 120, 2,
				[
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',	 allowUnLeafClick:true,	fieldLabel:'机构号',		passName: 'branch', tree:treepanel_a, width:250,value:<%="'" + user.getUnit().getUnitname() + "'"%>}} ,//部门
				{rowIndex:0, field:{xtype:'textfield', 	name:'teller',	  fieldLabel:'柜员号', value:'',width:100}},
				//{rowIndex:0, field:{xtype:'combo', 	    name:'queueType', 	fieldLabel:'业务类型',value:'', editable:false, hiddenName:'queueType',blankText : '', store:queueBusiness, 	displayField:'dataName', valueField:'dataValue'}},				
				{rowIndex:1, field:{xtype:'combo', name:'reportType',id:'reportTypeCom',fieldLabel:'报表类型',editable:false,hiddenName:'reportType',store:ReportTypeStore,displayField:'dataName',valueField:'dataValue',value:3}},
				{rowIndex:1, field:{xtype:'datefield', 	name:'startDate', fieldLabel:'开始时间',format:'Y-m-d',value:new Date(new Date().setDate(new Date().getDate() - 30)),  allowBlank : false,editable:false}},
				{rowIndex:1, field:{xtype:'datefield', 	name:'endDate',   fieldLabel:'结束时间',format:'Y-m-d',value:new Date(),  allowBlank : false,editable:false}}
				],
				[
				{iconCls: "x-image-query", 			id:'01',text:'<s:text name="common.button.query"/>'},		//查询
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
				'<%=basePath%>dataAnalysis/clientAssess_queryAssess',
				['branch','branch_name','date','teller','teller_name','total_call_num','total_service_num','total_assess_num',
				'very_satisfy','satisfy','genl_satisfy','not_satisfy','not_evaluate','not_sed_evaluate','assess_rate','very_satisfy_rate','satisfy_rate',
				'genl_satisfy_rate','not_satisfy_rate','not_evaluate_rate','not_sed_evaluate_rate'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',  dataIndex:'branch',width:70},
				{header:'机构名',  dataIndex:'branch_name',width:100},
				{header:'日期',   dataIndex:'date',width:100,sortable:true},
				{header:'柜员号',dataIndex:'teller',width:100,sortable:true},
				{header:'柜员名',dataIndex:'teller_name',width:100,sortable:true},
				{header:'总叫号人数',dataIndex:'total_call_num',width:100,sortable:true},
				{header:'总服务人数',dataIndex:'total_service_num',width:100,sortable:true},
				{header:'总评价人数',dataIndex:'total_assess_num',width:100,sortable:true},
				{header:'非常满意', dataIndex:'very_satisfy',width:100,sortable:true},
				{header:'满意', dataIndex:'satisfy',width:100,sortable:true},
				{header:'一般', dataIndex:'genl_satisfy',width:100,sortable:true},
				{header:'不满意',dataIndex:'not_satisfy',width:100,sortable:true},
				{header:'客户未评价',dataIndex:'not_evaluate',width:100,sortable:true},
				{header:'柜员未发起评价',dataIndex:'not_sed_evaluate',width:120,sortable:true},
				
				{header:'评价率',dataIndex:'assess_rate',width:100,sortable:true},
				{header:'非常满意率',dataIndex:'very_satisfy_rate',width:100,sortable:true},
				{header:'满意率',dataIndex:'satisfy_rate',width:100,sortable:true},
				{header:'一般率',dataIndex:'genl_satisfy_rate',width:100,sortable:true},
				{header:'不满意率',dataIndex:'not_satisfy_rate',width:100,sortable:true},
				{header:'客户未评价率',dataIndex:'not_evaluate_rate',width:100,sortable:true},
				{header:'柜员未发起评价率',dataIndex:'not_sed_evaluate_rate',width:180,sortable:true}
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
						//if(startdate>=new Date().format('Y-m-d')){
						//	Ext.MessageBox.alert('信息提示','不可选择本日与之后的日期!');
						//	return;
						//}
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
						Ext.getCmp('unitname').setValue(<%="'" + user.getUnit().getUnitname() + "'"%>);
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
						
						requestAjax('<%=basePath%>waitingTime_resetAnalysis',query_obj,function(sRet){
							Ext.MessageBox.alert('信息提示','重新生成成功!');
						});
						break;
					case 3:
						var query_obj = conditionPanel.getFields();
						var value=query_obj["reportType"];
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
	
	<s:form action="/dataAnalysis/clientAssess_exportExcel" target="REPORTRESULTFRAME">
		<s:hidden name="querycondition_str"></s:hidden>
	</s:form>
	<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
	
  </body>
</html>
