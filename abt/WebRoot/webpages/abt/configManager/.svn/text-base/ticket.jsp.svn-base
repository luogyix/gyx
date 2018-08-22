<%@page
	import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%
//获取项目名称
String path = request.getContextPath();
//ip+port+项目名
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//获取当前用户信息
User user = (User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
//根据当前用户获取机构信息
String brno = user.getUnitid();
%>


<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>小票模板配置</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="selfcss/common.css" />
<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs/ext-all.js"></script>
<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
<script type="text/javascript">
		var cursor = 0;//此变量用于保存小票模板中的光标索引
		var pagereturn=${actionresult};//获得页面向select域中设置的值
		//指定弹出窗口是以下拉框或输入框为内容，这是第一步设置，下面还有第二部设置。
		var conditionPanel = null;
		
		/**
		*	设置编辑小票模版 类别选择 按钮 弹出窗口中的数据
		*/
		var bcTags = [
		              {"style_content":"排队号","tagValue":"@排队号@"},
		              {"style_content":"等待人数","tagValue":"@等待人数@"},
		              {"style_content":"业务类别","tagValue":"@业务类别@"},
		              {"style_content":"等待区域","tagValue":"@等待区域@"},
		              {"style_content":"网点名称","tagValue":"@网点名称@"},
		              {"style_content":"取号时间","tagValue":"@时间@"},
		              {"style_content":"叫号时间","tagValue":"@虚拟叫号时间@"},
		              {"style_content":"提示信息","tagValue":"@提示信息@"}
		             ];
		/**
		*	设置编辑小票模版 字体按钮 弹出窗口中的数据
		*/
		var fontTags = [
		           	{"style_content":"打开粗体模式","tagValue":"<FONT:BOLD-ON>"},
		           	{"style_content":"关闭粗体模式","tagValue":"<FONT:BOLD-OFF>"},
		           	{"style_content":"打开倾斜模式","tagValue":"<FONT:SLANT-ON>"},
		           	{"style_content":"关闭倾斜模式","tagValue":"<FONT:SLANT-OFF>"},
		           	{"style_content":"正常大小字体","tagValue":"<FONT:SIZE-NORMAL>"},
		           	{"style_content":"大字体","tagValue":"<FONT:SIZE-LARGE>"},
		           	{"style_content":"小字体","tagValue":"<FONT:SIZE-SMALL>"}
		           ];
		/**
		*	按钮对齐方式 弹窗中的数据
		*/
		var textTags = [
		               {"style_content":"左对齐","tagValue":"<TEXT:ALIGN-LEFT>"},
		               {"style_content":"居中对齐","tagValue":"<TEXT:ALIGN-CENTER>"},
		               {"style_content":"右对齐","tagValue":"<TEXT:ALIGN-RIGHT>"},
		               {"style_content":"打开自动换行模式","tagValue":"<TEXT:AUTOWARP-ON>"},
		               {"style_content":"关闭自动换行模式","tagValue":"<TEXT:AUTOWARP-OFF>"}
		               ];
		var barcodeTags = [
		                  	{"style_content":"打开条形码","tagValue":"<BARCODE:ON>"},
		                  	{"style_content":"关闭条形码","tagValue":"<BARCODE:OFF>"} 
		                  ];
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示小票模板信息界面
 */		//指定弹出窗口是以下拉框或输入框为内容，这是第二部设置，这就完成了
 		var priv=pagereturn.field2;
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '小票模板配置', 120, 0,
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
				'<%=basePath%>confManager/ticket_queryTicketPage',
				['branch','ticket_style_id','default_flag','style_content'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'小票模板编号',dataIndex:'ticket_style_id',width:100},
				{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
					return value=='1'?'0-默认':'1-非默认'}},
				{header:'模板内容',dataIndex:'style_content',width:500}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			//当选择"添加"时，弹出的窗口			//730=>设置弹出窗口的长度，400=>高，160=>样式，3=>窗口中下拉框或输入狂的列数
			addwindow = new SelfFormWindow('addWindow', '添加小票模板信息', 730, 420, 320, 2,
				/*
					设置弹窗中的内容：
						colIndex：设置此组件位于弹窗中的第几行
						field：
								xtype=>子组件的类型：textfield=>输入框，column=>纯文本，button=>按钮名称，
								items=> 为当前组件设置子组件，设置方式照 field
								items:
										xtype=>子组件的类型，text=>按钮名称，listener=>点击按钮后执行的操作，这里是不同的按钮带着不同的参数调用同一个函数
				*/
				[
					{colIndex:0, field:{xtype : 'textarea', name:'style_content',id:'style_content_add', 	fieldLabel:'模板内容',height:240,allowBlank : false, blankText:'请输入模板内容'}},
					{colIndex:1, field:{layout:'column',fieldLabel:'设置类别',items:[{xtype:'button',text:'选择',width:160,listeners:{"click":function(){category('style_content_add',1)}}}]}},
					{colIndex:1, field:{layout:'column',fieldLabel:'设置字体',items:[{xtype:'button',text:'选择',width:160,listeners:{"click":function(){category('style_content_add',2)}}}]}},
					{colIndex:1, field:{layout:'column',fieldLabel:'设置对齐方式',items:[{xtype:'button',text:'选择',width:160,listeners:{"click":function(){category('style_content_add',3)}}}]}},
					{colIndex:1, field:{layout:'column',fieldLabel:'条形码开关',items:[{xtype:'button',text:'选择',width:160,listeners:{"click":function(){category('style_content_add',4)}}}]}},
					{colIndex:1, field:{layout:'column',fieldLabel:'回车换行',items:[{xtype:'button',text:'回车换行',width:160,listeners:{"click":function(){enter('style_content_add')}}}]}},
					{colIndex:0, hiddenLabel:true,field:{width:240,html:'<font color="red">注:如不做选择，将采用系统默认设置</font>'}},
					{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'default_flag',id:'default_flag_add', boxLabel:'设为默认',hiddenName:'default_flag'}}
				],
				//弹窗中的按钮
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : ticketDetails},
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑小票模板信息', 730, 420, 320, 2,
				[	
					{colIndex:0, field:{xtype : 'textfield', name:'branch', 	fieldLabel:'机构号',id:'branch', readOnly:true,hidden:true}},
					{colIndex:0, field:{xtype : 'textfield', name:'ticket_style_id',  	fieldLabel:'小票模板编号',id:'ticket_style_id', readOnly:true,hidden:true}},
					{colIndex:0, field:{xtype : 'textarea', name:'style_content',id:'style_content_edit', 	fieldLabel:'模板内容',height:240,allowBlank : false, blankText:'请输入模板内容'}},
					{colIndex:1, field:{layout:'column',fieldLabel:'设置类别',items:[{xtype:'button',text:'选择',width:160,listeners:{"click":function(){category('style_content_edit',1);}}}]}},
					{colIndex:1, field:{layout:'column',fieldLabel:'设置字体',items:[{xtype:'button',text:'选择',width:160,listeners:{"click":function(){category('style_content_edit',2);}}}]}},
					{colIndex:1, field:{layout:'column',fieldLabel:'设置对齐方式',items:[{xtype:'button',text:'选择',width:160,listeners:{"click":function(){category('style_content_edit',3)}}}]}},
					{colIndex:1, field:{layout:'column',fieldLabel:'条形码开关',items:[{xtype:'button',text:'选择',width:160,listeners:{"click":function(){category('style_content_edit',4)}}}]}},
					{colIndex:1, field:{layout:'column',fieldLabel:'回车换行',items:[{xtype:'button',text:'回车换行',width:160,listeners:{"click":function(){enter('style_content_edit')}}}]}},
					{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'default_flag',id:'default_flag_edit', boxLabel:'设为默认',hiddenName:'default_flag'}},
					{colIndex:0, hiddenLabel:true,field:{width:240,html:'<font color="red">注:如不做选择，将采用系统默认设置</font>'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : ticketDetails},
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				]
			);
			
			/**
			*	编辑小票模版的 回车换行按钮
			*/
			function enter(style_content_id){
				var myField = document.getElementById(style_content_id);
  				if(document.selection){//判断是否为IE浏览器
  					//激活当前对象
  					myField.focus();
  					//获取光标
  					sel = document.selection.createRange();
  					//在光标处设置值
  					sel.text = "<CTRL:CRLF>";
  				//非IE
  				}else if(myField.selectionStart||myField.selectionStart == "0"){
  					//得到光标的前一个字符的位置
  					var startPos = myField.selectionStart;
  					//得到光标后第一个字符的位置
  					var endPos = myField.selectionEnd;
  					//滚动条高度
  					var restoreTop = myField.scrollTop;
  					//插入数据
  					Ext.getCmp(style_content_id).setValue(myField.value.substring(0,startPos) + "<CTRL:CRLF>" + myField.value.substring(endPos,myField.value.length));
	  				//如果滚动条高度大于0
	  				if(restoreTop>0){
	  					//返回
	  					myField.scrollTop = restoreTop;
	  				}
	  				myField.focus();
	  				myField.selectionStart = startPos + "<CTRL:CRLF>".length;
	  				myField.selectionEnd = startPos + "<CTRL:CRLF>".length;
  				}else{
  					myField.value += "<CTRL:CRLF>";
  					myField.focus();
  				}
			}
			
			/**
			*添加时的小票模板输入框的离焦事件
			*	离焦时立即保存前一刻的光标索引
			*/
			var fieldChangeAdd = Ext.get("style_content_add");
			fieldChangeAdd.on({
				blur:function(){
					//根据id获得输入框
					var myField = document.getElementById("style_content_add");
					//获得输入框焦点
	  				myField.focus();
					//创建文档选择对象
	  				var textObj = document.selection.createRange();
					//创建输入框文本对象
  					var inputObj = myField.createTextRange(),inputObjCopy = inputObj.duplicate();
  					inputObj.moveToBookmark(textObj.getBookmark());
  					inputObjCopy.setEndPoint('EndToStart',re);
  					//把光标索引赋给一个全局变量
  					cursor = inputObjCopy.text.length;
				}
			})
			
			/**
			*修改时的小票模板输入框的离焦事件
			*	离焦时立即保存前一刻的光标索引
			*/
			var fieldChangeEdit = Ext.get("style_content_edit");
			fieldChangeEdit.on({
				blur:function(){
					//根据id获得输入框
					var myField = document.getElementById("style_content_edit");
					//获得输入框焦点
	  				myField.focus();
					//创建文档选择对象
	  				var textObj = document.selection.createRange();
					//创建输入框文本对象
  					var inputObj = myField.createTextRange(),inputObjCopy = inputObj.duplicate();
  					inputObj.moveToBookmark(textObj.getBookmark());
  					inputObjCopy.setEndPoint('EndToStart',re);
  					//把光标索引赋给一个全局变量
  					cursor = inputObjCopy.text.length;
				}
			})
			
			/**
			*	小票模版窗口中 各种按钮 的 弹出窗口
			*/
			function category(style_content_id,genre){
				var detailStore = new Ext.data.JsonStore({
			    	fields:['style_content','tagValue']
			    });
				
				//判断点击的是哪个按钮
			    if(genre == 1){
			    	//为按钮添加对应的值，值在js标签顶部设置过了
			    	detailStore.loadData(bcTags);
			    }else if(genre == 2){
			    	detailStore.loadData(fontTags);
			    }else if(genre == 3){
			    	detailStore.loadData(textTags);	
			    }else if(genre == 4){
			    	detailStore.loadData(barcodeTags);
			    }
			    
			    var detailData = [
			  						{header:'名称',dataIndex:'style_content',width:170}
			  					]
				var detailColModel=new Ext.grid.ColumnModel(detailData);
				//同添加小票模板弹出窗原理一致
			    var detailsWindow = new SelfFormWindowSetWidth('themeDetailsWindow','类别选择',200, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			//title:'',
			   			layout:'column',
			   			items:[{
			       			columnWidth:0,
			       			items:[{
			       				xtype:'grid',
			       				id:'paramGrid',
			       				store:detailStore,
			       				cm:detailColModel,
			       				autoHeight:true,
			       				iconCls:'icon-grid',
			       				stripeRows : true,
			       				doLayout:function(){
			       					this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true));
			       				}
			       			}]
			   			}]
			   		}
			    }],[
						{text:'<s:text name="common.button.cancel"/>', 	handler: function(){detailsWindow.close();}}
			        ]);
			    
			    Ext.getCmp('paramGrid').on('rowdblclick',function(grid, rowindex,e){
		  			var record = grid.getStore().getAt(rowindex);
		  			detailsWindow.close();
		  			//获取文本编辑框中光标的位置
		  			if(record.data['style_content']!='默认'){
		  				var myField = document.getElementById(style_content_id);
		  				myField.focus();
		  				var textObj = document.selection.createRange();
		  				//IE浏览器
		  				if(document.selection){
		  					var inputObj = myField.createTextRange(),inputObjCopy = inputObj.duplicate();
		  					inputObj.moveToBookmark(textObj.getBookmark());
		  					inputObjCopy.setEndPoint('EndToStart',inputObj);
		  					
		  					cursor = inputObjCopy.text.length;
		  					textObj.text = record.data['tagValue'];
		  				//非IE
		  				}else if(myField.selectionStart||myField.selectionStart == "0"){
		  					//得到光标的前一个字符的位置
		  					var startPos = myField.selectionStart;
		  					//得到光标后第一个字符的位置
		  					var endPos = myField.selectionEnd;
		  					//滚动条高度
		  					var restoreTop = myField.scrollTop;
		  					//插入数据
		  					Ext.getCmp(style_content_id).setValue(myField.value.substring(0,startPos) + record.data['tagValue'] + myField.value.substring(endPos,myField.value.length));
			  				//Ext.getCmp('style_content_edit').setValue(document.getElementById('style_content_edit').value + record.data['tagValue']);
			  				//如果滚动条高度大于0
			  				if(restoreTop>0){
			  					//返回
			  					myField.scrollTop = restoreTop;
			  				}
			  				myField.focus();
			  				myField.selectionStart = startPos + record.data['tagValue'].length;
			  				myField.selectionEnd = startPos + record.data['tagValue'].length;
		  				}else{
		  					myField.value += record.data['tagValue'];
		  					myField.focus();
		  				}
		  				/* Ext.getCmp('style_content_add').setValue(document.getElementById('style_content_add').value + record.data['tagValue']);
		  				Ext.getCmp('style_content_edit').setValue(document.getElementById('style_content_edit').value + record.data['tagValue']); */
		  			}else{
		  				Ext.getCmp(style_content_id).setValue('');
		  			}
		  		});
			    
			    detailsWindow.open();
			}
			
			/** 
			 * 小票模板弹出窗
			 */
			function ticketDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['branch','ticket_style_id','default_flag','style_content']
			    });
			    var detailData = [
      				{header:'机构号',dataIndex:'branch',width:100},
    				{header:'小票模板编号',dataIndex:'ticket_style_id',width:100},
    				{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
    					return value=='1'?'默认':'非默认';}},
    				{header:'模板内容',dataIndex:'style_content',width:500}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    detailsWindow = new SelfFormWindowSetWidth('ticketDetailsWindow','小票模板编号列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			title:'小票模板信息',
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
		  			Ext.getCmp('style_content_add').setValue(record.data['style_content']);
		  			Ext.getCmp('style_content_edit').setValue(record.data['style_content']);
		  			Ext.getCmp('default_flag_add').setValue(record.data['default_flag']);
		  			Ext.getCmp('default_flag_edit').setValue(record.data['default_flag']);
			  	});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '5';
			    requestAjax('<%=basePath%>/confManager/ticket_queryTicket',submitDetail, function(sRet){
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
						query_obj['query_rules'] = '4';
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
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['ticket_style_id','branch']);
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
								requestAjax('<%=basePath%>/confManager/ticket_delTicket',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										query_obj['query_rules'] = '4';
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
				createCheckBoxValue('default_flag',submitData);
				requestAjax('<%=basePath%>/confManager/ticket_editTicket',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '4';
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
				//获取小票弹出窗中输入的值
				var submitData = addwindow.getFields();
				createCheckBoxValue('default_flag',submitData);
				//调用Ajax操作js想后台发送请求
				requestAjax('<%=basePath%>/confManager/ticket_addTicket', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '4';
						pagequeryObj.queryPage(query_obj);
					});
					//关闭小票弹窗
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
		} 
	</script>

</head>

<body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
	<div id="editWindow"></div>
	<div id="themeDetailsWindow"></div>
	<div id="ticketDetailsWindow"></div>
</body>
</html>