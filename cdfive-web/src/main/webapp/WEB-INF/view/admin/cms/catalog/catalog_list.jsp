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
		<a id="js_btn_add" class="add-btn"><span>+</span>分类</a>
	</div>
	<div class="guidebar-cont">
		<ul id="js_tree" class="ztree"></ul>
	</div>
</div>

<div class="content">
	<div class="content-items">
		<h3 id="js_h3_title" class="content-items-tt">添加分类</h3>
		<div class="content-items-cont">
			<form id="js_form_catalog" class="form-horizontal fix" role="form">
				<div class="form-group col-sm-6">
					<label class="col-sm-3 control-label"><span>*</span>分类名称</label>
					<div class="col-sm-8">
						<input id="js_txt_name" name="name" type="text" class="form-control" placeholder="">
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label for="inputEmail3" class="col-sm-3 control-label">分类描述</label>
					<div class="col-sm-8">
						<input id="js_txt_description" name="description" type="text" class="form-control" placeholder="">
					</div>
				</div>
				<div class="btn-box" style="clear:both;">
					<a id="js_btn_submit" class="btn btn-primary">提交</a>
					<a id="js_btn_cancel" class="btn btn-default margin-left-30px">取消</a>
				</div>
			</form>
		</div>
	</div>
</div>
	
<script type="text/javascript" src="/static/js/admin/cms/catalog/catalog_list.js"></script>
