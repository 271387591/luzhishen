Ext.define('App.main.Main', {
    extend: 'Ext.container.Container',
    xtype: 'app-main',
    requires: [
        'App.main.MainModel',
        'App.main.MainController',
        'App.main.MainHeader',
        'App.main.TabCenter'
    ],
    controller: 'main',
    viewModel: {
        type: 'main'
    },
    layout: {
        type: 'border'
    },
    items: [
        {
            xtype:'mainHeader',
            region:'north'
        },
        {
            xtype: 'tabCenter',
            region:'center'
        }
    ]
});
