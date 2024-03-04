package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
		if (vehicle.constructeur().isEmpty()) {
			throw new ServiceException("Le véhicule doit avoir un constructeur non vide");
		}
		if (vehicle.nbPlaces() < 1) {
			throw new ServiceException("Le véhicule doit avoir au moins une place");
		}
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la création du véhicule");
		}
	}

	public Vehicle findById(long id) throws ServiceException {
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
	
}
