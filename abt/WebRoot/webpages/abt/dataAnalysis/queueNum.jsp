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
    
    <title>历史流水查询</title>
    
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
		
		var businessStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>/confManager/business_queryBusinessSmall?query_rules=4',
			autoLoad:true,
			root   : 'field1',
			fields:['bs_id','bs_name_ch']
		});
		
		var queuetypeStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>/confManager/queuetype_queryQueueTypeSmall?query_rules=4',
			autoLoad:true,
			root   : 'field1',
			fields:['queuetype_id','queuetype_name','queuetype_level']
		});
		
		var custTypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>/confManager/custType_queryCustTypeSmall.action',
			autoLoad:true,
			root   : 'field1',
			fields : ['custtype_i','custtype_e','custtype_name']
		});
		
		//证件类型
		var custinfo_typeStore = new Ext.data.SimpleStore({ 
			data:[['0','0-身份证'],['1',"1-银行卡卡号"],['2',"2-客户号"],['3','3-护照号码']],
			fields : ['dictValue','dictValueDesc']
		});
		//排队号类型
		var queuenum_typeStore =  new Ext.data.SimpleStore({ 
			data:[['1',"1-普通"],['2',"2-转移"],['3','3-预约'],['4','4-填单']],
			fields : ['dictValue','dictValueDesc']
		});
		//排队状态
		var queue_statusStore = new Ext.data.SimpleStore({ 
			data:[['0','0-取号'],['1',"1-业务办理中"],['2',"2-业务办理结束"],['3','3-弃号'],['4','4-转移']],
			fields : ['dictValue','dictValueDesc']
		});
		//提醒标志
		var remaind_flagStore = new Ext.data.SimpleStore({ 
			data:[['0','0-否'],['1','1-是']],
			fields : ['dictValue','dictValueDesc']
		});
		var reserv_flagStore = new Ext.data.SimpleStore({ 
			data:[['0','0-无'],['1','1-有']],
			fields : ['dictValue','dictValueDesc']
		});
		var isnotifyStore = new Ext.data.SimpleStore({ 
			data:[['0','0-未通知'],['1','1-已通知']],
			fields : ['dictValue','dictValueDesc']
		});
		//是否御填单
		var isbeforeStore = new Ext.data.SimpleStore({ 
			data:[['0','0-否'],['1','1-是']],
			fields : ['dictValue','dictValueDesc']
		});
		//填单状态
		var beforestatusStore = new Ext.data.SimpleStore({ 
			data:[['0','0-未完成'],['1','1-已完成'],['2','2-取消'],['3','3-正在处理'],['4','4-处理完毕']],
			fields : ['dictValue','dictValueDesc']
		});
		var assessStatusStore = new Ext.data.SimpleStore({ 
			data:[['-1','客户未评价'],['0','柜员未发起评价'],['1','不满意'],['2','一般'],['3','满意'],['4','非常满意']],
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
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '排队号历史查询', 120, 3,
				[
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,fieldLabel:'机构号',passName:'branch',tree:treepanel_a, width:200,value:<%="'" + user.getUnit().getUnitname()+ "'"%>}} ,//部门
				{rowIndex:0, field:{xtype:'datefield',editable:false, 	name:'startDate', format:'Y-m-d',value:new Date(new Date().setDate(new Date().getDate() - 30)),allowBlank : false, fieldLabel:'开始时间'}},
				{rowIndex:0, field:{xtype:'datefield',editable:false, 	name:'endDate', format:'Y-m-d',value:new Date(),allowBlank : false,fieldLabel:'结束时间'}},
				{rowIndex:0, field:{xtype:'combo', name:'custtype',fieldLabel:'客户类型',editable:false,hiddenName:'custtype',displayField:'custtype_name',valueField:'custtype_i',store: custTypeStore}},
				{rowIndex:0, field:{xtype:'textfield', 	name:'softcall_teller_name',id:'softcall_teller_name',fieldLabel:'柜员名称',width:'100'}},//柜员名称
				{rowIndex:1, field:{xtype:'combo', 	    name:'business', 	fieldLabel:'业务名称', editable:false, hiddenName:'business', store:businessStore, 	displayField:'bs_name_ch', valueField:'bs_id'}},
				{rowIndex:1, field:{xtype:'combo', name:'queuetype',fieldLabel:'队列类型',editable:false,hiddenName:'queuetype',displayField:'queuetype_name',valueField:'queuetype_id',store: queuetypeStore}},
				{rowIndex:1, field:{xtype:'textfield', 	name:'queue_time', 	width:'80',fieldLabel:'最小等待时间(分钟)'}},//等待时间
				{rowIndex:1, field:{xtype:'textfield', 	name:'serv_time', 	width:'80',fieldLabel:'最小服务时间(分钟)'}},//服务时间
				{rowIndex:1, field:{xtype:'textfield', 	name:'serv_time_2', 	width:'80',fieldLabel:'最大服务时间(分钟)'}}//服务时间
				],
				[
				{iconCls: "x-image-query",text:'<s:text name="common.button.query"/>'},//查询
				{iconCls: "x-image-reset", 			text:'<s:text name="common.button.reset"/>'},		//重置
				{iconCls: "x-image-application_form_add",id:'06',text:'导出Excel'} //导出excel
				],
				onButtonClicked
			);
			
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/dataAnalysis/queueNum_queryQueueNum',
				[
					'work_date','queue_num','branch','branch_name','transfer_num','qm_num','qm_ip',
					'queue_seq','softcall_teller','softcall_teller_name','softcall_seq','bs_id','bs_name_ch',
					'queuetype_id','queuetype_name','custtype_i','custtype_name','node_id','win_num','window_ip',
					'queuenum_type','custinfo_type','custinfo_num','queue_status','en_queue_time','de_queue_time','wait_time','serv_time',
					'start_serv_time','finish_serv_time','assess_status','reserv_flag','reserv_id','remaind_flag',
					'remaind_phone','noti_waitnum','noti_setnum','isnotify','isbefore','beforestatus','vcalltime','vcalltime_2'],
				[
				{header:'全选'},
				{header:'复选框'},
					{header:'日期',dataIndex:'work_date',width:100},
					{header:'队列号',dataIndex:'queue_num',width:100},
					{header:'机构号',dataIndex:'branch',width:80},
					{header:'机构名称',dataIndex:'branch_name',width:150},
					{header:'转移次数',dataIndex:'transfer_num',width:65},
					{header:'柜员号',dataIndex:'softcall_teller',width:100},
					{header:'柜员名',dataIndex:'softcall_teller_name',width:100},
					{header:'业务编号',dataIndex:'bs_id',width:120},
					{header:'业务名称',dataIndex:'bs_name_ch',width:120},
					{header:'队列编号',dataIndex:'queuetype_id',width:120},
					{header:'队列名称',dataIndex:'queuetype_name',width:120},
					{header:'内部客户类型',dataIndex:'custtype_i',width:100,renderer:function(value){
						return getDictValue(value,custTypeStore,'value','key');
					}},
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
					{header:'出队时间',dataIndex:'de_queue_time',width:100},
					{header:'等待时间',dataIndex:'wait_time',width:100},
					{header:'开始办理业务时间',dataIndex:'start_serv_time',width:120},
					{header:'结束办理业务时间',dataIndex:'finish_serv_time',width:120},
					{header:'服务时间',dataIndex:'serv_time',width:100},
					{header:'客户评价结果',dataIndex:'assess_status',width:100,renderer:function(value){
						return getDictValue(value,assessStatusStore,'dictValue','dictValueDesc');
					}},
					{header:'预约标志',dataIndex:'reserv_flag',width:60,renderer:function(value){
						return getDictValue(value,reserv_flagStore,'dictValue','dictValueDesc');
					}},
					{header:'预约编号',dataIndex:'reserv_id',width:60},
					{header:'提醒标志',dataIndex:'remaind_flag',width:80,renderer:function(value){
						return getDictValue(value,remaind_flagStore,'dictValue','dictValueDesc');
					}},
					{header:'手机号',dataIndex:'remaind_phone',width:100},
					{header:'客户设定的通知阀值',dataIndex:'noti_waitnum',width:150},
					{header:'网点预设的通知阀值',dataIndex:'noti_setnum',width:150},
					{header:'是否已经通知',dataIndex:'isnotify',width:100,renderer:function(value){
						return getDictValue(value,isnotifyStore,'dictValue','dictValueDesc');
					}},
					{header:'是否预填单',dataIndex:'isbefore',width:80,renderer:function(value){
						return getDictValue(value,isbeforeStore,'dictValue','dictValueDesc');
					}},
					{header:'填单状态',dataIndex:'beforestatus',width:80,renderer:function(value){
						return getDictValue(value,beforestatusStore,'dictValue','dictValueDesc');
					}},
/*					{header:'虚拟叫号时间',dataIndex:'vcalltime',width:120},
					{header:'虚拟叫号时间(等待)',dataIndex:'vcalltime_2',width:120},
*/					{header:'排队机编号',dataIndex:'qm_num',width:100},
					{header:'排队机IP',dataIndex:'qm_ip',width:100},
					/*{header:'排队机取号流水',dataIndex:'queue_seq',width:100},
					{header:'软叫号流水',dataIndex:'softcall_seq',width:100},
					{header:'节点ID(保留)',dataIndex:'node_id',width:100},*/
					{header:'处理窗口IP',dataIndex:'window_ip',width:220}
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
						var startdate=query_obj["startDate"];
						var endate=query_obj["endDate"];
						var branch = query_obj["branch"];
						var business = query_obj["business"];
						var queueType = query_obj["queuetype"];
						var queue_time = query_obj["queue_time"].trim();
						query_obj["queue_time"] = queue_time;
						var serv_time = query_obj["serv_time"].trim();
						query_obj["serv_time"] = serv_time;
						var serv_time_2 = query_obj["serv_time_2"].trim();
						query_obj["serv_time_2"] = serv_time_2;
						var softcall_teller_name = query_obj["softcall_teller_name"].trim();
						query_obj["softcall_teller_name"] = softcall_teller_name;
						
						if(softcall_teller_name.length > 5){
							Ext.MessageBox.alert('信息提示','柜员名称长度不可大于5位');
							return;
						}
						if(isNaN(queue_time)){
							Ext.MessageBox.alert('信息提示','请输入正确的最小等待时间');
							return;
						}else if(queue_time.length>4){
							Ext.MessageBox.alert('信息提示','最小等待时间长度不可大于4位');
							return;
						}else if(queue_time.indexOf('.') != -1){
							Ext.MessageBox.alert('信息提示','最小等待时间只能输入整数');
							return;
						}
						if(isNaN(serv_time)){
							Ext.MessageBox.alert('信息提示','请输入正确的最小服务时间');
							return;
						}else if(serv_time.length>4){
							Ext.MessageBox.alert('信息提示','最小服务时间长度不可大于4位');
							return;
						}else if(serv_time.indexOf('.') != -1){
							Ext.MessageBox.alert('信息提示','最小服务时间只能输入整数');
							return;
						}
						if(isNaN(serv_time_2)){
							Ext.MessageBox.alert('信息提示','请输入正确的最大服务时间');
							return;
						}else if(serv_time_2.length>4){
							Ext.MessageBox.alert('信息提示','最大服务时间长度不可大于4位');
							return;
						}else if(serv_time_2.indexOf('.') != -1){
							Ext.MessageBox.alert('信息提示','最大服务时间只能输入整数');
							return;
						}
						
						if(null==branch||branch===undefined||branch.length==0){
							Ext.MessageBox.alert('信息提示','请选择想查询的机构!');
							return;
						}
						if(startdate=="undefined"||startdate.indexOf("-")==-1||startdate.length==0||startdate.length!=10){
							Ext.MessageBox.alert('信息提示','请输入正确的起始日期!');
							return;
						}
						if(endate=="undefined"||endate.indexOf("-")==-1||endate.length==0||endate.length!=10){
							Ext.MessageBox.alert('信息提示','请输入正确的结束日期!');
							return;
						}
						if(startdate>endate){
							Ext.MessageBox.alert('信息提示','起始日期不能晚于结束日期!');
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
						var startdate=query_obj["startDate"];
						var endate=query_obj["endDate"] ;
						var branch = query_obj["branch"];
						var business = query_obj["business"];
						var queueType = query_obj["queuetype"];
						var custtype = query_obj["custtype"];
						
						var queue_time = query_obj["queue_time"].trim();
						query_obj["queue_time"] = queue_time;
						var serv_time = query_obj["serv_time"].trim();
						query_obj["serv_time"] = serv_time;
						var serv_time_2 = query_obj["serv_time_2"].trim();
						query_obj["serv_time_2"] = serv_time_2;
						var softcall_teller_name = query_obj["softcall_teller_name"].trim();
						query_obj["softcall_teller_name"] = softcall_teller_name;
						
						if(softcall_teller_name.length > 5){
							Ext.MessageBox.alert('信息提示','柜员名称长度不可大于5位');
							return;
						}
						if(isNaN(queue_time)){
							Ext.MessageBox.alert('信息提示','请输入正确的最小等待时间');
							return;
						}else if(queue_time.length>4){
							Ext.MessageBox.alert('信息提示','最小等待时间长度不可大于4位');
							return;
						}else if(queue_time.indexOf('.') != -1){
							Ext.MessageBox.alert('信息提示','最小等待时间只能输入整数');
							return;
						}
						if(isNaN(serv_time)){
							Ext.MessageBox.alert('信息提示','请输入正确的最小服务时间');
							return;
						}else if(serv_time.length>4){
							Ext.MessageBox.alert('信息提示','最小服务时间长度不可大于4位');
							return;
						}else if(serv_time.indexOf('.') != -1){
							Ext.MessageBox.alert('信息提示','最小服务时间只能输入整数');
							return;
						}
						if(isNaN(serv_time_2)){
							Ext.MessageBox.alert('信息提示','请输入正确的最大服务时间');
							return;
						}else if(serv_time_2.length>4){
							Ext.MessageBox.alert('信息提示','最大服务时间长度不可大于4位');
							return;
						}else if(serv_time_2.indexOf('.') != -1){
							Ext.MessageBox.alert('信息提示','最大服务时间只能输入整数');
							return;
						}
						
						if(null==branch||branch===undefined||branch.length==0){
							Ext.MessageBox.alert('信息提示','请选择想查询的机构!');
							return;
						}
						if(startdate=="undefined"||startdate.indexOf("-")==-1||startdate.length==0||startdate.length!=10){
							Ext.MessageBox.alert('信息提示','请输入正确的起始日期!');
							return;
						}
						if(endate=="undefined"||endate.indexOf("-")==-1||endate.length==0||endate.length!=10){
							Ext.MessageBox.alert('信息提示','请输入正确的结束日期!');
							return;
						}
						if(startdate>endate){
							Ext.MessageBox.alert('信息提示','起始日期不能晚于结束日期!');
							return;
						}
						if(pagequeryObj.selfPagingTool.totalNum == -1){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.queryneeded"/>');
						}else if(pagequeryObj.selfPagingTool.totalNum == 0 ){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commoninfo"><s:param>'+'查询记录数为0，无数据需要下载！'+'</s:param></s:text>');
						}else if(pagequeryObj.selfPagingTool.totalNum > 100000){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.recordsoverlimit"/>');
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
	
	<s:form action="/confManager/queueNum_exportExcel" target="REPORTRESULTFRAME">
		<s:hidden name="querycondition_str"></s:hidden>
	</s:form>
	<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
	
  </body>
</html>
