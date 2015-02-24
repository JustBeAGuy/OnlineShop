/**
 * Created by Администратор on 1/15/15.
 */
function adminUsers() {
    sortingClick();
    changeSelect();
    changePage();
    searchUsers();
    setSearchVal();
}

function sortingClick() {

    $(".admin_users_table_sorting").click(function() {
        var clickElId = $(this).attr("id");
        postToUrl("/account", {"command" : "account",
            "admin_content" : 'admin_users',
            "admin_users_sorting" : clickElId}, "POST");
    });

}

function changeSelect() {
    $(".user_permission select").change(function(){
        var clickUserId = $(this).attr("name");
        var clickUserPer = $(this).find("option:selected").val();
        postToUrl("/account", {"command" : "account",
            "admin_content" : 'admin_users',
            "admin_users_permission_id" : clickUserId,
            "admin_users_permission" : clickUserPer}, "POST");
    });
}

function setSelected(select, userId) {
    $(".user_permission select[name=" + userId + "] option[value=" + select + "]").attr('selected', 'selected');
}

function setPages(currPage, allPages) {
    var prevPage = $("#admin_users_prev_page");
    var nextPage = $("#admin_users_next_page");
    var currentPage = $("#admin_users_current_page input");
    var lastPage = $("#admin_users_last_page");
    var firstPage = $("#admin_users_first_page");
    firstPage.text("1");
    currentPage.attr("value", currPage);
    lastPage.text(allPages);
    currentPage.attr("max", allPages);

    if(currPage < 2) {
        prevPage.addClass("hidden");
        firstPage.addClass("hidden");
        if(currPage > allPages -1) {
            nextPage.addClass("hidden");
            lastPage.addClass("hidden");
        }
    } else {
        if(currPage > allPages -1) {
            nextPage.addClass("hidden");
            lastPage.addClass("hidden");
        } else {
            if(prevPage.is(":hidden") || nextPage.is(":hidden")) {
                nextPage.removeClass("hidden");
                prevPage.removeClass("hidden");
                lastPage.removeClass("hidden");
                firstPage.removeClass("hidden");
            }
        }
    }
}

function changePage() {
    $("#admin_users_prev_page").click(function() {
        var currPage = $("#admin_users_current_page input").val();
        if (currPage > 1) {
            var prevPage = --currPage;
            postToUrl("/account", {"command" : "account",
                "admin_content" : 'admin_users',
                "admin_users_curr_page" : prevPage}, "POST");
        }
    });

    $("#admin_users_next_page").click(function() {
        var currPage = $("#admin_users_current_page input").val();
        var lastPage = $("#admin_users_last_page").text();
        if (currPage < lastPage) {
            currPage --;
            var nextPage = currPage + 2;
            postToUrl("/account", {"command" : "account",
                "admin_content" : 'admin_users',
                "admin_users_curr_page" : nextPage}, "POST");
        }
    });

    $(".admin_users_pages").click(function() {
        var page = $(this).text();
            postToUrl("/account", {"command" : "account",
                "admin_content" : 'admin_users',
                "admin_users_curr_page" : page}, "POST");
    });

    $("#admin_users_current_page input").keypress(function(e){
        if(e.keyCode==13){
            var currPage = $("#admin_users_current_page input").val();
            var lastPage = $("#admin_users_last_page").text();
            if ((currPage <= lastPage) && (currPage >= 1)) {
                postToUrl("/account", {"command" : "account",
                    "admin_content" : 'admin_users',
                    "admin_users_curr_page" : currPage}, "POST");
            }
        }
    });
}

function searchUsers() {
    $("#admin_users_table_head input").keypress(function(e) {
        if(e.keyCode==13){
            var searchCol = $(this).attr("placeholder");
            var value = $(this).val();
            postToUrl("/account", {"command" : "account",
                "admin_content" : 'admin_users',
                "admin_users_searchCol" : searchCol,
                "admin_users_searchVal" : value}, "POST");
        }
    })
}

function setSearchVal() {
    var column = $("#admin_users_searchCol").text();
    var value = $("#admin_users_searchVal").text();

    $("#admin_users_table_head input[placeholder = " + column + "]").val(value);
}