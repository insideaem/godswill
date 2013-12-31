Ext.define('GW.view.TocBrowser', {
    extend : 'Ext.window.Window',
    alias : 'widget.tocbrowser',
    constrain : true,
    modal : true,
    width : 304,
    cls : 'tocBrowser',
    closeAction : 'hide',

    constructor : function(config) {
	var me = this;

	config.items = [ {
	    xtype : 'dataview',
	    itemSelector : 'div.book',
	    hidden : true,
	    itemId : 'booksView',
	    overItemCls : 'book-over',
	    selectedItemCls : 'selected-book',
	    tpl : new Ext.XTemplate('<tpl for="."><div class="book {name}">{title}</div></tpl>'),
	    listeners : {
		itemclick : function(booksView, book, item, index, e, eOpts) {
		    var chaptersView = me.down('#chaptersView');
		    chaptersView.bindStore(book.chapters());
		    chaptersView.refresh();

		    booksView.hide();
		    chaptersView.show();

		    me.setTitle(me.chapter.getBook().getBible().getTitle() + ' &raquo; ' + book.getTitle());
		}
	    }
	}, {
	    xtype : 'dataview',
	    itemSelector : 'div.chapter',
	    overItemCls : 'chapter-over',
	    selectedItemCls : 'selected-chapter',
	    hidden : true,
	    itemId : 'chaptersView',
	    tpl : new Ext.XTemplate('<tpl for="."><div class="chapter">{name}</div></tpl>'),
	    listeners : {
		itemclick : function(chaptersView, chapter, item, index, e, eOpts) {
		    chaptersView.hide();
		    me.fireEvent('chapter_selected', chapter);
		    me.hide();
		}
	    }
	} ];

	this.addEvents('chapter_selected');
	this.callParent(arguments);
    },
    setChapter : function(chapter) {
	this.chapter = chapter;
    },

    beforeShow : function() {
	this.callParent();

	var bible = this.chapter.getBook().getBible();
	var booksView = this.down('#booksView');
	booksView.bindStore(bible.books());
	booksView.getSelectionModel().select(this.chapter.getBook());

	var chaptersView = this.down('#chaptersView');
	chaptersView.bindStore(this.chapter.getBook().chapters());
	chaptersView.getSelectionModel().select(this.chapter);

	this.setTitle(this.chapter.getBook().getBible().getTitle());
	this.down('#booksView').show();
	this.down('#chaptersView').hide();
    }
});