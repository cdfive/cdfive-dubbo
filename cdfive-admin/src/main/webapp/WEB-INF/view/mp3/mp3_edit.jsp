<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/base-param.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link type="text/css" rel="stylesheet" href="${webroot}/static/madmin/vendors/select2/select2-madmin.css">
<link type="text/css" rel="stylesheet"
      href="${webroot}/static/madmin/vendors/bootstrap-select/bootstrap-select.min.css">
<link type="text/css" rel="stylesheet"
      href="${webroot}/static/madmin/vendors/multi-select/css/multi-select-madmin.css">

<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">修改歌曲</h3>
    </div>
    <button  type="button" class="cancle btn btn-gray">返回</button>
    <form role="form" id="mp3Form" class="form-horizontal form-bordered" onsubmit="return false;">
        <input name="id" value="${detailVo.id}" type="hidden"/>
        <div class="panel-body">
            <div class="form-group">
                <label class="col-md-2 control-label" for="categoryId">分类</label>
                <div class="col-md-4">
                    <select class="select2-size form-control select2-offscreen" id="categoryId" name="categoryId">
                        <option value="">全部${detailVo.categoryId}</option>
                        <c:forEach var="category" items="${categories}">
                            <c:if test="${detailVo.categoryId eq category.id}">
                                <option selected="selected" value="${category.id}">${category.name}</option>
                            </c:if>
                            <c:if test="${detailVo.categoryId != category.id}">
                                <option value="${category.id}">${category.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label" for="name">歌名:</label>
                <div class="col-md-5">
                    <div class="input-icon">
                        <input type="text" name="name" class="form-control" value="${detailVo.name}">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label" for="author">歌手:</label>
                <div class="col-md-5">
                    <div class="input-icon">
                        <input type="text" name="author" class="form-control" value="${detailVo.author}">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">喜欢理由:</label>
                <div class="col-md-5">
                    <div class="input-icon">
                        <textarea type="text" name="reason" class="form-control">${detailVo.reason}</textarea>
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

<script src="${webroot}/static/madmin/vendors/select2/select2.min.js"></script>
<script src="${webroot}/static/madmin/vendors/bootstrap-select/bootstrap-select.min.js"></script>
<script src="${webroot}/static/madmin/vendors/multi-select/js/jquery.multi-select.js"></script>
<script src="${webroot}/static/js/ui-dropdown-select.js"></script>

<script type="text/javascript">
    $(function () {
        var id = $("#id");
        var url = id == null ? "/mp3/add" : "/mp3/update";
        var data = $("#mp3Form").serializeJson();
        data["categoryIds"] = [];
        data["categoryIds"].push($("#selectCategoryId").val());
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
                    menuopen('/mp3/list');
                } else {
                    layer.msg("保存失败：" + data.msg);
                    $this.prop('disabled', false);
                }
            });
        });
    });
</script>