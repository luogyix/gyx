//重写CheckboxSelectionModel添加,使checkBox放到lock列之中也能响应
Ext.grid.ExtCheckboxSelectionModel = Ext.extend(Ext.grid.CheckboxSelectionModel, {  
	
      handleMouseDown: function(g, rowIndex, e) { 
       if (e.button !== 0 || this.isLocked()) { 
           return; 
       } 
       var view = this.grid.getView(); 
       if (e.shiftKey && !this.singleSelect && this.last !== false) { 
           var last = this.last; 
           this.selectRange(last, rowIndex, e.ctrlKey); 
           this.last = last; 
           view.focusRow(rowIndex); 
       } else { 
           var isSelected = this.isSelected(rowIndex); 
           if (isSelected) { 
               this.deselectRow(rowIndex); 
           } else if (!isSelected || this.getCount() > 1) { 
               this.selectRow(rowIndex, true); 
               view.focusRow(rowIndex); 
           } 
       } 
   }, 
   isLocked: Ext.emptyFn, 
   locked: true,
   initEvents: function() { 
       Ext.grid.CheckboxSelectionModel.superclass.initEvents.call(this); 
       this.grid.on('render', function() { 
           var view = this.grid.getView(); 
           view.mainBody.on('mousedown', this.onMouseDown, this); 
           Ext.fly(view.lockedInnerHd).on('mousedown', this.onHdMouseDown, this); 
       }, this); 
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
 
PageQuery = function(whetherpage, divid, width, height, dataStore, columnModel,sm, pageToolText,tbar, columnSortFunc){
	this.divid = divid;
	this.rsM=columnModel;
	this.dataStore = dataStore;
	pageToolText = pageToolText.replace(/\<\$/g,'{');
	pageToolText = pageToolText.replace(/\$\>/g,'}');
	var pagingToolParamObj = Ext.util.JSON.decode(pageToolText);
	pagingToolParamObj.displayMsg = pagingToolParamObj.displayMsg.replace(/\<\%\$/g,'{');
	pagingToolParamObj.displayMsg = pagingToolParamObj.displayMsg.replace(/\$\%\>/g,'}');
	
	this.selfPagingTool = new SelfPagingToolbar({
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
		region : 'center',
		height:	height,
		width:  width,
		//bbar   : this.bbar,
		tbar:tbar,
	    cm     : columnModel,
	    loadMask: true,
		sm     : sm,
		store  : this.dataStore,
		renderTo:this.divid,
		stripeRows : true,
		monitorResize: true,
	//	cls: 'rowspan-grid', //******必须配置此样式   
        view: new Ext.ux.grid.LockingGridView(), //****使用view   
     	
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
	var records = this.pagingGrid.getSelectionModel().getSelections();
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
	var records = this.pagingGrid.getSelectionModel().getSelections();
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

PageQuery.prototype.queryPage = function(store,query_obj) {
	this.submit_obj = query_obj;
	this.selfPagingTool.resetPageInfo();
	this.selfPagingTool.doLoad(store,1);
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
    paramNames : {start: 'start', limit: 'limit', total: 'total',page:'page', resultList:'field1', resultObject:'field2'},
    pageData   : [[10,'全部']],
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
    	//this.doLoad(1); 多余，会产生运行错误
    },

    // private
    updateInfo : function(){
        if(this.displayEl){
            var count = this.store.getCount();
             var limit=this.jsonData[this.paramNames.limit];
             var page=this.jsonData[this.paramNames.page];
             var total = this.jsonData[this.paramNames.total];
        	var startrow = (this.jsonData[this.paramNames.start] - 1) *limit ;
        	var endrow   = startrow + count;
        	startrow = startrow + 1;
            var msg = count == 0 ?
                this.emptyMsg :
                String.format(
                    this.displayMsg,
                    startrow, endrow,total);
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
        	this.jsonData[this.paramNames.start] = this.jsonData[this.paramNames.page];
        }
        
        this.field.el.dom.value = this.jsonData[this.paramNames.start];
        this.first.setDisabled(this.jsonData[this.paramNames.start] == 1);
        this.prev.setDisabled(this.jsonData[this.paramNames.start] == 1);
        
      //查询出条数刚好是limit数时，还显示有下一页。（lich）
       /*
         if(this.jsonData[this.paramNames.page] == 0){
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
        */
        if(this.jsonData[this.paramNames.page] > -1){
        	if(this.getCurrentPage() < this.jsonData[this.paramNames.page]){
        		this.next.setDisabled(false);
        		this.last.setDisabled(false);
        	}else{
        		this.next.setDisabled(true);
        		this.last.setDisabled(true);
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
    doLoad : function(store,start){
    	this.jsonData[this.paramNames.start] = start;
		this.parentComp.submit_obj[this.paramNames.start] = this.jsonData[this.paramNames.start];
		this.parentComp.submit_obj[this.paramNames.limit] = this.jsonData[this.paramNames.limit];
		var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"请稍候...", removeMask:true});
		store.load({params:this.parentComp.submit_obj});
		
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
    	//this.field.el.dom.value = 1;
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
