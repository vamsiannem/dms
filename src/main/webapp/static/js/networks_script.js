$(document).ready(function() {

  var table_config = {
               data: networkUnits,
               columns: [
                   {data: 'projectInfoId', title: 'ProjectInfo Id', class: 'hide_column'},
                   {data: 'projectId', title: 'ProjectID', class: 'center'},
                   {data: 'companyName', title: 'Client', class: 'center', "width": "15%"},
                   {data: 'platform', title: 'Platform', class: 'center'},
                   {data: 'controlSystem', title: 'Control System', class: 'center'},
                   {data: 'channel', title: 'Channel', class: 'center'},
                   {data: 'ipAddress', title: 'IP Address', class: 'center'},
                   {data: 'alive', title: 'Status', class: 'center'}
                   ]
               };
  var table = $('#network_unit_table').DataTable(table_config);
  applySelectEventForDataTable("network_unit_table", table);

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
            var firstColumnValues = $(table.column(0).nodes());
            $.each($(table.column( colIdx ).nodes()), function(index, value){
               var projectInfoId = $(firstColumnValues[index]).text();
               getNodesList(projectInfoId);
            });
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
    emailRegex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/,
    name = $( "#name" ),
    email = $( "#email" ),
    password = $( "#password" ),
    allFields = $( [] ).add( name ).add( email ).add( password ),
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
    function checkRegexp( o, regexp, n ) {
        if ( !( regexp.test( o.val() ) ) ) {
        o.addClass( "ui-state-error" );
        updateTips( n );
        return false;
        } else {
        return true;
        }
    }
    function addUser() {
        var valid = true;
        allFields.removeClass( "ui-state-error" );
        valid = valid && checkLength( name, "username", 3, 16 );
        valid = valid && checkLength( email, "email", 6, 80 );
        valid = valid && checkLength( password, "password", 5, 16 );
        valid = valid && checkRegexp( name, /^[a-z]([0-9a-z_\s])+$/i, "Username may consist of a-z, 0-9, underscores, spaces and must begin with a letter." );
        valid = valid && checkRegexp( email, emailRegex, "eg. ui@jquery.com" );
        valid = valid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );
        if ( valid ) {
        $( "#users tbody" ).append( "<tr>" +
        "<td>" + name.val() + "</td>" +
        "<td>" + email.val() + "</td>" +
        "<td>" + password.val() + "</td>" +
        "</tr>" );
        dialog.dialog( "close" );
        }
        return valid;
    }
    dialog = $( "#dialog-form" ).dialog({
        autoOpen: false,
        height: 500,
        width: 700,
        modal: true,
        buttons: {
        "Create an account": addUser,
        Cancel: function() {
        dialog.dialog( "close" );
        }
        },
        close: function() {
        form[ 0 ].reset();
        allFields.removeClass( "ui-state-error" );
        }
    });
    form = dialog.find( "form" ).on( "submit", function( event ) {
        event.preventDefault();
        addUser();
    });
    $( "#create-project" ).button().on( "click", function() {
        dialog.dialog( "open" );
    });
 });