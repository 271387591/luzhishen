/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.view.money.UserMoneyController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.userMoneyController',
    requires:[
    ],

    onAddClick:function(){
        var view=this.getView();
        var model=view.getViewModel();
        var sModel=view.getSelectionModel();
        if(!sModel.hasSelection()){
            Ext.Msg.alert('提示','请选择用户');
            return;
        }
        var recs=sModel.getSelection();
        var win=Ext.widget('userMoneyForm',{
            height:view.getHeight(),
            title:'修改平台金'
        });
        win.animShow();
        win.setActiveRecord(recs[0]);
        this.mon(win,'refreshStore',function(){
            view.getStore().load();
        });
    },
    save:function(){
        var view=this.getView();
        var model=view.getViewModel();
        var rec=model.get('rec');
        Ext.Ajax.request({
            url: 'money/update',
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
    detailClick:function(grid, rowIndex, colIndex,item,e,record){
        var view=this.getView();
        var model=view.getViewModel();
        var win=Ext.widget('userMoneyDetail',{
            height:view.getHeight(),
            moneyId:record.get('id'),
            title:'平台金明细'
        });
        win.animShow();
    }
});