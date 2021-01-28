<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<li class="dropdown ">
	<a id="theme-setting" href="javascript:;" data-hover="dropdown" data-step="1"
	data-position="left" class="dropdown-toggle"><i class="fa fa-cogs"></i></a>
	<ul class="dropdown-menu dropdown-theme-setting pull-right">
		<li class="hide">
			<h4 class="mtn">主题</h4>
			<select name="list-style" id="list-style" class="form-control">
				<option value="style1">线框</option>
			</select>
		</li>
		<li>
			<h4 class="mtn">菜单样式</h4>
			<select name="list-menu" id="list-menu" class="form-control">
				<option value="sidebar-default">色块式</option>
				<option value="sidebar-colors">线框式</option>
				<option value="sidebar-icons">大图标</option>
				<option value="sidebar-collapsed">小图标</option>
			</select>
		</li>
		<li>
			<h4 class="mtn">页眉 &amp; 侧边栏</h4>
			<select name="list-header" id="list-header" class="form-control">
				<option selected="selected" value="header-fixed">固定</option>
				<%--<option value="header-static">静止</option>--%>
			</select>
		</li>
		<li>
			<h4 class="mtn">主题色彩</h4>
			<ul id="list-color" class="list-unstyled list-inline">
				<li data-color="green-dark" data-hover="tooltip" title="绿色 - 暗黑" class="green-dark"></li>
				<li data-color="red-dark" data-hover="tooltip" title="红色 - 暗黑" class="red-dark"></li>
				<li data-color="pink-dark" data-hover="tooltip" title="粉红 - 暗黑" class="pink-dark"></li>
				<li data-color="blue-dark" data-hover="tooltip" title="蓝色 - 暗黑" class="blue-dark"></li>
				<li data-color="yellow-dark" data-hover="tooltip" title="黄色 - 暗黑" class="yellow-dark"></li>
				<li data-color="green-grey" data-hover="tooltip" title="绿色 - 灰色" class="green-grey"></li>
				<li data-color="red-grey" data-hover="tooltip" title="红色 - 灰色" class="red-grey"></li>
				<li data-color="pink-grey" data-hover="tooltip" title="粉红 - 灰色" class="pink-grey"></li>
				<li data-color="blue-grey" data-hover="tooltip" title="蓝色 - 灰色" class="blue-grey"></li>
				<li data-color="yellow-grey" data-hover="tooltip" title="黄色 - 灰色" class="yellow-grey"></li>
				<li data-color="yellow-green" data-hover="tooltip" title="黄色 - 绿色" class="yellow-green"></li>
				<li data-color="orange-grey" data-hover="tooltip" title="橙色 - 灰色" class="orange-grey"></li>
				<li data-color="pink-blue" data-hover="tooltip" title="粉红 - 蓝色" class="pink-blue"></li>
				<li data-color="pink-violet" data-hover="tooltip" title="粉红 - 紫色" class="pink-violet active"></li>
				<li data-color="orange-violet" data-hover="tooltip" title="橙色 - 紫色" class="orange-violet"></li>
				<li data-color="pink-green" data-hover="tooltip" title="粉红 - 绿色" class="pink-green"></li>
				<li data-color="pink-brown" data-hover="tooltip" title="粉红 - 棕色" class="pink-brown"></li>
				<li data-color="orange-blue" data-hover="tooltip" title="橙色 - 蓝色" class="orange-blue"></li>
				<li data-color="yellow-blue" data-hover="tooltip" title="黄色 - 蓝色" class="yellow-blue"></li>
				<li data-color="green-blue" data-hover="tooltip" title="绿色 - 蓝色" class="green-blue"></li>
				<li data-color="blue-green" data-hover="tooltip" title="绿色 -浅蓝色" class="blue-green"></li>
			</ul>
		</li>
	</ul>
</li>