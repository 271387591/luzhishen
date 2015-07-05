/**
 * Created by lihao1 on 6/18/15.
 */
Ext.define('App.model.Comments', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'comment',
        'contract',
        'username',
        {
            name: 'createDate', convert: function (v) {
            return Ext.util.Format.date(new Date(v), 'Y-m-d H:i:s');
        }
        }
    ]
});