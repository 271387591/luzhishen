/**
 * Created by lihao1 on 5/23/15.
 */
Ext.define('App.view.user.UserModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.userModel',
    requires:[
        'App.model.User',
        'App.model.Role'
    ],
    data: {
        rec: {}
    },
    stores:{
        users:{
            model:'App.model.User',
            proxy:{
                type: 'ajax',
                url: 'user/listUsers',
                reader: {
                    type: 'json',
                    root : 'data',
                    totalProperty  : 'total',
                    messageProperty: 'message'
                }
            },
            autoLoad:true,
            extraParams:{}
        },
        roles:{
            model:'App.model.Role',
            proxy:{
                type: 'ajax',
                url: 'role/listRoles',
                reader: {
                    type: 'json',
                    root : 'data',
                    totalProperty  : 'total',
                    messageProperty: 'message'
                }
            },
            extraParams:{}
        }
    }
});