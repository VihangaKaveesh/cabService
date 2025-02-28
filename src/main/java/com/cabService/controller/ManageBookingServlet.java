/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.cabService.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.cabService.dao.BookingDAO;

/**
 *
 * @author vihan
 */
public class ManageBookingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int bookingID = Integer.parseInt(request.getParameter("bookingID"));
            String action = request.getParameter("action");

            String status = "";
            if ("Complete".equals(action)) {
                status = "Completed";
            } else if ("Reject".equals(action)) {
                status = "Rejected";
            }

            boolean success = BookingDAO.updateBookingStatus(bookingID, status);

            if (success) {
                response.sendRedirect("pages/manageBookings.jsp?message=Booking updated successfully!");
            } else {
                response.sendRedirect("pages/manageBookings.jsp?message=Failed to update booking.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/manageBookings.jsp?message=Invalid request.");
        }
    }

}
