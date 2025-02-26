<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, 
         java.sql.SQLException, 
         com.cabService.dao.DBConnection, 
         com.cabService.dao.CustomerDAO" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Customer</title>
        
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
            border-radius: 5px;
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
             <a href="${pageContext.request.contextPath}/pages/driverAssign.jsp">Assign a driver</a>
             <a href="${pageContext.request.contextPath}/pages/manageBookings.jsp">Bookings</a>
            <a href="${pageContext.request.contextPath}/pages/login.jsp">Logout</a>
        </div>
        
        <h2>Edit Customer</h2>

    <%
        String customerID = request.getParameter("customerID");
        Connection conn = null;
        String[] customer = null;

        if (customerID != null) {
            try {
                conn = DBConnection.getConnection();
                CustomerDAO customerDAO = new CustomerDAO();
                customer = customerDAO.getCustomerById(Integer.parseInt(customerID));
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
            }
        }
        
        if (customer != null) {
    %>

    <form action="${pageContext.request.contextPath}/CustomerServlet" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="customerID" value="<%= customer[0] %>">
        NIC: <input type="text" name="nic" value="<%= customer[1] %>" required><br>
        Name: <input type="text" name="name" value="<%= customer[2] %>" required><br>
        Email: <input type="email" name="email" value="<%= customer[3] %>" required><br>
        Password: <input type="password" name="password" value="<%= customer[4] %>" required><br>
        Phone: <input type="text" name="phone" value="<%= customer[5] %>" required><br>
        <button type="submit">Update Customers</button>
    </form>

    <%
        } else {
            out.println("<p>Customer not found.</p>");
        }
    %>

    </body>
</html>
