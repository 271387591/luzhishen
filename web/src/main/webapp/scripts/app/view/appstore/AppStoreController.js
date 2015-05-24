/**
 * Created by lihao1 on 5/24/15.
 */

Ext.define('App.view.appstore.AppStoreController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.appStoreController',
    requires:[
        //'App.model.Role'
    ],
    onAddClick:function(){
        var view=this.getView();
        var model=view.getViewModel();
        var win = Ext.widget('appStoreForm', {
            title:globalRes.buttons.add,
            height:view.getHeight()
        });
        win.animShow();
        this.mon(win,'refreshStore',function(){
            view.getStore().load();
        });
    },
    save:function(){
        var view=this.getView();
        var model=view.getViewModel();
        var form=view.down('form');
        var data=form.getValues();
        form.submit({
            url: 'appstore/save',
            waitMsg: globalRes.buttons.fileLoading,
            params:data,
            success: function(fp, o) {
                Ext.Msg.alert('成功','保存成功',function(){
                    view.fireEvent('refreshStore');
                    view.close();
                });
            },
            failure:function(){
                Ext.Msg.alert(globalRes.title.prompt, globalRes.addFail);
            }
        });
    },
    deleteClick:function(grid, rowIndex, colIndex,item,e,record){
        var view=this.getView();
        var model=view.getViewModel();
        Ext.Msg.confirm(globalRes.buttons.remove, Ext.String.format(appStoreRes.removeAlert, record.data.version), function (txt) {
            if (txt === 'yes') {
                ajaxPostRequest('appstore/delete/'+record.get('id'),function(result){
                    if (result.success) {
                        Ext.Msg.alert('成功','删除成功',function(){
                            grid.getStore().load();
                        });
                    } else {
                        Ext.MessageBox.show({
                            title: globalRes.title.prompt,
                            width: 300,
                            msg: result.message,
                            buttons: Ext.MessageBox.OK,
                            icon: Ext.MessageBox.ERROR
                        });

                    }
                });
            }
        });
    }
});