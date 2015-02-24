/**
 * Created by Администратор on 1/23/15.
 */
function goods() {
    addToCart();
}

function addToCart() {
    $(".good_id a").click(function(){
        var goodBuyDiv = $(".good_buy");
        var goodId = goodBuyDiv.find(".good_id").attr("id");
        var goodCategory = goodBuyDiv.find(".good_category").attr("id");
        var number = goodBuyDiv.find("input[name=good_buy_number]").val();
        var url = "/category/"+goodCategory;
        postToUrl(url, {"command" : "category",
            "2" : goodCategory, //First parameter
            "good_buy" : goodId,
            "good_buy_number" : number}, "POST");
    });
}