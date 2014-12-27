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
                   {data: 'limResistance', title: 'Lim Resistance', class: 'center'}
                   ]
               };
  var table = $('#networks_table').DataTable(table_config);
    applySelectEventForDataTable("networks_table", table);

    $('#companyName').append(
     $('<option></option>').val('_blank').html('--Select Company--')
    );
    appendNewData2Dropdown("companyName", companies, false);

    var contextPath = $("#common-form").attr("action");
    $("#companyName").change(function() {
      // ajax call to get list of unit numbers
      var company = $("#companyName").val();
      var request = $.ajax({
          url: contextPath+"/api/product/"+company+"/unitSerialNo.json",
          type: "GET",
          dataType: "json"
      });
      request.done(function( msg ) {
         var unitSerialNoArray = msg['productDataList'];
         $('#unitSerialNo').empty();
         $('#unitSerialNo').append(
              $('<option></option>').val('_blank').html('--Select Option--')
          );
         appendNewData2Dropdown("unitSerialNo", unitSerialNoArray, false);
      });
      request.fail(function( jqXHR, textStatus ) {
        console.log( "Request failed: " + textStatus );
      });
    });
    $("#unitSerialNo").change(function(){
        var contextPath = $("#common-form").attr("action");
        var companyName = $("#companyName").val();
        var unitSerialNo = $("#unitSerialNo").val();
        if( ! ($("select[name='companyName'] option:selected").index() > 0 &&
            $("select[name='unitSerialNo'] option:selected").index() > 0 ) ) {
            console.log("Select right combination of companyName & UnitSerialNo in order to refresh the table data.");
            return;
        }
        var request = $.ajax({
            url: contextPath+"/api/product/data/"+companyName+"/"+ unitSerialNo+".json",
            type: "GET",
            dataType: "json"
        });
        request.done(function( obj ) {
           $('#table_div').empty();
           $('#table_div').append('<table cellpadding="0" cellspacing="0" border="0" class="display compact" id="networks_table" width="95%"></table>');
           table_config.data = obj.products;
           var _table = $('#networks_table').DataTable(table_config);
           applySelectEventForDataTable("networks_table", _table);
        });
        request.fail(function( jqXHR, textStatus ) {
            console.log( "Request failed: " + textStatus );
        });
    });
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
            var companyName = $("#companyName").val();
            var unitSerialNo = $("#unitSerialNo").val();
            var contextPath = $("#common-form").attr("action");
            $("#common-form").attr("action", contextPath+"/api/graph");
            $("#common-form").attr("method", "GET");
            $("#frm_companyName").val(companyName);
            $("#frm_unitSerialNo").val(unitSerialNo);
            $("#common-form").submit();
    });

}