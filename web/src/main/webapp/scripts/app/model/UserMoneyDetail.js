/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.model.UserMoneyDetail', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'type',
        'dealId',
        'dealName',
        'moneyId',
        'total',
        'lastUsername',
        'credits',
        'price',
        {
            name: 'createDate', convert: function (v) {
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