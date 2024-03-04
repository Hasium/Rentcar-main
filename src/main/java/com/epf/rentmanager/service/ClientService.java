package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

public class ClientService {

    private ClientDao clientDao;
    public static ClientService instance;

    private ClientService() {
        this.clientDao = ClientDao.getInstance();
    }

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }

        return instance;
    }


    public long create(Client client) throws ServiceException {
        if (client.nom().isEmpty() || client.prenom().isEmpty()) {
            throw new ServiceException("Le client doit avoir un nom et un prénom non vide");
        }
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
        } catch (DaoException e){
            throw new ServiceException("Une erreur a eu lieu lors de récupération d'un client");
        }
    }

    public List<Client> findAll() throws ServiceException {
        try {
            return clientDao.findAll();
        } catch (DaoException e){
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
}
