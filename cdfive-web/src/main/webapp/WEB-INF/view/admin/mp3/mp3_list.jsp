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
						<span class="sub-mark">*</span>歌名:
					</label>
					<div class="col-sm-6">
						<input type="text" class="form-control js-input-reset" id="js-songName" name="songName" value="" />
					</div>
				</div>
				<div class="form-group no-bottom">
					<label for="" class="col-sm-3 control-label"> 
						<span class="sub-mark">*</span>歌手:
					</label>
					<div class="col-sm-6">
						<input type="text" class="form-control js-input-reset" id="js-author" name="author" value="" />
					</div>
				</div>
				<div class="form-group no-bottom">
					<label for="" class="col-sm-3 control-label"> 
						<span class="sub-mark"></span>分组:
					</label>
					<div class="col-sm-6">
						<input type="text" class="form-control js-input-reset" id="js-groups" name="groups" value="" />
					</div>
				</div>
				<div class="form-group no-bottom">
					<label for="" class="col-sm-3 control-label"> 
						<span class="sub-mark"></span>喜欢理由:
					</label>
					<div class="col-sm-6">
						<input type="text" class="form-control js-input-reset" id="js-reason" name="reason" value="" />
					</div>
				</div>
				<div class="form-group no-bottom">
					<label for="" class="col-sm-3 control-label"> 
						<span class="sub-mark">*</span>上传mp3:
					</label>
					<div class="col-sm-6">
						<input type="text" class="form-control js-input-reset" id="js-mp3-path" name="mp3Path" value=""  readonly/>
						<div type="button" class="browse-btn col-sm-4 colo-sm-offset-4" id="js-upload-mp3" ></div>
					</div>
				</div>
				<div class="form-group no-bottom">
					<label for="" class="col-sm-3 control-label"> 
						<span class="sub-mark">*</span>上传lrc:
					</label>
					<div class="col-sm-6">
						<input type="text" class="form-control js-input-reset" id="js-lrc-path" name="lrcPath" value=""  readonly/>
						<div type="button" class="browse-btn col-sm-4 colo-sm-offset-4" id="js-upload-lrc" ></div>
					</div>
				</div>
				<div class="form-group no-bottom">
					<label for="" class="col-sm-3 control-label"> 
						<span class="sub-mark">*</span>上传krc:
					</label>
					<div class="col-sm-6">
						<input type="text" class="form-control js-input-reset" id="js-krc-path" name="krcPath" value=""  readonly/>
						<div type="button" class="browse-btn col-sm-4 colo-sm-offset-4" id="js-upload-krc" ></div>
					</div>
				</div>
			</form>
		</div>
		<div class="btn-bottom-box fix ">
			<a id="js_btn_submit" class="fl submit-data">提交</a>
			<a id="js_btn_cancel" class="fr cancel">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript" src="/static/js/admin/mp3/mp3_list.js"></script>
