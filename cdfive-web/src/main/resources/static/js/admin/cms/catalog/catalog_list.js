//# sourceURL=catalog_list.js
$(function() {
	var $js_h3_title = $("#js_h3_title");
	var $js_btn_add = $("#js_btn_add");
	var $js_tree = $("#js_tree");
	var $js_txt_name = $("#js_txt_name");
	var $js_txt_description = $("#js_txt_description");
	var $js_btn_submit = $("#js_btn_submit");
	var $js_btn_cancel = $("#js_btn_cancel");
	var $js_form_catalog = $("#js_form_catalog");
	
	var title = "分类";
	var id;
	var pId;
	var prevId;
	
	var treeSetting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				addDiyDom : function(treeId, treeNode) {
					$("#" + treeNode.tId + "_a").on('click',function(){
						$js_h3_title.text("修改" + title);
						treeObj.selectNode(treeNode);
						id = treeNode.id;
						$js_txt_name.val(treeNode.name);
						$js_txt_description.val(treeNode.description);
						return false;
					});
				},
				addHoverDom : function(treeId, treeNode) {
					var $treeNodeSpan = $('#' + treeNode.tId + "_span");
					var btnStr = '';
					if ($('#' + treeNode.tId + "_a").find('.iconfont').length == 0) {
						btnStr = "<span id='js_btn_add_" + treeNode.tId + "' class='iconfont'"
									+ " title='添加'>&#xe61b;</span>"
									+ "<span id='js_btn_del_" + treeNode.tId + "' class='iconfont color-red'"  
									+ " title='删除'>&#xe61d;</span>";
						$treeNodeSpan.after(btnStr);
						$("#js_btn_add_" + treeNode.tId).on('click', function(event) {
							$js_h3_title.text("添加子" + title);
							treeObj.selectNode(treeNode);
							$js_txt_name.val("");
							$js_txt_description.val("");
				    		id = null;
				    		pId = treeNode.id;
				    		prevId = null;
				    		return false;
						});
						$("#js_btn_del_" + treeNode.tId).on('click', function() {
							msg.confirm("您确定要删除分类【" + treeNode.name + "】吗？",function(){
								sendAjax(CATALOG_DEL,{"id": treeNode.id},function(d){
									$js_txt_name.val("");
									$js_txt_description.val("");
						    		var nodes = treeNode.getParentNode() == null ? treeObj.getNodes() : treeNode.getParentNode().children;
									var index = treeObj.getNodeIndex(treeNode);
									var selectNode = null;
									if (nodes.length > index + 1) {
										selectNode = nodes[index+1];
									} else {
										if (index > 0) {
											selectNode = nodes[index-1];	
										}
									}
									if (selectNode != null) {
										treeObj.selectNode(selectNode);
									}
									treeObj.removeNode(treeNode);
								})
							})
							return false;
						});
					}
				},
				removeHoverDom : function(treeId, treeNode) {
					$("#js_btn_add_" + treeNode.tId).unbind().remove();
					$("#js_btn_del_" + treeNode.tId).unbind().remove();
				},
				showLine: false
			},
			callback : {
				onClick : function(event, treeId, treeNode, clickFlag) {
					event.stopPropagation();
					return false;
				}
			}
		};
	
	$js_btn_add.click(function() {
		$js_h3_title.text("添加" + title);
		form.clearData($js_form_catalog);
		id = null;
		pId = null;
		var selectedNodes = treeObj.getSelectedNodes();
    	var selectedNode = selectedNodes[0];
    	prevId = selectedNode == null ? null : selectedNode["id"];
	});
	
	$js_btn_submit.click(function() {
		var selectedNodes = treeObj.getSelectedNodes();
    	var selectedNode = selectedNodes[0];
    	prevId = selectedNode == null ? null : selectedNode["id"];
		var param = form.getData($js_form_catalog);
		if (id == null) {
			param["pId"] = pId;
			param["prevId"] = prevId;
			sendAjax(CATALOG_ADD, param, function(data) {
				var newNode = {};
	        	newNode.id = data.id;
	        	newNode.pId = data.pId;
	        	newNode.name = data.name;
	        	newNode.description = data.description;
	        	newNode.sort = data.sort;
	        	var parentNode = null;
	        	var index = -1;
	        	if (pId == null) {
	        		if (selectedNode != null) {
	        			parentNode = selectedNode.getParentNode();
	        			index = treeObj.getNodeIndex(selectedNode) + 1;
	        		} else {
	        			index = 0;
	        		}
	        	} else {
	        		parentNode = treeObj.getNodesByParam("id", pId, null)[0];
	        		index = 0;
	        	}
	       		var nodes = treeObj.addNodes(parentNode, index, newNode);
	       		form.clearData($js_form_catalog);
	    		if (pId == null) {
	    			treeObj.selectNode(nodes[0]);
	    		}
			});
		} else {
			param["id"] = id;
			sendAjax(CATALOG_UPDATE, param, function(data) {
				var node = treeObj.getNodesByParam("id", id, null)[0];
				node.name = param.name;
				node.description = param.description;
				treeObj.updateNode(node);
			});
		}
	});
	
	$js_btn_cancel.click(function() {
		form.clearData($js_form_catalog);
	});
	
	$.post(CATALOG_LIST, {}, function(data) {
		treeObj=$.fn.zTree.init($js_tree, treeSetting, data);
		treeObj.expandAll(true);
	});
});