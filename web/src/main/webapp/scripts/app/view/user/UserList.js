/**
 * Created by lihao1 on 5/23/15.
 */
Ext.define('App.view.user.UserList', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.userList',
    requires: [
        'App.view.user.UserModel',
        'App.view.user.UserController',
        'Ext.ux.grid.Search',
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
            text:globalRes.buttons.edit,
            handler: 'onEditClick'
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
            iconCls:'user-delete',
            text:userRoleRes.disableUser,
            handler: 'onDisableUserClick'
        },{
            iconCls:'user-edit',
            text:userRoleRes.unLockUser,
            handler: 'onUnLockUserClick'
        },{
            iconCls:'user-edit',
            text:userRoleRes.unDisableUser,
            handler: 'onUnDisableUserClick'
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
            fields: 'fields',
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
            header: userRoleRes.header.fullName,
            dataIndex: 'fullName'
        },
        {
            header: userRoleRes.header.username,
            dataIndex: 'username'
        },
        {
            header: userRoleRes.header.defaultRoleName,
            dataIndex: 'defaultRoleDisplayName'
        },
        {
            header:userRoleRes.header.accountLocked,
            dataIndex:'accountLocked',
            renderer: function(v){
                return v?globalRes.yes:globalRes.no;
            }
        },
        {
            header:userRoleRes.header.enabled,
            dataIndex:'enabled',
            renderer: function(v){
                return v?globalRes.yes:globalRes.no;
            }
        },
        {
            header: globalRes.header.createDate,
            dataIndex: 'createDate'
        }
    ]
});