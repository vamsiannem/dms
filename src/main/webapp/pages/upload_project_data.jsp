<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>
        DMS - Data Management Software
    </title>
     	<script language="javascript" type="text/javascript">
            var ctx = "${pageContext.request.contextPath}";
            var projects= JSON.parse('${projects}');
            var projectsDataRange = JSON.parse('${projects_data_time_range}');
        </script>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/static/css/sample_style.css" />
     <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/static/css/file_upload.css" />
          <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/folder-tree-static.css" type="text/css">
     	<link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/context-menu.css" type="text/css">
     	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/static/css/jquery-ui.css">
     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/ajax.js"></script>
     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/folder-tree-static.js"></script>
     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/context-menu.js"></script>
     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/jquery-1.11.1.js"></script>
     	<script type="text/javascript" charset="utf8" src="<%= request.getContextPath() %>/static/js/jquery-ui.js"></script>
     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/common.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/static/js/upload_project_data.js"></script>
</head>
<body onload="expandAll('dhtmlgoodies_tree2');return false">

<div class="header1">
	<span style="float:left"><img src=""/></span>
    	<span class='options' ><a class="ref_option" href="" >Profile</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="" >Help</a>&nbsp;&nbsp;&nbsp;<a href="" class="ref_option">Admin</a></span>
	<h2>Upload Unit Data</h2>

</div>
<div>
  <div class='leftNavMenu'>
    <jsp:include page="left_nav.jsp"/>

  </div>
    <div>

        <input type="hidden" id="statusMessage" name="statusMessage" value="${flag}-${status}"/>
	<div style="float:left; padding-left:15px;" id="statusWrapper">
    </div>
	<div style="float:right;padding-right: 15px;"><input type="submit" name="submit" value="Page buttons" class="button" /></div>
	</div>
	<div style="border-bottom: 2px solid #cfcfcf;border-radius: 3px 3px 0 0;padding-top: 52px">
	</div>

</div>
<div style="padding-left: 15px;padding-top: 4px;float:left; width:80%">
    <div><h3 style="margin-left:17%">Upload CSV Data</h3></div>
    <div style="float:left;width: 65%;height: 350px;border: 2px solid #fff;"><span style="padding:5px;">
      <div>
        <form name="file_upload_form" id="file_upload_form" action="<%= request.getContextPath() %>/api/project/data/import" method="post" enctype="multipart/form-data" style="position:relative; height:180px">
            <div class="content" >
                <div style="margin-bottom:10px;">
                    <label style="padding-right:27px;">Project:</label>
                    <span>
                        <select class="input_field" style='width:240px;'  id="networkUnitSelect" name="networkUnitSelect">
                            <option value="----">-- Please Select --</option>
                        </select>
                    </span>
                    <div id="wrapper" style='position:fixed; top: 115px; left: 592px; z-index:5' ></div>
                </div>
                <div style='margin-top:5px;'>
                    <p class="form">
                        <label style='padding-right:15px;'>File Path:</label>
                        <input type="text" id="fileName" name="fileName" class="input_field" placeholder="Select a File to Upload">
                    	<label class="add-photo-btn">Load File
                    	    <span><input type="file" id="myfile" name="myfile"></span>
                        </label>
                    </p>
                </div>
    	    </div>
    	    <div style="margin-left:125px">
    	            <input type="submit" value="Upload File" class="button">
    	            <input type="reset" value="Reset" class="button" style="margin-left:10px">
    	    </div>
    	</form>
      </div>
      	</span></div>
    <div style="float:right;width: 30%;height: 350px;border: 2px solid #fff; ">
        <div id="header" style="text-align:center;text-decoration:underline;font-weight:bold;padding:6px;">
            <span> Project info </span>
        </div>
        <div id="content">
            <div style='margin-top:5px; padding:5px 5px;'>
                <div class="row">
                <label for="info_projectId" >
                    Project Id:
                </label>
                <div id="info_projectId" > None</div>
                </div>
                <div class="row">
                <label  for="info_client" title="Selected project data core measurements captured till this date/time.">
                    Client:
                </label>
                    <div id="info_client" > None</div>
                </div>
                <div class="row">
                <label for="info_platform" >
                    Platform:
                </label>
                    <div id="info_platform" > None</div>
                </div>
                <div class="row">
                <label for="info_controlSystem" title="Selected project data core measurements captured till this date/time.">
                    <span>Control System:</span>
                </label>
                    <div id="info_controlSystem" > None</div>
                </div>
                <div class="row">
                <label for="info_channel" >
                    <span>Channel:</span>
                </label>
                    <div id="info_channel" > None</div>
                </div>
                <div class="row">
                <label for="info_unitSerialNo" title="Selected project data core measurements captured till this date/time.">
                    <span>Unit Serial No:</span>
                </label>
                    <div id="info_unitSerialNo" > None</div>
                </div>
                <div class="row">
                <label for="dataStartDate" >
                    <span>Start Time:</span>
                </label>
                    <div id="dataStartDate" > None</div>
                </div>
                <div class="row">
                <label for="dataEndDate" >
                    <span>End Time:</span>
                </label>
                    <div id="dataEndDate" > None</div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>

<div class="footer1">
	<span class="copylink">Copyright @ 20014-2016. All Rights reserved</span>
	<span class="contactus options"><a class="ref_option" href="">Contact Us</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="" >Privacy Policy</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="" >Terms of use</a></span>
</div>
<div>
    <form name="common-form"  id="common-form" action="<%= request.getContextPath() %>" method="post"></form>
 </div>
</body>
</html>