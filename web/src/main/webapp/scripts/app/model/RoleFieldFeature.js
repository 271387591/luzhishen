/**
 * Created by lihao1 on 5/5/15.
 */
Ext.define('App.model.RoleFieldFeature', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'roleId',
        'fieldId',
        'tableId',
        'canUpdate',
        'canAdd',
        'canQuery',
        'fieldCid',
        'fieldName',
        'isDept',
        'isCreator'
    ]
});