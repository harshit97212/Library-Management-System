<%-- 
    Document   : login
    Created on : 8 Dec 2025, 3:01:06 am
    Author     : abhi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Library Management System</title>
    <style>
        body {
            font-size: 20px; 
        }
    </style>
</head>
<body>
    
    <h1>Library Management System</h1>

<h2>Login</h2>

<form action="LoginServlet" method="post">
    
    
    <label>Login As:</label><br>
    <select name="role" required>
        <option value="student">Student</option>
        <option value="admin">Admin</option>
    </select>
    
    <br><br><br>

    <label>Email / Username:</label><br>
    <input type="text" name="user" required><br><br>

    <label>Password:</label><br>
    <input type="password" name="pass" required><br><br>

    <button type="submit">Login</button>

</form>

</body>
</html>
