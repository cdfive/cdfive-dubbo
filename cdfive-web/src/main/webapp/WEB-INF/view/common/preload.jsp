<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="/static/plugin/queryloader2/queryloader2.js"></script>
<script type="text/javascript">
$(document).ready(function () {
    $("body").queryLoader2({
        barColor: "#efefef",
        backgroundColor: "#111",
        percentage: true,
        barHeight: 1,
        minimumTime: 200,
        fadeOutTime: 1000,
        onComplete: function() {
            //debugger;
            console.info("queryloader debug");
            // $("#toolbox").next().css("z-index","");
            $("#toolbox").next().remove();
        }
    });
    /*
    new QueryLoader2(document.querySelector("body"), {
        barColor: "#efefef",
        backgroundColor: "#111",
        percentage: true,
        barHeight: 1,
        minimumTime: 200,
        fadeOutTime: 1000,
        onLoadComplete: function() {
            //debugger;
            //$("#toolbox").next().remove();
            $("#qLtempOverlay").remove();
        }
    });*/
});
</script>