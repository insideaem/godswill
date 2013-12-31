Ext.define('GW.store.BibleContent', {
    extend : 'Ext.data.Store',
    model : 'GW.model.bible.Chapter',

    contains : function(id) {
	var result = this.getById(id.replace(/\//g, "_"));
	return result != undefined;
    }
});