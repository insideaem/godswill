Ext.define('GW.store.Bible', {
    extend : 'Ext.data.Store',
    model : 'GW.model.bible.Bible',
    proxy : {
	type : 'ajax',
	url : '/content/godswill.list.json'
    },

    getBible : function(id) {
	return this.getById(id);
    },

    loadBible : function(bibleId, callback) {
	var bible = this.getBible(bibleId);
	if (bible.isLoaded()) {
	    callback(bible);
	} else {
	    GW.model.bible.Bible.load(bibleId + '.list', {
		success : function(record) {
		    bible.set('loaded', true);
		    var books = record.books().getRange();
		    bible.books().loadData(books);
		    callback(bible);
		}
	    });
	}
    },

    getChapter : function(bibleId, chapterId, callback) {
	var bible = this.getBible(bibleId);
	var bibleObject = this.buildBibleObject(chapterId);
	var book = bible.books().getById(bibleObject.bookId);
	var chapters = book.chapters();
	var chapter = chapters.getById(bibleObject.chapterId);

	if (!chapter.isLoaded()) {
	    GW.model.bible.Chapter.load(bibleId + '/' + bibleObject.chapterPath + '.list', {
		success : function(record) {
		    chapter.set('loaded', true);
		    var verses = record.verses().getRange();
		    chapter.verses().loadData(verses);

		    callback(chapter);
		}
	    });
	} else {
	    callback(chapter);
	}
    },

    buildBibleObject : function(id) {
	var result = {};
	var splits = id.split('_');

	result.bookId = splits[0];
	if (splits[1]) {
	    result.chapterId = result.bookId + '_' + splits[1];
	    result.chapterPath = result.bookId + '/' + splits[1];
	}

	return result;
    }
});