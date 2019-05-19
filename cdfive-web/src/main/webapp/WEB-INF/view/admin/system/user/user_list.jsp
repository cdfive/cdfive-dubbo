<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.browse-btn{
	background-color: #ccc;
    margin-top: 5px;
    height: 30px;
    border-radius: 5px;
}
.uploadify-button-text {
    padding: 3px 5px;
    background-color: #ddd;
    border-radius: 3px;
    cursor: pointer;
}
.col-sm-6{
	width: 70%;
}
#js-mp3-path,#js-lrc-path,#js-krc-path{
	width: 75%;
    float: left;
}
#js-upload-mp3,#js-upload-lrc,#js-upload-krc{
    float: left;
    margin-left: 10px;
    margin-top: 6px;
}
.sub-mark{
	color:red;
}
</style>
<div class="content margin-left-30px">
	<div class="tab-list-group">
		<div class="list-item">
			<div class="content-items">
				<div class="content-items-cont">
					<div class="operate-btns margin-bottom-30px">
						<a id="js_btn_add" class="btn action-button">
							<i class="iconfont">&#xe61b;</i>
							添加
						</a>
						<a id="js_btn_edit" class="btn action-button">
							<i class="iconfont">&#xe61c;</i>
							编辑
						</a>
						<a id="js_btn_del" class="btn del-button">
							<i class="iconfont">&#xe61d;</i>
							删除
						</a>
					</div>
					<div class="uset-list-jqgrid">
						<div class="jqrid-cont" class="js-jqrid-cont">
							<table id="jqGrid"></table>
							<div id="jqGridPager"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="sidebar-container" id="js-sidebar">
	<div class="sidebar-item" id='js-sidebar-right'>
		<div class="sidebar-tt">添加音乐</div>
		<div class="sidebar-main-cont">
			<form class="form-horizontal" id="form">
				<input type="hidden" name="id" />
				<div class="form-group no-bottom">
					<label for="" class="col-sm-3 control-label"> 
						<span class="sub-mark">*</span>用户名:
					</label>
					<div class="col-sm-6">
						<input id="js_txt_userName" name="userName" type="text" class="form-control" value="" readonly/>
					</div>
				</div>
				<div id="div_password" class="form-group no-bottom">
					<label for="" class="col-sm-3 control-label"> 
						<span class="sub-mark">*</span>密码:
					</label>
					<div class="col-sm-6">
						<input id="js_txt_password" name="password" type="password" class="form-control" value="" />
					</div>
				</div>
				<div class="form-group no-bottom">
					<label for="" class="col-sm-3 control-label"> 
						<span class="sub-mark"></span>姓名:
					</label>
					<div class="col-sm-6">
						<input id="js_txt_realName" name="realName" type="text" class="form-control js-input-reset" value="" />
					</div>
				</div>
				<div class="form-group no-bottom">
					<label for="" class="col-sm-3 control-label"> 
						<span class="sub-mark">*</span>类型:
					</label>
					<div class="col-sm-6">
						<select class="form-control js-visit-limit col-sm-2" name="isAdmin">
							<option value="" >请选择</option>
							<option value="1">管理员</option>
							<option value="0">普通用户</option>
						</select>
					</div>
				</div>
				<div id="div_userStatus" class="form-group no-bottom" style="display:none;">
					<label for="" class="col-sm-3 control-label"> 
						<span class="sub-mark">*</span>状态:
					</label>
					<div class="col-sm-6">
						<select class="form-control js-visit-limit col-sm-2" name="userStatus">
							<option value="">请选择</option>
							<option value="1">正常</option>
							<option value="2">冻结</option>
						</select>
					</div>
				</div>
				<div id="div_errCount" class="form-group no-bottom" style="display:none;">
					<label for="" class="col-sm-3 control-label"> 
						<span class="sub-mark"></span>密码错误次数:
					</label>
					<div class="col-sm-6">
						<input id="js_txt_errCount" name="errCount" type="text" class="form-control js-input-reset" value="" />
					</div>
				</div>
				<div class="form-group no-bottom" >
					<label for="" class="col-sm-3 control-label"> 
						<span class="sub-mark"></span>角色:
					</label>
					<div id="div_role" class="col-sm-6">
						
					</div>
				</div>
			</form>
		</div>
		<div class="btn-bottom-box fix ">
			<a id="js-submit" class="fl submit-data">提交</a>
			<a id="js-cancel" class="fr cancel">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript" src="/static/js/admin/system/user/user_list.js"></script>
