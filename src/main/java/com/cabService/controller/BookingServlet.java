package com.cabService.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.cabService.dao.BookingDAO;
import com.cabService.dao.DBConnection;

public class BookingServlet extends HttpServlet {
   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            String pickupLocation = request.getParameter("pickupLocation");
            String dropoffLocation = request.getParameter("dropoffLocation");
            int packageId = Integer.parseInt(request.getParameter("packageId"));

            boolean success = BookingDAO.addBooking(customerId, pickupLocation, dropoffLocation, packageId);
            if (success) {
                response.sendRedirect("pages/customerDashboard.jsp?message=Ride request submitted successfully!");
            } else {
                response.sendRedirect("pages/rideRequest.jsp?message=Failed to request ride.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/rideRequest.jsp?message=Invalid input.");
        }
    }
   
    
}
