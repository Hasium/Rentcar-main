package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;

@Service
public class ClientService {

    private ClientDao clientDao;

    @Autowired
    private ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    private void verifNameFirstName(Client client) throws ServiceException {
        if (client.nom().isEmpty() || client.prenom().isEmpty()
                || client.nom().length() < 3 || client.prenom().length() < 3) {
            throw new ServiceException("Le client doit avoir un nom et un prénom non vide");
        }
    }

    private void verifEmail(Client client) throws ServiceException {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!client.email().matches(regex)) {
            throw new ServiceException("Email invalide");
        }
    }

    private void verifAge(Client client) throws ServiceException {
        if (client.naissance().isAfter(LocalDate.now().minusYears(18))) {
            throw new ServiceException("Le client doit avoir au moins 18 ans");
        }
    }

    public long create(Client client) throws ServiceException {
        verifNameFirstName(client);
        verifEmail(client);
        verifAge(client);
        try {
            client = new Client(
                    client.id(),
                    client.nom().toUpperCase(),
                    client.prenom(),
                    client.email(),
                    client.naissance()
            );
            return clientDao.create(client);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la création du client");
        }
    }

    public Optional<Client> findById(long id) throws ServiceException {
        try {
            return clientDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de récupération d'un client");
        }
    }

    public List<Client> findAll() throws ServiceException {
        try {
            return clientDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération des clients");
        }
    }

    public long delete(Client client) throws ServiceException {
        try {
            return clientDao.delete(client);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la suppression du client");
        }
    }

    public int count() throws ServiceException {
        try {
            return clientDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération du nombre de clients");
        }
    }

    public long countVehiclesRentedByClient(long id) throws ServiceException {
        try {
            return clientDao.countVehiclesRentedByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération du nombre de véhicules loués par le client");
        }
    }

    public long countResaByClient(long id) throws ServiceException {
        try {
            return clientDao.countResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération du nombre de réservations du client");
        }
    }

    public int update(Client client) throws ServiceException {
        verifNameFirstName(client);
        verifEmail(client);
        verifAge(client);
        try {
            client = new Client(
                    client.id(),
                    client.nom().toUpperCase(),
                    client.prenom(),
                    client.email()
            );
            return clientDao.update(client);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la mise à jour du client");
        }
    }

    public List<Client> findClientsByVehicleId(long id) throws ServiceException {
        try {
            return clientDao.findClientsByVehicleId(id);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération des clients");
        }
    }

}
