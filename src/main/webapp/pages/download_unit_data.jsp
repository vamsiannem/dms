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
     	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/static/css/jquery-ui.css">

     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/ajax.js"></script>
     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/folder-tree-static.js"></script>
     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/context-menu.js"></script>
     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/jquery-1.11.1.js"></script>
     	<script type="text/javascript" charset="utf8" src="<%= request.getContextPath() %>/static/js/jquery-ui.js"></script>
     	<script type="text/javascript" src="<%= request.getContextPath() %>/static/js/common.js"></script>
     	<script language="javascript" type="text/javascript">
            var projects= JSON.parse('${projects}');

        </script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/static/js/download_unit_data.js"></script>

</head>
<body onload="expandAll('dhtmlgoodies_tree2');return false">

<div class="header1">
	<span style="float:left"><img src=""/></span>
    	<span class='options' ><a class="ref_option" href="" >Profile</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="" >Help</a>&nbsp;&nbsp;&nbsp;<a href="" class="ref_option">Admin</a></span>
	<h2>Export Unit Data</h2>

</div>
<div>
  <div class='leftNavMenu'>
    <jsp:include page="left_nav.jsp"/>

  </div>
    <div>

	<div style="float:left; margin-left:18px; margin-top:10px;" id="statusMessage">
        <span style='color:<c:out value="${flag}"> </c:out>;font-size: 18px;'>
                <c:out value="${status}"> </c:out>
        </span>
	</div>
	<div style="float:right;padding-right: 15px;"><input type="submit" name="submit" value="Page buttons" class="button" /></div>
	</div>
	<div style="border-bottom: 2px solid #cfcfcf;border-radius: 3px 3px 0 0;padding-top: 52px">
	</div>

</div>
<div style="padding-left: 15px;padding-top: 4px;float:left;width:80%">
    <div style="float:left;width: 70%;height: 350px;border: 2px solid #fff;"><span style="padding:5px;">
        <div><p class="validateTips"></p></div>
      <div>
        <form name="file_download_form" id="file_download_form" action="<%= request.getContextPath() %>/api/project/data/export" method="post" style="position:relative; height:180px">
            <div class="content" >
                <div>
                    <label>Network Unit:
                    <span>
                        <select class="input_field" style='width:340px;'  id="networkUnitSelect" name="networkUnitSelect">
                            <option value="----">-- Please Select --</option>
                        </select>
                    </span>
                    <div id="wrapper" style='position:fixed; top: 115px; left: 592px; z-index:5' ></div>
                </div>
                <div style='margin-top:5px;'>
                    <label class='dialog-label' for="from">
                        <span>Start Date:</span>
                        <input type="text" style='margin-left:3%;padding:10px' name="from" id="from" placeholder="From date" class="text ui-widget-content ui-corner-all"/>
                    </label>
                    <label class='dialog-label' for="to">
                        <span>End Date:</span>
                        <input type="text" style='margin-left:3.5%;padding:10px' name="to" id="to" placeholder="To date" class="text ui-widget-content ui-corner-all"/>
                    </label>
                </div>
                
    	    </div>
    	    <div style="margin-left:125px">
    	            <input type="submit" value="Submit" class="button">
    	            <input type="reset" value="Reset" class="button" style="margin-left:10px">
    	    </div>
    	</form>
      </div>
      	</span></div>
    <div style="float:right;width: 28%;height: 350px;border: 2px solid #fff">
	Project info
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