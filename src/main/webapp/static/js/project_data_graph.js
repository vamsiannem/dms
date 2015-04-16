$(document).ready(function() {
    var anotherPlotData = new Array();
    var i=0;
    $.each(plotData, function (index, item) {
            // "2014-10-24 14:36"
            var dt = item[0].split(/[-: ]/);
            item[0] = new Date(dt[0], dt[1]-1, dt[2], dt[3], dt[4], 00);
           item[1]= parseFloat(item[1]);
    });
   // console.log(plotData);


    $('#line-chart').highcharts({
        chart: {
            type: 'spline'
        },
        title: {
            text: 'Line Chart Analysis of Unit'
        },
        subtitle: {
            text: 'Source: DMS Unit Data'
        },
        xAxis: {
            type: 'datetime',
            dateTimeLabelFormats: { // don't display the dummy year
                day: '%e of %b'
            },
            title: {
                text: 'Date'
            }
        },
        yAxis: {
            title: {
                text: 'Measurement'
            },
            min: 0
        },
        tooltip: {
            headerFormat: '<b>{series.name}</b><br>',
            pointFormat: '{point.x:%e. %b %H:%M}  :  {point.y:.2f} '
        },

        plotOptions: {
            spline: {
                marker: {
                    enabled: true
                }
            }
        },

        series: [{
            name: 'LimResistance',
            data:
                plotData

        }]
    });


    /*$('#line-chart').bind('resize', function(event, ui) {
        plot2.replot( { resetAxes: true } );
    });*/

});