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
    var statusMsg = $("#statusMessage").val();
    if(statusMsg && statusMsg!="-" ){
        var statusMsgSplit = statusMsg.split("-");
        setAlertMessage(statusMsgSplit[1], parseInt(statusMsgSplit[0]), "statusWrapper");
    }

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
           var infoMsg = "No Data Log available for Project:"+ projectId +", Please sync the data first.";
           setAlertMessage(infoMsg, 1, "statusWrapper" )
           return;
        }
        if(nodes.length == 1){
            showNetworkGraph(projectInfoId);
        } else {
            showNetworkImage(projectInfoId);
        }
      } else {
        setAlertMessage("Unexpected problem while fetching details.", 2, "statusWrapper" );
      }
    });

    request.fail(function( jqXHR, textStatus ) {
      console.log( "Request failed: " + textStatus );
      setAlertMessage("Unable to Fetch Nodes for Project:"+ projectInfoId, 3, "statusWrapper" );
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
        setAlertMessage("An error has occurred while reloading all units, Try again.", 3, "statusWrapper" );
      }
    });

    request.fail(function( jqXHR, textStatus ) {
      console.log( "Request failed: " + textStatus );
      setAlertMessage("An error has occurred while reloading all units, Try again.", 2, "statusWrapper" );
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
    });
    $('#wrapper').mouseleave(function(){
            hideTip();
    });
    $('#networkUnitSelect ').change(function(){
        var toolTipValue = $('#networkUnitSelect > option:selected').attr('title');
        var selectedVal = $('#networkUnitSelect > option:selected').val();
        if(toolTipValue && toolTipValue+'' != 'undefined'){
            var toolTipDisplayText = toolTipValue.replace(/,/g, "\n");
            showTip(toolTipDisplayText);
            var projectInfoArray = toolTipValue.split(',');
            setProjectInfo(projectInfoArray);
            if(projectsDataRange && projectsDataRange[selectedVal]){
                $("#dataStartDate").text(projectsDataRange[selectedVal].fromDate);
                $("#dataEndDate").text(projectsDataRange[selectedVal].toDate);
            } else {
                  $("#dataStartDate").text('---');
                  $("#dataEndDate").text('---');
            }
        } else {
            resetProjectInfo();
        }
    });

}

function setProjectInfo(projectInfoArray){
    $.each(projectInfoArray, function (index, item) {
        var itemSplit = item.split(":");
        switch(index){
            case 0:
                $("#info_projectId").text(itemSplit[1]);
                break;
            case 1:
                $("#info_client").text(itemSplit[1]);
                break;
            case 2:
                $("#info_platform").text(itemSplit[1]);
                break;
            case 3:
                $("#info_controlSystem").text(itemSplit[1]);
                break;
            case 4:
                $("#info_channel").text(itemSplit[1]);
                break;
            case 5:
                $("#info_unitSerialNo").text(itemSplit[1]);
                break;
        }
    });
}

function resetProjectInfo(){
    $("#info_projectId").text('None');
    $("#info_client").text('None');
    $("#info_platform").text('None');
    $("#info_controlSystem").text('None');
    $("#info_channel").text('None');
    $("#info_unitSerialNo").text('None');
    $("#dataStartDate").text('None');
    $("#dataEndDate").text('None');
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

function setAlertMessage(textMessage, alertType, wrapperDivId ){
    var htmlContent;
    switch (alertType){
        case 0: // success
             htmlContent = "<div class='alert-box success'><span>success: </span>"+textMessage+"</div>";
             break;
        case 1: // info or notice
             htmlContent = "<div class='alert-box notice'><span>notice: </span>"+textMessage+"</div>";
             break;
        case 2: // warning
             htmlContent = "<div class='alert-box warning'><span>warning: </span>"+textMessage+"</div>";
             break;
        case 3: // error
             htmlContent = "<div class='alert-box error'><span>error: </span>"+textMessage+"</div>";
             break;
        default:
            htmlContent = "<div class='alert-box notice'><span>notice: </span>"+textMessage+"</div>";
            break;
    }
    //$("#"+wrapperDivId).html("");
    $("#"+wrapperDivId).html(htmlContent);
    shakeIt(wrapperDivId);
}

function updateTips( text ) {
    setAlertMessage(text, 3, "statusWrapper");
}
function checkLength( o, n, min, max ) {
    if ( o.val().length > max || o.val().length < min ) {
    o.addClass( "ui-state-error" );
    updateTips( "Length of " + n + " must be between " +
    min + " and " + max + "." );
    return false;
    } else {
    return true;
    }
}
function checkNotEmpty(o, n){
    if($(o).is("select") && $(o).prop('selectedIndex') ==0){
        o.addClass( "ui-state-error" );
        updateTips(n + "cannot be blank.");
        return false;
    } else if( $.trim(o.val()).length == 0 ){
        o.addClass( "ui-state-error" );
        updateTips(n + "cannot be blank.");
        return false;
    }
    return true;
}
function addError(n){
    o.addClass( "ui-state-error" );
    updateTips(n + "cannot be blank.");
    return false;
}
function checkRegexp( o, regexp, n ) {
    if ( !( regexp.test( o.val() ) ) ) {
    o.addClass( "ui-state-error" );
    updateTips( n );
    return false;
    } else {
    return true;
    }
}

function shakeIt(elementId){
    $("#"+elementId).effect("shake", {"distance": 10, "times": 2, "direction": "left"}, 1000);
        /*$("#"+elementId).animate({
            right: -width+'px',
            opacity: 1
        },*/
}