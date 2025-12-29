/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlets;

import db.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String role = request.getParameter("role");

        try {
            Connection con = DBConnection.getConnection();

            // ------------------------------
            //          ADMIN LOGIN
            // ------------------------------
            if (role.equals("admin")) {

                String sql = "SELECT * FROM admin WHERE username=? AND password=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, user);
                ps.setString(2, pass);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    // ADMIN SESSION
                    HttpSession session = request.getSession();
                    session.setAttribute("adminId", rs.getInt("admin_id"));
                    session.setAttribute("adminUsername", rs.getString("username"));
                    session.setAttribute("role", "admin");  // FIX ADDED

                    response.sendRedirect("dashboard.jsp");
                } else {
                    out.println("Invalid Admin Credentials!");
                }

            } 
            // ------------------------------
            //         STUDENT LOGIN
            // ------------------------------
            else if (role.equals("student")) {

                // Email validation
                if (!user.endsWith("@kiet.edu")) {
                    out.println("Only @kiet.edu email allowed!");
                    return;
                }

                // Check if student exists
                String sql = "SELECT * FROM students WHERE email=? AND password=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, user);
                ps.setString(2, pass);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    
                    // STUDENT EXISTS → LOGIN
                    HttpSession session = request.getSession();
                    session.setAttribute("studentId", rs.getInt("student_id"));
                    session.setAttribute("studentName", rs.getString("name"));
                    session.setAttribute("studentEmail", rs.getString("email"));
                    session.setAttribute("role", "student");  // FIX ADDED

                    response.sendRedirect("dashboard.jsp");
                } 
                else {
                    // STUDENT DOES NOT EXIST → AUTO REGISTER
                    String insert = "INSERT INTO students(name, email, password) VALUES (?, ?, ?)";
                    PreparedStatement ins = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
                    ins.setString(1, user.split("@")[0]);
                    ins.setString(2, user);
                    ins.setString(3, pass);
                    ins.executeUpdate();

                    ResultSet genId = ins.getGeneratedKeys();
                    int newId = 0;

                    if (genId.next()) newId = genId.getInt(1);

                    // CREATE SESSION
                    HttpSession session = request.getSession();
                    session.setAttribute("studentId", newId);
                    session.setAttribute("studentName", user.split("@")[0]);
                    session.setAttribute("studentEmail", user);
                    session.setAttribute("role", "student");  // FIX ADDED

                    response.sendRedirect("dashboard.jsp?new=true");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
