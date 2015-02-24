/**
 * Created by Администратор on 2/3/15.
 */

function admincart() {
    changeSelect();
    sortingClick();
    changePage();
    searchCart();
    setSearchVal();
}

function sortingClick() {
    $(".admin_cart_table_sorting").click(function() {
        var clickElId = $(this).attr("id");
        postToUrl("/account", {"command" : "account",
            "admin_content" : 'admin_cart',
            "admin_cart_sorting" : clickElId}, "POST");
    });

}

function setSelected(select, status) {
    $(".cart_status select[name=" + status + "] option[value=" + select + "]").attr('selected', 'selected');
}

function setPages(currPage, allPages) {
    var prevPage = $("#admin_cart_prev_page");
    var nextPage = $("#admin_cart_next_page");
    var currentPage = $("#admin_cart_current_page input");
    var lastPage = $("#admin_cart_last_page");
    var firstPage = $("#admin_cart_first_page");
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

function changeSelect() {
    $(".cart_status select").change(function(){
        var clickUserId = $(this).attr("name");
        var clickUserStat = $(this).find("option:selected").val();
        postToUrl("/account", {"command" : "account",
            "admin_content" : 'admin_cart',
            "admin_cart_status_id" : clickUserId,
            "admin_cart_status" : clickUserStat}, "POST");
    });
}

function changePage() {
    $("#admin_cart_prev_page").click(function() {
        var currPage = $("#admin_cart_current_page input").val();
        if (currPage > 1) {
            var prevPage = --currPage;
            postToUrl("/account", {"command" : "account",
                "admin_content" : 'admin_cart',
                "admin_cart_curr_page" : prevPage}, "POST");
        }
    });

    $("#admin_cart_next_page").click(function() {
        var currPage = $("#admin_cart_current_page input").val();
        var lastPage = $("#admin_cart_last_page").text();
        if (currPage < lastPage) {
            currPage --;
            var nextPage = currPage + 2;
            postToUrl("/account", {"command" : "account",
                "admin_content" : 'admin_cart',
                "admin_cart_curr_page" : nextPage}, "POST");
        }
    });

    $(".admin_cart_pages").click(function() {
        var page = $(this).text();
        postToUrl("/account", {"command" : "account",
            "admin_content" : 'admin_cart',
            "admin_cart_curr_page" : page}, "POST");
    });

    $("#admin_cart_current_page input").keypress(function(e){
        if(e.keyCode==13){
            var currPage = $("#admin_cart_current_page input").val();
            var lastPage = $("#admin_cart_last_page").text();
            if ((currPage <= lastPage) && (currPage >= 1)) {
                postToUrl("/account", {"command" : "account",
                    "admin_content" : 'admin_cart',
                    "admin_cart_curr_page" : currPage}, "POST");
            }
        }
    });
}

function searchCart() {
    $("#admin_cart_table_head input").keypress(function(e) {
        if(e.keyCode==13){
            var searchCol = $(this).attr("placeholder");
            var value = $(this).val();
            postToUrl("/account", {"command" : "account",
                "admin_content" : 'admin_cart',
                "admin_cart_searchCol" : searchCol,
                "admin_cart_searchVal" : value}, "POST");
        }
    })
}

function setSearchVal() {
    var column = $("#admin_cart_searchCol").text();
    var value = $("#admin_cart_searchVal").text();

    $("#admin_cart_table_head input[placeholder = " + column + "]").val(value);
}
