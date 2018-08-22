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
    
    <title>排队机信息配置</title>
    
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
		
		var qmNumStore=new Ext.data.SimpleStore({ 
			data:[["1","1"],["2","2"],["3","3"],["4","4"],["5","5"],["6","6"],["7","7"],["8","8"],["9","9"],["10","10"]],
			fields : ['key','value']
		});
		
		var qmStatusStore=new Ext.data.SimpleStore({ 
			data:[["1","1-可用"],["0","0-不可用"]],
			fields : ['key','value']
		});
		
		var labelTextFontStore=new Ext.data.ArrayStore({ 
			data:[
				["新宋体","新宋体"],
				["仿宋","仿宋"],
				["幼圆","幼圆"],
				["黑体","黑体"],
				["宋体","宋体"],
				["隶书","隶书"],
				["楷体","楷体"],
				["微软雅黑","微软雅黑"],
				["华文新魏","华文新魏"],
				["华文细黑","华文细黑"],
				["华文宋体","华文宋体"],
				["华文楷体","华文楷体"],
				["华文行楷","华文行楷"],
				["华文仿宋","华文仿宋"],
				["华文彩云","华文彩云"],
				["方正姚体","方正姚体"],
				["宋体-方正超大字符集","宋体-方正超大字符集"]
			],
			fields : ['key','value']
		});
		
		var labelTextSizeStore=new Ext.data.ArrayStore({ 
			data:[
				["44","初号"],
				["36","小初"],
				["26","一号"],
				["24","小一"],
				["22","二号"],
				["18","小二"],
				["16","三号"],
				["15","小三"],
				["14","四号"],
				["12","小四"],
				["10.5","五号"],
				["9","小五"],
				["7.5","六号"],
				["6.5","小六"],
				["5.5","七号"],
				["5","八号"],
				["8","8"],
				["9","9"],
				["10","10"],
				["11","11"],
				["12","12"],
				["14","14"],
				["16","16"],
				["18","18"],
				["20","20"],
				["22","22"],
				["24","24"],
				["26","26"],
				["28","28"],
				["36","36"],
				["48","48"],
				["72","72"],
				["73","73"],
				["74","74"],
				["75","75"],
				["76","76"],
				["77","77"],
				["78","78"],
				["79","79"],
				["80","80"],
				["81","81"],
				["82","82"],
				["83","83"],
				["84","84"],
				["85","85"],
				["86","86"],
				["87","87"],
				["88","88"],
				["89","89"],
				["90","90"],
				["91","91"],
				["92","92"],
				["93","93"],
				["94","94"],
				["95","95"],
				["96","96"]
			],
			fields : ['key','value']
		});
		
		var labelTextStyleStore=new Ext.data.ArrayStore({ 
			data:[
				["0","0-常规"],
				["1","1-粗体"],
				["2","2-斜体"]
			],
			fields : ['key','value']
		});
		var systemParamStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>/confManager/systemParameter_querySystemParameter',
			root   : 'field1',
			fields:['parameter_id','parameter_name','branch','default_flag','parameter_flag']
		});
		/*
			,
			listeners : {  
				load : function(store,records){
					var a = store.data.items;
					this.removeAll();
					var rec = new (store.recordType)();
					//写入数据
					rec.set('custtype_i', '默认');
					rec.set('custtype_e', '默认');
					store.add(rec);
					store.add(a);
				}
			}
		*/
/**
 * @Title: loadPage 
 * @Description: 显示排队机信息界面
 */	
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '排队机信息配置', 120, 0,
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
				'<%=basePath%>/confManager/qm_queryQMInfoPage',
				[
				 	'qm_num',
				 	'qm_name',
				 	'branch',
				 	'qm_ip',
				 	'status',
				 	'qm_param_id',
				 	'qm_oid',
				 	'parameter_id',
				 	'propertynum',
				 	'deviceadm_num'
				 ],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'排队机编号',dataIndex:'qm_num',width:150},
				{header:'排队机名称',dataIndex:'qm_name',width:150},
				{header:'机构号',dataIndex:'branch',width:150},
				{header:'资产编码',dataIndex:'propertynum',width:150},
				{header:'虚拟柜员号',dataIndex:'deviceadm_num',width:150},
				{header:'排队机IP',dataIndex:'qm_ip',width:230},
				{header:'设备唯一标识',dataIndex:'qm_oid',width:250},
				{header:'排队机状态',dataIndex:'status',width:150,renderer:function(value){return value=='1'?'可用':'不可用'}},
				{header:'排队机参数',dataIndex:'qm_param_id',width:200},
				{header:'排队机界面',dataIndex:'parameter_id',width:363,hidden:<%=!machine_view_flag%>,renderer:function(value){
          		 	return getDictValue(value,systemParamStore,'parameter_id','parameter_name');//翻译字段方法
       			}}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加排队机信息', 300, 330, 140, 1,
				[
				{colIndex:0, field:{xtype : 'combo', name:'qm_num', 	fieldLabel:'排队机编号',editable:false, 	allowBlank : false, store:qmNumStore, 	displayField:'value', valueField:'key',listeners:{
		            select:function(combo,record,index){
		                //对应的处理函数
		                Ext.getCmp('qm_name_add').setValue(record.get('value')+'号排队机');
		            }
		        }}},
				{colIndex:0, field:{xtype : 'textfield', name:'qm_name', 	fieldLabel:'排队机名称',id:'qm_name_add', 	allowBlank : false, blankText:'请输入排队机名称',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{layout:'column',fieldLabel:'界面编号',items:[{xtype : 'textfield',editable:false, name:'parameter_id',id:'parameter_id_add', 	fieldLabel:'界面编号',	allowBlank : true,width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){systemParam();}}}]}},
				{colIndex:0, field:{layout:'column',fieldLabel:'排队机参数',items:[{xtype : 'textfield',readOnly:true,editable:false, name:'qm_param_id',id:'qm_param_id_add', 	fieldLabel:'排队机参数',	allowBlank : true,width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){paramDetails();}}}]}},
				{colIndex:0, field:{xtype : 'textfield', name:'deviceadm_num', 	fieldLabel:'虚拟柜员号',id:'deviceadm_num_add', 	allowBlank : false , blankText:'请输入虚拟柜员号',maxLength :10,	maxLengthText : '长度不能大于10位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'propertynum', 	fieldLabel:'资产编号',id:'propertynum_add', 	allowBlank : false , blankText:'请输入资产编号',maxLength :10,	maxLengthText : '长度不能大于10位'}},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,  name:'status', 	boxLabel:'启用',checked : true}}
				//{colIndex:0, field:{xtype : 'textfield', name:'qm_param_id', 	fieldLabel:'排队机参数',	allowBlank : false,blankText:'请输入排队机参数ID'}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left',80
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑排队机信息', 300, 330, 140, 1,
			   [
				{colIndex:0, field:{xtype : 'textfield', id:'branch', name:'branch', 	fieldLabel:'机构号',readOnly:true,hidden:true}},
				{colIndex:0, field:{xtype : 'textfield', id:'qm_ip', name:'qm_ip', 	fieldLabel:'排队机IP',readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', id:'qm_num_old', 	fieldLabel:'原排队机编号',readOnly:true,hidden:true}},
				{colIndex:0, field:{xtype : 'combo',	fieldLabel:'排队机编号', readOnly:true,editable:false,name:'qm_num', 	allowBlank : false, store:qmNumStore, 	displayField:'value', valueField:'key',listeners:{
		            select:function(combo,record,index){
		                //对应的处理函数
		                Ext.getCmp('qm_name_edit').setValue(record.get('value')+'号排队机');
		            }
		        }}},
				{colIndex:0, field:{xtype : 'textfield', name:'qm_name', value:'',	fieldLabel:'排队机名称', id:'qm_name_edit',	allowBlank : false, blankText:'请输入排队机名称',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{layout:'column',fieldLabel:'界面编号',items:[{xtype : 'textfield',editable:false, name:'parameter_id',id:'parameter_id_edit', 	fieldLabel:'界面编号',	allowBlank : true,width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){systemParam();}}}]}},
				{colIndex:0, field:{layout:'column',fieldLabel:'排队机参数',items:[{xtype : 'textfield',editable:false, name:'qm_param_id',id:'qm_param_id_edit', 	fieldLabel:'排队机参数',	allowBlank : true,width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){paramDetails();}}}]}},
				{colIndex:0, field:{xtype : 'textfield', name:'deviceadm_num', 	fieldLabel:'虚拟柜员号',id:'deviceadm_num_edit', 	allowBlank : false , blankText:'请输入虚拟柜员号',maxLength :10,	maxLengthText : '长度不能大于10位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'propertynum', 	fieldLabel:'资产编号',id:'propertynum_edit', 	allowBlank : false , blankText:'请输入资产编号',maxLength :10,	maxLengthText : '长度不能大于10位'}},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,  name:'status', 	boxLabel:'启用'}}
					],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left',80
			);
			
			/**
			 * 排队机参数弹出窗
			 */
			function paramDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['qm_param_id','default_flag','branch','bs_servicetime_status','ticket_style_id','theme_id','theme_status',
							 'timeing_shutdown','cfg_pwd','show_clock','call_voice','isprecontract',//'doublescreen_id',
							 'show_bs_waitnum','label_content','label_text_font','label_text_style','label_text_size','label_text_color']
			    });
			    var detailData = [
					{header:'排队机参数编号',dataIndex:'qm_param_id',width:100},
				{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
					if(value!=""){
						return value=='1'?'默认':'非默认';
					}else{
						return value;
					}
				}},
				{header:'机构号',dataIndex:'branch',width:100},
				//{header:'二层屏编号',dataIndex:'doublescreen_id',width:100},
				{header:'小票模板编号',dataIndex:'ticket_style_id',width:100},
				{header:'主题编号',dataIndex:'theme_id',width:100},
				//{header:'是否启用主题',dataIndex:'theme_status',width:100},
				{header:'定时关机时间',dataIndex:'timeing_shutdown',width:100},
				{header:'排队机设置密码',dataIndex:'cfg_pwd',width:100},
				{header:'是否启用业务服务时间',dataIndex:'bs_servicetime_status',width:150,renderer:function(value){return value=='1'?'是':'否'}},
				{header:'是否显示时钟',dataIndex:'show_clock',width:100,renderer:function(value){return value=='1'?'是':'否'}},
				{header:'语音呼叫设置',dataIndex:'call_voice',width:150},
				//{header:'是否短信通知客户',dataIndex:'sms_customer',width:150,renderer:function(value){return value=='1'?'是':'否'}},
				//{header:'是否支持预约',dataIndex:'isprecontract',width:100},
				{header:'是否显示业务等待人数',dataIndex:'show_bs_waitnum',width:200,renderer:function(value){return value=='1'?'是':'否'}},
				{header:'标签内容',dataIndex:'label_content',width:200},
				{header:'标签字体大小',dataIndex:'label_text_size',width:100},
				{header:'标签字体颜色',dataIndex:'label_text_color',width:100},
				{header:'标签字体名称',dataIndex:'label_text_font',width:100,renderer:function(value){
					value = value.toString();
					return getDictValue(value,labelTextFontStore,'key','value');//翻译字段方法
       			 }},
				{header:'标签字体类型',dataIndex:'label_text_style',width:100,renderer:function(value){
					value = value.toString();
					return getDictValue(value,labelTextStyleStore,'key','value');//翻译字段方法
       			 }}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('paramDetailsWindow','排队机参数列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			title:'排队机参数信息',
			   			layout:'column',
			   			items:[{
			       			columnWidth:1,
			       			items:[{
			       				xtype:'grid',
			       				id:'paramGrid',
			       				store:detailStore,
			       				cm:detailColModel,
			       				height:250,
			       				iconCls:'icon-grid',
			       				stripeRows : true,
			       				doLayout:function(){
			       					this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true));
			       				}
			       			}]
			   			}]
			   		}
			    }],[],'left',140);
			    
			    Ext.getCmp('paramGrid').on('rowdblclick',function(grid, rowindex,e){
		  			var record = grid.getStore().getAt(rowindex);
		  			detailsWindow.close();
		  			if(record.data['qm_param_id']!='默认'){
		  				Ext.getCmp('qm_param_id_add').setValue(record.data['qm_param_id']);
		    			Ext.getCmp('qm_param_id_edit').setValue(record.data['qm_param_id']);
		  			}else{
		  				Ext.getCmp('qm_param_id_add').setValue('');
			  			Ext.getCmp('qm_param_id_edit').setValue('');
		  			}
			  	});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '4';
			    requestAjax('<%=basePath%>/confManager/machineParam_queryQMParam.action',submitDetail, function(sRet){
			    	sRet.field1.unshift({'qm_param_id':'默认'});
			    	detailStore.loadData(sRet.field1);
				});
			}
			function systemParam(){
				var systemParamData=[
					{header: '参数编号', width: 80, dataIndex: 'parameter_flag', hidden:true},
				    {header: '参数编号', width: 80, dataIndex: 'parameter_id'},
				    {header: '参数名称', width: 150, dataIndex: 'parameter_name', sortable:true},
				    {header: '参数来源', width: 100, dataIndex: 'branch', sortable:true},
				    {header: '是否默认', width: 100, dataIndex: 'default_flag', sortable:true,renderer:function(value){
							return value=='1'?'默认':'非默认';
						}
				    }
				];
				var systemParamColModel=new Ext.grid.ColumnModel(systemParamData);
				var systemParamWindow = new SelfFormWindowSetWidth('systemParamWindow','界面参数列表',600, 355, 555, 1, [555],[{
				   	colIndex:0,
				   	field:{
				   			xtype:'fieldset',
				   			title:'排队机参数信息',
				   			layout:'column',
				   			items:[{
				       			columnWidth:1,
				       			items:[{
				       				xtype:'grid',
				       				id:'systemParamGrid',
				       				store:systemParamStore,
				       				cm:systemParamColModel,
				       				height:250,
				       				iconCls:'icon-grid',
				       				stripeRows : true,
				       				doLayout:function(){
				       					this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true));
				       				}
				       			}]
				   			}]
				   		}
				    }],[],'left',140);
				    
				    Ext.getCmp('systemParamGrid').on('rowdblclick',function(grid, rowindex,e){
			  			var record = grid.getStore().getAt(rowindex);
			  			systemParamWindow.close();
			  			if(record.data['parameter_id']!='默认'){
			  				Ext.getCmp('parameter_id_add').setValue(record.data['parameter_id']);
			    			Ext.getCmp('parameter_id_edit').setValue(record.data['parameter_id']);
			  			}else{
			  				Ext.getCmp('parameter_id_add').setValue('');
				  			Ext.getCmp('parameter_id_edit').setValue('');
			  			}
				  	});
				    systemParamWindow.open();
			}
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
						//无法修改非本行机构
						var branch = '<%=branch%>';
					    for(var i=0;i<records.length;i++){
					    	if(records[i].data.branch!=branch){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的配置');
					    		return;
					    	}
					    }
						if(!isEdit){
							break;
						}
						editwindow.open();
						editwindow.updateFields(records[0]);
						Ext.getCmp('qm_num_old').setValue(records[0].data['qm_num']);
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['qm_num','branch']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							return;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/confManager/qm_delQMInfo',submitdata,function(sRet){
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
			 * @createCheckBoxValue
			 * @Description:判断checkbox数据
			 */
            function createCheckBoxValue(check,data){
            	if(data[check]==undefined){
            		data[check] = 'off';
				}
            }
			
			/**
			 * @Title:onButtonClicked
			 * @Description:点击修改时的操作
			 */
			function oneditclicked(){
				var submitData = editwindow.getFields();
				createCheckBoxValue('status',submitData);
				requestAjax('<%=basePath%>/confManager/qm_editQMInfo',submitData,function(sRet){
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
				createCheckBoxValue('status',submitData);
				requestAjax('<%=basePath%>/confManager/qm_addQMInfo', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
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
      		var branch = '<%=branch%>';
      		systemParamStore.load({params:{'branch':branch,'parameter_flag':'01'}});
      		
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
	<div id="paramDetailsWindow"></div>
	<div id="systemParamWindow"></div>
	<div id="editWindow"></div>
  </body>
</html>
