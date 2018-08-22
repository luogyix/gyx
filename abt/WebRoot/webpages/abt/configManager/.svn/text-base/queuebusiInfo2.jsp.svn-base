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
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="extjs/ux/CheckColumn.js"></script>
	
	<script type="text/javascript">
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
	  
		Ext.onReady(loadPage);
		function loadPage(){
	    	//取界面长宽
			var clientHeight = document.body.clientHeight;
	    	var clientWidth  = document.body.clientWidth;
	    
	    	var busiStore = new Ext.data.JsonStore({
				fields : ['bs_id','bs_name_ch','monday','tuesday','wednesday','thursday','friday','saturday','sunday','apply_holiday','ss_time','se_time','at_time','note','serv_time_list'],
	    		url : '<%=basePath%>confManager/queuebusi_loadConf',
	    		root   : 'field1',
	    		autoLoad : true//,
	    		//listeners : {  
				//	load : function(store,records){
				//		if(records.length>0){
							//Ext.getCmp('ticket_fstip').setValue(store.getAt(0).data['ticket_fstip']);
				//			for ( var i = 0; i < records.length; i++) {
								//var serv_time_list = records.get(i).get('serv_time_list');
				//				var serv_time_list = records[i].data.serv_time_list;
				//				if(serv_time_list.length>0){
				//					var servTimeArray = serv_time_list.split(';');
				//					var final_serv_time = '';
				//					for ( var j = 0; j < servTimeArray.length; j++) {
										//080000-090000
										//0123456789012
				//						var servTime = servTimeArray[j].substr(0,2) + ':' + servTimeArray[j].substr(2,2) + 
				//										':' + servTimeArray[j].substr(4,5) + ':' + servTimeArray[j].substr(9,2) + 
				//										':' + servTimeArray[j].substr(11,2);
				//						final_serv_time += ';' + servTime;
				//					}
				//					store.getAt(i).data['serv_time_list'] = final_serv_time.substr(1);
									//records[i].data.serv_time_list = final_serv_time.substr(1);
									//records.get(i).set('serv_time_list',final_serv_time.substr(1));
				//				}
				//			}
							//busiStore.loadData(records);
				//		}
				//	}
				//}
	    	});
	    	
	    	var timeStore = new Ext.data.JsonStore({
				fields : ['begin_time','end_time']
			});
	    	
	    	var timeData = [
   				new Ext.grid.RowNumberer({header:'序号',width:33}),
   				{xtype: 'gridcolumn', header:'复选框', align: 'center',hidden:true},
   				{ xtype: 'gridcolumn',dataIndex: 'begin_time',header: '服务时间(从)',sortable:false,editor: {xtype: 'timefield',format:'H:i:s',increment:30,minValue: '08:00:00',maxValue: '18:00:00'}},
		      	{ xtype: 'gridcolumn',dataIndex: 'end_time',header: '服务时间(到)',sortable:false,editor: {xtype: 'timefield',format:'H:i:s',increment:30,minValue: '08:00:00',maxValue: '18:00:00'}}
   			];
	    	var timeSM = new Ext.grid.CheckboxSelectionModel({ singleSelect: false });
	    	var timeCM = new Ext.grid.ColumnModel(timeData);
	    	
			var timeGrid = new Ext.grid.EditorGridPanel({
				id: 'timeGrid',
			    store: timeStore,
			    colModel: timeCM,
			    sm:timeSM,
			    stripeRows: true,
			    height:240,
			    frame:true,
			    viewConfig: {forceFit: true},
			    clicksToEdit:1,
			    tbar: [
			    	{id:'addBtn', text: '添加', disabled: false, iconCls: "x-image-add", 
			    		handler: function(){
							var length= timeStore.getCount();
   							var recordType = timeStore.recordType;
   							var num = timeStore.getCount();
   							var record = new recordType({
   								begin_time:'',end_time:''
							});
   							timeGrid.stopEditing();
   							timeStore.insert(length, record);
   							timeGrid.getView().refresh();
   							timeSM.selectRow(length);
   							timeGrid.startEditing(timeStore.getCount()-1, 3);
			    		} 
			    	},'-',
			    	{id:'delBtn', text: '删除', disabled: false, iconCls: "x-image-delete", 
			    		handler: function(){
			    			if(!timeSM.hasSelection()){
								Ext.Msg.alert('<s:text name="common.info.title"/>' , '<s:text name="common.info.mustselectrecord"/>');
								return;
							}
							Ext.MessageBox.confirm('<s:text name="common.info.title"/>' , '<s:text name="common.info.suredelete"/>' , function(id){
								if(id=='yes'){
									var records = timeSM.getSelections();
									for(var i=0; i<records.length; i++){
										timeStore.remove(records[i]);
									}
								}
							});
			    		}
			    	}
			    ]
			});
			
			timeGrid.on('afteredit',function(e,a,b,c){
				var record = e.record;
				//e.originalValue;
				if(record.get('end_time')!=''&&record.get('begin_time')!=''){//判断都非空
					if (record.get('begin_time') >= record.get('end_time')) {
						Ext.MessageBox.alert('提示','起始时间必须小于结束时间',function(id){
							record.set(e.field,e.originalValue);
							return;
						});
					}
					//本条内条件皆通过,与已有数据进行比对
					for ( var i = 0; i < timeStore.getCount(); i++) {
						//e.row 所在行 一会循环要排除 e.column所在列 
						if(i==e.row){
							continue;
						}
						var r = timeStore.getAt(i);
						//r.get('begin_time') r.get('end_time') a b
						//record.get('begin_time')  record.get('end_time') s e
						if(record.get('begin_time') < r.get('begin_time') && record.get('end_time') > r.get('begin_time')){
							Ext.MessageBox.alert('提示','['+record.get('begin_time')+'-'+record.get('end_time')+']与['+r.get('begin_time')+'-'+r.get('end_time')+']的时间段冲突,请重新修改',function(id){
								record.set(e.field,e.originalValue);
								return;
							});
						}else if(record.get('begin_time') >= r.get('begin_time') && record.get('begin_time')<r.get('end_time')){
							Ext.MessageBox.alert('提示','['+record.get('begin_time')+'-'+record.get('end_time')+']与['+r.get('begin_time')+'-'+r.get('end_time')+']的时间段冲突,请重新修改',function(id){
								record.set(e.field,e.originalValue);
								return;
							});
						}
					}
				}
			});
			var row = null;
			var timeWindow = new SelfFormWindow('timeWindow', '服务时段配置', 500, 355, 460, 1,
				[
				 	{colIndex:0,field:{xtype:'hidden',id:'row'}},
					{colIndex:0,field:{
						xtype:'fieldset', layout:'column', items:[
                       		timeGrid
                       	]
					}}
				],
				[
					{xtype: 'button' , text:'保存' , id:'btnSave' , handler:function(){
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>' , '保存此配置?' , function(id){
							if(id=='yes'){
								var serv_time_list = '';
								for ( var i = 0; i < timeStore.getCount(); i++) {
									var r = timeStore.getAt(i);
									serv_time_list+=';' + r.get('begin_time')+'-'+r.get('end_time');
									if(r.get('begin_time')==''||r.get('end_time')==''){
										Ext.MessageBox.alert('提示','数据未填写完整,请修改');
										return;
									}
								}
								busiStore.getAt(row).set('serv_time_list',serv_time_list.substr(1));
								timeWindow.close();
							}
						});
					}},
					{xtype: 'button' , text:'取消' , id:'btnClose', handler:function(){timeWindow.close();}}
				]
			);
			
		    var busiData = [
				new Ext.grid.RowNumberer({header:'序号',width:33}),
		      	{ xtype: 'gridcolumn',dataIndex: 'bs_id',header: '业务编号',sortable:false,width:100,hidden:true,align:'center'},
		      	{ xtype: 'gridcolumn',dataIndex: 'serv_time_list',hidden:true},
		      	{ xtype: 'gridcolumn',dataIndex: 'bs_name_ch',header: '业务名称',sortable:false,width:150,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'allcheck',header: '全选',sortable:false,width:60,align:'center',listeners:{
		      		'checkchange' : function(column, rowIndex, checked,record){
		      			record.set('monday',checked);
		      			record.set('tuesday',checked);
		      			record.set('wednesday',checked);
		      			record.set('thursday',checked);
		      			record.set('friday',checked);
		      			record.set('saturday',checked);
		      			record.set('sunday',checked);
		      			record.set('apply_holiday',checked);
		      		}
		      	}},
		      	{ xtype: 'checkcolumn',dataIndex: 'monday',header: '星期一',sortable:false,width:60,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'tuesday',header: '星期二',sortable:false,width:60,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'wednesday',header: '星期三',sortable:false,width:60,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'thursday',header: '星期四',sortable:false,width:60,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'friday',header: '星期五',sortable:false,width:60,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'saturday',header: '星期六',sortable:false,width:60,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'sunday',header: '星期日',sortable:false,width:60,align:'center'},
		      	{ xtype: 'checkcolumn',dataIndex: 'apply_holiday',header: '节假日',sortable:false,width:60,align:'center'},
		      	//{ xtype: 'gridcolumn',dataIndex: 'ss_time',header: '服务时间(从)',sortable:false,editor: {xtype: 'timefield',format:'H:i:s',increment:60,minValue: '08:00:00',maxValue: '18:00:00'}},
		      	//{ xtype: 'gridcolumn',dataIndex: 'se_time',header: '服务时间(到)',sortable:false,editor: {xtype: 'timefield',format:'H:i:s',increment:60,minValue: '08:00:00',maxValue: '18:00:00'}},
		      	{ xtype: 'gridcolumn',dataIndex: 'at_time',header: '提前取号时间(分钟)',width:110,sortable:false,editor: {xtype: 'numberfield',allowBlank: true}},
		      	{ xtype: 'actioncolumn',header: '服务时段配置',width:90,align:'center',sortable:false,items: [{
                    icon   : 'images/config.png',  // Use a URL in the icon config
                    tooltip: '服务时段配置',
                    handler: function(grid, rowIndex, colIndex) {
                        var gridData = busiStore.getAt(rowIndex);
                        //gridData.data   json   store
                        timeStore.removeAll();
                        var newArray = new Array();
                        
                        //这里需要将某个长字段拆分成多个数组放入store
                        //var a = '07:00:00-08:00:00;08:00:00-09:00:00';
                        var serv_time_list = gridData.get('serv_time_list').split(';');
                        //将数据冒泡法排序
                        //int temp=0;
                        //for ( var m=serv_time_list.length-1;m>0;--m) {
                        //	for(var n=0;n<m;++n)
            			//	{
            			//		if(serv_time_list.get(n+1)<serv_time_list.get(n))
            			//		{
            			//			temp=serv_time_list.get(n);
            			//			serv_time_list[n]=serv_time_list[n+1];
            			//			serv_time_list[n+1]=temp;
            			//		}
            			//	}
                        //}
                        
                        for ( var i = 0; i < serv_time_list.length; i++) {
							var serv_time = serv_time_list[i];
							if(serv_time.length>0){
								var datas = {};
								//if (data.indexOf(':')==-1) {
								//	var begin_time = data.substr(0,6);
								//	var end_time = data.substr(7);
								//	datas['begin_time'] = begin_time.substr(0,2)+':'+begin_time.substr(2,2)+':'+begin_time.substr(4,2);
								//	datas['end_time'] = end_time.substr(0,2)+':'+end_time.substr(2,2)+':'+end_time.substr(4,2);
								//} else {
								//	datas['begin_time'] = data.substr(0,8);
								//	datas['end_time'] = data.substr(9,8);
								//}
								datas['begin_time'] = serv_time.substr(0,8);
								datas['end_time'] = serv_time.substr(9,8);
								newArray.push(datas);
							}
						}
                        timeStore.loadData(newArray);
                        row = rowIndex;
                        timeWindow.open();
                    }
                }]},
                { xtype: 'gridcolumn',dataIndex: 'serv_time_list',header: '服务时段',width:500,sortable:false}
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
								viewConfig: {forceFit: false},
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
							height:((clientHeight*1/2)*1/3)-20,
							xtype:'fieldset',
							title:'说明:',
							border :false,
							layout:'form',
							items:[{
								html:'<font style="font-size:16px;">1、排队机加载节假日业务优先于星期业务；2、可配置多个服务时间段</font>'
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
									data['serv_time_list'] = record[i].data['serv_time_list'];
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
  <body>
	<div id="panel"></div>
	<div id="timeWindow"></div>
  </body>
</html>