$(document).ready(function() {
    // All page navigation related clicks here
    $("#node_99").click(function(){
        var contextPath = $("#common-form").attr("action");
        $("#common-form").attr("action", contextPath+"/api/home");
        $("#common-form").attr("method", "POST");
        $("#common-form").submit();
    });
    $("#list_of_networks").click(function() {
        var contextPath = $("#common-form").attr("action");
        $("#common-form").attr("action", contextPath+"/api/product");
        $("#common-form").attr("method", "GET");
        $("#common-form").submit();
    });
    $("#list_10").click(function() {
        var contextPath = $("#common-form").attr("action");
        $("#common-form").attr("action", contextPath+"/api/product");
        $("#common-form").attr("method", "GET");
        $("#common-form").submit();
    });
    $("#list_20").click(function() {
        var contextPath = $("#common-form").attr("action");
        $("#common-form").attr("action", contextPath+"/api/product/upload/view");
        $("#common-form").attr("method", "POST");
        $("#common-form").submit();
    });
    $("#list_21").click(function() {
            var contextPath = $("#common-form").attr("action");
            $("#common-form").attr("action", contextPath+"/api/product/upload/view");
            $("#common-form").attr("method", "POST");
            $("#common-form").submit();
    });

     $("#list_30").click(function() {
            var contextPath = $("#common-form").attr("action");
            $("#common-form").attr("action", contextPath+"/api/assets");
            $("#common-form").attr("method", "POST");
            $("#common-form").submit();
    });
     $("#list_31").click(function() {
            var contextPath = $("#common-form").attr("action");
            $("#common-form").attr("action", contextPath+"/api/assets");
            $("#common-form").attr("method", "POST");
            $("#common-form").submit();
    });
     $("#list_31").click(function() {
            var contextPath = $("#common-form").attr("action");
            $("#common-form").attr("action", contextPath+"/api/assets");
            $("#common-form").attr("method", "POST");
            $("#common-form").submit();
    });
});