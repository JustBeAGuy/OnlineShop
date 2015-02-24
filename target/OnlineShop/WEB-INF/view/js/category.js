/**
 * Created by Администратор on 1/22/15.
 */
function category() {
    sortingClick();
    changePage();
    searchCategory();
    setSearchVal();
}

function sortingClick() {
    $(".category_table_sorting").click(function() {
        var clickElId = $(this).attr("id");
        var category = $(".good_category").first().text();
        var url = "/category/" + category;
        postToUrl(url, {"command" : "category",
            "2" : category,
            "category_sorting" : clickElId}, "POST");
    });
}

function setPages(currPage, allPages) {
    var prevPage = $("#category_prev_page");
    var nextPage = $("#category_next_page");
    var currentPage = $("#category_current_page input");
    var lastPage = $("#category_last_page");
    var firstPage = $("#category_first_page");
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
    $("#category_prev_page").click(function() {
        var currPage = $("#category_current_page input").val();
        if (currPage > 1) {
            var prevPage = --currPage;
            var category = $(".good_category").first().text();
            var url = "/category/" + category;
            postToUrl(url, {"command" : "category",
                "2" : category,
                "category_curr_page" : prevPage}, "POST");
        }
    });

    $("#category_next_page").click(function() {
        var currPage = $("#category_current_page input").val();
        var lastPage = $("#category_last_page").text();
        if (currPage < lastPage) {
            currPage --;
            var nextPage = currPage + 2;
            var category = $(".good_category").first().text();
            var url = "/category/" + category;
            postToUrl(url, {"command" : "category",
                "2" : category,
                "category_curr_page" : nextPage}, "POST");
        }
    });

    $(".category_pages").click(function() {
        var page = $(this).text();
        var category = $(".good_category").first().text();
        var url = "/category/" + category;
        postToUrl(url, {"command" : "category",
            "2" : category,
            "category_curr_page" : page}, "POST");
    });

    $("#category_current_page input").keypress(function(e){
        if(e.keyCode==13){
            var currPage = $("#category_current_page input").val();
            var lastPage = $("#category_last_page").text();
            var category = $(".good_category").first().text();
            var url = "/category/" + category;
            if ((currPage <= lastPage) && (currPage >= 1)) {
                postToUrl(url, {"command" : "category",
                    "2" : category,
                    "category_curr_page" : currPage}, "POST");
            }
        }
    });
}

function searchCategory() {
    $("#category_table_head input").keypress(function(e) {
        if(e.keyCode==13){
            var searchCol = $(this).attr("placeholder");
            var value = $(this).val();
            var url = $("#current_url").text();
            var category = url.split("/category/");
            postToUrl(url, {"command" : "category",
                "2" : category[1],
                "category_searchCol" : searchCol,
                "category_searchVal" : value}, "POST");
        }
    })
}

function setSearchVal() {
    var column = $("#category_searchCol").text();
    var value = $("#category_searchVal").text();

    $("#category_table_head input[placeholder = " + column + "]").val(value);
}