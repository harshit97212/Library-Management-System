<%-- 
    Document   : logout
    Created on : 8 Dec 2025, 3:59:41?am
    Author     : abhi
--%>

<%
    session.invalidate();  // Destroy all session data
    response.sendRedirect("login.jsp");  // Redirect to login page
%>

