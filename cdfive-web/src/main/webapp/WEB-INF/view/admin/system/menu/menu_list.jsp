<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.sub-container .guidebar{
	width: 300px;
}
.sub-container .content{
	width: auto;
	min-width: 865px;
	margin-left: 320px;
}
.sub-container .content-items-cont{
	padding: 30px;
}
.sub-container .sidebar .btn-box{
	position: absolute;
	bottom: 0;
}
.sub-container .sidebar legend{
    line-height: 60px;
    padding-left: 30px;
}
.sub-container .btn-box{
	width: 100%;
	padding: 20px 0 20px 108px;
}
.sub-container .sidebar .add-module{
	width: 530px;
}
.margin-top-30px{
	margin-top: 30px;
}
.margin-left-30px{
	margin-left: 30px;	
}
.border-top{
	border-top:1px solid #ebebeb;
}
.permission-btn{
	margin-bottom: 20px;
}
/*左侧添加子定义小图标*/
.ztree li a:hover{
	background: #ddd;
}
.ztree li a .iconfont{
	padding-right: 4px;
}
.ztree .node_name{
	padding-right: 10px;
}
.form-horizontal span{
	color:red;
}
.btn-box .btn {
	padding: 6px 26px;
}
.button-btn-box .btn{
	margin-right: 14px;
}
.button-btn-box{
	padding-top: 20px;
	padding-bottom: 20px;
}
</style>
<div class="guidebar">
	<div class="guidebar-tt">
		<a class="add-btn" id="btnAddMenu"><span>+</span>菜单</a>
	</div>
	<div class="guidebar-cont">
		<ul id="js-tree" class="ztree"></ul>
	</div>
</div>

<div class="content">
	<div class="content-items" id="divMenu">
		<h3 id="titleMenu" class="content-items-tt">添加菜单</h3>
		<div class="content-items-cont">
			<form id="menuForm" class="form-horizontal fix" role="form">
				<div class="form-group col-sm-6">
					<label for="inputEmail3" class="col-sm-3 control-label"><span>*</span>菜单名称</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="txtMenuName" name="name" placeholder="">
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label for="inputEmail3" class="col-sm-3 control-label">菜单地址</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="txtMenuUrl" name="url" placeholder="">
					</div>
				</div>
				<div class="btn-box" style="clear:both;">
					<a id="btnSaveMenu" class="btn btn-primary">提交</a>
					<a id="btnCancelSaveMenu" class="btn btn-default margin-left-30px">取消</a>
				</div>
			</form>
		</div>
	</div>
	
	<div id="divButton" class="content-items margin-top-30px" style="display:none;">
		<h3 class="content-items-tt" id="subTitle">按钮列表</h3>
		<div class="content-items-cont">
			<div class="button-btn-box">
				<a id="btnAddButton" class="btn btn-primary">
					<i class="iconfont">&#xe61b;</i>
					添加
				</a>
				<a id="btnUpdateButton" class="btn btn-primary">
					<i class="iconfont">&#xe61c;</i>
					编辑
				</a>
				<a id="btnDelButton" class="btn btn-primary">
					<i class="iconfont">&#xe61d;</i>
					删除
				</a>
			</div>
			<div class="jqrid-cont">
				<table id="js-jqGrid"></table>
				<div id="js-jqGrid-pager"></div>
			</div>
		</div>
	</div>
</div>
	
<div id="js_div_sidebar" class="sidebar-container">
	<div class="sidebar-item" id='js-permission-add'>
		<div class="sidebar-tt">
			添加按钮
		</div>
		<div class="sidebar-main-cont">
			<form id="buttonForm" class="form-horizontal" role="form">
				<div class="form-group">
					<label for="nickname" class="col-sm-3 control-label">
					<span class="sub-mark" style="color:red;">*</span>按钮名称:
				</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" id="txtButtonName" name="name" placeholder="">
					</div>
				</div>
				<div class="form-group">
					<label for="businessTitle" class="col-sm-3 control-label">
					<span class="sub-mark" style="color:red;">*</span>按钮地址:
				</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" id="txtButtonUrl" name="url" placeholder="">
					</div>
				</div>
			</form>
		</div>
		<div class="btn-bottom-box fix ">
			<a id="btnSaveButton" class="fl btn btn-primary">提交</a>
			<a id="btnCancelSaveButton" class="fr btn btn-default">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript" src="/static/js/admin/system/menu/menu_list.js"></script>
