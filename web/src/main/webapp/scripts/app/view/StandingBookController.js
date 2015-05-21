/**
 * Created by lihao on 4/24/15.
 */

Ext.define('App.view.StandingBookController', {
    extend: 'App.BaseController',

    requires: [
        'Ext.window.MessageBox',
    ],

    alias: 'controller.standingBookController',

    init: function() {
        var me=this;
    },
    searchBookClick: function () {
        var model =  this.getView().getViewModel();
        var name = model.get('rec.Q_name_LK');
        var serialNum=model.get('rec.Q_serialNum_EQ');
        if(!name && !serialNum){
            return;
        }
        var store=this.getView().getStore();
        store.getProxy().extraParams={Q_name_LK:name,Q_serialNum_EQ:serialNum};
        store.loadPage(1);
    },
    cleanBookClick:function(){
        var model =  this.getView().getViewModel();
        model.set('rec.Q_name_LK','');
        model.set('rec.Q_serialNum_EQ','');
        var store=this.getView().getStore();
        store.getProxy().extraParams={};
        store.loadPage(1);
    },

    saveClick:function(){
        var view=this.getView();
        var model=view.getViewModel();
        Ext.Ajax.request({
            url: 'standingbook/save',
            jsonData: model.get('rec'),
            method: 'POST',
            success: function (response, options) {
                var result = Ext.decode(response.responseText,true);
                if(result.success){
                    Ext.Msg.alert('成功','保存成功',function(){
                        view.fireEvent('refreshStore');
                        view.close();
                        var menuTree=Ext.ComponentQuery.query('#treeNav')[0]
                        menuTree.getStore().load({
                            callback:function(){
                                menuTree.expandAll();
                            }
                        });

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
        var win=Ext.widget('standingBookForm',{
            title:'添加',
            activeRecord:null
        });
        this.mon(win,'refreshStore',function(){
            view.getStore().load();
        });
        win.show();
    },

    updateClick:function(grid, rowIndex, colIndex,item,e,record){
        var view=this.getView();
        var win=Ext.widget('standingBookForm',{
            title:'编辑',
            activeRecord:record
        });
        this.mon(win,'refreshStore',function(){
            view.getStore().load();
        });
        win.show();
    },
    deleteClick:function(grid, rowIndex, colIndex,item,e,record){
        var me=this;
        Ext.Msg.confirm('删除','如果删除该数据，会将其所创建的菜单一并删除，您确定要删除该数据？',function(btn){
            if(btn=='yes'){
                Ext.Ajax.request({
                    url: 'standingbook/delete',
                    params: {id:record.get('id')},
                    method: 'POST',
                    success: function (response, options) {
                        var result = Ext.decode(response.responseText,true);
                        if(result.success){
                            var menuTree=Ext.ComponentQuery.query('#treeNav')[0]
                            menuTree.getStore().load({
                                callback:function(){
                                    menuTree.expandAll();
                                }
                            });
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
        var tableId=record.get('tableId');
        var fields=record.get('fields');
        var win=Ext.widget('featureForm',{
            title:'权限(这里只模拟了一个用户，如果要改变权限，重新赋权后刷新界面即可看见效果)',
            tableId:tableId,
            fields:fields
        });
        win.show();
    },
    roleGridItemClick:function(g, record, item, index, e, eOpts){
        var userFieldFeatures = this.getViewModel().getStore('userFieldFeatures');
        var tableId=this.getViewModel().get('tableId');
        var fields=record.get('features');
        var datas=[];
        if(!fields || fields.length<1){
            fields=this.getViewModel().get('fields');
            for(var i=0;i<fields.length;i++){
                datas.push(new App.model.RoleFieldFeature({
                    fieldId:fields[i].id,
                    fieldCid:fields[i].cid,
                    fieldName:fields[i].name,
                    tableId:tableId
                }));
            }
        }else{
            datas=fields;
        }
        var tableFeatures=record.get('tableFeatures');
        if(tableFeatures && tableFeatures.length>0){
            for(var i=0;i<tableFeatures.length;i++){
                if(tableId==tableFeatures[i].tableId){
                    this.getView().down('#tableFeatureCheck').setValue({
                        rb:tableFeatures[i].queryType
                    });
                }
            }
        }
        userFieldFeatures.setData(datas);
    },
    rolesChange:function(c,nValue,oValue){
        var grid = this.getView().down('#userGrid');
        var store=grid.getStore();
        var rec=grid.getSelectionModel().getSelection()[0];
        var model=this.getViewModel();
        var add=true;
        store.each(function(rec){
            if(rec.get('id')==nValue){
                add=false;
            }
        });

        if(!add){
            Ext.Msg.alert('提示','该角色已经添加',function(){
                store.remove(rec);
                grid.getView().refresh();
            });

            return;
        }
        rec.set('id',nValue);
        var userFieldFeatures = this.getViewModel().getStore('userFieldFeatures');
        var tableId=this.getViewModel().get('tableId');
        var roleId=rec.get('id');
        if(isNaN(roleId)==false){
            userFieldFeatures.getProxy().extraParams={tableId:tableId,roleId:roleId};
            userFieldFeatures.load({
                callback: function(records, operation, success){
                    if(records.length==0){
                        var datas=[];
                        var fields=model.get('fields');
                        for(var i=0;i<fields.length;i++){
                            datas.push(new App.model.RoleFieldFeature({
                                fieldId:fields[i].id,
                                fieldCid:fields[i].cid,
                                fieldName:fields[i].name,
                                tableId:tableId
                            }));
                        }
                        userFieldFeatures.setData(datas);
                    }
                }
            });
        }
        var tableFeatures=rec.get('tableFeatures');
        if(tableFeatures && tableFeatures.length>0){
            for(var i=0;i<tableFeatures.length;i++){
                if(tableId==tableFeatures[i].tableId){
                    this.getView().down('#tableFeatureCheck').setValue({
                        rb:tableFeatures[i].queryType
                    });
                }
            }
        }

    },
    addUserClick:function(){
        var grid= this.getView().down('#userGrid');
        var store=grid.getStore();
        var last=grid.getStore().last();
        if(last){
            var id=last.get('id');
            id=new String(id);
            if(id.indexOf('User')!=-1){
                Ext.Msg.alert('提示','请选择角色');
                return;
            }
        }
        var tableId=this.getViewModel().get('tableId');
        var fields=this.getViewModel().get('fields');
        var datas=[];
        for(var i=0;i<fields.length;i++){
            datas.push(new App.model.RoleFieldFeature({
                fieldId:fields[i].id,
                fieldCid:fields[i].cid,
                fieldName:fields[i].name,
                tableId:tableId
            }));
        }
        var rec = new App.model.Role({
            features:datas
        });
        var tableFeatureCheck=this.getView().down('#tableFeatureCheck');
        tableFeatureCheck.show();
        store.add(rec);
        grid.getPlugin('cellEditingUser').startEditByPosition({
            row: Math.max(0,grid.getStore().getCount()-1),
            column: 0
        });
    },
    saveFeature:function(){
        var win=this.getView();
        var grid= win.down('#userGrid');
        var store=grid.getStore();
        if(store.getCount()<1){
            Ext.Msg.alert('提示','请添加角色');
            return;
        }
        var tablesGrid=win.down('#TablesGrid');
        var tableStore=tablesGrid.getStore();

        //var features=[];
        //tableStore.each(function(rec){
        //    var temp=rec.data;
        //    temp.id=null;
        //    features.push(temp);
        //});
        var data=[];
        store.each(function(rec){
            var obj={};
            obj.id=rec.get('id');
            var features=rec.get('features'),tempFeatures=[];
            if(features && features.length>0){
                for(var i=0;i<features.length;i++){
                    var temp=features[i].data;
                    if(temp==null){
                        temp=features[i];
                    }
                    temp.id=null;
                    tempFeatures.push(temp);
                }
            }
            obj.features=tempFeatures;
            obj.tableFeatures=rec.get('tableFeatures')||[{queryType:0}];
            data.push(obj);

        });
        //console.log(data);
        //return;
        Ext.Ajax.request({
            url: 'user/saveFeature',
            jsonData: data,
            method: 'POST',
            success: function (response, options) {
                var result = Ext.decode(response.responseText,true);
                if(result.success){
                    Ext.Msg.alert('成功','保存成功',function(){
                        win.close();
                    });
                }else{
                    Ext.Msg.alert('失败',result.message);
                }
            }
        });
    }
});