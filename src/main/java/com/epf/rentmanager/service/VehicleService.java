package com.epf.rentmanager.service;

import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;

	@Autowired
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	private void verifNbPlaces(Vehicle vehicle) throws ServiceException {
		if (vehicle.nbPlaces() < 2 || vehicle.nbPlaces() > 9) {
			throw new ServiceException("Le nombre de places doit être supérieur à 0");
		}
	}

	private void verifConstructeur(Vehicle vehicle) throws ServiceException {
		if (vehicle.constructeur().isEmpty()) {
			throw new ServiceException("Le constructeur ne doit pas être vide");
		}
	}

	private void verifModele(Vehicle vehicle) throws ServiceException {
		if (vehicle.modele().isEmpty()) {
			throw new ServiceException("Le modèle ne doit pas être vide");
		}
	}
	
	public long create(Vehicle vehicle) throws ServiceException {
		verifNbPlaces(vehicle);
		verifConstructeur(vehicle);
		verifModele(vehicle);
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la création du véhicule");
		}
	}

	public Optional<Vehicle> findById(long id) throws ServiceException {
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération du véhicule");
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération des véhicules");
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la suppression du véhicule");
		}
	}

	public int count() throws ServiceException {
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération du nombre de véhicules");
		}
	}

	public List<Vehicle> findVehiclesRentedByClient(long clientId) throws ServiceException {
		try {
			return vehicleDao.findVehiclesRentedByClient(clientId);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération des véhicules");
		}
	}

	public int update(Vehicle vehicle) throws ServiceException {
		verifNbPlaces(vehicle);
		verifConstructeur(vehicle);
		verifModele(vehicle);
		if (vehicle.constructeur().isEmpty()) {
			throw new ServiceException("Le véhicule doit avoir un constructeur non vide");
		}
		if (vehicle.nbPlaces() < 1) {
			throw new ServiceException("Le véhicule doit avoir au moins une place");
		}
		try {
			return vehicleDao.update(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la mise à jour du véhicule");
		}
	}

	public int countResaByVehicleId(long id) throws ServiceException {
		try {
			return vehicleDao.countResaByVehicleId(id);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération des réservations");
		}
	}

	public int countClientByVehicleId(long id) throws ServiceException {
		try {
			return vehicleDao.countClientByVehicleId(id);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération des clients");
		}
	}
	
}
