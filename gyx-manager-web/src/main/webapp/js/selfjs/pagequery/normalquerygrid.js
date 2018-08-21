//重写CheckboxSelectionModel添加
Ext.grid.ExtCheckboxSelectionModel = Ext.extend(Ext.grid.CheckboxSelectionModel, {   
    hd : null,   
    onHdMouseDown : function(e, t){   
        Ext.grid.ExtCheckboxSelectionModel.superclass.onHdMouseDown.call(this, e, t);   
        if (this.hd == null && t.className == 'x-grid3-hd-checker') { 
            this.hd = t.parentNode;   
            this.on('rowdeselect', this.offHdChecker, this);
            this.on('selectionchange', function(sm_) { //解决全选去不掉问题(lich)
            	var hd = Ext.fly(this.hd); 
            	if (this.grid.getStore().getCount()!=0 && 
            			sm_.getCount() == this.grid.getStore().getCount()) { 
            		hd.addClass('x-grid3-hd-checker-on'); 
            	} else { 
            		hd.removeClass('x-grid3-hd-checker-on'); 
            	}
            }); 
        }   
    },   
    offHdChecker : function() {   
        if (this.hd == null)   
            return;   
        var hd = Ext.fly(this.hd);   
        var isChecked = hd.hasClass('x-grid3-hd-checker-on');   
        if(isChecked)   
            hd.removeClass('x-grid3-hd-checker-on');   
    },  
    setHeaderChecked:function(check){//此函数没用
       var temp=Ext.DomQuery.selectNode('.x-grid3-hd-checker');   
       var hd = Ext.fly(temp);   
       var isChecked = hd.hasClass('x-grid3-hd-checker-on');   
       if(isChecked){   
           hd.removeClass('x-grid3-hd-checker-on');   
       }   
       if(check){   
           hd.addClass('x-grid3-hd-checker-on');   
       }   
   } 
 }); 
//重写CheckboxSelectionModel添加完毕
 Ext.ns('Ext.ux.grid');   
 
if (!Ext.grid.GridView.prototype.templates) {    
    Ext.grid.GridView.prototype.templates = {};    
}    
Ext.grid.GridView.prototype.templates.cell =  new  Ext.Template(    
     '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>' ,    
     '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>' ,    
     '</td>'    
);  

PageQuery = function(whetherpage, divid, width, height, url, fields, colM, pageToolText, columnSortFunc,tbar,plugins){
	this.divid = divid;
	this.url = url;
	this.fields = fields;
	this.total=-1;
	for(var i=1;i<colM.length;i++){
		var col = colM[i];
		if(i==0&&col.header!=null){
			colM[0]=new Ext.grid.RowNumberer({header:'',width:30,locked: true});
		}
		if(col.translateField !== undefined){
			col.renderer = this.translatecolumn;
		}else{
			if(col.renderType !== undefined){
				col.renderer = this.rendertype;
			}
		}
	}
	
	this.rsM = new Ext.grid.ExtCheckboxSelectionModel({
        singleSelect: false
    });
	colM[1] = this.rsM;
	colM[0]=new Ext.grid.RowNumberer({header:'',width:30,locked: true});
	this.colMData = colM;
	var columnModel = new Ext.grid.ColumnModel(colM);
	
	this.dataStore = new Ext.data.JsonStore({fields : fields});	
	var div = document.getElementById(this.divid);
	this.pagingGrid = new Ext.grid.EditorGridPanel({
		region : 'center',
	    cm     : columnModel,
		sm     : this.rsM,
		store  : this.dataStore,
		renderTo:this.divid,
		stripeRows : true,
		monitorResize: true,
		tbar:tbar,		
		plugins: plugins,
		doLayout: function() { 
			this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true))}
	});
	
	
	if(PageQuery.objects === undefined){
		PageQuery.objects = new Array();
	}
	PageQuery.objects.push(this);
}

PageQuery.TYPE = {};
PageQuery.TYPE.MONEY = 1;
PageQuery.TYPE.DATE = 2;
PageQuery.TYPE.TIME = 3;
PageQuery.TYPE.DATETIME = 4;
PageQuery.TYPE.MINUTE=5;


PageQuery.prototype.getSelectedObjects = function(fields){
	var records = this.rsM.getSelections();
	if(records.length === 0){
		return undefined;
	}else{
		//return records;
		var submitdata = new Array();
		for(var i=0;i<records.length;i=i+1){
			var data = {};
			for(var j=0; j<fields.length;j=j+1){
				data[fields[j]] = records[i].get(fields[j]);
			}
			submitdata.push(data);
		}
		return submitdata;
	}
}
PageQuery.prototype.getGridCM = function(){
	return this.pagingGrid.getColumnModel();
}

PageQuery.prototype.getSelectedRecords = function(){
	var records = this.rsM.getSelections();
	if(records.length === 0){
		return undefined;
	}else{
		return records;
	}
}
/*得到grid中的store   lich*/
PageQuery.prototype.getStore = function(){
	return this.dataStore;
}
/*************end***********/

PageQuery.prototype.translatecolumn = function(value, metadata, record, rowIndex, colIndex, store){
	var obj;
	for(var i=0;i<PageQuery.objects.length;i=i+1){
		if(PageQuery.objects[i].dataStore === store){
			obj = PageQuery.objects[i];
		}
	}
	var colm = obj.colMData;
	var col = colm[colIndex];
	return record.get(col.translateField);
}

PageQuery.prototype.rendertype = function(value, metadata, record, rowIndex, colIndex, store){
	var obj;
	for(var i=0;i<PageQuery.objects.length;i=i+1){
		if(PageQuery.objects[i].dataStore === store){
			obj = PageQuery.objects[i];
		}
	}
	var colm = obj.colMData;
	var col = colm[colIndex];
	if(col.renderType === PageQuery.TYPE.MONEY){
		return fmoney(value);
	}else if(col.renderType === PageQuery.TYPE.DATE){
		return fdate(value);
	}else if(col.renderType === PageQuery.TYPE.TIME){
		return ftime(value);
	}else if(col.renderType === PageQuery.TYPE.DATETIME){
		return fDateTime(value);
	}else if(col.renderType === PageQuery.TYPE.MINUTE){
		return fminute(value);
	}
	return '';
}

PageQuery.prototype.queryPage = function(query_obj) {
	this.submit_obj = query_obj;
	
	var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"请稍候...", removeMask:true});
	myMask.show();
	
	Ext.Ajax.timeout=90000000;//处理请求超时(lich)90000秒
    Ext.Ajax.request({
		url : this.url, jsonData : this.submit_obj, scope:this, params:{requesttype:'ajax'},
		callback : function(options, success, response){
    		if(success){
    			var rawObj = Ext.util.JSON.decode(response.responseText);
    			var jsonObj = Ext.util.JSON.decode(rawObj.actionresult);
    			if(jsonObj.result){
    				var resultList = jsonObj["field1"];
    				this.dataStore.loadData(resultList,false);
    				myMask.hide();
    			}else{
    				myMask.hide();
    				var tishimessage = jsonObj.message;
    				var objectSign = tishimessage.substring(0,1);
    				var tsm = tishimessage.substring(1,tishimessage.length);
    				if(objectSign == 0){
    				    Ext.MessageBox.alert('',tsm,function(id){
								var ss = window.location.href;
		                        var arr = ss.split("/");
	                            parent.window.location.href(arr[0]+"/"+arr[1]+"/"+arr[2]+"/"+arr[3]);
							});
    				}else{
    				    Ext.MessageBox.alert('',tishimessage);
    				}
    			}
    		}else{//ajax error
    			myMask.hide();
    		}
    	}
	});
    
	
}

PageQuery.prototype.setData = function(data){
	this.dataStore.loadData(data,false);
}

