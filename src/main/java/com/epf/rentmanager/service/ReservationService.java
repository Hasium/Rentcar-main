package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dto.ReservationWithVehicleDto;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;

import java.util.List;

public class ReservationService {
    private ReservationDao reservationDao;
    public static ReservationService instance;

    private ReservationService() {
        this.reservationDao = ReservationDao.getInstance();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public long create(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la création de la réservation");
        }
    }

    public long delete(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.delete(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la suppression de la réservation");
        }
    }

    public List<ReservationWithVehicleDto> findResaByClientId(long id) throws ServiceException {
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération de la réservation");
        }
    }

    public List<Reservation> findResaByVehicleId(long id) throws ServiceException {
        try {
            return reservationDao.findResaByVehicleId(id);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération de la réservation");
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération des réservations");
        }
    }

    public long count() throws ServiceException {
        try {
            return reservationDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération du nombre de réservations");
        }
    }

}
