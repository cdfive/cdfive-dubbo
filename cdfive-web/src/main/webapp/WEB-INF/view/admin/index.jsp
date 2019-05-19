<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Disposition" content="attachment;"> 
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>管理后台</title>
<%@include file="/WEB-INF/view/taglib.jsp"%>
<link rel="stylesheet" href="/static/plugin/bootstrap/3.3.5/css/bootstrap.min.css" />
<link rel="stylesheet" href="/static/css/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="/static/plugin/jqGrid/5.0.1/ui.jqgrid-bootstrap.css" />
<link rel="stylesheet" href="/static/plugin/jqGrid/5.0.1/ui.jqgrid-plugin.css" />
<!-- <link rel="stylesheet" href="/static/plugin/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="/static/plugin/bootstrap-select/css/bootstrap-select.min.css"> -->
<!-- <link rel="stylesheet" href="/static/plugin/lightbox/css/lightbox.css"> -->
<link rel="stylesheet" href="/static/plugin/ztree/3.5/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" href="/static/plugin/ztree/3.5/css/ztree-reset-v1.css">
<link rel="stylesheet" href="/static/css/public/reset.css"/>
<link rel="stylesheet" href="/static/css/admin/index.css"/>
<link rel="stylesheet" href="/static/css/admin/sub_container_layout.css" />
<!-- <link rel="stylesheet" href="/static/plugin/uploadify/uploadify.css" /> -->
</head>
<%-- <input id="js-data-base-path" type="hidden" value="<%=basePath %>">
<input id="js-data-file-path" type="hidden" value="${file_path}"> --%>
<div class="index-header">
	<div class="index-header-logo">
		<div class="pic">
		</div>
		<h2 id="js_h2_curTime" style="margin-left: 30px;"></h2>
	</div>
	<div class="index-header-user">
		<a class="user-info"> <i class="icon-male"></i> 
			<span class="name" id="userName">${mp3_session_user.realName != null ? mp3_session_user.realName : mp3_session_user.userName}</span>
			<i class="icon-caret-down small"></i>
		</a>
		<ul class="index-header-submenu">
			<li><a id="js_btn_change_password" href="javascript:void(0);"> <i
					class="icon-key"></i> 修改密码
			</a></li>
			<li><a id="js_btn_logout" href="javascript:void(0);">
			 	<i class="icon-key"></i> 退出
			</a></li>
		</ul>
	</div>
</div>
<div class="index-contain">
	<div class="index-contain-sidebar" id="js-index-contain-sidebar" status="show">
		<div id="sidebar-content">
			<ul id="js_ul_siderbarNav" class="siderbar-nav">
				<script id="tpl-side-nav" type="text/html">
				{{each list as v i}}
				<li class="siderbar-nav-items">
					
			        <a{{if v.url}} href="{{v.url}}"{{/if}}>
			            <i class=""></i>
						{{v.name}}
			            <span class=" icon-angle-left"></span>
			        </a>
					{{if v.subMenu}}
			        <ul class="sub-menu js-sub-menu">
						{{each v.subMenu as w j}}
						<li>
							<a href="{{w.url}}" class="js-side-nav"><i class="icon-angle-right"></i>
			            		{{w.name}}
			        		</a>
						</li>
						{{/each}}
			        </ul>
					{{/if}}
    			</li>
				{{/each}}
			    </script>
			</ul>
		</div>
	</div>
	<div class="index-contain-content sub-container"
		id="js-index-contain-content">
		<!--  js 加载-->
	</div>
</div>
<script type="text/javascript" src="/static/plugin/jquery/jquery.1.10.2.js"></script>
<script type="text/javascript" src="/static/plugin/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/static/plugin/layer/layer.js"></script>
<script type="text/javascript" src="/static/plugin/artTemplate/template.js"></script>
<script type="text/javascript" src="/static/plugin/jqGrid/5.0.1/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="/static/plugin/jqGrid/5.0.1/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="/static/plugin/ztree/3.5/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="/static/plugin/uploadify/jquery.uploadify.min.js"></script>
<script src="/static/plugin/echarts/echarts.3.5.4.js" type="text/javascript"></script>
<script type="text/javascript" src="/static/plugin/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="/static/plugin/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="/static/plugin/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/static/plugin/ueditor/third-party/zeroclipboard/ZeroClipboard.min.js"></script>

<script type="text/javascript" src="/static/js/public/api.js"></script>
<script type="text/javascript" src="/static/js/public/tool.js"></script>
<script type="text/javascript" src="/static/js/public/common.js"></script>
<script type="text/javascript" src="/static/js/admin/index.js"></script>
</body>
</html>