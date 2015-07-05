/**
 * Created by lihao1 on 6/18/15.
 */

Ext.define('App.view.comments.CommentsModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.commentsModel',
    requires:[
        'App.model.Comments'
    ],
    data: {
        rec: {}
    },
    stores:{
        apps:{
            autoLoad:true,
            model:'App.model.Comments',
            proxy:{
                type: 'ajax',
                url: 'comments/list',
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