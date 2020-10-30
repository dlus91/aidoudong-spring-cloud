$(function(){
	loadPermissionTree();
});

var setting = {
	view: {
		showLine: true, // 显示连接线
		selectedMulti: false
	},
	check: {
		enable: false // 不显示复选框
	},
	data: {
		simpleData: {
			enable: true, // 开启简单模式，List自动 转json
			idKey: "id", //唯一标识属性名
			pIdKey: "parentId", // 父节点唯一标识的属性名称
			rootPId: 0 // 根节点数据
		},
		key: {
			name: "name", // 显示的节点名称对应的属性名称
			title: "name" // 鼠标放上去显示的
		}
	},
	callback: {
		onClick: zTreeOnClick
	}
};

// 加载权限树
function loadPermissionTree(){
	
	// 查询到所有权限资源
	$.post(contextPath + "client/permission/list",function(data){
		var zTreeObj = $.fn.zTree.init($("#permissionTree"), setting, data.data);
		
		var parentNode = zTreeObj.getNodeByParam("id",$("#parentId").val(),null);
		if(parentNode){
			$("#parentName").val(parentNode.name);
		}
	});
	
}

function zTreeOnClick(event, treeId, treeNode){
	console.log(treeNode);
	// 被点击之后阻止跳转
	event.preventDefault();
	
	if(treeNode.id == $("#id").val()){
		layer.tips("自己不能作为父资源", "#"+treeId, {time:2000});
		return;
	}
	
	// 就将选择的节点放到父资源处
	parentPermission(treeNode.id,treeNode.name);
	
}

function parentPermission(parentId,parentName){
	if(parentId || parentName){
		$("#parentId").val(parentId);
		$("#parentName").val(parentName);
	}else{
		$("#parentId").val(0);
		$("#parentName").val("根菜单");
	}
}

