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
        var products= JSON.parse('${products}');
    </script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/static/js/network.js"></script>


    <!-- <script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1','packages':['corechart']}]}"></script> -->
</head>
<body onload="expandAll('dhtmlgoodies_tree2');return false">

<div class="header1">
	<span style="float:left"><img src="" style="color:#aaa"/></span>
	<span style="float:right;padding-top:0px "><a href="" style="color:#aaa">Profile</a>&nbsp;&nbsp;&nbsp;<a href="" style="color:#aaa">Help</a>&nbsp;&nbsp;&nbsp;<a href="" style="color:#aaa">Admin</a></span><br>
	<h2>Dashboard</h2>

</div>
<div>
    <div style="background-color:#aaa; height:580px;width:200px;float:left;">
          <ul id="dhtmlgoodies_tree2" class="dhtmlgoodies_tree">
            <li><a href="#" id="node_99">Dashboard</a></li>
            <li><a href="#" id="node_100">Networks</a>
                <ul>
                    <li><a href="#" id="list_10">List of Networks</a>
                    </li>
                </ul>
            </li>
            <li><a href="#" id="node_101">Data Management</a>
                <ul>
                    <li><a href="#" id="list_20">Import</a>
                    </li>
                    <li><a href="#" id="list_21">exports</a>
                    </li>
                </ul>
            </li>
            <li><a href="#" id="node_102">Assets</a>
                <ul>
                    <li><a href="#" id="list_30">Maintenance Contracts </a>
                    </li>
                    <li><a href="#" id="list_31">Warranties</a>
                    </li>
                    <li><a href="#" id="list_32">Notification</a>
                    </li>
                </ul>
            </li>
        </ul>
        </div>
    <div>

    <div style="float:left;" id="statusMessage">
        <span style='color:<c:out value="${flag}"> </c:out>;font-size: 18px;'>
                <c:out value="${status}"> </c:out>
        </span>
    </div>

	<div style="float:left;font-size:20px;padding-left: 15px">Hold</div>
	<div style="float:right;padding-right: 15px;"><input type="submit" name="submit" value="Page buttons" class="button" /></div>
	</div>
	<div style="border-bottom: 2px solid #cfcfcf;border-radius: 3px 3px 0 0;padding-top: 52px">
	</div>

</div>
<div style="padding-left: 15px;padding-top: 4px;float:left;">
    <div style="float:left;width: 780px;height: 400px;border: 2px solid #fff;">
	    <div>Network Status Graph</div>
	    <div id="resizable"> <div id="chart1">  </div> </div>
    </div>&nbsp;
    <div style="float:right;width: 250px;height: 200px;border: 2px solid #fff">
	Monthly Reports
    </div><br>&nbsp;
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
	<span class="copylink">copyright A @ 2004-2015. All Rights reserved</span>
	<span class="contactus"><a href="" style="color:#aaa">Contact Us</a>&nbsp;&nbsp;&nbsp;<a href="" style="color:#aaa">Privacy Policy</a>&nbsp;&nbsp;&nbsp;<a href="" style="color:#aaa">Terms of use</a></span>
</div>
<div>
    <form name="common-form"  id="common-form" action="<%= request.getContextPath() %>" method="post"></form>
 </div>
</body>
</html>



