/**
 * Created by lihao on 4/24/15.
 */
Ext.define('App.view.FeatureForm', {
    extend: 'Ext.window.Window',
    alias: 'widget.featureForm',

    requires: [
    ],

    mySelf: false,
    resizable: false,
    controller: 'standingBookController',
    viewModel: {
        type: 'standingBookModel'
    },
    

    initComponent: function () {
        var me = this;
        var fields=me.fields;
        me.getViewModel().set('tableId',me.tableId);
        me.getViewModel().set('fields',me.fields);
        Ext.apply(this, {
            layout: 'fit',
            autoShow: true,
            modal: true,
            width: 800,
            height: 400,
            border: false,
            //minWidth: 600,
            //maxHeight: 600,
            autoScroll: true,
            buttons:[
                {
                    text:'保存',
                    handler:'saveFeature'
                },
                {
                    text:'关闭',
                    handler:function(){
                        this.up('window').close();
                    }
                }
            ],
            listeners:{
                afterrender:function(w){
                    var store = this.getViewModel().getStore('tableRoles');
                    var grid=this.down('#userGrid');
                    var userFieldFeatures = this.getViewModel().getStore('userFieldFeatures');
                    store.getProxy().extraParams={tableId:me.tableId};
                    store.load({
                        callback: function(records, operation, success){
                            var datas=[];
                            if(records && records.length>0){
                                for(var i=0;i<records.length;i++){
                                    if(i==0){
                                        grid.getSelectionModel().select(records[i]);
                                        var features=records[i].get('features');
                                        datas=features;
                                        userFieldFeatures.setData(datas);
                                        var tableFeature=records[i].get('tableFeatures');
                                        if(tableFeature && tableFeature.length>0){
                                            w.down('#tableFeatureCheck').setValue({
                                                rb:tableFeature[i].queryType
                                            });
                                        }
                                        w.down('#tableFeatureCheck').show();

                                        break;
                                    }
                                }
                            }
                        }
                    });
                }
            },
            items: [
                {
                    xtype:'panel',
                    layout:'border',
                    border:false,
                    items:[
                        {
                            region:'west',
                            xtype:'grid',
                            width:200,
                            bind:{
                                store:'{tableRoles}'
                            },
                            itemId:'userGrid',
                            tbar:[
                                {
                                    text:'增加',
                                    handler:'addUserClick'
                                }
                                
                            ],
                            plugins:[
                                new Ext.grid.plugin.CellEditing({
                                    clicksToEdit: 1,
                                    pluginId:'cellEditingUser'
                                })
                            ],
                            listeners:{
                                itemclick:'roleGridItemClick'
                            },
                            columns:[
                                {
                                    header: '角色名称',
                                    flex:1,
                                    dataIndex: 'name',
                                    editor: {
                                        xtype:'combo',
                                        displayField: 'name',
                                        valueField: 'id',
                                        emptyText:'必填',
                                        triggerAction: 'all',
                                        editable:false,
                                        bind:{
                                            store:'{roles}'
                                        },
                                        listeners:{
                                            change:'rolesChange'

                                        },
                                        allowBlank: true
                                    }
                                },
                                {
                                    xtype:'actioncolumn',
                                    width:60,
                                    header: '操作',
                                    items:[
                                        {
                                            iconCls:'user-delete',
                                            tooltip:'删除',
                                            handler:'deleteUserClick'
                                        }
                                    ]
                                }
                            ]
                            
                            
                        },
                        {
                            margin:'0 0 0 4',
                            region:'center',
                            xtype:'grid',
                            bind:{
                                store:'{userFieldFeatures}'
                            },
                            itemId:'TablesGrid',
                            tbar:[
                                {
                                    xtype: 'radiogroup',
                                    fieldLabel: '表数据访问权限',
                                    itemId:'tableFeatureCheck',
                                    hidden:true,
                                    listeners:{
                                        change:function(r,nValue){
                                            var roleSel=me.down('#userGrid').getSelectionModel().getSelection()[0];
                                            if(roleSel){
                                                var features=roleSel.get('tableFeatures');
                                                if(features && features.length>0){
                                                    for(var i=0;i<features.length;i++){
                                                        var tableId=features[i].tableId;
                                                        if(tableId==me.tableId){
                                                            features[i].queryType=nValue.rb;
                                                        }
                                                    }
                                                }else{
                                                    features=[];
                                                    var obj={};
                                                    obj.queryType=nValue.rb;
                                                    obj.roleId=roleSel.get('id');
                                                    obj.tableId=me.tableId;
                                                    features.push(obj);
                                                    roleSel.set('tableFeatures',features);
                                                }
                                            }
                                        }
                                    },
                                    items: [
                                        { boxLabel: '全部数据', name: 'rb', inputValue: '0',checked: true},
                                        { boxLabel: '仅用户自己的数据', name: 'rb', inputValue: '1'},
                                        { boxLabel: '用户所在部门的数据', name: 'rb', inputValue: '2'}
                                    ]
                                }
                            ],
                            columns:[
                                {
                                    header: '标识列',
                                    flex:1,
                                    sortable:false,
                                    enableColumnHide:false,
                                    dataIndex: 'fieldCid'
                                },
                                {
                                    header: '名称',
                                    flex:1,
                                    sortable:false,
                                    dataIndex: 'fieldName'
                                },
                                {
                                    xtype: 'checkcolumn',
                                    header: '查询',
                                    sortable:false,
                                    dataIndex: 'canQuery',
                                    width: 70,
                                    stopSelection: false,
                                    listeners:{
                                        checkchange:function(g, rowIndex, checked, eOpts){
                                            var roleSel=me.down('#userGrid').getSelectionModel().getSelection()[0];
                                            var featureSel=me.down('#TablesGrid').getStore().getAt(rowIndex);
                                            var features=roleSel.get('features');
                                            var featureFieldId= featureSel.get('fieldId');
                                            for(var i=0;i<features.length;i++){
                                                var fieldId=features[i].data.fieldId;
                                                if(featureFieldId==fieldId){
                                                    features[i].set('canQuery',checked);
                                                }

                                            }
                                        }
                                    }
                                },
                                {
                                    xtype: 'checkcolumn',
                                    header: '修改',
                                    sortable:false,
                                    dataIndex: 'canUpdate',
                                    width: 70,
                                    stopSelection: false,
                                    listeners:{
                                        checkchange:function(g, rowIndex, checked, eOpts){
                                            var roleSel=me.down('#userGrid').getSelectionModel().getSelection()[0];
                                            var featureSel=me.down('#TablesGrid').getStore().getAt(rowIndex);
                                            var features=roleSel.get('features');
                                            var featureFieldId= featureSel.get('fieldId');
                                            for(var i=0;i<features.length;i++){
                                                var fieldId=features[i].data.fieldId;
                                                if(featureFieldId==fieldId){
                                                    features[i].set('canUpdate',checked);
                                                }

                                            }
                                        }
                                    }
                                },
                                {
                                    xtype: 'checkcolumn',
                                    header: '增加',
                                    sortable:false,
                                    dataIndex: 'canAdd',
                                    width: 70,
                                    stopSelection: false,
                                    listeners:{
                                        checkchange:function(g, rowIndex, checked, eOpts){
                                            var roleSel=me.down('#userGrid').getSelectionModel().getSelection()[0];
                                            var featureSel=me.down('#TablesGrid').getStore().getAt(rowIndex);
                                            var features=roleSel.get('features');
                                            var featureFieldId= featureSel.get('fieldId');
                                            for(var i=0;i<features.length;i++){
                                                var fieldId=features[i].data.fieldId;
                                                if(featureFieldId==fieldId){
                                                    features[i].set('canAdd',checked);
                                                }

                                            }
                                        }
                                    }
                                },
                                {
                                    width: 80,
                                    xtype: 'checkcolumn',
                                    header: '区分部门ID',
                                    dataIndex: 'isDept',
                                    sortable:false,
                                    stopSelection: false,
                                    listeners: {
                                        checkchange: function (item, rowIndex, checked, eOpts) {
                                            var store=this.up('grid').getStore();
                                            var rec=store.getAt(rowIndex);
                                            store.each(function(m){
                                                if(rec.get('fieldId')!= m.get('fieldId')){
                                                    if(m.get('isDept')!=null){
                                                        m.set('isDept',false);
                                                    }
                                                }
                                            });
                                            var roleSel=me.down('#userGrid').getSelectionModel().getSelection()[0];
                                            var features=roleSel.get('features');
                                            var featureFieldId= rec.get('fieldId');
                                            for(var i=0;i<features.length;i++){
                                                var fieldId=features[i].data.fieldId;
                                                if(featureFieldId==fieldId){
                                                    features[i].set('isDept',checked);
                                                }else{
                                                    features[i].set('isDept',false);
                                                }
                                            }
                                        }
                                    }
                                },
                                {
                                    width: 90,
                                    xtype: 'checkcolumn',
                                    header: '区分创建者ID',
                                    dataIndex: 'isCreator',
                                    sortable:false,
                                    stopSelection: false,
                                    listeners: {
                                        checkchange: function (item, rowIndex, checked, eOpts) {
                                            var store=this.up('grid').getStore();
                                            var rec=store.getAt(rowIndex);
                                            store.each(function(m){
                                                if(rec.get('fieldId')!= m.get('fieldId')){
                                                    if(m.get('isCreator')!=null){
                                                        m.set('isCreator',false);
                                                    }
                                                }
                                            });
                                            var roleSel=me.down('#userGrid').getSelectionModel().getSelection()[0];
                                            var features=roleSel.get('features');
                                            var featureFieldId= rec.get('fieldId');
                                            for(var i=0;i<features.length;i++){
                                                var fieldId=features[i].data.fieldId;
                                                if(featureFieldId==fieldId){
                                                    features[i].set('isCreator',checked);
                                                }else{
                                                    features[i].set('isCreator', false);
                                                }

                                            }
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
        });
        this.callParent(arguments);
    }

})