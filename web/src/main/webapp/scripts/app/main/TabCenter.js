/**
 * Created by lihao1 on 5/23/15.
 */
Ext.define('App.main.TabCenter', {
    extend: 'Ext.tab.Panel',
    xtype: 'tabCenter',
    requires: [
        'App.main.MainModel',
        'App.main.MainController',
        'App.main.MainHeader',
        'Ext.ux.window.AnimWindow',
        'App.view.user.UserCenter'
    ],
    controller: 'main',
    viewModel: {
        type: 'main'
    },
    ui: 'navigation',
    cls: 'exec-menu-navigation',
    tabBarHeaderPosition: 1,
    titleRotation: 0,
    tabRotation: 0,
    header: {
        layout: {
            align: 'stretchmax'
        },
        iconCls: 'exec-header-icon',
        title: {
            text: 'MyBiz',
            textAlign: 'center',
            flex: 0,
            minWidth: 50
        },
        tools: [{
            type: 'gear',
            plugins: 'responsive',
            width: 120,
            margin: '0 0 0 0',
            handler: 'onSwitchTool',
            responsiveConfig: {
                'width < 768 && tall': {
                    visible: true
                },
                'width >= 768': {
                    visible: false
                }
            }
        }]
    },
    tabBar: {
        flex: 1,
        layout: {
            align: 'stretch',
            overflowHandler: 'none'
        }
    },
    items: [
        {
        xtype: 'panel',
        title: '首页',
            items:[
                {
                    xtype:'button',
                    text:'sddf',
                    handler:function(btn){
                        var awin=Ext.create('Ext.ux.window.AnimWindow',{
                            width:1100,
                            height:this.up('panel').getHeight()
                        });
                        awin.animShow();
                    }
                },
                {
                    xtype:'textfield'
                },
                {
                    xtype:'textfield'
                },
                {
                    xtype:'textfield'
                },
                {
                    xtype:'textfield',
                    allowBlank: false
                },


            ]
    },{
        xtype: 'panel',
        title: 'Performance',
        iconCls: 'exec-quarterly-icon',
        html:'sdf111'
    },{
        xtype: 'panel',
        title: 'Profit & Loss',
        iconCls: 'exec-pl-icon'
    },{
        xtype: 'userCenter',
        title: '用户模块',
        iconCls: 'exec-news-icon'
    }]
});