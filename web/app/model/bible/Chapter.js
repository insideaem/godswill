Ext.define('GW.model.bible.Chapter', {
    extend : 'GW.model.bible.BibleElement',

    fields : [ {
	name : 'loaded',
	type : 'boolean'
    } ],
    hasMany : {
	model : 'GW.model.bible.Verse',
	name : 'verses'
    },

    belongsTo : {
	model : 'GW.model.bible.Book',
	getterName : 'getBook'
    },

    getLabel : function() {
	return this.getBook().getTitle() + ' ' + this.getName();
    },

    isLoaded : function() {
	return this.get('loaded');
    },
});