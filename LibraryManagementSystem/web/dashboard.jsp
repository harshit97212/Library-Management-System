<%-- 
    Document   : dashboard
    Created on : 8 Dec 2025, 3:42:05 am
    Author     : abhi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Library Management System</title>
    <style>
        body {
            font-size: 20px; 
        }
    </style>
</head>
<body>

<%
    String role = (String) session.getAttribute("role");

    if (role == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String name = "";
    if (role.equals("student")) {
        name = (String) session.getAttribute("studentName");
    }
%>

<h2>
    Welcome, 
    <%= role.equals("admin") ? "Admin" : name %>!
</h2>

<hr>

<%
    if (role.equals("admin")) {
%>
    <!-- ADMIN MENU -->
    <h3>Admin Controls</h3>
    <ul>
        <li><a href="addBook.jsp">Add New Book</a></li>
        <li><a href="AdminViewBooksServlet">View All Books</a></li>
        <li><a href="logout.jsp">Logout</a></li>
    </ul>

<%
    } else {
%>
    <!-- STUDENT MENU -->
    <h3>Student Controls</h3>
    <ul>
        <li><a href="StudentViewBooksServlet">View All Books</a></li>
        <li><a href="IssuedBooksServlet">My Issued Books</a></li>
        <li><a href="logout.jsp">Logout</a></li>
    </ul>

<%
    }
%>

</body>
</html>
