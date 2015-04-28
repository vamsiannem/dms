var fadeSpeed = 100; // a value between 1 and 1000 where 1000 will take 10
                        // seconds to fade in and out and 1 will take 0.01 sec.
$(document).ready(function() {

    jQuery('#from').datetimepicker({
        format:'d/m/Y H:i',
        onChangeDateTime:function(dp,$input){
            jQuery('#from').datetimepicker('hide');
        }
        /*,
        onShow:function( ct, time ){
            console.log(time);
           this.setOptions({
            maxDate:jQuery('#to').val()?jQuery('#to').val():false
            })
        }*/
     })
    jQuery('#to').datetimepicker({
        format:'d/m/Y H:i',
        onChangeDateTime:function(dp,$input){
            jQuery('#from').datetimepicker('hide');
        }
        /*,
        onShow:function( ct ){
           this.setOptions({
            minDate:jQuery('#from').val()?jQuery('#from').val():false
           })
        }*/
    });
   fillNetworkUnitDropDown()
});

 $(function() {
    var form,
    // From http://www.whatwg.org/specs/web-apps/current-work/multipage/states-of-the-type-attribute.html#e-mail-state-%28type=email%29
    //emailRegex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/,
    networkUnit = $( "#networkUnitSelect" ),
    from = $( "#from" ),
    to = $( "#to" ),
    allFields = $( [] ).add( networkUnit ).add( from).add( to ),
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
    function checkRegexp( o, regexp, n ) {
        if ( !( regexp.test( o.val() ) ) ) {
        o.addClass( "ui-state-error" );
        updateTips( n );
        return false;
        } else {
        return true;
        }
    }
    function exportUnitData() {
        var valid = true;
        allFields.removeClass( "ui-state-error" );
        valid = valid && checkNotEmpty( networkUnit, "NetworkUnit ");
        valid = valid && checkNotEmpty( from, "Start Date ");
        valid = valid && checkNotEmpty( to, "End Date ");
        if ( valid ) {
            console.log("Valid... fire ajax call to download csv");
            /// ajax call here.

            //fetchUnitData();
        }
        return valid;
    }
    /*fetchUnitData = function(){
        var reqParam = "projectInfoId="+networkUnit.val()+"&from="+
                        from.val()+"&to="+to.val();
        var request = $.ajax({
          url: "/dms/api/project/data/export",
          type: "POST",
          data: reqParam
        });

        request.done(function( response ) {
          if( response && response.indexOf('No Results' )==-1){

          } else {
            tips.text(response);
          }
        });

        request.fail(function( jqXHR, textStatus ) {
          console.log( "Request failed: " + textStatus );
          //tips.text("Unexpected Error! Unable to Create Project with ProjectInfoId:"+ projectInfoId.val());
        });
    }*/

    form = $( "#file_download_form" ).on( "submit", function( event ) {
        //event.preventDefault();
         return exportUnitData();
    });

 });