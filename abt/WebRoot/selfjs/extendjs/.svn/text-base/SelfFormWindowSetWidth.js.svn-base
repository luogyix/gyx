SelfFormWindowSetWidth = function(divid, title, width, height, fieldwidth, cols,widths, fields, buttons,labelAlign,labelWidth){
	var heights = 0;
	var total = 0;
	for(var j =0;j<widths.length;j++){
		total+=widths[j];
	}
	var items = new Array();
	for(var i=0;i<cols;i=i+1){
		var item = {columnWidth:widths[i]/total, layout:'form', bodyStyle:'padding:5 8 0 8', labelAlign:'left'};
		item.items = new Array();
		for(var j=0;j<fields.length;j=j+1){
			heights = heights + fields[j].height;//循环确定窗口内部高度
			var field=fields[j].field;
			if(fields[j].colIndex === i){
				if(field.width === undefined){
					field.width = widths[i];
				}
				if(field.xtype === 'combo'){
					field.mode = 'local';
					field.triggerAction = 'all';
					field.emptyText= field.emptyText=== undefined?'':field.emptyText;
					field.editable= field.editable=== undefined?true:field.editable;
					field.resizable= true;
					field.typeAhead= field.typeAhead=== undefined?true:field.typeAhead;
				}
				if(field.readOnly == true){
					field.style='background:#DDDDDD;';
				}
			
				if(field.allowBlank ==false){
					field.fieldLabel =field.fieldLabel+ '<font color=red>*</font>';
				}
				if(field.xtype=='fieldset'){
					for(var n=0;n<field.items.length;n=n+1){
						if(field.items[n].items&&field.items[n].items[0].readOnly == true){
							field.items[n].items[0].style='background:#DDDDDD;';
						}
							if(field.items[n].items&&field.items[n].items[0].allowBlank == false){
								field.items[n].items[0].fieldLabel =field.items[n].items[0].fieldLabel+'<font color=red>*</font>';
							}
						
					}
					
				}
				item.items.push(field);
			}
		}
		items.push(item);
	}
	this.window = new Ext.Window({
		renderTo:divid, width:width, height:height, title:title, closable:true, modal: true,
		collapsible : true,closeAction:'hide',
		items:[
	       {xtype:'form', monitorValid:true, buttonAlign:'right', layout:'column', frame:true,labelWidth: labelWidth,
	    	   items:items,bodyStyle :'overflow-x:hidden; overflow-y:auto;',autoScroll:true,height:height - 30,width:width - 10,
	    	   buttons : buttons
	       }
		]
	});
}

SelfFormWindowSetWidth.prototype.open = function(){
	this.window.getComponent(0).getForm().reset();
	var clientWidth = document.body.clientWidth;
	var clientHeight = document.body.clientHeight;
	this.window.setPosition((clientWidth - this.window.getSize().width)/2, (clientHeight - this.window.getSize().height)/2);
	this.window.show();
}
SelfFormWindowSetWidth.prototype.getForm = function(){//得到form对象  lich 添加
	return this.window.getComponent(0).getForm();
}
SelfFormWindowSetWidth.prototype.close = function(){
	this.window.hide();
}
SelfFormWindowSetWidth.prototype.destory = function(){
	this.window.destroy();
}

SelfFormWindowSetWidth.prototype.updateFields = function(record){
	this.window.getComponent(0).getForm().loadRecord(record);
	var items=this.window.getComponent(0).getForm().items.items;
	for(var i=0;i<items.length;i++){
		var item=items[i];
		if(item.xtype=='combotree'){
			var name=item.passName;
			item.passField.value=record.data[name];
			item.value=record.data[item.name];
		}
		
	}
}

SelfFormWindowSetWidth.prototype.getFields = function(){
	return this.window.getComponent(0).getForm().getValues(false);
}
SelfFormWindowSetWidth.prototype.getEl = function(){
	return this.window.getEl();
}

SelfToolBarPanel = function(divid, title, fieldwidth, rows, fields, buttons, buttonClickFunc){
	this.fields = fields;//lich
	var items = new Array();
	if(buttons !== undefined){
		var item = new Ext.Toolbar({height:24});
		for(var i=0;i<buttons.length;i=i+1){
			buttons[i].handler = buttonClickFunc.createDelegate(this, [i]);
			item.addButton(buttons[i]);
			if(i !== buttons.length - 1){
				item.addSeparator();
			}
		}
		items.push(item);
	}
	for(var i=0;i<rows;i=i+1){
		var item = new Ext.Toolbar({height:24});
	//	item.items = new Array();
		for(var j=0;j<fields.length;j=j+1){
			var field=fields[j].field;
			if(fields[j].rowIndex === i){
				item.addText(field.fieldLabel);
				//field.width = fieldwidth;
				if(field.xtype === 'combo'){
					field.mode = 'local';
					field.width=field.width==undefined?fieldwidth:field.width;
					field.triggerAction = 'all';
					field.emptyText=  field.emptyText=== undefined?'':field.emptyText;
					field.editable= field.editable=== undefined?true:field.editable;
					field.resizable= true;
					field.typeAhead= field.typeAhead=== undefined?true:field.typeAhead;
				}
				item.addField(field);
				if(j!=fields.length-1){
					if(field.xtype!='hidden'){
						item.addSeparator();
					}
				}
			}
		}
		items.push(item);
	}
	var height = 0;
	if(buttons !== undefined){
		height = 24	*(rows + 1) + 27;
	}else{
		height = 24	*rows + 27;
	}
	this.toolbarwindow = new Ext.form.FormPanel({
		region:'north',
		renderTo:divid,
		items:items
	});
}

SelfToolBarPanel.prototype.open = function(){
	var clientWidth = document.body.clientWidth;
	var clientHeight = document.body.clientHeight;
	this.toolbarwindow.setPosition((clientWidth - this.toolbarwindow.getSize().width)/2, (clientHeight - this.toolbarwindow.getSize().height)/2);
	this.toolbarwindow.show();
}

SelfToolBarPanel.prototype.close = function(){
	this.toolbarwindow.hide();
}

SelfToolBarPanel.prototype.getHeight = function(){
	return this.toolbarwindow.getHeight();
}

SelfToolBarPanel.prototype.getFields = function(){
	return this.toolbarwindow.getForm().getValues(false);
}

SelfToolBarPanel.prototype.reset = function(){
	this.toolbarwindow.getForm().reset();
	for(var k = 0;k<this.fields.length;k++){//清空combox中的内容   lich
		if(this.fields[k].field.xtype =='combo'){
			var f = this.toolbarwindow.getForm().findField(this.fields[k].field.hiddenName);
			f.clearValue();
		}
		if(this.fields[k].field.xtype =='combotree'){
			var f = this.toolbarwindow.getForm().findField(this.fields[k].field.id);
			f.clearValue();
		}
	}
}
