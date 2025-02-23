<%-- 
    Document   : login
    Created on : Feb 23, 2025, 1:50:48 PM
    Author     : vihan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>Login</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        .login-container { width: 300px; margin: 100px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
        input, select { width: 90%; padding: 8px; margin: 10px 0; }
        button { padding: 10px 15px; cursor: pointer; }
    </style>
    </head>
    <body>
        <div class="login-container">
        <h2>Login</h2>
        <form action="LoginServlet" method="post">
            <label for="email">Email:</label>
            <input type="email" name="email" required>

            <label for="password">Password:</label>
            <input type="password" name="password" required>

            <label for="role">Login As:</label>
            <select name="role">
                <option value="customer">Customer</option>
                <option value="management">Management</option>
            </select>

            <button type="submit">Login</button>
        </form>
        <% if(request.getParameter("error") != null) { %>
            <p style="color:red;"><%= request.getParameter("error") %></p>
        <% } %>
    </div>
    </body>
</html>
