/**
 * Created by lihao1 on 5/27/15.
 */
Ext.define('App.view.money.UserMoneyForm', {
    extend: 'Lzs.ux.window.AnimWindow',
    alias: 'widget.userMoneyForm',
    requires: [
    ],
    config: {
        title: undefined,
        activeRecord: null
    },
    controller: 'userMoneyController',
    viewModel: {
        type: 'userMoneyModel'
    },
    layout: 'fit',
    resizable: false,
    modal: true,
    width: 700,
    border:true,
    autoHeight: true,
    initComponent: function () {
        var me = this;
        var roleStore=me.roleStore;
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
                        title: '用户平台金',
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
                                xtype:'displayfield',
                                name:'username',
                                fieldLabel: userRoleRes.header.username
                            },
                            {
                                xtype:'displayfield',
                                fieldLabel: userRoleRes.header.mobile,
                                name: 'mobile'
                            },{
                                xtype:'numberfield',
                                hideTrigger: true,
                                keyNavEnabled: false,
                                mouseWheelEnabled: false,
                                fieldLabel: '用户平台金',
                                name: 'total',
                                bind:'{rec.total}'
                            }
                        ]
                    }
                ]
            }

        ];
        this.callParent(arguments);
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