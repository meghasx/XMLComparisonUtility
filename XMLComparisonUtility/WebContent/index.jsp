<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hello</title>
</head>
<body>
<h1>Hello JSP and Servlet!</h1>
<form action="xmlComparer" method="post">
    Enter path for XML1: <input type="text" name="xml1" size="20" required>
    Enter path for XML2: <input type="text" name="xml2" size="20" required>
    <input type="submit" value="Call Servlet" />
</form>
</body>
</html>