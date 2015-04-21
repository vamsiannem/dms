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
        $("#common-form").attr("action", contextPath+"/api/project");
        $("#common-form").attr("method", "GET");
        $("#common-form").attr("accept", "application/json");
        $("#common-form").submit();
    });
    $("#list_10").click(function() {
        var contextPath = $("#common-form").attr("action");
        $("#common-form").attr("action", contextPath+"/api/project");
        $("#common-form").attr("method", "GET");
        $("#common-form").submit();
    });
    $("#list_20").click(function() {
        var contextPath = $("#common-form").attr("action");
        $("#common-form").attr("action", contextPath+"/api/project/data/import");
        $("#common-form").attr("method", "GET");
        $("#common-form").submit();
    });
    $("#list_21").click(function() {
            var contextPath = $("#common-form").attr("action");
            $("#common-form").attr("action", contextPath+"/api/project/data/export");
            $("#common-form").attr("method", "GET");
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
                $("#common-form").attr("action", contextPath+"/api/project");
                $("#common-form").attr("method", "POST");
                $("#common-form").submit();
        });
    $('#myfile').change(function(){
    		$('#fileName').val($(this).val());
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
var getNodesList = function(projectInfoId, projectId) {
    var request = $.ajax({
      url: "project/"+projectInfoId+"/node.json",
      type: "GET",
      dataType: "json"
    });

    request.done(function( response ) {
      if( typeof response === 'object' && typeof(response.nodes) != 'undefined'){
        var nodes = response.nodes;
        if(nodes.length ==0){
           $("#statusMessage").html("No Data Log available for Project:"+ projectId +", Please sync the data first.")
           return;
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
    $("#common-form").attr("action", contextPath+"/api/project/data/"+ projectInfoId);
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

var reloadAllUnits = function(){
    reqParam = "orderBy=lastModifiedDate";
    var request = $.ajax({
      url: "unit.json",
      type: "GET",
      dataType: "json",
      data: reqParam
    });

    request.done(function( response ) {
      if( response && response.projects !=null){
        $("#orderBy").val("lastModifiedDate");
        $("#list_10").click();
      } else {
        $("#statusMessage").html("An error has occurred while reloading all units, Please try again later.");
      }
    });

    request.fail(function( jqXHR, textStatus ) {
      console.log( "Request failed: " + textStatus );
      $("#statusMessage").html("An error has occurred while reloading all units, Please try again later.");
    });
}


//  Starts Network Drop down fill...
function fillNetworkUnitDropDown(){
    $('#networkUnitSelect').html('');
    $('#networkUnitSelect').append($('<option>', { value: '----',  text: '-- Please Select --' }));
    $.each(projects, function (i, item) {
        var toolTipText = 'Project Id:'+ item.projectId + ',Client:'+ item.companyName+ ',Platform:'+item.platform
                            +',Control System:'+ item.controlSystem+ ',Channel:'+item.channel
                            +',Unit Serial No:'+item.unitSerialNo;
        $('#networkUnitSelect').append($('<option>', {
            value: item.projectInfoId,
            text : item.projectId+'-'+ item.companyName+'-'+ item.platform+ '-' +item.controlSystem+'-'+ item.channel+'-'+ item.unitSerialNo,
            title: toolTipText
        }));
        $('#networkUnitSelect ').change(function(){
            var toolTipValue = $('#networkUnitSelect > option:selected').attr('title');
            var selectedVal = $('#networkUnitSelect > option:selected').val();
            if(toolTipValue+'' != 'undefined'){
                var toolTipDisplayText = toolTipValue.replace(/,/g, "\n");
                showTip(toolTipDisplayText);
                $("#dataStartDate").text(projectsDataRange[selectedVal].fromDate);
                $("#dataEndDate").text(projectsDataRange[selectedVal].toDate);
            } else {
                $("#dataStartDate").text('');
                $("#dataEndDate").text('');
            }
        });
        $('#wrapper').mouseleave(function(){
            hideTip();
        });
    });

}


var showTip = function(toolTipValue){
    /*var tip = document.createElement("pre");
    tip.className = "tooltip";
    tip.id = "tip";
    tip.innerHTML = toolTipValue;*/
    var htmlTip = "<pre id='tip' class='tooltip' style='opacity:0;z-index:10' >"+toolTipValue+"</pre>"
    $("#wrapper").html(htmlTip);
    var tip = $("#tip");
    //tip.style.opacity="0"; // to start with...
    var intId = setInterval(function(){
        newOpacity = parseFloat(tip.css("opacity"))+0.1;
        tip.css("opacity", newOpacity.toString());
        if( parseFloat(tip.css("opacity")) >= 0.8){
            clearInterval(intId);
        }
    }, fadeSpeed);
};
var hideTip = function(){
    var tip = $("#tip");
    if(tip.length){
        var intId = setInterval(function(){
            newOpacity = parseFloat(tip.css('opacity'))-0.1;
            tip.css('opacity', newOpacity.toString())
            if(parseFloat(tip.css("opacity")) <= 0.0){
                clearInterval(intId);
                tip.remove();
            }
        }, fadeSpeed);

    }
};