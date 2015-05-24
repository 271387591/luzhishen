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
                                    text:'修改密码'
                                },{
                                    text:'安全退出',
                                    handler:function(){
                                        document.location.replace(globalRes.logoutUrl);
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
