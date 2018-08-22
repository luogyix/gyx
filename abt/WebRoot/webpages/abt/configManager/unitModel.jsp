<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>机构参数模板配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		/**
		 * 翻译字段用store
		 */
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.chart.Chart.CHART_URL = '<%=basePath%>extjs/resources/charts.swf';
		Ext.QuickTips.init();
		var systemUnits=pagereturn.field1;
		var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'isleaf',nodeLevel:'unitlevel',nodeType:'unitlevel'};
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
		var modelStore=new Ext.data.SimpleStore({ 
			data:[["1-模板1","1"],["2-模板2","2"],["3-模板3","3"],["4-模板4","4"],["5-提取上级配置","5"]],
			fields : ['value','key']
		});
		
		Ext.onReady(loadPage);
/**
 * @Title: loadPage 
 * @Description: 显示机构参数配置界面
 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var form = new Ext.FormPanel({
				labelWidth: 100, // label settings here cascade unless overridden
				autoScroll:true,
				frame:true,
				title: '选择机构参数模板',
				width: 350,
				height: 150,
				buttonAlign :'left',
				defaults: {width: 230},
				layout:'form',
				items: [{
					xtype : 'combotree', 
					name:'branchCom',
					id:'branchCom',
					allowBlank : false,
					fieldLabel:'配置机构',
					tree: treepanel_a,
					allowUnLeafClick:false
				},{
					xtype : 'combo', 
					name:'modelCom',
					id:'modelCom',
					store:modelStore,
					fieldLabel:'选用模板',
					editable:false,	
					allowBlank : false,
					hiddenName:'modelCom',
					displayField:"value",
					valueField:"key",
					triggerAction : 'all',
					mode: 'local'
				}],
				buttons: [{
					text: '保存',
					handler : function() { // 按钮响应函数
						onSaveClicked();
					} 
				}]
			});
			
			form.render(document.body);
			
            /**
			* @Title:onSaveClicked
			* @Description:保存修改事件
			*/
			function onSaveClicked(){
				var submitData = {};
				submitData['branch'] = Ext.getCmp('branchCom').getValue();
				submitData['model'] = Ext.getCmp('modelCom').getValue();
				if(""==submitData['branch']){
					Ext.Msg.alert('提示','请选择要配置的机构');
					return;
				}
				if(""==submitData['model']){
					Ext.Msg.alert('提示','请选择模板');
					return;
				}
				requestAjax('<%=basePath%>confManager/unitModel_saveModel', submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','配置机构参数模板成功',function(id){
					});
				});
			}
		} 
	</script>
  </head>
  <body>
  </body>
</html>
