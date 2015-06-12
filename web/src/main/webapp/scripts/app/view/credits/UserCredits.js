/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.view.credits.UserCredits', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.userCredits',
    requires: [
        'App.view.credits.UserCreditsModel',
        'App.view.credits.UserCreditsController',
        'Lzs.ux.grid.Search',
        'App.view.credits.UserCreditsForm',
        'App.view.credits.CreditsDetail'
    ],
    controller: 'userCreditsController',
    viewModel: {
        type: 'userCreditsModel'
    },
    tbar: [
        {
            iconCls:'user-add',
            text:'修改积分',
            handler: 'onAddClick'
        }
    ],
    border: false,
    forceFit: true,
    autoScroll: true,
    bind:{
        store:'{credits}'
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
            fields: ['Q_user.username_LK_OR_S'],
            query: 'keyword'
        },
        searchMode: 'remote'
    }],


    dockedItems:[{
        xtype: 'pagingtoolbar',
        bind:{
            store:'{credits}'
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
            header: '总积分',
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
                    return '积分上传';
                }else if(v==1){
                    return '出售积分';
                }else if(v==2){
                    return '买入积分';
                }else if(v==3){
                    return '赠予积分';
                }else if(v==4){
                    return '接收积分';
                }else if(v==5){
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
                    tooltip:'查看积分明细',
                    handler:'detailClick'
                }
            ]
        }
    ]
});