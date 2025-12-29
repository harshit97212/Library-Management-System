/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package student;

import db.DBConnection;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ReturnBookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !role.equals("student")) {
            response.sendRedirect("login.jsp");
            return;
        }

        int issueId = Integer.parseInt(request.getParameter("issueId"));

        try {
            Connection con = DBConnection.getConnection();

            // 1️⃣ Get the book ID from issued_books
            String sql1 = "SELECT book_id FROM issued_books WHERE issue_id=?";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.setInt(1, issueId);
            ResultSet rs = ps1.executeQuery();

            int bookId = 0;
            if (rs.next()) {
                bookId = rs.getInt("book_id");
            }

            // 2️⃣ Increase available quantity
            String sql2 = "UPDATE books SET available_quantity = available_quantity + 1 WHERE book_id=?";
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setInt(1, bookId);
            ps2.executeUpdate();

            // 3️⃣ Remove issued entry
            String sql3 = "DELETE FROM issued_books WHERE issue_id=?";
            PreparedStatement ps3 = con.prepareStatement(sql3);
            ps3.setInt(1, issueId);
            ps3.executeUpdate();

            // 4️⃣ Redirect back to issued list
            response.sendRedirect("IssuedBooksServlet");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
