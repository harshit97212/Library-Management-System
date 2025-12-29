<%-- 
    Document   : addBook
    Created on : 8 Dec 2025, 3:43:01 am
    Author     : abhi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Book</title>
    <style>
        body {
            font-size: 20px; 
        }
    </style>
</head>
<body>

<%
    String role = (String) session.getAttribute("role");
    if (role == null || !role.equals("admin")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<h2>Add New Book</h2>

<form action="AddBookServlet" method="post">

    <label>Title:</label><br>
    <input type="text" name="title" required><br><br>

    <label>Author:</label><br>
    <input type="text" name="author" required><br><br>

    <label>Total Quantity:</label><br>
    <input type="number" name="quantity" min="1" required><br><br>

    <button type="submit">Add Book</button>
</form>

<br>
<a href="dashboard.jsp">Back to Dashboard</a>

</body>
</html>

