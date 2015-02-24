<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 1/12/15
  Time: 11:22 PM
  To change this template use File | Settings | File Templates.
--%>
<script>
    <%@include file="js/admincart.js"%>
</script>
<style>
    <%@include file="css/admincart.css"%>
</style>
<div>
    <table class="table table-striped table-hover table-bordered">
        <caption>Cart</caption>
        <tr id="admin_cart_table_head">
            <%--setting serch Column and value to it--%>
            <div id="admin_cart_searchCol" class="hidden">${sessionScope.admin_cart_searchCol}</div>
            <div id="admin_cart_searchVal" class="hidden">${sessionScope.admin_cart_searchVal}</div>
            <th>
                <input type="number" class="form-control" name="admin_cart_search_id" value="" placeholder="Id">
                <div id="de_id" class="admin_cart_table_sorting"> De </div>
                <div id="as_id" class="admin_cart_table_sorting"> As </div>
            </th>
            <th>
                <input type="text" class="form-control" name="admin_cart_search_userid" value="" placeholder="UserId">
                <div id="de_userid" class="admin_cart_table_sorting"> De </div>
                <div id="as_userid" class="admin_cart_table_sorting"> As </div>
            </th>
            <th>
                <input type="text" class="form-control" name="admin_cart_search_good" value="" placeholder="Good">
                <div id="de_good" class="admin_cart_table_sorting"> De </div>
                <div id="as_good" class="admin_cart_table_sorting"> As </div>
            </th>
            <th>
                <input type="number" max="3" min="0" class="form-control" name="admin_cart_search_status" value="" placeholder="Status">
                <div id="de_status" class="admin_cart_table_sorting"> De </div>
                <div id="as_status" class="admin_cart_table_sorting"> As </div>
            </th>
            <th>
                <input type="text" class="form-control" name="admin_cart_search_timechange" value="" placeholder="TimeChange">
                <div id="de_timechange" class="admin_cart_table_sorting"> De </div>
                <div id="as_timechange" class="admin_cart_table_sorting"> As </div>
            </th>
        </tr>
        <c:forEach var="cart" items="${sessionScope.admin_cart}">
            <tr>
                <td>
                    ${cart.getId()}
                </td>
                <td>
                    ${cart.getUser()}
                </td>
                <td>
                    ${cart.getGood()}
                </td>
                <td class="cart_status">
                    <select name="${cart.getId()}">
                        <option value="0">Cart</option>
                        <option value="1">UnPaid</option>
                        <option value="2">Paid</option>
                        <option value="3">Delivered</option>
                    </select>
                    <%--call the select function to choose selected to cart_status--%>
                    <script>
                        setSelected(${cart.getStatus()}, ${cart.getId()});
                    </script>
                </td>
                <td>
                    ${cart.getTime()}
                </td>
            </tr>
        </c:forEach>
    </table>
    <div id="admin_cart_pages">
        <div id="admin_cart_first_page" class="admin_cart_pages">
        </div>
        <div id="admin_cart_prev_page">
            <
        </div>
        <div id="admin_cart_current_page">
            <input name="admin_cart_current_page" value="" type="number" min="1">
        </div>
        <div id="admin_cart_next_page">
            >
        </div>
        <div id="admin_cart_last_page" class="admin_cart_pages">
        </div>
        <%--call setPages function--%>
        <script>
            setPages(${sessionScope.admin_cart_curr_page}, ${sessionScope.admin_cart_number_pages});
        </script>
    </div>
</div>
