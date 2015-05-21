/**
 * Created by lihao1 on 4/29/15.
 */
Ext.define('App.store.MainMenu', {
    extend: 'Ext.data.TreeStore',
    fields:[
        'widget','text','id','iconCls','leaf','config','children','tableId','tableFields'
    ],
    proxy: {
        type: 'ajax',
        url: 'menu/listMenus'
    },
    root: {
        expanded: true
    }
});