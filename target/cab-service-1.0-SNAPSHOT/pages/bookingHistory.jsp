<%-- 
    Document   : bookingHistory
    Created on : Feb 23, 2025, 2:06:04 PM
    Author     : vihan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        <style>
        body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
}

.navbar {
    background-color: #333;
    overflow: hidden;
    display: flex;
    justify-content: center;
    padding: 10px 0;
    flex-wrap: wrap;
}

.navbar a {
    color: white;
    padding: 14px 20px;
    text-decoration: none;
    text-align: center;
    transition: background 0.3s ease-in-out;
}

.navbar a:hover {
    background-color: #575757;
     border-radius: 5px
}

@media screen and (max-width: 768px) {
    .navbar {
        flex-direction: column;
        align-items: center;
    }

    .navbar a {
        width: 100%;
        padding: 12px;
    }
}

@media screen and (max-width: 480px) {
    .navbar {
        padding: 5px 0;
    }

    .navbar a {
        padding: 10px;
        font-size: 14px;
    }
}

    </style>
    
    </head>
    <body>
        
          <div class="navbar">
               <a href="${pageContext.request.contextPath}/pages/managementDashboard.jsp">Home</a>
               <a href="${pageContext.request.contextPath}/pages/customerDashboard.jsp">Home</a>
        <a href="${pageContext.request.contextPath}/pages/rideRequest.jsp">Need a Ride</a>
        <a href="${pageContext.request.contextPath}/pages/bookingHistory.jsp">History</a>
        <a href="${pageContext.request.contextPath}/pages/login.jsp">Logout</a>
       
    </div>
        
        <h1>Hello World!</h1>
    </body>
</html>