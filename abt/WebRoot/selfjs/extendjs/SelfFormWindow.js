SelfFormWindow = function(divid, title, width, height, fieldwidth, cols, fields, buttons, labelAlign,labelWidth,upload){
	var items = new Array();
	for(var i=0;i<cols;i=i+1){
		var item = {columnWidth:1/cols, layout:'form', bodyStyle:'padding:5 8 0 8'};
		if(labelAlign === undefined){
			item.labelAlign = 'top';
		}else{
			item.labelAlign = labelAlign;
		}
		item.items = new Array();
		for(var j=0;j<fields.length;j=j+1){
			var field=fields[j].field;
			if(fields[j].colIndex === i){
				if(field.width === undefined){
					field.width = fieldwidth;
				}
				if(field.xtype === 'combo'){
					field.mode = 'local';
					field.triggerAction = 'all';
					field.emptyText= field.emptyText=== undefined?'':field.emptyText;
					field.editable= field.editable=== undefined?true:field.editable;
					field.resizable= true;
					field.typeAhead= field.typeAhead=== undefined?true:field.typeAhead;
				}
				if(fields[j].labelAlign !== undefined){	//可在fields中自定义labelAlign，默认为label显示在组件上方
					item.labelAlign=fields[j].labelAlign;
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
		renderTo:divid,  width:width, height:height, title:title, closable:true,  modal: true,
		closeAction:'hide', collapsible : true,
		items:[
	       {xtype:'form', height:height - 30,width:width - 10, monitorValid:true, buttonAlign:'right', layout:'column', frame:true,labelWidth: labelWidth,
	    	   items:items,autoScroll:true,bodyStyle :'overflow-x:hidden; overflow-y:auto;',fileUpload:Ext.isEmpty(upload)?false:true,
	    	   buttons : buttons
	       }
		]
	});
}

SelfFormWindow.prototype.open = function(){
	this.window.getComponent(0).getForm().reset();
	var clientWidth = document.body.clientWidth;
	var clientHeight = document.body.clientHeight;
	this.window.setPosition((clientWidth - this.window.getSize().width)/2, (clientHeight - this.window.getSize().height)/2);
	this.window.show();
}

SelfFormWindow.prototype.getForm = function(){
	return this.window.getComponent(0).getForm();
}

SelfFormWindow.prototype.close = function(){
	this.window.hide();
}
SelfFormWindow.prototype.destory = function(){
	this.window.destroy();
}

SelfFormWindow.prototype.updateFields = function(record){
	this.window.getComponent(0).getForm().loadRecord(record);
	var items=this.window.getComponent(0).getForm().items.items;
	for(var i=0;i<items.length;i++){
		var item=items[i];
		if(item.xtype=='combotree'){
			var name=item.passName;
			item.passField.value=record.data[name];
            if(item.traslateStore){//此处如果下拉树组件定义了翻译store，那么按照store翻译 
                item.setValue(getDictValue(record.data[name],item.traslateStore,item.valueField,item.displayField));
            }else{//否则显示name字段的值
                item.setValue(record.data[item.name]);
            }
		}
	}
}

SelfFormWindow.prototype.getFields = function(){
	var form=this.window.getComponent(0);
	var submitValues = form.getForm().getValues(false);
	for (var param in submitValues) {
		if (form.form.findField(param)&& form.form.findField(param).emptyText == submitValues[param]) {
			submitValues[param]='';
		}
	}
	return submitValues;
	//return this.window.getComponent(0).getForm().getValues(false);
}
SelfFormWindow.prototype.reset=function(){
	this.window.getForm().reset();
	for(var k = 0;k<this.fields.length;k++){//清空combox中的内容   lich
		if(this.fields[k].field.xtype =='combo'){
			var f = this.window.getForm().findField(this.fields[k].field.hiddenName);
			f.clearValue();
		}
	}
}

SelfFormWindow.prototype.getEl = function(){
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
				if(field.xtype === 'combo'||field.xtype === 'multiSelect'){
					field.mode = 'local';
					field.width=field.width==undefined?fieldwidth:field.width;
					field.triggerAction = 'all';
					field.emptyText=  field.emptyText=== undefined?'':field.emptyText;
					field.editable= field.editable=== undefined?true:field.editable;
					field.resizable= true;
					field.typeAhead= field.typeAhead=== undefined?true:field.typeAhead;
				}
				if(field.readOnly == true){
					field.style='background:#DDDDDD;';
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
		//collapsible:true, //是否可收缩
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

SelfToolBarPanel.prototype.getWidth = function(){
	return this.toolbarwindow.getWidth();
}

SelfToolBarPanel.prototype.getFields = function(){
	var form=this.toolbarwindow;
	var submitValues = form.getForm().getValues(false);
	for (var param in submitValues) {
		if (form.form.findField(param)&& form.form.findField(param).emptyText == submitValues[param]) {
			submitValues[param]='';
		}
	}
	return submitValues;
}

SelfToolBarPanel.prototype.getForm = function(){
	return this.toolbarwindow;
}

SelfToolBarPanel.prototype.reset = function(){
	this.toolbarwindow.getForm().reset();
	for(var k = 0;k<this.fields.length;k++){//清空combox中的内容
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