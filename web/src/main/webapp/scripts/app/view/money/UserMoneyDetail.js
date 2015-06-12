/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.view.money.UserMoneyDetail', {
    extend: 'Lzs.ux.window.AnimWindow',
    alias: 'widget.userMoneyDetail',
    requires: [
    ],

    controller: 'userMoneyController',
    viewModel: {
        type: 'userMoneyModel'
    },
    layout: 'fit',
    resizable: false,
    modal: true,
    width: document.body.clientWidth-300,
    border:true,
    moneyId:null,
    items:[
        {
            xtype:'tabpanel',
            items:[
                {
                    title:'收入明细',
                    xtype:'grid',
                    bind:{
                        store:'{buyDetails}'
                    },
                    autoScroll:true,
                    listeners:{
                        afterrender:function(f){
                            var store= f.up('userMoneyDetail').getViewModel().getStore('buyDetails');
                            var proxy = store.getProxy();
                            var moneyId= f.up('userMoneyDetail').moneyId;
                            if(moneyId){
                                proxy.extraParams={
                                    'moneyId':moneyId,
                                    'type':0
                                };
                                store.load();
                            }
                        }
                    },
                    dockedItems:[{
                        xtype: 'pagingtoolbar',
                        bind:{
                            store:'{buyDetails}'
                        },
                        dock: 'bottom',
                        displayInfo: true
                    }],
                    columns:[
                        {
                            flex:1,
                            text:'交易时间',
                            dataIndex:'lastUpdateDate'
                        },
                        {
                            flex:1,
                            text:'交易金额',
                            dataIndex:'total'
                        },
                        {
                            flex:1,
                            text:'购买积分',
                            dataIndex:'credits'
                        },
                        {
                            flex:1,
                            text:'购买单价',
                            dataIndex:'price'
                        },
                        {
                            flex:1,
                            text:'对方账号',
                            dataIndex:'dealName'
                        }
                    ]
                },
                {
                    title:'提现明细',
                    xtype:'grid',
                    autoScroll:true,
                    bind:{
                        store:'{saleDetails}'
                    },
                    listeners:{
                        afterrender:function(f){
                            var store= f.up('userMoneyDetail').getViewModel().getStore('saleDetails');
                            var proxy = store.getProxy();
                            var moneyId= f.up('userMoneyDetail').moneyId;
                            if(moneyId){
                                proxy.extraParams={
                                    'moneyId':moneyId,
                                    'type':1
                                };
                                store.load();
                            }
                        }
                    },
                    dockedItems:[{
                        xtype: 'pagingtoolbar',
                        bind:{
                            store:'{saleDetails}'
                        },
                        dock: 'bottom',
                        displayInfo: true
                    }],
                    columns:[
                        {
                            flex:1,
                            text:'提现时间',
                            dataIndex:'lastUpdateDate'
                        },
                        {
                            flex:1,
                            text:'提现数量',
                            dataIndex:'total'
                        },{
                            flex:1,
                            text:'操作人',
                            dataIndex:'lastUsername'
                        }
                    ]
                },
                {
                    title:'其他明细',
                    xtype:'grid',
                    autoScroll:true,
                    bind:{
                        store:'{otherDetails}'
                    },
                    listeners:{
                        afterrender:function(f){
                            var store= f.up('userMoneyDetail').getViewModel().getStore('otherDetails');
                            var proxy = store.getProxy();
                            var moneyId= f.up('userMoneyDetail').moneyId;
                            if(moneyId){
                                proxy.extraParams={
                                    'moneyId':moneyId,
                                    'type':2
                                };
                                store.load();
                            }
                        }
                    },
                    dockedItems:[{
                        xtype: 'pagingtoolbar',
                        bind:{
                            store:'{otherDetails}'
                        },
                        dock: 'bottom',
                        displayInfo: true
                    }],
                    columns:[
                        {
                            flex:1,
                            text:'操作时间',
                            dataIndex:'lastUpdateDate'
                        },
                        {
                            flex:1,
                            text:'金额',
                            dataIndex:'total'
                        },
                        {
                            flex:1,
                            text:'操作人',
                            dataIndex:'lastUsername'
                        },
                        {
                            flex:1,
                            header: '操作类型',
                            dataIndex: 'type',
                            renderer: function(v){
                                if(v==2){
                                    return '管理员修改';
                                }else{
                                    return '无';
                                }
                            }
                        }
                    ]
                }
            ]
        }
    ]
});