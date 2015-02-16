$(document).ready(function() {

  var table_config = {
     data: networkUnits,
     "searching": false,
     "sort": false,
     "info": false,
     "pageLength": 5,
     "lengthMenu": [ [5], [5] ],
     columns: [
         {data: 'projectId', title: 'ProjectID', class: 'center'},
         {data: 'companyName', title: 'Client', class: 'center'},
         {data: 'platform', title: 'Platform', class: 'center'},
         {data: 'controlSystem', title: 'Control System', class: 'center'},
         {data: 'channel', title: 'Channel', class: 'center'},
         ]
  };
  var table = $('#network_unit_table').DataTable(table_config);
  applySelectEventForDataTable("network_unit_table", table);

});


function applySelectEventForDataTable(tableId, table){
    $('#'+tableId).on( 'click', 'tr',
        function () {
            if ( $(this).hasClass('selected') ) {
                $(this).removeClass('selected');
            } else {
                table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }
            var columns = $(this).find('td');
            var projectId = $(columns[0]).text();
            getNodesList(projectId);
    });

}
