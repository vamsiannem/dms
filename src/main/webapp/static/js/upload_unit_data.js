var fadeSpeed = 100; // a value between 1 and 1000 where 1000 will take 10
                        // seconds to fade in and out and 1 will take 0.01 sec.
//var tipMessage = "The content of the tooltip...";

$(document).ready(function() {

    fillNetworkUnitDropDown();

});

function fillNetworkUnitDropDown(){
    $('#networkUnitSelect').html('');
    $('#networkUnitSelect').append($('<option>', { value: '----',  text: '-- Please Select --' }));
    $.each(networkUnits, function (i, item) {
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
            if(toolTipValue+'' != 'undefined'){
                var toolTipDisplayText = toolTipValue.replace(/,/g, "\n");
                showTip(toolTipDisplayText);
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