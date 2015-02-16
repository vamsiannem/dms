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
        <link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.4/css/jquery.dataTables.css">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/static/css/jquery-ui.css">
        <!-- DataTables -->
        <script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.4/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf8" src="<%= request.getContextPath() %>/static/js/jquery-ui.js"></script>

      	<script language="javascript" type="text/javascript">
      	    var networkUnits= JSON.parse('${networkUnits}');
        </script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/static/js/networks_script.js"></script>
</head>
<body onload="expandAll('dhtmlgoodies_tree2');return false">

<div class="header1">
	<span style="float:left"><img src="" style="color:#aaa"/></span>
	<span style="float:right;padding-top:0px "><a href="" style="color:#aaa">Profile</a>&nbsp;&nbsp;&nbsp;<a href="" style="color:#aaa">Help</a>&nbsp;&nbsp;&nbsp;<a href="" style="color:#aaa">Admin</a></span><br>
	<h2>Network Units</h2>

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
	    <div><h3 style="text-align:center">List of Network Units</h3></div>

    <div id="dialog-form" title="Create New Project">
        <p class="validateTips">All form fields are required.</p>
        <form>
            <fieldset >
                <div>
                <label class='dialog-label' for="projectId">Project ID</label>
                <input type="text" name="projectId" id="projectId" value="" class="text ui-widget-content ui-corner-all">
                <label class='dialog-label' for="client">Client</label>
                <input type="text" name="client" id="client" value=""  class="text ui-widget-content ui-corner-all">
                </div>
                <div>
                <label class='dialog-label' for="platform">Platform</label>
                <input type="text" name="platform" id="platform" value="" class="text ui-widget-content ui-corner-all">

                <label class='dialog-label' for="controlSystem">Control System</label>
                <input type="text" name="controlSystem" id="controlSystem" value="" class="text ui-widget-content ui-corner-all">
                </div>
                <div>
                <label class='dialog-label' for="channel">Channel</label>
                <input type="text" name="channel" id="channel" value="" class="text ui-widget-content ui-corner-all">
                <label class='dialog-label' for="ipAddress">IP Address</label>
                <input type="text" name="ipAddress" id="ipAddress" value="" class="text ui-widget-content ui-corner-all">
                </div>
                <div>
                <label class='dialog-label' for="unitSerialNo">Unit Serial No:</label>
                <input type="text" name="unitSerialNo" id="unitSerialNo" value="" class="text ui-widget-content ui-corner-all">
                </div>
                <!-- Allow form submission with keyboard without duplicating the dialog button -->
                <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
            </fieldset>
        </form>
    </div>
        <button id="create-project" class='button'>New Project</button>
	    <div id="table_div" style="padding:20px; border: 1px solid #ffafa4; margin: 25px; overflow:hidden;  ">
	        <table cellpadding="0" cellspacing="0" border="0" class="display compact" id="network_unit_table" width="95%"></table>
	    </div>
    </div>

</div>
<div class="footer1">
	<span class="copylink">copyright A @ 2004-2015. All Rights reserved</span>
	<span class="contactus"><a href="" style="color:#aaa">Contact Us</a>&nbsp;&nbsp;&nbsp;<a href="" style="color:#aaa">Privacy Policy</a>&nbsp;&nbsp;&nbsp;<a href="" style="color:#aaa">Terms of use</a></span>
</div>
<div>
    <form style="visibility:hidden" name="common-form"  id="common-form" action="<%= request.getContextPath() %>" method="post">
    </form>
 </div>
</body>
</html>