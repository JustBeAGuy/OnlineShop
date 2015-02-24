$(document).ready(main);

function main() {
    // means functions, which is equals to names of jsp pages
    try {
        admin();
    }
    catch (e) {
    // Means function not included(not this page)
    }

    try {
        adminUsers();
    }
    catch (e) {

    }

    try {
        category();
    }
    catch (e) {

    }

    try {
        goods();
    }
    catch (e) {

    }

    try {
        cart();
    }
    catch (e) {

    }

    try {
        admincatalog();
    }
    catch (e) {

    }

    try {
        admincart();
    }
    catch (e) {

    }

    try {
        mainhead();
    }
    catch (e) {

    }
}

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
}