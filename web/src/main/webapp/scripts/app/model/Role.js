/**
 * Created with IntelliJ IDEA.
 * User: wangym
 * Date: 2/1/13
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
Ext.define('App.model.Role', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'name',
        'displayName',
        'context',
        'systemViewId',
        'description',
        'text',
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