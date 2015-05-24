/**
 * Created by lihao1 on 5/23/15.
 */

Ext.define('App.view.user.RoleController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.roleController',
    requires:[
        //'App.model.Role'
    ],

    onAddClick:function(){
        var view=this.getView();
        var model=view.getViewModel();
        var win=Ext.widget('roleForm',{
            height:view.getHeight(),
            title:'添加角色'
        });
        win.animShow();
        this.mon(win,'refreshStore',function(){
            view.getStore().load();
        });
    },
    onEditClick:function(grid, rowIndex, colIndex,item,e,record){
        var view=this.getView();
        var model=view.getViewModel();
        var win=Ext.widget('roleForm',{
            height:view.getHeight(),
            title:'编辑角色',
            isEdit:true,
            roleId:record.get('id')
        });
        win.setActiveRecord(record);
        win.animShow();
        this.mon(win,'refreshStore',function(){
            view.getStore().load();
        });
    },
    deleteClick:function(grid, rowIndex, colIndex,item,e,record){
        var view=this.getView();
        var model=view.getViewModel();
        Ext.Msg.confirm("提示",'你确定要删除该角色',function(btn){
            if(btn=='yes'){
                ajaxPostRequest('role/remove/'+record.get('id'),{},function(result){
                    if(result.success){
                        Ext.Msg.alert('成功','删除成功',function(){
                            grid.getStore().load();
                        });

                    }else{
                        Ext.Msg.alert('提示',result.message);
                    }
                });
            }
        });
    },
    save:function(){
        var view=this.getView();
        var model=view.getViewModel();
        var rec=model.get('rec');
        var features=view.down('treepanel').getChecked();
        var featureCmd=[];
        for(var i=0;i<features.length;i++){
            var obj={};
            obj.id=features[i].get('id');
            featureCmd.push(obj);
        }
        rec.simpleFeatures=featureCmd;
        Ext.Ajax.request({
            url: 'role/save',
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

    }
});