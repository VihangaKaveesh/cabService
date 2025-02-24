/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cabService.dao;
import java.sql.*;
import com.cabService.dao.DBConnection;


public class CustomerDAO {
    
    //validating the customer upon login
      public int validateCustomer(String email, String password) {
        int customerId = -1;
        String query = "SELECT CustomerID FROM customers WHERE Email = ? AND Password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customerId = rs.getInt("CustomerID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerId;
    }
      
      //registering the customer
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
}
