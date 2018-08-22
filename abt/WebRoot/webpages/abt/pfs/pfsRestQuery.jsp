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
    
    <title>休眠排队号查询</title>
    
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
	<script type="text/javascript" src="selfjs/pagequery/pagetoafaquery.js"></script>	
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
		//排队号类型
		var queuenum_typeStore =  new Ext.data.SimpleStore({ 
			data:[['1',"1-普通"],['2',"2-转移"],['3','3-预约']],
			fields : ['dictValue','dictValueDesc']
		});
		
		//排队状态
		var queue_statusStore = new Ext.data.SimpleStore({ 
			data:[['0','0-取号'],['1',"1-业务办理中"],['2',"2-业务办理结束"],['3','3-弃号'],['4','4-转移'],['5','5-休眠']],
			fields : ['dictValue','dictValueDesc']
		});
		
		//内部客户类型
		var custTypeIStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%><%=basePath%>/confManager/custType_queryCustTypeSmall.action',
			autoLoad:true,
			root   : 'field1',
			fields : ['custtype_i','custtype_name']
		});
		
		//机构类型
		var branch_levelStore = new Ext.data.SimpleStore({ 
			data:[['1','1-总行'],['2',"2-分行"],['3',"3-支行"],['4','4-网点']],
			fields : ['dictValue','dictValueDesc']
		});
		
		//业务类型
		var bs_idStore = new Ext.data.JsonStore({
			fields : ['bs_id','bs_name_ch'],  
			url    : '<%=basePath%>pfs/pfsRestQuery_getSystemDictionaryItem?query_rules=4',
			root   : 'field1', autoLoad:true
		});
		
		//证件类型
		var custinfo_typeStore = new Ext.data.SimpleStore({ 
			data:[['0','0-身份证'],['1',"1-银行卡卡号"],['2',"2-客户号"],['3','3-护照号码'],['4','4-帐号'],['9','9-其他']],
			fields : ['dictValue','dictValueDesc']
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
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '休眠排队号查询', 120, 1,
				[
				{rowIndex:0, field:{xtype:'textfield', 	name:'queue_num', width:'80',fieldLabel:'队列号'}},
				{rowIndex:0, field:{xtype:'combo', name:'queuenum_type',fieldLabel:'排队号类型',editable:false,hiddenName:'queuenum_type',displayField:'dictValueDesc',valueField:'dictValue',store: queuenum_typeStore}},
				{rowIndex:0, field:{xtype:'textfield', 	name:'queuetype_id', width:'80',fieldLabel:'队列编号'}},
				{rowIndex:0, field:{xtype :'combo', name:'bs_id',fieldLabel:'业务类型',id:'sel_bs_id',
					store:bs_idStore, displayField:"bs_name_ch", valueField:"bs_id",hiddenName:'bs_id', mode:'local', triggerAction: 'all', forceSelection:true, editable:false}},
				{rowIndex:0, field:{xtype : 'combo', name:'custtype_i', fieldLabel:'内部客户类型',blankText:'请选择内部客户类型',hiddenName:'custtype_i',store:custTypeIStore,displayField:"custtype_name",valueField:"custtype_i",editable:false}}
				],
				[
				{iconCls: "x-image-query",id:'01',text:'<s:text name="common.button.query"/>'},//查询
				{iconCls: "x-image-application_form_edit",id:'02',text:'恢复排队号'},//恢复排队号
				{iconCls: "x-image-reset",id:'03',text:'<s:text name="common.button.reset"/>'}//重置
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>pfs/pfsRestQuery_queryQMRestPage',
				['branch','queue_num','branch_name','qm_num','bs_name_ch','queuetype_name','custtype_name','win_num',
                 'queuenum_type','custinfo_type','custinfo_num','queue_status','en_queue_time','remaind_phone','check_queue_value'
				],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'队列号',dataIndex:'queue_num',width:100},
				{header:'机构名',dataIndex:'branch_name',width:100},
				{header:'排队机编号',dataIndex:'qm_num',width:100},
				{header:'业务名称',dataIndex:'bs_name_ch',width:100},
				{header:'队列名称',dataIndex:'queuetype_name',width:100},
				{header:'客户类型名称',dataIndex:'custtype_name',width:100},
				{header:'处理窗口号',dataIndex:'win_num',width:100},
				{header:'排队号类型',dataIndex:'queuenum_type',width:100,renderer:function(value){
						return getDictValue(value,queuenum_typeStore,'dictValue','dictValueDesc');
					}},
				{header:'证件类型',dataIndex:'custinfo_type',width:100,renderer:function(value){
						return getDictValue(value,custinfo_typeStore,'dictValue','dictValueDesc');
					}},
				{header:'证件号码',dataIndex:'custinfo_num',width:200},
				{header:'排队状态',dataIndex:'queue_status',width:100,renderer:function(value){
						return getDictValue(value,queue_statusStore,'dictValue','dictValueDesc');
					}},
				{header:'进队时间',dataIndex:'en_queue_time',width:100},
				{header:'手机号',dataIndex:'remaind_phone',width:200},
				{header:'排队号验证码',dataIndex:'check_queue_value',width:100,hidden:true}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			/**
			* @Title:onButtonClicked
			* @Description:触发"查询"的选择语句
			*/
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
						break;
					case 1:
						 var submitdata = pagequeryObj.getSelectedObjects(['branch','queue_num','check_queue_value','queue_status']);
						if(submitdata === undefined || submitdata.length !== 1){
							Ext.MessageBox.alert('系统提示','请选择一条记录');
							break;
						}
								requestAjax('<%=basePath%>pfs/pfsRestQuery_renewQueueNum',submitdata[0],function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>',sRet.field2,function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});			
					    break;
					case 2:
						conditionPanel.reset();
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
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
  </body>
</html>
