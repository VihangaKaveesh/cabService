 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.cabService.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.cabService.dao.DriverDAO;
import com.cabService.dao.DBConnection;


public class DriverServlet extends HttpServlet {
    
    private DriverDAO driverDAO;

    //connecting to dtabase
    public void init() throws ServletException {
        try {
            Connection conn = DBConnection.getConnection();
            driverDAO = new DriverDAO(conn);
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    // crud functions of the drivers
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                addDriver(request, response);
            } else if ("update".equals(action)) {
                updateDriver(request, response);
            } else if ("delete".equals(action)) {
                deleteDriver(request, response);
            } else {
                response.sendRedirect("manageDrivers.jsp");
            }
        } catch (SQLException e) {
            throw new ServletException("Database operation failed", e);
        }
    }

    //add driver part 
    private void addDriver(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String nic = request.getParameter("nic");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String licenseNumber = request.getParameter("licenseNumber");
        String vehicleType = request.getParameter("vehicleType");
        String vehicleModel = request.getParameter("vehicleModel");

        driverDAO.addDriver(nic, name, email, phone, licenseNumber, vehicleType, vehicleModel);
        response.sendRedirect("pages/manageDrivers.jsp?message= Driver added ");
    }

    //edit driver part
    private void updateDriver(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int driverID = Integer.parseInt(request.getParameter("driverID"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String vehicleModel = request.getParameter("vehicleModel");
        String status = request.getParameter("status");

        driverDAO.updateDriver(driverID, name, email, phone, vehicleModel, status);
        response.sendRedirect("pages/manageDrivers.jsp?message= Driver edited successfully");
    }

    //delete driver art
    private void deleteDriver(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int driverID = Integer.parseInt(request.getParameter("driverID"));
        driverDAO.deleteDriver(driverID);
        response.sendRedirect("pages/manageDrivers.jsp?message= Driver deleted successfully");
    }

    //displaying the drivers part
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<String[]> drivers = driverDAO.getDrivers();
            request.setAttribute("drivers", drivers);
            request.getRequestDispatcher("manageDrivers.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Failed to fetch drivers", e);
        }
    }
}