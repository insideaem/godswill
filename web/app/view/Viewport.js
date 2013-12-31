Ext.define('GW.view.Viewport', {
    extend : 'Ext.container.Viewport',
    alias : 'widget.bibleapp',
    requires : [ 'GW.view.BibleContent' ],
    layout : 'fit',
    items : {
	xtype : 'biblecontent',
	hidden : true
    }
});