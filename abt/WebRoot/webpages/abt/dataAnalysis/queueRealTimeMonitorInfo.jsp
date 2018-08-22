<%@page
	import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@ page language="java"
	import="java.util.*,com.agree.framework.web.form.administration.*"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	User user = (User) request.getSession().getAttribute("logonuser");
	String bank_level = user.getUnit().getBank_level();
%>

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>队列实时监控</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css"
			href="extjs/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="selfcss/common.css" />
		<style type="text/css">
.cell_1 {
	text-align: center;
	vertical-align: text-top;
	font-size: medium;
	font-weight: blod;
	height: 50;
	background-color: #ffcc33;
	color: #996633;
}

.cell_2 {
	text-align: center;
	vertical-align: text-top;
	border-top: 0px solid red !important;
	border-right: 0px !important;
	font-size: small;
	height: 45;
	background-color: #fde482;
}

.cell_3 {
	text-align: center;
	vertical-align: text-top;
	border-top: 0px solid red !important;
	border-right: 0px !important;
	font-size: small;
	height: 45;
	background-color: #fef1c0;
}

.x-grid-record-red table {
	background: #E75353;
	color: #FFFFFF;
}

.x-grid-record-yellow table {
	background: #F3FEC2;
}

.x-grid-record-green table {
	background: #F3FEC2;
}
</style>
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.js"></script>
		<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
		<script type="text/javascript" src="selfjs/common/TreeFilter.js"></script>
		<script type="text/javascript" src="selfjs/common/PinyinFilter.js"></script>
		<script type="text/javascript"
			src="selfjs/extendjs/SelfTreeGenerator.js"></script>

		<script type="text/javascript"
			src="selfjs/common/ajax-pushlet-client.js"></script>

		<script type="text/javascript"
			src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
		<script type="text/javascript"
			src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
		<script type="text/javascript">
	var detailsWindow;
	  //数据store
	  var realTimeStore = new Ext.data.JsonStore({
        fields:[ 'branch', 'queuetype_id', 'queuetype_name', 'btqmsum', 'averagewaittime', 'queuedetails','branch_name']	        
      });
		var queuetypeStore = new Ext.data.JsonStore({
			
			url: '<%=basePath%>/confManager/queuetype_queryQueueTypeSmall?query_rules=0',
			autoLoad:true,
			root   : 'field1',
			fields:['queuetype_id','queuetype_name'],
			listeners : {
				load : function(store,records){
					var temp = store.data.items;
					for(var i=0;i<temp.length;i++){
						temp[i].data['queuetype_name'] = temp[i].data['queuetype_id'] + '-' + temp[i].data['queuetype_name'];
					}
					this.removeAll();
					store.add(temp);
				}
			}
		});
		var defWaitnumThreshold = '';
		var defWaittimeThreshold = '';
		var dataStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>confManager/branchParam_loadConfByBranch',
			root: 'field1',
			//dataStore.load({params:temp}); temp = {}; temp[''] = Ext.getCmp('').getValue();
			fields : ['branch','default_flag','reserv_status','reserv_max_time','reserv_maxdays_before','reserv_minmin_before','reserv_prompt'
			          ,'negative_monitor','remaind_flag','def_waitnum_threshold','def_waittime_threshold','def_notify_threshold','def_show_notify_threshold'],
			listeners : {  
				load : function(store,records){
					if(store.data.items!=''){
						defWaitnumThreshold = store.getAt(0).data['def_waitnum_threshold'];
						defWaittimeThreshold = store.getAt(0).data['def_waittime_threshold'];
					}
				}
			}
	    });
		var qmMonitorStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%><%=basePath%>/confManager/QMMonitorConf_queryQMMonitorConfByBranch',
			root   : 'field1',
			fields : ['branch','queuetype_id','waitnum_threshold','waittime_threshold','notify_threshold','show_notify_threshold']
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
			data:[['1',"1-普通"],['2',"2-转移"],['3','3-预约']],
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
			data:[['0','0-未完成'],['1',"1-已完成"],['2',"2-取消"],['3','3-正在处理'],['4','4-处理完毕']],
			fields : ['dictValue','dictValueDesc']
		});
		var assessStatuseStore = new Ext.data.SimpleStore({ 
			data:[['-1','客户未评价'],['0','柜员未发起评价'],['1','不满意'],['2','一般'],['3','满意'],['4','非常满意']],
			fields : ['dictValue','dictValueDesc']
		});
		var branch = <%="'" + user.getUnit().getUnitid() + "'"%>;
	  	var pagereturn=${actionresult};
	 	var systemUnits=pagereturn.field1;
	  	var haschild;
		var branchName =  <%="'" + user.getUnit().getUnitname() + "'"%>;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		var pagequeryObj;
      //配置推送事件
      var list = [];
      function onData(event){
        var result = event.get("result");
        var obj=eval("("+result+")");
        //推送事件
        var tsList = obj.list;
        if(tsList.length==0){
        	return;
        }
        var f = false;
        var finList = [];
        for(var i=0;i<tsList.length;i++){//第一层 推送来的数据
        	var ts = tsList[i];
			finList.push(ts);
		}
        //if(f){
        //	list = tsList;
        //	refresh(tsList);
        //}
        list = finList;
		if(haschild=='1'){
			refresh1(finList);
		}else{
			refresh(finList);
		}
      }
      

	  
	  Ext.onReady(loadPage);
	  var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'isleaf',nodeLevel:'unitlevel',nodeType:'bank_level'};
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
		    })],
			rootVisible : true, 
			root:tree_a
		});
	  	var detailStore = new Ext.data.JsonStore({
	      fields:[
			'work_date','queue_num','branch','branch_name','transfer_num','qm_num',
			'qm_ip','queue_seq','softcall_teller','softcall_teller_name','softcall_seq',
			'bs_id','bs_name_ch','queuetype_id','queuetype_name','custtype_i',
			'custtype_name','node_id','win_num','window_ip','queuenum_type',
			'custinfo_type','custinfo_num','queue_status','en_queue_time','de_queue_time',
			'start_serv_time','finish_serv_time','assess_status','reserv_flag',
			'reserv_id','remaind_flag','remaind_phone','noti_waitnum','noti_setnum',
			'isnotify','isbefore','beforestatus','time','vcalltime'
	      ]
	    });
	    var detailData = [
			//{header:'调整队列',dataIndex:'',width:100,align:'center',renderer:adjust},
//			{header:'手动弃号',dataIndex:'',width:100,align:'center',renderer:qihao},
			{header:'日期',dataIndex:'work_date',width:100},
			{header:'队列号',dataIndex:'queue_num',width:100},
			{header:'处理窗口号',dataIndex:'win_num',width:100},
			{header:'机构号',dataIndex:'branch',width:80},
			{header:'机构名称',dataIndex:'branch_name',width:150},
			{header:'转移次数',dataIndex:'transfer_num',width:65},
			{header:'业务编号',dataIndex:'bs_id',width:120},
			{header:'业务名称',dataIndex:'bs_name_ch',width:120},
			{header:'队列编号',dataIndex:'queuetype_id',width:120},
			{header:'队列名称',dataIndex:'queuetype_name',width:120},
			{header:'内部客户类型',dataIndex:'custtype_i',width:100,renderer:function(value){
				return getDictValue(value,custTypeStore,'value','key');
			}},
			{header:'客户类型名称',dataIndex:'custtype_name',width:100},
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
			{header:'进队时间',dataIndex:'en_queue_time',width:100,renderer:function(value){
				var date = value;
				if(date!=""){
					return date.substr(0,2)+":"+date.substr(2,2)+":"+date.substr(4,2);
				}else{
					return date;
				}
			}},
			{header:'等待时间',dataIndex:'time',width:100,renderer:function(value){
				return value+"分钟";
			}},
			{header:'出队时间',dataIndex:'de_queue_time',width:100,renderer:function(value){
				var date = value;
				if(date!=""){
					return date.substr(0,2)+":"+date.substr(2,2)+":"+date.substr(4,2);
				}else{
					return date;
				}
			}},
			{header:'开始办理业务时间',dataIndex:'start_serv_time',width:120,renderer:function(value){
				var date = value;
				if(date!=""){
					return date.substr(0,2)+":"+date.substr(2,2)+":"+date.substr(4,2);
				}else{
					return date;
				}
			}},
			{header:'结束办理业务时间',dataIndex:'finish_serv_time',width:120,renderer:function(value){
				var date = value;
				if(date!=""){
					return date.substr(0,2)+":"+date.substr(2,2)+":"+date.substr(4,2);
				}else{
					return date;
				}
			}},
			{header:'客户评价结果',dataIndex:'assess_status',width:110,renderer:function(value){
				return getDictValue(value,assessStatuseStore,'dictValue','dictValueDesc');
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
			//{header:'是否预填单',dataIndex:'isbefore',width:80,renderer:function(value){
			//	return getDictValue(value,isbeforeStore,'dictValue','dictValueDesc');
			//}},
			{header:'填单状态',dataIndex:'beforestatus',width:80,renderer:function(value){
				return getDictValue(value,beforestatusStore,'dictValue','dictValueDesc');
			}},
			{header:'虚拟叫号时间',dataIndex:'vcalltime',width:120,renderer:function(value){
				var date = value;
				if(date!=""){
					return date.substr(0,2)+":"+date.substr(2,2)+":"+date.substr(4,2);
				}else{
					return date;
				}
			}},
			{header:'柜员号',dataIndex:'softcall_teller',width:100},
			{header:'柜员名',dataIndex:'softcall_teller_name',width:100},
			{header:'排队机编号',dataIndex:'qm_num',width:100},
			{header:'排队机IP',dataIndex:'qm_ip',width:100},
			{header:'排队机取号流水',dataIndex:'queue_seq',width:100},
			{header:'软叫号流水',dataIndex:'softcall_seq',width:100},
			{header:'节点ID(保留)',dataIndex:'node_id',width:100},
			{header:'处理窗口IP',dataIndex:'window_ip',width:160}
	    ];
	    var detailColModel=new Ext.grid.ColumnModel(detailData);
	function adjust(value, cellmeta, record, rowIndex, columnIndex, store){
		return "<A href='javascript:void(0)' onclick=\"submitAdjust('"+record.data['queue_num']+"','"+record.data['queuetype_id']+"');return false;\">调整队列</A>";
	}
	function qihao(value, cellmeta, record, rowIndex, columnIndex, store){
		if(record.data['queue_status']!='0'){
			return "";
		}else{
			return "<A href='javascript:void(0)' onclick=\"submitqihao('"+record.data['queue_num']+"','"+record.data['branch']+"','"+record.data['queuetype_id']+"','"+record.data['work_date']+"');return false;\">手动弃号</A>";
		}
	}
	  
	//通过队列号和队列ID调整该队列号的队列ID
	function submitAdjust(queue_num,queuetype_id){
		var adjustWindow = new SelfFormWindowSetWidth('adjustWindow','',400, 200, 355, 1, [355],[
			{colIndex:0,field:{xtype:'fieldset',title:'队列调整',layout:'column',items:[//queuetypeStore
	        	{columnWidth:1,layout:'form',items:[{xtype:'combo',id:'old_queuetype_id',fieldLabel:'原队列',width:100,anchor:'98%',readOnly:true,value:queuetype_id,store: queuetypeStore,displayField:'queuetype_name',valueField:'queuetype_id'}]},
	        	{columnWidth:1,layout:'form',items:[{xtype:'combo',id:'new_queuetype_id',fieldLabel:'调整队列',width:100,anchor:'98%',value:queuetype_id,store: queuetypeStore,displayField:'queuetype_name',valueField:'queuetype_id',editable:false}]}
				]}
			}
	    ],[
	    {text:'提交',width:60,handler:function(){
	      var submitAdjust = adjustWindow.getFields();
	      submitAdjust['old_queuetype_id'] = Ext.getCmp('old_queuetype_id').getValue();
	      submitAdjust['new_queuetype_id'] = Ext.getCmp('new_queuetype_id').getValue();
	      submitAdjust['queue_num'] = queue_num;
	      submitAdjust['branch'] = branch;
		  submitAdjust['query_rules'] = '1';	      
	      requestAjax('<%=basePath%>/dataAnalysis/queueRealTime_adjust',submitAdjust, function(sRet){
	        refresh(sRet.field1);
	        adjustWindow.destory();
	        detailsWindow.destory();
	      });
	    }},
	    {text:'关闭',handler: function(){adjustWindow.destory();},width:60}
	    ],'left',100);
	    adjustWindow.open();
	}
	
	/**
	 * 手动弃号执行方法
	 */
	function submitqihao(queue_num,branch,queuetype_id,work_date){
		Ext.MessageBox.confirm('提示','确定弃掉此队列号:'+queue_num+'?',function(id){
			if(id === 'yes'){
				var submitdata = {};
				submitdata['queue_num'] = queue_num;
				submitdata['branch'] = branch;
				submitdata['work_date'] = work_date;
				requestAjax('<%=basePath%>dataAnalysis/queueRealTime_delNum',submitdata,function(sRet){
					Ext.MessageBox.alert('提示','弃号成功',function(id){
						var submitDetail= {};
						submitDetail.branch = branch;
					    submitDetail.queuetype_id = queuetype_id;
					    requestAjax('<%=basePath%>/dataAnalysis/queueRealTime_details',submitDetail, function(sRet){
					      	detailStore.loadData(sRet.field1);
					      	requestAjax('<%=basePath%>dataAnalysis/queueRealTime_query?branch='+branch,{},function(sRet){
				  		        list = sRet.field1;
								refresh(list);
							});
					    });
					});
				});
			}
		});
	}
	  
	  function customerMessage(value, cellmeta, record, rowIndex, columnIndex, store){
	    return "<A href='javascript:void(0)' onclick=\"custmessage('"+record.data['custinfo_num']+"','"+record.data['custinfo_type']+"');return false;\">"+value+"</A>";
	  }
	  
	  //证件号码，证件类型查询客户信息
	  function custmessage(custinfo_num,custinfo_type){
	    alert("添加客户信息");
	  }
	  
	  //查看队列详情方法
	  //给定队列ID，通过网店id和队列id查看详情
	  function details(branch,queuetype_id){
	    detailsWindow = new SelfFormWindowSetWidth('detailsWindow','队列详细信息列表',670, 410, 630, 1, [630],[
			{
				colIndex:0,
				field:{
					xtype:'fieldset',
					title:'队列详细信息',
					layout:'column',
					items:[{
						columnWidth:1,
						items:[{
							xtype:'grid',
							store:detailStore,
		          			cm:detailColModel,
		          			height:300,
		          			iconCls:'icon-grid',
		          			stripeRows : true,
		          			doLayout:function(){this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true))},
		          			viewConfig: 
		          	        {
		          				columnsText:'列', 
		          	          	sortAscText:'升序', 
		          	          	sortDescText:'降序',
		          	          	getRowClass : function(record,rowIndex,rowParams,store){
		          	        		//record.data.en_queue_time 进队时间
		          	        		//record.data.de_queue_time 出队时间 de_queue_time
		          	        		//record.data.queuetype_id
		          	        		if(record.data.queue_status=='0'){//判断此队列未出队
		          	        			//将时间差算出来
		          	        			//var en = record.data.en_queue_time;
		          	        			//en = en.substr(0,2)+":"+en.substr(2,2)+":"+en.substr(4,2);
		          	        			//var en_queue_time = Date.parseDate(en,'H:i:s');//进队时间
										//var nowtime = new Date(new Date().setDate(new Date().getDate()));
		          	        			//var time = (nowtime.getTime()-en_queue_time.getTime())/1000/60;
		          	        			var time = record.data.time;
		          	        			var index = qmMonitorStore.find('queuetype_id',record.data.queuetype_id);
		          	        			//'waitnum_threshold','waittime_threshold'
		          	        			if(index==-1){//不存在此队列的监控数据,用默认的进行判断 defWaittimeThreshold
		          	        				if(time>=parseInt(defWaittimeThreshold)&&(defWaittimeThreshold!=0)){
		          	        					return 'x-grid-record-red';
		          	        				}else{
				          	        			return '';
				          	        		}
		          	        			}else{//存在监控数据
		          	        				var waittimeThreshold = qmMonitorStore.getAt(index).data.waittime_threshold;
		          	        				if(time>=parseInt(waittimeThreshold)&&(waittimeThreshold!=0)){
		          	        					return 'x-grid-record-red';
		          	        				}else{
		          	        					return '';
				          	        		}
		          	        			}
		          	        		}else{
		          	        			return 'x-grid-record-green';
		          	        		}  
	          	               	}
		          	        }
		        }]}
			]}}
	    ],[],'left',140);
	    
	    detailsWindow.open();
	    var submitDetail= {};
	    submitDetail.branch = branch;
	    submitDetail.queuetype_id = queuetype_id;
	    requestAjax('<%=basePath%>/dataAnalysis/queueRealTime_details',submitDetail, function(sRet){
	      detailStore.loadData(sRet.field1);
	    });
	    
	  }
	  
	  /**
	   *查询队列监控数据
	   */
	  function refresh(data){
	    Ext.getCmp('fieldset_refreah').removeAll(true);
	    realTimeStore.loadData(data);
			  //if(unitname.passField.value===undefined||unitname.passField.value.length==0){
			//	Ext.Msg.alert("信息提示：","请选择要监控的部门！");
			//	return;
			//  }
	    Ext.getCmp('fieldset_refreah').removeAll(true);
	    var items=[
			{columnWidth:1/5,html: '<a><br>机构名称</a>',cls:'cell_1'},
			{columnWidth:3/10,html: '<a><br>队列名称</a>',cls:'cell_1'},
			{columnWidth:1/10,html: '<a><br>等待人数</a>',cls:'cell_1'},
			{columnWidth:1/5,html: '<a><br>平均等待时间(分)</a>',cls:'cell_1'},
			{columnWidth:1/5,html: '<a><br>队列详情</a>',cls:'cell_1'}
	    ];
	    var x = 0;
	    realTimeStore.each(function(record){
	      if(x%2 == 0){
			var branch_name={columnWidth:1/5,html: '<a><br>'+branchName+'</a>',cls:'cell_2'};
			items.push(branch_name);
			var queuetype_name={columnWidth:3/10,html: '<a><br>'+record.get('queuetype_name')+'</a>',cls:'cell_2'};
			items.push(queuetype_name);
			//判断,超过预警则显示颜色变更,没超过则不变
  			var index = qmMonitorStore.find('queuetype_id',record.get('queuetype_id'));
  			if(index==-1){//不存在此队列的监控数据,用默认的进行判断 defWaitnumThreshold
  				if(parseInt(record.data['btqmsum'])>parseInt(defWaitnumThreshold)&&(defWaitnumThreshold!=0)){
  					var sum={columnWidth:1/10,html: '<a style="color:red"><br>'+record.get('btqmsum')+'</a>',cls:'cell_2'};
  				}else{
  					var sum={columnWidth:1/10,html: '<a><br>'+record.get('btqmsum')+'</a>',cls:'cell_2'};
  				}
  			}else{//存在监控数据waitnumThreshold
  				var waitnumThreshold = qmMonitorStore.getAt(index).data.waitnum_threshold;
  				if(parseInt(record.data['btqmsum'])>parseInt(waitnumThreshold)&&(waitnumThreshold!=0)){
  					var sum={columnWidth:1/10,html: '<a style="color:red"><br>'+record.get('btqmsum')+'</a>',cls:'cell_2'};
  				}else{
  					var sum={columnWidth:1/10,html: '<a><br>'+record.get('btqmsum')+'</a>',cls:'cell_2'};
  				}
  			}
			items.push(sum);
			if(record.get('averagewaittime')=='0'){
				var waittime={columnWidth:1/5,html: '<a><br>'+0+'</a>',cls:'cell_2'};
			}else{
				var time = record.get('averagewaittime')/60+'';
				var waittime={columnWidth:1/5,html: '<a><br>'+time.substr(0,time.indexOf('.',1)+3)+'</a>',cls:'cell_2'};
			}
			items.push(waittime);
			//if( record.get('queuedetails') == '0')
			//var details={columnWidth:1/5,html: '<a><br>无详细信息</a>',cls:'cell_2'};
			//else if( record.get('queuedetails') == '1')
			var details={columnWidth:1/5,html: "<a href='javascript:void(0)' onclick=\"details('"+record.get('branch')+"','"+record.get('queuetype_id')+"');return false;\"><br>查看详细信息</a>",cls:'cell_2'};
			items.push(details);
          }else{
			var branch_name={columnWidth:1/5,html: '<a><br>'+branchName+'</a>',cls:'cell_3'};
			items.push(branch_name);
			var queuetype_name={columnWidth:3/10,html: '<a><br>'+record.get('queuetype_name')+'</a>',cls:'cell_3'};
			items.push(queuetype_name);
			//判断,超过预警则显示颜色变更,没超过则不变
			var index = qmMonitorStore.find('queuetype_id',record.get('queuetype_id'));
  			if(index==-1){//不存在此队列的监控数据,用默认的进行判断 defWaitnumThreshold
  				if(parseInt(record.data['btqmsum'])>parseInt(defWaitnumThreshold)&&(defWaitnumThreshold!=0)){
  					var sum={columnWidth:1/10,html: '<a style="color:red"><br>'+record.get('btqmsum')+'</a>',cls:'cell_3'};
  				}else{
  					var sum={columnWidth:1/10,html: '<a><br>'+record.get('btqmsum')+'</a>',cls:'cell_3'};
  				}
  			}else{//存在监控数据waitnumThreshold
  				var waitnumThreshold = qmMonitorStore.getAt(index).data.waitnum_threshold;
  				if(parseInt(record.data['btqmsum'])>parseInt(waitnumThreshold)&&(waitnumThreshold!=0)){
  					var sum={columnWidth:1/10,html: '<a style="color:red"><br>'+record.get('btqmsum')+'</a>',cls:'cell_3'};
  				}else{
  					var sum={columnWidth:1/10,html: '<a><br>'+record.get('btqmsum')+'</a>',cls:'cell_3'};
  				}
  			}
			items.push(sum);
			if(record.get('averagewaittime')=='0'){
				var waittime={columnWidth:1/5,html: '<a><br>'+0+'</a>',cls:'cell_3'};
			}else{
				var time = record.get('averagewaittime')/60+'';
				var waittime={columnWidth:1/5,html: '<a><br>'+time.substr(0,time.indexOf('.',1)+3)+'</a>',cls:'cell_3'};
			}
			items.push(waittime);
			//if( record.get('queuedetails') == '0')
			//var details={columnWidth:1/5,html: '<a><br>无详细信息</a>',cls:'cell_3'};
			//else if( record.get('queuedetails') == '1')
			var details={columnWidth:1/5,html: "<a href='javascript:void(0)' onclick=\"details('"+record.get('branch')+"','"+record.get('queuetype_id')+"');return false;\"><br>查看详细信息</a>",cls:'cell_3'};
			items.push(details);
          }
          x = x+1;
        },this);
         Ext.getCmp('fieldset_refreah').title='队列情况';
		 Ext.getCmp('fieldset_refreah').add(items);
    	 Ext.getCmp('fieldset_refreah').doLayout();
      }
      /**
       *查询队列监控数据(总行、分行)
       */
	  function refresh1(data){
	    Ext.getCmp('fieldset_refreah').removeAll(true);
	    realTimeStore.loadData(data);
	    
	    var sum = realTimeStore.getCount();
	    
	    Ext.getCmp('fieldset_refreah').removeAll(true);
	   	var items1=[
			{columnWidth:1/4,html: '<a><br>机构名称</a>',cls:'cell_1'},
			{columnWidth:1/4,html: '<a><br>等待人数</a>',cls:'cell_1'},
			{columnWidth:1/4,html: '<a><br>平均等待时间(分)</a>',cls:'cell_1'},
			{columnWidth:1/4,html: '<a><br>详情</a>',cls:'cell_1'}
	    ];
	    var x = 0;
	    realTimeStore.each(function(record){
//	      if(x%2 == 0){
			var branch_name={columnWidth:1/4,html: '<a><br>'+record.get('branch_name')+'</a>',cls:'cell_2'};
			items1.push(branch_name);
   			if(parseInt(record.data['btqmsum'])>parseInt(defWaitnumThreshold)&&(defWaitnumThreshold!=0&&defWaitnumThreshold!='')){
  				var sum={columnWidth:1/4,html: '<a style="color:red"><br>'+record.get('btqmsum')+'</a>',cls:'cell_2'};
  			}else{
  				var sum={columnWidth:1/4,html: '<a><br>'+record.get('btqmsum')+'</a>',cls:'cell_2'};
  			}
			
			items1.push(sum);
			if(record.get('averagewaittime')=='0'){
				var waittime={columnWidth:1/4,html: '<a><br>'+0+'</a>',cls:'cell_2'};
			}else{
				var time = record.get('averagewaittime')/60+'';
				var waittime={columnWidth:1/4,html: '<a><br>'+time.substr(0,time.indexOf('.',1)+3)+'</a>',cls:'cell_2'};
			}
			items1.push(waittime);
			var details={columnWidth:1/4,html: "<a href='javascript:void(0)' onclick=\"getChildDetails('"+record.get('branch')+"','"+record.get('branch_name')+"');return false;\"><br>查看详细信息</a>",cls:'cell_2'};
			items1.push(details);
/*          }else{
			var branch_name={columnWidth:1/4,html: '<a><br>'+branchName+'</a>',cls:'cell_3'};
			items1.push(branch_name);
 			var sum={columnWidth:1/4,html: '<a><br>'+record.get('btqmsum')+'</a>',cls:'cell_3'};
			items1.push(sum);
			var waittime={columnWidth:1/4,html: '<a><br>'+0+'</a>',cls:'cell_3'};
			items1.push(waittime);
			var details={columnWidth:1/4,html: "<a href='javascript:void(0)' onclick=\"getChildDetails('"+record.get('branch')+"');return false;\"><br>查看详细信息</a>",cls:'cell_3'};
			items1.push(details);
          }
*/          x = x+1;
        },this);
         Ext.getCmp('fieldset_refreah').title='机构情况';
		 Ext.getCmp('fieldset_refreah').add(items1);
   		  Ext.getCmp('fieldset_refreah').doLayout();				
      }
      function  getChildDetails(branch,branch_name){
      	  Ext.getCmp('unitname1').setPassValue(branch);
      	  Ext.getCmp('unitname1').setValue(branch_name);
      	  branch = branch;
      	 	var unitname=Ext.getCmp('unitname1');
			  branchName=unitname.value;
	        	dataStore.load({
	        		params:{'branch':branch,'query_rules':'0'},
	        		callback:function(r,options,success){
						if(success){
       						qmMonitorStore.load({
       			        		params:{'branch':branch},
       			        		callback:function(r,options,success){
									if(success){
				       		       		var submitData = {};
										requestAjax('<%=basePath%>dataAnalysis/queueRealTime_query?branch='+branch,submitData,function(sRet){
							  		        list = sRet.field1;
							  		        haschild = sRet.field2;
											if(haschild=='1'){
												refresh1(list);
											}else{
												refresh(list);
											}
										});
       		       					}
       							},
       							scope:this
       			        	});
       					}
					},
					scope:this
	        	});

      }
	    function refresh2(){
	    	var time = 5000;
	    	var branch = Ext.getCmp('unitname1').passField.value;
			var submitData = {};
			requestAjax('<%=basePath%>dataAnalysis/queueRealTime_query?branch='+branch,submitData,function(sRet){
				list = sRet.field1;
				haschild = sRet.field2;
				if(haschild=='1'){
					refresh1(list);
					setTimeout('refresh2()',12000);
				}else{
					refresh(list);
					setTimeout('refresh2()',6000);
				}
			},false	);
   	    	
	    }
	  function loadPage(){
	    var clientWidth = document.body.clientWidth;
	    var clientHeight = document.body.clientHeight;
	    
	    var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '队列实时监控', 120, 1,
	      [
	      {rowIndex:0, field:{xtype : 'combotree', name:'unitname1',id:'unitname1',allowBlank : false,fieldLabel:'监控机构',passName:'unitid',tree: treepanel_a,width:200,allowUnLeafClick:false,value:<%="'" + user.getUnit().getUnitname() + "'"%>}}
	      ],[
	        {iconCls: "x-image-query",text:'<s:text name="common.button.query"/>'}
	      ],onButtonClicked
	    );
	    conditionPanel.open();
	    
	    var panel = new Ext.Panel({
			layout : 'absolute',
			frame:true,
			region:'center',
			height:clientHeight-conditionPanel.getHeight()+44,
			applyTo :'panel',
			items  : [{
				layout:'form', 
				bodyStyle:'padding:0 0 0 0',
				items:[{
				  	width:clientWidth-15,
				  	height:clientHeight-conditionPanel.getHeight()-44,
				  	xtype:'fieldset',
				  	layout:'column',
				  	autoScroll : true,
				  	id:'fieldset_refreah'
				}]
			}]
	    });
	    function onButtonClicked(btn_index){
	      switch(btn_index){
	        case 0:
 //     		 String bank_level = treepanel_a.getNodeById(branch).getPath();
	          var unitname=Ext.getCmp('unitname1');
			  //if(unitname.passField.value===undefined||unitname.passField.value.length==0){
			//	Ext.Msg.alert("信息提示：","请选择要监控的部门！");
			//	return;
			//  }
			
			  branch=unitname.passField.value;
			  //branch='001001';
			  branchName=unitname.value;
	        	dataStore.load({
	        		params:{'branch':branch,'query_rules':'0'},
	        		callback:function(r,options,success){
						if(success){
       						qmMonitorStore.load({
       			        		params:{'branch':branch},
       			        		callback:function(r,options,success){
									if(success){
				       		       		var submitData = {};
										requestAjax('<%=basePath%>dataAnalysis/queueRealTime_query?branch='+branch,submitData,function(sRet){
							  		        list = sRet.field1;
							  		        haschild = sRet.field2;
											if(haschild=='1'){
												refresh1(list);
											}else{
												refresh(list);
											}
										});
       		       					}
       							},
       							scope:this
       			        	});
       					}
					},
					scope:this
	        	});
			  
	        break;
	      }
	    }
	    
//---------------------------------------------------------------
	    function buildLayout(){
	      var viewport = new Ext.Viewport({
	        layout: 'border',
	        items : [conditionPanel.toolbarwindow,panel]
	      });
	    };
	    buildLayout();
	    Ext.getCmp('unitname1').setPassValue(branch);
	  }
	  setTimeout('refresh2()',5000);
	  
	</script>
	</head>
	<body scroll="no">
		<div id="queryConditionPanel"></div>
		<div id="panel"></div>
		<div id="detailsWindow"></div>
		<dir id="adjustWindow"></dir>
	</body>
</html>
