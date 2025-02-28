/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cabService.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BookingDAO {
     public static boolean addBooking(int customerId, String pickupLocation, String dropoffLocation, int packageId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement packageStmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            // Fetch vehicle type and price based on PackageID
//            String packageQuery = "SELECT VehicleType, Price FROM RidePackages WHERE PackageID = ?";
//            packageStmt = conn.prepareStatement(packageQuery);
//            packageStmt.setInt(1, packageId);
//            rs = packageStmt.executeQuery();
//
//            if (!rs.next()) {
//                return false; // No matching package
//            }

            String vehicleType = rs.getString("VehicleType");
            double price = rs.getDouble("Price");

            // Insert booking
            String insertQuery = "INSERT INTO Bookings (CustomerID, PickupLocation, DropoffLocation, PackageID, Price, Status) VALUES (?, ?, ?, ?, ?, 'Pending')";
            pstmt = conn.prepareStatement(insertQuery);
            pstmt.setInt(1, customerId);
            pstmt.setString(2, pickupLocation);
            pstmt.setString(3, dropoffLocation);
//            pstmt.setString(4, vehicleType);
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
     
       public boolean assignDriverToBooking(int bookingID, int driverID) {
        Connection conn = null;
        PreparedStatement psUpdateBooking = null;
        PreparedStatement psUpdateDriver = null;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Update booking: Assign driver and update status
            String updateBookingSQL = "UPDATE Bookings SET DriverID = ?, Status = 'Assigned' WHERE BookingID = ?";
            psUpdateBooking = conn.prepareStatement(updateBookingSQL);
            psUpdateBooking.setInt(1, driverID);
            psUpdateBooking.setInt(2, bookingID);
            psUpdateBooking.executeUpdate();

            // Update driver: Change status to 'Assigned'
            String updateDriverSQL = "UPDATE Drivers SET Status = 'Assigned' WHERE DriverID = ?";
            psUpdateDriver = conn.prepareStatement(updateDriverSQL);
            psUpdateDriver.setInt(1, driverID);
            psUpdateDriver.executeUpdate();

            conn.commit(); // Commit transaction
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); // Rollback in case of failure
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (psUpdateBooking != null) psUpdateBooking.close();
                if (psUpdateDriver != null) psUpdateDriver.close();
                if (conn != null) conn.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
       
         public static boolean updateBookingStatus(int bookingID, String status) {
        boolean updated = false;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Bookings SET Status = ? WHERE BookingID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, bookingID);

            int rowsAffected = ps.executeUpdate();
            updated = (rowsAffected > 0);

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
    } 
         
        
         // Fetch all bookings with package details
    public static List<HashMap<String, String>> getAllBookings() {
        List<HashMap<String, String>> bookings = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT b.BookingID, b.CustomerID, b.PickupLocation, b.DropoffLocation, b.DriverID, " +
                         "p.PackageName, p.VehicleType, p.Price, b.Status " +
                         "FROM Bookings b " +
                         "JOIN Packages p ON b.PackageID = p.PackageID";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HashMap<String, String> booking = new HashMap<>();
                booking.put("BookingID", String.valueOf(rs.getInt("BookingID")));
                booking.put("CustomerID", String.valueOf(rs.getInt("CustomerID")));
                booking.put("PickupLocation", rs.getString("PickupLocation"));
                booking.put("DropoffLocation", rs.getString("DropoffLocation"));
                booking.put("DriverID", rs.getString("DriverID") != null ? String.valueOf(rs.getInt("DriverID")) : "Not Assigned");
                booking.put("PackageName", rs.getString("PackageName"));
                booking.put("VehicleType", rs.getString("VehicleType"));
                booking.put("Price", String.valueOf(rs.getDouble("Price")));
                booking.put("Status", rs.getString("Status"));

                bookings.add(booking);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

 public static List<HashMap<String, String>> getCustomerBookings(int customerID) {
    List<HashMap<String, String>> bookings = new ArrayList<>();

    try (Connection conn = DBConnection.getConnection()) {
        String sql = "SELECT b.BookingID, b.PickupLocation, b.DropoffLocation, b.BookingDate, " +
                     "p.VehicleType, p.Price, b.Status " +
                     "FROM bookings b " +
                     "JOIN ridepackages p ON b.PackageID = p.PackageID " +
                     "WHERE b.CustomerID = ? " +
                     "ORDER BY b.BookingDate DESC";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            HashMap<String, String> booking = new HashMap<>();
            booking.put("BookingID", String.valueOf(rs.getInt("BookingID")));
            booking.put("PickupLocation", rs.getString("PickupLocation"));
            booking.put("DropoffLocation", rs.getString("DropoffLocation"));

            // Fetch the date correctly
            java.sql.Timestamp bookingDate = rs.getTimestamp("BookingDate");
            booking.put("Date", bookingDate != null ? bookingDate.toString() : "N/A");

            booking.put("VehicleType", rs.getString("VehicleType"));
            booking.put("Price", String.valueOf(rs.getDouble("Price")));
            booking.put("Status", rs.getString("Status"));

            bookings.add(booking);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return bookings;
}

  
  public static HashMap<String, String> getReceiptDetails(int BookingID) {
    HashMap<String, String> receiptDetails = new HashMap<>();

    try (Connection conn = DBConnection.getConnection()) {
        String sql = "SELECT b.BookingID, b.PickupLocation, b.DropoffLocation, b.BookingDate, " +
                     "p.VehicleType, p.Price, " +
                     "d.Name, d.Phone,d.VehicleModel, d.LicenseNumber,  " +
                     "FROM bookings b " +
                     "JOIN ridepackages p ON b.PackageID = p.PackageID  " +
                     "JOIN Drivers d ON b.DriverID = d.DriverID " +
                     "WHERE b.BookingID = ? AND b.Status IN ('Assigned', 'Completed')";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, BookingID);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            receiptDetails.put("BookingID", String.valueOf(rs.getInt("BookingID")));
            receiptDetails.put("PickupLocation", rs.getString("PickupLocation"));
            receiptDetails.put("DropoffLocation", rs.getString("DropoffLocation"));
            receiptDetails.put("Date", rs.getString("BookingDate"));
            receiptDetails.put("VehicleType", rs.getString("VehicleType"));
            receiptDetails.put("Price", String.valueOf(rs.getDouble("Price")));
            receiptDetails.put("VehicleModel", rs.getString("VehicleModel"));
            receiptDetails.put("VehicleNumber", rs.getString("VehicleNumber"));
            receiptDetails.put("DriverName", rs.getString("Name"));
            receiptDetails.put("Phone", rs.getString("Phone"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return receiptDetails;
}


}
