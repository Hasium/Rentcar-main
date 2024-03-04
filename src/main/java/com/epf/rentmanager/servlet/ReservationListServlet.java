package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rents")
public class ReservationListServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    private ReservationService reservationService = ReservationService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try {
            request.setAttribute("rents", reservationService.findAll());
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);

    }

}
