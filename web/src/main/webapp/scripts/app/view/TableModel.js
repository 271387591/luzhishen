/**
 * Created by lihao on 4/23/15.
 */
Ext.define('App.view.TableModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.tableModel',
    requires: [
        'App.model.Table',
        'App.model.TableField',
        'App.model.DataSource',
        'App.model.DataSourceField'
    ],
    data: {
        rec: {}
    },
    stores:{
        tables:{
            model:'App.model.Table',
            proxy: {
                type: 'ajax',
                url: 'table/listTables',
                reader: {
                    type: 'json',
                    root: 'data',
                    totalProperty: 'total',
                    messageProperty: 'message'
                },
                extraParams:{
                }
            }
        },
        datasources:{
            model:'App.model.DataSource',
            proxy: {
                type: 'ajax',
                url: 'datasource/listDataSources',
                reader: {
                    type: 'json',
                    root: 'data',
                    totalProperty: 'total',
                    messageProperty: 'message'
                }
            }
        },
        datasourceFields:{
            model:'App.model.DataSourceField',
            proxy: {
                type: 'ajax',
                url: 'datasource/listDataSourceFields',
                reader: {
                    type: 'json',
                    root: 'data',
                    totalProperty: 'total',
                    messageProperty: 'message'
                }
            }
        }

    }
});