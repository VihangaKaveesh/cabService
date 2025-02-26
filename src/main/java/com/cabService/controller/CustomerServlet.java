package com.cabService.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.cabService.dao.CustomerDAO;

@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
    
    private CustomerDAO customerDAO;

    // Connecting to the database
    public void init() throws ServletException {
        customerDAO = new CustomerDAO();
    }

    // CRUD functions of the customer
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                addCustomer(request, response);
            } else if ("update".equals(action)) {
               
                int customerID = Integer.parseInt(request.getParameter("customerID"));
        String nic = request.getParameter("nic");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        CustomerDAO customerDAO = new CustomerDAO();
        boolean success = customerDAO.updateCustomer(customerID, nic, name, email, password, phone);

        if (success) {
            response.sendRedirect("pages/manageCustomers.jsp?message=updated the customer details");
        } else {
            response.sendRedirect("editCustomer.jsp?customerID=" + customerID + "&error=failed");
        }
            } 
            
            else if ("delete".equals(action)) {
                deleteCustomer(request, response);
            } else {
                response.sendRedirect("pages/manageCustomers.jsp");
            }
        } catch (SQLException e) {
            throw new ServletException("Database operation failed", e);
        }
    }

    // Add customer part
    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String nic = request.getParameter("nic");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        boolean success = customerDAO.registerCustomer(nic, name, email, password, phone);
        if (success) {
            response.sendRedirect("manageCustomers.jsp?message=Customer added successfully");
        } else {
            response.sendRedirect("manageCustomers.jsp?message=Failed to add customer");
        }
    }

    // Update customer part
    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String nic = request.getParameter("nic");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        boolean success = customerDAO.updateCustomer(customerId, nic, name, email, password, phone);
        if (success) {
            response.sendRedirect("pages/manageCustomers.jsp?message=Customer updated successfully");
        } else {
            response.sendRedirect("pages/manageCustomers.jsp?message=Failed to update customer");
        }
    }

    // Delete customer part
   private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
    int customerId = Integer.parseInt(request.getParameter("customerID"));  // Ensure case matches in JSP
    boolean success = customerDAO.deleteCustomer(customerId);
    response.sendRedirect("pages/manageCustomers.jsp?message=" + (success ? "Customer deleted successfully" : "Failed to delete customer"));
}


    // Displaying customer details part
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
            String action = request.getParameter("action");
            if ("edit".equals(action)) {
                int customerId = Integer.parseInt(request.getParameter("customerId"));
                String[] customer = customerDAO.getCustomerById(customerId);
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("editCustomer.jsp").forward(request, response);
            } else {
                List<String[]> customers = customerDAO.getAllCustomers();
                request.setAttribute("customers", customers);
                request.getRequestDispatcher("manageCustomers.jsp").forward(request, response);
            }
        
    }
        
 
}
