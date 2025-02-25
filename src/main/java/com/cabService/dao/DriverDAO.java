/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cabService.dao;

import java.sql.*;
import java.util.*;
import com.cabService.dao.DBConnection;

public class DriverDAO {
    private Connection conn;

    public DriverDAO(Connection conn) {
        this.conn = conn;
    }

    // Add a new driver
    public void addDriver(String nic, String name, String email, String phone, String licenseNumber, String vehicleType, String vehicleModel) throws SQLException {
        String sql = "INSERT INTO Drivers (NIC, Name, Email, Phone, LicenseNumber, VehicleType, VehicleModel) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nic);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, licenseNumber);
            stmt.setString(6, vehicleType);
            stmt.setString(7, vehicleModel);
            stmt.executeUpdate();
        }
    }

    // Get all drivers
    public List<String[]> getDrivers() throws SQLException {
        List<String[]> drivers = new ArrayList<>();
        String sql = "SELECT * FROM Drivers";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String[] driver = new String[10];
                driver[0] = String.valueOf(rs.getInt("DriverID"));
                driver[1] = rs.getString("NIC");
                driver[2] = rs.getString("Name");
                driver[3] = rs.getString("Email");
                driver[4] = rs.getString("Phone");
                driver[5] = rs.getString("LicenseNumber");
                driver[6] = rs.getString("VehicleType");
                driver[7] = rs.getString("VehicleModel");
                driver[9] = rs.getString("Status");
                drivers.add(driver);
            }
        }
        return drivers;
    }

    // Update a driver
    public void updateDriver(int driverID, String name, String email, String phone, String vehicleModel, String status) throws SQLException {
        String sql = "UPDATE Drivers SET Name=?, Email=?, Phone=?, VehicleModel=?, Status=? WHERE DriverID=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, vehicleModel);
            stmt.setString(5, status);
            stmt.setInt(6, driverID);
            stmt.executeUpdate();
        }
    }

    // Delete a driver
    public void deleteDriver(int driverID) throws SQLException {
        String sql = "DELETE FROM Drivers WHERE DriverID=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, driverID);
            stmt.executeUpdate();
        }
    }
    
    // Get driver details by ID
public String[] getDriverByID(int driverID) throws SQLException {
    String sql = "SELECT * FROM Drivers WHERE DriverID=?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, driverID);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String[] driver = new String[9];
                driver[0] = String.valueOf(rs.getInt("DriverID"));
                driver[1] = rs.getString("NIC");
                driver[2] = rs.getString("Name");
                driver[3] = rs.getString("Email");
                driver[4] = rs.getString("Phone");
                driver[5] = rs.getString("LicenseNumber");
                driver[6] = rs.getString("VehicleType");
                driver[7] = rs.getString("VehicleModel");
                driver[8] = rs.getString("Status");
                return driver;
            }
        }
    }
    return null;
}
}