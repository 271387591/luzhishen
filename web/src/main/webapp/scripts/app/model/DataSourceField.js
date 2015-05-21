/**
 * Created by lihao1 on 5/1/15.
 */
Ext.define('App.model.DataSourceField', {
    extend: 'Ext.data.Model',
    idProperty:'name',
    fields: [
        'name',
        'type',
        'displayName',
        'saveDatabase',
        'gridHeader',
        'parent',
        'treeId'
    ]
});