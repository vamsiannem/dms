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
</head>
<body onload="expandAll('dhtmlgoodies_tree2');return false">

<div class="header1">
	<span style="float:left"><img src="" style="color:#aaa"/></span>
	<span style="float:right;padding-top:0px "><a href="" style="color:#aaa">Profile</a>&nbsp;&nbsp;&nbsp;<a href="" style="color:#aaa">Help</a>&nbsp;&nbsp;&nbsp;<a href="" style="color:#aaa">Admin</a></span><br>
	<h2>Upload Product Data</h2>

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
	<div style="float:right;padding-right: 15px;"><input type="submit" name="submit" value="Page buttons" class="button" /></div>
	</div>
	<div style="border-bottom: 2px solid #cfcfcf;border-radius: 3px 3px 0 0;padding-top: 52px">
	</div>

</div>
<div style="padding-left: 15px;padding-top: 4px;float:left;">
    <div style="float:left;width: 600px;height: 350px;border: 2px solid #fff;"><span style="padding:5px;">
      <div>
        <form name="file_upload_form" id="file_upload_form" action="<%= request.getContextPath() %>/api/product/upload" method="post" enctype="multipart/form-data">
            <div class="content">
                <div>
                    <input type="text" name="companyName" class="input_field" placeholder="Company Name">
                </div>
                <div>
                    <input type="text" name="unitSerialNo" class="input_field" placeholder="Unit Serial No">
                </div>
                <div>
                    <p class="form">
                        <input type="text" id="path" class="input_field" placeholder="Select a File to Upload">
                    	<label class="add-photo-btn">upload<span><input type="file" id="myfile" name="myfile"></span>
                    </label>
                    </p>
                </div>
                <div>
    	            <input type="submit" value="Submit" class="button">
    	        </div>
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
	<span class="copylink">copyright A @ 2004-2015. All Rights reserved</span>
	<span class="contactus"><a href="" style="color:#aaa">Contact Us</a>&nbsp;&nbsp;&nbsp;<a href="" style="color:#aaa">Privacy Policy</a>&nbsp;&nbsp;&nbsp;<a href="" style="color:#aaa">Terms of use</a></span>
</div>
<div>
    <form name="common-form"  id="common-form" action="<%= request.getContextPath() %>" method="post"></form>
 </div>
</body>
</html>