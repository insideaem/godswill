Ext.define('GW.view.BibleContent', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.biblecontent',
    requires : [ 'GW.view.BibleBrowser', 'GW.view.TocBrowser', 'GW.view.VersePanel', 'GW.view.LoginWindow' ],
    layout : 'border',
    listeners : {
	resize : function() {
	    console.log('Resized');
	}
    },

    constructor : function(config) {
	var me = this;

	this.bibleStore = Ext.getStore('Bible');
	config.stateEvents = [ 'resize' ];
	config.stateful = true;
	config.stateId = 'biblecontent';
	config.tbar = [ '->', {
	    text : 'Select Bible',
	    scale : 'medium',
	    itemId : 'bibleBrowserBtn',
	    handler : function() {
		me.showBibleBrowser();
	    }
	}, {
	    text : '&laquo;',
	    tooltip : 'Previous book',
	    itemId : 'previousBookBtn',
	    handler : function() {
		// Load previous book at first chapter
		me.loadChapter(me.chapter.getBook().getBible().getId(), me.chapter.getBook().getPrevious() + '_1');
	    }
	}, {
	    text : '<',
	    tooltip : 'Previous chapter',
	    itemId : 'previousChapterBtn',
	    handler : function() {
		me.loadChapter(me.chapter.getBook().getBible().getId(), me.chapter.getPrevious());
	    }
	}, {
	    text : 'Select Chapter',
	    scale : 'medium',
	    itemId : 'tocBrowserBtn',
	    handler : function() {
		me.showTocBrowser();
	    }
	}, {
	    text : '>',
	    tooltip : 'Next chapter',
	    itemId : 'nextChapterBtn',
	    handler : function() {
		me.loadChapter(me.chapter.getBook().getBible().getId(), me.chapter.getNext());
	    }
	}, {
	    text : '&raquo;',
	    tooltip : 'Next book',
	    itemId : 'nextBookBtn',
	    handler : function() {
		// Load next book at first chapter
		me.loadChapter(me.chapter.getBook().getBible().getId(), me.chapter.getBook().getNext() + '_1');
	    }
	}, '->', '->', {
	    xtype : 'textfield',
	    value : 'Search',
	    listeners : {
		specialkey : function(field, event) {
		    if (event.getKey() == event.ENTER) {
			var operation = new Ext.data.Operation({
			    action : 'read',
			    params : {
				q : field.getValue()
			    }
			});
			var proxy = new Ext.data.proxy.Ajax({
			    url : '/content/godswill/bibles/' + me.chapter.getBook().getBible().getPath() + '.search.json',
			    reader : 'array'
			});

			proxy.read(operation, function(a, b, c, d, e, f) {
			    console.log(a);
			});
		    }
		}
	    }
	}, '->', '->', {
	    xtype : 'button',
	    text : 'Login',
	    iconCls : 'x-login',
	    scale : 'medium',
	    handler : function() {
		me.showLoginWindow();
	    }
	}, '->'

	];
	config.items = [ {
	    xtype : 'versepanel',
	    autoScroll : true,
	    region : 'center',
	    border : false,
	    bodyPadding : 5
	}, {
	    xtype : 'panel',
	    region : 'east',
	    split : true,
	    minWidth : 200,
	    width : 200,
	    header : false,
	    collapsed : true,
	    layout : 'accordion',
	    collapsible : true,
	    title : 'EXTRAS',
	    align : 'stretch',
	    defaults : {
		// applied to each contained panel
		autoScroll : true
	    },
	    items : [ {
		xtype : 'versepanel',
		title : 'Search Results',
		itemId : 'searchResults'
	    }, {
		xtype : 'versepanel',
		title : 'References',
		itemId : 'references'
	    }, {
		xtype : 'panel',
		title : 'Notes'
	    } ]
	} ];

	this.callParent(arguments);
    },

    showLoginWindow : function() {
	if (!this.loginWindow) {
	    this.loginWindow = new GW.view.LoginWindow();
	}

	this.loginWindow.show();
    },

    showTocBrowser : function() {
	var me = this;
	if (!this.tocBrowser) {
	    this.tocBrowser = new GW.view.TocBrowser({
		renderTo : me.getEl(),
		chapter : me.chapter,
		listeners : {
		    chapter_selected : function(chapter) {
			me.loadChapter(chapter.getBook().getBible().getId(), chapter.getId());
		    },
		    show : function() {
			this.anchorTo(me.down('#tocBrowserBtn').getEl());
		    }
		}
	    });
	}

	this.tocBrowser.setChapter(this.chapter);
	this.tocBrowser.show();
    },

    initNextPreviousButtons : function() {
	var noNextChapter = Ext.isEmpty(this.chapter.getNext());
	var noPreviousChapter = Ext.isEmpty(this.chapter.getPrevious());
	var noNextBook = Ext.isEmpty(this.chapter.getBook().getNext());
	var noPreviousBook = Ext.isEmpty(this.chapter.getBook().getPrevious());

	this.down('#nextChapterBtn').setDisabled(noNextChapter);
	this.down('#previousChapterBtn').setDisabled(noPreviousChapter);
	this.down('#nextBookBtn').setDisabled(noNextBook);
	this.down('#previousBookBtn').setDisabled(noPreviousBook);

    },

    loadChapter : function(bibleId, chapterId) {
	var me = this;

	me.setLoading(true);
	me.bibleStore.getChapter(bibleId, chapterId, function(chapter) {
	    me.chapter = chapter;
	    me.down('#tocBrowserBtn').setText(chapter.getLabel());
	    me.down('versepanel>dataview').bindStore(chapter.verses());

	    me.initNextPreviousButtons();
	    me.setLoading(false);
	});
    },

    applyState : function(state) {
	this.chapterSignature = state.chapterSignature;
    },

    loadAfterBibleSelected : function(bibleId, chapterId) {
	var me = this;
	this.setLoading(true);

	this.bibleStore.loadBible(bibleId, function(loadedBible) {
	    me.down('#bibleBrowserBtn').setText(loadedBible.getTitle());
	    me.loadChapter(bibleId, chapterId);
	});
    },

    showBibleBrowser : function() {
	var me = this;
	if (!this.bibleBrowser) {
	    this.bibleBrowser = new GW.view.BibleBrowser({
		renderTo : me.getEl(),
		listeners : {
		    bible_selected : function(bible) {
			// Load selected bible at chapter gen 1:1
			var chapterId = me.chapter ? me.chapter.getId() : 'gen_1';

			me.loadAfterBibleSelected(bible.getId(), chapterId);
		    },
		    show : function() {
			this.anchorTo(me.down('#bibleBrowserBtn').getEl());
		    }
		}
	    });
	}

	this.bibleBrowser.show();
    },

    afterShow : function() {
	this.bibleStore.load({
	    scope : this,
	    callback : function() {
		if (!this.chapterSignature) {
		    this.showBibleBrowser();
		} else {
		    this.loadAfterBibleSelected(this.chapterSignature.bibleId, this.chapterSignature.chapterId);
		}
	    }
	});
    },

    getState : function() {
	if (this.chapter) {
	    currentState.chapterSignature = this.chapter.getSignature();
	}
	return currentState;
    }

});