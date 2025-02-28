//package com.cabService.controller;
//
//import com.cabService.dao.BookingDAO;
//import com.cabService.model.Booking;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//
//
//public class ReceiptServlet extends HttpServlet {
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession(false);
//        Integer customerId = (session != null) ? (Integer) session.getAttribute("userId") : null;
//
//        if (customerId == null) {
//            response.sendRedirect("login.jsp?message=Please login first");
//            return;
//        }
//
//        int bookingId = Integer.parseInt(request.getParameter("bookingID"));
//        Booking booking = BookingDAO.getBookingDetails(bookingId, customerId);
//
//        if (booking != null) {
//            request.setAttribute("booking", booking);
//            request.getRequestDispatcher("receipt.jsp").forward(request, response);
//        } else {
//            response.sendRedirect("bookingHistory.jsp?message=No receipt found.");
//        }
//    }
//}
