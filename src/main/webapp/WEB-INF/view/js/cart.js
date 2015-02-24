/**
 * Created by Администратор on 1/24/15.
 */
function cart() {
    removeGood();
    cartClear();
    cartPay();
}

function removeGood() {
    $(".remove_from_cart a").click(function() {
        var goodId = $(this).attr("name");
        postToUrl("/cart", {"command" : "cart",
            "cart_good_remove" : goodId}, "POST");
    })
}

function cartClear() {
    $("#cart_clear a").click(function() {
        postToUrl("/cart", {"command" : "cart",
            "cart_clear" : "true"}, "POST");
    })
}

function cartPay() {
    $("#cart_pay a").click(function() {
        postToUrl("/cart", {"command" : "cart",
            "cart_pay" : "true"}, "POST");
    })
}