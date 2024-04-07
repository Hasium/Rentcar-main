package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehiclesServiceTest {
    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleDao vehicleDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.vehicleDao.findAll()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> vehicleService.findAll());
    }

    @Test
    void findById_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.vehicleDao.findById(1)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> vehicleService.findById(1));
    }

    @Test
    void create_should_fail_when_vehicle_has_empty_constructeur() throws DaoException {
        // Given
        Vehicle vehicle = new Vehicle((long) 1, "", "Doe", 5);

        // Then
        assertThrows(ServiceException.class, () -> vehicleService.create(vehicle));
    }

    @Test
    void create_should_fail_when_vehicle_has_empty_modele() throws DaoException {
        // Given
        Vehicle vehicle = new Vehicle((long) 1, "John", "", 5);

        // Then
        assertThrows(ServiceException.class, () -> vehicleService.create(vehicle));
    }

    @Test
    void create_should_fail_when_vehicle_has_bad_nb_places() throws DaoException {
        // Given
        Vehicle vehicle = new Vehicle((long) 1, "JOHN", "Doe", 1);

        // Then
        assertThrows(ServiceException.class, () -> vehicleService.create(vehicle));
    }

    @Test
    void create_should_pass_when_vehicle_has_valid_data() throws DaoException, ServiceException {
        // Given
        Vehicle vehicle = new Vehicle("John", "Doe", 5);
        when(this.vehicleDao.create(vehicle)).thenReturn((long) 0);

        // Then
        assertEquals(0, vehicleService.create(vehicle));
    }

    @Test
    void delete_should_fail_when_dao_throws_exception() throws DaoException {
        // Given
        Vehicle vehicle = new Vehicle((long) 1, "John", "Doe", 5);
        when(this.vehicleDao.delete(vehicle)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> vehicleService.delete(vehicle));
    }

    @Test
    void count_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.vehicleDao.count()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> vehicleService.count());
    }

    @Test
    void update_should_fail_when_dao_throws_exception() throws DaoException {
        // Given
        Vehicle vehicle = new Vehicle((long) 1, "John", "Doe", 5);
        when(this.vehicleDao.update(vehicle)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> vehicleService.update(vehicle));
    }

}
