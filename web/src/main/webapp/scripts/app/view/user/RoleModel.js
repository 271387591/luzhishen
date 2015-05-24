/**
 * Created by lihao1 on 5/23/15.
 */
Ext.define('App.view.user.RoleModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.roleModel',
    requires:[
        'App.model.User',
        'App.model.Role'
    ],
    data: {
        rec: {}
    },
    stores:{
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