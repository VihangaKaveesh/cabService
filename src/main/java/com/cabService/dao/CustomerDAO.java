/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cabService.dao;
import java.sql.*;
import com.cabService.dao.DBConnection;


public class CustomerDAO {
      public int validateCustomer(String email, String password) {
        int customerId = -1;
        String query = "SELECT CustomerID FROM Customers WHERE Email = ? AND Password = ?";
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
}
