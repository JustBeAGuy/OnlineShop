<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 1/5/15
  Time: 12:27 AM
  To change this template use File | Settings | File Templates.
--%>
<script>
    <%@include file='js/admin.js' %>
</script>
<div class="row">
    <div id="admin_menu" class="span1">
        <a href="#" ><div id="admin_users" class="admin_menu">USERS</div></a>
        <a href="#" ><div id="admin_catalog" class="admin_menu">CATALOG</div></a>
        <a href="#" ><div id="admin_cart" class="admin_menu">CART</div></a>
        <%--<a href=<ownlib:outConfig config="account/catalog"/> >Catalog</a>--%>
        <%--<a href=<ownlib:outConfig config="account/catalog"/> >Catalog</a>--%>
    </div>
    <div id="admin_content" class="span9">
        <jsp:include page ='${sessionScope.admin_content}' />
    </div>
</div>
</div>
