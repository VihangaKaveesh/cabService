package com.cabService.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.cabService.dao.DBConnection;
import jakarta.servlet.http.HttpSession;

public class CustomerDAO {

    // Validating the customer upon login
   public int validateCustomer(String email, String password, HttpSession session) {
    int customerId = -1;
    String query = "SELECT CustomerID FROM customers WHERE Email = ? AND Password = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, email);
        stmt.setString(2, password);
        
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            customerId = rs.getInt("CustomerID");
            // Optionally store additional session attributes if needed
            session.setAttribute("role", "customer");  // For example
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return customerId;
}


    // Registering the customer
    public boolean registerCustomer(String nic, String name, String email, String password, String phone) {
        boolean success = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            String sql = "INSERT INTO customers (NIC, Name, Email, Password, Phone) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nic);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password); 
            preparedStatement.setString(5, phone);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                success = true;
                System.out.println("Customer registered successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error in registerCustomer: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

   // Get all customers without including password
public List<String[]> getAllCustomers() {
    List<String[]> customers = new ArrayList<>();
    String query = "SELECT CustomerID, NIC, Name, Email, Phone FROM customers";  // Excluding Password field
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String[] customer = new String[5];  // Changed to 5 instead of 6 to match the selected columns
            customer[0] = String.valueOf(rs.getInt("CustomerID"));
            customer[1] = rs.getString("NIC");
            customer[2] = rs.getString("Name");
            customer[3] = rs.getString("Email");
            customer[4] = rs.getString("Phone");
            customers.add(customer);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return customers;
}

    // Get customer by ID
    public String[] getCustomerById(int customerId) {
//        String[] customer = null;
        String query = "SELECT * FROM customers WHERE CustomerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String[] customer = new String[6];
                customer[0] = String.valueOf(rs.getInt("CustomerID"));
                customer[1] = rs.getString("NIC");
                customer[2] = rs.getString("Name");
                customer[3] = rs.getString("Email");
                customer[4] = rs.getString("Password");
                customer[5] = rs.getString("Phone");
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update customer details
   public boolean updateCustomer(int customerID, String nic, String name, String email, String password, String phone) {
    String sql = "UPDATE Customers SET NIC=?, Name=?, Email=?, Password=?, Phone=? WHERE CustomerID=?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, nic);
        pstmt.setString(2, name);
        pstmt.setString(3, email);
        pstmt.setString(4, password);
        pstmt.setString(5, phone);
        pstmt.setInt(6, customerID);

        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}


    // Delete customer
    public boolean deleteCustomer(int customerId) {
        boolean success = false;
        String query = "DELETE FROM customers WHERE CustomerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                success = true;
                System.out.println("Customer deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error in deleteCustomer: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }
}
