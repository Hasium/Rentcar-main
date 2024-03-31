package com.epf.rentmanager.servlet;


import com.epf.rentmanager.dto.ReservationWithVehicleClientDto;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/rents/details")
public class ReservationDetailsServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;

    @Autowired
    ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            Optional<ReservationWithVehicleClientDto> reservation = reservationService.findDetailsById(id);
            if (reservation.isPresent()) {
                request.setAttribute("rent", reservation.get());
            } else {
                IOUtils.print("Reservation not found");
            }
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/details.jsp").forward(request, response);
    }

}
