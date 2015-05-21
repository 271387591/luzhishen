/**
 * Created by lihao on 4/24/15.
 */

Ext.define('App.model.StandingBook', {
    extend: 'Ext.data.Model',
    fields: ['id','serialNum','name','createDate','creator','tableId','tableName','url','hasMenu','fields']
});