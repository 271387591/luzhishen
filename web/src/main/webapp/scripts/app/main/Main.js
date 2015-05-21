Ext.define('App.main.Main', {
    extend: 'Ext.container.Container',
    requires: [
        'App.main.MainController',
        'App.main.MainHeader',
        'App.main.MainMenu'
    ],
    controller: 'main',
    layout: {
        type: 'border'
    },
    xtype:'app-main',
    items: [
        {
            xtype:'mainHeader'
        },
        {
        xtype: 'mainMenu'
    },{
        region: 'center',
        xtype: 'tabpanel',
        items:[
            
        ]
    }]
});
