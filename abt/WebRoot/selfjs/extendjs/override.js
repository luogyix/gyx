Ext.override(Ext.form.CheckboxGroup, {
  getNames: function() {
    var n = [];

    this.items.each(function(item) {
      if (item.getValue()) {
        n.push(item.getName());
      }
    });

    return n;
  },

  getValues: function(submitdata) {
 //   var v = {};
    this.items.each(function(item) {
        submitdata[item.id]=item.getValue();
    });

    return submitdata;
  },

  setValues: function(v) {
    var r = new RegExp('(' + v.join('|') + ')');
    this.items.each(function(item) {
      item.setValue(r.test(item.getRawValue()));
    });
  }
});

Ext.override(Ext.form.RadioGroup, {
  getName: function() {
    return this.items.first().getName();
  },

  getValue: function() {
    var v;

    this.items.each(function(item) {
      v = item.getRawValue();
      return !item.getValue();
    });

    return v;
  },

    getValues: function(submitdata) {
  //    var v ={} ;
      this.items.each(function(item) {
          submitdata[item.id]=item.getValue();
   	 });
    return submitdata;
  },
  
  setValue: function(v) {
    this.items.each(function(item) {
      item.setValue(item.getRawValue() == v);
    });
  }
});