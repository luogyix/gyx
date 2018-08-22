<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String brno = user.getUnitid();
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <base href="<%=basePath%>">
    
    <title>预填单版本更新</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html ;charset=utf-8" />
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
    	.editorGrid-topmargin{margin-top:10px;}
    </style>
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css"/>
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjs/ext-all-debug.js"></script>
	<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script language="javascript" type="text/javascript">
	var pagereturn=${actionresult};
		var conditionPanel = null;
	//	var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"请稍候...", removeMask:true});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		//包类型
		var packageTypeStore = new Ext.data.ArrayStore({
             fields: ['dictValue', 'dictValueDesc'],
             data: [['全部', '全部'],['apk', 'android'], ['ipa', 'ios']]
         });
		var packageTypeStoreAdd = new Ext.data.ArrayStore({
             fields: ['dictValue', 'dictValueDesc'],
             data: [['apk', 'android'], ['ipa', 'ios']]
         });
		
//		var packageTypeStore11 = new Ext.data.ArrayStore({
//			fields : ['dictValue' , 'dictValueDesc'] , 
//			data: [['android', 'android'], ['ios', 'ios']],
//			listeners:{
//				'load':function(){                   
//                    var data ={dictValue:'全部', dictValueDesc:'全部'};
//                    var q = new packageTypeStore.recordType(data);
//                    packageTypeStore.insert(0,q);
//				}
//			}
//		});
		
		//添加数据和包
//		var packageTypeStoreAdd = new Ext.data.JsonStore({
//			fields : ['dictValue' , 'dictValueDesc'] , 
//			url    : '<%=basePath%>framework/bsmscommon_getSystemDictionaryItem?item_id=updatePackType',
//			root   : 'field1', autoLoad:true
//		});
	

		//版本号 
		var updatepackageStoreVersion = new Ext.data.JsonStore({
			fields : ['packagetype','version','versionid'] , 
			url    : '<%=basePath%>pfs/update_package_getSystemDictionaryItem',
			root   : 'field1', autoLoad:true,
			listeners:{
				'load':function(){
					var data ={version:'N-最新版本', versionid:'N-最新版本标识', packagetype:'all'};
                    var p = new updatepackageStoreVersion.recordType(data);
                    updatepackageStoreVersion.insert(0,p);
                    
                    var data ={version:'全部', versionid:'全部', packagetype:'all'};
                    var q = new updatepackageStoreVersion.recordType(data);
                    updatepackageStoreVersion.insert(0,q);
				}
			}		
		});	
		//版本标识的数据源
		var updatepackageStoreVersionid = new Ext.data.JsonStore({
			fields : ['packagetype','version','versionid'] , 
			url    : '<%=basePath%>pfs/update_package_getSystemDictionaryItem',
			root   : 'field1', autoLoad:true,
			listeners:{
				'load':function(){
					var data ={version:'N-最新版本', versionid:'N-最新版本标识', packagetype:'all'};
                    var p = new updatepackageStoreVersionid.recordType(data);
                    updatepackageStoreVersionid.insert(0,p);
                    
                    var data ={version:'全部', versionid:'全部', packagetype:'all'};
                    var q = new updatepackageStoreVersion.recordType(data);
                    updatepackageStoreVersionid.insert(0,q);
				}
			}		
		});	
		
		/**
 * @Title: loadPage 
 * @Description: 点击查询弹出更新包查询条件
 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('updatePackagePanel', '预填单版本更新', 120, 1,
				[
				{rowIndex:0, field:{xtype :'combo', name:'packagetype', id:'sel_packageType1', fieldLabel:'包类型',hiddenName:'packagetype',
					store:packageTypeStore,displayField:"dictValueDesc", valueField:"dictValue", mode:'local', value:'全部',triggerAction: 'all', forceSelection:true, editable:false, 
					listeners:{
						'select':function(combo){
							Ext.getCmp('sel_version').setValue('');
							Ext.getCmp('sel_versionid').setValue('');
							updatepackageStoreVersion.load({params:{},
								callback: function(records , options , success){
									updatepackageStoreVersion.filterBy(function(record){
										return (record.get('packagetype') === Ext.getCmp('sel_packageType1').getValue() || record.get('packagetype') === 'all'||record.get('packagetype') === 'allquery');
									});
									Ext.getCmp('sel_version').setValue(updatepackageStoreVersion.getAt(0).get('version'));
								}
							});
							updatepackageStoreVersionid.load({params:{},
								callback: function(records , options , success){
									updatepackageStoreVersionid.filterBy(function(record){
										return (record.get('packagetype') === Ext.getCmp('sel_packageType1').getValue() || record.get('packagetype') === 'all');
									});
									Ext.getCmp('sel_versionid').setValue(updatepackageStoreVersionid.getAt(0).get('versionid'));
								}
							});
						}
					}
				}},
				{rowIndex:0, field:{xtype :'combo', name:'versionid',fieldLabel:'版本标识',id:'sel_versionid',value:'全部',
					store:updatepackageStoreVersionid, displayField:"versionid", valueField:"versionid", mode:'local', triggerAction: 'all', forceSelection:true, editable:false
					,
					listeners:{
						'expand':function(combo){
							var packagetype = Ext.getCmp('sel_packageType1').getValue();
							combo.getStore().filterBy(function(record){
								return (record.get('packagetype') === Ext.getCmp('sel_packageType1').getValue() || record.get('packagetype') === 'all');
							});
						},
						'select':function(combo){
							Ext.getCmp('sel_version').setValue('');
							updatepackageStoreVersion.load({params:{},
								callback: function(records , options , success){
									updatepackageStoreVersion.filterBy(function(record){
										return ((record.get('packagetype') === Ext.getCmp('sel_packageType1').getValue() 
												&& record.get('versionid') === Ext.getCmp('sel_versionid').getValue())
												|| Ext.getCmp('sel_versionid').getValue()==='N-最新版本标识'&&record.get('version') === 'N-最新版本'
												|| Ext.getCmp('sel_versionid').getValue()==='全部'&&record.get('version') === '全部'
												);
									});
									Ext.getCmp('sel_version').setValue(updatepackageStoreVersion.getAt(0).get('version'));
								}
							});
						}
					}
				  }
				},
				{rowIndex:0, field:{xtype :'combo', name:'version',fieldLabel:'版本号', id:'sel_version', value:'全部',
					store:updatepackageStoreVersion, displayField:"version", valueField:"version", mode:'local', triggerAction: 'all', forceSelection:true, editable:false,
					listeners:{
						'expand':function(combo){
							var packagetype = Ext.getCmp('sel_packageType1').getValue();
							combo.getStore().filterBy(function(record){
								return (record.get('packagetype') === Ext.getCmp('sel_packageType1').getValue() || record.get('packagetype') === 'all');
							});
						}
					}
				  }
				}
				],
				[
				{iconCls: "x-image-query", 			        id:'01',    text:'更新包查询'},		//查询
				{iconCls: "x-image-application_form_add", 	id:'02',	text:'<s:text name="common.button.addrecord"/>'},
				{iconCls: "x-image-application_form_edit", 	id:'03',	text:'<s:text name="common.button.editrecord"/>'},
				{iconCls: "x-image-application_form_delete",id:'04', 	text:'<s:text name="common.button.deleterecord"/>'},//删除
				{iconCls: "x-image-application_form_add",id:'06',text:'下载更新包'}
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '数据包上传', 542, 350, 140, 1,
				[
				{colIndex:0, field:{xtype :'combo', name:'packagetype',id:'sel_packageType',fieldLabel:'包类型',hiddenName:'packagetype',readOnly : false,
					store:packageTypeStoreAdd,displayField:"dictValueDesc", valueField:"dictValue", mode:'local', triggerAction: 'all', forceSelection:true, editable:false
					/*,
					listeners :{'select':function(combo ,  record ,  index  ){
										var submitData = {'packagetype':combo.getValue()};
										requestAjax('<%=basePath%>pfs/update_package_getVersion', submitData,function(sRet){
											Ext.getCmp('version').setValue(sRet.field1);
										});
										}
								}*/
							
					}},
				{colIndex:0, field:{xtype : 'textfield', name:'version',id:'version', 	fieldLabel:'版本号',	allowBlank : true,blankText:'请输入版本号',maxLength : 5,maxLengthText : '长度不能大于5位',editable:false, readOnly :true}},
				{colIndex:0, field:{xtype : 'textfield', name:'rcid',id:'rcid', 	fieldLabel:'数据库rcid',	allowBlank : true,hidden:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'versionid',id:'versionid', 	fieldLabel:'版本标识', regex:/^\d{1,3}\.\d{1,3}\.\d{1,3}$/,regexText:'输入格式:n.n.n', allowBlank:false,maxLength : 11,maxLengthText : '长度不能大于11位',blankText:'版本标识不能为空'}},
				{colIndex:0, field:{xtype : 'textfield', name:'packagename',id:'packagename', 	fieldLabel:'包名称',	allowBlank : true,hidden:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'filePath',id:'filePath', 	fieldLabel:'更新包路径',	allowBlank : true,hidden:true}},
				{colIndex:0,field:{xtype:'fieldset', layout:'column', title:'更新包上传', collapseFirst:true, collapsible:false, height:180,anchor:'98.5%', items:[
						{columnWidth:3/4, layout:'form', items:[{xtype:'textfield', fieldLabel:'更新包路径', id:'upload', name:'upload', anchor:'98%', inputType:"file", allowBlank:false, blankText:'上传文件不能为空' }]},
						{columnWidth:1/1, layout:'form', items:[{xtype:'textarea', fieldLabel:'更新包描述', name:'remark', id:'remark', anchor:'98%',height:100,allowBlank:true, maxLength:300 }]}
					]}}
					//items 使用方法
					//Ext.getCmp(fieldset).get(0)
					//Ext.getCmp().items[0].
				
				],	
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked, id:'addBtn'},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left',90,true
			);
			
			
	         /**
			 * @Title:onqueryclicked
			 * @Description:点击查询时的操作
			 */
			function onqueryclicked(){
				var submitData = conditionPanel.getFields();
				pagequeryObj.queryPage(submitData);	
			}
	
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>pfs/update_package_query_package',
				['rcid','packagetype','versionid','version','packagename','updatedatetime'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'数据库ID',dataIndex:'rcid',width:100,hidden:true},
				{header:'包类型',dataIndex:'packagetype',width:100,
					renderer:function(value){
						return getDictValue(value,packageTypeStore,'dictValue','dictValueDesc');
					}
				},
				{header:'版本号',dataIndex:'version',width:150},
				{header:'版本标识',dataIndex:'versionid',width:150},
				{header:'包名称',dataIndex:'packagename',width:150},
				{header:'更新时间',dataIndex:'updatedatetime',width:150,renderType:4}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			/**
			* @Title:onaddclicked
			* @Description:添加数据函数
			*/
			function onaddclicked(){
				var submitData = addwindow.getFields();
				if(!Ext.getCmp('upload').isValid())//?
					return;
				var furl = Ext.getCmp('upload').getValue();
				Ext.getCmp('filePath').setValue(furl);
				//上传文件的格式
				var type = furl.substring(furl.length - 3).toLowerCase();
				//var packagetype = (Ext.getCmp('sel_packageType').getValue()=="mfp"?"apk":"zip");
				//选择包类型的格式
				var packagetype = Ext.getCmp('sel_packageType').getValue();
				if (type != packagetype && packagetype == 'apk') {
	    			Ext.MessageBox.alert('提示','包类型android仅支持'+packagetype+'格式文件');
	    			return;
	    		}
	    		if (type != packagetype && packagetype == 'ipa') {
	    			Ext.MessageBox.alert('提示','包类型ios仅支持'+packagetype+'格式文件');
	    			return;
	    		}
					//myMask.show();
				if (addwindow.window.title=='数据包上传'){
				    addwindow.getForm().submit({
				    	url : '<%=basePath%>pfs/update_package_uploadPackage',
				    	method:'POST',
				    	waitTitle: '提交中',
    					waitMsg: '提交中，请稍后......',
						success : function(form, action){Ext.MessageBox.alert('提示','上传成功');addwindow.close();//myMask.hide();
						onqueryclicked();},
						failure : function(form, action){Ext.MessageBox.alert('提示','上传失败');}					
					});
				}
				else{
					addwindow.getForm().submit({url : '<%=basePath%>pfs/update_package_update',
						method:'POST',
						waitTitle: '提交中',
    					waitMsg: '提交中，请稍后......',
					    success : function(form, action){
						Ext.MessageBox.alert('提示','更新成功');
						// myMask.hide();
						addwindow.close();
						onqueryclicked();
						},
					failure : function(form, action){
							Ext.MessageBox.alert('提示','更新失败');
							}
				});
				}
				
			}
			
			/**
			 * @Title:onButtonClicked
			 * @Description:触发"查询"，"添加"，"修改"，"删除"的选择语句
			 */
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						onqueryclicked();
						break;					
					case 1:
						addwindow.window.setTitle('数据包上传');
						Ext.getCmp('addBtn').setText('添加');
						Ext.getCmp('sel_packageType').setReadOnly(false);
						Ext.getCmp('sel_packageType').removeClass('x-readOnly-bgColor');
						Ext.getCmp('version').setVisible(false);
						addwindow.open();
						break;
					case 2:
						addwindow.window.setTitle('修改更新包');
						Ext.getCmp('addBtn').setText('修改');
						Ext.getCmp('sel_packageType').setReadOnly(true); 
						Ext.getCmp('sel_packageType').addClass('x-readOnly-bgColor');
						Ext.getCmp('version').setVisible(true);
						
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
						
						addwindow.open();
						displayBack(records[0].get('rcid'));
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['rcid','packagename']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('系统提示','请选择一条记录');
							break;
						}
						
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>pfs/update_package_delete',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					    break;
					 case 4:
						 var submitdata = pagequeryObj.getSelectedObjects(['rcid','packagename']);
						 var records = pagequeryObj.getSelectedRecords();
						if(records === undefined){
							Ext.MessageBox.alert('系统提示','请选择一条记录');
							break;
						}
						if(records.length !== 1){
							Ext.MessageBox.alert('系统提示','每次只能下载一个更新包');
							break;
						}
						else{
							window.location = encodeURI('<%=basePath%>update_package_download?rcid='+submitdata[0].rcid+'&packagename='+submitdata[0].packagename);	
						}
					break;
				}
			}
			
			/**
			* @Title:displayBack
			* @Description:修改回显函数
			*/
			function displayBack(rcid){
				var submitData = "{rcid:"+rcid+"}";
				requestAjax('<%=basePath%>pfs/update_package_queryBtpfsupdatepackage', submitData,function(sRet){
   					 		var entity = sRet.field1;
  					 			 addwindow.updateFields(new Ext.data.Record(entity));
							}
				);
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
     <div id="updatePackagePanel"></div>
     <div id="pageQueryTable"></div>
     <div id="querywindow"></div>
     <div id="addWindow"></div>
  </body>
</html>
