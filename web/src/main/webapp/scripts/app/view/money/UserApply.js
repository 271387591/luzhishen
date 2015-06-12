/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.view.money.UserApply', {
    extend: 'Ext.tab.Panel',
    alias: 'widget.userApply',
    requires: [
        'App.view.money.UserApplyModel',
        'App.view.money.UserApplyController',
        'Lzs.ux.grid.Search',
    ],
    controller: 'userApplyController',
    viewModel: {
        type: 'userApplyModel'
    },
    listeners:{
        tabchange:'tabChange'
    },
    items:[
        {
            xtype:'grid',
            title:'待处理申请',
            selType: 'checkboxmodel',
            border: false,
            autoScroll: true,
            bind:{
                store:'{nopay}'
            },
            itemId:'nopayGrid',
            listeners:{
                afterrender:function(f){
                    f.up('userApply').getViewModel().getStore('nopay').load();
                }
            },
            tbar:[
                {
                    text:'批量支付',
                    handler:'bachPay'
                },
                '->',
                {
                    fieldLabel:'批次号',
                    labelWidth:65,
                    xtype:'textfield',
                    bind:'{rec.Q_batchNo_LK}'
                },
                {
                    fieldLabel:'用户账号',
                    labelWidth:65,
                    xtype:'textfield',
                    bind:'{rec.Q_name_LK}'
                },
                {
                    fieldLabel:'流水号',
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
                    store:'{nopay}'
                },
                dock: 'bottom',
                displayInfo: true
            }],
            columns: [
                {
                    width:150,
                    header: '批次号',
                    dataIndex: 'batchNo'
                },
                {
                    width:150,
                    header: '流水号',
                    dataIndex: 'applyNo'
                },

                {
                    header: '申请账号',
                    flex:1,
                    dataIndex: 'username'
                },
                {
                    header: '申请金额',
                    flex:1,
                    dataIndex: 'total'
                },
                {
                    header: '申请时间',
                    flex:1,
                    dataIndex: 'createDate'
                },
                {
                    header: '申请人支付宝账号',
                    flex:1,
                    dataIndex: 'email'
                },
                {
                    header: '申请人姓名',
                    flex:1,
                    dataIndex: 'name'
                },
                {
                    header: '状态',
                    flex:1,
                    dataIndex: 'status',
                    renderer: function(v){
                        if(v==0){
                            return '<font color="#5f9ea0">待处理</font>';
                        }else if(v==1){
                            return '提现成功';
                        }else if(v==2){
                            return '<font color="red">提现失败</font>';
                        }
                    }
                }
            ]
        },
        {
            xtype:'grid',
            title:'已提现列表',
            border: false,
            autoScroll: true,
            bind:{
                store:'{successes}'
            },
            listeners:{
                afterrender:function(f){
                    f.up('userApply').getViewModel().getStore('successes').load();
                }
            },
            tbar:[
                '->',
                {
                    fieldLabel:'批次号',
                    labelWidth:65,
                    xtype:'textfield',
                    bind:'{rec.Q_batchNo_LK}'
                },
                {
                    fieldLabel:'用户账号',
                    labelWidth:65,
                    xtype:'textfield',
                    bind:'{rec.Q_name_LK}'
                },
                {
                    fieldLabel:'流水号',
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
                    store:'{successes}'
                },
                dock: 'bottom',
                displayInfo: true
            }],
            columns: [
                {
                    width:150,
                    header: '批次号',
                    dataIndex: 'batchNo'
                },
                {
                    width:150,
                    header: '流水号',
                    dataIndex: 'applyNo'
                },
                {
                    header: '提现账号',
                    flex:1,
                    dataIndex: 'username'
                },
                {
                    header: '支付宝账号',
                    flex:1,
                    dataIndex: 'email'
                },
                {
                    header: '提现人姓名',
                    flex:1,
                    dataIndex: 'name'
                },
                {
                    header: '提现金额',
                    flex:1,
                    dataIndex: 'total'
                },
                {
                    header: '申请时间',
                    flex:1,
                    dataIndex: 'createDate'
                },
                {
                    header: '处理时间',
                    flex:1,
                    dataIndex: 'lastUpdateDate'
                },
                {
                    header: '支付宝返回信息',
                    width:350,
                    dataIndex: 'successDetail'
                },

                {
                    header: '状态',
                    flex:1,
                    dataIndex: 'status',
                    renderer: function(v){
                        if(v==0){
                            return '<font color="#5f9ea0">待处理</font>';
                        }else if(v==1){
                            return '提现成功';
                        }else if(v==2){
                            return '<font color="red">提现失败</font>';
                        }
                    }
                }
            ]
        },
        {
            xtype:'grid',
            title:'提现失败列表',
            border: false,
            autoScroll: true,
            itemId:'userapplyFailGrid',
            bind:{
                store:'{fails}'
            },
            listeners:{
                afterrender:function(f){
                    f.up('userApply').getViewModel().getStore('fails').load();
                }
            },
            selType: 'checkboxmodel',
            tbar:[
                {
                    text:'批量支付',
                    handler:'bachPay1'
                },
                {
                    text:'批量取消用户提现',
                    handler:'cancelApply'
                },

                '->',
                {
                    fieldLabel:'批次号',
                    labelWidth:65,
                    xtype:'textfield',
                    bind:'{rec.Q_batchNo_LK}'
                },
                {
                    fieldLabel:'用户账号',
                    labelWidth:65,
                    xtype:'textfield',
                    bind:'{rec.Q_name_LK}'
                },
                {
                    fieldLabel:'流水号',
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
                    header: '批次号',
                    dataIndex: 'batchNo'
                },
                {
                    width:150,
                    header: '流水号',
                    dataIndex: 'applyNo'
                },
                {
                    header: '提现账号',
                    flex:1,
                    dataIndex: 'username'
                },
                {
                    header: '支付宝账号',
                    flex:1,
                    dataIndex: 'email'
                },
                {
                    header: '提现人姓名',
                    flex:1,
                    dataIndex: 'name'
                },
                {
                    header: '提现金额',
                    flex:1,
                    dataIndex: 'total'
                },
                {
                    header: '申请时间',
                    flex:1,
                    dataIndex: 'createDate'
                },
                {
                    header: '处理时间',
                    flex:1,
                    dataIndex: 'lastUpdateDate'
                },
                {
                    header: '支付宝返回信息',
                    width:350,
                    dataIndex: 'failDetail'
                },
                {
                    header: '状态',
                    flex:1,
                    dataIndex: 'status',
                    renderer: function(v){
                        if(v==0){
                            return '<font color="#5f9ea0">待处理</font>';
                        }else if(v==1){
                            return '提现成功';
                        }else if(v==2){
                            return '<font color="red">提现失败</font>';
                        }else if(v==3){
                            return '管理员取消';
                        }
                    }
                }
            ]
        }
        ,
        {
            xtype:'grid',
            title:'已取消列表',
            border: false,
            autoScroll: true,
            itemId:'userapplyCancelGrid',
            bind:{
                store:'{cancels}'
            },
            listeners:{
                afterrender:function(f){
                    f.up('userApply').getViewModel().getStore('cancels').load();
                }
            },
            selType: 'checkboxmodel',
            tbar:[
                '->',
                {
                    fieldLabel:'批次号',
                    labelWidth:65,
                    xtype:'textfield',
                    bind:'{rec.Q_batchNo_LK}'
                },
                {
                    fieldLabel:'用户账号',
                    labelWidth:65,
                    xtype:'textfield',
                    bind:'{rec.Q_name_LK}'
                },
                {
                    fieldLabel:'流水号',
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
                    store:'{cancels}'
                },
                dock: 'bottom',
                displayInfo: true
            }],
            columns: [
                {
                    width:150,
                    header: '批次号',
                    dataIndex: 'batchNo'
                },
                {
                    width:150,
                    header: '流水号',
                    dataIndex: 'applyNo'
                },
                {
                    header: '提现账号',
                    flex:1,
                    dataIndex: 'username'
                },
                {
                    header: '支付宝账号',
                    flex:1,
                    dataIndex: 'email'
                },
                {
                    header: '提现人姓名',
                    flex:1,
                    dataIndex: 'name'
                },
                {
                    header: '提现金额',
                    flex:1,
                    dataIndex: 'total'
                },
                {
                    header: '申请时间',
                    flex:1,
                    dataIndex: 'createDate'
                },
                {
                    header: '处理时间',
                    flex:1,
                    dataIndex: 'lastUpdateDate'
                },
                {
                    header: '取消原因',
                    width:350,
                    dataIndex: 'cancelDetail'
                },
                {
                    header: '状态',
                    flex:1,
                    dataIndex: 'status',
                    renderer: function(v){
                        if(v==0){
                            return '<font color="#5f9ea0">待处理</font>';
                        }else if(v==1){
                            return '提现成功';
                        }else if(v==2){
                            return '<font color="red">提现失败</font>';
                        }else if(v==3){
                            return '管理员取消';
                        }
                    }
                }
            ]
        }

    ]
});