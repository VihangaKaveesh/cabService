<%-- 
    Document   : manageDrivers
    Created on : Feb 23, 2025, 2:05:46 PM
    Author     : vihan
--%>
<%@page import="java.util.*"%>
<%@ page import="java.sql.Connection, 
         java.sql.SQLException, 
         java.util.List, 
         com.cabService.dao.DBConnection, 
         com.cabService.dao.DriverDAO" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Drivers</title>
        
        <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; }
        .navbar {
            background-color: #333;
            overflow: hidden;
            display: flex;
            justify-content: center;
            padding: 10px 0;
        }
        .navbar a {
            color: white;
            padding: 14px 20px;
            text-decoration: none;
            text-align: center;
        }
        .navbar a:hover {
            background-color: #575757;
             border-radius: 5px
        }
   table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
    
    
    
    </head>
    <body>
     <!-- Navigation bar -->
        <div class="navbar">
            <a href="${pageContext.request.contextPath}/pages/managementDashboard.jsp">Home</a>
            <a href="${pageContext.request.contextPath}/pages/manageCustomers.jsp">Customers</a>
            <a href="${pageContext.request.contextPath}/pages/manageDrivers.jsp">Drivers</a>
            <a href="${pageContext.request.contextPath}/pages/manageBookings.jsp">Bookings</a>
            <a href="${pageContext.request.contextPath}/pages/login.jsp">Logout</a>
        </div>
        
        
         <h2>Driver Management</h2>

    <h3>Add a New Driver</h3>
    <form action="${pageContext.request.contextPath}/DriverServlet" method="post">
        <input type="hidden" name="action" value="add">
        NIC: <input type="text" name="nic" required><br>
        Name: <input type="text" name="name" required><br>
        Email: <input type="email" name="email" required><br>
        Phone: <input type="text" name="phone" required><br>
        License Number: <input type="text" name="licenseNumber" required><br>
        Vehicle Type: 
        <select name="vehicleType" required>
            <option value="Car">Car</option>
            <option value="Bike">Bike</option>
            <option value="Van">Van</option>
            <option value="Tuk">Tuk</option>
        </select><br>
        Vehicle Model: <input type="text" name="vehicleModel" required><br>
        <button type="submit">Add Driver</button>
    </form>

    <h3>Current Drivers</h3>
    <table>
        <tr>
            <th>Driver ID</th>
            <th>NIC</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>License Number</th>
            <th>Vehicle Type</th>
            <th>Vehicle Model</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>

        <%
            Connection conn = null;
            try {
                conn = DBConnection.getConnection();
                DriverDAO driverDAO = new DriverDAO(conn);
                List<String[]> drivers = driverDAO.getDrivers();

                for (String[] driver : drivers) {
        %>
            <tr>
                <td><%= driver[0] %></td>
                <td><%= driver[1] %></td>
                <td><%= driver[2] %></td>
                <td><%= driver[3] %></td>
                <td><%= driver[4] %></td>
                <td><%= driver[5] %></td>
                <td><%= driver[6] %></td>
                <td><%= driver[7] %></td>
                <td><%= driver[9] %></td>
                <td>
                    <a href="editDriver.jsp?driverID=<%= driver[0] %>">Edit</a> |
                    <form action="${pageContext.request.contextPath}/DriverServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="driverID" value="<%= driver[0] %>">
                        <button type="submit" onclick="return confirm('Are you sure you want to delete this driver?');">Delete</button>
                    </form> 
                </td>
            </tr>
        <%
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
            }
        %>
    </table>

      
</html>
