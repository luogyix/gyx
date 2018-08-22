<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute("logonuser");
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>自助交易流水查询</title>
    
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
		 		var systemUnits=pagereturn.field2;
 		var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'isleaf',nodeLevel:'unitlevel',nodeType:'unitlevel'};
 		//构建机构树a
		var treeGenerator_a = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var tree_a = treeGenerator_a.generate(false,false,false,false);
		var treepanel_a = new Ext.tree.TreePanel({
			height:220,
			width:200,
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
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		var machineTypeStore=new Ext.data.SimpleStore({ 
			data:[["1","1-排队机"],["10","10-填单机"],["11","11-自助机"]],
			fields : ['key','value']
		});
		
		//设备类型
		var deviceTypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=devicetype',
			root   : 'field1',
			fields : ['dictValue','dictValueDesc'],
			autoLoad:true
		});
		
		
/**
 * @Title: loadPage 
 * @Description: 显示排队机信息界面
 */	
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var branch = <%="'"+user.getUnit().getUnitid()+"'"%>;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '排队机信息配置', 120, 1,
				[
					{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,		fieldLabel:'机构号',passName: 'branch', tree:treepanel_a, width:202, value:<%="'" + user.getUnit().getUnitname() + "'"%>}}, 
					//{rowIndex:0, field:{xtype:'combo', 	name:'tellerno',id:'tellerno',  editable:false,	hiddenName:'tellerno',fieldLabel:'发卡机柜员号',store:deviceTypeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
					{rowIndex:0, field:{xtype :'textfield', name:'cardmachine_tellerno', id:'cardmachine_tellerno',	fieldLabel:'发卡机柜员号',hiddenName:'cardmachine_tellerno',maxLength :20,	maxLengthText : '长度不能大于10位'}},
					{rowIndex:0, field:{xtype:'datefield',editable:false, 	name:'workdate_start', format:'Ymd',value:new Date(new Date().setDate(new Date().getDate() - 30)),allowBlank : false,fieldLabel:'开始时间'}},
					{rowIndex:0, field:{xtype:'datefield',editable:false, 	name:'workdate_end', format:'Ymd',value:new Date(),allowBlank : false,fieldLabel:'结束时间'}}
				],
				[
				{iconCls: "x-image-query", id:'01', text:'<s:text name="common.button.query"/>'},//查询
				{iconCls: "x-image-reset", 			text:'<s:text name="common.button.reset"/>'}//重置
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/devmanager/machineNotes_queryMachineNotes',
				[
				 	'branch',//机构号
				 	'serial_date',//交易时间
				 	'cardmachine_tellerno',//发卡机柜员号
				 	'menu_name',//交易名称
				 	'account_num',//卡号
				 	'custinfo_name',//户名
				 	'amount',//金额
				 	'trade_status',//交易状态
				 	'esb_channelserno'//核心流水号
				],
				[
					{header:'全选'},
					{header:'复选框'},
					{header:'机构号',dataIndex:'branch',width:150},
					{header:'交易时间',dataIndex:'serial_date',width:150},
					{header:'发卡机柜员号',dataIndex:'cardmachine_tellerno',width:150},
					{header:'交易种类',dataIndex:'menu_name',width:150},
					{header:'卡号',dataIndex:'account_num',width:150},
					{header:'户名',dataIndex:'custinfo_name',width:150},
					{header:'金额',dataIndex:'amount',width:150},
					{header:'交易状态',dataIndex:'trade_status',width:150,renderer:function(value){
						if(value == "0"){
							return "成功"
						}
						return value == "1" ? "失败" : "超时";
					}},
					{header:'核心流水号',dataIndex:'esb_channelserno',width:150}
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
						//获得页面表格中所有的值
						var query_obj = conditionPanel.getFields();
						//用户选择的开始时间条件
						var workdate_start = query_obj["workdate_start"];
						//用户选择的结束时间条件
						var workdate_end = query_obj["workdate_end"];
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
				}
			}
			
			/**
			 * @createCheckBoxValue
			 * @Description:判断checkbox数据
			 */
            function createCheckBoxValue(check,data){
            	if(data[check]==undefined){
            		data[check] = 'off';
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
  </body>
</html>
