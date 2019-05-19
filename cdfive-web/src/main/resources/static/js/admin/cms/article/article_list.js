//# sourceURL=article_list.js
$(function() {
	var jqGridId = "js_jqGrid";
	
	var $js_tree = $("#js_tree");
	var $js_jqGrid = $("#" + jqGridId);
	var $js_jqGridPager = $("#js_jqGridPager");
	var $js_btn_add = $("#js_btn_add");
	var $js_btn_update = $("#js_btn_update");
	var $js_btn_del = $("#js_btn_del");
	var $js_div_sidebar = $("#js_div_sidebar");
	var $js_form_article = $("#js_form_article");
	var $js_btn_submit = $("#js_btn_submit");
	var $js_btn_cancel = $("#js_btn_cancel");
	
	var $js_div_tt = $("#js_div_tt");
	var $js_div_title = $("#js_div_title");
	var $js_div_oriUrl = $("#js_div_oriUrl");
	var $js_div_oriUrlClickCount = $("#js_div_oriUrlClickCount");
	var $js_div_clickCount = $("#js_div_clickCount");
	
	var $js_span_oriUrlClickCount = $("#js_span_oriUrlClickCount");
	var $js_span_clickCount = $("#js_span_clickCount");
	
	var $js_txt_title = $("#js_txt_title");
	
	var js_ueditor = "js_ueditor";
	
	var initJqGridFlag = false;
	var catalogId;
	
	var isOriMap = {
		"" : "请选择",
		"1" : "原创",
		"0" : "转载"
	}
	
	var isTopMap = {
		"" : "请选择",
		"1" : "是",
		"0" : "否"
	}
	
	var treeSetting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			addDiyDom : function(treeId, treeNode) {
				$("#" + treeNode.tId + "_a").on('click',function(){
					treeObj.selectNode(treeNode);
					if (treeNode.pId == null) {
						catalogId = null
					} else {
						catalogId = treeNode.id;
					}
		    		var param = {"catalogId" : catalogId};
		    		if(initJqGridFlag){
	    				$js_jqGrid.setGridParam({postData : param}).trigger("reloadGrid");
					}else{
						initJqGrid(param);
					}
					return false;
				});
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
		$js_div_tt.html("新增");
		form.clearData($js_form_article);
		UE.getEditor(js_ueditor).setContent('');
		$js_div_oriUrlClickCount.hide();
		$js_div_clickCount.hide();
		sidebar.show($js_div_sidebar);
	});
	
	$js_btn_update.click(function() {
		var _selectedIds = $js_jqGrid.jqGrid("getGridParam","selrow");
		if (!_selectedIds) {
			msg.error('请选择一行');
			return;
		}
		var rowData = $js_jqGrid.jqGrid('getRowData', _selectedIds);
		sendAjax(ARTICLE_DETAIL, {id:rowData["id"]}, function(data) {
			$js_div_tt.html("修改");
			form.setData($js_form_article, data);
			UE.getEditor(js_ueditor).setContent(data.content);
			$js_span_oriUrlClickCount.text(data["oriUrlClickCount"]);
			$js_span_clickCount.text(data["clickCount"]);
			if (data["isOri"] == "0") {
				$js_div_oriUrl.show();
				$js_div_oriUrlClickCount.show();
			} else {
				$js_div_oriUrl.hide();
				$js_div_oriUrlClickCount.hide();
			}
			$js_div_clickCount.show();
		});
		sidebar.show($js_div_sidebar);
	});
	
	$('#js_btn_del').click(function() {
		var _selectedIds = $js_jqGrid.jqGrid("getGridParam","selrow");
		if (!_selectedIds) {
			msg.error('请选择一行');
			return;
		}
		var rowData = $js_jqGrid.jqGrid('getRowData', _selectedIds);
		msg.confirm("确定要删除吗？", function() {
			sendAjax(ARTICLE_DEL, {id:rowData["id"]}, function() {
				msg.success('删除成功');
				reloadJqGrid($js_jqGrid);
			});
		});
	});
	
	$("input[name='isOri']").click(function() {
		var val = $(this).val();
		if (val == "1") {
			$js_div_oriUrl.hide();
		} else {
			$js_div_oriUrl.show();
		}
	});
	
	$js_btn_submit.click(function() {
		var data = form.getData($js_form_article);
		if (!data.id) {
			data["catalogId"] = catalogId;
		}
		sendAjax(!data.id ? ARTICLE_ADD : ARTICLE_UPDATE, data, function(d) {
			msg.success((!data.id ? '新增' : '修改') + '成功');
			reloadJqGrid($js_jqGrid);
			sidebar.hide($js_div_sidebar);
		});
	});
	
	$js_btn_cancel.click(function() {
		sidebar.hide($js_div_sidebar);
	});
	
	
	function initJqGrid(param) {
		initJqGridFlag = true;
		var jqGridSetting = $.extend({}, jqGridCommonSetting, {
			url : ARTICLE_LIST,
			pager:$js_jqGridPager,
			height : '800',
			postData : param,
			colModel : [
			    {
					label : '标题',
					name : 'title',
					searchoptions :{
	                	sopt: ['cn']
					}
			    },
			    {
					label : '来源',
					name : 'isOri',
					width:33,
					formatter : function(cellvalue) {
	                    return cellvalue == null ? '' : isOriMap[cellvalue];
	                },
	                stype : 'select',
	                searchoptions :{
						value : isOriMap,
						sopt : ['eq']
					}
			    },
			    {
					label : '原地址',
					name : 'oriUrl',
					formatter : function(cellvalue) {
						if (str.isEmpty(cellvalue)) {
							return "";
						}
	                    return "<a href='" + cellvalue + "' target='_blank'>" + cellvalue + "</a>";
	                },
	                searchoptions :{
	                	sopt: ['eq', 'ne', 'cn', 'nc']
					}
			    },
			    {
			    	label : '原地址点击数',
			    	name : 'oriUrlClickCount',
			    	width:50,
			    	search : false
			    },
			    {
					label : '置顶',
					name : 'isTop',
					width:33,
					formatter:function(cellvalue) {
	                    return cellvalue == null ? '' : isTopMap[cellvalue];
	                },
	                stype :'select',
	                searchoptions : {
						value : isTopMap,
						sopt : ['eq']
					}
			    },
			    {
			    	label : '点击数',
			    	name : 'clickCount',
			    	width:50,
			    	search : false
			    },
			    {
					label : '创建时间',
					name : 'createTime',
					width:65,
					search : false
			    },
			    {
					label : '',
					name : 'id',
					hidden : true
			    }
			]
		});
		$js_jqGrid.jqGrid(jqGridSetting).navGrid("#" + jqGridId + "_toppager", { //开始设置按钮事件
			edit : false,
			save : false,
			cancel : false,
			del : false,
			add : false,
			refresh : false,
			search : true,
            searchtext : "筛选",
            searchtitle : "筛选",
			refreshstate : "current"
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
	
	sendAjaxOri(CATALOG_LIST, {}, function(data) {
		var rootNode = {};
		rootNode.id = "1";
		rootNode.name = "全部";
		rootNode.pId = null;
		for (var i = 0; i < data.length; i++) {
			if (data[i].pId == null) {
				data[i].pId = rootNode.id;
			}
		}
		data.push(rootNode);
		treeObj=$.fn.zTree.init($js_tree, treeSetting, data);
		treeObj.expandAll(true);
	});
	
	initJqGrid();
	
	UE.getEditor(js_ueditor);
});