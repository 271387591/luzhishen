/**
 * Created by lihao on 4/14/15.
 */
Ext.define('App.view.TableController', {
    extend: 'App.BaseController',

    requires: [
        'Ext.window.MessageBox',
    ],

    alias: 'controller.tableController',
    
    init: function() {
        var me=this;
    },
    onLaunch: function() {

    },
    searchClick: function () {
        var model =  this.getView().getViewModel();
        var name = model.get('rec.Q_name_LK');
        var databaseName=model.get('rec.Q_databaseName_LK');
        if(!name && !databaseName){
            return;
        }
        var store=this.getView().getStore();
        store.getProxy().extraParams={Q_name_LK:name,Q_databaseName_LK:databaseName};
        store.loadPage(1);


    },
    cleanClick:function(){
        var model =  this.getView().getViewModel();
        model.set('rec.Q_name_LK','');
        model.set('rec.Q_databaseName_LK','');
        var store=this.getView().getStore();
        store.getProxy().extraParams={};
        store.loadPage(1);
    },
    dataSourceFormOk:function(){
        var view=this.getView();
        var model=this.getViewModel();
        var sourceId=model.get("rec.sourceId");
        var grid=view.down('grid');
        var store=grid.getStore();

        var filedName,displayName,gridHeader,parent,treeId;
        store.each(function(rec){
            var saveDatabase=rec.get('saveDatabase');
            var cobomData=rec.get('displayName');
            var name=rec.get('name');
            var isParent=rec.get('parent');
            var isTreeId=rec.get('treeId');
            if(saveDatabase){
                filedName=name;
            }
            if(cobomData){
                displayName=name;
                gridHeader=rec.get('gridHeader');
            }
            if(isParent){
                parent=name;
            }
            if(isTreeId){
                treeId=name;
            }

        });
        if(filedName && !displayName){
            Ext.Msg.alert('提示','请选择数据表的字段，并设置显示名称');
            return;
        }
        //console.log(sourceId,filedName,displayName,gridHeader)

        view.fireEvent('okClick',{sourceId:sourceId,filedName:filedName,displayName:displayName,gridHeader:gridHeader,parent:parent,treeId:treeId});

    },
    datasourceTriggerClick:function(){
        var view=this.getView();
        var model=view.getViewModel();
        var grid=view.down('grid');
        var rec=grid.getSelectionModel().getSelection()[0];
        var inputType=rec.get('inputType');
        if(!inputType){
            Ext.Msg.alert('提示','请选择输入方式');
            return;
        }
        if(inputType==3 || inputType==4){
            Ext.Msg.alert('提示','该输入方式不需要设置数据源');
            return;
        }



        var win=Ext.widget('dataSourceForm',{
            inputType:rec.get('inputType')
        });
        var winViewModel=win.getViewModel();
        winViewModel.set('rec.sourceId',rec.get('dataSourceId'));
        winViewModel.set('rec.filedName',rec.get('inputValueFiled'));
        winViewModel.set('rec.displayName',rec.get('inputDisplayField'));
        winViewModel.set('rec.gridHeader',rec.get('gridHeader'));
        winViewModel.set('rec.parent',rec.get('treeParent'));
        winViewModel.set('rec.treeId',rec.get('treeId'));


        this.mon(win,'okClick',function(data){
            var rec=grid.getSelectionModel().getSelection()[0];
            rec.set('dataSourceId',data.sourceId);
            rec.set('inputValueFiled',data.filedName);
            rec.set('inputDisplayField',data.displayName);
            rec.set('gridHeader',data.gridHeader);
            rec.set('treeParent',data.parent);
            rec.set('treeId',data.treeId);
            win.close();
        })
        win.show();
    },
    dataSourceFormComboChange:function(c,nValue,oValue){
        var grid=this.getView().down('grid');
        var store=grid.getStore();
        var filedName=this.getView().getViewModel().get('rec.filedName');
        var displayName=this.getView().getViewModel().get('rec.displayName');
        var gridHeader=this.getView().getViewModel().get('rec.gridHeader');
        var parent=this.getView().getViewModel().get('rec.parent');
        var treeId=this.getView().getViewModel().get('rec.treeId');
        store.getProxy().extraParams={id:nValue};
        store.load({
            callback: function(records, operation, success) {
                for(var i=0;i<records.length;i++){
                    var name=records[i].get('name');
                    if(name==filedName){
                        records[i].set('saveDatabase',true);
                        records[i].set('gridHeader',gridHeader);
                    }

                    if(name==displayName){
                        records[i].set('displayName',true);
                    }
                    if(name==parent){
                        records[i].set('parent',true);
                    }
                    if(name==treeId){
                        records[i].set('treeId',true);
                    }
                }
            }
        });
        this.getViewModel().set('rec.tableName', c.getStore().getById(nValue).get('mtableName'));
    },


    saveClick:function(){
        var view=this.getView();
        var model=view.getViewModel();
        var grid=view.down('grid');
        var store=grid.getStore();
        if(store.getCount()<1){
            Ext.Msg.alert('提示','请至少填写一个表字段');
            return;
        }
        var first=store.last();
        if(!first.get('cid')){
            Ext.Msg.alert('提示','标识列必须填写');
            return;
        }
        if(!first.get('type')){
            Ext.Msg.alert('提示','类型必须填写');
            return;
        }
        var fields=[];
        store.each(function(rec){
            var data=rec.data;
            data.id=null;
            if(data.type=='date'){
                data.inputType='3';
            }
            fields.push(data);
        });
        var data=model.get('rec');
        data.fields=fields;
        //console.log(data)
        //return;
        Ext.Ajax.request({
            url: 'table/save',
            jsonData: Ext.encode(data),
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
    addClick: function () {
        var view=this.getView();
        this.getViewModel().set('rec.activeRecord',false);
        var panel=Ext.widget('tableForm',{
            itemId:'tableForm_add',
            title:'建立台账',
            closable:true
        });
        this.mon(panel,'refreshStore',function(){
            view.getStore().load();
        });
        this.addPanel(panel,'tableForm_add');

    },

    updateClick:function(grid, rowIndex, colIndex,item,e,record){
        var view=this.getView();
        var panel=Ext.widget('tableForm',{
            itemId:'tableForm_'+record.get('id'),
            title:'修改台账('+record.get('displayName')+')',
            activeRecord:record,
            closable:true
        });
        this.mon(panel,'refreshStore',function(){
            view.getStore().load();
        });
        this.addPanel(panel,'tableForm_'+record.get('id'));
    },
    deleteClick:function(grid, rowIndex, colIndex,item,e,record){
        var me=this;
        Ext.Msg.confirm('删除','如果删除该数据，会将该数据所创建的数据表以及其存储的数据一并删除，您确定要删除该数据？',function(btn){
            if(btn=='yes'){
                Ext.Ajax.request({
                    url: 'table/delete',
                    params: {id:record.get('id')},
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
    deleteFormClick:function(grid, rowIndex, colIndex,item,e,record){
        var me=this;
        me.getView().down('grid').getStore().remove(record);
    },
    
    addTableField:function(){
        var grid=this.getView().down('grid');
        var store=grid.getStore();
        var rec = new App.model.TableField();
        if(store.getCount()>0){
            var first=store.last();
            if(!first.get('cid')){
                Ext.Msg.alert('提示','标识列必须填写');
                return;
            }
            if(!first.get('type')){
                Ext.Msg.alert('提示','类型必须填写');
                return;
            }
        }
        grid.getStore().add(rec);
        grid.getPlugin('cellEditingFiled').startEditByPosition({
            row: (grid.getStore().getCount()-1),
            column: 0
        });
    }

});