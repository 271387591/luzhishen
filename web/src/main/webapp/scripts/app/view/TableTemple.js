/**
 * Created by lihao on 4/24/15.
 */

Ext.define('App.view.TableTemple', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.tableTemple',
    requires: [
        'App.view.TableTempleController',
        'App.view.TableTempleModel',
        'App.view.TableTempleForm'
    ],

    autoScroll:true,
    controller: 'tableTempleController',
    viewModel: {
        type: 'tableTempleModel'
    },
    initComponent:function(){
        var me=this;
        var fields=me.tableFields;
        var columns=[];
        var storeFields=['id'],tbars=[{
            iconCls: 'user-add',
            text:'新增',
            handler:'onAddClick'
        },'->'];

        if(fields&&fields.length>0){
            for(var i=0;i<fields.length;i++){
                var fieldId=fields[i].id;
                var availability=fields[i].availability;
                var isQuery=fields[i].isQuery;
                var name=fields[i].name;
                var cid=fields[i].cid;
                var inputType=fields[i].inputType;
                var type=fields[i].type;
                var obj={};
                obj.dataIndex=cid;
                if(fields[i].dataSourceId && fields[i].inputDisplayField!=fields[i].inputValueFiled){
                    storeFields.push(cid+"_display");
                    obj.dataIndex=cid+"_display";
                }

                if(availability){
                    obj.text=name;
                    obj.flex=1;
                    obj.hidden=(!(accessFeature(me.tableId,fieldId).canQuery.valueOf()));
                    columns.push(obj);
                }
                if(isQuery){
                    var field;
                    if(inputType==1){
                        var inputValueFiled=fields[i].inputValueFiled;
                        var inputDisplayField=fields[i].inputDisplayField;
                        var dataSourceId=fields[i].dataSourceId;
                        var store=[];
                        try{
                            store=Ext.create('Ext.data.Store',{
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
                            store=[];
                        }
                        field={
                            store:store,
                            xtype:'combo',
                            typeAhead: true,
                            pageSize:25,
                            triggerAction: 'all',
                            editable:false,
                            hidden:(!(accessFeature(me.tableId,fieldId).canQuery.valueOf())),
                            displayField: inputDisplayField,
                            valueField: inputValueFiled,
                            name:'Q_'+cid+'_LK',
                            bind:'{rec.Q_'+cid+'_LK}',
                            fieldLabel:name,
                            width:400
                        }
                    }else if(inputType == 2){

                        var inputValueFiled=fields[i].inputValueFiled;
                        var gridHeader=fields[i].gridHeader;
                        var dataSourceId=fields[i].dataSourceId;
                        var inputDisplayField=fields[i].inputDisplayField;
                        var wcolumns=[
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
                        var valueField=inputValueFiled;
                        var displayField=inputDisplayField;
                        field={
                            xtype:'container',
                            items:[
                                {
                                    xtype:'textfield',
                                    hidden:true,
                                    name:'Q_'+cid+'_LK',
                                    bind:'{rec.Q_'+cid+'_LK}',
                                    itemId:'Q_'+cid+'_LK'
                                },
                                {
                                    xtype:'textfield',
                                    fieldLabel:name,
                                    labelWidth:100,
                                    hidden:(!(accessFeature(me.tableId,fieldId).canQuery.valueOf())),
                                    gstore:gstore,
                                    wcolumns:wcolumns,
                                    bind:'{rec.'+cid+'}',
                                    cid:cid,
                                    triggers: {
                                        foo: {
                                            cls: 'field',
                                            handler: function(){
                                                var ff=this;
                                                var win=me.createWindow(this.gstore,this.wcolumns,function(records){
                                                    var texts=[],ids=[];
                                                    if(records){
                                                        for(var i=0;i<records.length;i++){
                                                            texts.push(records[i].get(displayField));
                                                            ids.push(records[i].get(valueField));
                                                        }
                                                    }
                                                    Ext.ComponentQuery.query('#'+'Q_'+ff.cid+'_LK')[0].setValue(ids.join(','));
                                                    ff.setValue(texts.join(','));
                                                });
                                                win.show();
                                            }
                                        }
                                    }
                                }
                            ]
                        }

                    }else if(inputType==3){
                        field={
                            xtype: 'datefield',
                            hidden:(!(accessFeature(me.tableId,fieldId).canQuery.valueOf())),
                            format: 'Y-m-d',
                            name:'Q_'+cid+'_LK',
                            bind:'{rec.Q_'+cid+'_LK}',
                            fieldLabel:name
                        }
                    }else if(inputType==5){
                        var store=[];
                        var dataSourceId=fields[i].dataSourceId;
                        var inputValueFiled=fields[i].inputValueFiled;
                        var parent=fields[i].treeParent;
                        var parentDisplay=fields[i].inputDisplayField;
                        var treeId=fields[i].treeId;
                        var filedValue=inputValueFiled;
                        var tstore;

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
                            //store.load();
                        }catch (e){
                            tstore=[];
                        }

                        field={
                            xtype:'container',
                            items:[
                                {
                                    xtype:'textfield',
                                    hidden:true,
                                    name:'Q_'+cid+'_LK',
                                    bind:'{rec.Q_'+cid+'_LK}',
                                    itemId:'Q_'+cid+'_LK'
                                },
                                {
                                    xtype:'textfield',
                                    fieldLabel:name,
                                    labelWidth:100,
                                    bind:'{rec.'+cid+'}',
                                    hidden:(!(accessFeature(me.tableId,fieldId).canQuery.valueOf())),
                                    tstore:tstore,
                                    cid:cid,
                                    triggers: {
                                        foo: {
                                            cls: 'field',
                                            handler: function(){
                                                var ff=this;
                                                var ttstore=this.tstore,ccid=this.cid;
                                                var tree=Ext.widget('treepanel',{
                                                    store: ttstore,
                                                    rootVisible: false,
                                                    autoScroll:true,
                                                    containerScroll: true
                                                });


                                                var win=me.createTreeWindow(tree,function(records){
                                                    var texts=[],ids=[];
                                                    if(records){
                                                        for(var i=0;i<records.length;i++){
                                                            texts.push(records[i].get(parentDisplay));
                                                            ids.push(records[i].get(filedValue));
                                                        }
                                                    }
                                                    ff.setValue(texts.join(','));
                                                    Ext.ComponentQuery.query('#'+'Q_'+ccid+'_LK')[0].setValue(ids.join(','));
                                                });
                                                win.show();
                                            }
                                        }
                                    }
                                }
                            ]
                        }

                    }else {
                        if(type=='int' || type=='bigint' || type=='double'){

                            field={
                                xtype: 'numberfield',
                                fieldLabel:name,
                                hideTrigger: true,
                                hidden:(!(accessFeature(me.tableId,fieldId).canQuery.valueOf())),
                                keyNavEnabled: false,
                                name:'Q_'+cid+'_LK',
                                bind:'{rec.Q_'+cid+'_LK}',
                                labelWidth:100,
                                mouseWheelEnabled: false
                            }

                        }else{
                            field={
                                xtype:'textfield',
                                bind:'{rec.'+cid+'}',
                                name:'Q_'+cid+'_LK',
                                bind:'{rec.Q_'+cid+'_LK}',
                                hidden:(!(accessFeature(me.tableId,fieldId).canQuery.valueOf())),
                                fieldLabel:name,
                                labelWidth:100
                            }
                        }
                    }
                    tbars.push(field);
                }
                storeFields.push(fields[i].cid);
            }
            var showEdit=false;
            for(var i=0;i<columns.length;i++){
                if(columns[i].hidden==false){
                    showEdit=true;
                    break;
                }
            }

            if(showEdit){
                columns.push({
                    xtype:'actioncolumn',
                    width:80,
                    header: '操作',
                    items:[
                        {
                            iconCls:'user-edit',
                            tooltip:'编辑',
                            handler:'updateClick'
                        },
                        '-',
                        {
                            iconCls:'user-delete',
                            tooltip:'删除',
                            handler:'deleteClick'
                        }

                    ]
                });
            }
            if(tbars.length>2){
                tbars.push({
                    text:'查询',
                    handler:'searchClick'
                });
                tbars.push({
                    text:'清空',
                    handler:'cleanClick'
                });
            }
        }
        me.tbar=tbars;

        me.columns=columns;
        var params={
            tableId:me.tableId
        };
        var deptFiled,creatorField;
        for(var i=0;i<accesses.length;i++){
            if(accesses[i].isDept.valueOf()){
                deptFiled=accesses[i].fieldCid;
            }
            if(accesses[i].isCreator.valueOf()){
                creatorField=accesses[i].fieldCid;
            }
        }


        var queryType=accessTableFeature(me.tableId,globalRes.roleId).queryType;
        if(queryType==1){
            if(creatorField){
                params['Q_'+creatorField+'_EQ']=globalRes.depId;
            }
        }else if(queryType==2){
            if(deptFiled){
                params['Q_'+deptFiled+'_EQ']=globalRes.depId;
            }
        }
        //console.log('queryType',queryType)
        //console.log('params',params)
        var store=Ext.create('Ext.data.Store',{
            fields:storeFields,
            proxy: {
                type: 'ajax',
                url: 'temple/list',
                reader: {
                    type: 'json',
                    root: 'data',
                    totalProperty: 'total',
                    messageProperty: 'message'
                },
                extraParams:params
            }

        });
        store.load();
        me.store=store;
        me.dockedItems=[
            {
                xtype: 'pagingtoolbar',
                store:store,
                dock: 'bottom',
                displayInfo: true
            }
        ];
        me.getViewModel().set('fields',fields);
        me.getViewModel().set('tableId',me.tableId);
        me.callParent();
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
                    xtype:'grid',
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
    createTreeWindow:function(item,fuc){
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
                item

            ]
        });
        return win;
    }
});