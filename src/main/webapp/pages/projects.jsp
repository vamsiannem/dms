<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>
        DMS - Data Management Software
    </title>
      	<script language="javascript" type="text/javascript">
      	    var projects= JSON.parse('${projects}');
      	    var clients = JSON.parse('${clients}');
      	    var products = JSON.parse('${availableProducts}');
      	    var ctx = "${pageContext.request.contextPath}"
        </script>
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
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/static/css/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.6/css/jquery.dataTables.css">
        <!-- DataTables -->
        <script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.6/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf8" src="<%= request.getContextPath() %>/static/js/jquery-ui.js"></script>

        <script type="text/javascript" src="<%= request.getContextPath() %>/static/js/projects.js"></script>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/projects.css" type="text/css">
</head>
<body onload="expandAll('dhtmlgoodies_tree2');return false">
<div>
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
    <input type="hidden" id="statusMessage" name="statusMessage" value="${flag}-${status}"/>
	<div style="float:left; padding-left:15px; width:600px" id="statusWrapper"></div>
	<div id="create_project_button_div" style="float:right;padding: 5px 25px 5px 5px;"><button id="create-project" class='button'>New Project</button></div>

</div>
<div  style="padding-left: 15px;padding-top: 4px;float:left;width:81%">
    <div id="table_div" style="float:left;width: 100%;height:100%;border: 2px solid #fff;">
	    <div style="padding:20px; border: 1px solid #ffafa4; margin: 25px; overflow:hidden;  ">
	        <table cellpadding="0" cellspacing="0" border="0" class="display compact" id="projects_table" width="95%"></table>
	    </div>
    </div>
    <div id="new_project_div" style="float:left;width: 100%;height:515px;border: 2px solid #fff; display:none; margin-left:10px;">
        <div title="Create New Project" >
            <p class="validateTips">All form fields are required.</p>
            <form id="create_project_form">
                  <div style="margin: 15px 0px 15px 40px;float:left ">
                        <div class="row">
                        <label for="projectId">Starjar Project Id:</label>
                        <input type="text" name="projectId" id="projectId" placeholder="Enter project id" class="text ui-widget-content ui-corner-all"/>
                        </div>
                        <div class="row">
                        <label for="platform">Platform:</label>
                        <input type="text" name="platform" id="platform" placeholder="Platform" class="text ui-widget-content ui-corner-all"/>
                        </div>
                        <div class="row">
                        <label for="controlSystem">Control System:</label>
                        <input type="text" name="controlSystem" id="controlSystem" placeholder="Control System" class="text ui-widget-content ui-corner-all"/>
                        </div>
                        <div class="row">
                        <label for="channel">Channel:</label>
                        <input type="text" name="channel" id="channel" placeholder="Channel" class="text ui-widget-content ui-corner-all"/>
                        </div>
                        <div class="row">
                        <label for="descrption">Description:</label>
                        <input type="text" name="description" id="description" placeholder="Description" class="text ui-widget-content ui-corner-all"/>
                        </div>
                  </div>
                  <div style="margin: 15px 0px 15px 40px; float:left ">
                        <div class="row">
                            <label for="installationDate">Installation Date:</label>
                            <input type="text" name="installationDate" id="installationDate" placeholder="Unit Install Date" class="text ui-widget-content ui-corner-all"/>
                        </div>
                        <div class="row">
                            <label for="consignedEngineer">Lead Engineer:</label>
                            <input type="text" name="consignedEngineer" id="consignedEngineer" placeholder="Engineer" class="text ui-widget-content ui-corner-all"/>
                        </div>
                        <div class="row">
                            <label for="clientId">Client:</label>
                            <select  id="clientId" name="clientId" class="text ui-widget-content ui-corner-all">
                                <option value="----">-- Please Select --</option>
                            </select>
                        <!--<input type="text" name="companyName" id="companyName" placeholder="Company Name" class="text ui-widget-content ui-corner-all"/>-->
                        </div>
                        <div class="row">
                            <label for="clientContact">Client Contact:</label>
                            <input type="text" readonly="readonly" name="clientContact" id="clientContact" class="text ui-widget-content ui-corner-all ui-state-disabled"/>
                        </div>
                  </div>

                    <!-- Allow form submission with keyboard without duplicating the dialog button -->
                    <div style="clear:both;"></div>
                    <div>
                    <div style="margin: 5px 0px 2px 17px;">Available Products:</div>
                    <div style="padding:5px; border: 1px solid #ECE8E4; margin: 10 px; overflow:hidden; margin:0 0 0 17px; width: 48% ; box-shadow: 5px 5px 2px #888888">
                        <input type="hidden" value="" name="productId" id="productId">
                        <table cellpadding="0" cellspacing="0" border="0" class="display compact" id="client_info_table" width="90%" style="font-size:11px;"></table>
                    </div>
                    </div>
                    <div style="float: right;margin-right: 100px;position: absolute;top: 500px;left: 807px;">
                       <span style="padding:10px"> <input type="submit" value="Create Project" tabindex="-1" style="border-radius:18px"  class="ui-button ui-widget ui-state-default ui-corner-all"> </span>
                       <span style="padding:10px"><input id="cancel_button" type="button" value="Cancel" tabindex="-1" style="border-radius:18px" > </span>
                    </div>
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