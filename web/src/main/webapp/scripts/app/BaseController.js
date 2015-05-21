/**
 * Created by lihao on 2/13/15.
 */
Ext.define('App.BaseController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.baseController',
    
    init: function() {
        var me=this;
        
    },
    onLaunch: function() {
    },
    addPanel: function (widget, itemId) {
        var tab=Ext.ComponentQuery.query('container[xtype=app-main] tabpanel')[0];
        tab.fireEvent('addOtherPanel',widget,itemId);
    }
});