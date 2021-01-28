<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- 从 spring security获取 -->
<sec:authentication property="principal.menus" var="menus"/>
<c:forEach var="menu" items="${menus}">
	<li>
<%--		<c:if test="${menu.linkType == 'main_content'}">--%>
		<a href="${menu.url}">
			<i class="${menu.icon} fa-fw"></i>
			<span class="menu-title"><c:out value="${menu.name}"/></span>
			<c:if test="${menu.subMenus!=null && fn:length(menu.subMenus) > 0}">
				<span class="fa arrow"></span>
			</c:if>
		</a>
<%--		</c:if>--%>
<%--		<c:if test="${menu.linkType != 'main_content'}">--%>
<%--		<a href="/new-bbc/#${menu.url}">--%>
<%--			<i class="${menu.icon} fa-fw"></i>--%>
<%--			<span class="menu-title"><c:out value="${menu.name}"/></span>--%>
<%--			<c:if test="${menu.subMenus!=null && fn:length(menu.subMenus) > 0}">--%>
<%--				<span class="fa arrow"></span>--%>
<%--			</c:if>--%>
<%--		</a>--%>
<%--		</c:if>--%>
		<c:if test="${menu.subMenus!=null && fn:length(menu.subMenus) > 0}">
			<ul class="nav nav-second-level">
				<c:forEach var="menu2" items="${menu.subMenus}">
					<li>
<%--						<c:if test="${menu2.linkType == 'main_content'}">--%>
						<a href="${menu2.url}">
							<i class="${menu2.icon}"></i>
							<span class="submenu-title"><c:out value="${menu2.name}"/></span>
							<c:if test="${menu2.subMenus!=null && fn:length(menu2.subMenus) > 0}">
								<span class="fa arrow"></span>
							</c:if>
						</a>
<%--						</c:if>--%>
<%--						<c:if test="${menu2.linkType != 'main_content'}">--%>
<%--						<a href="/new-bbc/#${menu2.url}">--%>
<%--							<i class="${menu2.icon}"></i>--%>
<%--							<span class="submenu-title"><c:out value="${menu2.name}"/></span>--%>
<%--							<c:if test="${menu2.subMenus!=null && fn:length(menu2.subMenus) > 0}">--%>
<%--								<span class="fa arrow"></span>--%>
<%--							</c:if>--%>
<%--						</a>--%>
<%--						</c:if>--%>
						<c:if test="${menu2.subMenus!=null && fn:length(menu2.subMenus) > 0}">
							<ul class="nav nav-third-level">
								<c:forEach var="menu3" items="${menu2.subMenus}">
									<li>
<%--										<c:if test="${menu3.linkType == 'main_content'}">--%>
											<a href="${menu3.url}">
												<i class="${menu3.icon}"></i>
												<span class="submenu-title"><c:out value="${menu3.name}"/></span>
												<c:if test="${menu3.label != null}">
													<span class="label label-${menu3.label.color}"><c:out value="${menu3.label.name}"/></span>
												</c:if>
											</a>
<%--										</c:if>--%>
<%--										<c:if test="${menu3.linkType != 'main_content'}">--%>
<%--											<a ${menu3.target} href="/new-bbc/#${menu3.url}">--%>
<%--												<i class="${menu3.icon}"></i>--%>
<%--												<span class="submenu-title"><c:out value="${menu3.text}"/></span>--%>
<%--												<c:if test="${menu3.label != null}">--%>
<%--													<span class="label label-${menu3.label.color}"><c:out value="${menu3.label.text}"/></span>--%>
<%--												</c:if>--%>
<%--											</a>--%>
<%--										</c:if>--%>
									</li>
								</c:forEach>
							</ul>
						</c:if>
					</li>
				</c:forEach>
			</ul>
		</c:if>
	</li>
</c:forEach>

<%-- <c:forEach var="demomenu" items="${demomenus}">
	<li>
		<a ${demomenu.target} href="${demomenu.url}">
			<i class="${demomenu.icon} fa-fw"></i>
			<span class="menu-title"><c:out value="${demomenu.text}"/></span>
			<c:if test="${demomenu.subMenus!=null && fn:length(demomenu.subMenus) > 0}">
				<span class="fa arrow"></span>
			</c:if>
			<c:if test="${demomenu.label != null}">
				<span class="label label-${demomenu.label.color}"><c:out value="${demomenu.label.text}"/></span>
			</c:if>
		</a>
		<c:if test="${demomenu.subMenus!=null && fn:length(demomenu.subMenus) > 0}">
			<ul class="nav nav-second-level">
				<c:forEach var="demomenu2" items="${demomenu.subMenus}">
					<li>
						<a ${demomenu2.target} href="${demomenu2.url}">
							<i class="${demomenu2.icon}"></i>
							<span class="submenu-title"><c:out value="${demomenu2.text}"/></span>
							<c:if test="${demomenu2.subMenus!=null && fn:length(demomenu2.subMenus) > 0}">
								<span class="fa arrow"></span>
							</c:if>
							<c:if test="${demomenu2.label != null}">
								<span class="label label-${demomenu2.label.color}"><c:out value="${demomenu2.label.text}"/></span>
							</c:if>
						</a>
						<c:if test="${demomenu2.subMenus!=null && fn:length(demomenu2.subMenus) > 0}">
							<ul class="nav nav-third-level">
								<c:forEach var="demomenu3" items="${demomenu2.subMenus}">
									<li>
										<a ${demomenu3.target} href="${demomenu3.url}">
											<i class="${demomenu3.icon}"></i>
											<span class="submenu-title"><c:out value="${demomenu3.text}"/></span>
											<c:if test="${demomenu3.label != null}">
												<span class="label label-${demomenu3.label.color}"><c:out value="${demomenu3.label.text}"/></span>
											</c:if>
										</a>
									</li>
								</c:forEach>
							</ul>
						</c:if>
					</li>
				</c:forEach>
			</ul>
		</c:if>
	</li>
</c:forEach>
 --%>