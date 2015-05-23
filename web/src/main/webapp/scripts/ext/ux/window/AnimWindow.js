/**
 * Created by lihao1 on 5/23/15.
 */
Ext.define('Ext.ux.window.AnimWindow', {
    extend: 'Ext.window.Window',
    alternateClassName: 'Ext.ux.AnimWindow',
    alias: 'widget.animWindow',
    requires: [
        'Ext.fx.Anim'
    ],
    renderTo:Ext.getBody(),
    width:100,
    height:100,
    leftMargin:100,
    draggable:false,
    animShow:function(){
        var me=this;
        var width=me.getWidth();
        this.showAt(document.body.clientWidth-me.leftMargin);
        Ext.create('Ext.fx.Anim', {
            target: me,
            duration: 1000,
            from: {
                left:(document.body.clientWidth-me.leftMargin)
            },
            to: {
                left:(document.body.clientWidth-width)
            }
        });
    },
    listeners:{
        blur:function(){
            console.log('sdfdsf')
        },
        focus:function(){
            console.log('sdfdsf')
        },

    }
});