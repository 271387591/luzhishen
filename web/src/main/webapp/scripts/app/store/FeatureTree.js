/**
 * Created by lihao1 on 4/29/15.
 */
Ext.define('App.store.FeatureTree', {
    extend: 'Ext.data.TreeStore',
    fields:[
        'name','text','id','iconCls','leaf','config','children'
    ],
    proxy: {
        type: 'ajax',
        url: 'role/listRoleFeatures'
    },
    autoLoad:false,
    root: {
        expanded: true
    }
});