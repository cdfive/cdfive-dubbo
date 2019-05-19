//# sourceURL=role_list.js

var role_list = (function() {
	
	
	
	
})();

$(function() {
	var treeObj;
	var id;
	
	var $js_role_tree = $("#js_role_tree");
	var $js_txt_name = $("#js_txt_name");
	var $js_btn_submit = $("#js_btn_submit");
	var $js_btn_cancel = $("#js_btn_cancel");
	
	
	var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			showLine: false
		},
		check: {
			enable: true//,
//			chkboxType : {"Y":"","N":""}
		},
		callback : {
			onClick : function(event, treeId, treeNode, clickFlag) {
				event.stopPropagation();
				return false;
			}
		}
	};
	
	$.post(MENU_LIST,{},function(data){
		treeObj=$.fn.zTree.init($js_role_tree, setting, data);
		treeObj.expandAll(true);
	});
	
	function init_role() {
		sendAjax(ROLE_LIST, {}, function(data) {
			var html = template('tpl-role-list', {'list' : data});
			$('#role_list').html(html);
			
			$(".js_btn_del").click(function() {
				var id = $(this).parent().prev().attr('data');
				msg.confirm("确定删除吗？", function() {
					sendAjax(ROLE_DEL, {id:id}, function() {
						init_role();
						id = null;
						$js_txt_name.val("");
						treeObj.checkAllNodes(false);
					});
				});
				return false;
			});
			
			$('#role_list li').click(function() {
				var $span = $(this).find('span.name');
				id = $span.attr('data');
				$js_txt_name.val($span.text());
				
				sendAjax(ROLE_MENU_LIST,{id:id}, function(data) {
					treeObj.checkAllNodes(false);
					for (var i in data) {
						var nodes = treeObj.getNodesByParam("id", data[i], null);
						var len = nodes.length;
						if (len == 0) {
							continue;
						}
						for (var i = 0; i < len; i++) {
							treeObj.checkNode(nodes[i], true, false);
						}
					}
				});
			});
		});
	}
	
	init_role();
	
	
	$("#js_btn_add").click(function() {
		id = null;
		$js_txt_name.val("").focus();
		treeObj.checkAllNodes(false);
	});
	
	
	$js_btn_submit.click(function() {
		var name = $js_txt_name.val();
		if (str.isEmpty(name)) {
			msg.error("名称不能为空");
			return;
		}
		
		var nodes = treeObj.getCheckedNodes(true);
		
		if (nodes.length == 0) {
			msg.error("请选择权限");
			return;
		}
		
		var menuIds = [];
		for ( var i in nodes) {
			menuIds.push(nodes[i]["id"]);
		}
		
		var param = {};
		param["name"] = name;
		param["menuIds"] = menuIds;
		if (id != null) {
			param["id"] = id;
		}
		
		sendAjax(!id ? ROLE_ADD : ROLE_UPDATE, param, function(data) {
			init_role();
			id = null;
			$js_txt_name.val("");
			treeObj.checkAllNodes(false);
		});
	});
	
	$("#js_btn_cancel").click(function() {
		$js_txt_name.val("");
		treeObj.checkAllNodes(false);
	});
});