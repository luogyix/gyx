SelfFormWindowForUpload = function(divid, title, width, height, fieldwidth, cols, fields, buttons){
	var items = new Array();
	for(var i=0;i<cols;i=i+1){
		var item = {columnWidth:1/cols, layout:'form', bodyStyle:'padding:5 8 0 8', labelAlign:'top'};
		item.items = new Array();
		for(var j=0;j<fields.length;j=j+1){
			var field=fields[j].field;
			if(fields[j].colIndex === i){
				if(field.width === undefined){
					field.width = fieldwidth;
				}
				if(field.xtype === 'combo'){
					field.mode = 'local';
					field.emptyText= field.emptyText=== undefined?'':field.emptyText,
					field.editable= field.editable=== undefined?true:field.editable,
					field.editable= false,
					field.resizable= true,
					field.typeAhead= field.typeAhead=== undefined?true:field.typeAhead
				}
				item.items.push(field);
			}
		}
		items.push(item);
	}
	this.window = new Ext.Window({
		renderTo:divid, modal:true, width:width, height:height, title:title, closable:false, animateTarget : Ext.getBody(),
		items:[
	       {xtype:'form', height:height - 30, monitorValid:true, buttonAlign:'right', layout:'column', frame:true,fileUpload:true,
	    	   items:items,
	    	   buttons : buttons
	       }
		]
	});
}

SelfFormWindowForUpload.prototype.open = function(){
	this.window.getComponent(0).getForm().reset();
	var clientWidth = document.body.clientWidth;
	var clientHeight = document.body.clientHeight;
	this.window.setPosition((clientWidth - this.window.getSize().width)/2, (clientHeight - this.window.getSize().height)/2);
	this.window.show();
}
SelfFormWindowForUpload.prototype.getForm = function(){//得到form对象  lich 添加
	return this.window.getComponent(0).getForm();
}
SelfFormWindowForUpload.prototype.close = function(){
	this.window.hide();
}

SelfFormWindowForUpload.prototype.updateFields = function(record){
	this.window.getComponent(0).getForm().loadRecord(record);
}

SelfFormWindowForUpload.prototype.getFields = function(){
	return this.window.getComponent(0).getForm().getValues(false);
}
