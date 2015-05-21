/**
 * Created by lihao1 on 5/1/15.
 */
Ext.define('App.view.DataSourceForm', {
    extend: 'Ext.window.Window',
    alias: 'widget.dataSourceForm',

    requires: [
        'App.view.TableModel'
    ],
    resizable: false,
    controller: 'tableController',
    viewModel: {
        type: 'tableModel'
    },
    layout: 'fit',
    modal: true,
    width: 600,
    height: 400,
    border: false,
    buttons:[
        {
            text:'确定',
            handler:'dataSourceFormOk'
        },
        {
            text:'关闭',
            handler:function(){
                this.up('window').close();
            }
        }
    ],
    listeners:{
        afterrender:function(){
            var model=this.getViewModel();
            model.getStore('datasources').load();
        }
    },
    initComponent:function(){
        var me=this;
        me.items=[
            {
                xtype:'panel',
                layout:'border',
                items:[
                    {
                        xtype:'form',
                        region:'north',
                        layout:'hbox',
                        items:[
                            {
                                padding:10,
                                xtype: 'combo',
                                bind:{
                                    store:'{datasources}',
                                    value:'{rec.sourceId}'
                                },
                                queryMode: 'remote',
                                displayField: 'displayName',
                                editable: false,
                                valueField: 'id',
                                labelWidth:75,
                                fieldLabel: '表显示名称',
                                listeners:{
                                    change:'dataSourceFormComboChange'
                                }
                            },
                            {
                                padding:10,
                                xtype:'textfield',
                                readOnly:true,
                                labelWidth:50,
                                bind:'{rec.tableName}',
                                readOnlyCls: 'x-item-disabled',
                                fieldLabel:'表名称'
                            }
                        ]
                    },
                    {
                        margin:'2 0 0 0',
                        border:true,
                        title:'表字段列表(勾选前面的选择框并设置显示名称)',
                        //selModel: {
                        //    selType: 'checkboxmodel',
                        //    mode:'SINGLE'
                        //},
                        xtype:'grid',
                        region:'center',
                        bind:{
                            store:'{datasourceFields}'
                        },
                        autoScroll:true,
                        plugins:[
                            new Ext.grid.plugin.CellEditing({
                                clicksToEdit: 1,
                                pluginId:'dataSourceFormCellEdit'
                            })
                        ],
                        columns:[
                            {
                                text:'字段名称',
                                dataIndex:'name',
                                flex:1
                            },
                            {
                                text:'类型',
                                dataIndex:'type',
                                flex:1
                            },
                            {
                                xtype: 'checkcolumn',
                                text:'是否存入数据库',
                                width:110,
                                dataIndex:'saveDatabase',
                                listeners: {
                                    checkchange: function (item, rowIndex, checked, eOpts) {
                                        var store=this.up('grid').getStore();
                                        var rec=store.getAt(rowIndex);
                                        store.each(function(m){
                                            if(rec.get('name')!= m.get('name')){
                                                if(m.get('saveDatabase')!=null){
                                                    m.set('saveDatabase',false);
                                                }
                                            }
                                        });
                                    }
                                }
                            },
                            {
                                xtype: 'checkcolumn',
                                text:'作为显示值',
                                width:100,
                                dataIndex:'displayName',
                                listeners: {
                                    checkchange: function (item, rowIndex, checked, eOpts) {
                                        var store=this.up('grid').getStore();
                                        var rec=store.getAt(rowIndex);
                                        store.each(function(m){
                                            if(rec.get('name')!= m.get('name')){
                                                if(m.get('displayName')!=null){
                                                    m.set('displayName',false);
                                                }
                                            }
                                        });
                                    }
                                }
                            },
                            {
                                xtype: 'checkcolumn',
                                text:'树形父ID',
                                width:100,
                                hidden:(me.inputType!=5),
                                dataIndex:'parent',
                                listeners: {
                                    checkchange: function (item, rowIndex, checked, eOpts) {
                                        var store=this.up('grid').getStore();
                                        var rec=store.getAt(rowIndex);
                                        store.each(function(m){
                                            if(rec.get('name')!= m.get('name')){
                                                if(m.get('parent')!=null){
                                                    m.set('parent',false);
                                                }
                                            }
                                        });
                                    }
                                }
                            },
                            {
                                xtype: 'checkcolumn',
                                text:'树形ID',
                                width:100,
                                hidden:(me.inputType!=5),
                                dataIndex:'treeId',
                                listeners: {
                                    checkchange: function (item, rowIndex, checked, eOpts) {
                                        var store=this.up('grid').getStore();
                                        var rec=store.getAt(rowIndex);
                                        store.each(function(m){
                                            if(rec.get('name')!= m.get('name')){
                                                if(m.get('treeId')!=null){
                                                    m.set('treeId',false);
                                                }
                                            }
                                        });
                                    }
                                }
                            },

                            {
                                text:'列表显示标题',
                                width:100,
                                hidden:(me.inputType!=2),
                                dataIndex:'gridHeader',
                                editor:{
                                    xtype:'textfield'
                                }
                            }
                        ]
                    }
                ]
            }

        ];

        me.callParent();
    }

});