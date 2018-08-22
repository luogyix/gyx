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
            	if (this.grid.getStore().getCount()!=0 && sm_.getCount() == this.grid.getStore().getCount()) { 
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
    }
}); 
Ext.reg('selfcheckbox', Ext.grid.ExtCheckboxSelectionModel);

PagingQueryTreeGrid = function(conf){
	this.divid = conf.divid;
	this.dataStore = conf.store;
    var colM=conf.colM;
	var div = document.getElementById(this.divid);
	this.rsM =  new Ext.grid.ExtCheckboxSelectionModel({
        singleSelect: false,
        checkOnly:true
    });
	this.selfPagingTool = new Ext.ux.maximgb.tg.PagingToolbar({
		store: this.dataStore,
		displayInfo: true,
		pageSize: 20
	});
	
	for(var i=1;i<colM.length;i++){
		var col = colM[i];
		colM[0]=new Ext.grid.RowNumberer({header:'',width:30,locked: true});
		if(col.translateField !== undefined){
			col.renderer = this.translatecolumn;
		}else{
			if(col.renderType !== undefined){
				col.renderer = this.rendertype;
			}
		}
	}
	this.colMData = colM;
	var columnModel = new Ext.grid.ColumnModel(colM);
    conf.sm=this.rsM;
    conf.region="center";
    conf.stripeRows=true;
    conf.cm=columnModel;
    conf.bbar=this.selfPagingTool;
    conf.viewConfig = {
            sortAscText : '升序',
            sortDescText : '降序',
            columnsText : '列定义'
        }
    
	this.pagingTreeGrid = new Ext.ux.maximgb.tg.GridPanel(conf);
	
	if(PagingQueryTreeGrid.objects === undefined){
		PagingQueryTreeGrid.objects = new Array();
	}
	PagingQueryTreeGrid.objects.push(this);
}

PagingQueryTreeGrid.TYPE = {};
PagingQueryTreeGrid.TYPE.MONEY = 1;
PagingQueryTreeGrid.TYPE.DATE = 2;
PagingQueryTreeGrid.TYPE.TIME = 3;
PagingQueryTreeGrid.TYPE.DATETIME = 4;
PagingQueryTreeGrid.TYPE.MINUTE=5;


PagingQueryTreeGrid.prototype.getSelectedObjects = function(fields){
	var records = this.rsM.getSelections();
	if(records.length === 0){
		return undefined;
	}else{
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

PagingQueryTreeGrid.prototype.getSelectedRecords = function(){
	var records = this.rsM.getSelections();
	if(records.length === 0){
		return undefined;
	}else{
		return records;
	}
}

PagingQueryTreeGrid.prototype.getStore = function(){
	return this.dataStore;
}

PagingQueryTreeGrid.prototype.translatecolumn = function(value, metadata, record, rowIndex, colIndex, store){
	var obj;
	for(var i=0;i<PagingQueryTreeGrid.objects.length;i=i+1){
		if(PagingQueryTreeGrid.objects[i].dataStore === store){
			obj = PagingQueryTreeGrid.objects[i];
		}
	}
	var colm = obj.colMData;
	var col = colm[colIndex];
	return record.get(col.translateField);
}

PagingQueryTreeGrid.prototype.rendertype = function(value, metadata, record, rowIndex, colIndex, store){
	var obj;
	for(var i=0;i<PagingQueryTreeGrid.objects.length;i=i+1){
		if(PagingQueryTreeGrid.objects[i].dataStore == store){
			obj = PagingQueryTreeGrid.objects[i];
		}
	}
	var colm = obj.colMData;
	var col = colm[colIndex];
	if(col.renderType ==PagingQueryTreeGrid.TYPE.MONEY){
		if(value==undefined ||value=="")
			return;
		n=2//两位小数
		s=value/100;//取值
		n = n > 0 && n <= 20 ? n : 2;  
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
		var l = s.split(".")[0].split("").reverse(),  
		r = s.split(".")[1];  
		t = "";  
		for(i = 0; i < l.length; i ++ ) {  
		    t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
		}  
		return t.split("").reverse().join("") + "." + r; 
		    //(value / 100).toString();
		
	}else if(col.renderType == PagingQueryTreeGrid.TYPE.DATE){
		if(value==undefined || value=="")
			return;
		var str = value.toString();
	    return str.substring(0,4) + '-' + str.substring(4,6) + '-' + str.substring(6,8);
	}else if(col.renderType == PagingQueryTreeGrid.TYPE.TIME){
	    if(value==undefined || value=="")
			return;
		var str = value.toString();
		return str.substring(0,2) + ':' + str.substring(2,4) + ':' + str.substring(4,6);
	}else if(col.renderType == PagingQueryTreeGrid.TYPE.DATETIME){
	    if(value==undefined || value=="")
			return;
		var str = value.toString();
		return str.substring(0,4) + '-' + str.substring(4,6) + '-' + str.substring(6,8) + ' ' + str.substring(8,10) + ':' + str.substring(10,12) + ':' + str.substring(12,14);
	    
	}else if(col.renderType == PagingQueryTreeGrid.TYPE.MINUTE){
	    if(value==undefined || value=="")
			return;
		return (value / 60).toString();
		
	}
	return '';
}

PagingQueryTreeGrid.prototype.queryPage = function(query_obj) {
	var bbar = this.selfPagingTool;
	Ext.apply(bbar.conditionParams,query_obj);
	bbar.doLoad(0);
}

/***********end************/
PagingQueryTreeGrid.prototype.setData = function(data){
	this.dataStore.loadData(data,false);
}

//遍历选择
PagingQueryTreeGrid.prototype.chooseit = function(rowIndex, columnIndex){
	var store = this.dataStore;
	var sm = this.rsM;
	var record = store.getAt(rowIndex);
	var ck=document.getElementById('cb'+record.get('_id'));
	if(ck.checked){
		if(record.get('_is_leaf'))
			sm.selectRow(rowIndex,true);
		checkAllParent(record);
	}else{
		sm.deselectRow(rowIndex);
		unChooseAllParent(store.getAt(rowIndex));
	}
	//选中父节点，则将其下面所有的子节点都选中
	chooseAllChildren(store.getAt(rowIndex).get('_id'));
	function chooseAllChildren(_id){
		store.each(function(record){
			if(_id == record.get('_parent')){
				var row = store.indexOf(record);
	            var cck = document.getElementById('cb'+record.get('_id'));
				if(ck.checked){
					if(record.get('_is_leaf'))
						sm.selectRow(row,true);
	    			cck.checked = true;
	    		}else{
	    			sm.deselectRow(row);
	    			cck.checked = false;
	    		}
				if(!record.get('_is_leaf'))
					chooseAllChildren(record.get('_id'));//遍历选择
			}
		});
	}
	
	//如果子节点有一个取消选择，则将其父节点也取消选择
	function unChooseAllParent(record){
		var parent = store.getNodeParent(record);
		if(parent === undefined)
			return;
		var prow = store.indexOf(parent);
		var pck = document.getElementById('cb'+parent.get('_id'));
		if(pck.checked){
			pck.checked = false;
			sm.deselectRow(prow);
		}
		unChooseAllParent(parent);
	}
	
	//如果子节点全选中则其父节点也选中
	function checkAllParent(record){
		var parent = store.getNodeParent(record);
		if(parent != undefined){
			var pck = document.getElementById('cb'+parent.get('_id'));
			if(!pck.checked){
				var prow = store.indexOf(parent);
				var children = store.getNodeChildren(parent);
				var pischecked = true;
				for (i = 0, len = children.length; i < len; i++) {
					var cck = document.getElementById('cb'+children[i].get('_id'));
					if(!cck.checked){
						pischecked = false;
						break;
					}
		        }
				if(pischecked){
					//sm.selectRow(prow,true);
					pck.checked = true;
				}
				checkAllParent(parent);
			}
		}
	}
}
/*
function chooseit(rowIndex, columnIndex){
	var store = this.dataStore;
	var ck=document.getElementById('cb'+store.getAt(rowIndex).get('_id'));
	if(ck.checked){
		sm.selectRow(rowIndex,true);
	}else{
		sm.deselectRow(rowIndex);
		unChooseAllParent(store.getAt(rowIndex));
	}
	chooseAllChildren(store.getAt(rowIndex));
	function chooseAllChildren(record){
		if(!record.get('_is_leaf')){
			var children = store.getNodeChildren(record);
			for (i = 0, len = children.length; i < len; i++) {
	            var row = store.indexOf(children[i]);
	            var cck = document.getElementById('cb'+store.getAt(row).get('_id'));
	            if(ck.checked){
	    			sm.selectRow(row,true);
	    			cck.checked = true;
	    		}else{
	    			sm.deselectRow(row);
	    			cck.checked = false;
	    		}
				chooseAllChildren(children[i]);
	        }
		}
	}
	function unChooseAllParent(record){
		var parent = store.getNodeParent(record);
		if(parent === undefined)
			return;
		var prow = store.indexOf(parent);
		var pck = document.getElementById('cb'+parent.get('_id'));
		if(pck.checked){
			pck.checked = false;
			sm.deselectRow(prow);
		}
		unChooseAllParent(parent);
	}
}*/