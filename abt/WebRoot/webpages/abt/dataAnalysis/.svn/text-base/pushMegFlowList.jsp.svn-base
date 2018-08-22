<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	User user = (User)request.getSession().getAttribute("logonuser");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<base href="<%=basePath%>">
	
	<title>消息流水查询</title>
	
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
	<script type="text/javascript" src="selfjs/common/TreeFilter.js"></script>
	<script type="text/javascript" src="selfjs/common/PinyinFilter.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	
	<script type="text/javascript">
		var pagereturn = ${actionresult};
		var systemUnits = pagereturn.field1;
		var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'',nodeLevel:'unitlevel',nodeType:'unitlevel'};
		//构建机构树
		var treeGenerator_a = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var tree_a = treeGenerator_a.generate(false,false,false,false);
		var treepanel_a = new Ext.tree.TreePanel({
			rootVisible : true, 
			height:220,
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
		
		//设备类型
		var deviceTypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=devicetype',
			root   : 'field1',
			fields : ['dictValue','dictValueDesc'],
			autoLoad:true
		});
			
		var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '消息流水查询配置', 120, 2,
				[ 
				  {rowIndex:1, field:{xtype:'timefield',name:'msgTime_begin_time',fieldLabel:'开始时间',format:'H:i:s',increment:30,minValue: '08:00:00',maxValue: '18:00:00'}},//开始时间
				  {rowIndex:1, field:{xtype:'timefield',name:'msgTime_end_time',fieldLabel:'结束时间',format:'H:i:s',increment:30,minValue: '08:00:00',maxValue: '18:00:00'}},//结束时间
				  {rowIndex:0, field:{xtype:'textfield',name:'msgId',emptyText:'--支持模糊查询--',fieldLabel:'消息流水号'}},//消息流水号
				  {rowIndex:0, field:{xtype:'combo', name:'devicetype',fieldLabel:'设备类型',forceSelection:true,hiddenName:'devicetype',displayField:'dictValueDesc',valueField:'dictValue',store: deviceTypeStore}},//设备类型
				  {rowIndex:0,field:{xtype:'combotree',name:'unitname',emptyText:'----<s:text name="admin.user.userunit"/>----',fieldLabel:'<s:text name="admin.user.userunit"/>',passName: 'unitid',tree:treepanel_a,value:<%="'" + user.getUnit().getUnitname()+ "'"%>, width:200,id:'unitnameid'}}//机构
				],
				[
				{iconCls: "x-image-query", 			text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-reset", 			text:'<s:text name="common.button.reset"/>'}		//重置
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/developTest/pushMegFlow_queryPushMsgList',
				['msgId', 'branch', 'pdNo', 'msgDate','msgTime','msgType','deviceType','msgContent','isNew'],
				[
					{header:'全选'},
					{header:'复选框'},
					{header:'消息流水号',dataIndex:'msgId',width:100},
					{header:'网点号', dataIndex:'branch',width:100},
					{header:'设备号',	dataIndex:'pdNo',width:100},
					{header:'日期',	dataIndex:'msgDate',width:100},
					{header:'时间',	dataIndex:'msgTime',width:100,renderer:function(value){
						//格式化时间为 H：m:s
						if(value != '' && value != undefined && value != null){
							var hour = value.substring(0,2);
							var min = value.substring(2,4);
							var second = value.substring(4,6);
							return hour+':'+min+':'+second;
						}
					}},
					{header:'消息类型',	dataIndex:'msgType',width:100},
					{header:'设备类型',	dataIndex:'deviceType',width:100,renderer:function(value){
						return getDictValue(value.toString(),deviceTypeStore,'dictValue','dictValueDesc');//翻译字段方法
					}},
					{header:'消息内容',	dataIndex:'msgContent',width:100},
					{header:'是否新消息',dataIndex:'isNew',width:100,renderer:function(value){
						//格式化是否是新消息为 0-否,1-是
						if(value != '' && value != undefined && value != null){
							if(value == 0){
								return '0-否';
							}
							if(value == 1){
								return '1-是';
							}
						}
					}}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			/*主窗口按钮设置*/
			function onButtonClicked(btn_index){
				switch(btn_index){
				case 0://查询
					var query_obj = conditionPanel.getFields();
				   //验证开始时间不能大于结束时间
					var msgBeginTime = query_obj["msgTime_begin_time"];
					var msgEndTime = query_obj["msgTime_end_time"];
					var reBeginTime = msgBeginTime.replace(/:/g,'');
					var reEndTime = msgEndTime.replace(/:/g,'');
					if(reBeginTime > reEndTime){
						Ext.Msg.alert('提示', '开始时间不能大于结束时间');
						return ;
					}
					pagequeryObj.queryPage(query_obj);
				break;
				case 1://重置
					Ext.getCmp('unitnameid').clearValue();
					conditionPanel.reset();
					Ext.getCmp('unitnameid').setPassValue(<%="'"+user.getUnit().getUnitid()+"'"%>);
					Ext.getCmp('unitnameid').setValue(<%="'" + user.getUnit().getUnitname() + "'"%>);
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
     		Ext.getCmp('unitnameid').setPassValue(<%="'"+user.getUnit().getUnitid()+"'"%>);
		}
	</script>
	</head>
	<body>
		<div id="queryConditionPanel"></div>
		<div id="pageQueryTable"></div>
		<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
	</body>
</html>