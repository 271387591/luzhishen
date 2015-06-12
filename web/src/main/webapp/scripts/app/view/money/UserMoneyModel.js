/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.view.money.UserMoneyModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.userMoneyModel',
    requires:[
        'App.model.UserMoney',
        'App.model.UserMoneyDetail'
    ],
    data: {
        rec: {}
    },
    stores:{
        money:{
            model:'App.model.UserMoney',
            proxy:{
                type: 'ajax',
                url: 'money/listMoney',
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
            model:'App.model.UserMoneyDetail',
            proxy:{
                type: 'ajax',
                url: 'money/listDetails',
                reader: {
                    type: 'json',
                    root : 'data',
                    totalProperty  : 'total',
                    messageProperty: 'message'
                }
            }
        },
        saleDetails:{
            model:'App.model.UserMoneyDetail',
            proxy:{
                type: 'ajax',
                url: 'money/listDetails',
                reader: {
                    type: 'json',
                    root : 'data',
                    totalProperty  : 'total',
                    messageProperty: 'message'
                }
            }
        },
        otherDetails:{
            model:'App.model.UserMoneyDetail',
            proxy:{
                type: 'ajax',
                url: 'money/listDetails',
                reader: {
                    type: 'json',
                    root : 'data',
                    totalProperty  : 'total',
                    messageProperty: 'message'
                }
            }
        }
    }
});