/**
 * Created by Администратор on 1/15/15.
 */
function admin() {
    adminMenuClick(); //clicking menu
}

function adminMenuClick() {

    $(".admin_menu").click(function() {
        var clickElId = $(this).attr("id");
        postToUrl("/account", {"command" : "account",
            "admin_content" : clickElId,
            "reset" : "true"}, "POST");
    });

}