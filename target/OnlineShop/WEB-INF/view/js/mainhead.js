/**
 * Created by Администратор on 2/4/15.
 */
function mainhead() {
    hideNotif();
    language();
    menuCatalog();
    setLang();
}

//function mainheadNotify(message, type) {
//    var notif = $("#notif");
//    notif.addClass(type);
//    notif.find("p").text(message);
//}
function menuCatalog() {
//    $(".menu_catalog").fadeOut(0);
    $("#menu_dropdown_button").mouseenter(function() {
        $(".menu_catalog").fadeIn(400);
    });
    $("#menu_catalog").mouseleave(function() {
        $(".menu_catalog").fadeOut(100);
    });
}

function language() {
    $("#head_lang a").click(function() {
        var lang = $(this).attr("name");
        var url = $("#current_url").text();
        postToUrl(url, {"command" : "language",
            "lang" : lang}, "POST");
    });
}

function hideNotif() {
    $(document).click(function() {
        $("#notif").fadeOut();
    })
}

function setLang() {
    var lang = $("#curr_lang").text();
    $("#head_lang a").removeClass("currentLang");
    $("#head_lang a[name=" + lang + "]").addClass("currentLang");
}