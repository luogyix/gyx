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
    
    <title>预约信息查询</title>
    
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
		
		/**
		 *业务类型store
		 */
		var businessStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>/confManager/business_queryBusinessSmall?query_rules=4',
			autoLoad:true,
			root   : 'field1',
			fields:['bs_id','bs_name_ch']
		});
		
		//证件类型
		var custinfoTypeStore = new Ext.data.SimpleStore({ 
			
			data:[['0','0-身份证'],['1','1-银行卡卡号'],['2','2-客户号'],['3','3-护照号码'],
			['B','B-外国公民护照'],['C','C-户口簿'],['D','D-港澳居民通行证'],['E','E-还乡证'],['F','F-边民出入境通行证'],
			['G','G-军官证'],['H','H-士兵证'],['I','I-军事院校学员证'],['J','J-军队离休干部荣誉证'],['K','K-军官退休证'],
			['L','L-军人文职干部退休证'],['M','M-营业执照'],['N','N-批文'],['O','O-开户证明'],['P','P-其他'],
			['Q','Q-武警身份证'],['R','R-台湾居民通行证'],['S','S-中国公民护照'],['T','T-台湾居民往大陆通知证'],['U','U-临时身份证'],
			['X','X-事业单位登记证'],['Y','Y-企业名称预先核准通知书']],
			fields : ['dictValue','dictValueDesc']
		});
		
		var reservStatusStore = new Ext.data.SimpleStore({ 
			data:[['0','0-已登记'],['1','1-已激活'],['2','2-已取消'],['3','3-已过期']],
			fields : ['dictValue','dictValueDesc']
		});
		
		/**
		 *是否通知
		 */
		var isnotifyStore = new Ext.data.SimpleStore({ 
			data:[['0','0-未通知'],['1','1-已通知']],
			fields : ['dictValue','dictValueDesc']
		});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示预约信息查询界面
 */	    var systemUnits=pagereturn.field1;
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'isleaf',nodeLevel:'unitlevel',nodeType:'unitlevel'};
			var treeGenerator_a = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
			var tree_a = treeGenerator_a.generate(false,false,false,false);
			var treepanel_a = new Ext.tree.TreePanel({
				height:280,
				rootVisible : true, 
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
			var branch = <%="'"+user.getUnit().getUnitid()+"'"%>;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '预约信息查询', 120, 2,
				[
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,fieldLabel:'机构号',passName:'branch',tree:treepanel_a, width:200,value:<%="'" + user.getUnit().getUnitname().replace("珠海华润银行","").replace("股份有限公司","") + "'"%>}} ,//部门
				{rowIndex:0, field:{xtype:'combo', 	    name:'bs_id', 	fieldLabel:'业务名称', editable:false, hiddenName:'bs_id', store:businessStore, 	displayField:'bs_name_ch', valueField:'bs_id'}},
				{rowIndex:0, field:{xtype:'textfield', name:'reserv_id',fieldLabel:'预约号'}},
				{rowIndex:1, field:{xtype:'datefield',editable:false, 	name:'startdate',   format:'Y-m-d',value:new Date(),allowBlank : false, fieldLabel:'预约开始日期'}},
				{rowIndex:1, field:{xtype:'datefield',editable:false, 	name:'enddate',   format:'Y-m-d',value:new Date(),allowBlank : false, fieldLabel:'预约结束日期'}},
				{rowIndex:1, field:{xtype:'timefield', name:'reserv_begin_time',fieldLabel:'预约开始时间',format:'H:i:s',increment:60,minValue: '08:00:00',maxValue: '23:00:00',width:100}},
				{rowIndex:1, field:{xtype:'timefield', name:'reserv_end_time',fieldLabel:'预约结束时间',format:'H:i:s',increment:60,minValue: '08:00:00',maxValue: '23:00:00',width:100}}
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
				'<%=basePath%>/dataAnalysis/reservFlow_queryReservFlow',
				[
					'work_date','request_seq','request_channel','reserv_id','reserv_bs_id','bs_name_ch','reserv_record_date',
					'reserv_record_time','reserv_begin_time','reserv_end_time','reserv_branch','reserv_status',
					'reserv_modify_time','reserv_seq','queue_seq','service_seq','custinfo_type','custinfo_num',
					'custinfo_name','account','sms_customer','phone_no','reserv_begin_date',
					'reserv_end_date','reserv_zone','check_reserv_value'
				],[
					{header:'全选'},
					{header:'复选框'},
					{header:'日期',dataIndex:'work_date',width:100},
					{header:'机构号',dataIndex:'reserv_branch',width:100},
					{header:'预约号',dataIndex:'reserv_id',width:150},
					{header:'预约业务编号',dataIndex:'reserv_bs_id',width:100, renderer:function(value){
					      return getDictValue(value.toString(),businessStore,'bs_id','bs_name_ch');//翻译字段方法
				    }},
				    {header:'预约业务名称',dataIndex:'bs_name_ch',width:100},
					{header:'预约登记日期',dataIndex:'reserv_record_date',width:100},
					{header:'预约登记时间',dataIndex:'reserv_record_time',width:100},
					{header:'预约开始日期',dataIndex:'reserv_begin_date',width:100},
					{header:'预约结束日期',dataIndex:'reserv_end_date',width:100},
					{header:'预约开始时间',dataIndex:'reserv_begin_time',width:100},
					{header:'预约结束时间',dataIndex:'reserv_end_time',width:100},
					{header:'预约区域',dataIndex:'reserv_zone',width:100},
					{header:'预约状态',dataIndex:'reserv_status',width:100, renderer:function(value){
					      return getDictValue(value.toString(),reservStatusStore,'dictValue','dictValueDesc');//翻译字段方法
				    }},
					{header:'预约编号验证码',dataIndex:'check_reserv_value',width:120},
					{header:'修改时间',dataIndex:'reserv_modify_time',width:100},
					{header:'预约流水',dataIndex:'reserv_seq',width:100},
					{header:'排队流水',dataIndex:'queue_seq',width:200},
					{header:'柜员服务流水',dataIndex:'service_seq',width:100},
					{header:'证件类型',dataIndex:'custinfo_type',width:100, renderer:function(value){
					      return getDictValue(value.toString(),custinfoTypeStore,'dictValue','dictValueDesc');//翻译字段方法
				    }},
					{header:'证件号码',dataIndex:'custinfo_num',width:100},
					{header:'客户姓名',dataIndex:'custinfo_name',width:100},
					{header:'账户',dataIndex:'account',width:100},
					{header:'是否短信通知客户',dataIndex:'sms_customer',width:100, renderer:function(value){
					      return getDictValue(value.toString(),isnotifyStore,'dictValue','dictValueDesc');//翻译字段方法
				    }},
					{header:'预约人手机号',dataIndex:'phone_no',width:120},
					{header:'请求序号',dataIndex:'request_seq',width:100},
					{header:'请求渠道',dataIndex:'request_channel',width:150}
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
						var branch = query_obj["branch"];
						
						if(null==branch||branch===undefined||branch.length==0){
							Ext.MessageBox.alert('信息提示','请选择想查询的机构!');
							return;
						}
						pagequeryObj.queryPage(query_obj);
					break;
					case 1:
						conditionPanel.reset();
						Ext.getCmp('unitname').setPassValue(<%="'"+user.getUnitid()+"'"%>);
						Ext.getCmp('unitname').setValue(<%="'" + user.getUnit().getUnitname() + "'"%>);					break;
					case 2:
						var query_obj = conditionPanel.getFields();
						var branch = query_obj["branch"];
						
						if(null==branch||branch===undefined||branch.length==0){
							Ext.MessageBox.alert('信息提示','请选择想查询的机构!');
							return;
						}
						if(pagequeryObj.selfPagingTool.totalNum == -1){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.queryneeded"/>');
						}else if(pagequeryObj.selfPagingTool.totalNum == 0 ){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commoninfo"><s:param>'+'查询记录数为0，无数据需要下载！'+'</s:param></s:text>');
						}/*else if(pagequeryObj.selfPagingTool.totalNum > 10000){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.recordsoverlimit"/>');
						}*/else{
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
	
	<s:form action="/dataAnalysis/reservFlow_exportReservFlow" target="REPORTRESULTFRAME" namespace="./">
		<s:hidden name="querycondition_str"></s:hidden>
	</s:form>
	<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
	
  </body>
</html>
