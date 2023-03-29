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
<form action="xmlComparer" method="post" enctype="multipart/form-data">
    Enter path for XML1: <input type="file" name="xml1" size="20" required>
    Enter path for XML2: <input type="file" name="xml2" size="20" required>
    <input type="submit" value="Compare" />
</form>
</body>
</html>