/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.view.credits.CreditsDetail', {
    extend: 'Lzs.ux.window.AnimWindow',
    alias: 'widget.creditsDetail',
    requires: [
    ],

    controller: 'userCreditsController',
    viewModel: {
        type: 'userCreditsModel'
    },
    layout: 'fit',
    resizable: false,
    modal: true,
    width: document.body.clientWidth-300,
    border:true,
    creditsId:null,
    items:[
        {
            xtype:'tabpanel',
            items:[
                {
                    title:'买入积分明细',
                    xtype:'grid',
                    bind:{
                        store:'{buyDetails}'
                    },
                    autoScroll:true,
                    listeners:{
                        afterrender:function(f){
                            var store= f.up('creditsDetail').getViewModel().getStore('buyDetails');
                            var proxy = store.getProxy();
                            var creditsId= f.up('creditsDetail').creditsId;
                            if(creditsId){
                                proxy.extraParams={
                                    'creditsId':creditsId,
                                    'type':2
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
                            text:'交易数量',
                            dataIndex:'total'
                        },
                        {
                            flex:1,
                            text:'对方账号',
                            dataIndex:'dealName'
                        },
                        {
                            flex:1,
                            text:'设备ID',
                            dataIndex:'ssid'
                        },
                        {
                            flex:1,
                            text:'设备Mac地址',
                            dataIndex:'bssid'
                        }
                    ]
                },
                {
                    title:'出售积分明细',
                    xtype:'grid',
                    autoScroll:true,
                    bind:{
                        store:'{saleDetails}'
                    },
                    listeners:{
                        afterrender:function(f){
                            var store= f.up('creditsDetail').getViewModel().getStore('saleDetails');
                            var proxy = store.getProxy();
                            var creditsId= f.up('creditsDetail').creditsId;
                            if(creditsId){
                                proxy.extraParams={
                                    'creditsId':creditsId,
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
                            text:'交易时间',
                            dataIndex:'lastUpdateDate'
                        },
                        {
                            flex:1,
                            text:'交易数量',
                            dataIndex:'total'
                        },
                        {
                            flex:1,
                            text:'对方账号',
                            dataIndex:'dealName'
                        },
                        {
                            flex:1,
                            text:'设备ID',
                            dataIndex:'ssid'
                        },
                        {
                            flex:1,
                            text:'设备Mac地址',
                            dataIndex:'bssid'
                        }
                    ]
                },
                {
                    title:'其他积分明细',
                    xtype:'grid',
                    autoScroll:true,
                    bind:{
                        store:'{otherDetails}'
                    },
                    listeners:{
                        afterrender:function(f){
                            var store= f.up('creditsDetail').getViewModel().getStore('otherDetails');
                            var proxy = store.getProxy();
                            var creditsId= f.up('creditsDetail').creditsId;
                            if(creditsId){
                                proxy.extraParams={
                                    'creditsId':creditsId,
                                    'type':3
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
                            text:'积分数量',
                            dataIndex:'total'
                        },
                        {
                            flex:1,
                            text:'WIFI积分',
                            dataIndex:'wifi'
                        },
                        {
                            flex:1,
                            text:'GPRS积分',
                            dataIndex:'gprs'
                        },
                        {
                            flex:1,
                            text:'设备ID',
                            dataIndex:'ssid'
                        },
                        {
                            flex:1,
                            text:'设备Mac地址',
                            dataIndex:'bssid'
                        },
                        {
                            flex:1,
                            header: '操作类型',
                            dataIndex: 'type',
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
                        }
                    ]
                }
            ]
        }
    ]
});