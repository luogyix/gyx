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
	colM[0]=new Ext.grid.RowNumberer({header:'',width:30,locked: true});
	colM[1] = this.rsM;
	
	this.colMData = colM;
	var columnModel = new Ext.grid.ColumnModel({defaults:{ sortable: true },columns:colM});
	
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
		region : 'center',
		bbar   : this.selfPagingTool,
	    cm     : columnModel,
		sm     : this.rsM,
		store  : this.dataStore,
		renderTo:this.divid,
		stripeRows : true,
		monitorResize: true,
		tbar:tbar,
		heigth:height,
        plugins: plugins,
     	cls: 'rowspan-grid', //******必须配置此样式   
        view: new Ext.ux.grid.RowspanView(), //****使用view   

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

/**
 * 添加event事件,传入事件类型如select,fun传事件内容function
 */
PageQuery.prototype.setEvent = function(functionType, fun){
	this.pagingGrid.on(functionType, fun);
}

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
/*得到grid中的store*/
PageQuery.prototype.getStore = function(){
	return this.dataStore;
}
/*得到grid*/
PageQuery.prototype.getGrid = function(){
	return this.pagingGrid;
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
PageQuery.prototype.queryPage = function(query_obj,url) {
	this.submit_obj = query_obj;
	this.url=url;
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
    displayMsg : '正在显示第{0} - {1}条记录,一共{2}条记录',
    emptyMsg : '对不起，没有您需要的数据',
    beforePageText : "第",
    afterPageText : "页",
    firstText : "第一页",
    prevText : "上一页",
    nextText : "下一页",
    lastText : "最后一页",
    refreshText : "Refresh",
    paramNames : {start: 'start', limit: 'limit', total: 'total',page:'page', resultList:'field1', resultObject:'field2'},
    pageData   : [[20,'每页20条记录'],[50,'每页50条记录'],[100,'每页100条记录']],
    jsonData   : {},
    addRecordText : '',
    deleteRecordText : '',
    url        : '',
    whetherpage : true,
    parentComp  : {},
    buttonsComp : [],
    totalNum: -1,
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
        	value : this.pageData[0]==undefined?'':this.pageData[0][0],
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
        this.jsonData[this.paramNames.limit] = this.pageData[0]==undefined?'':this.pageData[0][0];

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
             this.totalNum=total;
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
    doLoad : function(start){
    	var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"请稍候...", removeMask:true});
    	myMask.show();
    	this.jsonData[this.paramNames.start] = start;
		this.parentComp.submit_obj[this.paramNames.start] = this.jsonData[this.paramNames.start];
		this.parentComp.submit_obj[this.paramNames.limit] = this.jsonData[this.paramNames.limit];
		
		
		Ext.Ajax.timeout=90000;//处理请求超时(lich)90000秒
        Ext.Ajax.request({
			url : this.url, jsonData : this.parentComp.submit_obj, scope:this, params:{requesttype:'ajax'},
			callback : function(options, success, response){
				myMask.hide();
        		if(success){
        			var rawObj = Ext.util.JSON.decode(response.responseText);
        			var jsonObj = Ext.util.JSON.decode(rawObj.actionresult);
        			if(jsonObj.result){
        				var resultList = jsonObj[this.paramNames.resultList];
        				var resultObject = jsonObj[this.paramNames.resultObject];
        				this.jsonData[this.paramNames.page] = resultObject[this.paramNames.page];
        				this.jsonData[this.paramNames.total] = resultObject[this.paramNames.total];
        				this.store.loadData(resultList,false);
        				this.field.enable();
        			}else{
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
/**  
 * 实现grid的rowspan效果  
 *  @author: tipx.iteye.com  
 *  
 *  1.在列模型里需要配置合并行的列中设置groupCol属性，如：{dataIndex:'xxx', header:'xxx', groupCol:true} //该列需要分组显示 
 *  2.为grid设置view属性 => view : new Ext.ux.grid.RowspanView()  
 *  3.为grid设置cls属性 => cls : 'rowspan-grid'  
 *  4.加入css样式  
 */  

 var minIndex=0;
 var m = new Map();

Ext.ux.grid.RowspanView = Ext.extend(Ext.grid.GridView, {   
    constructor: function(conf) {   
        Ext.ux.grid.RowspanView.superclass.constructor.call(this, conf);   
    },   
    // private   
    //单元格样式判断 ，如果groupCol为true，并且上一行的值与本行相同，加上 ' rowspan-unborder'样式，并返回空值
    //2011.07.12修改，如果本行前面一列未分组，那么本行该列的值不论是否与上一行相同，都不加 ' rowspan-unborder'样式
    cleanRenderer : function(column, value, metaData, record, rowIndex, colIndex, store) {   
    	var message=column.renderer(value, metaData, record, rowIndex, colIndex, store);
    	var groupCol=column.groupCol;
    	 var flag=false;
    	if(groupCol==true){
    		var k=rowIndex+"_"+colIndex;
    		m.put(k,value);
    		if(rowIndex==0||rowIndex%(SelfPagingToolbar.pageSize)==0){
    			metaData.css += ' rowspan-unborder';
    		}else {
    			m.each(function(key,val,index){
    				var str= key.split('_');
    				var i=parseInt(str[0]);
    				var col=parseInt(str[1]);
    				var len=str.length;
    				
    				if(rowIndex==i&&col==colIndex-1&&len==3){
    					flag=false;
    					return "break";
    				}else if(rowIndex==i+1&&col==colIndex&&value==val){
    					flag=true;
    				
					}
				});
    		}
    		if(flag==true){
    			metaData.css += ' rowspan-unborder'; 
				message= '';
				
    		}else{
    			m.put(k+"_true",value);
    		}
    	}
    	
    	var count = store.getCount();
    	if(rowIndex+1==count){
    		metaData.css += ' rowspan_bottom';
    	}
        return message;   
    },   
    // private   
    doRender : function(cs, rs, ds, startRow, colCount, stripe){   
        var ts = this.templates, ct = ts.cell, rt = ts.row, last = colCount-1;   
        var tstyle = 'width:'+this.getTotalWidth()+';';   
        // buffers   
        var buf = [], cb, c, p = {}, rp = {tstyle: tstyle}, r;   
  
        //cmConfig列模型   
        var cmConfig = this.cm.config, rowspans=[];   
        for(var i = 0, len = cmConfig.length; i < len; i++){   
            rowspans.push(Math.max((cmConfig[i].rowspan || 0), 0));   
        }   
  
        for(var j = 0, len = rs.length; j < len; j++){   
            r = rs[j]; cb = [];   
            var rowIndex = (j+startRow);   
            for(var i = 0; i < colCount; i++){   
                c = cs[i];   
                p.id = c.id;   
                p.css = i === 0 ? 'x-grid3-cell-first ' : (i == last ? 'x-grid3-cell-last ' : '');   
                p.attr = p.cellAttr = "";   
                p.value = this.cleanRenderer(cmConfig[i], r.data[c.name], p, r, rowIndex, i, ds);   
                p.style = c.style;   
                if(Ext.isEmpty(p.value)){   
                    p.value = "&#160;";   
                }   
                if(this.markDirty && r.dirty && typeof r.modified[c.name] !== 'undefined'){   
                    p.css += ' x-grid3-dirty-cell';   
                }   
                cb[cb.length] = ct.apply(p);   
            }   
            var alt = [];   
            if(stripe && ((rowIndex+1) % 2 === 0)){   
                alt[0] = "x-grid3-row-alt";   
            }   
            if(r.dirty){   
                alt[1] = " x-grid3-dirty-row";   
            }   
            rp.cols = colCount;   
            if(this.getRowClass){   
                alt[2] = this.getRowClass(r, rowIndex, rp, ds);   
            }   
            rp.alt = alt.join(" ");   
            rp.cells = cb.join("");   
            buf[buf.length] =  rt.apply(rp);   
        }   
        m = new Map();
        return buf.join("");   
    }   
});  


Array.prototype.remove = function(s) {
	for (var i = 0; i < this.length; i++) {
		if (s == this[i])
			this.splice(i, 1);
	}
}

/**
 * Simple Map
 * 
 * 
 * var m = new Map();
 * m.put('key','value');
 * ...
 * var s = "";
 * m.each(function(key,value,index){
 * 		s += index+":"+ key+"="+value+"\n";
 * });
 * alert(s);
 * 
 * @author dewitt
 * @date 2011-06-24
 */
function Map() {
	/** 存放键的数组(遍历用到) */
	this.keys = new Array();
	/** 存放数据 */
	this.data = new Object();
	
	/**
	 * 放入一个键值对
	 * @param {String} key
	 * @param {Object} value
	 */
	this.put = function(key, value) {
		if(this.data[key] == null){
			this.keys.push(key);
		}
		this.data[key] = value;
	};
	
	/**
	 * 获取某键对应的值
	 * @param {String} key
	 * @return {Object} value
	 */
	this.get = function(key) {
		return this.data[key];
	};
	
	/**
	 * 删除一个键值对
	 * @param {String} key
	 */
	this.remove = function(key) {
		this.keys.remove(key);
		this.data[key] = null;
	};
	
	/**
	 * 遍历Map,执行处理函数
	 * 
	 * @param {Function} 回调函数 function(key,value,index){..}
	 */
	this.each = function(fn){
		if(typeof fn != 'function'){
			return;
		}
		var len = this.keys.length;
		for(var i=0;i<len;i++){
			var k = this.keys[i];
			var mes=fn(k,this.data[k],i);
			if(mes=="break"){
				break;
			}
		}
	};
	
	/**
	 * 获取键值数组(类似Java的entrySet())
	 * @return 键值对象{key,value}的数组
	 */
	this.entrys = function() {
		var len = this.keys.length;
		var entrys = new Array(len);
		for (var i = 0; i < len; i++) {
			entrys[i] = {
				key : this.keys[i],
				value : this.data[i]
			};
		}
		return entrys;
	};
	
	/**
	 * 判断Map是否为空
	 */
	this.isEmpty = function() {
		return this.keys.length == 0;
	};
	
	/**
	 * 获取键值对数量
	 */
	this.size = function(){
		return this.keys.length;
	};
	
	/**
	 * 重写toString 
	 */
	this.toString = function(){
		var s = "{";
		for(var i=0;i<this.keys.length;i++,s+=','){
			var k = this.keys[i];
			s += k+"="+this.data[k];
		}
		s+="}";
		return s;
	};
}

Ext.reg('selfpaging', SelfPagingToolbar);

