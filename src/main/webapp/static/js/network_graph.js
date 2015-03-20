$(document).ready(function() {
    var anotherPlotData = new Array();
    var i=0;
    $.each(plotData, function (index, item) {
            // "2014-10-24 14:36"
            var dt = item[0].split(/[-: ]/);
            item[0] = new Date(dt[0], dt[1]-1, dt[2], dt[3], dt[4], 00);
           item[1]= parseFloat(item[1]);

    });
    console.log(plotData);

   /*

    var plot2 = $.jqplot ('chart1', [plotData], {
      // Give the plot a title.
      title: 'Line Chart',
      // You can specify options for all axes on the plot at once with
      // the axesDefaults object.  Here, we're using a canvas renderer
      // to draw the axis label which allows rotated text.
      axesDefaults: {
       //labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
      },
      // An axes object holds options for all axes.
      // Allowable axes are xaxis, x2axis, yaxis, y2axis, y3axis, ...
      // Up to 9 y axes are supported.
      axes: {
        // options for each axis are specified in seperate option objects.
        xaxis: {
          label: "Time",
          // Turn off "padding".  This will allow data point to lie on the
          // edges of the grid.  Default padding is 1.2 and will keep all
          // points inside the bounds of the grid.
         // pad: 0,
          renderer:$.jqplot.DateAxisRenderer,
          tickOptions:{formatString:'%b %#d, %T', angle: 80},
         // min:'Jan 30, 2014',
          //tickInterval:'3600'
          tickRenderer:$.jqplot.CanvasAxisTickRenderer,
          labelOptions:{
              fontFamily:'Helvetica',
              fontSize: '14pt'
          }
        },
        yaxis: {
          label: "Lim Resistance",
          //renderer: $.jqplot.CategoryAxisRenderer,
          tickOptions: {formatString: '%.3f'},
          min: -100
          //tickInterval: 80.5
        }
      },
      series:[{lineWidth:1, color: '#00ffff'}],
      cursor: {
              show: true,
              zoom: true
          }
    });*/

        $('#chart1').highcharts({
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


    /*$('#chart1').bind('resize', function(event, ui) {
        plot2.replot( { resetAxes: true } );
    });*/

    // Google charts Not required for this now.. can be used later if required.
    /*google.load('visualization', '1', {packages: ['corechart']});
        google.setOnLoadCallback(drawChart);

        function drawChart() {

            var data = new google.visualization.DataTable();
            data.addColumn('number', 'Time');
            data.addColumn('number', 'Lim Resistance');

            data.addRows([
            ['2014','1.89'],['2014','1.5'],['2014','5.9'],['2014','1']
            ]);

            var options = {
            width: 1000,
            height: 563,
            hAxis: {
            title: 'Time'
            },
            vAxis: {
            title: 'Popularity'
            }
            };

            var chart = new google.visualization.LineChart(
            document.getElementById('chart1'));

            chart.draw(data, options);

        }*/

});