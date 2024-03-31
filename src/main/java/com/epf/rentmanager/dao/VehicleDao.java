package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	private VehicleDao() {}

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places, modele) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) FROM Vehicle;";
	private static final String FIND_VEHICLES_BY_CLIENT_QUERY = """
            SELECT Vehicle.id, constructeur, modele, nb_places
            FROM Vehicle
            INNER JOIN Reservation ON Reservation.vehicle_id = Vehicle.id
            WHERE Reservation.client_id=?;
            """;
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur=?, nb_places=?, modele=? WHERE id=?;";
	private static final String COUNT_RESA_QUERY = """
			SELECT COUNT(*) 
			FROM Reservation
			WHERE Reservation.vehicle_id=?;""";
	private static final String COUNT_Client_QUERY = """
			SELECT COUNT(*) 
			FROM Reservation
			INNER JOIN Client ON Client.id = Reservation.client_id
			WHERE Reservation.vehicle_id=?;""";

	public long create(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY,
					 Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, vehicle.constructeur());
			ps.setInt(2, vehicle.nbPlaces());
			ps.setString(3, vehicle.modele());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY)) {
			ps.setLong(1, vehicle.id());
			int affectedRows = ps.executeUpdate();
			if (affectedRows > 0) {
				return affectedRows;
			} else {
				throw new DaoException();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public Optional<Vehicle> findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY)) {
			ps.setLong(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return Optional.of(new Vehicle(
						resultSet.getLong("id"),
						resultSet.getString("constructeur"),
						resultSet.getString("modele"),
						resultSet.getShort("nb_places")
				));
			} else {
				throw new DaoException();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY)) {
			ResultSet resultSet = ps.executeQuery();
			List<Vehicle> vehicles = new ArrayList<Vehicle>();
			while (resultSet.next()) {
				vehicles.add(new Vehicle(
						resultSet.getLong("id"),
						resultSet.getString("constructeur"),
						resultSet.getString("modele"),
						resultSet.getShort("nb_places")
				));
			}
			return vehicles;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(COUNT_VEHICLES_QUERY)) {
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public List<Vehicle> findVehiclesRentedByClient(long clientId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_BY_CLIENT_QUERY)) {
			ps.setLong(1, clientId);
			ResultSet resultSet = ps.executeQuery();
			List<Vehicle> vehicles = new ArrayList<Vehicle>();
			while (resultSet.next()) {
				vehicles.add(new Vehicle(
						resultSet.getLong("id"),
						resultSet.getString("constructeur"),
						resultSet.getString("modele"),
						resultSet.getShort("nb_places")
				));
			}
			return vehicles;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public int update(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(UPDATE_VEHICLE_QUERY)) {
			ps.setString(1, vehicle.constructeur());
			ps.setInt(2, vehicle.nbPlaces());
			ps.setString(3, vehicle.modele());
			ps.setLong(4, vehicle.id());
			int affectedRows = ps.executeUpdate();
			if (affectedRows > 0) {
				return affectedRows;
			} else {
				throw new DaoException();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public int countResaByVehicleId(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(COUNT_RESA_QUERY)) {
			ps.setLong(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public int countClientByVehicleId(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(COUNT_Client_QUERY)) {
			ps.setLong(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

}
