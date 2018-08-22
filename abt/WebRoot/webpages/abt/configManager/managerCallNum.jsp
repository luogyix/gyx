<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String brno = user.getUnitid();
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>大堂经理取号配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		/**
		 * 翻译字段用store
		 */
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
		  
		var QMInfoStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>/confManager/qm_queryQMInfoSmall.action',
			autoLoad:true,
			root   : 'field1',
			fields:['qm_num','qm_name']
		});
		
		var custTypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>/confManager/custType_queryCustTypeSmall.action',
			autoLoad:true,
			root   : 'field1',
			fields : ['custtype_i','custtype_name'],
			listeners : {
				load : function(store,records){
					var temp = store.data.items;
					for(var i=0;i<temp.length;i++){
						temp[i].data['custtype_name'] = temp[i].data['custtype_i'] + '-' + temp[i].data['custtype_name'];
					}
					this.removeAll();
					store.add(temp);
				}
			}
		});
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示窗口维护界面
 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '大堂经理取号配置', 120, 0,
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
				'<%=basePath%>confManager/managerCallNum_queryQueuePage',
				['branch','queuetype_id','qm_num','bs_id','custtype_i'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'排队机',dataIndex:'qm_num',width:100,renderer:function(value){
          		 	return getDictValue(value,QMInfoStore,'qm_num','qm_name');//翻译字段方法
       			 }},
       			{header:'队列编号',dataIndex:'queuetype_id',width:100,renderer:function(value){
          		 	return getDictValue(value,queuetypeStore,'queuetype_id','queuetype_name');//翻译字段方法
       			 }},
				//{header:'业务编号',dataIndex:'bs_id',width:100,renderer:function(value){
          		// 	return getDictValue(value,businessStore,'bs_id','bs_name_ch');//翻译字段方法
       			// }},
				{header:'客户类型',dataIndex:'custtype_i',width:(window.screen.width)-576,renderer:function(value){
          		 	return getDictValue(value,custTypeStore,'custtype_i','custtype_name');//翻译字段方法
       			 }
				}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加窗口信息', 300, 180, 140, 1,
				[
				{colIndex:0, field:{layout:'column',fieldLabel:'排队机编号<font style="color:red">*</font>',items:[{xtype : 'textfield', name:'qm_num',id:'qm_num_add',readOnly:true, 	fieldLabel:'排队机编号',	allowBlank : false,blankText:'请输入排队机编号',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){queueMachineDetails();}}}]}},
				{colIndex:0, field:{layout:'column',fieldLabel:'队列编号<font style="color:red">*</font>',items:[{xtype : 'textfield', name:'queuetype_id',id:'queuetype_id_add',readOnly:true, fieldLabel:'队列编号',blankText:'请选择队列类型编号',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){queuetypeDetails();}}}]}},
				//{colIndex:0, field:{layout:'column',fieldLabel:'业务编号<font style="color:red">*</font>',items:[{xtype : 'textfield', name:'bs_id',id:'bs_id_add',readOnly:true, 	fieldLabel:'业务编号',	allowBlank : false,blankText:'请选择业务编号',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){businessDetails();}}}]}},
				{colIndex:0, field:{layout:'column',fieldLabel:'内部客户类型<font style="color:red">*</font>',items:[{xtype : 'textfield', name:'custtype_i',id:'custtype_i_add',readOnly:true, 	fieldLabel:'内部客户类型',	allowBlank : false,blankText:'请选择客户类型',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){custtypeDetails();}}}]}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left','100'
			);
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑窗口信息', 300, 220, 140, 1,
			   [
				{colIndex:0, field:{xtype : 'hidden', name:'branch', 	fieldLabel:'机构号'}},
				{colIndex:0, field:{xtype : 'hidden', name:'qm_num_old',id:'qm_num_old', 	fieldLabel:'老排队机编号'}},
				{colIndex:0, field:{layout:'column',fieldLabel:'排队机编号<font style="color:red">*</font>',items:[{xtype : 'textfield', name:'qm_num',id:'qm_num_edit',readOnly:true, 	fieldLabel:'排队机编号',	allowBlank : false,blankText:'请输入排队机编号',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){queueMachineDetails();}}}]}},
				{colIndex:0, field:{layout:'column',fieldLabel:'队列编号<font style="color:red">*</font>',items:[{xtype : 'textfield', name:'queuetype_id',id:'queuetype_id_edit',readOnly:true,fieldLabel:'队列编号',	allowBlank : false,blankText:'请选择队列类型编号',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){queuetypeDetails();}}}]}},
				//{colIndex:0, field:{layout:'column',fieldLabel:'业务编号<font style="color:red">*</font>',items:[{xtype : 'textfield', name:'bs_id',id:'bs_id_edit',readOnly:true, 	fieldLabel:'业务编号',	allowBlank : false,blankText:'请选择业务编号',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){businessDetails();}}}]}},
				{colIndex:0, field:{layout:'column',fieldLabel:'内部客户类型<font style="color:red">*</font>',items:[{xtype : 'textfield', name:'custtype_i',id:'custtype_i_edit',readOnly:true, 	fieldLabel:'内部客户类型',	allowBlank : false,blankText:'请选择客户类型',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){custtypeDetails();}}}]}}
				],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left','100'
			);
			
			//排队机编号
            function queueMachineDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['qm_num','qm_name','branch','qm_ip','status','qm_param_id']
			    });
			    var detailData = [
					{header:'排队机编号',dataIndex:'qm_num',width:100},
					{header:'排队机名称',dataIndex:'qm_name',width:100},
					{header:'机构号',dataIndex:'branch',width:100},
					{header:'排队机IP',dataIndex:'qm_ip',width:180},
					{header:'排队机状态',dataIndex:'status',width:100,renderer:function(value){return value=='1'?'1-可用':'0-不可用'}},
					{header:'排队机参数',dataIndex:'qm_param_id',width:100}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('qmDetailsWindow','排队机编号列表',600, 355, 555, 1, [555],[{
		    		colIndex:0,
		    		field:{
		    			xtype:'fieldset',
		    			title:'排队机信息',
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
	    			Ext.getCmp('qm_num_add').setValue(record.data['qm_num']);
	    			Ext.getCmp('qm_num_edit').setValue(record.data['qm_num']);
	    		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    requestAjax('<%=basePath%>/confManager/qm_queryQMInfo',submitDetail, function(sRet){
				      detailStore.loadData(sRet.field1);
				});
			}
			
            function queuetypeDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['branch','queuetype_id','queuetype_name','queuetype_prefix','queuetype_prefix_num','queuetype_level','status']
			    });
			    var detailData = [
					{header:'机构号',dataIndex:'branch',width:100},
					{header:'队列编号',dataIndex:'queuetype_id',width:100},
					{header:'队列名称',dataIndex:'queuetype_name',width:100},
					{header:'队列前缀字母',dataIndex:'queuetype_prefix',width:100},
					{header:'队列前缀数字',dataIndex:'queuetype_prefix_num',width:100},
					{header:'队列优先级',dataIndex:'queuetype_level',width:100},
					{header:'队列状态',dataIndex:'status',width:100}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('queuetypeDetailsWindow','队列编号列表',600, 355, 555, 1, [555],[{
		    		colIndex:0,
		    		field:{
		    			xtype:'fieldset',
		    			title:'队列信息',
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
	    			Ext.getCmp('queuetype_id_add').setValue(record.data['queuetype_id']);
	    			Ext.getCmp('queuetype_id_edit').setValue(record.data['queuetype_id']);
	    		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '4';
			    requestAjax('<%=basePath%>/confManager/queuetype_queryQueue',submitDetail, function(sRet){
				      detailStore.loadData(sRet.field1);
				});
			}
            
            function businessDetails(){
            	var bsTypeStore=new Ext.data.SimpleStore({ 
    				data:[["0-个人业务","0"],["1-对公业务","1"]],
    				fields : ['key','value']
	    		});
	    		var stateStore=new Ext.data.SimpleStore({ 
	    				data:[["1-可用","1"],["0-不可用","0"]],
	    				fields : ['key','value']
	    		});
	    		
	    		var cardFlagStore=new Ext.data.SimpleStore({ 
	    				data:[["0-不刷","0"],["1-可刷可不刷","1"],["2-必刷","2"]],
	    				fields : ['key','value']
	    		});
	    		var branchLevelStore=new Ext.data.SimpleStore({ 
	    				data:[["1-总行","1"],["2-分行","2"],["3-支行","3"],["4-网点","4"]],
	    				fields : ['key','value']
	    		});
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['bs_id','branch','bs_name_ch','bs_name_en','bs_type','branch_level','card_flag','status','bs_signstatus','note']
			    });
			    var detailData = [
					{header:'业务编号',     dataIndex:'bs_id',width:150},
					{header:'机构号',     dataIndex:'branch',width:150},
					{header:'业务名称(中文)',dataIndex:'bs_name_ch',width:150},
					{header:'业务名称(英文)',dataIndex:'bs_name_en',width:150},
					{header:'所属业务类别',dataIndex:'bs_type',width:150,renderer:function(value){
						value = value.toString();
	          		 	return getDictValue(value,bsTypeStore,'value','key');//翻译字段方法
	       			 }},
					{header:'机构类型',dataIndex:'branch_level',width:150,renderer:function(value){
						value = value.toString();
	          		 	return getDictValue(value,branchLevelStore,'value','key');//翻译字段方法
	       			 }},
					{header:'刷卡标志',dataIndex:'card_flag',width:150,renderer:function(value){
						value = value.toString();
	          		 	return getDictValue(value,cardFlagStore,'value','key');//翻译字段方法
	       			 }},
					{header:'业务状态',dataIndex:'status',width:150,renderer:function(value){
						value = value.toString();
	          		 	return getDictValue(value,stateStore,'value','key');//翻译字段方法
	       			 }},
					{header:'业务签到状态',dataIndex:'bs_signstatus',width:150,renderer:function(value){
						value = value.toString();
	          		 	return getDictValue(value,stateStore,'value','key');//翻译字段方法
	       			 }},
					{header:'备注',dataIndex:'note',width:150}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('businessDetailsWindow','业务编号列表',600, 355, 555, 1, [555],[{
		    		colIndex:0,
		    		field:{
		    			xtype:'fieldset',
		    			title:'业务信息',
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
	    			Ext.getCmp('bs_id_add').setValue(record.data['bs_id']);
	    			Ext.getCmp('bs_id_edit').setValue(record.data['bs_id']);
	    		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '4';
			    requestAjax('<%=basePath%>/confManager/business_queryBusiness',submitDetail, function(sRet){
				      detailStore.loadData(sRet.field1);
				});
			}
            
            function custtypeDetails(){
        		var isVipStore=new Ext.data.SimpleStore({ 
        			data:[["1","是"],["0","否"]],
        			fields : ['key','value']
        		});
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['custtype_i','custtype_e','custtype_name','isvip']
			    });
			    var detailData = [
					{header:'内部客户类型',dataIndex:'custtype_i',width:100},
					{header:'外部客户类型',dataIndex:'custtype_e',width:100},
					{header:'客户类型名称',dataIndex:'custtype_name',width:100},
					{header:'是否VIP',dataIndex:'isvip',width:100,renderer:function(value){if(value=='1'){return '是'}else{return '否'}}}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('custtypeDetailsWindow','客户类型列表',600, 355, 555, 1, [555],[{
		    		colIndex:0,
		    		field:{
		    			xtype:'fieldset',
		    			title:'客户类型信息',
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
	    			Ext.getCmp('custtype_i_add').setValue(record.data['custtype_i']);
	    			Ext.getCmp('custtype_i_edit').setValue(record.data['custtype_i']);
	    		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    requestAjax('<%=basePath%>/confManager/custType_queryCustType',submitDetail, function(sRet){
				      detailStore.loadData(sRet.field1);
				});
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
					    for(var i=0;i<records.length;i++){
					    	if(records[i].data.branch!=<%="'"+brno+"'"%>){
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
					    var submitdata = pagequeryObj.getSelectedObjects(['branch','qm_num']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						 //无法删除非本行机构
					    for(var i=0;i<submitdata.length;i++){
					    	if(submitdata[i].branch!=<%="'"+brno+"'"%>){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法删除非本机构的配置');
					    		return;
					    	}
					    }
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/managerCallNum_delQueue',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
									window.parent.queryMessage();
								});
							}
						});
					    break;
				}
			};
			/**
			 * @Title:onButtonClicked
			 * @Description:点击修改时的操作
			 */
			function oneditclicked(){
				var submitData = editwindow.getFields();
				createCheckBoxValue('status',submitData);
				requestAjax('<%=basePath%>confManager/managerCallNum_editQueue',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
					window.parent.queryMessage();
				});
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
			* @Title:onaddclicked
			* @Description:添加数据函数
			*/
			function onaddclicked(){
				var submitData = addwindow.getFields();
				createCheckBoxValue('status',submitData);
				requestAjax('<%=basePath%>confManager/managerCallNum_addQueue', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
					window.parent.queryMessage();
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
	<div id="qmDetailsWindow"></div>
	<div id="custtypeDetailsWindow"></div>
	<div id="businessDetailsWindow"></div>
	<div id="queuetypeDetailsWindow"></div>
  </body>
</html>
