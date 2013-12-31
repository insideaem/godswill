Ext.define('GW.view.VersePanel', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.versepanel',
    mode : 'full',

    constructor : function(config) {
	var me = this;
	this.addEvents('verse_selected');

	config.items = {
	    xtype : 'dataview',
	    itemId : 'dataView',
	    itemSelector : 'div.verse',
	    tpl : new Ext.XTemplate('<tpl for="."><div class="verse"><span class="verseNr">{name}</span>{text}</div></tpl>'),
	    listeners : {
		itemclick : function(dataView, record, item, index, e, eOpts) {
		    me.fireEvent('verse_selected', record);
		},
		itemmouseenter : function(dataView, record, item, index, e, eOpts) {
		    Ext.fly(item).addCls('verse-over');
		},
		itemmouseleave : function(dataView, record, item, index, e, eOpts) {
		    Ext.fly(item).removeCls('verse-over');
		},
		itemcontextmenu : function(dataView, record, item, index, e, eOpts) {
		    var contextMenu = Ext.create('Ext.menu.Menu', {
			title : 'Add',
			items : [ {
			    text : 'Tag'
			}, {
			    text : 'To Bookmark'
			}, {
			    text : 'Note'
			} ]
		    });
		    e.stopEvent();
		    var xy = e.getXY();
		    // xy[1] = 0;
		    contextMenu.showAt(xy[0], xy[1]);
		}
	    }
	};

	this.callParent(arguments);
    }
});