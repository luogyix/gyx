<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>业务可办理时段配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="extjs/ux/CheckColumn.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	
	<script type="text/javascript">
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
	  
		Ext.onReady(loadPage);
		function loadPage(){
	    	//取界面长宽
			var clientHeight = document.body.clientHeight;
	    	var clientWidth  = document.body.clientWidth;
	    
	    	var busiStore = new Ext.data.JsonStore({
				fields : ['bs_id','bs_name_ch','monday','tuesday','wednesday','thursday','friday','saturday','sunday','apply_holiday','ss_time','se_time','at_time','note'],
	    		url : '<%=basePath%>confManager/queuebusi_loadConf',
	    		root   : 'field1',
	    		autoLoad : true
	    	});
		    var busiData = [
				new Ext.grid.RowNumberer({}),
		      	{ xtype: 'gridcolumn',dataIndex: 'bs_id',header: '业务编号',sortable:true,width:100,hidden:true,align:'center'},
		      	{ xtype: 'gridcolumn',dataIndex: 'bs_name_ch',header: '业务名称',sortable:true,width:150,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'monday',header: '星期一',sortable:true,width:60,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'tuesday',header: '星期二',sortable:true,width:60,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'wednesday',header: '星期三',sortable:true,width:60,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'thursday',header: '星期四',sortable:true,width:60,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'friday',header: '星期五',sortable:true,width:60,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'saturday',header: '星期六',sortable:true,width:60,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'sunday',header: '星期日',sortable:true,width:60,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'apply_holiday',header: '节假日',sortable:true,width:60,align:'center'},
		      	{ xtype: 'gridcolumn',dataIndex: 'ss_time',header: '服务时间(从)',sortable:true,editor: {xtype: 'timefield',format:'H:i:s',increment:60,minValue: '08:00:00',maxValue: '18:00:00'}},
		      	{ xtype: 'gridcolumn',dataIndex: 'se_time',header: '服务时间(到)',sortable:true,editor: {xtype: 'timefield',format:'H:i:s',increment:60,minValue: '08:00:00',maxValue: '18:00:00'}},
		      	{ xtype: 'gridcolumn',dataIndex: 'at_time',header: '提前取号时间(分钟)',width:140,sortable:true,editor: {xtype: 'textfield',regex:/^[0-9]*$/,regexText:'只能输入数字'}}
		    ];
		    var busiColModel=new Ext.grid.ColumnModel(busiData);
		    
			var panel = new Ext.Panel({
				layout : 'absolute',
				frame:true,
		     	height:clientHeight,
		     	applyTo :'panel',
		     	border :false,
		      	items:[{
					layout:'column', 
					frame:true,
					height:clientHeight,
					width:clientWidth,
					items:[{
						columnWidth:1, 
						layout:'form', 
						bodyStyle:'padding:5 8 0 8', 
						labelAlign:'left',
						items:[{
							width: clientWidth-40,
							height:clientHeight*2/3-40,
							xtype:'fieldset',
							layout:'column',
							title:'业务可办理时段配置',
							items:[{
								xtype: 'editorgrid',
								name:'grid',
								id:'grid',
								store: busiStore,
								height: (clientHeight*2/3)-75,
								viewConfig: {forceFit: true},
								doLayout: function(){
									this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true));
					  			},
					  			cm: busiColModel,
					  			clicksToEdit: 1
		            		}]
		          		}]
					},{
						columnWidth:1, 
						layout:'form', 
						bodyStyle:'padding:5 8 0 8', 
						labelAlign:'left',
						items:[{
							width: clientWidth-40,
							height:((clientHeight*1/2)*1/3)-30,
							xtype:'fieldset',
							layout:'column',
							title:'说明:',
							border :false,
							items:[{
								columnWidth:1, 
								layout:'form',
								items:[{
									html:'<font style="font-size:16px;">1、排队机加载节假日业务优先于星期业务；2、可点击服务时间和提前取票表格项进行修改；3、服务时间格式如：08:00-18:00</font>'
									}]
							}]
						}]
		        	},{
		        		columnWidth:1, 
		        		layout:'form', 
		        		bodyStyle:'padding:5 8 0 8',
		        		buttonAlign :'center',
						buttons:[{
							text:'提交',
							width:150,
							height:40,
							handler:function(){
								var record = Ext.getCmp('grid').getStore().data.items;
								var submitdata = {};
								for(var i = 0;i<record.length;i++){
									var data = {};
									data['bs_id'] = record[i].data['bs_id'];
									data['bs_name_ch'] = record[i].data['bs_name_ch'];
									data['monday'] = record[i].data['monday'];
									data['tuesday'] = record[i].data['tuesday'];
									data['wednesday'] = record[i].data['wednesday'];
									data['thursday'] = record[i].data['thursday'];
									data['friday'] = record[i].data['friday'];
									data['saturday'] = record[i].data['saturday'];
									data['sunday'] = record[i].data['sunday'];
									data['apply_holiday'] = record[i].data['apply_holiday'];
									data['ss_time'] = record[i].data['ss_time'];
									data['se_time'] = record[i].data['se_time'];
									data['at_time'] = record[i].data['at_time'];
									data['note'] = record[i].data['note'];
									submitdata[i] = data;
								}
								requestAjax('<%=basePath%>confManager/queuebusi_saveConf',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="操作成功"/>',function(id){
										busiStore.load();
									});
								});
		            		}
						}]
		        	}]
				}]
			});
	//-------------------------------------------------------------------------布局
	        function buildLayout(){
	          var viewport = new Ext.Viewport({
	            layout: 'absolute',
	            items : [panel]
	          });
	        }
	        buildLayout();	  
		  }
	</script>
  </head>
  <body scroll="no">
	<div id="panel"></div>
  </body>
</html>