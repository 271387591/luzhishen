/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.view.money.UserApplyModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.userApplyModel',
    requires:[
        'App.model.UserMoney'
    ],
    data: {
        rec: {}
    },
    stores:{
        nopay:{
            model:'App.model.UserMoney',
            proxy:{
                type: 'ajax',
                url: 'apply/listApply',
                reader: {
                    type: 'json',
                    root : 'data',
                    totalProperty  : 'total',
                    messageProperty: 'message'
                },
                extraParams:{
                    Q_status_EQ_N:0
                }
            }
        },
        successes:{
            model:'App.model.UserMoney',
            proxy:{
                type: 'ajax',
                url: 'apply/listApply',
                reader: {
                    type: 'json',
                    root : 'data',
                    totalProperty  : 'total',
                    messageProperty: 'message'
                },
                extraParams:{
                    Q_status_EQ_N:1
                }
            }
        },
        fails:{
            model:'App.model.UserMoney',
            proxy:{
                type: 'ajax',
                url: 'apply/listApply',
                reader: {
                    type: 'json',
                    root : 'data',
                    totalProperty  : 'total',
                    messageProperty: 'message'
                },
                extraParams:{
                    Q_status_EQ_N:2
                }
            }
        },
        cancels:{
            model:'App.model.UserMoney',
            proxy:{
                type: 'ajax',
                url: 'apply/listApply',
                reader: {
                    type: 'json',
                    root : 'data',
                    totalProperty  : 'total',
                    messageProperty: 'message'
                },
                extraParams:{
                    Q_status_EQ_N:3
                }
            }
        }

    }
});