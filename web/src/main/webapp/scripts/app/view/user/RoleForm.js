/**
 * Created by lihao1 on 5/23/15.
 */
Ext.define('App.view.user.RoleForm', {
    extend: 'Ext.ux.window.AnimWindow',
    alias: 'widget.roleForm',
    requires: [
        'App.store.FeatureTree'
    ],

    config: {
        title: undefined,
        activeRecord: null
    },
    controller: 'roleController',
    viewModel: {
        type: 'roleModel'
    },
    layout: 'fit',
    resizable: false,
    modal: true,
    width: 700,
    border:true,
    autoHeight: true,

    initComponent: function () {
        var me = this;
        var store = Ext.create('App.store.FeatureTree');
        //if(me.isEdit){
        //    store.load({
        //        params:{
        //            roleId:me.roleId
        //        }
        //    });
        //}
        me.items=[
            {
                buttons: [
                    {
                        text: globalRes.buttons.save,
                        formBind: true,
                        handler: 'save'
                    },
                    {
                        xtype: 'button',
                        text: globalRes.buttons.cancel,
                        handler: function () {
                            var win = this.up('window');
                            win.close();
                        }
                    }
                ],
                autoScroll:true,
                xtype: 'form',
                frame:true,
                bodyPadding: 5,
                layout: 'anchor',
                items: [
                    {
                        xtype: 'fieldset',
                        checkboxToggle: false,
                        title: userRoleRes.header.roleInfo,
                        defaultType: 'textfield',
                        defaults: {
                            anchor: '70%'
                        },
                        collapsed: false,
                        items: [
                            {
                                xtype: 'hidden',
                                bind:'{rec.id}',
                                name: 'id'
                            },
                            {
                                name: 'name',
                                fieldLabel: '<font color="red">*</font>'+userRoleRes.header.roleName,
                                allowBlank: false,
                                bind:'{rec.name}'
                            },{
                                name: 'displayName',
                                bind:'{rec.displayName}',
                                fieldLabel: '<font color="red">*</font>'+userRoleRes.header.displayName,
                                allowBlank: false
                            },{
                                name: 'description',
                                bind:'{rec.description}',
                                fieldLabel: '&nbsp;&nbsp;'+userRoleRes.header.description
                            }
                        ]
                    },
                    {
                        xtype: 'fieldset',
                        title: '权限分配',
                        checkboxToggle: false,
                        items:[
                            {
                                xtype:'treepanel',
                                store:store,
                                minHeight:400,
                                rootVisible: false,
                                listeners:{
                                    checkchange:function( node, checked, eOpts ){
                                        if(checked){
                                            me.changeParentNode(node,checked);
                                            this.expandNode(node);
                                        }else{
                                            me.changeChildNode(node,checked);
                                            this.collapseNode(node);
                                        }

                                    },
                                    afterrender:function(f){
                                        if(me.isEdit){
                                            f.getStore().load({
                                                params:{
                                                    roleId:me.roleId
                                                },
                                                callback:function(){
                                                    f.expandAll();
                                                }
                                            });
                                        }
                                    }
                                }
                            }

                        ]
                    }
                ]
            }

        ];
        this.callParent(arguments);
    },
    changeParentNode:function(node,checked){
        var me=this;
        var pNode=node.parentNode;
        if(pNode.id!='root' && checked){
            pNode.set('checked',checked)
            me.changeParentNode(pNode,checked);
        }
    },
    changeChildNode:function(node,checked){
        var me=this;
        var childNodes=node.childNodes;
        if(childNodes!=null && childNodes.length>0 && !checked){
            for(var i=0;i<childNodes.length;i++){
                childNodes[i].set('checked',checked)
                me.changeChildNode(childNodes[i],checked);
            }
        }
    },

    getFormPanel: function () {
        return this.down('form');
    },

    setActiveRecord: function (record) {
        this.activeRecord = record;
        if (record) {
            this.getFormPanel().loadRecord(record);
        } else {
            this.getFormPanel().getForm().reset();
        }
    }
});