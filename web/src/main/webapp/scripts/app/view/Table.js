/**
 * Created by lihao on 4/14/15.
 */
Ext.define('App.view.Table', {
    extend: 'Ext.grid.Panel',
    requires:[
        'App.view.TableModel',
        'App.view.TableController',
        'App.view.DataSourceForm',
        'App.view.TableForm'
    ],

    controller: 'tableController',
    viewModel: {
        type: 'tableModel'
    },
    alias: 'widget.table',
    listeners:{
        afterrender:function(){
            this.getViewModel().getStore('tables').load();
        }
    },
    autoScroll:true,
    tbar:[
        {
            frame: true,
            text: '新建数据表',
            iconCls: 'user-add',
            listeners:{
                click:'addClick'
            }
        },'->',
        {
            fieldLabel:'表名',
            labelWidth:35,
            xtype:'textfield',
            name:'Q_name_LK',
            bind:'{rec.Q_name_LK}'
        },
        {
            fieldLabel:'数据库',
            labelWidth:55,
            xtype:'textfield',
            name:'Q_databaseName_LK',
            bind:'{rec.Q_databaseName_LK}'
        },
        {
            text:'查询',
            listeners:{
                click:'searchClick'
            }
        },
        {
            text:'清空',
            listeners:{
                click:'cleanClick'
            }
        }
    ],
    dockedItems:[
        {
            xtype: 'pagingtoolbar',
            bind:{
                store:'{tables}'
            },
            dock: 'bottom',
            displayInfo: true
        }
    ],
    bind:{
        store:'{tables}'
    },
    columns:[
        {
            header: '表名',
            flex:1,
            dataIndex: 'name'
        },
        {
            header: '显示名称',
            flex:1,
            dataIndex: 'displayName'
        },
        {
            header: '创建时间',
            flex:1,
            dataIndex: 'createDate',
            renderer: function (v,metaData,rec){
                return Ext.util.Format.date(new Date(v), 'Y-m-d H:i:s');
            }
        },

        {
            xtype:'actioncolumn',
            width:80,
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
                }

            ]
        }
    ]
});