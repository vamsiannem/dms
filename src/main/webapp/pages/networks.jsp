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
        <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/networks.css" type="text/css">
</head>
<body onload="expandAll('dhtmlgoodies_tree2');return false">

<div class="header1">
	<span style="float:left"><img src=""/></span>
    	<span class='options' ><a href="" class="ref_option">Profile</a>&nbsp;&nbsp;&nbsp;<a href="" class="ref_option">Help</a>&nbsp;&nbsp;&nbsp;<a href="" class="ref_option">Admin</a></span>
	<h2>V-IR SYSTEMS OVERVIEW</h2>

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
	<div style="float:right;padding: 5px 25px 5px 5px;"><button id="create-project" class='button'>New Project</button></div>
	</div>
	<div style="border-bottom: 2px solid #cfcfcf;border-radius: 3px 3px 0 0;padding-top: 52px">
	</div>

</div>
<div style="padding-left: 15px;padding-top: 4px;float:left;">

    <div style="float:left;width: 1040px;height: 600px;border: 2px solid #fff;">
    <div id="dialog-form" title="Create New Project" style="background: url('../images/bg.png')">
        <p class="validateTips">All form fields are required.</p>
        <form>
            <fieldset style="border:1px solid #483D8B; padding: 10px;" >
                <div>
                    <h3 style="font-style: inherit;text-decoration: underline;font-family: Times New Roman;text-align:center">
                        Unit Details
                    </h3>
                </div>
                <div>
                    <label class='dialog-label' for="projectId">
                        <span>Project Id</span>
                        <input type="text" name="projectId" id="projectId" placeholder="Enter project id" class="text ui-widget-content ui-corner-all"/>
                    </label>
                    <label class='dialog-label' for="companyName">
                        <span>Client</span>
                        <input type="text" name="companyName" id="companyName" placeholder="Client Name" class="text ui-widget-content ui-corner-all"/>
                    </label>
                    <label class='dialog-label' for="platform">
                        <span>Platform</span>
                        <input type="text" name="platform" id="platform" placeholder="platform" class="text ui-widget-content ui-corner-all"/>
                    </label>
                </div>
                <div style="clear:both;"></div>
                <div>
                    <label class='dialog-label' for="controlSystem">
                        <span>Control System</span>
                        <input type="text" name="controlSystem" id="controlSystem" placeholder="Control System" class="text ui-widget-content ui-corner-all"/>
                    </label>

                    <label class='dialog-label' for="channel">
                        <span>Channel</span>
                        <input type="text" name="channel" id="channel" placeholder="Channel" class="text ui-widget-content ui-corner-all"/>
                    </label>
                    <label class='dialog-label' for="ipAddress">
                        <span>IP Address</span>
                        <input type="text" name="ipAddress" id="ipAddress" placeholder="IP Address" class="text ui-widget-content ui-corner-all"/>
                    </label>
                </div>
                <div style="clear:both;"></div>
                <div>
                    <label class='dialog-label' for="unitSerialNo">
                        <span>Unit Serial No:</span>
                        <input type="text" name="unitSerialNo" id="unitSerialNo" placeholder="Serial Number" class="text ui-widget-content ui-corner-all"/>
                    </label>
                </div>
                <!-- Allow form submission with keyboard without duplicating the dialog button -->
                <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
            </fieldset>
            <!-- This is for auto sync capabilities. Currently not required.
            <fieldset style="border:1px solid #483D8B; padding: 10px;">
                <div>
                    <h3 style="font-style: inherit;text-decoration: underline;font-family: Times New Roman;text-align:center">
                        Unit Sync Configuration
                    </h3>
                </div>
                <div>
                    <label class='dialog-label' for="URL">
                        <span>URL:</span>
                        <input type="text" name="url" id="url" placeholder="Enter HTTP URL" class="text ui-widget-content ui-corner-all"/>
                    </label>
                    <label class='dialog-label' for="headers">
                        <span>Request Headers:</span>
                        <select id="headers" name="headers" multiple="true" class="ui-widget-content ui-corner-all">
                            <option value="C-NA">Content: NOT APPLICABLE</option>
                            <option value="C-JSON">Content: JSON</option>
                            <option value="C-XML">Content: XML</option>
                            <option value="C-TEXT">Content: PLAIN TEXT</option>
                            <option value="A-NA">Accept: NOT APPLICABLE</option>
                            <option value="A-JSON">Accept: JSON</option>
                            <option value="A-XML">Accept: XML</option>
                            <option value="A-TEXT">Accept: PLAIN TEXT</option>
                        </select>
                    </label>
                    <label class='dialog-label' for="method">
                        <span>Request Method:</span>
                        <select id="method" name="method" class="ui-widget-content ui-corner-all">
                            <option value="GET">GET</option>
                            <option value="POST">POST</option>
                        </select>
                    </label>
                </div>
                <div style="clear:both;"></div>
                <div>
                    <label class='dialog-label' for="unitSerialNo">
                        <span>Type:</span>
                        <input type="text" name="unitSerialNo" id="unitSerialNo" placeholder="Serial Number" class="text ui-widget-content ui-corner-all"/>
                    </label>
                </div>
                <div>
                    <label class='dialog-label' for="unitSerialNo">
                        <span>vNet Address:</span>
                        <input type="text" name="unitSerialNo" id="unitSerialNo" placeholder="Serial Number" class="text ui-widget-content ui-corner-all"/>
                    </label>
                </div>
                <div>
                    <label class='dialog-label' for="unitSerialNo">
                        <span>Period:</span>
                        <input type="text" name="unitSerialNo" id="unitSerialNo" placeholder="Serial Number" class="text ui-widget-content ui-corner-all"/>
                    </label>
                </div>
            </fieldset>
            This code will be used for auto sync. Currently not required. -->
        </form>
    </div>
	    <div id="table_div" style="padding:20px; border: 1px solid #ffafa4; margin: 25px; overflow:hidden;  ">
	        <table cellpadding="0" cellspacing="0" border="0" class="display compact" id="network_unit_table" width="95%"></table>
	    </div>
    </div>

</div>
<div class="footer1">
	<span class="copylink">Copyright @ 20014-2016. All Rights reserved</span>
	<span class="contactus options"><a class="ref_option" href="">Contact Us</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="" >Privacy Policy</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="" >Terms of use</a></span>
</div>
<div>
    <form style="visibility:hidden" name="common-form"  id="common-form" action="<%= request.getContextPath() %>" method="post">
        <input type="hidden" id="frm_projectInfoId" name="frm_projectInfoId">
        <input type="hidden" id="orderBy" name="orderBy">
    </form>
 </div>
</body>
</html>