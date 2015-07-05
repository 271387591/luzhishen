/**
 * Created by lihao1 on 6/18/15.
 */

Ext.define('App.view.comments.Comments', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.comments',
    requires: [
        'App.view.comments.CommentsModel',
        'App.view.comments.CommentsController'
    ],
    controller: 'commentsController',
    viewModel: {
        type: 'commentsModel'
    },
    border: false,
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
    selType: 'checkboxmodel',
    tbar:[
        {
            text:'批量删除',
            handler:'deleteClick'
        }
    ],
    columns:[
        {
            flex:1,
            header: '用户名',
            dataIndex: 'username'
        },
        {
            flex:1,
            header: '留言时间',
            dataIndex: 'createDate'
        },
        {
            flex:1,
            header: '联系方式',
            dataIndex: 'contract'
        },
        {
            header: '反馈内容',
            dataIndex: 'comment',
            width:600
        }
    ]
});