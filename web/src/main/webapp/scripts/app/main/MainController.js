Ext.define('App.main.MainController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'App.view.Table',
        'App.view.StandingBook',
        'App.view.TableTemple'
    ],

    alias: 'controller.main',
    config: {
        control: {
            'container[xtype=app-main] tabpanel': {
                addPanel: 'addPanel',
                addOtherPanel: 'addOtherPanel'
            },
            'container[xtype=app-main] mainMenu':{
                itemclick:'mainMenuClick',
                itemcontextmenu:'mainMenuItemcontextmenu'
            }
        }
    },
    mainMenuItemcontextmenu:function (view, record, item, index, e, eOpts) {
        e.preventDefault();
        e.stopEvent();
    },
    mainMenuClick:function (view, record, item, index) {
        var me=this;
        var widget = record.data.widget, itemId = record.data.widgetItemId, config = record.data.config || {};
        Ext.apply(config,{
            itemId:itemId,
            closable:true,
            title:record.data.text,
            tableId:record.data.tableId,
            tableFields:record.data.tableFields
        });
        if (!widget) return;
        var centerTabPanel = Ext.ComponentQuery.query('container[xtype=app-main] tabpanel')[0];
        var items=centerTabPanel.items;
        if(items && items.length>1){
            for(var i=1;i<items.length;i++){
                centerTabPanel.remove(items[i]);
            }
        }
        me.addPanel(widget, itemId, config);
    },
    addPanel: function (widget, itemId, config) {
        var tab=Ext.ComponentQuery.query('container[xtype=app-main] tabpanel')[0];
        if(tab){
            var panel = Ext.ComponentQuery.query('#' + itemId)[0];
            if (!panel) {
                if (config) {
                    config=Ext.apply(config,{itemId:itemId});
                    panel = Ext.widget(widget, config);
                } else {
                    panel = Ext.widget(widget);
                }

                tab.add(panel);
            }
            tab.setActiveTab(panel);
            return panel;
        }
    },
    addOtherPanel:function(widget,itemId,config){
        var tab=Ext.ComponentQuery.query('container[xtype=app-main] tabpanel')[0];
        if(tab){
            var panel=tab.down('#' + itemId);
            if (!panel) {
                tab.add(widget);
                panel=widget;
            }
            tab.setActiveTab(panel);
            return panel;
        }
    }
});
