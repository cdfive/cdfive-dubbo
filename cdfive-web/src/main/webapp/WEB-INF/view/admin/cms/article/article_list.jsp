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
.form-horizontal span.sub-mark{
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
.sidebar-container {
	width:818px;
}
#js_form_article label {
	width:150px;
}
#js_form_article input[type='radio'] {
	margin-top:10px;
}

#js_span_oriUrlClickCount,#js_span_clickCount{
    display: table;
	margin-top: 6px;
	font-weight: normal;
}
.btn-bottom-box {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 18px;
    background: #f8f8f8;
    border-top: 1px solid #ececec;
    text-align: center;
}

#js_div_sidebar .btn-bottom-box a {
    margin-left: auto;
    margin-right: auto;
    display: inline-block;
    width: 100px;
    height: 40px;
    color: #fff;
    text-align: center;
    line-height: 40px;
    padding:0px;
}
#js_div_sidebar .btn-bottom-box a.margin-left-30px{
	margin-left: 30px;	
}
</style>
<div class="guidebar">
	<div class="guidebar-cont">
		<ul id="js_tree" class="ztree"></ul>
	</div>
</div>

<div class="content">
	<div class="button-btn-box">
		<a id="js_btn_add" class="btn btn-primary">
			<i class="iconfont">&#xe61b;</i>
			添加
		</a>
		<a id="js_btn_update" class="btn btn-primary">
			<i class="iconfont">&#xe61c;</i>
			编辑
		</a>
		<a id="js_btn_del" class="btn btn-primary">
			<i class="iconfont">&#xe61d;</i>
			删除
		</a>
	</div>
	<div>
		<div class="jqrid-cont">
			<table id="js_jqGrid"></table>
			<div id="js_jqGridPager"></div>
		</div>
	</div>
</div>

<div id="js_div_sidebar" class="sidebar-container">
	<div class="sidebar-item">
		<div id="js_div_tt" class="sidebar-tt">
		</div>
		<div class="sidebar-main-cont">
			<form id="js_form_article" class="form-horizontal" role="form">
				<input type="hidden" name="id" />
				<div id="js_div_title" class="form-group">
					<label class="col-sm-1 control-label">
						<span class="sub-mark" style="color:red;">*</span>标题:
					</label>
					<div class="col-sm-9">
						<input id="js_txt_title" name="title" type="text" class="form-control" placeholder="">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-1 control-label">
						<span class="sub-mark" style="color:red;">*</span>内容:
					</label>
					<div class="col-sm-9">
						<script id="js_ueditor" class="js-notice-content" name="content" type="text/plain" style="width:100%;height:258px;">
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-1 control-label">
						<span class="sub-mark" style="color:red;">*</span>来源:
					</label>
					<div class="col-sm-9">
						<input name="isOri" type="radio" value="1" />原创
						<input name="isOri" type="radio" value="0" checked/>转载
					</div>
				</div>
				<div id="js_div_oriUrl" class="form-group">
					<label class="col-sm-1 control-label">
						<span class="sub-mark" style="color:red;">*</span>原地址:
					</label>
					<div class="col-sm-9">
						<input id="js_txt_oriUrl" name="oriUrl" type="text" class="form-control" placeholder="">
					</div>
				</div>
				<div id="js_div_oriUrlClickCount" class="form-group" style="display:none;">
					<label class="col-sm-1 control-label">
						原地址点击数:
					</label>
					<label class="col-sm-1">
						<span id="js_span_oriUrlClickCount"></span>
					</label>
				</div>
				<div class="form-group">
					<label class="col-sm-1 control-label">
						<span class="sub-mark" style="color:red;">*</span>是否置顶:
					</label>
					<div class="col-sm-9">
						<input name="isTop" type="radio" value="1" />是
						<input name="isTop" type="radio" value="0" checked/>否
					</div>
				</div>
				<div id="js_div_clickCount" class="form-group" style="display:none;">
					<label class="col-sm-1 control-label">
						点击数:
					</label>
					<label class="col-sm-1">
						<span id="js_span_clickCount"></span>
					</label>
				</div>
			</form>
		</div>
		<div class="btn-bottom-box fix">
			<a id="js_btn_submit" class="submit-data">提交</a>
			<a id="js_btn_cancel" class="cancel margin-left-30px">取消</a>
		</div>
	</div>
</div>

<script type="text/javascript" src="/static/js/admin/cms/article/article_list.js"></script>
