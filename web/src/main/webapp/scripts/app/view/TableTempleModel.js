/**
 * Created by lihao on 4/24/15.
 */
Ext.define('App.view.TableTempleModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.tableTempleModel',
    requires: [
        'App.model.StandingBook',
        'App.model.User',
        'App.model.TableField'
    ],
    data: {
        rec: null
    }
});