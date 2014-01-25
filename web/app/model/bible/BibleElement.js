Ext.define('GW.model.bible.BibleElement', {
    extend : 'Ext.data.Model',
    fields : [ {
	name : 'id',
	type : 'string'
    }, {
	name : 'path',
	type : 'string'
    }, {
	name : 'name',
	type : 'string'
    }, {
	name : 'next',
	type : 'string'
    }, {
	name : 'previous',
	type : 'string'
    } ],
    proxy : {
	type : 'rest',
	url : '/content/godswill',
	format : 'json'
    },

    getName : function() {
	return this.get('name');
    },

    getPath : function() {
	return this.get('path');
    },

    getNext : function() {
	return this.get('next');
    },

    getPrevious : function() {
	return this.get('previous');
    }
});