package com.cabService.controller;

import com.cabService.dao.CustomerDAO;
import com.cabService.dao.ManagementDAO;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

public class LoginServlet extends HttpServlet {

    // Handling POST request for login
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Get the session object
        HttpSession session = request.getSession();

        // Check in Customer Table
        CustomerDAO customerDAO = new CustomerDAO();
        int customerId = customerDAO.validateCustomer(email, password, session); // Pass session here
        if (customerId > 0) {
            session.setAttribute("userId", customerId);
            response.sendRedirect("pages/customerDashboard.jsp?message= Welcome Customer!");
            return;
        }

        // Check in Management Table
        ManagementDAO managementDAO = new ManagementDAO();
        int managementId = managementDAO.validateManagement(email, password, session); // Pass session here
        if (managementId > 0) {
            session.setAttribute("userId", managementId);
            response.sendRedirect("pages/managementDashboard.jsp?message= Welcome Manager!");
            return;
        } else {
            // If login fails
            response.sendRedirect("pages/login.jsp?message=Invalid credentials");
        }
    }
}
