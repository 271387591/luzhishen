/**
 * Created by lihao on 4/14/15.
 */
Ext.define('App.model.TableField', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'cid',
        'name',
        'availability',
        'type',
        'length',
        'isPrimaryKey',
        'isnull',
        'differential',
        'isNow',
        'inputType',
        'isQuery',
        'dataSourceId',
        'inputValueFiled',
        'inputDisplayField',
        'isUnique',
        'gridHeader',
        'indexDex',
        'treeParent',
        'treeId',
        'isDept'
    ]
});