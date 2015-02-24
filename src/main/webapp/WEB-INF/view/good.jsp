<%@ taglib prefix="ownlib" uri="/WEB-INF/tld/ownlib.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 1/9/15
  Time: 9:09 PM
  To change this template use File | Settings | File Templates.
--%>
<script>
    <%@include file='js/good.js' %>
</script>
<style>
    <%@include file="css/good.css"%>
</style>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <table class="table table-striped table-hover table-bordered">
        <tr>
            <td>
                <div class='good_img'>
                    <img src="${sessionScope.good.getImg()}" >
                </div>
            </td>
            <td>
                <div class='good_description'>
                    <c:out value="${sessionScope.good.getDescription()}"/>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class='good_name'>
                    <c:out value="${sessionScope.good.getName()}"/>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class='good_price'>
                    <c:out value="${sessionScope.good.getPrice()}"/>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class='good_buy'>
                    <div class="good_category" id="${sessionScope.good.getCategory()}"></div>
                    <input name="good_buy_number" value="1" type="number" min="0" max="${sessionScope.good.getAvailability()}">
                    <div class="good_id" id="${sessionScope.good.getId()}">
                        <a href="#">Add to the cart</a>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</div>
