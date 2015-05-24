/**
 * Created by lihao1 on 5/23/15.
 */

Ext.define('App.view.user.UserController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.userController',
    requires:[
        //'App.model.Role'
    ],
    onAddClick:function(){
        var view=this.getView();
        var model=view.getViewModel();
        var roleStore=model.getStore('roles');
        roleStore.load();
        var win=Ext.widget('userForm',{
            height:view.getHeight(),
            title:'添加用户',
            roleStore:roleStore
        });
        win.animShow();
        this.mon(win,'refreshStore',function(){
            view.getStore().load();
        });
    },
    onEditClick:function(grid, rowIndex, colIndex,item,e,record){
        var view=this.getView();
        var model=view.getViewModel();
        model.set('rec',record.data);
        var roles=record.get('simpleRoles');
        var eroleStore=Ext.create('Ext.data.Store',{
            model:'App.model.Role',
            data:roles
        });
        var roleStore=model.getStore('roles');
        roleStore.load();
        var win=Ext.widget('userForm',{
            height:view.getHeight(),
            title:'编辑用户',
            isEdit:true,
            roleStore:roleStore
        });
        win.down('multiselector').setStore(eroleStore);
        win.setActiveRecord(record);
        win.animShow();
        this.mon(win,'refreshStore',function(){
            view.getStore().load();
        });
    },
    deleteClick:function(grid, rowIndex, colIndex,item,e,record){
        var view=this.getView();
        var model=view.getViewModel();
        Ext.Msg.confirm("提示",'你确定要删除该用户',function(btn){
            if(btn=='yes'){
                ajaxPostRequest('user/remove/'+record.get('id'),{},function(result){
                    if(result.success){
                        grid.getStore().load();
                        Ext.Msg.alert(globalRes.title.prompt, globalRes.removeSuccess);
                    }else{
                        Ext.Msg.alert('提示',result.message);
                    }
                });
            }
        });
    },


    saveUser:function(){
        var view=this.getView();
        var model=view.getViewModel();
        var rec=model.get('rec');
        var roles=view.down('multiselector').getStore();
        var roleCmd=[];
        roles.each(function(record){
            var obj={};
            obj.id=record.get('id');
            roleCmd.push(obj);
        });
        rec.simpleRoles=roleCmd;
        Ext.Ajax.request({
            url: 'user/save',
            jsonData: Ext.encode(rec),
            method: 'POST',
            success: function (response, options) {
                var result = Ext.decode(response.responseText,true);
                if(result.success){
                    Ext.Msg.alert('成功','保存成功',function(){
                        view.fireEvent('refreshStore');
                        view.close();
                    });
                }else{
                    Ext.Msg.alert('失败',result.message);
                }
            }
        });

    },
    onChangePasswordClick:function(){
        var view=this.getView();
        var sModel=view.getSelectionModel();
        if(!sModel.hasSelection()){
            Ext.Msg.alert('提示','请选择数据');
            return;
        }
        var recs=sModel.getSelection();
        var id=recs[0].get('id');
        var win = Ext.widget('window',{
            width:280,
            //height:200,
            title:'修改密码',
            layout:'fit',
            modal: true,
            border:true,
            autoHeight: true,
            items:[
                {
                    xtype:'form',
                    bodyPadding: 5,
                    layout: 'anchor',
                    defaults: {
                        anchor: '100%'
                    },
                    buttons:[
                        {
                            text: globalRes.buttons.save,
                            formBind: true,
                            handler: function(){
                                var value = this.up('form').getForm().getValues();
                                Ext.Ajax.request({
                                    url: 'user/adminChangePassword',
                                    params:value,
                                    method: 'POST',
                                    success: function (response, options) {
                                        var result = Ext.decode(response.responseText,true);
                                        if(result.success){
                                            Ext.Msg.alert('成功','保存成功',function(){
                                                view.getStore().load();
                                                win.close();
                                            });
                                        }else{
                                            Ext.Msg.alert('失败',result.message);
                                        }
                                    }
                                });
                            }
                        },
                        {
                            xtype: 'button',
                            text: globalRes.buttons.cancel,
                            handler: function () {
                                win.close();
                            }
                        }
                    ],
                    items:[
                        {
                            xtype:'hidden',
                            name:'id',
                            value:id
                        },
                        {
                            xtype:'textfield',
                            fieldLabel: '<font color="red">*</font>'+userRoleRes.header.password,
                            name: 'newPassword',
                            itemId:'password',
                            blankText:globalRes.tooltip.notEmpty,
                            minLength: 6,
                            minLengthText: userRoleRes.passwordError,
                            maxLength: 16,
                            maxLengthText: userRoleRes.passwordError,
                            inputType:'password',
                            labelWidth:65,
                            allowBlank: false
                        },
                        {
                            xtype:'textfield',
                            fieldLabel: '<font color="red">*</font>'+userRoleRes.passwordAffirm,
                            itemId:'passwordAffirm',
                            name: 'passwordAffirm',
                            blankText:globalRes.tooltip.notEmpty,
                            minLength: 6,
                            minLengthText: userRoleRes.passwordError,
                            maxLength: 16,
                            labelWidth:65,
                            maxLengthText: userRoleRes.passwordError,
                            inputType:'password',
                            validator: function(v) {
                                var newPass = win.down('#password');
                                if (v == newPass.getValue()) {
                                    return true;
                                }
                                else {
                                    return userRoleRes.passwordHitNotAllow;
                                }
                            },
                            allowBlank: false
                        }
                    ]
                }
            ]

        });
        win.show();
    },
    onLockUserClick:function(){
        var view=this.getView();
        var sModel=view.getSelectionModel();
        if(!sModel.hasSelection()){
            Ext.Msg.alert('提示','请选择数据');
            return;
        }
        var recs=sModel.getSelection();
        var id=recs[0].get('id');
        Ext.Msg.confirm('提示','你确定要锁定该用户?',function(btn){
            if(btn=='yes'){
                Ext.Ajax.request({
                    url: 'user/lockUser',
                    params:{id:id},
                    method: 'POST',
                    success: function (response, options) {
                        var result = Ext.decode(response.responseText,true);
                        if(result.success){
                            Ext.Msg.alert('成功','保存成功',function(){
                                view.getStore().load();
                            });
                        }else{
                            Ext.Msg.alert('失败',result.message);
                        }
                    }
                });
            }
        });
    },
    onUnLockUserClick:function(){
        var view=this.getView();
        var sModel=view.getSelectionModel();
        if(!sModel.hasSelection()){
            Ext.Msg.alert('提示','请选择数据');
            return;
        }
        var recs=sModel.getSelection();
        var id=recs[0].get('id');
        Ext.Msg.confirm('提示','你确定要解锁该用户?',function(btn){
            if(btn=='yes'){
                Ext.Ajax.request({
                    url: 'user/unLockUser',
                    params:{id:id},
                    method: 'POST',
                    success: function (response, options) {
                        var result = Ext.decode(response.responseText,true);
                        if(result.success){
                            Ext.Msg.alert('成功','保存成功',function(){
                                view.getStore().load();
                            });
                        }else{
                            Ext.Msg.alert('失败',result.message);
                        }
                    }
                });
            }
        });
    }
});