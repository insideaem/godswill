Ext.define('GW.controller.BibleReader', {
    extend : 'Ext.app.Controller',
    requires : [ 'GW.view.Verse', 'Ext.state.Manager', 'Ext.state.CookieProvider' ],

    refs : [ {
	ref : 'bibleContent',
	selector : 'biblecontent'
    }, {
	ref : 'versePanelView',
	selector : 'biblecontent > versepanel > dataview'
    }, {
	ref : 'bibleReferences',
	selector : '#references'
    } ],

    stores : [ 'Bible' ],

    init : function() {
	// 7 days from now
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider({
	    expires : new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 7)),
	}));
	// Start listening for events on views
	var me = this;
	this.control({
	    'biblecontent > versepanel ' : {
		verse_selected : function(verse) {
		    alert('Verse clicked');
		    return;
		    var verse = verseView.verse;
		    // Update references
		    var verses = [];
		    Ext.Array.each(verse.getReferences(), function(reference) {
			verses.push({
			    xtype : 'verse',
			    displayMode : 'short',
			    showTip : true,
			    verse : reference
			});
		    });

		    this.getBibleReferences().setVerses(verses).expand();
		}
	    },
	    'biblecontent search' : {
		search : function(e, query) {
		    this.getBibleSearch().setLoading(true);
		    var me = this;
		    Ext.Ajax.request({
			url : '/content/godswill.search.json',
			method : 'GET',
			disableCaching : false,
			params : {
			    q : query,
			    b : me.currentBibleVersion.getId()
			},
			success : function(response) {
			    var text = response.responseText;
			    var searchResults = Ext.JSON.decode(text);
			    var verses = [];

			    // Convert to verse objects
			    Ext.Array.each(searchResults, function(searchResult) {
				verses.push({
				    xtype : 'verse',
				    displayMode : 'short',
				    showTip : true,
				    highlight : query,
				    verse : GW.model.bible.Verse.searchResultToVerse(searchResult)
				});
			    });
			    me.getBibleSearch().setVerses(verses).setLoading(false);
			}
		    });
		}
	    }
	});
    },

    onLaunch : function() {
	this.getBibleContent().show();
    }

});
