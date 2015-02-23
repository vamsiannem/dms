
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>
        DMS - Data Management System
    </title>

    <link rel="stylesheet" type="text/css" href="../static/css/sample_style.css" />
</head>
<body>
<div class="header1">
	<span style="float:left"><img src="" alt="DMS Logo"/></span>
	<h2>Data Management Software</h2>
</div>
<div id="wrapper">

	<form name="login-form" class="login-form" action="<%= request.getContextPath() %>/api/login" method="post">
		<div class="header">
    		<h1>Welcome to DMS</h1>
	    	<span>Please sign in</span>
		</div>
		<div>
		    <span style="float:left;color:red;font-size: 13px;">
		        <c:if test="${message ne null}">
		            <c:out value="${message}"> </c:out>
		        </c:if>
		    </span>
		</div>
		<div class="content">
		    <input name="username" type="text" class="input username" placeholder="Username" />
		    <input name="password" type="password" class="input password" placeholder="Password" />
		</div>

		<div class="footer">
		    <input type="submit" name="login" value="Sign In" class="button" />
		    <input type="button" name="register" value="Register" class="register" />

		</div>
	
	</form>

</div>
<!--
<form action="" name="form2" class="form2" method="POST" >
	<div class="login-help">
		<input type="submit" name="submit" value="Register" class="button" />
	</div>
</form>
-->
<div class="footer1">
	<span class="copylink">Copyright @ 20014-2016. All Rights reserved</span>
	<span class="contactus options"><a class="ref_option" href="">Contact Us</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="" >Privacy Policy</a>&nbsp;&nbsp;&nbsp;<a class="ref_option" href="" >Terms of use</a></span>
</div>
</body>
</html>



