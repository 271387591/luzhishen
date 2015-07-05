/**
 * Created by lihao1 on 5/24/15.
 */

Ext.define('App.view.comments.CommentsController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.commentsController',
    requires:[
    ],

    deleteClick:function(){
        var view=this.getView();
        var model=view.getViewModel();
        var sModel=view.getSelectionModel();
        if(!sModel.hasSelection()){
            Ext.Msg.alert('提示','请选择数据');
            return;
        }
        var recs=sModel.getSelection();
        var ids=[];
        for(var i=0;i<recs.length;i++){
            ids.push((recs[i].get('id')));
        }
        Ext.Msg.confirm('提示', '你确定要删除这些数据？', function (txt) {
            if (txt === 'yes') {
                ajaxPostRequest('comments/delete',{ids:ids.join(",")},function(result){
                    if (result.success) {
                        Ext.Msg.alert('成功','删除成功',function(){
                            view.getStore().load();
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