/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cabService.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BookingDAO {
     public static boolean addBooking(int customerId, String pickupLocation, String dropoffLocation, int packageId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement packageStmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            // Fetch vehicle type and price based on PackageID
            String packageQuery = "SELECT VehicleType, Price FROM RidePackages WHERE PackageID = ?";
            packageStmt = conn.prepareStatement(packageQuery);
            packageStmt.setInt(1, packageId);
            rs = packageStmt.executeQuery();

            if (!rs.next()) {
                return false; // No matching package
            }

            String vehicleType = rs.getString("VehicleType");
            double price = rs.getDouble("Price");

            // Insert booking
            String insertQuery = "INSERT INTO Bookings (CustomerID, PickupLocation, DropoffLocation, VehicleType, PackageID, Price, Status) VALUES (?, ?, ?, ?, ?, ?, 'Pending')";
            pstmt = conn.prepareStatement(insertQuery);
            pstmt.setInt(1, customerId);
            pstmt.setString(2, pickupLocation);
            pstmt.setString(3, dropoffLocation);
            pstmt.setString(4, vehicleType);
            pstmt.setInt(5, packageId);
            pstmt.setDouble(6, price);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (packageStmt != null) packageStmt.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
