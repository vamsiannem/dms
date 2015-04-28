<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>
        DMS - Data Management Software
    </title>
  	<script language="javascript" type="text/javascript">
        var projects= JSON.parse('${projects}');
        var ctx = "${pageContext.request.contextPath}"
    </script>
   <link rel="stylesheet" type="text/css" href="../static/css/sample_style.css" />
   <link rel="stylesheet" href="../static/css/folder-tree-static.css" type="text/css">
   <link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.4/css/jquery.dataTables.css">
  	<link rel="stylesheet" href="../static/css/context-menu.css" type="text/css">
  	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/static/css/jquery-ui.css">
  	<script type="text/javascript" src="../static/js/ajax.js"></script>
  	<script type="text/javascript" src="../static/js/folder-tree-static.js"></script>
  	<script type="text/javascript" src="../static/js/context-menu.js"></script>
  	<script type="text/javascript" src="../static/js/jquery-1.11.1.js"></script>
  	<script type="text/javascript" charset="utf8" src="<%= request.getContextPath() %>/static/js/jquery-ui.js"></script>
  	<script type="text/javascript" src="../static/js/common.js"></script>
  	<script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.4/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="../static/js/dashboard.js"></script>
</head>
<body onload="expandAll('dhtmlgoodies_tree2');return false">

    <div class="header1">
        <span style="float:left"><img src=""/></span>
        <span class='options' ><a href="" class="ref_option">Profile</a>&nbsp;&nbsp;&nbsp;<a href="" class="ref_option">Help</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="">Admin</a></span>
        <h2>Dashboard</h2>
    </div>
    <div>
        <div class='leftNavMenu'>
          <jsp:include page="left_nav.jsp"/>
        </div>
        <input type="hidden" id="statusMessage" name="statusMessage" value="${flag}-${status}"/>
        <div style="float:left; padding-left:15px;" id="statusWrapper">
        </div>
        <div style="float:right;padding-right: 15px;"><input type="submit" name="submit" value="Page buttons" class="button" />
        </div>
        <div style="border-bottom: 2px solid #cfcfcf;border-radius: 3px 3px 0 0;padding-top: 52px">
        </div>

        <!---->
        <div style="padding-left: 15px;padding-top: 4px;float:left;" >
            <div class='shadow' style="float:left;width: 450px;height: 250px;border: 2px solid #fff;text-align:center;position:relative;" >
                <span style="text-decoration:underline;" >V-IR Systems Overview</span>
                <span style="text-align:right;float:right; position:absolute; bottom:0;text-decoration:underline;font-family:serif;cursor:pointer;" id="list_of_networks">List Of Networks</span>
                <div id="table_div" style="padding:5px; border: 1px solid #ffafa4; margin: 5px; overflow:hidden; font-size:10px;">
                    <table cellpadding="0" cellspacing="0" border="0" class="display compact" id="projects_table" width="95%"></table>
                </div>
            </div>&nbsp;
            <div class='shadow' style="margin-left:20px;float:right;width: 450px;height: 250px;border: 2px solid #fff; cursor:pointer;text-align:center;text-decoration:underline;" id="monthly_reports">
                Monthly Reports
            </div>
        </div>

        <div style="padding-top: 10px;float:left;padding-right: 100px;padding-left: 15px;margin-top:2px;">
            <div class='shadow' style="float:left;width: 450px;height: 250px;border: 2px solid #fff; cursor:pointer;text-align:center;text-decoration:underline;" id="recent_projects">
                Recent Projects
            </div>&nbsp;
            <div class='shadow' style="margin-left:20px;float:right;width: 450px;height: 250px;border: 2px solid #fff; cursor:pointer;text-align:center;text-decoration:underline;" id="user_activities">
                User Activities
            </div>
        </div>
    </div>

    <div class="footer1">
        <span class="copylink">Copyright @ 20014-2016. All Rights reserved</span>
        <span class="contactus options"><a class="ref_option" href="">Contact Us</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="" >Privacy Policy</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="" >Terms of use</a></span>
    </div>
    <div>
        <form name="common-form"  id="common-form" action="<%= request.getContextPath() %>" method="post">
                <input type="hidden" id="frm_projectInfoId" name="frm_projectInfoId">
        </form>
    </div>
</body>
</html>



