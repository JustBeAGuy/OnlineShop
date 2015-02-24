<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 1/12/15
  Time: 11:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
    <%@include file="js/admincatalog.js"%>
</script>
<style>
    <%@include file="css/admincatalog.css"%>
</style>
<div>
    <h2>CATALOG</h2><br>
    <div class="admin_catalog_add">
        <form method="POST" action="/account" enctype="multipart/form-data">
            Category<input name="admin_catalog_category" value="" type="text"><br>
            Name<input name="admin_catalog_name" value="" type="text"><br>
            Price<input name="admin_catalog_price" value="" min="0" type="number"><br>
            Description<textarea name="admin_catalog_description" wrap="soft"></textarea><br>
            Availability<input name="admin_catalog_availability" value="" min="1" type="number"><br>
            <input type="file" name="file" size="60" /><br>
            <input name="command" value="account" type="hidden">
            <input name="admin_content" value="admin_catalog" type="hidden">
            <input name="admin_catalog_upload" value="new" type="hidden">
            <input type="submit" value="Upload">
        </form>
    </div>
    <table class="table table-striped table-hover table-bordered">
        <caption>Catalog</caption>
        <tr id="admin_catalog_table_head">
            <%--setting serch Column and value to it--%>
            <div id="admin_catalog_searchCol" class="hidden">${sessionScope.admin_catalog_searchCol}</div>
            <div id="admin_catalog_searchVal" class="hidden">${sessionScope.admin_catalog_searchVal}</div>
            <th>
                <input type="number" class="form-control" name="admin_catalog_search_id" value="" placeholder="Id">
                <div id="de_id" class="admin_catalog_table_sorting"> De </div>
                <div id="as_id" class="admin_catalog_table_sorting"> As </div>
            </th>
            <th>
                <input type="text" class="form-control" name="admin_catalog_search_category" value="" placeholder="Category">
                <div id="de_category" class="admin_catalog_table_sorting"> De </div>
                <div id="as_category" class="admin_catalog_table_sorting"> As </div>
            </th>
            <th>
                <input type="text" class="form-control" name="admin_catalog_search_name" value="" placeholder="Name">
                <div id="de_name" class="admin_catalog_table_sorting"> De </div>
                <div id="as_name" class="admin_catalog_table_sorting"> As </div>
            </th>
            <th>
                <input type="number" class="form-control" name="admin_catalog_search_price" value="" placeholder="Price">
                <div id="de_price" class="admin_catalog_table_sorting"> De </div>
                <div id="as_price" class="admin_catalog_table_sorting"> As </div>
            </th>
            <th>
                Description
            </th>
            <th>
                <input type="number" class="form-control" name="admin_catalog_search_availability" value="" placeholder="Availability">
                <div id="de_availability" class="admin_catalog_table_sorting"> De </div>
                <div id="as_availability" class="admin_catalog_table_sorting"> As </div>
            </th>
            <th>
                IMG
            </th>
        </tr>
        <c:forEach var="good" items="${sessionScope.admin_catalog}">
            <form method="POST" action="/account" enctype="multipart/form-data">
                <tr>
                    <td>
                        ${good.getId()}
                    </td>
                    <td>
                        <input name="admin_catalog_category" value="${good.getCategory()}" type="text">
                    </td>
                    <td>
                        <input name="admin_catalog_name" value="${good.getName()}" type="text">
                    </td>
                    <td>
                        <input name="admin_catalog_price" value="${good.getPrice()}" min="0" type="number">
                    </td>
                    <td>
                        <%--<input name="admin_catalog_description" value="${good.getDescription()}" type="text">--%>
                        <textarea name="admin_catalog_description" cols="10" wrap="soft">${good.getDescription()}</textarea>
                    </td>
                    <td>
                        <input name="admin_catalog_availability" value="${good.getAvailability()}" min="1" type="number">
                    </td>
                    <td>
                        <input type="file" name="file" size="60" />
                        <input name="admin_catalog_img" value="${good.getImg()}" type="hidden">
                        <img class="admin_catalog_img" src="/img/good_img/${good.getImg()}">
                    </td>
                    <td>
                        <input type="submit" value="Update">
                        <input type="submit" name="admin_catalog_delete" value="Delete">
                    </td>
                    <input name="command" value="account" type="hidden">
                    <input name="admin_content" value="admin_catalog" type="hidden">
                    <input name="admin_catalog_upload" value="${good.getId()}" type="hidden">
                </tr>
            </form>
        </c:forEach>
    </table>
    <%--pages--%>
    <div id="admin_catalog_pages">
        <div id="admin_catalog_first_page" class="admin_catalog_pages">
        </div>
        <div id="admin_catalog_prev_page">
            <
        </div>
        <div id="admin_catalog_current_page">
            <input name="admin_catalog_current_page" value="" type="number" min="1">
        </div>
        <div id="admin_catalog_next_page">
            >
        </div>
        <div id="admin_catalog_last_page" class="admin_catalog_pages">
        </div>
        <%--call setPages function--%>
        <script>
            setPages(${sessionScope.admin_catalog_curr_page}, ${sessionScope.admin_catalog_number_pages});
        </script>
    </div>
</div>
