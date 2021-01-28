<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ include file="/WEB-INF/view/include/base-param.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>控制中心 - ${webname}</title>
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="pragma" CONTENT="no-cache">
    <meta http-equiv="Cache-Control" CONTENT="no-cache, must-revalidate">
    <meta http-equiv="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
    <%@ include file="css.jsp" %>
	<style>
		.sidebar-collapse.menu-scroll.collapse{height:0px!important;}
		.sidebar-collapse.menu-scroll,.sidebar-collapse.menu-scroll.collapse.in{height:100%!important;min-height:100%;overflow-y: auto;overflow-y: auto!important;}
		.sidebar-collapse.menu-scroll::-webkit-scrollbar {
		  width: 12px;
		  height: 12px;
		  background-color: #efefef;
		}
		.sidebar-collapse.menu-scroll::-webkit-scrollbar-thumb {
		  background-color: #1b5d9b;
		  outline: 1px solid #1b5d9b;
		}
	</style>
</head>
<body class="sidebar-default header-fixed pace-done">
	<div>
		<%@ include file="topbar.jsp" %>
        <div id="wrapper">
            <%@ include file="sidebar.jsp" %>
            <div id="page-wrapper">
                <%@ include file="page-title.jsp" %>
                <div class="page-content"></div>
            </div>
            <%@ include file="footer.jsp" %>
        </div>
    </div>
    <%@ include file="js.jsp" %>
</body>
</html>