<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 1/12/15
  Time: 9:38 PM
  To change this template use File | Settings | File Templates.
--%>
<div>
    Hello, <c:out value="${account.getLogin()}"/>
    <%--<div id="account_id" class="hidden">${account.getId()}</div>--%>
    <form method="POST" action="/account">
        <div class="form-group">
            <label for="account_user_name">Name</label>
            <input required type="text" class="form-control" name="account_user_name" value="${userDescription.getName()}" id="account_user_name" placeholder="Name">
        </div>
        <div class="form-group">
            <label for="account_user_surname">SurName</label>
            <input required type="text" class="form-control" name="account_user_surname" value="${userDescription.getSurname()}" id="account_user_surname" placeholder="SurName">
        </div>
        <div class="form-group">
            <label for="account_user_phone">Phone</label>
            <input required type="tel" class="form-control" name="account_user_phone" value="${userDescription.getPhone()}" id="account_user_phone" placeholder="Phone Number">
        </div>
        <div class="form-group">
            <label for="account_user_city">Town</label>
            <input required type="text" class="form-control" name="account_user_city" value="${userDescription.getCity()}" id="account_user_city" placeholder="Town">
        </div>
        <div class="form-group">
            <label for="account_user_street">Street</label>
            <input required type="text" class="form-control" name="account_user_street" value="${userDescription.getStreet()}" id="account_user_street" placeholder="Street">
        </div>
        <div class="form-group">
            <label for="account_user_house">House</label>
            <input required type="text" class="form-control" name="account_user_house" value="${userDescription.getHouse()}" id="account_user_house" placeholder="House or Flat">
        </div>
        <div class="form-group">
            <label for="account_user_zip">ZIP</label>
            <input required type="number" class="form-control" name="account_user_zip" value="${userDescription.getZip()}" id="account_user_zip" placeholder="ZIP Code" maxlength=5 minlength=5>
        </div>
        <input name="command" value="account" type="hidden">
        <input name="account_user_update" value="true" type="hidden">
        <div class="form-group">
            <label for="account_user_email">E-Mail</label>
            <input readonly type="email" class="form-control" value="${account.getEmail()}" id="account_user_email" placeholder="Enter email">
        </div>
        <div class="form-group">
            <label for="account_user_newpassword">New Password</label>
            <input type="password" class="form-control" name="account_user_newpassword" id="account_user_newpassword" placeholder="New Password">
        </div>
        <div class="form-group">
            <label for="account_user_password_re">Re-Enter Password</label>
            <input type="password" class="form-control" name="account_user_password_re" id="account_user_password_re" placeholder="Re-Enter Password">
        </div>
        <div class="form-group">
            <label for="account_user_password">Current Password</label>
            <input type="password" class="form-control" name="account_user_password" id="account_user_password" placeholder="Current Password">
        </div>
        <button type="submit" class="btn btn-default">Update</button>
    </form>
</div>
