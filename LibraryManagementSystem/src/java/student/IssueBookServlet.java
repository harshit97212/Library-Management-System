/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package student;

import db.DBConnection;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class IssueBookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Only students can issue books
        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !role.equals("student")) {
            response.sendRedirect("login.jsp");
            return;
        }

        int studentId = (int) request.getSession().getAttribute("studentId");
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        try {
            Connection con = DBConnection.getConnection();

            // 1️⃣ Check if book has available quantity
            String checkQty = "SELECT available_quantity FROM books WHERE book_id=?";
            PreparedStatement ps1 = con.prepareStatement(checkQty);
            ps1.setInt(1, bookId);
            ResultSet rs = ps1.executeQuery();

            int available = 0;
            if (rs.next()) {
                available = rs.getInt("available_quantity");
            }

            if (available <= 0) {
                response.getWriter().println("Book not available!");
                return;
            }

            // 2️⃣ Insert into issued_books
            String insert = "INSERT INTO issued_books(student_id, book_id, issue_date, return_date) VALUES (?, ?, ?, ?)";
            PreparedStatement ps2 = con.prepareStatement(insert);

            LocalDate issueDate = LocalDate.now();
            LocalDate returnDate = issueDate.plusDays(7); // student must return in 7 days

            ps2.setInt(1, studentId);
            ps2.setInt(2, bookId);
            ps2.setDate(3, java.sql.Date.valueOf(issueDate));
            ps2.setDate(4, java.sql.Date.valueOf(returnDate));
            ps2.executeUpdate();

            // 3️⃣ Reduce quantity
            String updateQty = "UPDATE books SET available_quantity = available_quantity - 1 WHERE book_id=?";
            PreparedStatement ps3 = con.prepareStatement(updateQty);
            ps3.setInt(1, bookId);
            ps3.executeUpdate();

            // 4️⃣ Redirect back to books page
            response.sendRedirect("StudentViewBooksServlet");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
