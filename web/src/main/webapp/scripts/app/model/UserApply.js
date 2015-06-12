/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.model.UserApply', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'username',
        'mobile',
        'total',
        'gender',
        'lastUsername',
        'lastUserId',
        'applyNo',
        'status',
        'email',
        'name',
        'batchNo',
        'successDetail',
        'failDetail',
        'cancelDetail',
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