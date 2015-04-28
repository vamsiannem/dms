var prevSelectedRowClientTable;
$(document).ready(function() {

  var table_config = {
    data: projects,
    columns: [
        {data: 'projectInfoId', title: 'ProjectInfo Id', class: 'hide_column'},
        {data: 'projectId', title: 'ProjectID', class: 'center'},
        {data: 'companyName', title: 'Client', class: 'center', "width": "15%"},
        {data: 'platform', title: 'Platform', class: 'center'},
        {data: 'controlSystem', title: 'Control System', class: 'center'},
        {data: 'channel', title: 'Channel', class: 'center'},
        {data: 'ipAddress', title: 'IP Address', class: 'center'},
        {data: 'alive', title: 'Status', class: 'center'}
    ],
    "sort": false
    };
  var table = $('#projects_table').DataTable(table_config);
  applySelectEventForDataTable("projects_table", table);


  var table_config_client_info = {
    data: products,
    columns: [
        {data: 'id', title: 'Product Id', class: 'hide_column'},
        {data: 'partNo', title: 'Part No', class: 'center', "width": "33%"},
        {data: 'unitSerialNo', title: 'Serial No', class: 'center', "width": "33%"},
        {data: 'description', title: 'Description', class: 'center', "width": "33%"}
    ],
    "sort": false,
    "searching": false,
    "info": false,
    "paging": false,
     scrollY: 150,
    "initComplete": function( settings, json ) {
         $('.dataTables_scrollHeadInner').css("width", "100%");
         $('.dataTables_scrollHeadInner').find("table").css({"width":"100%"});
         $('.dataTables_scrollBody').find("table").addClass("cell-border");
    }
  };

  var client_table = $('#client_info_table').DataTable(table_config_client_info);
  applySelectEventForClientInfoTable("client_info_table", client_table)

  $( "#installationDate" ).datepicker({
       defaultDate: "+1w",
       changeMonth: true,
       changeYear: true,
       numberOfMonths: 1,
       dateFormat: "dd/mm/yy"
     });

    fillCompanyDropdown();
    $('#clientId').change(function(){
        $("#clientContact").val("");
        var selectedVal = $('#clientId > option:selected').val();
        $.each(clients, function (i, item) {
            if(item.id == selectedVal){
                $("#clientContact").val(item.contactNo);
                return false;
            }
        });
    });
});
function applySelectEventForClientInfoTable(tableId, table){

    $('#'+tableId+' tbody')
        .on('mouseover', 'tr', function(){
           $(this).addClass('hover_text');
        })
        .on('mouseout', 'tr', function(){
            $(this).removeClass('hover_text');
        })
        .on('click', 'tr', function(){
            if(prevSelectedRowClientTable && prevSelectedRowClientTable!=this){
                $(prevSelectedRowClientTable).removeClass('selected_row');
            }
            if($(this).hasClass('selected_row')){
                $(this).removeClass('selected_row');
                prevSelectedRowClientTable = null;
            } else {
                prevSelectedRowClientTable = this;
                $(this).addClass('selected_row');
            }
        });
}


function applySelectEventForDataTable(tableId, table){
    var lastIdx = null;
    $('#'+tableId+' tbody')
      .on( 'mouseover', 'td', function () {
          var colIdx = table.cell(this).index().column;
          // this is very specific fix for a column
          if(colIdx === 5){
            $(this).addClass('hover_text');
          }
      })
      .on( 'mouseleave','td', function () {
          var colIdx = table.cell(this).index().column;
          if(colIdx === 5){
              $(this).removeClass('hover_text');
          }
      })
      .on('click', 'td', function(){
        var colIdx = table.cell(this).index().column;
        /*
            5th column is the Channel displayed.
            First column is hidden column for Project_Info_Id.
         */
        if(colIdx === 5){
            var rowIdx = table.cell(this).index().row;
            var firstColumnValues = $(table.column(0).nodes());
               var projectInfoId = $(firstColumnValues[rowIdx]).text();
               var projectId = $($(table.column(1).nodes())[rowIdx]).text();
               getNodesList(projectInfoId, projectId);
        }
      })
      .on( 'mouseover', 'tr', function () {
            $(this).addClass('selected');
      })
      .on( 'mouseleave', 'tr', function () {
           $(this).removeClass('selected');
      });


    /*$('#'+tableId).on( 'click', 'tr',
        function () {
            *//*if ( $(this).hasClass('selected') ) {
                $(this).removeClass('selected');
            } else {
                table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }*//*
            var columns = $(this).find('td');
            var projectInfoId = $(columns[0]).text();
            console.log(projectInfoId);
            // getNodesList(projectId);
    });*/


}


 $(function() {
    var form,
    // From http://www.whatwg.org/specs/web-apps/current-work/multipage/states-of-the-type-attribute.html#e-mail-state-%28type=email%29
    //emailRegex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/,
    projectId = $( "#projectId" ),
    client = $( "#clientId" ),
    platform = $( "#platform" ),
    controlSystem = $( "#controlSystem" ),
    channel = $( "#channel" ),
    //ipAddress = $( "#ipAddress" ),
    //unitSerialNo = $( "#unitSerialNo" ),
    installationDate = $( "#installationDate" ),
    consignedEngineer = $("#consignedEngineer"),
    description = $("#description"),
    allFields = $( [] ).add( projectId ).add( client ).add( platform ).add( controlSystem ).add( channel ).add(consignedEngineer).add(description).add(installationDate),
    tips = $( ".validateTips" );

    function createAccount() {
        product = $(prevSelectedRowClientTable).find(".hide_column");
        var valid = true;
        allFields.removeClass( "ui-state-error" );
        valid = valid && checkNotEmpty( projectId, "ProjectId ");
        valid = valid && checkNotEmpty( platform, "Platform ");
        valid = valid && checkNotEmpty( controlSystem, "ControlSystem ");
        valid = valid && checkNotEmpty( channel, "Channel ");
        valid = valid && checkNotEmpty( description, "Description ");
        valid = valid && checkNotEmpty( installationDate, "InstallationDate ");
        valid = valid && checkNotEmpty( consignedEngineer, "ConsignedEngineer ");
        valid = valid && checkNotEmpty( client, "Client ");

        if( valid && product) {
            var selectedProduct = $.trim(product.text());
            if(selectedProduct==""){
                updateTips("Please select a product from the table.");
                setAlertMessage("Please select a product from the table.", 3, "statusWrapper");
                valid = false;
            }
        } else if (valid){
            setAlertMessage("Please select a product from the table." , 3, "statusWrapper");
            valid = false;
        }


        if ( valid ) {
            $("#productId").val(selectedProduct);
            console.log("Valid INput... fire ajax call to persist project information");
            /// ajax call here.
            addNetworkUnit();
        }
        //return valid;
    }
    addNetworkUnit = function(){
        var product = $(prevSelectedRowClientTable).find(".hide_column")
        var reqParam = "clientId="+client.val()+"&platform="+
                        platform.val()+"&controlSystem="+controlSystem.val()+"&channel="+channel.val()+
                        "&installationDate="+ installationDate.val()+"&consignedEngineer="+consignedEngineer.val()
                        +"&description="+ description.val()+"&productId="+$.trim(product.text());
        var request = $.ajax({
          url: "project/"+projectId.val()+".json?"+reqParam,
          type: "PUT",
          dataType: "json"
          //data: reqParam,

        });

        request.done(function( response ) {
          if( response && response.status.indexOf('created successfully') != -1 ){
            setAlertMessage("Project created successfully", 0, "statusWrapper");
            $("#orderBy").val("lastModifiedDate");
            $("#list_10").click();
          } else {
            setAlertMessage("An error has occurred while persisting. Project creation failed for Project ID:"+ projectId.val(), 3, "statusWrapper");
          }
        });

        request.fail(function( jqXHR, textStatus ) {
          console.log( "Request failed: " + textStatus );
          setAlertMessage("Unexpected Error! Unable to Create Project with ProjectId:"+ projectId.val(), 3, "statusWrapper");
          //tips.text("Unexpected Error! Unable to Create Project with ProjectId:"+ projectId.val());
        });
    }
    /*dialog = $( "#dialog-form" ).dialog({
        autoOpen: false,
        height: 450,
        width: 700,
        modal: true,
        buttons: {
        "Create Project": createAccount,
        Cancel: function() {
        dialog.dialog( "close" );
        }
        },
        close: function() {
        form[ 0 ].reset();
        tips.text("All form fields are required.");
        allFields.removeClass( "ui-state-error" );
        }
    });*/
    form = $("#create_project_form").on( "submit", function( event ) {
        event.preventDefault();
        createAccount();
    });
    $( "#cancel_button" ).button().on( "click", function() {
                $('#create_project_form')[0].reset();
        tips.text("All form fields are required.");
        allFields.removeClass( "ui-state-error" );
        $("#new_project_div").hide();
        $("#table_div").show();
        //$("#create_project_button_div").show();

    });
    $( "#create-project" ).button().on( "click", function() {
        $("#new_project_div").show();
        $("#table_div").hide();
        //$("#create_project_button_div").hide();
    });
 });

 function fillCompanyDropdown(){
    $('#clientId').html('');
    $('#clientId').append($('<option>', { value: '----',  text: '-- Please Select --' }));
    $.each(clients, function (i, item) {
        $('#clientId').append($('<option>', {
            value: item.id,
            text : item.name
        }));
    });

 }