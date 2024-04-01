package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dto.ReservationWithVehicleClientDto;
import com.epf.rentmanager.dto.ReservationWithVehicleDto;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private ReservationDao reservationDao;

    @Autowired
    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    private void verifDoubleResa(Reservation reservation) throws ServiceException {
        try {
            List<Reservation> reservations = reservationDao.findResaByVehicleId(reservation.vehicle_id());
            for (Reservation resa : reservations) {
                if ((reservation.debut().isBefore(resa.fin()) && reservation.debut().isAfter(resa.debut()))
                        || (reservation.fin().isBefore(resa.fin()) && reservation.fin().isAfter(resa.debut()))
                        || (reservation.debut().isBefore(resa.debut()) && reservation.fin().isAfter(resa.fin()))
                        || (reservation.debut().isEqual(resa.debut()))
                        || (reservation.fin().isEqual(resa.fin()))
                        || (reservation.debut().isEqual(resa.fin()))
                        || (reservation.fin().isEqual(resa.debut()))) {
                    throw new ServiceException("Le véhicule est déjà réservé sur cette période");
                }
            }
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération des réservations");
        }
    }

    private void verifPause(Reservation reservation) throws ServiceException {
        try {
            List<Reservation> reservations = reservationDao.findResaByVehicleId(reservation.vehicle_id());
            reservations.add(reservation);
            reservations.sort(Comparator.comparing(Reservation::debut));
            int nbJoursSansPause = reservations.get(0).debut().until(reservations.get(0).fin().plusDays(1)).getDays();
            for (int i = 1; i < reservations.size(); i++) {
                if (reservations.get(i).debut().isEqual(reservations.get(i - 1).fin().plusDays(1))) {
                    nbJoursSansPause += reservations.get(i).debut().until(reservations.get(i).fin().plusDays(1)).getDays();
                    if (nbJoursSansPause > 30) {
                        throw new ServiceException("La voiture ne peut pas être réservée plus de 30 jours de suite sans pause");
                    }
                } else {
                    nbJoursSansPause = 0;
                }
            }
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération des réservations");
        }
    }

    private void verifDuree(Reservation reservation) throws ServiceException {
        if (reservation.fin().isAfter(reservation.debut().plusDays(7))) {
            throw new ServiceException("La réservation ne peut pas dépasser 7 jours");
        }
    }

    public long create(Reservation reservation) throws ServiceException {
        verifDuree(reservation);
        verifDoubleResa(reservation);
        verifPause(reservation);
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

    public List<ReservationWithVehicleClientDto> findAll() throws ServiceException {
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

    public Long update(Reservation reservation) throws ServiceException {
        verifDuree(reservation);
        verifDoubleResa(reservation);
        verifPause(reservation);
        try {
            return reservationDao.update(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la mise à jour de la réservation");
        }
    }

    public Optional<Reservation> findById(long id) throws ServiceException {
        try {
            return reservationDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération de la réservation");
        }
    }

    public Optional<ReservationWithVehicleClientDto> findDetailsById(long id) throws ServiceException {
        try {
            return reservationDao.findDetailsById(id);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération de la réservation");
        }
    }

}
