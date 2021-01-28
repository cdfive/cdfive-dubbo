/**
 * Created by Evan on 2017/11/17.
 *
 * 用于bootstraptable插件，返回当前页或跳转指定页
 */

/**
 * 保存基本数据，用户分页，跳转
 *
 * $objectTable：bootstraptable对象
 * number：当前页码
 * size：显示数据条数
 * refresh：是否刷新bootstraptable
 */
function savePageDataFun($objectTable, number, size, refresh) {
    var pagenum;
    if (number !== null) {
        pagenum = number;
    } else {
        pagenum = 1;
    }

    if (refresh) {
        $objectTable.bootstrapTable('refreshOptions', {pageNumber: pagenum});
    }

    sessionStorage.setItem("page", pagenum);
    sessionStorage.setItem("size", size);
}

/**
 * 根据key获取session的value
 */
function getValByKey(key) {
    return sessionStorage.getItem(key);
}

/**
 * 适用于搜索时，存储数据
 */
function searchPageDataFun() {
    var $objectTable = arguments[0];
    var val01 = arguments[1];
    var val02 = arguments[2];
    var val03 = arguments[3];
    var val04 = arguments[4];
    var val05 = arguments[5];
    var val06 = arguments[6];
    var val07 = arguments[7];
    var val08 = arguments[8];
    var val09 = arguments[9];
    var val10 = arguments[10];

    sessionStorage.setItem("page", 1);
    sessionStorage.setItem("size", 10);
    sessionStorage.setItem("val01", val01);
    sessionStorage.setItem("val02", val02);
    sessionStorage.setItem("val03", val03);
    sessionStorage.setItem("val04", val04);
    sessionStorage.setItem("val05", val05);
    sessionStorage.setItem("val06", val06);
    sessionStorage.setItem("val07", val07);
    sessionStorage.setItem("val08", val08);
    sessionStorage.setItem("val09", val09);
    sessionStorage.setItem("val10", val10);

    $objectTable.bootstrapTable('refreshOptions', {pageNumber: 1});
}

/**
 * 用以清除所有的会话级别的数据
 */
function clearPageDataSession() {
    sessionStorage.clear();
}

/**
 * bootstraptable初始化执行方法
 *
 * $objectTable：bootstraptable对象
 * number：当前页码
 * size：显示数据条数
 */
function refreshTableInit($objectTable, number, size) {

    // bootstraptable分页栏跳转页码插件
    var blockEle = '<div class="form-inline" style="float: right; margin-top: 10px; margin-left: 10px;"><input type="button" class="btn btn-primary" id="goPage" value="跳转">&nbsp;<input type="number" class="form-control" id="gotoPage" style="" placeholder="跳转页码">&nbsp;</div>';
    // 分页栏对象
    var $pullRightPagination = $('.pull-right.pagination');

    var maxPageNumber = $('.page-next').prev('li').find('a').html();

    // 如果最大页码为1, 则不显示跳转框
    /*if (parseInt(maxPageNumber) <= 1) {
        return;
    }*/

    $pullRightPagination.before(blockEle);

    // 修复跳转到指定页或返回当前页时，分页栏当前页按钮没有高亮的bug
    function pagingStyleDefined() {
        var $paginationLi = $('.pagination > li');

        if ($paginationLi !== null){
            $paginationLi.each(function () {
                var val = $(this).children('a').text();
                if (val === number) {
                    $(this).addClass("active").siblings().removeClass("active");
                }
            });
        }
    }

    pagingStyleDefined();

    // 校验用户输入页码的合法性
    $('#goPage').click(function () {
        var $gotoPage = $('#gotoPage');
        var gotoPage = $gotoPage.val();
        var reg = /^[1-9]\d*$/;
        if (!reg.test(gotoPage) || parseInt(gotoPage) > parseInt(maxPageNumber)) {
            layer.msg("请输入合法页码！当前输入：" + gotoPage);
            $gotoPage.val("");
            return;
        }

        savePageDataFun($objectTable, gotoPage, size, true);
    });

    /**
     * 监听菜单栏点击事件，用以清除所有的会话级别的数据
     */
    $('#side-menu').click(function () {
        clearPageDataSession();
        // localStorage.clear();
    });
}



