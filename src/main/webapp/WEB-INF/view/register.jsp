<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 1/6/15
  Time: 11:07 PM
  To change this template use File | Settings | File Templates.
--%>
<form method="POST" action="/register">
    Login<input required name="login" value="" placeholder="Login" type="text">
    <br>
    Password<input required name="password" value="" placeholder="Password" type="password">
    Re-Enter Password<input required name="rePassword" value="" placeholder="Re-Enter Password" type="password">
    <br>
    Email<input required name="email" value="" placeholder="E-mail" type="email">
    <br>
    <input name="command" value="register" type="hidden">
    <input type="submit" value="Register">
</form>
