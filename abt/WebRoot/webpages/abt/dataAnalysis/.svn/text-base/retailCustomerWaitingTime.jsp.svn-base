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
    
    <title>零售客户等候时间统计</title>
    
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
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		var businessStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>/confManager/business_queryBusinessSmall?query_rules=4',
			autoLoad:true,
			root   : 'field1',
			fields:['bs_id','bs_name_ch']
		});
		
		var custTypeStore=new Ext.data.SimpleStore({ 
			data:[["PT","普通客户"],["HJ","黄金客户"],["BJ","白金客户"],["ZS","钻石客户"],["HK","黑卡客户"]],
			fields : ['custtype_i','custtype_name']
		});
		
		/**
		 * @Title: loadPage 
		 * @Description: 显示数据字典维护界面
		 */		
 		var systemUnits=pagereturn.field1;
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
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '零售客户等候时间', 120, 2,
				[
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,		fieldLabel:'机构号',		passName: 'branch', tree:treepanel_a, width:250,value:<%="'" + user.getUnit().getUnitname().replace("平安银行","").replace("股份有限公司","") + "'"%>}} ,//发给希望后告诉他修改成机构树
				//{rowIndex:0, field:{xtype:'combo', 	    name:'queueType', 	fieldLabel:'业务类型', editable:false, hiddenName:'queueType', store: queueBusiness, 	displayField:'dataName', valueField:'dataValue',value:'Z1-对公业务'}},
				{rowIndex:0, field:{xtype:'datefield',editable:false, 	name:'startDate',   format:'Y-m-d',value:new Date(new Date().setDate(new Date().getDate() - 30)),allowBlank : false, fieldLabel:'开始时间'}},
				{rowIndex:0, field:{xtype:'datefield',editable:false, 	name:'endDate',     format:'Y-m-d',value:new Date(new Date().setDate(new Date().getDate() - 1)),allowBlank : false,fieldLabel:'结束时间'}},
				{rowIndex:1, field:{xtype:'combo', 	    name:'business', 	fieldLabel:'业务名称', editable:false, hiddenName:'business', store:businessStore, 	displayField:'bs_name_ch', valueField:'bs_id'}},
				{rowIndex:1, field:{xtype:'combo', name:'custtype',fieldLabel:'客户类型',editable:false,hiddenName:'custtype',displayField:'custtype_name',valueField:'custtype_i',store: custTypeStore}}
				],
				[
				{iconCls: "x-image-query", 			    id:'01', text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-reset", 			text:'<s:text name="common.button.reset"/>',id:'08'},		//重置
				{iconCls: "x-image-application_form_add",id:'06',text:'导出Excel'} //导出excel
				],
				onButtonClicked
			);
			conditionPanel.open();

			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>dataAnalysis/retailcustomerwaittime_queryCustomerWaitingTime',
				['BRANCH','WORK_DATE','BRANCH_NAME','TICKET_NUM','VALID_TICKET_NUM','DISCARDED_TICKET_NUM',
					'PLATINUM_TICKET_NUM','PLATINUM_VALID_TICKET_NUM','PLATINUM_DISCARDED_TICKET_NUM',
					'PLATINUM_DISCARDED_TICKET_RATE','PLATINUM_STANDARD_TICKET_NUM','PLATINUM_STANDARD_TICKET_RATE',
					'GOLD_TICKET_NUM','GOLD_VALID_TICKET_NUM','GOLD_DISCARDED_TICKET_NUM','GOLD_DISCARDED_TICKET_RATE',
					'GOLD_STANDARD_TICKET_NUM','GOLD_STANDARD_TICKET_RATE',
					'COMMON_TICKET_NUM','COMMON_VALID_TICKET_NUM','COMMON_DISCARDED_TICKET_NUM',
					'COMMON_DISCARDED_TICKET_RATE','COMMON_STANDARD_TICKET_NUM','COMMON_STANDARD_TICKET_RATE'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'BRANCH',width:80,sortable:true},
				{header:'机构名称',dataIndex:'BRANCH_NAME',width:120,sortable:true},
				{header:'日期',dataIndex:'WORK_DATE',width:80,sortable:true},
				//网点所有客户汇总
				{header:'所有客户取票数',dataIndex:'TICKET_NUM',width:110,sortable:true},
				{header:'所有客户有效数',dataIndex:'VALID_TICKET_NUM',width:110,sortable:true},
				{header:'所有客户弃票数',dataIndex:'DISCARDED_TICKET_NUM',width:110,sortable:true},
				//白金卡客户（目标值〉=90%）
				{header:'白金以上客户取票数',dataIndex:'PLATINUM_TICKET_NUM',width:120,sortable:true},
				{header:'白金以上客户有效票',dataIndex:'PLATINUM_VALID_TICKET_NUM',width:120,sortable:true},
				{header:'白金以上客户弃票数',dataIndex:'PLATINUM_DISCARDED_TICKET_NUM',width:120,sortable:true},
				{header:'白金以上客户弃票率',dataIndex:'PLATINUM_DISCARDED_TICKET_RATE',width:120,sortable:true},
				{header:'白金以上客户达标数',dataIndex:'PLATINUM_STANDARD_TICKET_NUM',width:120,sortable:true},
				{header:'白金以上客户达标率',dataIndex:'PLATINUM_STANDARD_TICKET_RATE',width:120,sortable:true},
				//金卡客户（目标值〉=90%）
				{header:'黄金客户取票数',dataIndex:'GOLD_TICKET_NUM',width:110,sortable:true},
				{header:'黄金客户有效票',dataIndex:'GOLD_VALID_TICKET_NUM',width:110,sortable:true},
				{header:'黄金客户弃票数',dataIndex:'GOLD_DISCARDED_TICKET_NUM',width:110,sortable:true},
				{header:'黄金客户弃票率',dataIndex:'GOLD_DISCARDED_TICKET_RATE',width:110},
				{header:'黄金客户达标数',dataIndex:'GOLD_STANDARD_TICKET_NUM',width:110,sortable:true},
				{header:'黄金客户达标率',dataIndex:'GOLD_STANDARD_TICKET_RATE',width:110,sortable:true},
				//普通客户（目标值〉=80%）
				{header:'普通客户取票数',dataIndex:'COMMON_TICKET_NUM',width:110,sortable:true},
				{header:'普通客户有效票',dataIndex:'COMMON_VALID_TICKET_NUM',width:110,sortable:true},
				{header:'普通客户弃票数',dataIndex:'COMMON_DISCARDED_TICKET_NUM',width:110,sortable:true},
				{header:'普通客户弃票率',dataIndex:'COMMON_DISCARDED_TICKET_RATE',width:110,sortable:true},
				{header:'普通客户达标数',dataIndex:'COMMON_STANDARD_TICKET_NUM',width:110,sortable:true},
				{header:'普通客户达标率',dataIndex:'COMMON_STANDARD_TICKET_RATE',width:150,sortable:true}
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
						var startdate=query_obj["startDate"];
						var endate=query_obj["endDate"] ;
						var branch=query_obj["branch"];

						if(null==branch||branch===undefined||branch.length==0){
							Ext.MessageBox.alert('信息提示','请选择想查询的机构!');
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
						Ext.getCmp('unitname').setValue(<%="'" + user.getUnit().getUnitname().replace("平安银行","").replace("股份有限公司","") + "'"%>);
						Ext.getCmp('unitname').setPassValue(<%="'"+user.getUnitid()+"'"%>);
						break;
					case 2:
						var query_obj = conditionPanel.getFields();
						var startdate=query_obj["startDate"];
						var endate=query_obj["endDate"] ;
						var branch=query_obj["branch"];

						if(null==branch||branch===undefined||branch.length==0){
							Ext.MessageBox.alert('信息提示','请选择想查询的机构!');
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
						}/*else if(pagequeryObj.selfPagingTool.totalNum > 100000){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.recordsoverlimit"/>');
						}*/else{
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
  
  <body>
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	
	<s:form action="/dataAnalysis/retailcustomerwaittime_exportExcel" target="REPORTRESULTFRAME">
		<s:hidden name="querycondition_str"></s:hidden>
	</s:form>
	<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
	
  </body>
</html>
