package com.cabService.controller;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.cabService.dao.BookingDAO;

public class BookingHistoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int customerId = (int) session.getAttribute("userId");

        // Fetch booking history using DAO
        List<HashMap<String, String>> bookings = BookingDAO.getCustomerBookings(customerId);

        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("pages/bookingHistory.jsp").forward(request, response);
    }
}
