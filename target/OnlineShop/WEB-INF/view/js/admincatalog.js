/**
 * Created by Администратор on 1/27/15.
 */
function admincatalog() {
    sortingClick();
    changePage();
    searchCatalog();
    setSearchVal();
}

function sortingClick() {

    $(".admin_catalog_table_sorting").click(function() {
        var clickElId = $(this).attr("id");
        postToUrl("/account", {"command" : "account",
            "admin_content" : 'admin_catalog',
            "admin_catalog_sorting" : clickElId}, "POST");
    });

}

function setPages(currPage, allPages) {
    var prevPage = $("#admin_catalog_prev_page");
    var nextPage = $("#admin_catalog_next_page");
    var currentPage = $("#admin_catalog_current_page input");
    var lastPage = $("#admin_catalog_last_page");
    var firstPage = $("#admin_catalog_first_page");
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
    $("#admin_catalog_prev_page").click(function() {
        var currPage = $("#admin_catalog_current_page input").val();
        if (currPage > 1) {
            var prevPage = --currPage;
            postToUrl("/account", {"command" : "account",
                "admin_content" : 'admin_catalog',
                "admin_catalog_curr_page" : prevPage}, "POST");
        }
    });

    $("#admin_catalog_next_page").click(function() {
        var currPage = $("#admin_catalog_current_page input").val();
        var lastPage = $("#admin_catalog_last_page").text();
        if (currPage < lastPage) {
            currPage --;
            var nextPage = currPage + 2;
            postToUrl("/account", {"command" : "account",
                "admin_content" : 'admin_catalog',
                "admin_catalog_curr_page" : nextPage}, "POST");
        }
    });

    $(".admin_catalog_pages").click(function() {
        var page = $(this).text();
        postToUrl("/account", {"command" : "account",
            "admin_content" : 'admin_catalog',
            "admin_catalog_curr_page" : page}, "POST");
    });

    $("#admin_catalog_current_page input").keypress(function(e){
        if(e.keyCode==13){
            var currPage = $("#admin_catalog_current_page input").val();
            var lastPage = $("#admin_catalog_last_page").text();
            if ((currPage <= lastPage) && (currPage >= 1)) {
                postToUrl("/account", {"command" : "account",
                    "admin_content" : 'admin_catalog',
                    "admin_catalog_curr_page" : currPage}, "POST");
            }
        }
    });
}

function searchCatalog() {
    $("#admin_catalog_table_head input").keypress(function(e) {
        if(e.keyCode==13){
            var searchCol = $(this).attr("placeholder");
            var value = $(this).val();
            postToUrl("/account", {"command" : "account",
                "admin_content" : 'admin_catalog',
                "admin_catalog_searchCol" : searchCol,
                "admin_catalog_searchVal" : value}, "POST");
        }
    })
}

function setSearchVal() {
    var column = $("#admin_catalog_searchCol").text();
    var value = $("#admin_catalog_searchVal").text();

    $("#admin_catalog_table_head input[placeholder = " + column + "]").val(value);
}

