Ext.define('GW.view.Search', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.search',
	title: 'Search',
	title: 'Search',
	cls: 'x-search',
	collapseFirst: false,
	tools: [{
        type: 'gear',
        tooltip: 'Search options',
        handler: function(){
            // show help here
        }
    }],
	items: [
	        {
	        	xtype: 'form',
	        	itemId: 'searchForm',
	        	border: false,
	        	fieldDefaults: {
	        		labelWidth: 55
	        	},
	        	defaultType: 'textfield',
	        	bodyPadding: 5,
	        	items: [
	        	        {
	        	        	fieldLabel: 'Text',
	        	        	name: 'to',
	        	        	anchor:'100%'
	        	        },
	        	        {
	        	        	xtype: 'button',
					    	text: 'Search',
					    	handler: function(btn, event){
					    		var query = btn.prev().getValue();
					    		if(!Ext.isEmpty(query)){
					    			// Fire event
					    			this.up().up().fireEvent('search', event, query);
					    		}
						    }
	        	        }
	        	]
	        },
	        {
	        	xtype: 'versepanel',
	        	itemId: 'searchResults'
	        },
	        {
	        	xtype: 'panel',
	        	itemId: 'noSearchResults',
	        	html: 'No search results',
	        	border: false,
	        	hidden: true
	        }
	],
	
	setVerses: function(verses){
		if(verses.length>0){
			this.down('#searchResults').setVerses(verses).show();
			this.down('#noSearchResults').hide();
		}
		else{
			this.down('versepanel').hide();
			this.down('#noSearchResults').show();
		}
		
		return this;
	}
});