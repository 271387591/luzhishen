/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.view.order.OrderController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.orderController',
    requires:[
    ],
    tabChange:function(){
        var model =  this.getView().getViewModel();
        model.set('rec.Q_name_LK','');
        model.set('rec.Q_serialNum_EQ','');
    },
    searchClick: function () {
        var model =  this.getView().getViewModel();
        var name = model.get('rec.Q_name_LK');
        var serialNum=model.get('rec.Q_serialNum_EQ');
        if(!name && !serialNum){
            return;
        }
        var store=this.getView().getActiveTab().getStore();
        var extraParams=store.getProxy().extraParams;
        extraParams['Q_creator.username_EQ_S']=name;
        extraParams['Q_orderNo_EQ_S']=serialNum;
        store.getProxy().extraParams=extraParams;
        store.loadPage(1);
    },
    cleanClick:function(){
        var model =  this.getView().getViewModel();
        model.set('rec.Q_name_LK','');
        model.set('rec.Q_serialNum_EQ','');
        var store=this.getView().getActiveTab().getStore();
        store.getProxy().extraParams['Q_creator.username_EQ_S']='';
        store.getProxy().extraParams['Q_orderNo_EQ_S']='';
        store.loadPage(1);
    }
});