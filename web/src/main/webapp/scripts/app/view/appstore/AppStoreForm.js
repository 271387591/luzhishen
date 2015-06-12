/**
 * Created by lihao on 1/6/15.
 */
Ext.define('App.view.appstore.AppStoreForm', {
    extend: 'Lzs.ux.window.AnimWindow',
    alias: 'widget.appStoreForm',
    controller: 'appStoreController',
    viewModel: {
        type: 'appStoreModel'
    },
    config: {
        title: undefined,
        activeRecord: null
    },
    controller: 'appStoreController',
    viewModel: {
        type: 'appStoreModel'
    },
    layout: 'fit',
    resizable: false,
    modal: true,
    width: 700,
    border:true,
    autoHeight: true,
    initComponent: function () {
        var me = this;
        me.items=[
            {
                xtype: 'form',
                frame: true,
                bodyPadding: 5,
                layout: 'anchor',
                defaults: {
                    anchor: '100%'
                },
                autoScroll: true,
                buttons: [
                    {
                        xtype: 'button',
                        itemId: 'save',
                        text: globalRes.buttons.save,
                        formBind: true,
                        handler: 'save'
                    },
                    {
                        xtype: 'button',
                        text: globalRes.buttons.cancel,
                        handler: function () {
                            me.close();
                        }
                    }
                ],
                items: [
                    {
                        xtype: 'fieldset',
                        checkboxToggle: false,
                        title: appStoreRes.header.title,
                        autoHeight: true,
                        defaultType: 'textfield',
                        defaults: {               // defaults are applied to items, not the container
                            anchor: '100%'
                        },
                        collapsed: false,
                        items: [
                            {
                                xtype: 'hidden',
                                bind:'{rec.id}',
                                name: 'id'
                            },
                            {
                                fieldLabel: '<font color="red">*</font>'+appStoreRes.header.version,
                                name: 'version',
                                maxLength: 50,
                                minLength: 1,
                                bind:'{rec.version}',
                                blankText: globalRes.tooltip.notEmpty,
                                allowBlank: false
                            },
                            {
                                xtype: 'filefield',
                                name: 'fileName',
                                fieldLabel: '<font color="red">*</font>'+appStoreRes.header.fileName,
                                msgTarget: 'side',
                                allowBlank: false,
                                bind:'{rec.fileName}',
                                buttonText: '选择文件'
                            },
                            {
                                fieldLabel:'<font color="red">*</font>'+appStoreRes.header.platform,
                                xtype:'combo',
                                name:'platform',
                                mode:'local',
                                editable:false,
                                triggerAction:'all',
                                bind:'{rec.platform}',
                                store:[
                                    ['Android', 'Android']
                                ]
                            },
                            {
                                fieldLabel:'<font color="red">*</font>最新版本',
                                xtype:'combo',
                                name:'currentVersion',
                                mode:'local',
                                editable:false,
                                triggerAction:'all',
                                bind:'{rec.currentVersion}',
                                store:[
                                    ['true', '是'],
                                    ['false', '否']
                                ]
                            },
                            
                            {
                                xtype:'textareafield',
                                grow: true,
                                fieldLabel: appStoreRes.header.description,
                                anchor: '100%',
                                bind:'{rec.description}',
                                name: 'description'
                            }
                        ]
                    }
                ]
            }
        ];
        me.callParent(arguments);
    },
    setActiveRecord: function (record) {
        this.activeRecord = record;
        if(record){
            this.down('form').getForm().loadRecord(record);
        }

    }
});