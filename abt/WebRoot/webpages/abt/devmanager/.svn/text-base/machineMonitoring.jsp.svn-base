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
    
    <title>机具外设状态</title>
    
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
		
		var peripheralTypeStore = new Ext.data.SimpleStore({
			data:[
			      	["1","1-综合屏"],
			      	["2","2-条屏"],
			      	["3","3-二代身份证阅读器"],
			      	["4","4-评价器"],
			      	["5","5-读卡器"],
			      	["6","6-语音设置"],
			      	["7","7-热敏打印机"],
			      	["8","8-IC读卡器"],
			      	["9","9-二维码扫描仪"],
			      	["10","10-摄像头"],
			      	["0","0-通用"]
			     ],
			fields : ['key','value']
		})
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
					{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitnameId',  allowUnLeafClick:true,		fieldLabel:'机构号',passName: 'branch', tree:treepanel_a, width:202, value:<%="'" + user.getUnit().getUnitname() + "'"%>}}, 
					{rowIndex:0, field:{xtype:'combo', 	name:'devicetype',id:'devicetypeId',  editable:false,	hiddenName:'devicetype',fieldLabel:'设备类型',store:deviceTypeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
					{rowIndex:0, field:{xtype:'combo', 	name:'peripheral_type',id:'peripheral_typeId',  editable:false,	hiddenName:'peripheral_type',fieldLabel:'外设类型',store:peripheralTypeStore, 	displayField:'value', valueField:'key'}},
					{rowIndex:0, field:{xtype:'datefield',editable:false, 	name:'workdate', format:'Ymd',value:new Date(),allowBlank : false,fieldLabel:'发生时间'}}
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
				'<%=basePath%>/devmanager/machineMonitoring_queryMachineMonitoring',
				[
				 	'branch',//机构号
				 	'workdate',//异常发生日期
				 	'devicetype',//设备类型
				 	'device_num',//设备编号
				 	'peripheral_type',//外设类型
				 	'status_code',//状态码
				 	'desc',//状态描述
				 	'time'//发生时间
				],
				[
					{header:'全选'},
					{header:'复选框'},
					{header:'设备所属机构号',dataIndex:'branch',width:150},
					{header:'设备唯一编号',dataIndex:'device_num',width:150},
					{header:'异常发生日期',dataIndex:'workdate',width:150},
					{header:'设备类型',dataIndex:'devicetype',width:150,renderer:function(value){
						return getDictValue(value,deviceTypeStore,'dictValue','dictValueDesc');
					}},
					{header:'外设类型',dataIndex:'peripheral_type',width:150,renderer:function(value){
						return getDictValue(value,peripheralTypeStore,'key','value');
					}},
					{header:'状态码',dataIndex:'status_code',width:150},
					{header:'状态描述',dataIndex:'desc',width:150},
					{header:'异常发生时间',dataIndex:'time',width:150}
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
						//用户选择的时间条件
						var workdate = query_obj["workdate"];
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
						if(workdate>myDate){
							Ext.MessageBox.alert('信息提示','请选择正确日期');
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