<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="/static/css/admin/role/role_list.css" />
<div class="sub-container">
	<div class="guidebar">
		<div class="guidebar-tt">
			<a id="js_btn_add" class="add-btn"><span>+</span>添加角色</a>
		</div>
		<ul id="role_list" class="role-name">
			<script id="tpl-role-list" type="text/html">
			{{each list as v i}}
				<li><a class="role-name-detail"> 
					<i class="iconfont role-name-jiaose">&#xe619;</i>
					<span class="name" data="{{v.id}}">{{v.name}}</span>
					<span class="role-name-right"> 
						<i class="js_btn_del iconfont delete color-red">&#xe61d;</i>
					</span>
					</a>
				</li>
			{{/each}}
			</script>
		</ul>
	</div>
	<div class="content">
		<div class="content-items">
			<h3 class="content-items-tt">角色信息</h3>
			<div class="content-items-cont">
				<form class="form-horizontal fix" id="form_role" role="form">
					<div class="form-group col-sm-5">
						<label for="rolename" class="col-sm-4 control-label txt-left"><span>*</span>角色名称:</label>
						<div class="col-sm-8 no-left-padding">
							<input id="js_txt_name" name="name" type="text" maxlength="20" class="form-control" > 
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="content-items margin-top-30px">
			<h3 class="content-items-tt">角色权限</h3>
			<div class="content-items-cont">
				<ul id="js_role_tree" class="ztree power-ztree"></ul>
			</div>
		</div>
		<div class="btn-box">
			<a id="js_btn_submit" class="btn btn-primary">提交</a> 
			<a id="js_btn_cancel" class="btn btn-default margin-left-30px">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript" src="/static/js/admin/system/role/role_list.js"></script>

