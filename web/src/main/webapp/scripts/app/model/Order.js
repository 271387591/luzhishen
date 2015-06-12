/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.model.Order', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'orderNo',
        'money',
        'credits',
        'price',
        'status',
        'createId',
        'createUsername',
        'tradeNo',
        'tradeStatus',
        {
            name: 'createDate', convert: function (v) {
            return Ext.util.Format.date(new Date(v), 'Y-m-d H:i:s');
        }
        },
        {
            name: 'loseDate', convert: function (v) {
            return Ext.util.Format.date(new Date(v), 'Y-m-d H:i:s');
        }
        },
        {
            name: 'lastUpdateDate', convert: function (v) {
            return Ext.util.Format.date(new Date(v), 'Y-m-d H:i:s');
        }
        }
    ]
});