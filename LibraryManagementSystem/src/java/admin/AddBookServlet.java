/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package admin;

import db.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class AddBookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !role.equals("admin")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        int qty = Integer.parseInt(request.getParameter("quantity"));

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO books(title, author, total_quantity, available_quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, qty);
            ps.setInt(4, qty);

            ps.executeUpdate();

            response.sendRedirect("dashboard.jsp?added=true");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
