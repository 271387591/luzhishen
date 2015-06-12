/**
 * Created by lihao1 on 5/23/15.
 */
Ext.define('App.view.user.UserList', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.userList',
    requires: [
        'App.view.user.UserModel',
        'App.view.user.UserController',
        'Lzs.ux.grid.Search',
        'App.view.user.UserForm'

    ],
    controller: 'userController',
    viewModel: {
        type: 'userModel'
    },
    tbar: [
        {
            iconCls:'user-add',
            text:globalRes.buttons.add,
            handler: 'onAddClick'
        },

        {
            iconCls:'user-edit',
            text:userRoleRes.passwordTilte,
            handler:'onChangePasswordClick'
        },
        {
            iconCls:'user-delete',
            text:userRoleRes.lockUser,
            handler: 'onLockUserClick'
        },
        {
            iconCls:'user-edit',
            text:userRoleRes.unLockUser,
            handler: 'onUnLockUserClick'
        }
    ],
    border: false,
    forceFit: true,
    autoScroll: true,
    bind:{
        store:'{users}'
    },
    features:[{
        ftype: 'search',
        disableIndexes: ['username', 'fullName'],
        paramNames: {
            fields: ['Q_username_LK_OR_S'],
            query: 'keyword'
        },
        searchMode: 'remote'
    }],


    dockedItems:[{
        xtype: 'pagingtoolbar',
        bind:{
            store:'{users}'
        },
        dock: 'bottom',
        displayInfo: true
    }],
    columns: [
        {
            header: userRoleRes.header.username,
            dataIndex: 'username'
        },
        {
            header: '手机号',
            dataIndex: 'mobile'
        },

        {
            header: '电子邮件',
            dataIndex: 'email'
        },
        {
            header:userRoleRes.header.accountLocked,
            dataIndex:'accountLocked',
            renderer: function(v){
                return v?globalRes.yes:globalRes.no;
            }
        },
        {
            header: '注册时间',
            dataIndex: 'createDate'
        },
        {
            header: '介绍人',
            dataIndex: 'referee'
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