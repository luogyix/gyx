<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute("logonuser");
%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>    
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>吞卡明细交易流水</title>
    
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
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示预约信息查询界面
 */	    var systemUnits=pagereturn.field1;
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'isleaf',nodeLevel:'unitlevel',nodeType:'unitlevel'};
			var treeGenerator_a = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
			var tree_a = treeGenerator_a.generate(false,false,false,false);
			var treepanel_a = new Ext.tree.TreePanel({
				height:280,
				rootVisible : true, 
				root:tree_a,
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
			    }),{xtype:'button',text:''}]
			});
			var branch = <%="'"+user.getUnit().getUnitid()+"'"%>;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '吞卡交易明细查询', 120, 1,
				[
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,fieldLabel:'网点号',passName:'branch',tree:treepanel_a, width:200,value:<%="'" + user.getUnit().getUnitname().replace("珠海华润银行","").replace("股份有限公司","") + "'"%>}} ,//部门
				{rowIndex:0, field:{xtype:'datefield',editable:false, 	name:'startdate',   format:'Y-m-d',value:new Date(),allowBlank : false, fieldLabel:'起始日期'}},
				{rowIndex:0, field:{xtype:'datefield',editable:false, 	name:'enddate',   format:'Y-m-d',value:new Date(),allowBlank : false, fieldLabel:'终止日期'}}
				],
				[
				{iconCls: "x-image-query", text:'<s:text name="common.button.query"/>'},//查询
				{iconCls: "x-image-reset", text:'<s:text name="common.button.reset"/>'},//重置
				{iconCls: "x-image-application_form_add",id:'06',text:'导出Excel'} //导出excel
				],
				onButtonClicked
			);
			
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/dataAnalysis/eatCardTrade_queryEatCardTrade',
				[
					//'serial_number',
					'branch',
					'branch_name',
					'cust_name',
					'teller_name',
					'date',
					'time',
					'cardnumber',
					'reason',
					'return_code',
					'address'
				],[
					{header:'全选'},
					{header:'复选框'},
					//{header:'序号', dataIndex:'serial_number',width:50},
					{header:'机构号',dataIndex:'branch',width:100},
					{header:'机构名称',dataIndex:'branch_name',width:100},
					{header:'客户姓名',dataIndex:'cust_name',width:100},
					{header:'虚拟柜员号',dataIndex:'teller_name',width:100},
					{header:'日期', dataIndex:'date',width:100},
					{header:'时间', dataIndex:'time',width:100},
					{header:'卡号',dataIndex:'cardnumber',width:150},
					{header:'吞卡原因',dataIndex:'reason',width:150},
					{header:'返回码',dataIndex:'return_code',width:100},
					{header:'安装地址',dataIndex:'address',width:100}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			 /**
			 * @Title:onButtonClicked
			 * @Description:触发"查询"
			 */
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
						var branch = query_obj["branch"];
						var startdate=query_obj["startdate"];
						var endate=query_obj["enddate"];
						
						if(null==branch||branch===undefined||branch.length==0)
						{
							Ext.MessageBox.alert('信息提示','请选择想查询的机构!');
							return;
						}
						if(startdate=="undefined"||startdate.indexOf("-")==-1||startdate.length==0||startdate.length!=10)
							{
								Ext.MessageBox.alert('信息提示','请输入正确的起始日期!');
								return;
							}
						if(endate=="undefined"||endate.indexOf("-")==-1||endate.length==0||endate.length!=10)
							{
								Ext.MessageBox.alert('信息提示','请输入正确的结束日期!');
								return;	
							}
						if(startdate>endate)
							{
								Ext.MessageBox.alert('信息提示','起始日期不能晚于结束日期!');
								return;
							}else{
 							pagequeryObj.queryPage(query_obj);
						}
					break;
					case 1:
						conditionPanel.reset();
						Ext.getCmp('unitname').setPassValue(<%="'"+user.getUnitid()+"'"%>);
						Ext.getCmp('unitname').setValue(<%="'" + user.getUnit().getUnitname() + "'"%>);					break;
					case 2:
						var query_obj = conditionPanel.getFields();
						var branch = query_obj["branch"];
						
						if(null==branch||branch===undefined||branch.length==0){
							Ext.MessageBox.alert('信息提示','请选择想查询的机构!');
							return;
						}
						if(pagequeryObj.selfPagingTool.totalNum == -1){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.queryneeded"/>');
						}else if(pagequeryObj.selfPagingTool.totalNum == 0 ){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commoninfo"><s:param>'+'查询记录数为0，无数据需要下载！'+'</s:param></s:text>');
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
      		Ext.getCmp('unitname').setPassValue(branch);
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	
	<s:form action="/dataAnalysis/eatCardTrade_exportEatCardTrade" target="REPORTRESULTFRAME">
		<s:hidden name="querycondition_str"></s:hidden>
	</s:form>
	<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
  </body>
</html>
