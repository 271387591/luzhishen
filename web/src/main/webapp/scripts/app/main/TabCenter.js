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
        'Lzs.ux.window.AnimWindow',
        'App.view.user.UserCenter',
        'App.view.appstore.AppStore',
        'App.view.credits.UserCredits',
        'App.view.order.OrderList',
        'App.view.order.OrderChart',
        'App.view.money.UserMoney',
        'App.view.money.UserApply'
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
        xtype: 'userApply',
        title: '提现管理',
        iconCls: 'exec-quarterly-icon'
    },{
        xtype: 'userMoney',
        title: '平台金管理',
        iconCls: 'exec-quarterly-icon'
    },{
        xtype: 'panel',
        title: '订单查询',
        iconCls: 'exec-quarterly-icon',
            layout:'border',
            items:[
                {
                    xtype:'orderList',
                    region:'center'
                },
                {
                    xtype:'orderChart',
                    region:'west'
                }
            ]

    },{
        xtype: 'userCredits',
        title: '积分管理',
        iconCls: 'exec-pl-icon'
    },{
        xtype: 'userCenter',
        title: '用户模块',
        iconCls: 'exec-news-icon'
    },{
        xtype: 'appStore',
        title: '应用版本管理',
        iconCls: 'exec-news-icon'
    }]
});