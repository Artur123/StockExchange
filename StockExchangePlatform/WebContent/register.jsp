<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
	<h1>Register</h1>
	
	<form action="Login" method="post">
		<p>Enter username to register:</p>
		(if empty, user will continue as unregistered user)
		<p>
			<input type="text" name="user" /> <br>
		</p>
		<input type="submit" value="login">		
	</form>
</body>
</html>