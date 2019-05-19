//# sourceURL=user_list.js

$(function() {
	var user_list = (function(){
		var $jqGrid;
	    var $jqGridPager;
		var jqGridPagerTopId;
		var title = "用户";
		
		var $div_password = $('#div_password');
		var $div_userStatus = $('#div_userStatus');
		var $div_errCount = $('#div_errCount');
		var $js_txt_userName = $('#js_txt_userName');
		
		var isAdminMap = {
			'':'请选择',
			'1':'管理员',
			'0':'普通用户'
		}
		
		var userStatusMap = {
			'':'请选择',
			'1':'正常',
			'2':'冻结'
		}
		
		function init() {
			$jqGrid = $("#jqGrid");
		    $jqGridPager = $("#jqGridPager");
			jqGridPagerTopId = "#" + "jqGrid" + "_toppager";
			
			var jqGridSetting = $.extend({}, jqGridCommonSetting, {
		        url: USER_LIST,
		        pager:$jqGridPager,
		        colModel: [
		            {
		                name: 'id',
		                label: '编号',
		                hidden:true
		            },
		            {
		                label: '用户名',
		                name: 'userName',
		                width:100,
		                searchoptions :{
		                	sopt: ['eq', 'ne', 'cn', 'nc']
						}
		            },
		            {
		                label: '姓名',
		                name: 'realName',
		                width:100,
		                searchoptions :{
		                	sopt: ['eq', 'ne', 'cn', 'nc']
						}
		            },
		            {
		                label: '类型',
		                name: 'isAdmin',
		                formatter:function(cellvalue){
		                    return cellvalue == null ? '' : isAdminMap[cellvalue];
		                },
		                width:60,
		                stype:'select',
		                searchoptions :{
							value:isAdminMap,
							sopt: ['eq']
						}
		            },
		            {
		                label: '状态',
		                name: 'userStatus',
		                formatter:function(cellvalue){
		                    return cellvalue == null ? '' : userStatusMap[cellvalue];
		                },
		                width:60,
		                stype:'select',
		                searchoptions :{
							value:userStatusMap,
							sopt: ['eq']
						}
		            },
		            {
		                label: '密码错误次数',
		                name: 'errCount',
		                width:100,
		                searchoptions :{
		                	sopt: ['eq', 'ne', 'cn', 'nc']
						}
		            },
		            {
		                label: '创建时间',
		                name: 'createTime',
		                search: false
		            },
		            {
		                label: '修改时间',
		                name: 'updateTime',
		                search: false
		            }
		        ]
		    });
			
			$jqGrid.jqGrid(jqGridSetting)
		        .navGrid(jqGridPagerTopId, { //开始设置按钮事件
		            edit: false,
		            save: false,
		            cancel: false,
		            del: false,
		            add: false,
		            refresh: false,
		            search: true,
		            searchtext: "筛选",
		            searchtitle: "筛选",
		            refreshstate: "current "

		        }, {}, // prmEdit
		        {}, // prmAdd
		        {}, // prmDel
		        { // prmSearch
		            multipleSearch: true,
		            closeOnEscape: true
		        }, {})
		        
//		    $("#" + jqGridId + "_toppager_center,#" + jqGridId + "_toppager_right").addClass("hide");
		    
		    $(".ui-jqgrid .ui-jqgrid-pager .ui-paging-pager, .ui-jqgrid .ui-jqgrid-toppager .ui-paging-pager").eq(0).hide();
		    $(".ui-jqgrid .ui-jqgrid-pager .ui-paging-info, .ui-jqgrid .ui-jqgrid-toppager .ui-paging-info").eq(0).hide();
		}
		
		
		var $body = $('body');
		var $sidebar = $('#js-sidebar');
		var $sidebarTt	= $('.sidebar-tt');
		var $form = $("#form");
		var $songName = $("#js-songName");
		var $author = $("#js-author");
		
		$('#js_btn_add').click(function() {
			$sidebarTt.html('新增');
			$js_txt_userName.prop("readonly", false);
			$div_password.show();
			$div_userStatus.hide();
			$div_errCount.hide();
			form.clearData($form);
			sidebar.show($sidebar);
		});
		
		sendAjax(ROLE_LIST,{},function(data) {
			var html = '';
			for (var i = 0; i < data.length; i++) {
				html += '<label class="checkbox-inline"><input type="checkbox" class="js-district-role" name="roleIds" value="'+data[i].id+'">'+data[i].name+'</label>';
			}
			$('#div_role').html(html);
		});
		
		$('#js_btn_edit').click(function() {
			var _selectedIds = $jqGrid.jqGrid("getGridParam","selrow");
			if (!_selectedIds) {
				msg.error('请选择一行');
				return;
			}
			var rowData=$jqGrid.jqGrid('getRowData', _selectedIds);
			sendAjax(USER_DETAIL, {id:rowData["id"]}, function(data) {
				$sidebarTt.html('修改');
				$js_txt_userName.prop("readonly", true);
				$div_password.hide();
				$div_userStatus.show();
				$div_errCount.show();
//				form.setData($form, rowData);
				form.setData($form, data);
			});
			sidebar.show($sidebar);
		});
		
		$('#js_btn_del').click(function() {
			var _selectedIds = $jqGrid.jqGrid("getGridParam","selrow");
			if (!_selectedIds) {
				msg.error('请选择一行');
				return;
			}
			var rowData=$jqGrid.jqGrid('getRowData', _selectedIds);
			msg.confirm("确定要删除吗？", function() {
				sendAjax(USER_DEL, {id:rowData["id"]}, function() {
					msg.success('删除成功');
					reloadJqGrid($jqGrid);
				});
			});
		});
		
		$('#js-cancel').click(function() {
			sidebar.hide($sidebar);
		});
		
		$body.on('click.mp3', '#js-submit', function() {
			var data = form.getData($("#form"));
			sendAjax(!data.id ? USER_ADD : USER_UPDATE, data, function(d) {
				msg.success((!data.id ? '新增' : '修改') + '成功');
				reloadJqGrid($jqGrid);
				sidebar.hide($sidebar);
			});
		});
		
		init();
	})();
});

