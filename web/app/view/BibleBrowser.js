Ext.define('GW.view.BibleBrowser', {
    extend : 'Ext.window.Window',
    alias : 'widget.biblebrowser',
    constrain : true,
    title : 'Select Bible',
    autoScroll : true,
    width : 338,
    cls : 'bibleBrowser',
    constructor : function(config) {
	var me = this;
	this.addEvents('bible_selected');

	config.modal = true;
	config.closeAction = 'hide';
	config.items = [ {
	    xtype : 'dataview',
	    store : 'Bible',
	    overItemCls : 'bible-over',
	    selectedItemCls : 'selected-bible',
	    itemSelector : 'div.bible',
	    tpl : new Ext.XTemplate('<tpl for="."><div class="bible {id}"><a title="{title}">{title}</a></div></tpl>'),
	    listeners : {
		itemclick : function(dataView, record, item, index, e, eOpts) {
		    me.fireEvent('bible_selected', record);
		    me.close();
		}
	    }
	} ];

	this.callParent(arguments);
    }

});