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

public class StudentViewBooksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Only students can access this page
        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !role.equals("student")) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM books";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Map<String, Object>> bookList = new ArrayList<>();

            while (rs.next()) {
                Map<String, Object> book = new HashMap<>();
                book.put("book_id", rs.getInt("book_id"));
                book.put("title", rs.getString("title"));
                book.put("author", rs.getString("author"));
                book.put("total_quantity", rs.getInt("total_quantity"));
                book.put("available_quantity", rs.getInt("available_quantity"));
                bookList.add(book);
            }

            request.setAttribute("books", bookList);

            RequestDispatcher rd = request.getRequestDispatcher("student_view_books.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
