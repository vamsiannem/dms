$(document).ready(function() {

  var table_config = {
               data: products,
               columns: [
                   {data: 'time', title: 'Time', class: 'center', "width": "20%"},
                   {data: 'vNetAddress', title: 'VNet Address', class: 'center'},
                   {data: 'type', title: 'Type', class: 'center'},
                   {data: 'status', title: 'Status', class: 'center'},
                   {data: 'limImbalance', title: 'Lim Imbalance', class: 'center'},
                   {data: 'limCapacitance', title: 'Lim Capacitance', class: 'center'},
                   {data: 'limResistance', title: 'Lim Resistance', class: 'center'},
                   {data: 'networkUnit.projectId', title: 'Project Id', class: 'center'}
                   ]
               };
  var table = $('#networks_table').DataTable(table_config);
    applySelectEventForDataTable("networks_table", table);


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
            var projectId = $(columns[columns.length-1]).text();
            var contextPath = $("#common-form").attr("action");
            $("#common-form").attr("action", contextPath+"/api/graph");
            $("#common-form").attr("method", "POST");
            $("#frm_projectId").val(projectId);
            $("#common-form").submit();
    });

}