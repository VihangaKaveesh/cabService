<%@ page import="java.util.HashMap, com.cabService.dao.BookingDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    //HttpSession session = request.getSession(false); // Get the current session, do not create a new one

    // Check if session exists and if user is logged in
    if (session == null || session.getAttribute("userId") == null || !"customer".equals(session.getAttribute("role"))) {
        response.sendRedirect("login.jsp?message=You must log in first");
        return; // Stop the execution of the page
    }
    
    //int customerId = (int) session.getAttribute("userId"); // Get the logged-in user's customer ID
        
%>

<!DOCTYPE html>
<html>
<head>
    <title>Booking Receipt</title>
    <style>
        table {
            width: 60%;
            margin: auto;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>

    <h2 style="text-align: center;">Booking Receipt</h2>

    <%
        int bookingID = Integer.parseInt(request.getParameter("bookingID"));
        HashMap<String, String> receipt = BookingDAO.getReceiptDetails(bookingID);
        if (receipt.isEmpty()) {
            out.println("<p style='text-align: center; color: red;'>Invalid Booking ID or Booking is not Assigned.</p>");
        } else {
    %>
        <table>
            <tr><th>Pickup Location</th><td><%= receipt.get("PickupLocation") %></td></tr>
            <tr><th>Dropoff Location</th><td><%= receipt.get("DropoffLocation") %></td></tr>
            <tr><th>Date</th><td><%= receipt.get("Date") %></td></tr>
            <tr><th>Vehicle Type</th><td><%= receipt.get("VehicleType") %></td></tr>
            <tr><th>Price</th><td>$<%= receipt.get("Price") %></td></tr>
            <tr><th>Vehicle Model</th><td><%= receipt.get("VehicleModel") %></td></tr>
            <tr><th>Vehicle Number</th><td><%= receipt.get("VehicleNumber") %></td></tr>
            <tr><th>Driver Name</th><td><%= receipt.get("DriverName") %></td></tr>
            <tr><th>Driver Phone</th><td><%= receipt.get("Phone") %></td></tr>
        </table>
    <% } %>

</body>
</html>
