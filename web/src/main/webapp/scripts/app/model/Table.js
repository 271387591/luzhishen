/**
 * Created by lihao on 4/14/15.
 */
Ext.define('App.model.Table', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'name',
        'displayName',
        'fields',
        'createDate'
    ]
});