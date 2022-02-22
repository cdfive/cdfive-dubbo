<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/base-param.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
    <div>
        <div class="col-*-12">
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel">
                        <div class="panel-body">
                            <div class="col-md-12">
                                <div class="form-inline">
                                    <div class="form-group col-md-12">
                                        <label for="name">分类名称：</label>
                                        <input id="name" type="text" class="form-control" style=" margin-top: -4px;">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <form id="form" action="">
                                    <table id="category_table" class="table table-hover"></table>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var PAGENUMBER = 1;
    var PAGESIZE = 10;
    $(function () {
        $(window).resize(function () {
            $('table#category_table').bootstrapTable('resetView', {
                height: tableHeight()
            })
        });

        function tableHeight() {
            return $(window).height() - 50;
        }

        $('table#category_table').bootstrapTable({
            method: "POST",
            toolbar: "#toolbar",
            striped: true,
            cache: false,
            pagination: true,
            sortable: false,
            sortOrder: "asc",
            pageNumber: PAGENUMBER,
            url: "${webroot}/category/list",
            // dataField: "result",
            pageSize: PAGESIZE,
            pageList: [5, 10, 20, 50],
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            sidePagination: "server",
            queryParamsType: '',
            queryParams: sortQueryParams,
            columns: [{
                checkbox: true
            },{
                title: "分类id", field: "id", align: "center", valign: "middle"
            },{
                title: "分类名称", field: "name", align: "center", valign: "middle"
            }, {
                title: "分类描述", field: "description", sortable: true, align: "center", valign: "middle"
            }, {
                title: "创建时间", field: "createTime", sortable: true, align: "center", valign: "middle"
            },  {
                title: "操作", formatter: operateFormatter, align: "center", valign: "middle"
            }],
            locale: "zh-CN",
            detailView: false
        });

        function sortQueryParams(params) {
            PAGENUMBER = params.pageNumber;
            PAGESIZE = params.pageSize;
            return {
                'pageNum' : PAGENUMBER,
                'pageSize' : PAGESIZE,
                'name': $("#name").val()
            }
        }

        function operateFormatter(value, row, index) {
            return "<button type='button' class='btn btn-primary' onclick='edit(\"" + row.id + "\");'>修改</button>&nbsp;"
                 + "<button type='button' class='btn btn-primary' onclick='del(\"" + row.id + "\");'>删除</button>&nbsp;";
        }
    });

    function edit(id) {
        menuopen("/category/edit?id="+ id);
    }

    function del(id) {
        layer.confirm('确定要删除该分类吗?', {
            btn: ['确定','取消']
        }, function(index){
            var url = "${webroot}/category/delete";
            $.ajax({
                type : "POST",
                url : url,
                data : {'id':id},
                error : function(request) {
                    layer.msg("操作失败！");
                    layer.close(index);
                },
                success : function(data) {
                    if(data.code == 0){
                        layer.msg(data.msg);
                        $('table#category_table').bootstrapTable('refresh');
                    }else{
                        layer.msg(data.msg);
                    }
                }
            });
        });
    }
</script>