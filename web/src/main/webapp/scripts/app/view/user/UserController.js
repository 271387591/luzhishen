/**
 * Created by lihao1 on 5/23/15.
 */

Ext.define('App.view.user.UserController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.userController',
    onAddClick:function(){
        var view=this.getView();
        var model=view.getViewModel();
        var roleStore=model.getStore('roles');
        var win=Ext.widget('userForm',{
            height:view.getHeight(),
            title:'添加用户',
            roleStore:roleStore
        });
        win.animShow();
    },
    saveUser:function(){
        var view=this.getView();
        var model=view.getViewModel();
        var rec=model.get('rec');
        var roles=view.down('multiselector').getStore();
        var roleIds=[];
        roles.each(function(record){
            console.log(record);
            roleIds.push(record.get('id'));
        });
        console.log(rec);
        console.log(roleIds);
    }

});