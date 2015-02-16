<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" /> 
    <title>
        DMS - Data Management Software
    </title>
  <link rel="stylesheet" type="text/css" href="../static/css/sample_style.css" />
   <link rel="stylesheet" href="../static/css/folder-tree-static.css" type="text/css">
   <link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.4/css/jquery.dataTables.css">
  	<link rel="stylesheet" href="../static/css/context-menu.css" type="text/css">
  	<script type="text/javascript" src="../static/js/ajax.js"></script>
  	<script type="text/javascript" src="../static/js/folder-tree-static.js"></script>
  	<script type="text/javascript" src="../static/js/context-menu.js"></script>
  	<script type="text/javascript" src="../static/js/jquery-1.11.1.js"></script>
  	<script type="text/javascript" src="../static/js/common.js"></script>
  	<script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.4/js/jquery.dataTables.js"></script>
  	<script language="javascript" type="text/javascript">
        var networkUnits= JSON.parse('${networkUnits}');
    </script>
    <script type="text/javascript" src="../static/js/dashboard.js"></script>
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
                <li><a href="#" id="list_33">Remote Unit Config</a>
                </li>
			</ul>
		</li>
	</ul> 
    </div>
    <div>
    <div style="float:left;" id="statusMessage">
            <span style="color:red">
            </span>
    </div>
	<div style="float:right;padding-right: 15px;"><input type="submit" name="submit" value="Page buttons" class="button" /></div>
	</div>
	<div style="border-bottom: 2px solid #cfcfcf;border-radius: 3px 3px 0 0;padding-top: 52px">
	</div>
    
</div>
<div style="padding-left: 15px;padding-top: 4px;float:left;">
    <div style="float:left;width: 450px;height: 250px;border: 2px solid #fff;text-align:center;position:relative;" >
        <span style="text-decoration:underline;" >V-IR Systems Overview</span>
        <span style="text-align:right;float:right; position:absolute; bottom:0;text-decoration:underline;font-family:serif;cursor:pointer;" id="list_of_networks">List Of Networks</span>
        <div id="table_div" style="padding:5px; border: 1px solid #ffafa4; margin: 5px; overflow:hidden; font-size:10px;">
            <table cellpadding="0" cellspacing="0" border="0" class="display compact" id="network_unit_table" width="95%"></table>
        </div>
    </div>&nbsp;
    <div style="float:right;width: 450px;height: 250px;border: 2px solid #fff; cursor:pointer;text-align:center;text-decoration:underline;" id="monthly_reports">
        Monthly Reports
    </div>   
</div>

<div style="padding-top: 10px;float:left;padding-right: 100px;padding-left: 15px;">
    <div style="float:left;width: 450px;height: 250px;border: 2px solid #fff; cursor:pointer;text-align:center;text-decoration:underline;" id="recent_projects">
	    Recent Projects
    </div>&nbsp;
    <div style="float:right;width: 450px;height: 250px;border: 2px solid #fff; cursor:pointer;text-align:center;text-decoration:underline;" id="user_activities">
	    User Activities
    </div> 
</div>
</div>

<div class="footer1">
	<span class="copylink">copyright A @ 2004-2015. All Rights reserved</span>
	<span class="contactus"><a href="" style="color:#aaa">Contact Us</a>&nbsp;&nbsp;&nbsp;<a href="" style="color:#aaa">Privacy Policy</a>&nbsp;&nbsp;&nbsp;<a href="" style="color:#aaa">Terms of use</a></span>
</div>
<div>
    <form name="common-form"  id="common-form" action="<%= request.getContextPath() %>" method="post">
            <input type="hidden" id="frm_projectId" name="frm_projectId">
    </form>
 </div>
</body>
</html>



