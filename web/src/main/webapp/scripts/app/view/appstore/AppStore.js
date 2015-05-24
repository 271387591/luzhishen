/**
 * Created by lihao1 on 5/24/15.
 */
Ext.define('App.view.appstore.AppStore', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.appStore',
    requires: [
        'App.view.appstore.AppStoreModel',
        'App.view.appstore.AppStoreController',
        'App.view.appstore.AppStoreForm'
    ],
    controller: 'appStoreController',
    viewModel: {
        type: 'appStoreModel'
    },
    border: false,
    forceFit: true,
    autoScroll: true,
    bind:{
        store:'{apps}'
    },
    dockedItems :[{
        xtype: 'pagingtoolbar',
        bind:{
            store:'{apps}'
        },
        dock: 'bottom',
        displayInfo: true
    }],
    tbar:[
        {
            iconCls: 'table-add',
            text: globalRes.buttons.add,
            handler: 'onAddClick'
        }
    ],
    columns:[
        {xtype: 'rownumberer'},
        {
            header: appStoreRes.header.platform,
            dataIndex: 'platform'
        },
        {
            header: appStoreRes.header.version,
            dataIndex: 'version'
        },
        {
            header: '最新版本',
            dataIndex: 'currentVersion',
            renderer: function (v) {
                return v?'是':'否';
            }
        },

        {
            header: appStoreRes.header.url,
            dataIndex: 'url',
            renderer: function (v) {
                return '<a href="'+v+'" target="_blank">'+v+'</a>';
            }
        },
        {
            header: appStoreRes.header.description,
            dataIndex: 'description'
        },
        {
            header: globalRes.header.createDate,
            dataIndex: 'createDate'
        },

        {
            xtype:'actioncolumn',
            header:globalRes.buttons.remove,
            width:40,
            items:[
                {
                    iconCls:'table-delete',
                    tooltip:globalRes.buttons.remove,
                    handler:'deleteClick'
                }
            ]
        }
    ]
});