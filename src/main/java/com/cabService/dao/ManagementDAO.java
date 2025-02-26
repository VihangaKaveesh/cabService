/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cabService.dao;
import java.sql.*;
import com.cabService.dao.DBConnection;
import jakarta.servlet.http.HttpSession;

public class ManagementDAO {
       public int validateManagement(String email, String password, HttpSession session) {
    int managementId = -1;
    String query = "SELECT ManagementID FROM management WHERE Email = ? AND Password = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, email);
        stmt.setString(2, password);
        
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            managementId = rs.getInt("ManagementID");
            // Optionally store additional session attributes if needed
            session.setAttribute("role", "management");  // For example
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return managementId;
}

}
