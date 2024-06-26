package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.dto.ReservationWithVehicleClientDto;
import com.epf.rentmanager.dto.ReservationWithVehicleDto;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.exception.DaoException;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

    private ReservationDao() {}

    private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
    private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
    private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = """
            SELECT Reservation.id AS resa_id, Vehicle.id AS vehicle_id, Vehicle.constructeur, Vehicle.modele, Vehicle.nb_places, debut, fin 
            FROM Reservation
            INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id
            WHERE client_id=?;
            """;
    private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
    private static final String FIND_RESERVATIONS_QUERY = """
            SELECT Reservation.id, vehicle_id, debut, fin, Client.nom, Client.prenom, Vehicle.constructeur, Vehicle.modele
            FROM Reservation
            INNER JOIN Client ON Client.id = Reservation.client_id
            INNER JOIN Vehicle ON Vehicle.id = Reservation.vehicle_id;""";
    private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(*) FROM Reservation;";
    private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";
    private static final String FIND_RESERVATIONS_BY_ID = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
    private static final String FIND_DETAILS_RESERVATIONS_BY_ID = """
            SELECT Reservation.id, Reservation.vehicle_id, Reservation.client_id, Reservation.debut, Reservation.fin, Client.nom, Client.prenom, Client.email, Vehicle.constructeur, Vehicle.modele, Vehicle.nb_places
            FROM Reservation
            INNER JOIN Client ON Client.id = Reservation.client_id
            INNER JOIN Vehicle ON Vehicle.id = Reservation.vehicle_id
            WHERE Reservation.id=?;""";

    public long create(Reservation reservation) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, reservation.client_id());
            ps.setLong(2, reservation.vehicle_id());
            ps.setDate(3, Date.valueOf(reservation.debut()));
            ps.setDate(4, Date.valueOf(reservation.fin()));
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

    public long delete(Reservation reservation) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY)) {
            ps.setLong(1, reservation.id());
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


    public List<ReservationWithVehicleDto> findResaByClientId(long clientId) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)) {
            ps.setLong(1, clientId);
            ResultSet resultSet = ps.executeQuery();
            List<ReservationWithVehicleDto> reservationsDto = new ArrayList<ReservationWithVehicleDto>();
            while (resultSet.next()) {
                reservationsDto.add(new ReservationWithVehicleDto(
                        resultSet.getLong("resa_id"),
                        new Vehicle(
                                resultSet.getLong("vehicle_id"),
                                resultSet.getString("constructeur"),
                                resultSet.getString("modele"),
                                resultSet.getInt("nb_places")
                        ),
                        resultSet.getDate("debut").toLocalDate(),
                        resultSet.getDate("fin").toLocalDate()
                ));
            }
            return reservationsDto;
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY)) {
            ps.setLong(1, vehicleId);
            ResultSet resultSet = ps.executeQuery();
            List<Reservation> reservations = new ArrayList<Reservation>();
            while (resultSet.next()) {
                reservations.add(new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getLong("client_id"),
                        resultSet.getLong("vehicle_id"),
                        resultSet.getDate("debut").toLocalDate(),
                        resultSet.getDate("fin").toLocalDate()
                ));
            }
            return reservations;
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    public List<ReservationWithVehicleClientDto> findAll() throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_QUERY)) {
            ResultSet resultSet = ps.executeQuery();
            List<ReservationWithVehicleClientDto> reservations = new ArrayList<ReservationWithVehicleClientDto>();
            while (resultSet.next()) {
                reservations.add(new ReservationWithVehicleClientDto(
                        resultSet.getLong("id"),
                        new Vehicle(
                                resultSet.getString("constructeur"),
                                resultSet.getString("modele")
                        ),
                        new Client(
                                resultSet.getString("nom"),
                                resultSet.getString("prenom")
                        ),
                        resultSet.getDate("debut").toLocalDate(),
                        resultSet.getDate("fin").toLocalDate()
                ));
            }
            return reservations;
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    public int count() throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(COUNT_RESERVATIONS_QUERY)) {
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

    public long update(Reservation reservation) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_RESERVATION_QUERY)) {
            ps.setLong(1, reservation.client_id());
            ps.setLong(2, reservation.vehicle_id());
            ps.setDate(3, Date.valueOf(reservation.debut()));
            ps.setDate(4, Date.valueOf(reservation.fin()));
            ps.setLong(5, reservation.id());
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

    public Optional<Reservation> findById(long id) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_ID)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getLong("client_id"),
                        resultSet.getLong("vehicle_id"),
                        resultSet.getDate("debut").toLocalDate(),
                        resultSet.getDate("fin").toLocalDate()
                ));
            } else {
                throw new DaoException();
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    public Optional<ReservationWithVehicleClientDto> findDetailsById(long id) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_DETAILS_RESERVATIONS_BY_ID)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new ReservationWithVehicleClientDto(
                        resultSet.getLong("id"),
                        new Vehicle(
                                resultSet.getLong("vehicle_id"),
                                resultSet.getString("constructeur"),
                                resultSet.getString("constructeur"),
                                resultSet.getInt("nb_places")
                        ),
                        new Client(
                                resultSet.getLong("client_id"),
                                resultSet.getString("nom"),
                                resultSet.getString("prenom"),
                                resultSet.getString("email")
                        ),
                        resultSet.getDate("debut").toLocalDate(),
                        resultSet.getDate("fin").toLocalDate()
                ));
            } else {
                throw new DaoException();
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

}
