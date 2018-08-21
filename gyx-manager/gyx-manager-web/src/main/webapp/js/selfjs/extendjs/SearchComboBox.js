Ext.ns('Ext.form');
Ext.form.SearchComboBox = Ext.extend(Ext.form.ComboBox, {
    mode : 'local',   
    triggerAction : 'all',
    forceSelection:true,
    initComponent: function () {
        Ext.form.SearchComboBox.superclass.initComponent.apply(this, arguments);

        this.on({
            scope: this,
            beforequery: this.onBeforeQuery
        });
    },
    onBeforeQuery: function (e) {
    	var combo = e.combo;
		var value = e.query;
		//combo.collapse();
		if(!e.forceAll){
			combo.store.clearFilter();
			combo.store.filterBy(function(record, id){
				var text = record.get(combo.displayField);
				return (text.indexOf(value) != -1);
			});
			//this.selectedIndex = -1;
			this.expand();
			//this.onLoad();
			return false;
		}
		if(!value){
			this.store.clearFilter();
		}
	}
});
Ext.reg('searchCombo', Ext.form.SearchComboBox);