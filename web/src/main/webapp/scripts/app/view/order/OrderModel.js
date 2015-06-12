/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.view.order.OrderModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.orderModel',
    requires:[
        'App.model.Order'
    ],
    data: {
        rec: {}
    },
    stores:{
        nopays:{
            model:'App.model.Order',
            proxy:{
                type: 'ajax',
                url: 'order/listOrders',
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
        pays:{
            model:'App.model.Order',
            proxy:{
                type: 'ajax',
                url: 'order/listOrders',
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
            model:'App.model.Order',
            proxy:{
                type: 'ajax',
                url: 'order/listOrders',
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

        }

    }
});