package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
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

@WebServlet("/users/details")
public class ClientDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    ReservationService reservationService;
    @Autowired
    ClientService clientService;
    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            Optional<Client> client = clientService.findById(id);
            request.setAttribute("nbVehicle", clientService.countVehiclesRentedByClient(id));
            request.setAttribute("nbReservation", clientService.countResaByClient(id));
            request.setAttribute("reservations", reservationService.findResaByClientId(id));
            request.setAttribute("vehicles", vehicleService.findVehiclesRentedByClient(id));
            if (client.isPresent()) {
                request.setAttribute("user", client.get());
            } else {
                IOUtils.print("Client not found");
            }
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
    }
}
