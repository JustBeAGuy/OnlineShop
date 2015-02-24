<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 1/7/15
  Time: 10:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="/WEB-INF/tld/ownlib.tld" prefix="ownlib"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    <%@include file='css/category.css' %>
</style>
<script>
    <%@include file='js/category.js' %>
</script>
<div class="filter">

</div>
<div>
    <table class="table table-striped table-hover table-bordered">
        <%--<caption></caption>--%>
        <tr id="category_table_head">
            <%--setting serch Column and value to it--%>
            <div id="category_searchCol" class="hidden">${sessionScope.category_searchCol}</div>
            <div id="category_searchVal" class="hidden">${sessionScope.category_searchVal}</div>
            <%--NEVER CHANGE ID WHICH HAS CLASS category_table_sorting--%>
            <th>
            </th>
            <th>
                <input type="text" class="form-control" name="category_search_name" value="" placeholder="Name">
                <div id="de_name" class="category_table_sorting"> De </div>
                <div id="as_name" class="category_table_sorting"> As </div>
            </th>
            <th>
                Price $
                <div id="de_price" class="category_table_sorting"> De </div>
                <div id="as_price" class="category_table_sorting"> As </div>
            </th>
            <th>
            </th>
        </tr>
        <c:forEach var="good" items="${sessionScope.category}">
        <tr>
            <div class='good' name="${good.getId()}">
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
                </td>
                <td>
                    <div class='good_page'>
                        <a href="/category/${good.getCategory()}/${good.getId()}">View</a>
                        <div class="good_category hidden">${good.getCategory()}</div> <%--setting Category, dont touch--%>
                    </div>
                </td>
            </div>
        </tr>
        </c:forEach>
    </table>
    <div id="category_pages">
        <div id="category_first_page" class="category_pages">
        </div>
        <div id="category_prev_page">
            Prev
        </div>
        <div id="category_current_page">
            <input name="category_current_page" value="" type="number" min="1">
        </div>
        <div id="category_next_page">
            Next
        </div>
        <div id="category_last_page" class="category_pages">
        </div>
        <%--call setPages function--%>
        <script>
            setPages(${sessionScope.category_curr_page}, ${sessionScope.category_number_pages});
        </script>
    </div>
</div>