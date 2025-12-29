<%-- 
    Document   : issued_books
    Created on : 8 Dec 2025, 3:57:14 am
    Author     : abhi
--%>

<%@page import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>My Issued Books</title>
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

    List<Map<String, Object>> list =
        (List<Map<String, Object>>) request.getAttribute("issuedBooks");
%>

<h2>My Issued Books</h2>

<table border="1" cellpadding="7">
    <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Issued Date</th>
        <th>Return Date</th>
        <th>Action</th>
    </tr>

    <% for (Map<String, Object> row : list) { %>
    <tr>
        <td><%= row.get("title") %></td>
        <td><%= row.get("author") %></td>
        <td><%= row.get("issue_date") %></td>
        <td><%= row.get("return_date") %></td>

        <td>
            <a href="ReturnBookServlet?issueId=<%= row.get("issue_id") %>">
                <button>Return</button>
            </a>
        </td>
    </tr>
    <% } %>
</table>

<br>
<a href="dashboard.jsp">Back to Dashboard</a>

</body>
</html>

