/**
 * Created by lihao1 on 5/23/15.
 */
Ext.define('App.view.user.UserForm', {
    extend: 'Lzs.ux.window.AnimWindow',
    alias: 'widget.userForm',
    requires: [
    ],

    config: {
        title: undefined,
        activeRecord: null
    },
    controller: 'userController',
    viewModel: {
        type: 'userModel'
    },
    layout: 'fit',
    resizable: false,
    modal: true,
    width: 700,
    border:true,
    autoHeight: true,
    initComponent: function () {
        var me = this;
        var roleStore=me.roleStore;
        me.items=[
            {
                buttons: [
                    {
                        text: globalRes.buttons.save,
                        formBind: true,
                        handler: 'saveUser'
                    },
                    {
                        xtype: 'button',
                        text: globalRes.buttons.cancel,
                        handler: function () {
                            var win = this.up('window');
                            win.close();
                        }
                    }
                ],
                autoScroll:true,
                xtype: 'form',
                frame:true,
                bodyPadding: 5,
                layout: 'anchor',
                items: [
                    {
                        xtype: 'fieldset',
                        checkboxToggle: false,
                        title: userRoleRes.userInfo,
                        defaultType: 'textfield',
                        defaults: {
                            anchor: '70%'
                        },
                        collapsed: false,
                        items: [
                            {
                                xtype: 'hidden',
                                bind:'{rec.id}',
                                name: 'id'
                            },
                            {
                                xtype: 'hidden',
                                itemId:'roleId',
                                bind:'{rec.roleId}'
                            },
                            {
                                fieldLabel: '<font color="red">*</font>'+userRoleRes.header.username,
                                name: 'username',
                                maxLength: 50,
                                bind:'{rec.username}',
                                minLength: 1,
                                blankText:globalRes.tooltip.notEmpty,
                                readOnly:me.isEdit,
                                readOnlyCls:'x-item-disabled',
                                allowBlank: false
                            },
                            {
                                fieldLabel: '<font color="red">*</font>'+userRoleRes.header.password,
                                name: 'password',
                                itemId:'password',
                                blankText:globalRes.tooltip.notEmpty,
                                minLength: 6,
                                bind:'{rec.password}',
                                minLengthText: userRoleRes.passwordError,
                                maxLength: 16,
                                maxLengthText: userRoleRes.passwordError,
                                inputType:'password',
                                allowBlank: false
                            },{
                                fieldLabel: '<font color="red">*</font>'+userRoleRes.passwordAffirm,
                                itemId:'passwordAffirm',
                                name: 'passwordAffirm',
                                blankText:globalRes.tooltip.notEmpty,
                                minLength: 6,
                                minLengthText: userRoleRes.passwordError,
                                maxLength: 16,
                                maxLengthText: userRoleRes.passwordError,
                                inputType:'password',
                                validator: function(v) {
                                    var newPass = me.down('#password');
                                    if (v == newPass.getValue()) {
                                        return true;
                                    }
                                    else {
                                        return userRoleRes.passwordHitNotAllow;
                                    }
                                },
                                allowBlank: false
                            },{
                                fieldLabel:userRoleRes.header.gender,
                                xtype:'combo',
                                name:'gender',
                                editable:false,
                                triggerAction:'all',
                                bind:'{rec.gender}',
                                displayField:'name',
                                valueField:'value',
                                queryMode: 'local',
                                store:Ext.create('Ext.data.Store',{
                                    fields:['value','name'],
                                    data:[
                                        {
                                            name:globalRes.header.man,value:'M'
                                        },
                                        {
                                            name:globalRes.header.woman,value:'F'
                                        }
                                    ]
                                })
                            },{
                                fieldLabel: userRoleRes.header.mobile,
                                name: 'mobile',
                                bind:'{rec.mobile}',
                                validator:function(v){
                                    if(v){
                                        if(!(/^1[3|4|5|8][0-9]\d{8}$/.test(v))){
                                            return false;
                                        }
                                    }
                                    return true;
                                }
                            },{
                                fieldLabel: userRoleRes.header.email,
                                name: 'email',
                                vtype: 'email',
                                bind:'{rec.email}'
                            },{
                                fieldLabel: '介绍人',
                                name: 'referee',
                                bind:'{rec.referee}'
                            },{
                                hidden:true,
                                fieldLabel: '默认角色',
                                name: 'roleDisplayName',
                                bind:'{rec.roleDisplayName}',
                                itemId:'roleDisplayName',
                                readOnly:true,
                                readOnlyCls:'x-item-disabled',
                            }
                        ]
                    },
                    {
                        xtype: 'fieldset',
                        hidden:true,
                        title: userRoleRes.selectRole,
                        checkboxToggle: false,
                        items:[
                            {
                                xtype: 'multiselector',
                                height: 300,
                                width:400,
                                fieldName: 'name',
                                removeRowTip:'删除该行',
                                viewConfig: {
                                    deferEmptyText: false,
                                    emptyText: '当前没有角色'
                                },
                                columns:[
                                    {
                                        xtype: 'checkcolumn',
                                        text:'设为默认角色',
                                        dataIndex:'isDefault',
                                        flex:1,
                                        listeners: {
                                            checkchange: function (item, rowIndex, checked, eOpts) {
                                                var store=this.up('grid').getStore();
                                                var rec=store.getAt(rowIndex);
                                                if(checked){
                                                    me.down('#roleDisplayName').setValue(rec.get('displayName'));
                                                    me.down('#roleId').setValue(rec.get('id'));
                                                }
                                                store.each(function(m){
                                                    if(rec.get('id')!= m.get('id')){
                                                        if(m.get('isDefault')!=null){
                                                            m.set('isDefault',false);
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    },
                                    {
                                        text:'角色名称',
                                        flex:1,
                                        dataIndex:'name'
                                    },
                                    {
                                        text:'角色显示名称',
                                        flex:1,
                                        dataIndex:'displayName'
                                    },
                                    {
                                        xtype:'actioncolumn',
                                        width:50,
                                        header: '操作',
                                        items:[
                                            {
                                                iconCls:'user-delete',
                                                tooltip:'删除',
                                                handler:function(grid, rowIndex, colIndex,item,e,record){
                                                    grid.getStore().remove(record);
                                                }
                                            }
                                        ]
                                    }

                                ],
                                title:'已分配角色',
                                search: {
                                    width: 200,
                                    height: 200,
                                    field: 'name',
                                    store:roleStore
                                }
                            }
                        ]
                    }
                ]
            }

        ];
        this.callParent(arguments);
    },
    getFormPanel: function () {
        return this.down('form');
    },

    setActiveRecord: function (record) {
        this.activeRecord = record;
        if (record) {
            this.down('#password').disable();
            this.down('#passwordAffirm').disable();
            this.down('#password').hide();
            this.down('#passwordAffirm').hide();
            this.getFormPanel().loadRecord(record);
        } else {
            this.down('#password').show();
            this.down('#passwordAffirm').show();
            if(this.down('#password').isDisabled())
                this.down('#password').enable();
            if(this.down('#passwordAffirm').isDisabled())
                this.down('#passwordAffirm').enable();
            this.getFormPanel().getForm().reset();
        }
    }
});