var $table = $("#table");
$(function(){
	var options =  {
		url : contextPath + 'client/user/page',
		pageNumber: 1,
		pageSize: 3,
		columns: [
			{title: '序号', width: 20, formatter: function(value, row, index){
				return index + 1;
			}},
			{field: 'id', visible: false},
			{field: 'username', title: '用户名'},
			{field: 'nickName', title: '昵称'},
			{field: 'mobile', title: '手机号'},
			{field: 'email', title: '邮箱'},
			{field: 'accountNonExpired', title: '账号是否过期', formatter: statusFormatter},
			{field: 'accountNonLocked', title: '是否锁定', formatter: statusFormatter},
			{field: 'credentialsNonExpired', title: '密码是否过期', formatter: statusFormatter},
			{field: 'action', title: '操作 ', visible: false, width: 50, align: 'center', formatter: $.operationFormatter}
		]
	};
	$.pageTable(options);
	
});

function statusFormatter(value, row, index){
	return value == true ? "<span class='badge bg-success'>否</span>"
    : "<span class='badge bg-danger'>是</span>";
}

function searchForm(){
	var query = {
		size: 3,
		current: 1,
		username: $("#username").val().trim(),
		mobile: $("#mobile").val().trim()
	};
	$table.bootstrapTable('refresh',{query: query});
}

