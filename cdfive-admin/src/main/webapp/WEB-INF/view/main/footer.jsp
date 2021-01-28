<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<div id="footer">
	<div class="copyright">
	<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy" />
	&copy; cdfive-admin V0.1</div>
</div>