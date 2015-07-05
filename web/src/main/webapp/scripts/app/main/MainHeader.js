Ext.define('App.main.MainHeader', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.mainHeader',
    initComponent: function () {
        var me = this;
        Ext.apply(this, {
            border: false,
            layout: 'anchor',
            region: 'north',
            items: [
                {
                    xtype: 'toolbar',
                    height: 37,
                    items: [
                        {
                            xtype: 'label',
                            html: 'logo'
                        },
                        {
                            xtype: 'label',
                            style: 'font-size:16px;'
                        },
                        '->',
                        {
                            xtype: 'label',
                            id: 'head-lb-3',
                            cls: 'welcome',
                            style: 'font-size:12px',
                            text: '今天是' + this.getToday()
                        },
                        {
                            xtype: 'button',
                            text: globalRes.userName,
                            scale: 'small',
                            menu: [
                                {
                                    text:'修改密码',
                                    handler:function(){
                                        var win=Ext.widget('window',{
                                            width:400,
                                            autoHeight:200,
                                            layout:'fit',
                                            title:'修改密码',
                                            modal:true,
                                            buttons:[
                                                {
                                                    text:'确定',
                                                    handler:function(){
                                                        var pwd=win.down('form').getValues().password;
                                                        ajaxPostRequest("user/adminChangePassword",{id:globalRes.userId,newPassword:pwd},function(result){
                                                            if(result.success){
                                                                Ext.Msg.alert('提示','修改成功')
                                                            }else{
                                                                Ext.Msg.alert('提示',result.message)
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
                                                    layout:'form',
                                                    items:[
                                                        {
                                                            xtype:'textfield',
                                                            fieldLabel: '<font color="red">*</font>新密码',
                                                            name: 'password',
                                                            itemId:'password',
                                                            blankText:globalRes.tooltip.notEmpty,
                                                            minLength: 6,
                                                            minLengthText: userRoleRes.passwordError,
                                                            maxLength: 16,
                                                            maxLengthText: userRoleRes.passwordError,
                                                            inputType:'password',
                                                            allowBlank: false
                                                        },{
                                                            xtype:'textfield',
                                                            fieldLabel: '<font color="red">*</font>'+userRoleRes.passwordAffirm,
                                                            itemId:'passwordAffirm',
                                                            name: 'passwordAffirm',
                                                            blankText:globalRes.tooltip.notEmpty,
                                                            minLength: 6,
                                                            minLengthText: userRoleRes.passwordError,
                                                            maxLength: 16,
                                                            maxLengthText: userRoleRes.passwordError,
                                                            inputType:'password',
                                                            validator: function(v) {
                                                                var newPass = win.down('#password');
                                                                if (v == newPass.getValue()) {
                                                                    return true;
                                                                }
                                                                else {
                                                                    return userRoleRes.passwordHitNotAllow;
                                                                }
                                                            },
                                                            allowBlank: false
                                                        }
                                                    ]
                                                }
                                            ]
                                        });
                                        win.show();

                                    }
                                },{
                                    text:'安全退出',
                                    handler:function(){
                                        Ext.Msg.confirm('退出','你确定要退出么？',function(btn){
                                            if(btn=='yes'){
                                                document.location.replace(globalRes.logoutUrl);
                                            }
                                        })
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
        });
        this.callParent(arguments);
    },
    

    getToday: function () {
        var today = new Date();
        var date = Ext.Date.format(today, 'Y年m月d日');
        var week = Ext.Date.format(today, 'w');
        var weekArray = new Array("日", "一", "二", "三", "四", "五", "六");
        return date + ' 星期' + weekArray[week];
    }
});
