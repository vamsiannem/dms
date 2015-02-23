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
        $("#common-form").attr("action", contextPath+"/api/unit");
        $("#common-form").attr("method", "GET");
        $("#common-form").attr("accept", "application/json");
        $("#common-form").submit();
    });
    $("#list_10").click(function() {
        var contextPath = $("#common-form").attr("action");
        $("#common-form").attr("action", contextPath+"/api/unit");
        $("#common-form").attr("method", "GET");
        $("#common-form").submit();
    });
    $("#list_20").click(function() {
        var contextPath = $("#common-form").attr("action");
        $("#common-form").attr("action", contextPath+"/api/unit/data/view");
        $("#common-form").attr("method", "POST");
        $("#common-form").submit();
    });
    $("#list_21").click(function() {
            var contextPath = $("#common-form").attr("action");
            $("#common-form").attr("action", contextPath+"/api/unit/data/view");
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
     $("#list_32").click(function() {
            var contextPath = $("#common-form").attr("action");
            $("#common-form").attr("action", contextPath+"/api/assets");
            $("#common-form").attr("method", "POST");
            $("#common-form").submit();
    });
    $("#list_33").click(function() {
                var contextPath = $("#common-form").attr("action");
                $("#common-form").attr("action", contextPath+"/api/unit");
                $("#common-form").attr("method", "POST");
                $("#common-form").submit();
        });
    $('#myfile').change(function(){
    		$('#path').val($(this).val());
    });

});

function appendNewData2Dropdown(dropdownId, dataArray, emptyExistingOptions){
    var dropdown = "#"+dropdownId;
    if(emptyExistingOptions) {
        $(dropdown).empty();
    }
    $.each(dataArray, function (index, item) {
        $(dropdown).append(
            $('<option></option>').val(item).html(item)
        );
    });
}

//var base_url =
var getNodesList = function(projectInfoId) {
    var request = $.ajax({
      url: "unit/"+projectInfoId+"/node.json",
      type: "GET",
      dataType: "json"
    });

    request.done(function( response ) {
      if( typeof response === 'object' && typeof(response.nodes) != 'undefined'){
        var nodes = response.nodes;
        if(nodes.length ==0){
           $("#statusMessage").html("No Data Log available for Project:"+ projectInfoId+", Please sync the data first.")
        }
        if(nodes.length == 1){
            showNetworkGraph(projectInfoId);
        } else {
            showNetworkImage(projectInfoId);
        }
      } else {
        $("#statusMessage").html("Unknown error has occurred.");
      }
    });

    request.fail(function( jqXHR, textStatus ) {
      console.log( "Request failed: " + textStatus );
      $("#statusMessage").html("Unable to Fetch Nodes for Project:"+ projectInfoId)
    });
}

var showNetworkImage = function(projectInfoId){
    var contextPath = $("#common-form").attr("action");
    $("#common-form").attr("action", contextPath+"/api/unit/data/"+ projectInfoId);
    $("#common-form").attr("method", "GET");
    $("#common-form").submit();
}

var showNetworkGraph = function(projectInfoId){
    var contextPath = $("#common-form").attr("action");
    $("#common-form").attr("action", contextPath+"/api/graph");
    $("#common-form").attr("method", "POST");
    $("#frm_projectInfoId").val(projectInfoId)
    $("#common-form").submit();
}