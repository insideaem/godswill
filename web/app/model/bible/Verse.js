Ext.define('GW.model.bible.Verse', {
    extend : 'GW.model.bible.BibleElement',
    fields : [ {
	name : 'text',
	type : 'string'
    }, {
	name : 'label',
	type : 'string'
    }, {
	name : 'score',
	type : 'int'
    }, {
	name : 'references',
	type : 'string'
    }, {
	name : 'next',
	type : 'string'
    }, {
	name : 'prev',
	type : 'string'
    } ],
    belongsTo : {
	model : 'GW.model.bible.Chapter',
	getterName : 'getChapter'
    },
    hasText : function() {
	return !Ext.isEmpty(this.get('text'));
    },
    proxy : {
	type : 'rest',
	url : '/content/godswill',
	format : 'json'
    },

    getText : function() {
	return this.get('text');
    },
    getLabel : function() {
	return this.get('label');
    },
    getNextOrPrevVerse : function(id) {
	var result = undefined;
	if (!Ext.isEmpty(id)) {
	    var label = GW.model.bible.Verse.idToLabel(id);
	    return new GW.model.bible.Verse({
		label : label,
		id : id
	    });
	}

	return result;
    },

    hasNext : function() {
	return !Ext.isEmpty(this.get('next'));
    },

    hasPrev : function() {
	return !Ext.isEmpty(this.get('prev'));
    },

    getNextVerse : function() {
	var nextId = this.get('next');
	return this.getNextOrPrevVerse(nextId);
    },
    getPrevVerse : function() {
	var prevId = this.get('prev');
	return this.getNextOrPrevVerse(prevId);
    },
    getReferences : function() {
	var refString = this.get('references');
	var result = [];

	if (!Ext.isEmpty(refString)) {
	    var bibleId = this.getId().split('/')[0];
	    var refArray = refString.split(',');

	    Ext.Array.each(refArray, function(ref) {
		var refId = bibleId + '/' + ref;
		var refLabel = GW.model.bible.Verse.idToLabel(refId);

		var reference = new GW.model.bible.Verse({
		    label : refLabel,
		    id : refId
		});

		result.push(reference);
	    });
	}

	return result;
    },

    /* STATICS */
    statics : {
	/*
	 * id: kjv/gen/1/1
	 */
	idToLabel : function(id) {
	    var splits = id.split('_');
	    return Ext.String.capitalize(splits[1]) + ". " + splits[2] + ":" + splits[3];
	},
	/*
	 * searchResult: 1234#kjv/gen/1/1
	 */
	searchResultToVerse : function(searchResult) {
	    var splits = searchResult.split('#');
	    var verseScore = splits[0];
	    var verseId = splits[1];
	    var verseLabel = GW.model.bible.Verse.idToLabel(verseId);

	    return new GW.model.bible.Verse({
		label : verseLabel,
		id : verseId,
		score : verseScore
	    });
	}
    }
});