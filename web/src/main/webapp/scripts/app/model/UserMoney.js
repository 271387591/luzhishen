/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.model.UserMoney', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'username',
        'mobile',
        'email',
        'total',
        'gender',
        'lastType',
        'lastUsername',
        'lastUserId',
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