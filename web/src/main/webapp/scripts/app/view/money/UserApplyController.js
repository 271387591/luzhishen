/**
 * Created by lihao1 on 5/25/15.
 */
Ext.define('App.view.money.UserApplyController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.userApplyController',
    requires:[
    ],
    tabChange:function(){
        var model =  this.getView().getViewModel();
        model.set('rec.Q_name_LK','');
        model.set('rec.Q_serialNum_EQ','');
        model.set('rec.Q_batchNo_LK','');
    },
    searchClick: function () {
        var model =  this.getView().getViewModel();
        var name = model.get('rec.Q_name_LK');
        var serialNum=model.get('rec.Q_serialNum_EQ');
        var batchNo=model.get('rec.Q_batchNo_LK');
        if(!name && !serialNum){
            return;
        }
        var store=this.getView().getActiveTab().getStore();
        var extraParams=store.getProxy().extraParams;
        extraParams['Q_creator.username_EQ_S']=name;
        extraParams['Q_applyNo_EQ_S']=serialNum;
        extraParams['Q_systemRmb.bachNo_EQ_S']=serialNum;
        store.getProxy().extraParams=extraParams;
        store.loadPage(1);
    },
    cleanClick:function(){
        var model =  this.getView().getViewModel();
        model.set('rec.Q_name_LK','');
        model.set('rec.Q_serialNum_EQ','');
        model.set('rec.Q_batchNo_LK','');
        var store=this.getView().getActiveTab().getStore();
        store.getProxy().extraParams['Q_creator.username_EQ_S']='';
        store.getProxy().extraParams['Q_applyNo_EQ_S']='';
        store.getProxy().extraParams['Q_systemRmb.bachNo_EQ_S']='';
        store.loadPage(1);
    },
    bachPay:function(){
        var grid =  this.getView().down('#nopayGrid');
        var sModel=grid.getSelectionModel();
        if(!sModel.hasSelection()){
            Ext.Msg.alert('提示','请选择用户');
            return;
        }
        var recs=sModel.getSelection();
        var ids=[];
        for(var i=0;i<recs.length;i++){
            ids.push((recs[i].get('id')));
        }
        Ext.Msg.confirm("提示","批量支付将会跳转至支付宝支付界面，待支付提交成功后，请返回该页面刷新界面即可看到支付结果，如果由于网络原因，没及时拿到支付宝回执，请过段时间再查询执行结果！你确定要批量支付么？",function(btn){
            if(btn=='yes'){
                window.open(basePath+'alipay/pay?ids='+ids.join(','),'_blank');
            }
        });
    },
    bachPay1:function(){
        var grid =  this.getView().down('#userapplyFailGrid');
        var sModel=grid.getSelectionModel();
        if(!sModel.hasSelection()){
            Ext.Msg.alert('提示','请选择用户');
            return;
        }
        var recs=sModel.getSelection();
        var ids=[];
        for(var i=0;i<recs.length;i++){
            ids.push((recs[i].get('id')));
        }
        Ext.Msg.confirm("提示","批量支付将会跳转至支付宝支付界面，待支付提交成功后，请返回该页面刷新界面即可看到支付结果，如果由于网络原因，没及时拿到支付宝回执，请过段时间再查询执行结果！你确定要批量支付么？",function(btn){
            if(btn=='yes'){
                window.open(basePath+'alipay/pay?ids='+ids.join(','),'_blank');
            }
        });
    },
    cancelApply:function(){
        var grid =  this.getView().down('#userapplyFailGrid');
        var sModel=grid.getSelectionModel();
        if(!sModel.hasSelection()){
            Ext.Msg.alert('提示','请选择用户');
            return;
        }
        var recs=sModel.getSelection();
        var ids=[];
        for(var i=0;i<recs.length;i++){
            ids.push((recs[i].get('id')));
        }
        var win=Ext.widget('window',{
            width:400,
            autoHeight:200,
            layout:'fit',
            title:'取消提现',
            modal:true,
            buttons:[
                {
                    text:'确定',
                    handler:function(){
                        var cancelDetail=win.down('form').getValues().cancelDetail;
                        ajaxPostRequest("apply/cancelApply",{ids:ids,cancelDetail:cancelDetail},function(result){
                            if(result.success){
                                Ext.Msg.alert('提示','取消成功',function(){
                                    grid.getStore().load();
                                })
                            }
                        })

                    }
                },
                {
                    text:'取消',
                    handler:function(){
                        win.close();
                    }
                }
            ],
            items:[
                {
                    xtype:'form',
                    padding:10,
                    border:false,
                    items:[
                        {
                            xtype     : 'textareafield',
                            grow      : true,
                            labelWidth:65,
                            name      : 'cancelDetail',
                            fieldLabel: '取消原因',
                            anchor    : '100%'
                        }
                    ]
                }
            ]
        });
        win.show();
    }
});