<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 1/6/15
  Time: 2:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="/WEB-INF/tld/ownlib.tld" prefix="ownlib"%>
<div class="head_account">
    Hello, <a href=<ownlib:outConfig config="account"/> ><ownlib:outLoginFromSession attr="account" /> </a>
</div>
<div class="head_logout">
    <a href=<ownlib:outConfig config="logout"/> >LogOut</a>
</div>
<div id="head_cart">
    <a href=<ownlib:outConfig config="cart"/> >Cart</a>
</div>
