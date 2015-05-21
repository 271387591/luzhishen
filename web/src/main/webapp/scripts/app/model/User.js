/**
 * Created by lihao on 4/24/15.
 */
Ext.define('App.model.User', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'username',
        'name',
        'features',
        'roleId',
        'depId'
    ]
});