Ext.define('GW.model.bible.Bible', {
    extend : 'GW.model.bible.BibleElement',
    fields : [ {
	name : 'title',
	type : 'string'
    }, {
	name : 'description',
	type : 'string'
    }, {
	name : 'hasStrong',
	type : 'boolean'
    }, {
	name : 'loaded',
	type : 'boolean'
    } ],
    requires : [ 'GW.model.bible.Book' ],

    hasMany : {
	model : 'GW.model.bible.Book',
	name : 'books'
    },

    isLoaded : function() {
	return this.get('loaded');
    },
    getTitle : function() {
	return this.get('title');
    }
});