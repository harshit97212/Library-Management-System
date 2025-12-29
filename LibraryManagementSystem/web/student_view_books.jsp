<%-- 
    Document   : student_view_books
    Created on : 8 Dec 2025, 3:50:16 am
    Author     : abhi
--%>

<%@ page import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Available Books</title>
    <style>
        body {
            font-size: 20px; 
        }
    </style>
</head>
<body>

<%
    String role = (String) session.getAttribute("role");
    if (role == null || !role.equals("student")) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Map<String, Object>> books = 
        (List<Map<String, Object>>) request.getAttribute("books");
%>

<h2>All Books</h2>

<table border="1" cellpadding="7">
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Author</th>
        <th>Total Quantity</th>
        <th>Available</th>
        <th>Action</th>
    </tr>

    <% for (Map<String, Object> b : books) { %>
    <tr>
        <td><%= b.get("book_id") %></td>
        <td><%= b.get("title") %></td>
        <td><%= b.get("author") %></td>
        <td><%= b.get("total_quantity") %></td>
        <td><%= b.get("available_quantity") %></td>

        <td>
            <% if ((int)b.get("available_quantity") > 0) { %>
                <a href="IssueBookServlet?bookId=<%= b.get("book_id") %>">
                    <button>Issue</button>
                </a>
            <% } else { %>
                <button disabled>Not Available</button>
            <% } %>
        </td>
    </tr>
    <% } %>
</table>

<br>
<a href="dashboard.jsp">Back to Dashboard</a>

</body>
</html>

