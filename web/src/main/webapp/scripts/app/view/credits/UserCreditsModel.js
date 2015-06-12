/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.view.credits.UserCreditsModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.userCreditsModel',
    requires:[
        'App.model.UserCredits',
        'App.model.CreditsDetail'
    ],
    data: {
        rec: {},
        detailColumn:[
            {
                text:'交易时间',
                dataIndex:'lastUpdateDate'
            },
            {
                text:'交易数量',
                dataIndex:'total'
            },
            {
                text:'对方账号',
                dataIndex:'dealName'
            }
        ]
    },
    stores:{
        credits:{
            model:'App.model.UserCredits',
            proxy:{
                type: 'ajax',
                url: 'credits/listCredits',
                reader: {
                    type: 'json',
                    root : 'data',
                    totalProperty  : 'total',
                    messageProperty: 'message'
                }
            },
            extraParams:{}
        },
        buyDetails:{
            //autoLoad:true,
            model:'App.model.CreditsDetail',
            proxy:{
                type: 'ajax',
                url: 'credits/listDetails',
                reader: {
                    type: 'json',
                    root : 'data',
                    totalProperty  : 'total',
                    messageProperty: 'message'
                }
            },
            extraParams:{
                Q_type_EQ:2
            }
        },
        saleDetails:{
            model:'App.model.CreditsDetail',
            proxy:{
                type: 'ajax',
                url: 'credits/listDetails',
                reader: {
                    type: 'json',
                    root : 'data',
                    totalProperty  : 'total',
                    messageProperty: 'message'
                }
            },
            extraParams:{
                Q_type_EQ:1
            }
        },
        otherDetails:{
            model:'App.model.CreditsDetail',
            proxy:{
                type: 'ajax',
                url: 'credits/listDetails',
                reader: {
                    type: 'json',
                    root : 'data',
                    totalProperty  : 'total',
                    messageProperty: 'message'
                }
            },
            extraParams:{
                Q_type_NEQ:1,
                Q_type_NEQ:2
            }
        }
    }
});