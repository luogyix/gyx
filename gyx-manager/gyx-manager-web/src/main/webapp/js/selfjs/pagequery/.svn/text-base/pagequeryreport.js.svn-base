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
    setHeaderChecked:function(check){   
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
if (!Ext.grid.GridView.prototype.templates) {    
    Ext.grid.GridView.prototype.templates = {};    
}    
Ext.grid.GridView.prototype.templates.cell =  new  Ext.Template(    
     '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>' ,    
     '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>' ,    
     '</td>'    
); 
PageQuery = function(whetherpage, divid, width, height, url, fields, colM, pageToolText, columnSortFunc){
	this.divid = divid;
	this.url = url;
	this.fields = fields;
	for(var i=1;i<colM.length;i++){
		var col = colM[i];
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
	
	this.colMData = colM;
	var columnModel = new Ext.grid.ColumnModel(colM);
	
	this.dataStore = new Ext.data.JsonStore({fields : fields});
	
	pageToolText = pageToolText.replace(/\<\$/g,'{');
	pageToolText = pageToolText.replace(/\$\>/g,'}');
	var pagingToolParamObj = Ext.util.JSON.decode(pageToolText);
	pagingToolParamObj.displayMsg = pagingToolParamObj.displayMsg.replace(/\<\%\$/g,'{');
	pagingToolParamObj.displayMsg = pagingToolParamObj.displayMsg.replace(/\$\%\>/g,'}');
	
	this.selfPagingTool = new SelfPagingToolbar({
		url : this.url,
		displayMsg : pagingToolParamObj['displayMsg'],
	    emptyMsg : pagingToolParamObj['emptyMsg'],
	    beforePageText : pagingToolParamObj['beforePageText'],
	    afterPageText : pagingToolParamObj['afterPageText'],
	    firstText : pagingToolParamObj['firstText'],
	    prevText : pagingToolParamObj['prevText'],
	    nextText : pagingToolParamObj['nextText'],
	    lastText : pagingToolParamObj['lastText'],
	    pageData : pagingToolParamObj['pageData'],
	    whetherpage : whetherpage,
	    addRecordText : pagingToolParamObj['addRecordText'],
	    deleteRecordText : pagingToolParamObj['deleteRecordText'],
		store : this.dataStore,
		displayInfo : true,
		parentComp  : this,
		//buttonsComp : buttons,
		jsonData : {}
		//,onButtonClicked : buttonClickFunction
	});
	var div = document.getElementById(this.divid);
	
	this.pagingGrid = new Ext.grid.GridPanel({
		bbar   : this.selfPagingTool,
	    cm     : columnModel,
		sm     : this.rsM,
		store  : this.dataStore,
		renderTo:this.divid,
		stripeRows : true,
		stripeRows : true,
		monitorResize: true,
		doLayout: function() { 
			this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true))}
	});
	
	//Ext.grid.GridPanel.prototype.doLayout.call(this);
	
	this.pagingGrid.on('sortchange',function(field,sort){
		var sortStr = sort.field;
		if(columnSortFunc !== undefined){
			sortStr = columnSortFunc(sort);
		}
		if(this.submit_obj !== undefined){
			this.submit_obj['sortString'] = sortStr + ' ' + sort.direction;
			this.selfPagingTool.resetPageInfo();
			this.selfPagingTool.doLoad(1);
		}
	},this);
	
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
	this.selfPagingTool.resetPageInfo();
	this.selfPagingTool.doLoad(1);
}
/*指定跳到那一页   lich*/
PageQuery.prototype.queryPageByNumber = function(query_obj,num) {
	this.submit_obj = query_obj;
	this.selfPagingTool.resetPageInfo();
	this.selfPagingTool.doLoad(num);
}
/***********end************/
PageQuery.prototype.setData = function(data){
	this.dataStore.loadData(data,false);
}

PageQuery.prototype.reset = function(data){
	this.selfPagingTool.resetPageInfo();
	this.dataStore.loadData({},false);
}


SelfPagingToolbar = Ext.extend(Ext.Toolbar, {
    
    //pageSize: 20,
    displayMsg : '正在显示第{0} - {1}条记录',
    emptyMsg : '对不起，没有您需要的数据',
    beforePageText : "第",
    afterPageText : "页",
    firstText : "第一页",
    prevText : "上一页",
    nextText : "下一页",
    lastText : "最后一页",
    refreshText : "Refresh",
    paramNames : {start: 'start', limit: 'limit', total: 'total', resultList:'field1', resultObject:'field2'},
    pageData   : [[10,'每页10条记录'],[15,'每页15条记录'],[20,'每页20条记录']],
    jsonData   : {},
    addRecordText : '',
    deleteRecordText : '',
    url        : '',
    whetherpage : true,
    parentComp  : {},
    buttonsComp : [],
    // private
    initComponent : function(){
        this.addEvents('change', 'beforechange');
        SelfPagingToolbar.superclass.initComponent.call(this);
        this.bind(this.store);
        var tempPageData = new Array();
        for(var i=0;i<this.pageData.length; i=i+1){
        	if(this.whetherpage){
        		if(this.pageData[i][0] !== 0){
        			tempPageData.push(this.pageData[i]);
        		}
        	}else{
        		if(this.pageData[i][0] === 0){
        			tempPageData.push(this.pageData[i]);
        		}
        	}
        }
        this.pageData = tempPageData;
        this.pageDataStore = new Ext.data.SimpleStore({
        	fields : [{name:'value',type:'int'},{name:'name'}],
        	data   : this.pageData
        });
    },

    // private
    onRender : function(ct, position){
        SelfPagingToolbar.superclass.onRender.call(this, ct, position);
        this.first = this.addButton({
            tooltip: this.firstText,
            iconCls: "x-tbar-page-first",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["first"])
        });
        this.prev = this.addButton({
            tooltip: this.prevText,
            iconCls: "x-tbar-page-prev",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["prev"])
        });
        this.addSeparator();
        this.add(this.beforePageText);
        this.field = new Ext.form.NumberField({
            cls: 'x-tbar-page-number',
            allowDecimals: false,
            allowNegative: false,
            enableKeyEvents: true,
            selectOnFocus: true,
            submitValue: false,
            disabled:true
        })
        this.field.on("keydown", this.onPagingKeydown, this);
        this.field.on("focus", function(){this.el.dom.select();});
        this.field.on("blur", this.onPagingBlur, this);
        this.field.setHeight(18);
        this.addField(this.field);
        this.add(this.afterPageText);
        this.addSeparator();
        this.next = this.addButton({
            tooltip: this.nextText,
            iconCls: "x-tbar-page-next",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["next"])
        });
        this.last = this.addButton({
            tooltip: this.lastText,
            iconCls: "x-tbar-page-last",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["last"])
        });
        this.addSeparator();
        this.pageCombo = new Ext.form.ComboBox({
        	store :  this.pageDataStore,
        	displayField:'name',
        	valueField:'value',
        	value : this.pageData[0][0],
        	mode:'local',
        	editable : false,
        	triggerAction:'all',
        	width : 120
        });
        this.pageCombo.on('change',this.onComboChanged,this);
        if(!this.whetherpage){
        	this.pageCombo.disable();
        }
        this.addField(this.pageCombo);
        
        if(this.buttonsComp !== undefined){
        	for(var i=0;i<this.buttonsComp.length;i=i+1){
        		this.addSeparator();
        		var btnconfig = this.buttonsComp[i];
        		btnconfig.handler = this.onClick.createDelegate(this, ["buttonClick",i+1]);
        		this.addButton(btnconfig);
        	}
        }
        
        this.jsonData[this.paramNames.start] = 1;
        this.jsonData[this.paramNames.limit] = this.pageData[0][0];

        if(this.displayInfo){
            this.displayEl = Ext.fly(this.el.dom).createChild({cls:'x-paging-info'});
        }
        if(this.dsLoaded){
            this.onLoad.apply(this, this.dsLoaded);
        }
    },
    
    onComboChanged : function(field,newvalue,oldvalue){
    	this.jsonData[this.paramNames.limit] = newvalue;
    	//this.doLoad(1);多余，会产生运行错误
    },

    // private
    updateInfo : function(){
        if(this.displayEl){
            var count = this.store.getCount();
        	var startrow = (this.jsonData[this.paramNames.start] - 1) * this.jsonData[this.paramNames.limit];
        	var endrow   = startrow + count;
        	startrow = startrow + 1;
            var msg = count == 0 ?
                this.emptyMsg :
                String.format(
                    this.displayMsg,
                    startrow, endrow);
            this.displayEl.update(msg);
        }
    },

    // private
    onLoad : function(store, r, o){
        if(!this.rendered){
            this.dsLoaded = [store, r, o];
            return;
        }
        
        if(this.jsonData[this.paramNames.start] == -1){
        	this.jsonData[this.paramNames.start] = this.jsonData[this.paramNames.total];
        }
        
        this.field.el.dom.value = this.jsonData[this.paramNames.start];
        this.first.setDisabled(this.jsonData[this.paramNames.start] == 1);
        this.prev.setDisabled(this.jsonData[this.paramNames.start] == 1);
        
        if(this.jsonData[this.paramNames.total] == 0){
        	var storeCount = this.store.getTotalCount(); 
        	if(storeCount < this.jsonData[this.paramNames.limit]){
        		this.next.setDisabled(true);
        		this.last.setDisabled(true);
        	}else{
        		this.next.setDisabled(false);
        		this.last.setDisabled(false);
        	}
        }else{
        	this.next.setDisabled(true);
        	this.last.setDisabled(true);
        }
        
        this.updateInfo();
        //this.fireEvent('change', this, d);
        this.fireEvent('change', this);
    },

    // private
    onLoadError : function(){
        if(!this.rendered){
            return;
        }
    },

    // private
    readPage : function(d){
        var v = this.field.dom.value, pageNum;
        if (!v || isNaN(pageNum = parseInt(v, 10))) {
            this.field.dom.value = d.activePage;
            return false;
        }
        return pageNum;
    },

    //private
    onPagingBlur: function(e){
        this.field.el.dom.value = this.jsonData[this.paramNames.start];
    },

    // private
    onPagingKeydown : function(field,e){
        var k = e.getKey();
        if (k == e.RETURN) {
            e.stopEvent();
            var newvalue = this.field.el.dom.value;
            if(newvalue === '' || newvalue.search(/[^0-9]/) >= 0){
            	this.field.el.dom.value = this.jsonData[this.paramNames.start];
            }else{
            	var tmp = newvalue.toString().replace(/0*/,'');
            	this.doLoad(parseInt(tmp));
            }
        }
    },

    // private
    beforeLoad : function(){},

    // private
    doLoad : function(start){
    	this.jsonData[this.paramNames.start] = start;
		this.parentComp.submit_obj[this.paramNames.start] = this.jsonData[this.paramNames.start];
		this.parentComp.submit_obj[this.paramNames.limit] = this.jsonData[this.paramNames.limit];
		var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"Please wait...", removeMask:true});
		myMask.show();
		Ext.Ajax.timeout=90000000;//处理请求超时(lich)90000秒
        Ext.Ajax.request({
			url : this.url, jsonData : this.parentComp.submit_obj, scope:this, params:{requesttype:'ajax'},
			callback : function(options, success, response){
        		if(success){
        			var rawObj = Ext.util.JSON.decode(response.responseText);
        			var jsonObj = Ext.util.JSON.decode(rawObj.actionresult);
        			if(jsonObj.result){
        				var resultList = jsonObj[this.paramNames.resultList];
        				var resultObject = jsonObj[this.paramNames.resultObject];
        				this.jsonData[this.paramNames.total] = resultObject[this.paramNames.total];
        				this.store.loadData(resultList,false);
        				this.field.enable();
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
    },

    changePage: function(page){
        this.doLoad(((page-1) * this.pageSize).constrain(0, this.store.getTotalCount()));
         
    },

    // private
    onClick : function(which,num){
        var store = this.store;
        switch(which){
            case "first":
                this.doLoad(1);
            break;
            case "prev":
                this.doLoad(this.jsonData[this.paramNames.start] - 1);
            break;
            case "next":
                this.doLoad(this.jsonData[this.paramNames.start] + 1);
            break;
            case "last":
                this.doLoad(-1);
            break;
            case "buttonClick":
            	this.onButtonClicked(num);
            break;
        }
    },
    getCurrentPage : function(){//得到当前是第几页    lich
    	return this.jsonData[this.paramNames.start];
    },
    resetPageInfo : function(){
    	this.field.el.dom.value = 1;
    },
    
    onButtonClicked : function(which){
    },

    unbind : function(store){
        store = Ext.StoreMgr.lookup(store);
        store.un("beforeload", this.beforeLoad, this);
        store.un("load", this.onLoad, this);
        store.un("loadexception", this.onLoadError, this);
        this.store = undefined;
    },

    bind : function(store){
        store = Ext.StoreMgr.lookup(store);
        store.on("beforeload", this.beforeLoad, this);
        store.on("load", this.onLoad, this);
        store.on("loadexception", this.onLoadError, this);
        this.store = store;
    },

    onDestroy : function(){
        if(this.store){
            this.unbind(this.store);
        }
        SelfPagingToolbar.superclass.onDestroy.call(this);
    }
});

Ext.reg('selfpaging', SelfPagingToolbar);