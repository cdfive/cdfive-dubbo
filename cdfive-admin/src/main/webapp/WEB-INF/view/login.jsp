<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/include/base-param.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>登录 - ${webname}</title>
    <meta charset="utf-8">
    <%--<meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests" >--%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="cache-control" content="no-cache">
    <!--Loading bootstrap css-->
    <link type="text/css" rel="stylesheet"
          href="${webroot}/static/madmin/vendors/jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.min.css">
    <link type="text/css" rel="stylesheet"
          href="${webroot}/static/madmin/vendors/font-awesome/css/font-awesome.min.css">
    <link type="text/css" rel="stylesheet"
          href="${webroot}/static/madmin/vendors/bootstrap/css/bootstrap.min.css">
    <!--Loading style vendors-->
    <link type="text/css" rel="stylesheet"
          href="${webroot}/static/madmin/vendors/iCheck/skins/all.css">
    <!--Loading style-->
    <link type="text/css" rel="stylesheet"
          href="${webroot}/static/madmin/css/themes/style1/pink-blue.css"
          class="default-style">
    <link type="text/css" rel="stylesheet"
          href="${webroot}/static/madmin/css/themes/style1/pink-blue.css"
          id="theme-change" class="style-change color-change">
</head>

<body id="signin-page">
<div class="page-form new-login-form">
    <form action="${webroot}/loginProcess" class="form" method="post">
        <div id="msg" class="alert alert-danger" style="display: none;"></div>
        <div class="header-content">
            <h1>登 录</h1>
        </div>
        <div class="body-content">
            <div class="form-group">
                <div class="input-icon left">
                    <i class="fa fa-user"></i> <input type="text" placeholder="用户名"
                                                      name="username" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <div class="input-icon left">
                    <i class="fa fa-key"></i> <input type="password" placeholder="密码"
                                                     name="password" class="form-control">
                </div>
            </div>
            <c:if test="${openMobileCheck}">
                <div class="form-group">
                    <div class="input-icon left">
                        <i class="fa fa-mobile"></i> <input type="text" placeholder="手机号"
                                                            name="j_mobile" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-icon left">
                        <i class="fa fa-unlock"></i>
                        <input type="text" placeholder="短信验证码" name="j_smsCode" size="18" style="border: 0px solid ;">
                        <input type="button" value="发送验证码" id="smsCode" onclick="sendSmsCode();"
                               style="color: #0e0e0e;" class="btn new-login-btn"/>
                    </div>
                </div>
            </c:if>
            <div class="form-group">
                <div class="input-icon left">
                    <i class="fa fa-unlock"></i>
                    <input type="text" placeholder="图形验证码" name="verifyCode" size="18" style="border: 0px solid ;">
                    <img id="imgCode" src="${webroot}/loginImageCheckCode" title="验证码的有效期为60s">
                </div>
            </div>
            <div class="form-group">
                <div class="checkbox-list new-login-back">
                    <label> <input name="rememberMe" value="true" type="checkbox">&nbsp;记住密码 </label>
                </div>
            </div>
            <div class="form-group new-login-btn">
                <button type="submit" class="btn btn-success">登&nbsp;&nbsp;录</button>
            </div>
            <%--<div class="clearfix"></div>--%>
            <%--<div id="msg" class="alert alert-danger" style="display: none;"></div>--%>
            <!-- <div class="forget-password">
            <h4>忘记密码？</h4>
            <p>
                点击 <a href='#' class='btn-forgot-pwd'>这里</a> 找回你的密码。
            </p>
        </div>
        <hr>
        <p>
            没有账号？现在就 <a id="btn-register" href="extra-signup.html">注册一个 &gt;</a>
        </p> -->
        </div>
    </form>
</div>
<script src="${webroot}/static/madmin/js/jquery-1.10.2.min.js"></script>
<script src="${webroot}/static/madmin/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${webroot}/static/madmin/js/jquery-ui.js"></script>
<!--loading bootstrap js-->
<script src="${webroot}/static/madmin/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="${webroot}/static/madmin/js/html5shiv.js"></script>
<script src="${webroot}/static/madmin/js/respond.min.js"></script>
<script src="${webroot}/static/madmin/vendors/iCheck/icheck.min.js"></script>
<script src="${webroot}/static/madmin/vendors/iCheck/custom.min.js"></script>
<script src="${webroot}/static/js/jquerySession.js"></script>
<script>
    //BEGIN CHECKBOX & RADIO
    $('input[type="checkbox"]').iCheck({
        checkboxClass: 'icheckbox_minimal-grey',
        increaseArea: '20%' // optional
    });
    $('input[type="radio"]').iCheck({
        radioClass: 'iradio_minimal-grey',
        increaseArea: '20%' // optional
    });
    //END CHECKBOX & RADIO
    var webroot = '${webroot}';
    var openMobileCheck = '${openMobileCheck}';
    var enableCodeTest = '${enableCodeTest}';
    $("form").submit(function () {
        $.session.remove('audit.signCode');
        var btn = $("form button");
        var cls = btn.attr("class");
        if (cls == "btn btn-success disabled")
            return false;

        btn.attr("class", "btn btn-success disabled");
        btn.text("提交中...");

        var name = $("form input[name=j_username]").val();
        var pwd = $("form input[name=j_password]").val();
        var verifyCode = $('form input[name=verifyCode]').val();
        if (name == "")
            return loginShowErrorAndReset("用户名不能为空");
        if (pwd == "")
            return loginShowErrorAndReset("密码不能为空");
        if ((openMobileCheck == 'true' || openMobileCheck) && (enableCodeTest == 'false' || !enableCodeTest)) {
            var mobile = $('form input[name=j_mobile]').val();
            var smsCode = $('form input[name=j_smsCode]').val();
            if (mobile == "") {
                return loginShowErrorAndReset("手机号不能为空");
            }
            if (smsCode == "") {
                return loginShowErrorAndReset("短信验证码不能为空");
            }
        }
        if (verifyCode == "") {
            return loginShowErrorAndReset("图形验证码不能为空");
        }

        /* $.ajax({
         type: "POST",
         url: "login.do",
         data: $("form").serialize(),
         dataType: 'json',
         success: function(data)
         {
         if(data.status==1)
         {
         window.location.href=webroot+data.url;
         }
         else
         {
         loginShowErrorAndReset(data.message);
         }
         },
         error:function (xmlHttp, textStatus, errorThrown)
         {
         loginShowErrorAndReset("网络错误，登录失败，code="+xmlHttp.status);
         }
         });

         return false; */
    });

    $("form input").focus(function () {
        $("#msg").hide();
    });

    function loginShowErrorAndReset(msg) {
        $("#msg").text(msg);
        $("#msg").show();

        var btn = $("form button");
        btn.attr("class", "btn btn-success");
        btn.html('登 &nbsp;&nbsp;录');

        return false;
    }

    //Ajax验证码局部刷新
    $("#imgCode").click(function () {
        document.getElementById("imgCode").src = "${webroot}/loginImageCheckCode?id=" + Math.random();
    });

    $(document).ready(function () {
        var domsg = "${msg}";
        var doerror = "${error}";
        if (domsg != null && domsg != "" && domsg != "undefined") {
            loginShowErrorAndReset(domsg);
        }
        if (doerror != null && doerror != "" && doerror != "undefined") {
            loginShowErrorAndReset(doerror);
        }
    });

    var interval;
    var int = 60;

    function sendSmsCode() {
        var mobile = $('form input[name=j_mobile]').val();
        var name = $("form input[name=j_username]").val();
        var pwd = $("form input[name=j_password]").val();
        if (name == "")
            return loginShowErrorAndReset("用户名不能为空");
        if (pwd == "")
            return loginShowErrorAndReset("密码不能为空");
        if (mobile == "") {
            return loginShowErrorAndReset("手机号不能为空");
        }
        $("#smsCode").attr("disabled", "disabled");
        interval = window.setInterval("countdown()", 1000);
        $.post("${webroot}/static/verify/sendSmsCode.do", {
            j_mobile: mobile,
            j_username: name,
            j_password: pwd
        }, function (data) {
            if (data && data.code == 0) {
                $("#msg").text("发送成功！").show();
            } else {
                $("#msg").text(data.msg).show();
                if (data.data && data.data != '') {
                    int = parseInt(data.data);
                } else {
                    interval = window.clearInterval(interval);
                    $("#smsCode").removeAttr("disabled");
                }
            }
        });
    }

    function countdown() {
        var msg = int + "秒后重新发送";
        $("#smsCode").val(msg);
        int--;
        if (int < 0) {
            interval = window.clearInterval(interval)//结束
            int = 60; //重新赋值
            $("#smsCode").removeAttr("disabled").val("发送验证码");
        }
        return interval;
    }
</script>
</body>
</html>
