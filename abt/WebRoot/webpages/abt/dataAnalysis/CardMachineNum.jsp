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
    
    <title>自助交易流水报表统计</title>
    
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
		
		var menuCodeSrore = new Ext.data.JsonStore({
			url    : '<%=basePath%>dataAnalysis/cardMachineNum_queryMenucode',
			autoLoad:true,
			root   : 'field1',
			fields:['dictvalue','dicttype']
		});
		
		
		//机具编号
		var devicenoStore = new Ext.data.SimpleStore({ 
			data:[
			      ['1','1号设备'],['2',"2号设备"],
			      ['3','3号设备'],['4',"4号设备"],
			      ['5','5号设备'],['6',"6号设备"],
			      ['7','7号设备'],['8',"8号设备"],
			      ['9','9号设备'],['10',"10号设备"]
			      ],
			fields : ['dictValue','dictValueDesc']
		});
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示排队号流水查询界面
 */	    var systemUnits=pagereturn.field1;
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
			var branch = <%="'"+user.getUnit().getUnitid()+"'"%>;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '排队号历史查询', 120, 1,
				[
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,fieldLabel:'机构号',passName:'branch',tree:treepanel_a, width:200,value:<%="'" + user.getUnit().getUnitname()+ "'"%>}} ,//部门
				{rowIndex:0, field:{xtype:'combo', 	    name:'deviceno', 	fieldLabel:'机具编号', editable:false, hiddenName:'deviceno', store:devicenoStore, 	displayField:'dictValueDesc', valueField:'dictValue'}},
				{rowIndex:0, field:{xtype:'combo', 	    name:'menu_code', 	fieldLabel:'业务名称', editable:false, hiddenName:'menu_code', store:menuCodeSrore, 	displayField:'dicttype', valueField:'dictvalue'}},
				{rowIndex:0, field:{xtype:'datefield',editable:false, 	name:'startDate', format:'Ymd',value:new Date(new Date().setDate(new Date().getDate() - 30)),allowBlank : false, fieldLabel:'开始时间'}},
				{rowIndex:0, field:{xtype:'datefield',editable:false, 	name:'endDate', format:'Ymd',value:new Date(),allowBlank : false,fieldLabel:'结束时间'}}
				
				],
				[
				{iconCls: "x-image-query",text:'<s:text name="common.button.query"/>'},//查询
				{iconCls: "x-image-reset",text:'<s:text name="common.button.reset"/>'},		//重置
				{iconCls: "x-image-application_form_add",id:'06',text:'导出Excel'} //导出excel
				],
				onButtonClicked
			);
			
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>dataAnalysis/cardMachineNum_queryCardMachineNum',
				[	
					'branch',//机构号
					'deviceno',//设备编号
					'serial_date',//日期
					'menu_code',//交易编号
					'menu_name',//交易名称
					'executeNum'//交易笔数
				],
				[
					{header:'全选'},
					{header:'复选框'},
					{header:'日期',dataIndex:'serial_date',width:200},
					{header:'机构号',dataIndex:'branch',width:200},
					{header:'机具编号',dataIndex:'deviceno',width:200},
					{header:'业务编号',dataIndex:'menu_code',width:200},
					{header:'业务名称',dataIndex:'menu_name',width:200},
					{header:'交易笔数',dataIndex:'executeNum',width:170}
					
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
					pagequeryObj.queryPage(query_obj);
					break;
					case 1:
						conditionPanel.reset();
						Ext.getCmp('unitname').setPassValue(<%="'"+user.getUnitid()+"'"%>);
						Ext.getCmp('unitname').setValue(<%="'" + user.getUnit().getUnitname() + "'"%>);
						break;
					case 2:
						var query_obj = conditionPanel.getFields();
						var branch=query_obj["branch"];
						var startDate = query_obj["startDate"];
						var endDate = query_obj["endDate"];
						if(null==branch||branch===undefined||branch.length==0){
							Ext.MessageBox.alert('信息提示','请选择想查询的机构!');
							return;
						}
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
						if(startDate>myDate){
							Ext.MessageBox.alert('信息提示','请选择正确开始日期');
							return;
						}
						if(endDate>myDate){
							Ext.MessageBox.alert('信息提示','请选择正确结束日期');
							return;
						}
						if(pagequeryObj.selfPagingTool.totalNum == -1){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.queryneeded"/>');
						}else if(pagequeryObj.selfPagingTool.totalNum == 0 ){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commoninfo"><s:param>'+'查询记录数为0，无数据需要下载！'+'</s:param></s:text>');
						}else if(pagequeryObj.selfPagingTool.totalNum > 100000){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.recordsoverlimit"/>');
						}else{
							var submitdata = pagequeryObj.getSelectedObjects(['branch','startDate','endDate','deviceno','menu_code']);
							requestAjax('<%=basePath%>common_exportXls',submitdata,function(sRet){
								if(sRet.res){
									Ext.MessageBox.alert("导出失败",sRet.message);
								}else{
									var query_obj = conditionPanel.getFields();
									var myForm = document.getElementById("formId");
									var myInputs = myForm.getElementsByTagName("input");
									myInputs[0].value = Ext.util.JSON.encode(query_obj);
									myForm.submit();
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
	
	<form id="formId" name="queueNum_exportExcel" action="/abt/dataAnalysis/cardMachineNum_exportExcel" target="REPORTRESULTFRAME" method="post">
		<input type="hidden" name="querycondition_str" value="" id="inputId"/>
	</form>

	<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
	
  </body>
</html>
