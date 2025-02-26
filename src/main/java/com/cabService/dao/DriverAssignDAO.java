package com.cabService.dao;

import java.sql.*;
import java.util.*;

public class DriverAssignDAO {

    // ✅ Get pending bookings
    public static List<Map<String, String>> getPendingBookings() {
        List<Map<String, String>> pendingBookings = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT b.BookingID, b.CustomerID, b.PickupLocation, b.DropoffLocation , p.VehicleType " +
                         "FROM bookings b " +
                         "JOIN ridepackages p ON b.PackageID = p.PackageID " +
                         "WHERE b.Status = 'Pending'";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> booking = new HashMap<>();
                booking.put("BookingID", rs.getString("BookingID"));
                booking.put("CustomerID", rs.getString("CustomerID"));
                booking.put("PickupLocation", rs.getString("PickupLocation"));
                booking.put("DropoffLocation", rs.getString("DropoffLocation"));
                booking.put("VehicleType", rs.getString("VehicleType"));
                pendingBookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return pendingBookings;
    }

    // ✅ Get available drivers for a specific vehicle type
    public static List<Map<String, String>> getAvailableDrivers(String vehicleType) {
        List<Map<String, String>> availableDrivers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT DriverID, Name FROM Drivers WHERE Status = 'Available' AND VehicleType = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, vehicleType);
            rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> driver = new HashMap<>();
                driver.put("DriverID", rs.getString("DriverID"));
                driver.put("Name", rs.getString("Name"));
                availableDrivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return availableDrivers;
    }

    // ✅ Utility method to close resources
    private static void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
