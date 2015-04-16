<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>
        DMS - Data Management Software
    </title>
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/static/css/sample_style.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/folder-tree-static.css" type="text/css">
   	<link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/context-menu.css" type="text/css">
   	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/static/css/jquery.jqplot.css" />

   	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/ajax.js"></script>
   	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/folder-tree-static.js"></script>
   	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/context-menu.js"></script>
   	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/jquery-1.11.1.js"></script>
   	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/common.js"></script>
    <!--[if IE]><script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/static/js/jqplot/excanvas.js"></script><![endif]-->
    <script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/static/js/jqplot/jquery.jqplot.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/static/js/jqplot/jqplot.dateAxisRenderer.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/static/js/jqplot/jqplot.canvasAxisTickRenderer.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/static/js/jqplot/jqplot.canvasTextRenderer.js"></script>
    <script language="javascript" type="text/javascript">
        var plotData= JSON.parse('${plotData}');
        var dataCoreMeasurements = JSON.parse('${dataMeasurements}');
    </script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/static/js/project_data_graph.js"></script>
    <script type="text/javascript" src="http://code.highcharts.com/highcharts.js"></script>
    <script type="text/javascript" src="http://code.highcharts.com/modules/exporting.js"></script>


</head>
<body onload="expandAll('dhtmlgoodies_tree2');return false">

    <div class="header1">
        <span style="float:left"><img src=""/></span>
            <span class='options' ><a href="" class="ref_option">Profile</a>&nbsp;&nbsp;&nbsp;<a href="" class="ref_option">Help</a>&nbsp;&nbsp;&nbsp;<a href="" class="ref_option">Admin</a></span>
        <h2>${project_info.companyName}-${project_info.controlSystem}-${project_info.channel}-${node} </h2>

    </div>
    <div>
        <div class='leftNavMenu'>
              <jsp:include page="left_nav.jsp"/>
        </div>

        <div>
            <div style="float:left;" id="statusMessage">
                <span style='color:<c:out value="${flag}"> </c:out>;font-size: 18px;'>
                    <c:out value="${status}"> </c:out>
                </span>
            </div>
            <div style="float:right;padding-right: 15px;">
                <input type="submit" name="submit" value="Page buttons" class="button" />
            </div>
        </div>
        <div style="border-bottom: 2px solid #cfcfcf;border-radius: 3px 3px 0 0;padding-top: 52px">
        </div>


        <div style="padding-left: 15px;padding-top: 4px;float:left;">
            <div style="float:left;width: 780px;height: 400px;border: 2px solid #fff;overflow-y:hidden">
                <div style="margin: 10px;  border: 1px solid #ffafa4;" id="resizable">

                    <div id="line-chart" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

                </div>
            </div>&nbsp;
            <div style="float:right;width: 250px;height: 200px;border: 2px solid #fff">
                Monthly Reports
            </div>
            <br>&nbsp;
            <div style="float:right;width: 250px;height: 200px;border: 2px solid #fff">
                User Activities
            </div>
        </div>

        <div style="padding-top: 10px;float:left;padding-left: 15px;">
            <div style="float:left;width: 1038px;height: 50px;border: 2px solid #fff;">
            Recent Projects
            </div>&nbsp;
        </div>

        <div class="footer1">
            <span class="copylink">Copyright @ 20014-2016. All Rights reserved</span>
            <span class="contactus options"><a class="ref_option" href="">Contact Us</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="" >Privacy Policy</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="" >Terms of use</a></span>
        </div>
        <div>
            <form name="common-form"  id="common-form" action="<%= request.getContextPath() %>" method="post"></form>
        </div>
    </div>
</body>
</html>