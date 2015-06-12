/**
 * Created by lihao1 on 5/29/15.
 */
Ext.define('App.view.order.OrderChart', {
    extend: 'Ext.Panel',
    xtype: 'orderChart',
    width: 350,
    initComponent: function() {
        var me = this;
        var store = Ext.create('Ext.data.JsonStore', {
            fields: ['title', 'status','count'],
            proxy:{
                type: 'ajax',
                url: 'order/loadChart',
                reader: {
                    type: 'json',
                    root : 'data',
                    totalProperty  : 'total',
                    messageProperty: 'message'
                }
            }
        });
        store.load();
        var chart =Ext.create('Ext.chart.Chart', {
            width: 350,
            height: 400,
            animate: true,
            store: store,
            shadow: true,
            legend: {
                position: 'right'
            },
            theme: 'Base:gradients',
            series: [{
                type: 'pie',
                angleField: 'count',
                showInLegend: true,
                tips: {
                    trackMouse: true,
                    width: 140,
                    height: 28,
                    renderer: function(storeItem, item) {
                        // calculate and display percentage on hover
                        var total = 0;
                        store.each(function(rec) {
                            total += rec.get('count');
                        });
                        this.setTitle(storeItem.get('title') + ': ' + Math.round(storeItem.get('count') / total * 100) + '%');
                    }
                },
                highlight: {
                    segment: {
                        margin: 20
                    }
                },
                label: {
                    field: 'title',
                    display: 'rotate',
                    contrast: true,
                    font: '18px Arial'
                }
            }]
        });
        me.items=[chart];

        this.callParent();
    }
});