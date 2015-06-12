/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.model.CreditsDetail', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'type',
        'dealId',
        'dealName',
        'creditsId',
        'gprs',
        'wifi',
        'ssid',
        'bssid',
        'total',
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