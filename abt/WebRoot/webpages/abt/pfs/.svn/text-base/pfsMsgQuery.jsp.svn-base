<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String branch = user.getUnitid();
String hostIp = user.getHostip();
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>预填单信息查询</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>	
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
		//机构类型
		var branch_levelStore = new Ext.data.SimpleStore({ 
			data:[['1','1-总行'],['2',"2-分行"],['3',"3-支行"],['4','4-网点']],
			fields : ['dictValue','dictValueDesc']
		});
		//证件类型
		var per_agent_type = new Ext.data.SimpleStore({ 
			data:[['0','0-身份证'],['1',"1-银行卡卡号"],['2',"2-客户号"],['3','3-护照号码'],['4','4-帐号'],['9','9-其他']],
			fields : ['dictValue','dictValueDesc']
		});
		//介质类型
		var medium_typeStore = new Ext.data.SimpleStore({ 
			data:[['1','1-卡'],['2',"2-存折"]],
			fields : ['dictValue','dictValueDesc']
		});
		//填单业务ID
		var trade_idStore = new Ext.data.JsonStore({
			fields : ['trade_id'],  
			url    : '<%=basePath%>pfs/pfsMsgQuery_getSystemDictionaryItem?query_rules=3',
			root   : 'field1', autoLoad:true
		});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		
/**
 * @Title: loadPage 
 * @Description: 显示填单机信息界面
 */	
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '预填单信息查询', 120, 1,
				[
				{rowIndex:0, field:{xtype:'datefield',editable:true,name:'work_date', format:'Y-m-d',allowBlank : true,fieldLabel:'选择日期',minValue:new Date(new Date().setDate(new Date().getDate()))}},
				{rowIndex:0, field:{xtype:'textfield', 	name:'queue_num', width:'80',fieldLabel:'排队号'}}
				],
				[
				{iconCls: "x-image-query",id:'01',text:'<s:text name="common.button.query"/>'},//查询
			//	{iconCls: "x-image-application_form_delete",id:'03', 	text:'<s:text name="common.button.deleterecord"/>'},//删除
				{iconCls: "x-image-reset",id:'04', text:'<s:text name="common.button.reset"/>'}//重置
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>pfs/pfsMsgQuery_queryQMInfoPage',
				['work_date','pfs_seq','branch','reserv_id','queue_num','trade_id','trade_name_ch','trade_code_fr','trade_type','branch_level','per_name',
				 'per_type','per_num','per_phone','agent_name','agent_type','agent_num','agent_phone','medium_type','medium_num','pfs_content',],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'日期',dataIndex:'work_date',width:100},
				{header:'预填单流水号',dataIndex:'pfs_seq',width:100},
				{header:'网点号',dataIndex:'branch',width:100},
				{header:'预约号',dataIndex:'reserv_id',width:100},
				{header:'排队号',dataIndex:'queue_num',width:100},
				{header:'填单业务ID',dataIndex:'trade_id',width:100},
				{header:'交易名称',dataIndex:'trade_name_ch',width:100},
				{header:'柜面交易码',dataIndex:'trade_code_fr',width:100},
				{header:'所属业务类别',dataIndex:'trade_type',width:100,renderer:function(value){return value=='1'?'对公业务':'个人业务'}},
				{header:'机构类型',dataIndex:'branch_level',width:100,renderer:function(value){return getDictValue(value,branch_levelStore,'dictValue','dictValueDesc');}},
				{header:'办理人姓名',dataIndex:'per_name',width:100},
				{header:'办理人证件类型',dataIndex:'per_type',width:100,renderer:function(value){return getDictValue(value,per_agent_type,'dictValue','dictValueDesc');}},
				{header:'办理人证件号',dataIndex:'per_num',width:150},
				{header:'办理人手机号',dataIndex:'per_phone',width:100},
				{header:'代理人姓名',dataIndex:'agent_name',width:100},
				{header:'代理人证件类型',dataIndex:'agent_type',width:100,renderer:function(value){return getDictValue(value,per_agent_type,'dictValue','dictValueDesc');}},
				{header:'代理人证件号',dataIndex:'agent_num',width:150},
				{header:'代理人手机号',dataIndex:'agent_phone',width:100},
				{header:'介质类型',dataIndex:'medium_type',width:150},
				{header:'介质号码',dataIndex:'medium_num',width:200},
				{header:'填单内容',dataIndex:'pfs_content',width:1000,hidden:true}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			
			/**
			* @Title:onButtonClicked
			* @Description:触发"查询","添加","修改","删除"的选择语句
			*/
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
						break;
	
					/*case 1:
					    var submitdata = pagequeryObj.getSelectedObjects(['work_date','pfs_seq']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							return;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>pfs/pfsMsgQuery_delMsgInfo',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					    break;*/
					case 1:
						conditionPanel.reset();
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
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
	<div id="editWindow"></div>
  </body>
</html>
