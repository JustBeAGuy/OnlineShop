<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 1/12/15
  Time: 11:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    <%@include file="css/adminusers.css"%>
</style>
<script>
    <%@include file="js/adminusers.js"%>
</script>
<div>
    <table class="table table-striped table-hover table-bordered">
        <caption>Users</caption>
        <tr id="admin_users_table_head">
            <%--setting serch Column and value to it--%>
            <div id="admin_users_searchCol" class="hidden">${sessionScope.admin_users_searchCol}</div>
            <div id="admin_users_searchVal" class="hidden">${sessionScope.admin_users_searchVal}</div>
            <%--NEVER CHANGE ID WHICH HAS CLASS admin_users_table_sorting--%>
            <th>
                <input type="number" class="form-control" name="admin_users_search_id" value="" placeholder="Id">
                <div id="de_id" class="admin_users_table_sorting"> De </div>
                <div id="as_id" class="admin_users_table_sorting"> As </div>
            </th>
            <th>
                <input type="text" class="form-control" name="admin_users_search_login" value="" placeholder="Login">
                <div id="de_login" class="admin_users_table_sorting"> De </div>
                <div id="as_login" class="admin_users_table_sorting"> As </div>
            </th>
            <th>
                Password
                <div id="de_password" class="admin_users_table_sorting"> De </div>
                <div id="as_password" class="admin_users_table_sorting"> As </div>
            </th>
            <th>
                Permission
                <div id="de_permission" class="admin_users_table_sorting"> De </div>
                <div id="as_permission" class="admin_users_table_sorting"> As </div>
            </th>
        </tr>
        <c:forEach var="user" items="${sessionScope.admin_users}">
            <tr class='user'>
                <td class='user_id'>
                    <c:out value="${user.getId()}"/>
                </td>
                <td class='user_login'>
                    <c:out value="${user.getLogin()}"/>
                </td>
                <td class='user_password'>
                    <c:out value="${user.getPassword()}"/>
                </td>
                <td class='user_permission'>
                    <select name="${user.getId()}">
                        <option value="0">BlackList</option>
                        <option value="1">User</option>
                        <option value="2">Admin</option>
                    </select>
                    <%--call the select function to choose selected to user_permission--%>
                    <script>
                        setSelected(${user.getPermission()}, ${user.getId()});
                    </script>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div id="admin_users_pages">
        <div id="admin_users_first_page" class="admin_users_pages">
        </div>
        <div id="admin_users_prev_page">
            <
        </div>
        <div id="admin_users_current_page">
            <input name="admin_users_current_page" value="" type="number" min="1">
        </div>
        <div id="admin_users_next_page">
            >
        </div>
        <div id="admin_users_last_page" class="admin_users_pages">
        </div>
        <%--call setPages function--%>
        <script>
            setPages(${sessionScope.admin_users_curr_page}, ${sessionScope.admin_users_number_pages});
        </script>
    </div>
</div>
