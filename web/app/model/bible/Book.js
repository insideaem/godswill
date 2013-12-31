Ext.define('GW.model.bible.Book', {
    extend : 'GW.model.bible.BibleElement',
    fields : [ {
	name : 'title',
	type : 'string'
    } ],

    hasMany : {
	model : 'GW.model.bible.Chapter',
	name : 'chapters'
    },

    belongsTo : {
	model : 'GW.model.bible.Bible',
	getterName : 'getBible'
    },
    getTitle : function() {
	return this.get('title');
    }
});