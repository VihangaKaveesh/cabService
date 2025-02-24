<%-- 
    Document   : customerDashboard
    Created on : Feb 23, 2025, 1:51:21 PM
    Author     : vihan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        
        <script>
            window.onload = function() {
                const urlParams = new URLSearchParams(window.location.search);
                if (urlParams.has('message')) {
                    alert(urlParams.get('message'));
                }
            };
              </script>
              
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
    </style>
    </head>
    <body>
          <div class="navbar">
               <a href="${pageContext.request.contextPath}/pages/customerDashboard.jsp">Home</a>
        <a href="${pageContext.request.contextPath}/pages/rideRequest.jsp">Need a Ride</a>
        <a href="${pageContext.request.contextPath}/pages/bookingHistory.jsp">History</a>
        <a href="${pageContext.request.contextPath}/pages/login.jsp">Logout</a>
       
    </div>

    <div style="padding: 20px; text-align: center;">
        <h2>Welcome to Our Website</h2>
        <p>Use the navigation bar to explore different pages.</p>
    </div>
    </body>
</html>
