//# sourceURL=menu_list.js

$(function() {
	var $js_div_sidebar = $("#js_div_sidebar");
	
	var $jsTree = $("#js-tree");
	
	var $btnAddMenu = $("#btnAddMenu");
	var $btnSaveMenu = $("#btnSaveMenu");
	var $btnCancelSaveMenu = $("#btnCancelSaveMenu");
	
	var $divMenu = $("#divMenu");
	var $titleMenu = $("#titleMenu");
	var $txtMenuName = $("#txtMenuName");
	var $txtMenuUrl = $("#txtMenuUrl");
	
	var $divButton = $("#divButton");
	var $btnAddButton = $("#btnAddButton");
	var $btnUpdateButton = $("#btnUpdateButton");
	var $btnDelButton = $("#btnDelButton");
	
	var $txtButtonName = $("#txtButtonName")
	var $txtButtonUrl = $("#txtButtonUrl")
	var $btnSaveButton = $("#btnSaveButton");
	var $btnCancelSaveButton = $("#btnCancelSaveButton");
	
	var $menuForm = $("#menuForm");
	var $buttonForm = $("#buttonForm");
	
	var title = "菜单";
	var id;
	var pId;
	var prevId;
	
	var buttonId;
	
	var treeObj;
	
	var initJqGridFlag = false;
	var jqGridId = "js-jqGrid";
	var jqGridPager = "js-jqGrid-pager";
	
	var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			addDiyDom : function(treeId, treeNode) {
				$("#" + treeNode.tId + "_a").on('click',function(){
					$divButton.show();
					$titleMenu.text("修改" + title);
					treeObj.selectNode(treeNode);
					id = treeNode.id;
				   	$txtMenuName.val(treeNode.name);
		    		$txtMenuUrl.val(treeNode.url);
		    		var param = {"pId" : id};
		    		if(initJqGridFlag){
						$('#' + jqGridId).setGridParam({postData : param}).trigger("reloadGrid");
					}else{
						initJqGrid(param);
					}
					return false;
				});
			},
			addHoverDom : function(treeId, treeNode) {
				var _eId = $('#' + treeNode.tId + "_span");
				var btnStr = '';
				if ($('#' + treeNode.tId + "_a").find('.iconfont').length == 0) {
					btnStr = "<span class='iconfont'  id='addBtn_"
								+ treeNode.tId + "' title='添加'>&#xe61b;</span>"
//								+ "<span class='iconfont'  id='editBtn_"
//								+ treeNode.tId + "' title='编辑'>&#xe61c;</span>"
								+ "<span class='iconfont color-red'  id='delBtn_"
								+ treeNode.tId + "' title='删除'>&#xe61d;</span>";
					_eId.after(btnStr);
					$("#addBtn_" + treeNode.tId).on('click', function(event) {
						$divButton.hide();
						$titleMenu.text("添加子" + title);
						treeObj.selectNode(treeNode);
					   	$txtMenuName.val("");
			    		$txtMenuUrl.val("");
			    		id = null;
			    		pId = treeNode.id;
			    		prevId = null;
			    		return false;
					});
//					$("#editBtn_" + treeNode.tId).on('click', function() {
//						treeObj.selectNode(treeNode);
//						id = treeNode.id;
//					   	$txtMenuName.val(treeNode.name);
//			    		$txtMenuUrl.val(treeNode.url);
//			    		return false;
//					});
					$("#delBtn_" + treeNode.tId).on('click', function() {
						msg.confirm("您确定要删除菜单【" + treeNode.name + "】吗？",function(){
							sendAjax(MENU_DEL,{"id": treeNode.id},function(d){
							   	$txtMenuName.val("");
					    		$txtMenuUrl.val("");
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
				$("#addBtn_" + treeNode.tId).unbind().remove();
				$("#editBtn_" + treeNode.tId).unbind().remove();
				$("#delBtn_" + treeNode.tId).unbind().remove();
			},
			showLine: false//,
			//addDiyDom: addDiyDom
		},
		callback : {
			onClick : function(event, treeId, treeNode, clickFlag) {
				event.stopPropagation();
				return false;
			}
		}
	};
	
	function initJqGrid(param){
		initJqGridFlag = true;
		var jqGridSettion = $.extend({}, jqGridCommonSetting, {
			url: BUTTON_LIST,
			postData:param,
			colModel: [
			    {
					label: '按钮名称',
					name: 'name',
					width:200
			    },
			    {
					label: '按钮地址',
					name: 'url',
					width:200
			    },
			    {
					label: '',
					name: 'id',
					hidden:true
			    }
			]
		});
		$('#'+jqGridId).jqGrid(jqGridSettion).navGrid("#" + jqGridId + "_toppager", { //开始设置按钮事件
			edit: false,
			save: false,
			cancel: false,
			del: false,
			add: false,
			refresh: false,
			search: false,
			refreshstate: "current "
		}, {}, // prmEdit
		{}, // prmAdd
		{}, // prmDel
		{ // prmSearch
			multipleSearch: true,
			closeOnEscape: true
		}, {})
		$(".ui-jqgrid .ui-jqgrid-pager .ui-paging-pager, .ui-jqgrid .ui-jqgrid-toppager .ui-paging-pager").eq(0).hide();
		$(".ui-jqgrid .ui-jqgrid-pager .ui-paging-info, .ui-jqgrid .ui-jqgrid-toppager .ui-paging-info").eq(0).hide();
	}
	
	$btnAddMenu.click(function() {
		$divButton.hide();
		$titleMenu.text("添加" + title);
		form.clearData($menuForm);
		id = null;
		pId = null;
		var selectedNodes = treeObj.getSelectedNodes();
    	var selectedNode = selectedNodes[0];
    	prevId = selectedNode == null ? null : selectedNode["id"];
	});
	
	$btnSaveMenu.click(function() {
		var selectedNodes = treeObj.getSelectedNodes();
    	var selectedNode = selectedNodes[0];
    	prevId = selectedNode == null ? null : selectedNode["id"];
		var param = form.getData($menuForm);
		if (id == null) {
			param["pId"] = pId;
			param["prevId"] = prevId;
			sendAjax(MENU_ADD, param, function(data) {
				var newNode = {};
	        	newNode.id = data.id;
	        	newNode.pId = data.pId;
	        	newNode.name = data.name;
	        	newNode.url = data.url;
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
	       		form.clearData($menuForm);
	    		if (pId == null) {
	    			treeObj.selectNode(nodes[0]);
	    		}
	    		$divMenu.show();
			});
		} else {
			param["id"] = id;
			sendAjax(MENU_UPDATE, param, function(data) {
				var node = treeObj.getNodesByParam("id", id, null)[0];
				node.name = param.name;
				node.url = param.url;
				treeObj.updateNode(node);
			});
		}
	});
	
	$btnCancelSaveMenu.click(function() {
		form.clearData($menuForm);
	});
	
	$btnAddButton.click(function() {
		if (id == null) {
			msg.error("请先选择一个菜单");
			return;
		}
		buttonId = null;
		form.clearData($buttonForm);
		sidebar.show($js_div_sidebar);
	});
	
	$btnUpdateButton.click(function() {
		if (id == null) {
			msg.error("请先选择一个菜单");
			return;
		}
		var selectId = $('#' + jqGridId).jqGrid("getGridParam","selrow")
		if (!selectId) {return;}
		buttonId = selectId;
		var rowData = $('#' + jqGridId).jqGrid('getRowData', selectId);
		buttonId = rowData.id;
		$txtButtonName.val(rowData["name"]);
		$txtButtonUrl.val(rowData["url"]);
		sidebar.show($js_div_sidebar);
	});
	
	$btnDelButton.click(function() {
		var selectId = $('#' + jqGridId).jqGrid("getGridParam","selrow")
		if (!selectId) {return;}
		var rowData = $('#' + jqGridId).jqGrid('getRowData', selectId);
		var param = {"id":selectId};
		msg.confirm("确定删除按钮【" + rowData["name"] + "】吗?", function() {
			sendAjax(BUTTON_DEL, param, function(data) {
				$('#' + jqGridId).trigger("reloadGrid");
			});
		});
	});
	
	$btnSaveButton.click(function() {
		var param = form.getData($buttonForm);
		param["pId"] = id;
		var url = BUTTON_ADD;
		if (buttonId != null) {
			param["id"] = buttonId;
			url = BUTTON_UPDATE;
		}
		sendAjax(url, param, function(data) {
			form.clearData($buttonForm);
			sidebar.hide($js_div_sidebar);
			$('#' + jqGridId).trigger("reloadGrid");
		});
	});
	
	$btnCancelSaveButton.click(function() {
		sidebar.hide($js_div_sidebar);
	});
	
	$.post(MENU_LIST,{},function(data){
		treeObj=$.fn.zTree.init($jsTree,setting,data);
		treeObj.expandAll(true);
	});
});