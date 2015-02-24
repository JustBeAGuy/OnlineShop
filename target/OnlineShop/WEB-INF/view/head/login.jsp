<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 1/6/15
  Time: 2:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="/WEB-INF/tld/ownlib.tld" prefix="ownlib"%>
<div class="head_login">
    <a href=<ownlib:outConfig config="login"/> ><ownlib:textOut text="head_main_login"/></a>
</div>
<div class="head_register">
    <a href=<ownlib:outConfig config="register"/> ><ownlib:textOut text="head_main_register"/></a>
</div>
