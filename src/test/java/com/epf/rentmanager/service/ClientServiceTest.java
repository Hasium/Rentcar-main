package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao ClientDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.ClientDao.findAll()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.findAll());
    }

    @Test
    void findById_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.ClientDao.findById(1)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.findById(1));
    }

    @Test
    void create_should_fail_when_client_has_empty_name_or_surname() throws DaoException {
        // Given
        Client client = new Client(1, "", "Doe", "doe@example.com", LocalDate.now());

        // Then
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }

    @Test
    void create_should_pass_when_client_has_valid_data() throws DaoException, ServiceException {
        // Given
        Client client = new Client("John", "Doe", "doe@example.com", LocalDate.now());
        when(this.ClientDao.create(client)).thenReturn((long) 0);

        // Then
        assert((long) 0 == clientService.create(client));
    }

    @Test
    void delete_should_fail_when_dao_throws_exception() throws DaoException {
        // Given
        Client client = new Client(1, "John", "Doe", "doe@example.com", LocalDate.now());
        when(this.ClientDao.delete(client)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.delete(client));
    }

    @Test
    void count_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.ClientDao.count()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.count());
    }

    @Test
    void countVehiclesRentedByClient_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.ClientDao.countVehiclesRentedByClientId(1)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.countVehiclesRentedByClient(1));
    }

    @Test
    void countResaByClient_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.ClientDao.countResaByClientId(1)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.countResaByClient(1));
    }

    @Test
    void update_should_fail_when_client_has_empty_name_or_surname() throws DaoException {
        // Given
        Client client = new Client(1, "", "Doe", "doe@example.com", LocalDate.now());

        // Then
        assertThrows(ServiceException.class, () -> clientService.update(client));
    }

    @Test
    void findClientsByVehicleId_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.ClientDao.findClientsByVehicleId(1)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.findClientsByVehicleId(1));
    }

}
