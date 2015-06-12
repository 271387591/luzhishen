/**
 * Created by lihao1 on 5/29/15.
 */
Ext.define('App.view.order.OrderList', {
    extend: 'Ext.tab.Panel',
    alias: 'widget.orderList',
    requires: [
        'App.view.order.OrderModel',
        'App.view.order.OrderController'
    ],
    controller: 'orderController',
    viewModel: {
        type: 'orderModel'
    },
    listeners:{
        tabchange:'tabChange'
    },
    items:[
        {
            xtype:'grid',
            title:'待支付订单',
            border: false,
            autoScroll: true,
            bind:{
                store:'{nopays}'
            },
            listeners:{
                afterrender:function(f){
                    f.up('orderList').getViewModel().getStore('nopays').load();
                }
            },
            tbar:[
                '->',
                {
                    fieldLabel:'用户账号',
                    labelWidth:65,
                    xtype:'textfield',
                    bind:'{rec.Q_name_LK}'
                },
                {
                    fieldLabel:'订单编号',
                    labelWidth:65,
                    xtype:'textfield',
                    bind:'{rec.Q_serialNum_EQ}'
                },
                {
                    text:'查询',
                    handler:'searchClick'
                },
                {
                    text:'清空',
                    handler:'cleanClick'
                }
            ],
            dockedItems:[{
                xtype: 'pagingtoolbar',
                bind:{
                    store:'{nopays}'
                },
                dock: 'bottom',
                displayInfo: true
            }],
            columns: [
                {
                    width:150,
                    header: '订单编号',
                    dataIndex: 'orderNo'
                },
                {
                    header: '用户账号',
                    flex:1,
                    dataIndex: 'createUsername'
                },
                {
                    header: '订单金额',
                    flex:1,
                    dataIndex: 'money'
                },
                {
                    header: '购买积分',
                    flex:1,

                    dataIndex: 'credits'
                },
                {
                    header: '购买单价',
                    flex:1,

                    dataIndex: 'price'
                },

                {
                    header: '创建时间',
                    width:150,
                    dataIndex: 'createDate'
                },
                {
                    header: '失效时间',
                    width:150,

                    dataIndex: 'loseDate'
                },
                {
                    header: '支付状态',
                    flex:1,
                    dataIndex: 'status',
                    renderer: function(v){
                        if(v==0){
                            return '<font color="#5f9ea0">待支付</font>';
                        }else if(v==1){
                            return '已支付';
                        }else if(v==2){
                            return '<font color="red">支付失败</font>';
                        }
                    }
                }
            ]
        },
        {
            xtype:'grid',
            title:'已支付订单',
            border: false,
            autoScroll: true,
            bind:{
                store:'{pays}'
            },
            listeners:{
                afterrender:function(f){
                    f.up('orderList').getViewModel().getStore('pays').load();
                }
            },
            tbar:[
                '->',
                {
                    fieldLabel:'用户账号',
                    labelWidth:65,
                    xtype:'textfield',
                    bind:'{rec.Q_name_LK}'
                },
                {
                    fieldLabel:'订单编号',
                    labelWidth:65,
                    xtype:'textfield',
                    bind:'{rec.Q_serialNum_EQ}'
                },
                {
                    text:'查询',
                    handler:'searchClick'
                },
                {
                    text:'清空',
                    handler:'cleanClick'
                }
            ],
            dockedItems:[{
                xtype: 'pagingtoolbar',
                bind:{
                    store:'{pays}'
                },
                dock: 'bottom',
                displayInfo: true
            }],
            columns: [
                {
                    width:150,
                    header: '订单编号',
                    dataIndex: 'orderNo'
                },
                {
                    header: '用户账号',
                    flex:1,
                    dataIndex: 'createUsername'
                },
                {
                    header: '订单金额',
                    flex:1,
                    dataIndex: 'money'
                },
                {
                    header: '购买积分',
                    flex:1,

                    dataIndex: 'credits'
                },
                {
                    header: '购买单价',
                    flex:1,

                    dataIndex: 'price'
                },

                {
                    header: '创建时间',
                    width:150,
                    dataIndex: 'createDate'
                },

                {
                    header: '支付时间',
                    width:150,
                    dataIndex: 'lastUpdateDate'
                },
                {
                    header: '支付状态',
                    flex:1,
                    dataIndex: 'status',
                    renderer: function(v){
                        if(v==0){
                            return '<font color="#5f9ea0">待支付</font>';
                        }else if(v==1){
                            return '已支付';
                        }else if(v==2){
                            return '<font color="red">支付失败</font>';
                        }
                    }
                },
                {
                    header: '支付宝交易号',
                    flex:1,
                    dataIndex: 'tradeNo'
                },
                 {
                    header: '支付宝返回状态',
                    flex:1,
                    dataIndex: 'tradeStatus'
                }
            ]
        },
        {
            xtype:'grid',
            title:'支付失败订单',
            border: false,
            autoScroll: true,
            bind:{
                store:'{fails}'
            },
            listeners:{
                afterrender:function(f){
                    f.up('orderList').getViewModel().getStore('fails').load();
                }
            },
            tbar:[
                '->',
                {
                    fieldLabel:'用户账号',
                    labelWidth:65,
                    xtype:'textfield',
                    bind:'{rec.Q_name_LK}'
                },
                {
                    fieldLabel:'订单编号',
                    labelWidth:65,
                    xtype:'textfield',
                    bind:'{rec.Q_serialNum_EQ}'
                },
                {
                    text:'查询',
                    handler:'searchClick'
                },
                {
                    text:'清空',
                    handler:'cleanClick'
                }
            ],
            dockedItems:[{
                xtype: 'pagingtoolbar',
                bind:{
                    store:'{fails}'
                },
                dock: 'bottom',
                displayInfo: true
            }],
            columns: [
                {
                    width:150,
                    header: '订单编号',
                    dataIndex: 'orderNo'
                },
                {
                    header: '用户账号',
                    flex:1,
                    dataIndex: 'createUsername'
                },
                {
                    header: '订单金额',
                    flex:1,
                    dataIndex: 'money'
                },
                {
                    header: '购买积分',
                    flex:1,

                    dataIndex: 'credits'
                },
                {
                    header: '购买单价',
                    flex:1,

                    dataIndex: 'price'
                },

                {
                    header: '创建时间',
                    width:150,
                    dataIndex: 'createDate'
                },
                {
                    header: '支付状态',
                    flex:1,
                    dataIndex: 'status',
                    renderer: function(v){
                        if(v==0){
                            return '<font color="#5f9ea0">待支付</font>';
                        }else if(v==1){
                            return '已支付';
                        }else if(v==2){
                            return '<font color="red">支付失败</font>';
                        }
                    }
                },
                {
                    header: '支付宝交易号',
                    flex:1,
                    dataIndex: 'tradeNo'
                },
                {
                    header: '支付宝返回状态',
                    flex:1,
                    dataIndex: 'tradeStatus'
                }
            ]
        }
    ]
});