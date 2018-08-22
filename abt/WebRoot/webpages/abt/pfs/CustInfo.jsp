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
    
    <title>客户信息处理</title>
    
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
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		//客户类型
		var ocusttype_eStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>pfs/pfsCustInfo_queryCustType.action',
			autoLoad:true,
			root   : 'field1', 
			fields : ['custtype_name','custtype_e']
		});
		//服务期限
		var servetimeStore=new Ext.data.SimpleStore({ 
			data:[["0个月","0"],["1个月","1"],["2个月","2"],["3个月","3"],["4个月","4"],["5个月","5"],["6个月","6"],["7个月","7"],["8个月","8"],["9个月","9"],["10个月","10"],
				["11个月","11"],["12个月","12"],["13个月","13"],["14个月","14"],["15个月","15"],["16个月","16"],["17个月","17"],["18个月","18"],["19个月","19"],["20个月","20"],
				["21个月","21"],["22个月","22"],["23个月","23"],["24个月","24"],["25个月","25"],["26个月","26"],["27个月","27"],["28个月","28"],["29个月","29"],["30个月","30"],
				["31个月","31"],["32个月","32"],["33个月","33"],["34个月","34"],["35个月","35"],["36个月","36"],["37个月","37"],["38个月","38"],["39个月","39"],["40个月","40"],
				["41个月","41"],["42个月","42"],["43个月","43"],["44个月","44"],["45个月","45"],["46个月","46"],["47个月","47"],["48个月","48"],["49个月","49"],["50个月","50"],
				["51个月","51"],["52个月","52"],["53个月","53"],["54个月","54"],["55个月","55"],["56个月","56"],["57个月","57"],["58个月","58"],["59个月","59"],["60个月","60"],
				["61个月","61"],["62个月","62"],["63个月","63"],["64个月","64"],["65个月","65"],["66个月","66"],["67个月","67"],["68个月","68"],["69个月","69"],["70个月","70"],
				["71个月","71"],["72个月","72"],["73个月","73"],["74个月","74"],["75个月","75"],["76个月","76"],["77个月","77"],["78个月","78"],["79个月","79"],["80个月","80"],
				["81个月","81"],["82个月","82"],["83个月","83"],["84个月","84"],["85个月","85"],["86个月","86"],["87个月","87"],["88个月","88"],["89个月","89"],["90个月","90"],
				["91个月","91"],["92个月","92"],["93个月","93"],["94个月","94"],["95个月","95"],["96个月","96"],["97个月","97"],["98个月","98"],["99个月","99"]],
			fields : ['key','value']
		});
		
/**
 * @Title: loadPage 
 * @Description: 显示客户信息处理界面
 */	
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '客户信息处理', 120, 0,
				[
				],
				[
				{iconCls: "x-image-query", 			        id:'01',    text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-application_form_add", 	id:'02',	text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-application_form_edit", 	id:'03',	text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-application_form_delete",id:'04', 	text:'<s:text name="common.button.deleterecord"/>'}//删除
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>pfs/pfsCustInfo_queryCustInfoPage',
				['branch','ocusttype_e','ncusttype_e','servetime'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'原客户类型',dataIndex:'ocusttype_e',width:100,renderer:function(value){
          		 	return getDictValue(value,ocusttype_eStore,'custtype_e','custtype_name');
       			 }},
				{header:'新客户类型',dataIndex:'ncusttype_e',width:100,renderer:function(value){
          		 	return getDictValue(value,ocusttype_eStore,'custtype_e','custtype_name');
       			 }},
				{header:'服务期限(月)',dataIndex:'servetime',width:100,renderer:function(value){
          		 	return getDictValue(value,servetimeStore,'value','key');
       			 }}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '客户信息处理添加', 320, 220, 140, 1,
				[
				{colIndex:0, field:{xtype :'combo', name:'ocusttype_e', hiddenName:'ocusttype_e',fieldLabel:'原客户类型',allowBlank : false,blankText:'请选择原客户类型',editable:false,emptyText : '请选择原客户类型 ',
					store:ocusttype_eStore, displayField:"custtype_name", valueField:"custtype_e", mode:'local', triggerAction: 'all', forceSelection:true}},
				{colIndex:0, field:{xtype :'combo', name:'ncusttype_e',hiddenName:'ncusttype_e', fieldLabel:'新客户类型',allowBlank : false,blankText:'请选择新客户类型',editable:false,emptyText : '请选择新客户类型 ',
					store:ocusttype_eStore, displayField:"custtype_name", valueField:"custtype_e", mode:'local', triggerAction: 'all', forceSelection:true}},
				{colIndex:0, field:{xtype : 'combo',	fieldLabel:'服务期限',   name:'servetime', 	hiddenName:'servetime',allowBlank : false, blankText:'请选择服务期限',editable:false,emptyText : '请选择服务期限 ',
					store:servetimeStore, 	displayField:'key',valueField:'value', mode:'local', triggerAction: 'all', forceSelection:true}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left',80
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑填单机信息', 320, 220, 140, 1,
			   [
				{colIndex:0, field:{xtype :'combo', name:'ocusttype_e', hiddenName:'ocusttype_e', fieldLabel:'原客户类型',id:'upd_ocusttype_e',readOnly:true,editable:false,
					store:ocusttype_eStore, displayField:"custtype_name", valueField:"custtype_e"}},
				{colIndex:0, field:{xtype :'combo', name:'ncusttype_e', hiddenName:'ncusttype_e', fieldLabel:'新客户类型',id:'upd_ncusttype_e',readOnly:true,editable:false,
					store:ocusttype_eStore, displayField:"custtype_name", valueField:"custtype_e", mode:'local', triggerAction: 'all', forceSelection:true}},
				{colIndex:0, field:{xtype : 'combo',	fieldLabel:'服务期限',   name:'servetime', hiddenName:'servetime', allowBlank : false, blankText:'请选择服务期限',editable:false,
					store:servetimeStore, 	displayField:'key',valueField:'value', mode:'local', triggerAction: 'all', forceSelection:true,value:'0个月'}}
					],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left',80
			);
			
		   /**
			* @Title:onButtonClicked
			* @Description:触发"查询"，"添加"，"修改"，"删除"的选择语句
			*/
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
						break;
					case 1:
						addwindow.open();
						break;
					case 2:
					    var isEdit = true;
					    var records = pagequeryObj.getSelectedRecords();
						if(records === undefined || records.length !== 1){
							Ext.MessageBox.alert('系统提示','请选择一条记录');
							break;
						}
						var editable = pagequeryObj.getSelectedRecords();
						for(var i=0;i<editable.length;i++){
							if(records[i].get('editable') == '1'){
								Ext.MessageBox.alert('系统提示','该记录不能修改');
								isEdit = false;
							}
						}
						if(!isEdit){
							break;
						}
						//无法修改非本行机构
					    for(var i=0;i<records.length;i++){
					    	if(records[i].data.branch!=<%="'"+branch+"'"%>){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的配置');
					    		return;
					    	}
					    }
						editwindow.open();
						editwindow.updateFields(records[0]);
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['ocusttype_e','branch','ncusttype_e']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							return;
						}
						 //无法删除非本行机构
					    for(var i=0;i<submitdata.length;i++){
					    	if(submitdata[i].branch!=<%="'"+branch+"'"%>){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法删除非本机构的配置');
					    		return;
					    	}
					    }
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>pfs/pfsCustInfo_delCustInfo',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					    break;
				}
			}
			
			
			/**
			 * @Title:onButtonClicked
			 * @Description:点击修改时的操作
			 */
			function oneditclicked(){
				var submitData = editwindow.getFields();
				requestAjax('<%=basePath%>pfs/pfsCustInfo_editCustInfo',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
				});
			}
            /**
			* @Title:onaddclicked
			* @Description:添加数据函数
			*/
			
			
			function onaddclicked(){
				var submitData = addwindow.getFields();
				requestAjax('<%=basePath%>pfs/pfsCustInfo_queryCustInfo', submitData, 
					function(sRet){
				    for(var i=0;i<sRet.field1.length;i++){
				    	if(sRet.field1[i].ncusttype_e == submitData.ncusttype_e&&sRet.field1[i].ocusttype_e == submitData.ocusttype_e){
				    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','添加客户类型重复，请重新添加');
				    		return;
				    	}	    				    	
				    }
				    if(submitData.ncusttype_e == submitData.ocusttype_e){
				    	Ext.MessageBox.alert('<s:text name="common.info.title"/>','新客户类型与原客户类型重复，请重新添加');
				    		return;
				    }
				    requestAjax('<%=basePath%>pfs/pfsCustInfo_addCustInfo', submitData,
								function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
									addwindow.close();
				    });
				});	
				
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
