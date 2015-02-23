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
      	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/ajax.js"></script>
      	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/folder-tree-static.js"></script>
      	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/context-menu.js"></script>
      	 <!-- jQuery -->
        <script type="text/javascript" src="<%= request.getContextPath() %>/static/js/jquery-1.11.1.js"></script>

      	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/common.js"></script>

        <!-- DataTables CSS -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.4/css/jquery.dataTables.css">

        <!-- DataTables -->
        <script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.4/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/tabletools/2.2.3/js/dataTables.tableTools.min.js"></script>

      	<script language="javascript" type="text/javascript">
      	    var companies= JSON.parse('${companies}');
      	    var products= JSON.parse('${products}');
        </script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/static/js/networks_script.js"></script>
</head>
<body onload="expandAll('dhtmlgoodies_tree2');return false">

<div class="header1">
	<span style="float:left"><img src=""/></span>
    	<span class='options' ><a href="" class="ref_option">Profile</a>&nbsp;&nbsp;&nbsp;<a href="">Help</a>&nbsp;&nbsp;&nbsp;<a href="" class="ref_option">Admin</a></span>
	<h2>Remote Unit Maintenance</h2>

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
	<div style="float:right;padding-right: 15px;"><input type="submit" name="submit" value="Page buttons" class="button" /></div>
	</div>
	<div style="border-bottom: 2px solid #cfcfcf;border-radius: 3px 3px 0 0;padding-top: 52px">
	</div>

</div>
<div style="padding-left: 15px;padding-top: 4px;float:left;">

    <div style="float:left;width: 1040px;height: 500px;border: 2px solid #fff;">
	    <div><h3 style="text-align:center">Remote Unit Maintenance</h3></div>
	    <div id="table_div" style="padding: 10px; border: 1px solid #343434; margin: 5px; overflow:hidden;  ">
	        <table cellpadding="0" cellspacing="0" border="0" class="display compact" id="unit_table" width="95%"></table>
	    </div>
    </div>
</div>
<div class="footer1">
	<span class="copylink">Copyright @ 20014-2016. All Rights reserved</span>
	<span class="contactus options"><a class="ref_option" href="">Contact Us</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="" >Privacy Policy</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="" >Terms of use</a></span>
</div>
<div>
    <form style="visibility:hidden" name="common-form"  id="common-form" action="<%= request.getContextPath() %>" method="post">
        <input type="hidden" name="frm_companyName" id="frm_companyName" />
        <input type="hidden" name="frm_unitSerialNo" id="frm_unitSerialNo" />
    </form>
 </div>
</body>
</html>