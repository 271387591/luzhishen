/**
 * Created by lihao1 on 5/23/15.
 */
Ext.define('App.view.user.UserCenter', {
    extend: 'Ext.tab.Panel',
    xtype: 'userCenter',
    requires: [
        'App.view.user.UserList'
    ],
    ui: 'navigation',
    tabPosition:'left',
    tabRotation:0,
    titleRotation: 0,
    items: [
       {
        xtype: 'userList',
        title: '用户管理',
        iconCls: 'exec-quarterly-icon'
    },{
        xtype: 'panel',
        title: '角色管理',
        iconCls: 'exec-pl-icon'
    }]
});