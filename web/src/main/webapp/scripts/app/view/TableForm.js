/**
 * Created by lihao on 4/14/15.
 */

Ext.define('App.view.TableForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.tableForm',
    requires: [],

    config: {
        title: undefined,
        activeRecord: null
    },
    autoHeight: true,
    border: false,
    bodyPadding: 5,
    layout: 'anchor',
    defaults: {
        anchor: '100%'
    },
    controller: 'tableController',
    viewModel: {
        type: 'tableModel'
    },
    autoScroll: true,
    buttons:[
        {
            xtype: 'button',
            text: '保存',
            handler:'saveClick',
            formBind: true
        },
        {
            xtype: 'button',
            text: '关闭',
            handler:function(){
                this.up('form').close();
            }
        }
    ],
    listeners:{
        afterrender:function(){
            if(this.activeRecord){
                this.loadRecord(this.activeRecord);
                var  gridFields=this.activeRecord.get('fields');
                if(gridFields){
                    var store=Ext.create('Ext.data.Store',{
                        model:'App.model.TableField',
                        data:gridFields
                    });
                    this.down('#fieldsGrid').setStore(store);
                }
            }
            
        }
    },
    autoScroll:true,
    initComponent:function(){
        var me=this;
        me.items=[
            {
                xtype: 'fieldset',
                checkboxToggle: false,
                title: '基本信息',
                autoHeight: true,
                defaultType: 'textfield',
                defaults: {
                    anchor: '40%'
                },
                collapsed: false,
                items: [
                    {
                        xtype: 'hidden',
                        bind: '{rec.id}',
                        name:'id'
                    },
                    {
                        fieldLabel: '表名',
                        name: 'name',
                        maxLength: 50,
                        beforeLabelTextTpl: '<font color="red">*</font>',
                        minLength: 1,
                        blankText: '不能为空',
                        tabIndex: 2,
                        bind:'{rec.name}',
                        readOnly: me.activeRecord,
                        readOnlyCls: 'x-item-disabled',
                        regex: /^[a-zA-Z0-9_]{3,16}$/,
                        regexText: '只能输入数字、字母或下划线,长度位3到16位',
                        allowBlank: false
                    },
                    {
                        fieldLabel: '<font color="red">*</font>显示名称',
                        name: 'displayName',
                        maxLength: 20,
                        minLength: 1,
                        bind:'{rec.displayName}',
                        tabIndex: 1,
                        blankText: '不能为空',
                        allowBlank: false
                    }
                ]
            },
            {
                xtype: 'fieldset',
                title: '表字段设计(系统会自动生成一个没有任何业务意义的自增主键),请必须遵循MySQL建表字段命名规范，避开数据库关键字，id为本系统关键字，请勿使用',
                autoHeight: true,
                defaults: {
                    anchor: '100%'
                },
                autoScroll:true,
                selModel: {
                    selType: 'cellmodel'
                },
                items:[
                    {
                        xtype:'grid',
                        itemId:'fieldsGrid',
                        plugins:[
                            new Ext.grid.plugin.CellEditing({
                                clicksToEdit: 1,
                                pluginId:'cellEditingFiled'
                            })
                        ],
                        store:[],
                        border:true,
                        tbar:[
                            {
                                xtype: 'button',
                                text: '添加字段',
                                iconCls: 'user-add',
                                handler:'addTableField'

                            }
                        ],
                        columns:[
                            {
                                width:100,
                                header:'标识列',
                                dataIndex:'cid',
                                editor: {
                                    allowBlank: false,
                                    emptyText:'必填',
                                    regex: /^[a-zA-Z0-9_]{1,16}$/,
                                    regexText: '只能输入数字、字母或下划线，长度为1到16位'
                                }
                            },
                            {
                                width:120,
                                header:'名称',
                                dataIndex:'name',
                                editor: {
                                    emptyText:'默认与标识列相同'
                                }
                            },
                            {
                                xtype: 'checkcolumn',
                                header: '是否显示',
                                dataIndex: 'availability',
                                flex: 1,
                                stopSelection: false
                            },
                            {
                                header:'类型',
                                dataIndex:'type',
                                width:100,
                                editor: new Ext.form.field.ComboBox({
                                    typeAhead: true,
                                    triggerAction: 'all',
                                    editable:false,
                                    store: [
                                        ['int','int'],
                                        ['bigint','bigint'],
                                        ['varchar','varchar'],
                                        ['char','char'],
                                        ['double','double'],
                                        ['date','date']
                                        //['datetime','datetime']
                                    ]
                                })
                            },
                            {
                                flex: 1,
                                header:'长度',
                                dataIndex:'length',
                                editor: {
                                    xtype: 'numberfield',
                                    minValue: 0,
                                    maxValue: 65565
                                }
                            },
                            {
                                flex: 1,
                                xtype: 'checkcolumn',
                                header:'是否唯一',
                                dataIndex:'isPrimaryKey'
                            },
                            {
                                flex: 1,
                                xtype: 'checkcolumn',
                                header: '允许空',
                                dataIndex: 'isnull',
                                stopSelection: false
                            },
                            {
                                flex: 1,
                                header:'校验串',
                                dataIndex:'differential',
                                editor: {
                                    xtype:'textfield'

                                }
                            },
                            {
                                flex: 1,
                                xtype: 'checkcolumn',
                                header: '控制当期',
                                dataIndex: 'isNow',
                                stopSelection: false
                            },
                            {
                                width:80,
                                header:'录入方式',
                                dataIndex:'inputType',
                                editor: new Ext.form.field.ComboBox({
                                    typeAhead: true,
                                    triggerAction: 'all',
                                    editable:false,
                                    displayField: 'text',
                                    valueField: 'value',
                                    store: new Ext.data.SimpleStore({
                                        fields: ['value', 'text'],
                                        data: [
                                            ["1", '下拉框'],
                                            //["2", '下拉树'],
                                            ["2", '弹出列表'],
                                            //["4", '弹出树'],
                                            ["3", '日期'],
                                            ["4", '手工'],
                                            ["5", '弹出树']
                                        ]
                                    })
                                })
                            },
                            {
                                width: 75,
                                xtype: 'checkcolumn',
                                header: '创建查询',
                                dataIndex: 'isQuery',
                                stopSelection: false
                            },

                            {
                                width:75,
                                header:'显示顺序',
                                dataIndex:'indexDex',
                                editor: {
                                    xtype: 'numberfield',
                                    minValue: 1,
                                    maxValue: 65565

                                }
                            },

                            {
                                header: '数据源配置',
                                dataIndex: 'inputDisplayField',
                                width: 100,
                                stopSelection: false,
                                editor:{
                                    xtype:'textfield',
                                    editable:false,
                                    triggers: {
                                        foo: {
                                            cls: 'my-foo-trigger',
                                            handler:'datasourceTriggerClick'
                                        }
                                    }
                                }
                            },
                            {
                                xtype:'actioncolumn',
                                width:80,
                                header: '操作',
                                items:[
                                    {
                                        iconCls:'user-delete',
                                        tooltip:'删除',
                                        handler:'deleteFormClick'
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ];
        
        me.callParent();
    }
    
});