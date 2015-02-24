<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 1/6/15
  Time: 2:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tld/ownlib.tld" prefix="ownlib"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    <%@include file='../css/mainhead.css' %>
</style>
<script>
    <%@include file='../js/mainhead.js' %>
</script>
<div class="head_main">
    <div id="current_url" class="hidden">${requestScope['javax.servlet.forward.request_uri']}</div>
    <div class="head_left">
    </div>
    <div class="head_right">
        <jsp:include page ='${sessionScope.headRight}' />
        <div id="head_lang">
            <a id="lang_en" href="#" name="en" >EN</a>
            <a id="lang_ru" href="#" name="ru" >RU</a>
            <div id="curr_lang" class="hidden">${sessionScope.locale.getLanguage()}</div>
        </div>
    </div>
    <div class="menu_head">
        <div id="menu_catalog">
        <a id="menu_dropdown_button" href="#">Catalog</a>
            <ul class="menu_catalog">
                <li>
                    <a class="menu_catalog" href=<ownlib:outConfig config="category/cell-phones"/> >Cell-Phones</a>
                </li>
                <li>
                    <a class="menu_catalog" href=<ownlib:outConfig config="category/cell-phones"/> >Cell-Phones</a>
                </li>
                <li>
                    <a class="menu_catalog" href=<ownlib:outConfig config="category/cell-phones"/> >Cell-Phones</a>
                </li>
            </ul>
        </div>
    </div>
    <%--notify if notify variable exists--%>
    <c:if test="${sessionScope.notify != null}">
        <div id="notif" class="${sessionScope.notify.getType()}">
            <p>${sessionScope.notify.getMessage()}</p>
        </div>
    </c:if>
</div>
