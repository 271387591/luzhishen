Ext.define('App.model.User', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'roleId',
        'unitId',
        'unitName',
        'username',
        'roleName',
        'roleDisplayName',
        'firstName',
        'lastName',
        'password',
        'passwordAffirm',
        'mobile',
        'email',
        'fullName',
        'defaultRoleName',
        'defaultRoleDisplayName',
        'defaultRoleId',
        'gender',
        {
            name: 'createDate', convert: function (v) {
            return Ext.util.Format.date(new Date(v), 'Y-m-d H:i:s');
        }
        },
        'enabled',
        'accountLocked'
    ]
});