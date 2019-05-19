//# sourceURL=login_log_list.js

$(function() {
	var log_list = (function(){
		var $jqGrid;
	    var $jqGridPager;
		var jqGridPagerTopId;
		
		var isSuccMap = {
			'':'请选择',
			'1':'成功',
			'0':'失败'
		}
		
		var failTypeMap = {
			'':'请选择',
			'1':'密码错误',
			'2':'冻结'
		}
		
		function init() {
			$jqGrid = $("#jqGrid");
		    $jqGridPager = $("#jqGridPager");
			jqGridPagerTopId = "#" + "jqGrid" + "_toppager";
			
			var jqGridSetting = $.extend({}, jqGridCommonSetting, {
		        url: LOGIN_LOG_LIST,
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
		                label: '状态',
		                name: 'isSucc',
		                formatter:function(cellvalue){
		                    return cellvalue == null ? '' : isSuccMap[cellvalue];
		                },
		                width:60,
		                stype:'select',
		                searchoptions :{
							value:isSuccMap,
							sopt: ['eq']
						}
		            },
		            {
		                label: '失败原因',
		                name: 'failType',
		                formatter:function(cellvalue){
		                    return cellvalue == null ? '' : failTypeMap[cellvalue];
		                },
		                width:60,
		                stype:'select',
		                searchoptions :{
							value:failTypeMap,
							sopt: ['eq']
						}
		            },
		            {
		                label: 'ip',
		                name: 'ip',
		                width:100,
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

