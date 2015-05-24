/**
 * Created by lihao1 on 5/24/15.
 */
Ext.define('App.view.appstore.AppStoreModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.appStoreModel',
    requires:[
        'App.model.AppStore'
    ],
    data: {
        rec: {}
    },
    stores:{
        apps:{
            autoLoad:true,
            model:'App.model.AppStore',
            proxy:{
                type: 'ajax',
                url: 'appstore/listAppStores',
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