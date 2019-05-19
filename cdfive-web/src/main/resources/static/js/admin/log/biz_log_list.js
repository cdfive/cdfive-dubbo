//# sourceURL=biz_log_list.js

$(function() {
	var log_list = (function(){
		var $jqGrid;
	    var $jqGridPager;
		var jqGridPagerTopId;
		
		function init() {
			$jqGrid = $("#jqGrid");
		    $jqGridPager = $("#jqGridPager");
			jqGridPagerTopId = "#" + "jqGrid" + "_toppager";
			
			var jqGridSetting = $.extend({}, jqGridCommonSetting, {
		        url: BIZ_LOG_LIST,
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
		                searchoptions :{
		                	sopt: ['eq', 'ne', 'cn', 'nc']
						}
		            },
		            {
		                label: '姓名',
		                name: 'realName',
		                searchoptions :{
		                	sopt: ['eq', 'ne', 'cn', 'nc']
						}
		            },
		            {
		                label: '操作',
		                name: 'operKey',
		                searchoptions :{
		                	sopt: ['eq', 'ne', 'cn', 'nc']
						}
		            },
		            {
		                label: 'ip',
		                name: 'ip',
		                searchoptions :{
		                	sopt: ['eq', 'ne', 'cn', 'nc']
						}
		            },
		            {
		                label: '创建时间',
		                name: 'createTime',
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
		    
		    $(".ui-jqgrid .ui-jqgrid-pager .ui-paging-pager, .ui-jqgrid .ui-jqgrid-toppager .ui-paging-pager").eq(0).hide();
		    $(".ui-jqgrid .ui-jqgrid-pager .ui-paging-info, .ui-jqgrid .ui-jqgrid-toppager .ui-paging-info").eq(0).hide();
		}
		
		
		init();
	})();
});

