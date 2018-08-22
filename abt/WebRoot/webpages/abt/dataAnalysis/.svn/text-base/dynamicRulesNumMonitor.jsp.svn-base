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
    
    <title>动态排队流水监控</title>
    
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
		var defWaitnumThreshold = '';
		var defWaittimeThreshold = '';
		var dataStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>confManager/branchParam_loadConfByBranch',
			root: 'field1',
			//dataStore.load({params:temp}); temp = {}; temp[''] = Ext.getCmp('').getValue();
			fields : ['branch','default_flag','reserv_status','reserv_max_time','reserv_maxdays_before','reserv_minmin_before','reserv_prompt'
			          ,'negative_monitor','remaind_flag','def_waitnum_threshold','def_waittime_threshold','def_notify_threshold','def_show_notify_threshold'],
			listeners : {  
				load : function(store,records){
					if(store.data.items!=''){
						defWaitnumThreshold = store.getAt(0).data['def_waitnum_threshold'];
						defWaittimeThreshold = store.getAt(0).data['def_waittime_threshold'];
					}
				}
			}
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
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '排队流水监控', 120, 1,
				[
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,fieldLabel:'机构号',passName:'branch',tree:treepanel_a, width:200,value:<%="'" + user.getUnit().getUnitname() + "'"%>}} //部门
				],
				[
				{icon: "extjs/resources/images/access/grid/drop-yes.gif",id:'fstart',text:'启动'},//启动
				{icon: "extjs/resources/images/access/grid/drop-no.gif",id:'fstop',text:'停止',disabled: true}, //停止
				{iconCls: "x-image-reset",id:'reset',text:'<s:text name="common.button.reset"/>'}//重置
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			var task = {
	        	run: function(){
	        		var query_obj = conditionPanel.getFields();
	        		pagequeryObj.queryPage(query_obj);
	        	},
	        	interval: 60000
	        };
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/dataAnalysis/dynamicRulesNumMonitor_queryDynamicRulesNum',
				[
					'work_date','queue_num','branch','branch_name','bs_name_ch',
					'queuetype_name','custtype_name','en_queue_time','wait_time'
				],
				[
					{header:'全选'},
					{header:'复选框'},
					{header:'日期',dataIndex:'work_date',width:100},
					{header:'队列号',dataIndex:'queue_num',width:100},
					{header:'机构号',dataIndex:'branch',width:80},
					{header:'机构名称',dataIndex:'branch_name',width:150},
					{header:'业务名称',dataIndex:'bs_name_ch',width:120},
					{header:'队列名称',dataIndex:'queuetype_name',width:120},
					{header:'客户类型名称',dataIndex:'custtype_name',width:100},
					{header:'进队时间',dataIndex:'en_queue_time',width:100},
					{header:'等待时间',dataIndex:'wait_time',width:100},
					{header:'号码状态',dataIndex:'wait_time',width:100,renderer:function(value){
						value = value.replace('分钟','');
						if(parseInt(value)>parseInt(defWaittimeThreshold)&&defWaittimeThreshold!=0){
							return '<a style="color:red">超时</a>';
						}
						return '<a style="color:green">正常</a>';
					}}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			 /**
			 * @Title:onButtonClicked
			 * @Description:触发按钮
			 */
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
						var branch = query_obj["branch"];
						if(null==branch||branch===undefined||branch.length==0){
							Ext.MessageBox.alert('信息提示','请选择想查询的机构!');
							return;
						}
		        		dataStore.load({
			        		params:{'branch':branch,'query_rules':'0'},
			        		callback:function(r,options,success){
								if(success){
									Ext.TaskMgr.start(task);
									Ext.getCmp('fstop').enable();
					        		Ext.getCmp('fstart').disable();
					        		Ext.getCmp('reset').disable();
		       					}
							},
							scope:this
			        	});
						break;
					case 1:
						Ext.TaskMgr.stop(task);
		        		Ext.getCmp('fstart').enable();
		        		Ext.getCmp('reset').enable();
		        		Ext.getCmp('fstop').disable();
						break;
					case 2:
						conditionPanel.reset();
						Ext.getCmp('unitname').setPassValue(<%="'"+user.getUnitid()+"'"%>);
						Ext.getCmp('unitname').setValue(<%="'" + user.getUnit().getUnitname() + "'"%>);
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
  </body>
</html>
