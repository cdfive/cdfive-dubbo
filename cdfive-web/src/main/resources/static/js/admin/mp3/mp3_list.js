//# sourceURL=mp3_list.js

$(function() {
	//mp3_list.init();
	var mp3_list = (function(){
		var $jqGrid;
	    var $jqGridPager;
		var jqGridPagerTopId;
		
		function init() {
			$jqGrid = $("#jqGrid");
		    $jqGridPager = $("#jqGridPager");
			jqGridPagerTopId = "#" + "jqGrid" + "_toppager";
			
			var jqGridSetting = $.extend({}, jqGridCommonSetting, {
		        url: '/v1/mp3/list',
//				postData:param,
		        pager:$jqGridPager,
		        colModel: [
		            {
		                name: 'id',
		                label: '编号',
		                hidden:true
		            },
		            {
		                label: '歌名',
		                name: 'songName',
		                width:100,
		                searchoptions :{
		                	sopt: ['eq', 'ne', 'cn', 'nc']
						}
		            },
		            {
		                label: '歌手',
		                name: 'author',
		                width:100,
		                searchoptions :{
		                	sopt: ['eq', 'ne', 'cn', 'nc']
						}
		            },
		            {
		                label: '播放次数',
		                name: 'playCount',
		                width:40,
		                searchoptions :{
		                	sopt: ['eq', 'ne', 'cn', 'nc']
						}
		            },
		            {
		                label: '歌名长度',
		                name: 'digit',
		                width:40,
		                searchoptions :{
		                	sopt: ['eq', 'ne', 'cn', 'nc']
						}
		            },
		            {
		                label: '分组',
		                name: 'groups',
		                width:50,
		                searchoptions :{
		                	sopt: ['eq', 'ne', 'cn', 'nc']
						}
		            },
		            {
		                label: '喜欢理由',
		                name: 'reason',
		                searchoptions :{
		                	sopt: ['eq', 'ne', 'cn', 'nc']
						}
		            },
		            {
		                name: 'path',
		                hidden:true
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
		var $mp3Path = $("#js-mp3-path");
		var $lrcPath = $("#js-lrc-path");
		var $krcPath = $("#js-krc-path");
		
		var mp3UploadSetting=$.extend({},uploadSeting,{
			'width':'70px',
			'height':'30px',
			'buttonText':'上传',
			'fileTypeExts':'*.mp3',
			'fileObjName':'fileMp3',
			'onUploadSuccess': function (fileObj, data, response) {
				var fileName = fileObj.name;
				var tokens = fileName.split('-');
				var songName = tokens[0].trim(); 
				var author = tokens[1].substr(0, tokens[1].indexOf("."));
				$songName.val(songName);
				$author.val(author);
				layer.closeAll();
		   		var _data=eval('(' + data + ')');
		   		$('#js-mp3-path').val(_data.data);
		    },
			'onUploadError':function(file, errorCode, errorMsg, errorString){
				debugger;
//				console.log(file, errorCode, errorMsg, errorString);
			}
		});
		
		var lrcUploadSetting=$.extend({},uploadSeting,{
			'width':'70px',
			'height':'30px',
			'buttonText':'上传',
			'fileTypeExts':'*.lrc',
			'fileObjName':'fileLrc',
			'onUploadSuccess': function (fileObj, data, response) {
				layer.closeAll();
		   		var _data=eval('(' + data + ')');
		   		$('#js-lrc-path').val(_data.data);
		    },
			'onUploadError':function(file, errorCode, errorMsg, errorString){
				debugger;
//				console.log(file, errorCode, errorMsg, errorString);
			}
		});
		
		var krcUploadSetting=$.extend({},uploadSeting,{
			'width':'70px',
			'height':'30px',
			'buttonText':'上传',
			'fileTypeExts':'*.krc',
			'fileObjName':'fileKrc',
			'onUploadSuccess': function (fileObj, data, response) {
				layer.closeAll();
		   		var _data=eval('(' + data + ')');
		   		$('#js-krc-path').val(_data.data);
		    },
			'onUploadError':function(file, errorCode, errorMsg, errorString){
				debugger;
//				console.log(file, errorCode, errorMsg, errorString);
			}
		});
		
		$("#js-upload-mp3").uploadify(mp3UploadSetting);
		
		$("#js-upload-lrc").uploadify(lrcUploadSetting);
		
		$("#js-upload-krc").uploadify(krcUploadSetting);
		
		$('#js_btn_add').click(function() {
			$sidebarTt.html('新增');
			form.clearData($form);
			sidebar.show($sidebar);
		});
		
		$('#js_btn_edit').click(function() {
			var _selectedIds = $jqGrid.jqGrid("getGridParam","selrow");
			if (!_selectedIds) {
				msg.error('请选择一行');
				return;
			}
			var _rowData=$jqGrid.jqGrid('getRowData', _selectedIds);
			$sidebarTt.html('修改');
			form.setData($form, _rowData);
			var path = _rowData["path"];
			$mp3Path.val(path);
			$lrcPath.val(path.replace(".mp3", ".lrc"));
			$krcPath.val(path.replace(".mp3", ".krc"));
			sidebar.show($sidebar);
		});
		
		$('#js_btn_cancel').click(function() {
			sidebar.hide($sidebar);
		});
		
		$('#js_btn_submit').click(function() {
			var data = form.getData($("#form"));
			sendAjax(!data.id ? MP3_ADD : MP3_UPDATE, data, function(d) {
				msg.success((!data.id ? '新增' : '修改') + '成功');
				reloadJqGrid($jqGrid);
				sidebar.hide($sidebar);
			});
		});
		
		init();
		
//		return{
//			init:init
//		}
	})();
});

