/**
 * Created by lihao on 4/15/15.
 */
Ext.define('App.view.StandingBook', {
    extend: 'Ext.grid.Panel',
    requires:[
        'App.view.StandingBookModel',
        'App.view.StandingBookController',
        'App.view.StandingBookForm',
        'App.view.FeatureForm'
    ],

    controller: 'standingBookController',
    viewModel: {
        type: 'standingBookModel'
    },
    alias: 'widget.standingBook',
    autoScroll:true,
    tbar:[
        {
            text: '新建',
            iconCls: 'user-add',
            handler:'onAddClick'
        },'->',
        {
            fieldLabel:'名称',
            labelWidth:35,
            xtype:'textfield',
            bind:'{rec.Q_name_LK}'
        },
        {
            fieldLabel:'编码',
            labelWidth:55,
            xtype:'textfield',
            bind:'{rec.Q_serialNum_EQ}'
        },
        {
            text:'查询',
            handler:'searchBookClick'
        },
        {
            text:'清空',
            handler:'cleanBookClick'
        }
    ],
    bind:{
        store:'{books}'
    },
    listeners:{
        afterrender:function(){
            this.getViewModel().getStore('books').load();
        }
    },
    dockedItems:[
        {
            xtype: 'pagingtoolbar',
            bind:{
                store:'{books}'
            },
            dock: 'bottom',
            displayInfo: true
        }
    ],
    columns:[
        {
            header: '编码',
            flex:1,
            dataIndex: 'serialNum'
        },
        {
            header: '名称',
            flex:1,
            dataIndex: 'name'
        },
        {
            header: '考核报告URL',
            flex:1,
            dataIndex: 'url'
        },
        {
            header: '是否创建创建菜单',
            flex:1,
            dataIndex: 'hasMenu',
            renderer: function (v,metaData,rec) {
                if(v){
                    return '已创建';
                }
                return '未创建'
            }
        },
        {
            header: '创建日期',
            flex:1,
            dataIndex: 'createDate'
        },
        {
            header: '创建人',
            flex:1,
            dataIndex: 'creator'
        },
        {
            header: '数据表',
            flex:1,
            dataIndex: 'tableName'
        },
        {
            xtype:'actioncolumn',
            width:120,
            header: '操作',
            items:[
                {
                    iconCls:'user-edit',
                    tooltip:'编辑',
                    handler:'updateClick'
                },
                '-',
                {
                    iconCls:'user-delete',
                    tooltip:'删除',
                    handler:'deleteClick'
                },
                '-',
                {
                    iconCls:'key-add',
                    tooltip:'权限',
                    handler:'featureClick'
                }
            ]
        }
    ]
});