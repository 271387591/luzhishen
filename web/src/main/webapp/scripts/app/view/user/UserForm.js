/**
 * Created by lihao1 on 5/23/15.
 */
Ext.define('App.view.user.UserForm', {
    extend: 'Ext.ux.window.AnimWindow',
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
    width: 800,
    border:true,
    autoHeight: true,
    initComponent: function () {
        var me = this;
        var roleStore=me.roleStore;
        roleStore.load();
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
                            win.onCancel();
                        }
                    }
                ],
                autoScroll:true,
                xtype: 'form',
                frame:true,
                bodyPadding: 5,
                layout: 'anchor',
                //defaults: {
                //    anchor: '50%'
                //},
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
                                bind:'{rec.defaultRoleId}',
                                name: 'defaultRoleId'
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
                                fieldLabel: '<font color="red">*</font>'+userRoleRes.header.firstName,
                                name: 'firstName',
                                maxLength: 20,
                                minLength: 1,
                                bind:'{rec.firstName}',
                                blankText:globalRes.tooltip.notEmpty,
                                allowBlank: false
                            },
                            {
                                fieldLabel: '<font color="red">*</font>'+userRoleRes.header.lastName,
                                name: 'lastName',
                                maxLength: 20,
                                minLength: 1,
                                bind:'{rec.lastName}',
                                blankText:globalRes.tooltip.notEmpty,
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
                                fieldLabel:'<font color="red">*</font>'+userRoleRes.header.gender,
                                xtype:'combo',
                                name:'gender',
                                mode:'local',
                                editable:false,
                                triggerAction:'all',
                                bind:'{rec.gender}',
                                store:[
                                    ['M', globalRes.header.man],
                                    ['F', globalRes.header.woman]
                                ],
                                value:'M'
                            },{
                                fieldLabel: '<font color="red">*</font>'+userRoleRes.header.mobile,
                                name: 'mobile',
                                bind:'{rec.mobile}',
                                blankText:globalRes.tooltip.notEmpty,
                                validator:function(v){
                                    if(!(/^1[3|4|5|8][0-9]\d{8}$/.test(v))){
                                        return false;
                                    }
                                    return true;
                                },
                                allowBlank: false
                            },{
                                fieldLabel: '<font color="red">*</font>'+userRoleRes.header.email,
                                name: 'email',
                                vtype: 'email',
                                bind:'{rec.email}',
                                blankText:globalRes.tooltip.notEmpty,
                                allowBlank: false
                            },
                        ]
                    },
                    {
                        xtype: 'fieldset',
                        title: userRoleRes.selectRole,
                        checkboxToggle: false,
                        defaults: {               // defaults are applied to items, not the container
                            anchor: '50%'
                        },
                        items:[
                            {
                                xtype: 'multiselector',
                                height: 300,
                                fieldName: 'name',
                                removeRowTip:'删除该行',
                                viewConfig: {
                                    deferEmptyText: false,
                                    emptyText: '当前没有角色'
                                },
                                title:'已分配角色',
                                search: {
                                    width: 300,
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
    onGridViewRefresh : function(store,selModel) {
        var defaultRoleId = this.down('#defaultRoleId').getValue();

        if(defaultRoleId){
            var select =store.findBy(function(rec, id){
                return id == defaultRoleId;
            })
            if(select != -1){
                selModel.select(select);
            }
        }
    },
    onCheckedChange : function(records) {
        if(records&&records.length>0){
            this.down('#defaultRoleId').setValue(records[0].get('id'));
        }else{
            this.down('#defaultRoleId').setValue(null);
        }
    },

    getFormPanel: function () {
        return this.down('form');
    },

    setActiveRecord: function (record) {
        this.activeRecord = record;
        if (record) {
            this.setTitle(userRoleRes.editUser);
            this.down('#password').disable();
            this.down('#passwordAffirm').disable();
            this.down('#password').hide();
            this.down('#passwordAffirm').hide();
            this.getFormPanel().loadRecord(record);
        } else {
            this.setTitle(userRoleRes.addUser);
            this.down('#password').show();
            this.down('#passwordAffirm').show();
            if(this.down('#password').isDisabled())
                this.down('#password').enable();
            if(this.down('#passwordAffirm').isDisabled())
                this.down('#passwordAffirm').enable();

            this.getFormPanel().getForm().reset();
        }
    },

    onSave: function () {
        var me=this;
        var active = this.activeRecord,form = this.getFormPanel().getForm(),
            datas=form.getValues(),simpleRoles=datas.simpleRoles,roleids=[];
        Ext.each(simpleRoles,function(data){
            roleids.push(data.id);
        })
        if(roleids.length==0){
            Ext.MessageBox.alert(globalRes.title.prompt, userRoleRes.msg.addUserHasRole);
            return;
        }
        datas.roleIds= roleids.join(',');
        if (form.isValid()) {
            if (!active) {
                // create new record
                this.fireEvent('create', this, datas);
            }
            else {
                this.fireEvent('update', this, form, active,roleids);
            }
        }
    },

    onCancel: function () {
        this.close();
    }
});