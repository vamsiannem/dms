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
  $( "#installationDate" ).datepicker({
       defaultDate: "+1w",
       changeMonth: true,
       changeYear: true,
       numberOfMonths: 1,
       dateFormat: "dd/mm/yy"
     });

});


function applySelectEventForDataTable(tableId, table){
    var lastIdx = null;
    $('#'+tableId+' tbody')
      .on( 'mouseover', 'td', function () {
          var colIdx = table.cell(this).index().column;

          if ( colIdx !== lastIdx ) {
              $( table.cells().nodes() ).removeClass( 'highlight' );
              $( table.column( colIdx ).nodes() ).addClass( 'highlight' );
          }
          // this is very specific fix for a column
          if(colIdx === 5){
            $(table.column( colIdx ).nodes()).addClass('hover_text');
          }
      } )
      .on( 'mouseleave','td', function () {
          $( table.cells().nodes() ).removeClass( 'highlight' );
          var colIdx = table.cell(this).index().column;
          if(colIdx === 5){
              $(table.column( colIdx ).nodes()).removeClass('hover_text');
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
            //$.each($(table.column( colIdx ).nodes()), function(index, value){
               var projectInfoId = $(firstColumnValues[rowIdx]).text();
               var projectId = $($(table.column(1).nodes())[rowIdx]).text();
               getNodesList(projectInfoId, projectId);
            //});
        }
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
    var dialog, form,
    // From http://www.whatwg.org/specs/web-apps/current-work/multipage/states-of-the-type-attribute.html#e-mail-state-%28type=email%29
    //emailRegex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/,
    projectId = $( "#projectId" ),
    client = $( "#companyName" ),
    platform = $( "#platform" ),
    controlSystem = $( "#controlSystem" ),
    channel = $( "#channel" ),
    ipAddress = $( "#ipAddress" ),
    unitSerialNo = $( "#unitSerialNo" ),
    installationDate = $( "#installationDate" ),
    consignedEngineer = $("#consignedEngineer"),
    allFields = $( [] ).add( projectId ).add( client ).add( platform ).add( controlSystem ).add( channel ).add( ipAddress ).add(unitSerialNo).add(consignedEngineer),
    tips = $( ".validateTips" );
    function updateTips( t ) {
        tips
        .text( t )
        .addClass( "ui-state-highlight" );
        setTimeout(function() {
        tips.removeClass( "ui-state-highlight", 1500 );
        }, 500 );
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
        if( $.trim(o.val()).length == 0 ){
           o.addClass( "ui-state-error" );
            updateTips(n + "cannot be blank.");
            return false;
        }
        return true;
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
    function createAccount() {
        var valid = true;
        allFields.removeClass( "ui-state-error" );
        valid = valid && checkNotEmpty( projectId, "ProjectId ");
        valid = valid && checkNotEmpty( client, "Client ");
        valid = valid && checkNotEmpty( platform, "Platform ");
        valid = valid && checkNotEmpty( controlSystem, "ControlSystem ");
        valid = valid && checkNotEmpty( channel, "Channel ");
        valid = valid && checkNotEmpty( ipAddress, "IPAddress ");
        valid = valid && checkNotEmpty( unitSerialNo, "UnitSerialNo ");
        valid = valid && checkNotEmpty( installationDate, "InstallationDate ");
        valid = valid && checkNotEmpty( consignedEngineer, "ConsignedEngineer ");


        /*valid = valid && checkRegexp( name, /^[a-z]([0-9a-z_\s])+$/i, "Username may consist of a-z, 0-9, underscores, spaces and must begin with a letter." );
        valid = valid && checkRegexp( email, emailRegex, "eg. ui@jquery.com" );
        valid = valid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );*/
        if ( valid ) {
            console.log("Valid INput... fire ajax call to persist project information");
            /// ajax call here.
            addNetworkUnit();

        }
        return valid;
    }
    addNetworkUnit = function(){
        var reqParam = "companyName="+client.val()+"&platform="+
                        platform.val()+"&controlSystem="+controlSystem.val()+"&channel="+channel.val()+
                        "&ipAddress="+ipAddress.val()+"&unitSerialNo="+unitSerialNo.val()+
                        "&installationDate="+ installationDate.val()+"&consignedEngineer="+consignedEngineer.val();
        var request = $.ajax({
          url: "project/"+projectId.val()+".json?"+reqParam,
          type: "PUT",
          dataType: "json"
          //data: reqParam,

        });

        request.done(function( response ) {
          if( response && response.status=='success' ){
            console.log("Success");
            tips.text("Project created successfully");
            $("#statusMessage").html("Project created successfully");
            dialog.dialog("close");
            //reloadAllUnits();
            $("#orderBy").val("lastModifiedDate");
            $("#list_10").click();
          } else {
            tips.text("An error has occurred while persisting. Project creation failed for Project ID:"+ projectId.val());
          }
        });

        request.fail(function( jqXHR, textStatus ) {
          console.log( "Request failed: " + textStatus );
          tips.text("Unexpected Error! Unable to Create Project with ProjectId:"+ projectId.val());
        });
    }
    dialog = $( "#dialog-form" ).dialog({
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
    });
    form = dialog.find( "form" ).on( "submit", function( event ) {
        event.preventDefault();
        createAccount();
    });
    $( "#create-project" ).button().on( "click", function() {
        dialog.dialog( "open" );
    });
 });