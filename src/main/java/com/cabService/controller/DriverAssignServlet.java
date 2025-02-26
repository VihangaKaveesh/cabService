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
import com.cabService.dao.DBConnection;
import jakarta.servlet.RequestDispatcher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author vihan
 */
public class DriverAssignServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookingID = Integer.parseInt(request.getParameter("bookingID"));
        int driverID = Integer.parseInt(request.getParameter("driverID"));
        
        BookingDAO bookingDAO = new BookingDAO();
        boolean success = bookingDAO.assignDriverToBooking(bookingID, driverID);
        
        if (success) {
            response.sendRedirect("pages/driverAssign.jsp?message=Driver assigned successfully");
        } else {
            response.sendRedirect("pages/driverAssign.jsp?message=Failed to assign driver");
        }
    }
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String[]> pendingBookings = new ArrayList<>(); // Using String array instead of model
        
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT b.BookingID, b.CustomerID, b.PickupLocation, b.DropoffLocation, p.VehicleType " +
                         "FROM Bookings b " +
                         "JOIN Packages p ON b.PackageID = p.PackageID " +
                         "WHERE b.Status = 'Pending'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] booking = new String[5];
                booking[0] = String.valueOf(rs.getInt("BookingID"));
                booking[1] = String.valueOf(rs.getInt("CustomerID"));
                booking[2] = rs.getString("PickupLocation");
                booking[3] = rs.getString("DropoffLocation");
                booking[4] = rs.getString("VehicleType");
                pendingBookings.add(booking);
            }

            request.setAttribute("pendingBookings", pendingBookings);
            RequestDispatcher dispatcher = request.getRequestDispatcher("driverAssign.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
}
