$(function(){
	loadPermissionTree();
	onClickSumbit();
});

var setting = {
	view: {
		showLine: true, // 显示连接线
		selectedMulti: false
	},
	check: {
		enable: true // 不显示复选框
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
	}
};

// 加载权限树
function loadPermissionTree(){
	
	// 查询到所有权限资源
	$.post(contextPath + "system/permission/list",function(data){
		var zTreeObj = $.fn.zTree.init($("#permissionTree"), setting, data.data);
		
		// 1，判断一下是否修改，判断id是否有值，如果有就是修改页面 ，否则新增页面
		if($("#id").val()){
			// 2，获取当前角色所拥有的权限id
			var perIds = JSON.parse($("#perIds").val());
			// 3，勾选所有的拥有的权限节点
			$.each(perIds,function(index,value){
				var nodes = zTreeObj.getNodesByParam("id",value,null);
				// 勾选当前选中的节点
				zTreeObj.checkNode(nodes[0],true,false);
				// 展开当前节点
				zTreeObj.expandNode(nodes[0],true,false);
				
			});
		}

	});
	
};

function onClickSumbit(){
	$("#form").submit(function(){
		// 收集所有的被选中节点
		var zTreeObj = $.fn.zTree.getZTreeObj("permissionTree");
		// 获取被选中的节点集合
		var checkNodes = zTreeObj.getCheckedNodes(true);
		var perIds = [];
		$.each(checkNodes,function(index,value){
			perIds.push(value.id);
		});
		$("#perIds").val(perIds);
		return true;
	});
};

