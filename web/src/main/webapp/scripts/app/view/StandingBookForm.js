/**
 * Created by lihao on 4/15/15.
 */
Ext.define('App.view.StandingBookForm', {
    extend: 'Ext.window.Window',
    alias: 'widget.standingBookForm',

    requires: [
        'Ext.form.Panel',
        'Ext.data.Store'
    ],

    config: {
        title: undefined,
        activeRecord: null
    },
    mySelf: false,
    resizable: false,
    controller: 'standingBookController',
    viewModel: {
        type: 'standingBookModel'
    },
    listeners:{
        afterrender:function(){
            var model=this.getViewModel();
            model.getStore('tables').load();
        }
    },
    initComponent: function () {
        var me = this;
        Ext.apply(this, {
            layout: 'fit',
            autoShow: true,
            modal: true,
            width: 500,
            autoHeight: true,
            border: false,
            minWidth: 600,
            maxHeight: 600,
            autoScroll: true,
            items: [
                {
                    xtype: 'form',
                    frame: true,
                    bodyPadding: 5,
                    layout: 'anchor',
                    defaults: {
                        anchor: '100%'
                    },
                    autoScroll: true,
                    listeners:{
                        afterrender:function(){
                            if(me.activeRecord){
                                this.loadRecord(me.activeRecord);
                            }
                        }
                    },
                    buttons: [
                        {
                            xtype: 'button',
                            text: '保存 ',
                            formBind: true,
                            handler: 'saveClick'
                        },
                        {
                            xtype: 'button',
                            text: '关闭',
                            handler: function () {
                                var win = this.up('window');
                                win.close();
                            }
                        }
                    ],
                    items: [
                        {
                            xtype: 'fieldset',
                            checkboxToggle: false,
                            title: '基本信息',
                            autoHeight: true,
                            defaultType: 'textfield',
                            defaults: {               // defaults are applied to items, not the container
                                anchor: '100%'
                            },
                            collapsed: false,
                            items: [
                                {
                                    xtype:'hidden',
                                    name:'id',
                                    bind:'{rec.id}'
                                },
                                {
                                    regex: /^[a-zA-Z0-9_]{3,16}$/,
                                    regexText: '只能输入数字、字母或下划线',
                                    fieldLabel: '<font color="red">*</font>编码',
                                    bind: '{rec.serialNum}',
                                    name:'serialNum',
                                    maxLength: 50,
                                    minLength: 1,
                                    tabIndex: 2,
                                    readOnly: me.isEdit,
                                    readOnlyCls: 'x-item-disabled',
                                    allowBlank: false
                                },
                                {
                                    fieldLabel: '<font color="red">*</font>名称',
                                    name: 'name',
                                    bind: '{rec.name}',
                                    maxLength: 50,
                                    minLength: 1,
                                    allowBlank: false
                                },
                                {
                                    fieldLabel: '<font color="red">*</font>考核报告URL',
                                    name: 'url',
                                    bind: '{rec.url}',
                                    allowBlank: false
                                },
                                {
                                    xtype: 'combo',
                                    queryMode: 'remote',
                                    displayField: 'displayName',
                                    editable: false,
                                    name:'tableId',
                                    valueField: 'id',
                                    bind: {
                                        value:'{rec.tableId}',
                                        store:'{tables}'
                                    },
                                    allowBlank: false,
                                    fieldLabel: '<font color="red">*</font>数据表'
                                },
                                {
                                    xtype: 'combo',
                                    store: new Ext.data.SimpleStore({
                                        fields: ['value', 'text'],
                                        data: [
                                            ["true", '是'],
                                            ["false", '否']
                                        ]
                                    }),
                                    //value:'Y',
                                    queryMode: 'local',
                                    displayField: 'text',
                                    editable: false,
                                    valueField: 'value',
                                    name:'hasMenu',
                                    allowBlank: false,
                                    bind: '{rec.hasMenu}',
                                    fieldLabel: '<font color="red">*</font>创建菜单'
                                }
                            ]
                        }
                    ]
                }
            ]
        });
        this.callParent(arguments);
    }

})