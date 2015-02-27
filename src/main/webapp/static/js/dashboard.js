$(document).ready(function() {

  var table_config = {
     data: networkUnits,
     "searching": false,
     "sort": false,
     "info": false,
     "pageLength": 5,
     "lengthMenu": [ [5], [5] ],
     columns: [
         {data: 'projectInfoId', title: 'ProjectInfo Id', class: 'hide_column'},
         {data: 'projectId', title: 'ProjectID', class: 'center'},
         {data: 'companyName', title: 'Client', class: 'center'},
         {data: 'platform', title: 'Platform', class: 'center'},
         {data: 'controlSystem', title: 'Control System', class: 'center'},
         {data: 'channel', title: 'Channel', class: 'center'},
         ]
  };
  var table = $('#network_unit_table').DataTable(table_config);
  if(!$(".dataTables_empty").length) {
    applySelectEventForDataTable("network_unit_table", table);
  }


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
               console.log("Project Info ID:"+ projectInfoId);
               getNodesList(projectInfoId);
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
