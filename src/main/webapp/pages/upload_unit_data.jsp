<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>
        DMS - Data Management Software
    </title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/static/css/sample_style.css" />
     <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/static/css/file_upload.css" />
          <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/folder-tree-static.css" type="text/css">
     	<link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/context-menu.css" type="text/css">
     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/ajax.js"></script>
     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/folder-tree-static.js"></script>
     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/context-menu.js"></script>
     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/jquery-1.11.1.js"></script>
     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/common.js"></script>
     	<script language="javascript" type="text/javascript">
            var networkUnits= JSON.parse('${networkUnits}');

        </script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/static/js/upload_unit_data.js"></script>
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
    <div><h3 style="text-align:center">Upload CSV Data</h3></div>
    <div style="float:left;width: 600px;height: 350px;border: 2px solid #fff;"><span style="padding:5px;">
      <div>
        <form name="file_upload_form" id="file_upload_form" action="<%= request.getContextPath() %>/api/unit/data/upload" method="post" enctype="multipart/form-data" style="position:relative; height:180px">
            <div class="content" >
                <div>
                    <label>Network Unit:
                    <span>
                        <select class="input_field" style='width:240px;'  id="networkUnitSelect" name="networkUnitSelect">
                            <option value="----">-- Please Select --</option>
                        </select>
                    </span>
                    <div id="wrapper" style='position:fixed; top: 115px; left: 592px; z-index:5' ></div>
                </div>
                <div style='margin-top:5px;'>
                    <p class="form">
                        <label style='padding-right:15px;'>Select File:</label>
                        <input type="text" id="path" class="input_field" placeholder="Select a File to Upload">
                    	<label class="add-photo-btn">Upload
                    	    <span><input type="file" id="myfile" name="myfile"></span>
                        </label>
                    </p>
                </div>
    	    </div>
    	    <div style="margin-left:125px">
    	            <input type="submit" value="Submit" class="button">
    	            <input type="reset" value="Reset" class="button" style="margin-left:10px">
    	    </div>
    	</form>
      </div>
      	</span></div>
    <div style="float:right;width: 250px;height: 350px;border: 2px solid #fff">
	Project info
    </div>
</div>

<div style="padding-top: 10px;float:left;padding-right: 100px;padding-left: 15px;">

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