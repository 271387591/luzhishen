/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.view.money.UserMoney', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.userMoney',
    requires: [
        'App.view.money.UserMoneyModel',
        'App.view.money.UserMoneyController',
        'Lzs.ux.grid.Search',
        'App.view.money.UserMoneyDetail',
        'App.view.money.UserMoneyForm'
    ],
    controller: 'userMoneyController',
    viewModel: {
        type: 'userMoneyModel'
    },
    tbar: [
        {
            iconCls:'user-add',
            text:'修改平台金',
            handler: 'onAddClick'
        }
    ],
    border: false,
    forceFit: true,
    autoScroll: true,
    bind:{
        store:'{money}'
    },
    listeners:{
        afterrender:function(f){
            f.getStore().load();
        }
    },
    features:[{
        ftype: 'search',
        disableIndexes: ['username'],
        paramNames: {
            fields: ['Q_creator.username_LK_OR_S'],
            query: 'keyword'
        },
        searchMode: 'remote'
    }],


    dockedItems:[{
        xtype: 'pagingtoolbar',
        bind:{
            store:'{money}'
        },
        dock: 'bottom',
        displayInfo: true
    }],
    columns: [
        {
            header: userRoleRes.header.username,
            dataIndex: 'username'
        },
        {
            header: '手机号',
            dataIndex: 'mobile'
        },
        {
            header: '总金额',
            dataIndex: 'total'
        },
        {
            header: '最近操作时间',
            dataIndex: 'lastUpdateDate'
        },
        {
            header: '最近操作类型',
            dataIndex: 'lastType',
            renderer: function(v){
                if(v==0){
                    return '出售所得';
                }else if(v==1){
                    return '提现';
                }else if(v==2){
                    return '管理员修改';
                }else{
                    return '无';
                }
            }
        },
        {
            xtype:'actioncolumn',
            width:120,
            header: '操作',
            items:[
                {
                    iconCls:'btn-flowView',
                    tooltip:'查看金额明细',
                    handler:'detailClick'
                }
            ]
        }
    ]
});