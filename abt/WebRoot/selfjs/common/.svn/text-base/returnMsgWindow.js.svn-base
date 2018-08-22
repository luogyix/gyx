var returnWindow ;

var returnstatusStore = new Ext.data.JsonStore({
            fields : ['dictValue','dictValueDesc'],
            url    : 'BasePackage/common_getSystemDictionaryItem?item_id=status',
            root   : 'field1', autoLoad:true
        });

function openReturnWindow(sRet){
	
	if(returnWindow==null){
		
			 d = document.createElement("div");
		     d.id = "returnWindow";
		     document.getElementsByTagName('body')[0].appendChild(d);
			 returnWindow = new SelfFormWindow('returnWindow', '<s:text name="common.info.title"/>', 330, 405, 290, 1,
				[
					{colIndex:0, labelAlign:'left',field:{
							xtype:'fieldset',
							title:'业务处理平台返回结果',
							baseCls:'x-image-web-result',
							height:345,
							items:[
										{bodyStyle:"padding:10 10 10 10%",labelAlign:'left',layout:'form',items:[{xtype:'displayfield',fieldLabel:'受理状态',			 id:'result_status',				name:'status'					}]},
										{bodyStyle:"padding:10 10 10 10%",labelAlign:'left',layout:'form',items:[{xtype:'displayfield',fieldLabel:'处理码',				 id:'result_dealcode',			name:'dealcode'				}]},
										{bodyStyle:"padding:10 10 10 10%",labelAlign:'left',layout:'form',items:[{xtype:'displayfield',fieldLabel:'处理信息',			 id:'result_dealmsg',			name:'dealmsg'				}]},
										{bodyStyle:"padding:10 10 10 10%",labelAlign:'left',layout:'form',items:[{xtype:'displayfield',fieldLabel:'支付平台日期',	 id:'result_workdate',			name:'workdate'				}]},
										{bodyStyle:"padding:10 10 10 10%",labelAlign:'left',layout:'form',items:[{xtype:'displayfield',fieldLabel:'支付平台流水号',id:'result_agentserialno',name:'agentserialno'	}]},
										{bodyStyle:"padding:10 10 10 10%",labelAlign:'left',layout:'form',items:[{xtype:'displayfield'}]},
										
										{bodyStyle:"padding:10 49% 10 36%",	layout:'table',	items:[{xtype : 'button',text:'关闭',width:70,  handler:function(){returnWindow.close();}}]}
					]}}			
				]		
			);
	}
	returnWindow.open();	
	if (sRet!=null) {
        var status=(sRet.field1.status==null||sRet.field1.status=="null")?"":sRet.field1.status;
        var map=returnstatusStore.getAt(returnstatusStore.findExact('dictValue',status));
	    if(map !=null){
	        status= map.get('dictValueDesc'); 
        }
		Ext.getCmp('result_status').setValue(status);
		Ext.getCmp('result_dealcode').setValue((sRet.field1.dealcode==null||sRet.field1.dealcode=="null")?"":sRet.field1.dealcode);
		Ext.getCmp('result_dealmsg').setValue((sRet.field1.dealmsg==null||sRet.field1.dealmsg=="null")?"":sRet.field1.dealmsg);
		Ext.getCmp('result_workdate').setValue((sRet.field1.workdate==null||sRet.field1.workdate=="null")?"":sRet.field1.workdate);
		Ext.getCmp('result_agentserialno').setValue((sRet.field1.agentserialno==null||sRet.field1.agentserialno=="null")?"":sRet.field1.agentserialno);
	}
}