<%@ include file="/common/taglibs.jsp"%>
var globalRes = {
    username: '${command.username}',
    roleId: '${command.roleId}',
    userId: '${command.id}',
    depId: '${command.depId}'
};
var accesses=[];
<c:forEach var="feature" items="${command.role.features}" varStatus="status">
    var obj={};
    obj.tableId=${feature.tableId};
    obj.fieldId=${feature.fieldId};
    obj.fieldCid='${feature.fieldCid}';
    obj.canUpdate=new Boolean(${feature.canUpdate});
    obj.canAdd=new Boolean(${feature.canAdd});
    obj.canQuery=new Boolean(${feature.canQuery});
    obj.isDept=new Boolean(${feature.isDept});
    obj.isCreator=new Boolean(${feature.isCreator});
    accesses.push(obj);
</c:forEach>
var tableAccesses=[];
<c:forEach var="feature" items="${command.role.tableFeatures}" varStatus="status">
    var obj={};
    obj.tableId='${feature.tableId}';
    obj.roleId=${feature.roleId};
    obj.queryType='${feature.queryType}';
    tableAccesses.push(obj);
</c:forEach>


