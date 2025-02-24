/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.cabService.service;
import com.cabService.dao.CustomerDAO;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author vihan
 */
public class RegisterServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nic = request.getParameter("nic");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        CustomerDAO customerDAO = new CustomerDAO();
        boolean isRegistered = customerDAO.registerCustomer(nic, name, email, password, phone);

       if (isRegistered) {
    response.sendRedirect("pages/login.jsp?message=Registration successful! You can now log in.");
}
 else {
            response.sendRedirect("pages/register.jsp?message=Registration failed. Try again.");
        }
    }

}
