$(document).ready(function() {
    var anotherPlotData = new Array();
    var i=0;
    $.each(plotData, function (index, item) {
            // "2014-10-24 14:36"
            var dt = item[0].split(/[-: ]/);
            item[0] = Date.UTC(dt[0], dt[1]-1, dt[2], dt[3], dt[4], 0, 0);
           item[1]= parseFloat(item[1]);
    });
   // console.log(plotData);


    $('#line-chart').highcharts({
        chart: {
            zoomType: 'x'
        },
        title: {
            text: 'Line Chart Analysis of Unit'
        },
        subtitle: {
            text: document.ontouchstart === undefined ?
                    'Click and drag in the plot area to zoom in' :
                    'Pinch the chart to zoom in'
        },
        legend: {
            enabled: false
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
            }
        },
        tooltip: {
            headerFormat: '<b>{series.name}</b><br>',
            pointFormat: '{point.x:%e. %b %H:%M}  :  {point.y:.2f} '
        },

        plotOptions: {
            area: {
                fillColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                    ]
                },
                marker: {
                    radius: 2
                },
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
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