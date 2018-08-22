<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String template_id = (String)request.getSession().getAttribute("template");
String branch;
if(template_id!=null){
	branch = template_id;
}else{
	branch = user.getUnitid();
}

String hostIp = user.getHostip();
boolean machine_view_flag = (Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.MACHINEVIEWFLAG);
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
	<script type="text/javascript" src="extjs/ext-all.gzjs"></script>
	<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/common/TreeFilter.js"></script>
	<script type="text/javascript" src="selfjs/common/PinyinFilter.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
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
/**
 * @Title: loadPage 
 * @Description: 显示排队机信息界面
 */	
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '排队机信息配置', 120, 1,
				[
					{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,		fieldLabel:'机构号',passName: 'branch', tree:treepanel_a, width:202, value:<%="'" + user.getUnit().getUnitname() + "'"%>}}, 
					{rowIndex:0, field:{xtype:'combo', 	name:'machine_type',id:'machine_typea',  editable:false,	hiddenName:'machine_type',fieldLabel:'机具类别',store:machineTypeStore, 	displayField:'value', valueField:'key'}} 
				],
				[
				{iconCls: "x-image-query", 			        id:'01',    text:'<s:text name="common.button.query"/>'}		//查询
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/devmanager/machineDevice_queryMachineDevice',
				['branch','branch_name','machine_type','oid','macaddress','machine_num','machine_code','maching_firm','idcard_status',
				'cardsend_status','cardread_status','iccr_status','idcr_status','ticket_status','laser_status','carmar_status','pwdkeybord_status',
				'fingerprt_status','ukey_status','siu_status','beer_status','comp_status','voice_status','appr_status'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'设备唯一标识',dataIndex:'oid',width:150},
				{header:'机构号',dataIndex:'branch',width:150},
				{header:'机构名',dataIndex:'branch_name',width:150},
				{header:'mac地址',dataIndex:'macaddress',width:230},
				{header:'机具类别',dataIndex:'machine_type',width:250,renderer:function(value){
						value = value.toString();
	          		 	return getDictValue(value,machineTypeStore,'key','value');//翻译字段方法
	       		}},
				{header:'机具编号',dataIndex:'machine_num',width:70},
				{header:'机具型号',dataIndex:'machine_code',width:70},
				{header:'机具厂商',dataIndex:'maching_firm',width:70},
				{header:'身份证阅读器状态',dataIndex:'idcard_status',width:140,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'发卡器状态',dataIndex:'cardsend_status',width:100,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'读卡器状态',dataIndex:'cardread_status',width:100,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'非接IC卡状态',dataIndex:'iccr_status',width:100,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'刷磁设备状态',dataIndex:'idcr_status',width:100,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'凭条打印机状态',dataIndex:'ticket_status',width:100,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'激光打印机状态',dataIndex:'laser_status',width:100,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'摄像头状态',dataIndex:'carmar_status',width:100,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'密码键盘状态',dataIndex:'pwdkeybord_status',renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'指纹仪状态',dataIndex:'fingerprt_status',width:100,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'ukey状态',dataIndex:'ukey_status',width:100,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'红外探测仪状态',dataIndex:'siu_status',width:100,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'条屏状态',dataIndex:'beer_status',width:100,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'综合屏状态',dataIndex:'comp_status',width:100,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'音频状态',dataIndex:'voice_status',width:100,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}},
				{header:'评价器状态',dataIndex:'appr_status',width:100,renderer:function(value){
					if(value=='0'){
						value='<a style="color:green">正常</a>';
					}else if(value==''){
						value='--';
					}else{
						value='<a style="color:red">不正常</a>';
					}
					return value;
				}}
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
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
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
    		Ext.getCmp('unitname').setPassValue(<%="'"+user.getUnitid()+"'"%>);
      	}
 	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
  </body>
</html>
