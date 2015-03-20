$(document).ready(function() {
    $.each(plotData, function (index, item) {
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
                    text: 'Snow depth at Vikjafjellet, Norway'
                },
                subtitle: {
                    text: 'Irregular time data in Highcharts JS'
                },
                xAxis: {
                    type: 'datetime',
                    dateTimeLabelFormats: { // don't display the dummy year
                        month: '%e. %b',
                        year: '%b'
                    },
                    title: {
                        text: 'Date'
                    }
                },
                yAxis: {
                    title: {
                        text: 'Snow depth (m)'
                    },
                    min: 0
                },
                tooltip: {
                    headerFormat: '<b>{series.name}</b><br>',
                    pointFormat: '{point.x:%e. %b}: {point.y:.2f} m'
                },

                plotOptions: {
                    spline: {
                        marker: {
                            enabled: true
                        }
                    }
                },

                series: [{
                    name: 'Winter 2007-2008',
                    // Define the data points. All series have a dummy year
                    // of 1970/71 in order to be compared on the same x axis. Note
                    // that in JavaScript, months start at 0 for January, 1 for February etc.
                    data: [
                        [Date.UTC(1970,  9, 27), 0   ],
                        [Date.UTC(1970, 10, 10), 0.6 ],
                        [Date.UTC(1970, 10, 18), 0.7 ],
                        [Date.UTC(1970, 11,  2), 0.8 ],
                        [Date.UTC(1970, 11,  9), 0.6 ],
                        [Date.UTC(1970, 11, 16), 0.6 ],
                        [Date.UTC(1970, 11, 28), 0.67],
                        [Date.UTC(1971,  0,  1), 0.81],
                        [Date.UTC(1971,  0,  8), 0.78],
                        [Date.UTC(1971,  0, 12), 0.98],
                        [Date.UTC(1971,  0, 27), 1.84],
                        [Date.UTC(1971,  1, 10), 1.80],
                        [Date.UTC(1971,  1, 18), 1.80],
                        [Date.UTC(1971,  1, 24), 1.92],
                        [Date.UTC(1971,  2,  4), 2.49],
                        [Date.UTC(1971,  2, 11), 2.79],
                        [Date.UTC(1971,  2, 15), 2.73],
                        [Date.UTC(1971,  2, 25), 2.61],
                        [Date.UTC(1971,  3,  2), 2.76],
                        [Date.UTC(1971,  3,  6), 2.82],
                        [Date.UTC(1971,  3, 13), 2.8 ],
                        [Date.UTC(1971,  4,  3), 2.1 ],
                        [Date.UTC(1971,  4, 26), 1.1 ],
                        [Date.UTC(1971,  5,  9), 0.25],
                        [Date.UTC(1971,  5, 12), 0   ]
                    ]
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