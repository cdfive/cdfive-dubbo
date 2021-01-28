<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/base-param.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">修改分类</h3>
    </div>
    <button  type="button" class="cancle btn btn-gray">返回</button>
    <form role="form" id="categoryForm" class="form-horizontal form-bordered" onsubmit="return false;">
        <input name="id" value="${detailVo.id}" type="hidden"/>
        <div class="panel-body">
            <div class="form-group">
                <label class="col-md-2 control-label" for="name">分类名称:</label>
                <div class="col-md-5">
                    <div class="input-icon">
                        <input type="text" name="name" class="form-control" value="${detailVo.name}">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">分类描述:</label>
                <div class="col-md-5">
                    <div class="input-icon">
                        <textarea type="text" name="description" class="form-control">${detailVo.description}</textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-actions">
            <div class="col-md-offset-5 col-md-7">
                <button id="btnSubmit" class="btn btn-primary">
                    保存
                </button>
                <button  type="button" class="cancle btn btn-gray">返回</button>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript">
    $(function () {
        var id = $("#id");
        var url = id == null ? "/category/add" : "/category/update";
        var data = $("#categoryForm").serializeJson();
        $('#btnSubmit').click(function () {
            $.ajax({
                cache: false,
                type: "POST",
                url: "${webroot}" + url,
                data: data,
                async: false
            }).done(function (data) {
                if (data.code == 0) {
                    layer.msg("保存成功");
                    menuopen('/category/list');
                } else {
                    layer.msg("保存失败：" + data.msg);
                    $this.prop('disabled', false);
                }
            });
        });
    });
</script>