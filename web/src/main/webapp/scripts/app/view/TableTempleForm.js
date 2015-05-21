/**
 * Created by lihao on 4/24/15.
 */
Ext.define('App.view.TableTempleForm', {
    extend: 'Ext.window.Window',
    alias: 'widget.tableTempleForm',

    requires: [
        'Ext.form.Panel',
        'Ext.data.Store'
    ],

    config: {
        title: undefined,
        activeRecord: null
    },
    mySelf: false,
    resizable: false,
    controller: 'tableTempleController',
    viewModel: {
        type: 'tableTempleModel'
    },


    initComponent: function () {
        var me = this;
        
        var items=[
            {
                xtype:'hidden',
                name:'id',
                bind:'{rec.id}'
            }
        ];
        var fields=me.fields;

        var uniqueNames=[];
        for(var i=0;i<fields.length;i++){
            var fieldId=fields[i].id;
            var inputType=fields[i].inputType;
            var cid=fields[i].cid;
            var name=fields[i].name;
            var type=fields[i].type;
            var isPrimaryKey=fields[i].isPrimaryKey;
            var isnull=fields[i].isnull;
            if(isPrimaryKey){
                uniqueNames.push(cid);
            }
            if(!isnull){
                name='<font color="red">*</font>'+name;
            }
            var field;
            if(inputType==1){
                var inputValueFiled=fields[i].inputValueFiled;
                var inputDisplayField=fields[i].inputDisplayField;
                var dataSourceId=fields[i].dataSourceId;
                var cstore=[];
                try{
                    cstore=Ext.create('Ext.data.Store',{
                        fields:[inputValueFiled,inputDisplayField],
                        proxy: {
                            type: 'ajax',
                            url: 'datasource/listDataSourceDatas',
                            reader: {
                                type: 'json',
                                root: 'data',
                                totalProperty: 'total',
                                messageProperty: 'message'
                            },
                            extraParams:{
                                id:dataSourceId
                            }
                        }

                    });
                }catch (e){
                    cstore=[];
                }
                field={
                    store:cstore,
                    xtype:'combo',
                    queryMode:'remote',
                    pageSize:25,
                    typeAhead: true,
                    triggerAction: 'all',
                    editable:false,
                    displayField: inputDisplayField,
                    blankText: '不能为空',
                    allowBlank: isnull,
                    valueField: inputValueFiled,
                    name:cid,
                    fieldLabel:name,
                    hidden:(!(accessFeature(me.tableId,fieldId).canAdd.valueOf())),
                    readOnly:(!(accessFeature(me.tableId,fieldId).canUpdate.valueOf())),
                    bind:'{rec.'+cid+'}',

                    labelWidth:100
                }
            }else if(inputType == 2){

                var inputValueFiled=fields[i].inputValueFiled;
                var gridHeader=fields[i].gridHeader;
                var dataSourceId=fields[i].dataSourceId;
                var inputDisplayField=fields[i].inputDisplayField;
                var columns=[
                    {
                        text:gridHeader,
                        dataIndex:inputDisplayField,
                        flex:1
                    }
                ];
                var gstore=[];
                try{
                    gstore=Ext.create('Ext.data.Store',{
                        fields:[inputValueFiled],
                        proxy: {
                            type: 'ajax',
                            url: 'datasource/listDataSourceDatas',
                            reader: {
                                type: 'json',
                                root: 'data',
                                totalProperty: 'total',
                                messageProperty: 'message'
                            },
                            extraParams:{
                                id:dataSourceId
                            }
                        }

                    });
                }catch (e){
                    gstore=[];
                }
                items.push({
                    xtype:'hidden',
                    name:cid,
                    bind:'{rec.'+cid+'}',
                    itemId:cid
                });
                var valueField=inputValueFiled;
                var displayField=inputDisplayField;
                field={
                    xtype:'textfield',
                    name:cid+'_display',
                    fieldLabel:name,
                    labelWidth:100,
                    bind:'{rec.'+cid+'_display}',
                    blankText: '不能为空',
                    allowBlank: isnull,
                    //readOnly:true,
                    hidden:(!(accessFeature(me.tableId,fieldId).canAdd.valueOf())),
                    readOnly:(!(accessFeature(me.tableId,fieldId).canUpdate.valueOf())),
                    gstore:gstore,
                    gcolumns:columns,
                    cid:cid,
                    triggers: {
                        foo: {

                            cls: 'field',
                            handler: function(b){
                                var ff=this;
                                var win=me.createWindow(this.gstore,this.gcolumns,function(records){
                                    var texts=[],ids=[];
                                    if(records){
                                        for(var i=0;i<records.length;i++){
                                            texts.push(records[i].get(displayField));
                                            ids.push(records[i].get(valueField));
                                        }
                                    }
                                    ff.setValue(texts.join(','));
                                    Ext.ComponentQuery.query('#'+ff.cid)[0].setValue(ids.join(','));
                                });
                                win.show();
                            }
                        }
                    }
                }
            }else if(inputType==3){
                field={
                    xtype: 'datefield',
                    format: 'Y-m-d',
                    name:cid,
                    bind:'{rec.'+cid+'}',
                    blankText: '不能为空',
                    hidden:(!(accessFeature(me.tableId,fieldId).canAdd.valueOf())),
                    readOnly:(!(accessFeature(me.tableId,fieldId).canUpdate.valueOf())),
                    allowBlank: isnull,
                    fieldLabel:name
                }
            }else if(inputType==5){
                var tstore=[];
                var dataSourceId=fields[i].dataSourceId;
                var inputValueFiled=fields[i].inputValueFiled;
                var parent=fields[i].treeParent;
                var parentDisplay=fields[i].inputDisplayField;
                var treeId=fields[i].treeId;
                var filedValue=inputValueFiled;
                try{
                    tstore=Ext.create('Ext.data.TreeStore',{
                        proxy: {
                            type: 'ajax',
                            url: 'datasource/listDataSourceTreeDatas',
                            extraParams:{
                                id:dataSourceId,
                                parent:parent,
                                parentDisplay:parentDisplay,
                                treeId:treeId
                            }
                        }

                    });
                }catch (e){
                    tstore=[];
                }
                items.push({
                    xtype:'hidden',
                    name:cid,
                    bind:'{rec.'+cid+'}',
                    itemId:cid
                });
                field={
                    xtype:'textfield',
                    name:cid+'_display',
                    fieldLabel:name,
                    labelWidth:100,
                    bind:'{rec.'+cid+'_display}',
                    blankText: '不能为空',
                    allowBlank: isnull,
                    hidden:(!(accessFeature(me.tableId,fieldId).canAdd.valueOf())),
                    readOnly:(!(accessFeature(me.tableId,fieldId).canUpdate.valueOf())),
                    tstore:tstore,
                    cid:cid,
                    triggers: {
                        foo: {
                            cls: 'field',
                            handler: function(){
                                var ff=this;
                                var win=me.createTreeWindow(this.tstore,function(records){
                                    var texts=[],ids=[];
                                    if(records){
                                        for(var i=0;i<records.length;i++){
                                            texts.push(records[i].get(parentDisplay));
                                            ids.push(records[i].get(filedValue));
                                        }
                                    }
                                    ff.setValue(texts.join(','));
                                    Ext.ComponentQuery.query('#'+ff.cid)[0].setValue(ids.join(','));
                                });
                                win.show();
                            }
                        }
                    }
                }
            }else {
                if(type=='int' || type=='bigint' || type=='double'){

                    field={
                        xtype: 'numberfield',
                        name:cid,
                        bind:'{rec.'+cid+'}',
                        fieldLabel:name,
                        hideTrigger: true,
                        keyNavEnabled: false,
                        blankText: '不能为空',
                        allowBlank: isnull,
                        labelWidth:100,
                        hidden:(!(accessFeature(me.tableId,fieldId).canAdd.valueOf())),
                        readOnly:(!(accessFeature(me.tableId,fieldId).canUpdate.valueOf())),
                        mouseWheelEnabled: false
                    }

                }else{
                    field={
                        xtype:'textfield',
                        hidden:(!(accessFeature(me.tableId,fieldId).canAdd.valueOf())),
                        readOnly:(!(accessFeature(me.tableId,fieldId).canUpdate.valueOf())),
                        name:cid,
                        bind:'{rec.'+cid+'}',
                        blankText: '不能为空',
                        allowBlank: isnull,
                        fieldLabel:name,
                        labelWidth:100
                    }
                }
            }
            items.push(field);
        }
        Ext.apply(this, {
            layout: 'fit',
            autoShow: true,
            modal: true,
            width: 500,
            autoHeight: true,
            border: false,
            minWidth: 600,
            maxHeight: 600,
            autoScroll: true,
            items: [
                {
                    xtype: 'form',
                    frame: true,
                    bodyPadding: 5,
                    layout: 'anchor',
                    defaults: {
                        anchor: '100%'
                    },
                    autoScroll: true,
                    listeners:{
                        afterrender:function(){
                            if(me.activeRecord){
                                this.loadRecord(me.activeRecord);
                            }
                        }
                    },
                    buttons: [
                        {
                            xtype: 'button',
                            text: '保存 ',
                            formBind: true,
                            handler: 'saveClick'
                        },
                        {
                            xtype: 'button',
                            text: '关闭',
                            handler: function () {
                                var win = this.up('window');
                                win.close();
                            }
                        }
                    ],
                    items: [
                        {
                            xtype: 'fieldset',
                            checkboxToggle: false,
                            title: '基本信息',
                            autoHeight: true,
                            defaultType: 'textfield',
                            defaults: {
                                anchor: '100%'
                            },
                            collapsed: false,
                            items: items
                        }
                    ]
                }
            ]
        });
        me.getViewModel().set('tableId',me.tableId);
        me.getViewModel().set('uniqueNames',uniqueNames);
        this.callParent(arguments);
    },
    createWindow:function(store,columns,fuc){
        var win=Ext.widget('window',{
            width:500,
            height:400,
            modal:true,
            layout:'fit',
            buttons:[
                {
                    text:'确定',
                    handler:function(){
                        var records = win.down('grid').getSelectionModel().getSelection();
                        fuc(records);
                        win.close();
                    }
                },
                {
                    text:'关闭',
                    handler:function(){
                        win.close();
                    }
                }
            ],
            items:[
                {
                    tbar:[
                        {
                            xtype:'textfield',
                            name:'Q_'+columns[0].dataIndex+'_LK',
                            itemId:'searchText'
                        },{
                            text:'查询',
                            handler:function(){
                                var value = this.up('window').down('#searchText').getValue();
                                if(value){
                                    var store=this.up('window').down('grid').getStore();
                                    var data=store.getProxy().extraParams;
                                    data['Q_'+columns[0].dataIndex+'_LK']=value;
                                    store.getProxy().extraParams=data;
                                    store.loadPage(1);
                                }
                            }
                        },{
                            text:'清空',
                            handler:function(){
                                this.up('window').down('#searchText').setValue('');
                                var store=this.up('window').down('grid').getStore();
                                var data = store.getProxy().extraParams;
                                var value={};
                                for(var i in data){
                                    if(i=='id'){
                                        value[i]=data[i];
                                    }
                                }
                                store.getProxy().extraParams=value;
                                store.loadPage(1);
                            }
                        }

                    ],
                    xtype:'grid',
                    selModel: {
                        selType: 'checkboxmodel'
                    },
                    store: store,
                    columns:columns,
                    dockedItems: [{
                        xtype: 'pagingtoolbar',
                        store: store,
                        dock: 'bottom',
                        displayInfo: true
                    }],
                    autoScroll:true,
                    listeners:{
                        afterrender:function(f){
                            f.getStore().load();
                        }
                    }
                }
            ]
        });
        return win;
    },
    createTreeWindow:function(store,fuc){
        var win=Ext.widget('window',{
            width:400,
            height:400,
            modal:true,
            layout:'fit',
            buttons:[
                {
                    text:'确定',
                    handler:function(){
                        var records = win.down('treepanel').getView().getChecked();
                        fuc(records);
                        win.close();
                    }
                },
                {
                    text:'关闭',
                    handler:function(){
                        win.close();
                    }
                }
            ],
            items:[
                {
                    xtype:'treepanel',
                    store: store,
                    rootVisible: false,
                    containerScroll: true
                }
            ]
        });
        return win;
    }

});