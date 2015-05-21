Ext.define('App.main.MainMenu', {
	extend: 'Ext.tree.Panel',
	alias: 'widget.mainMenu',
	requires: [
        'App.store.MainMenu'
	],
	title:'导航',
	initComponent: function () {
		var me = this;

        var store = Ext.create('App.store.MainMenu');
        //store.load();
		
		Ext.apply(this, {
			itemId: 'treeNav',
			region: 'west',
			split: true,
			width: 212,
			minSize: 130,
			maxSize: 300,
			rootVisible: false,
			containerScroll: true,
			autoScroll: false,
			store: store,
            listeners:{
                afterrender:function(){
                    this.expandAll();
                }
            }
		});
		this.callParent(arguments);
		
	}
});
