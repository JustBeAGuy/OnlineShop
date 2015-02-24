<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 1/12/15
  Time: 4:31 PM
  To change tdis template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    <%@include file="js/cart.js"%>
</script>
<style>
    <%@include file="css/cart.css"%>
</style>
<div class="cart">
    <div id="cart_clear">
        <H1>
            <div><a href="#">Clear</a></div>
        </H1>
    </div>
    <div id="cart_pay">
        <H1>
            <div><a href="#">ORDER</a></div>
            <div id="cart_pay_amount">${sessionScope.amountToPay}$</div>
        </H1>
    </div>
    <table class="table table-striped table-hover table-bordered">
        <tr id="category_table_head">
            <%--img--%>
            <th>
            </th>
            <th>
                Name
            </th>
            <th>
                Price
            </th>
            <th>
                Good ID
            </th>
            <th>
                Remove From Cart
            </th>
        </tr>
        <c:forEach var="good" items="${sessionScope.cart}">
            <tr>
                <td>
                    <div class='good_img'>
                        <img src="${good.getImg()}" >
                    </div>
                </td>
                <td>
                    <div class='good_name'>
                        <c:out value="${good.getName()}"/>
                    </div>
                </td>
                <td>
                    <div class='good_price'>
                        <c:out value="${good.getPrice()}"/>
                    </div>
                    <%--Calculating Amount--%>
                    <script>
                        payAmount(${good.getPrice()});
                    </script>
                </td>
                <td>
                    <div class='good_buy'>
                        <c:out value="${good.getId()}"/>
                    </div>
                </td>
                <td>
                    <div class="remove_from_cart">
                        <a href="#" name="${good.getId()}">Remove</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
