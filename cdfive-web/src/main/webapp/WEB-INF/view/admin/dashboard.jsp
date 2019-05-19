<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
.huge {
	font-size: 50px;
	line-height: normal;
}

.panel-green {
	border-color: #5cb85c;
}

.panel-green>.panel-heading {
	border-color: #5cb85c;
	color: #fff;
	background-color: #5cb85c;
}

.panel-green>a {
	color: #5cb85c;
}

.panel-green>a:hover {
	color: #3d8b3d;
}

.panel-red {
	border-color: #d9534f;
}

.panel-red>.panel-heading {
	border-color: #d9534f;
	color: #fff;
	background-color: #d9534f;
}

.panel-red>a {
	color: #d9534f;
}

.panel-red>a:hover {
	color: #b52b27;
}

.panel-yellow {
	border-color: #f0ad4e;
}

.panel-yellow>.panel-heading {
	border-color: #f0ad4e;
	color: #fff;
	background-color: #f0ad4e;
}

.panel-yellow>a {
	color: #f0ad4e;
}

.panel-yellow>a:hover {
	color: #df8a13;
}
</style>

<div id="page-wrapper">
	<div class="container-fluid">
		<!-- Page Heading -->
		<div class="row">
			<div class="col-lg-12">
				<h2 class="page-header">
					首页 <small></small>
				</h2>
				<ol class="breadcrumb" style="height:33px;">
					<li class="active" style="height:17px;">
						<i class="fa fa-dashboard fl" style="margin-top:1px;margin-right:5px;"></i>
						<div id="js_span_tips" style="display:inline-block">
						</div>
						<!-- 多少曾经，是我们悔恨不已的过去？ -->
					</li>
				</ol>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-3 col-md-6">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3">
								<i class="fa fa-comments fa-5x"></i>
							</div>
							<div class="col-xs-9 text-right">
								<div id="js_div_mp3Count"class="huge"></div>
								<div>首mp3</div>
							</div>
						</div>
					</div>
					<a class="js_c_quick_jump" name="mp3管理" href="javascript:void(0)">
						<div class="panel-footer">
							<span class="pull-left">查看</span> <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
							<div class="clearfix"></div>
						</div>
					</a>
				</div>
			</div>
			<div class="col-lg-3 col-md-6">
				<div class="panel panel-green">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3">
								<i class="fa fa-tasks fa-5x"></i>
							</div>
							<div class="col-xs-9 text-right">
								<div id="js_div_catalogCount" class="huge"></div>
								<div>个栏目</div>
							</div>
						</div>
					</div>
					<a class="js_c_quick_jump" name="栏目管理" href="javascript:void(0)">
						<div class="panel-footer">
							<span class="pull-left">查看</span> <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
							<div class="clearfix"></div>
						</div>
					</a>
				</div>
			</div>
			<div class="col-lg-3 col-md-6">
				<div class="panel panel-yellow">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3">
								<i class="fa fa-shopping-cart fa-5x"></i>
							</div>
							<div class="col-xs-9 text-right">
								<div id="js_div_articleCount" class="huge"></div>
								<div>篇文章</div>
							</div>
						</div>
					</div>
					<a class="js_c_quick_jump" name="文章管理" href="javascript:void(0)">
						<div class="panel-footer">
							<span class="pull-left">查看</span> <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
							<div class="clearfix"></div>
						</div>
					</a>
				</div>
			</div>
			<div class="col-lg-3 col-md-6">
				<div class="panel panel-red">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3">
								<i class="fa fa-support fa-5x"></i>
							</div>
							<div class="col-xs-9 text-right">
								<div id="js_div_userCount" class="huge"></div>
								<div>个用户</div>
							</div>
						</div>
					</div>
					<a class="js_c_quick_jump" name="用户管理" href="javascript:void(0)">
						<div class="panel-footer">
							<span class="pull-left">查看</span> <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
							<div class="clearfix"></div>
						</div>
					</a>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="fa fa-bar-chart-o fa-fw"></i> 访问统计
						</h3>
					</div>
					<div class="panel-body">
						<!-- <div id="morris-area-chart"></div> -->
						<div id="js_btn_return" class="btn-return">返回</div>
						<div id="js_div_request" style="width: 100%; height: 375px;"></div>
						<div id="js_div_requestDate" style="width: 90%; height: 400px; display:none;"></div>
						<div id="js_div_requestDateIp" style="width: 90%; height: 90%; display:none;"></div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="fa fa-long-arrow-right fa-fw"></i>api日志
						</h3>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table-striped">
								<thead>
									<tr>
										<th>地址</th>
										<th>ip</th>
										<th>耗时</th>
										<th>创建时间</th>
									</tr>
								</thead>
								<tbody id="js_tbody_api_log">
									<script id="tpl_api_log_list" type="text/html">
									{{each list as v i}}
									<tr>
										<td>{{v.uri}}</td>
										<td>{{v.ip}}</td>
										<td>{{v.timeCost}}ms</td>
										<td>{{v.createTime | substr:11}}</td>
									</tr>
									{{/each}}
									</script>
								</tbody>
							</table>
						</div>
						<div class="text-right">
							<a href="#">查看<i class="fa fa-arrow-circle-right"></i></a>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="fa fa-clock-o fa-fw"></i> 最新文章
						</h3>
					</div>
					<div class="panel-body">
						<div id="js_div_article" class="list-group">
							<script id="tpl_article_list" type="text/html">
							{{each list as v i}}
								<a href="{{v.oriUrl}}" target="_blank" class="list-group-item text-overflow"> <span class="badge">{{v.createTime}}</span> <i class="fa fa-fw fa-calendar"></i> {{v.title}}
								</a>
							{{/each}}
							</script>
						</div>
						<div class="text-right">
							<a class="js_c_quick_jump" name="文章管理" href="javascript:void(0)">查看<i class="fa fa-arrow-circle-right"></i></a>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="fa fa-money fa-fw"></i> 最新mp3
						</h3>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table-striped">
								<thead>
									<tr>
										<th>歌名</th>
										<th>歌手</th>
										<th>播放次数</th>
										<th>创建时间</th>
									</tr>
								</thead>
								<tbody id="js_tbody_mp3">
									<script id="tpl_mp3_list" type="text/html">
									{{each list as v i}}
									<tr>
										<td>{{v.songName}}</td>
										<td>{{v.author}}</td>
										<td>{{v.playCount}}</td>
										<td>{{v.createTime}}</td>
									</tr>
									{{/each}}
									</script>
								</tbody>
							</table>
						</div>
						<div class="text-right">
							<a class="js_c_quick_jump" name="mp3管理" href="javascript:void(0)">查看 <i class="fa fa-arrow-circle-right"></i></a>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</div>
</div>

<!-- <div id="js_btn_return" class="btn-return">返回</div>
<div id="js_div_request" style="width: 90%; height: 400px;"></div>
<div id="js_div_requestDate" style="width: 90%; height: 400px;"></div>
<div id="js_div_requestDateIp" style="width: 90%; height: 90%;"></div> -->
<script src="/static/js/admin/dashboard.js" type="text/javascript"></script>
