Ext.define('GW.store.Bible', {
    extend : 'Ext.data.Store',
    model : 'GW.model.bible.Bible',
    proxy : {
	type : 'ajax',
	url : '/content/godswill/bibles.list.json'
    },

    getBible : function(id) {
	return this.getById(id);
    },

    loadBible : function(bibleId, callback) {
	var bible = this.getBible(bibleId);
	if (bible.isLoaded()) {
	    callback(bible);
	} else {
	    GW.model.bible.Bible.load('bibles/' + bibleId + '.list', {
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
	var parts = chapterId.split('_');
	var bookId = parts[0];

	var bible = this.getBible(bibleId);
	var book = bible.books().getById(bookId);
	var chapters = book.chapters();
	var chapter = chapters.getById(chapterId);

	if (!chapter.isLoaded()) {
	    GW.model.bible.Chapter.load('bibles/' + chapter.getPath() + '.list', {
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
    }
});