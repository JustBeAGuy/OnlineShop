<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 1/14/15
  Time: 12:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="/WEB-INF/tld/ownlib.tld" prefix="ownlib"%>
<div>
    ACCESS DENIED, Please
    <a href=<ownlib:outConfig config="login"/> >Login</a> <br>
    or <br>
    <a href=<ownlib:outConfig config="register"/> >Register</a>
</div>