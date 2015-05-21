/**
 * Created by lihao on 4/24/15.
 */
Ext.define('App.view.StandingBookModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.standingBookModel',
    requires: [
        'App.model.StandingBook',
        'App.model.User',
        'App.model.Table',
        'App.model.RoleFieldFeature',
        'App.model.Role'
    ],
    data: {
        rec: null
    },
    stores:{
        books:{
            model:'App.model.StandingBook',
            proxy: {
                type: 'ajax',
                url: 'standingbook/listStandingBooks',
                reader: {
                    type: 'json',
                    root: 'data',
                    totalProperty: 'total',
                    messageProperty: 'message'
                },
                extraParams:{
                }
            }
        },
        tables:{
            model:'App.model.Table',
            proxy: {
                type: 'ajax',
                url: 'table/listNoMenuTables',
                reader: {
                    type: 'json',
                    root: 'data',
                    totalProperty: 'total',
                    messageProperty: 'message'
                },
                extraParams:{
                }
            }
        },
        roles:{
            model:'App.model.Role',
            proxy: {
                type: 'ajax',
                url: 'user/listRoles',
                reader: {
                    type: 'json',
                    root: 'data',
                    totalProperty: 'total',
                    messageProperty: 'message'
                },
                extraParams:{
                }
            }
        },
        users:{
            model:'App.model.User',
            proxy: {
                type: 'ajax',
                url: 'user/listUsers',
                reader: {
                    type: 'json',
                    root: 'data',
                    totalProperty: 'total',
                    messageProperty: 'message'
                },
                extraParams:{
                }
            }
        },

        tableRoles:{
            model:'App.model.Role',
            proxy: {
                type: 'ajax',
                url: 'user/listTableRoles',
                reader: {
                    type: 'json',
                    root: 'data',
                    totalProperty: 'total',
                    messageProperty: 'message'
                },
                extraParams:{
                }
            }
        },
        userFieldFeatures:{
            model:'App.model.RoleFieldFeature',
            proxy: {
                type: 'ajax',
                url: 'user/listTableFeatures',
                reader: {
                    type: 'json',
                    root: 'data',
                    totalProperty: 'total',
                    messageProperty: 'message'
                },
                extraParams:{
                }
            }
        }

    }
});