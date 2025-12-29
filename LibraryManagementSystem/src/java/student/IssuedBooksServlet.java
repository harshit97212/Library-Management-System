/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package student;

import db.DBConnection;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

public class IssuedBooksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !role.equals("student")) {
            response.sendRedirect("login.jsp");
            return;
        }

        int studentId = (int) request.getSession().getAttribute("studentId");

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT ib.issue_id, b.title, b.author, ib.issue_date, ib.return_date " +
                         "FROM issued_books ib JOIN books b ON ib.book_id=b.book_id " +
                         "WHERE ib.student_id=?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            List<Map<String, Object>> issuedList = new ArrayList<>();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("issue_id", rs.getInt("issue_id"));
                row.put("title", rs.getString("title"));
                row.put("author", rs.getString("author"));
                row.put("issue_date", rs.getDate("issue_date"));
                row.put("return_date", rs.getDate("return_date"));
                issuedList.add(row);
            }

            request.setAttribute("issuedBooks", issuedList);

            RequestDispatcher rd = request.getRequestDispatcher("issued_books.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
