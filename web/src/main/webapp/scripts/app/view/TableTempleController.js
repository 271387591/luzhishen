/**
 * Created by lihao on 4/24/15.
 */

Ext.define('App.view.TableTempleController', {
    extend: 'App.BaseController',

    requires: [
        'Ext.window.MessageBox',
    ],

    alias: 'controller.tableTempleController',

    init: function() {
        var me=this;
    },
    searchClick: function () {
        var model =  this.getView().getViewModel();
        var rec = model.get('rec')||{};
        rec.tableId=model.get('tableId');
        var store=this.getView().getStore();
        var params=store.getProxy().extraParams;
        for(var i in rec){
            params[i]=rec[i];
        }
        store.getProxy().extraParams=params;
        store.loadPage(1);
    },
    cleanClick:function(){
        var model =  this.getView().getViewModel();
        var rec = model.get('rec') ||{};
        for(var i in rec){
            model.set('rec.'+i,'');
        }
        rec.tableId=model.get('tableId');
        var store=this.getView().getStore();
        var params=store.getProxy().extraParams;
        for(var i in rec){
            params[i]=rec[i];
        }
        store.getProxy().extraParams=params;
        store.loadPage(1);
    },
    saveClick:function(){
        var view=this.getView();
        var model=view.getViewModel();
        var data=model.get('rec');
        data.tableId=model.get('tableId');
        data.uniqueNames=model.get('uniqueNames');
        Ext.Ajax.request({
            url: 'temple/save',
            params: data,
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

    onAddClick:function(){
        var me=this;
        var view=this.getView();
        var win=Ext.widget('tableTempleForm',{
            title:'添加',
            activeRecord:null,
            tableId:me.getViewModel().get('tableId'),
            fields:me.getViewModel().get('fields')
        });
        this.mon(win,'refreshStore',function(){
            view.getStore().load();
        });
        win.show();
    },

    updateClick:function(grid, rowIndex, colIndex,item,e,record){
        var me=this;
        var view=this.getView();
        var win=Ext.widget('tableTempleForm',{
            title:'编辑',
            activeRecord:record,
            tableId:me.getViewModel().get('tableId'),
            fields:me.getViewModel().get('fields')
        });
        this.mon(win,'refreshStore',function(){
            view.getStore().load();
        });
        win.show();
    },
    deleteClick:function(grid, rowIndex, colIndex,item,e,record){
        var me=this;
        var model=this.getViewModel();
        Ext.Msg.confirm('删除','您确定要删除该数据',function(btn){
            if(btn=='yes'){
                Ext.Ajax.request({
                    url: 'temple/delete',
                    params: {tableId:model.get('tableId'),id:record.get('id')},
                    method: 'POST',
                    success: function (response, options) {
                        var result = Ext.decode(response.responseText,true);
                        if(result.success){
                            grid.getStore().loadPage(1);
                        }else{
                            Ext.Msg.alert('失败',result.message);
                        }
                    }
                });
            }
        });
    },
    deleteUserClick:function(grid, rowIndex, colIndex,item,e,record){
        var me=this;
        Ext.Msg.confirm('删除','您确定要删除该数据',function(btn){
            if(btn=='yes'){
                me.getView().down('#userGrid').getStore().remove(record);
            }
        });
    },

    featureClick:function(grid, rowIndex, colIndex,item,e,record){
        var win=Ext.widget('featureForm',{
            title:'权限'
        });
        win.show();
    }
});