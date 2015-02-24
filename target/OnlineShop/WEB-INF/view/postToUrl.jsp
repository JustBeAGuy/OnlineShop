<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tld/ownlib.tld" prefix="ownlib"%>
<html>
<head>
    <script>
        function postToUrl(path, params, method) {
            method = method || "post"; // Устанавливаем метод отправки.

            var form = document.createElement("form");
            form.setAttribute("method", method);
            form.setAttribute("action", path);
            for(var key in params) {
                var hiddenField = document.createElement("input");
                hiddenField.setAttribute("type", "hidden");
                hiddenField.setAttribute("name", key);
                hiddenField.setAttribute("value", params[key]);

                form.appendChild(hiddenField);
            }
            document.body.appendChild(form);
            form.submit();
        };
        document.addEventListener('DOMContentLoaded', function() {
            postToUrl('${url}', {<ownlib:getKeyValueJS/>}, 'POST');
        });
    </script>
</head>
<body>
<%--<a href="#" onclick="postToUrl('${url}', {'urlPOST':'login'}, 'POST');">HELLO</a>--%>
</body>
</html>
