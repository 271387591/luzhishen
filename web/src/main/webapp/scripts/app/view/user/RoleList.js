/**
 * Created by lihao1 on 5/24/15.
 */
Ext.define('App.view.user.RoleList', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.roleList',

    requires: [
        'App.view.user.RoleModel',
        'App.view.user.RoleController',
        'Lzs.ux.grid.Search',
        'App.view.user.RoleForm'

    ],
    controller: 'roleController',
    viewModel: {
        type: 'roleModel'
    },
    tbar: [
        {
            iconCls:'user-add',
            text:globalRes.buttons.add,
            handler: 'onAddClick'
        }
    ],
    border: false,
    forceFit: true,
    autoScroll: true,
    bind:{
        store:'{roles}'
    },
    features:[{
        ftype: 'search',
        disableIndexes: ['name', 'displayName'],
        paramNames: {
            fields: ['Q_name_LK_OR','Q_displayName_LK_OR'],
            query: 'keyword'
        },
        searchMode: 'remote'
    }],
    listeners:{
        afterrender:function(f){
            f.getStore().loadPage(1);
        }

    },

    dockedItems:[{
        xtype: 'pagingtoolbar',
        bind:{
            store:'{roles}'
        },
        dock: 'bottom',
        displayInfo: true
    }],
    columns: [
        {
            header:userRoleRes.header.roleName,
            dataIndex:'name',
            flex:1
        },{
            header:userRoleRes.header.displayName,
            dataIndex:'displayName',
            flex:1
        },{
            header: userRoleRes.header.description,
            dataIndex: 'description',
            flex:1
        },{
            header: globalRes.header.createDate,
            dataIndex: 'createDate',
            flex:1
        },
        {
            xtype:'actioncolumn',
            width:120,
            header: '操作',
            items:[
                {
                    iconCls:'user-edit',
                    tooltip:'编辑',
                    handler:'onEditClick'
                },
                '-',
                {
                    iconCls:'user-delete',
                    tooltip:'删除',
                    handler:'deleteClick'
                }
            ]
        }
    ]
});