/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.cabService.controller;
import com.cabService.dao.CustomerDAO;
import com.cabService.dao.ManagementDAO;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        // Check in Customer Table
        CustomerDAO customerDAO = new CustomerDAO();
        int customerId = customerDAO.validateCustomer(email, password);
        if (customerId > 0) {
            session.setAttribute("userId", customerId);
            session.setAttribute("role", "customer");
            response.sendRedirect("pages/customerDashboard.jsp?message= Welcome Customer!");
            return;
        }

        // Check in Management Table
        ManagementDAO managementDAO = new ManagementDAO();
        int managementId = managementDAO.validateManagement(email, password);
        if (managementId > 0) {
            session.setAttribute("userId", managementId);
            session.setAttribute("role", "management");
            response.sendRedirect("pages/managementDashboard.jsp?message= Welcome Manager!");
            return;
        }else {
         // If login fails
        response.sendRedirect("pages/login.jsp?message=Invalid credentials");
        }

        
       
    }
   
}

