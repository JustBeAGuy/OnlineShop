<%@ page import="java.util.Locale" %>
<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 12/19/14
  Time: 12:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tld/ownlib.tld" prefix="ownlib"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello! <ownlib:outLoginFromSession attr="account"/></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
    <style>
        <%@include file='css/main.css' %>
        <%@include file='css/bootstrap.css' %>
    </style>
    <script>
        <%@include file='js/jquery-1.10.2.min.js' %>
        <%@include file='js/main.js' %>
    </script>
</head>
<body>
<%--<img src="<%=getServletConfig().getServletContext().getContextPath()%>/img/good_img/1/mA2exy2-reE.jpg" />--%>
    <div class = "head" >
        <jsp:include page ='${sessionScope.head}' />
    </div>
    <div class = "body" >
            <jsp:include page ='${sessionScope.body}' />
    </div>
    <div class = "footer" >
        <%--<jsp:include page ='${footer}' />--%>
    </div>
</body>
</html>
