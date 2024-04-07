package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.clientDao.findAll()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.findAll());
    }

    @Test
    void findById_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.clientDao.findById(1)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.findById(1));
    }

    @Test
    void create_should_fail_when_client_has_empty_name_or_surname() throws DaoException {
        // Given
        Client client = new Client(1, "", "aaa", "aaa@example.com", LocalDate.now().minusYears(20));

        // Then
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }

    @Test
    void create_should_pass_when_client_has_valid_data() throws DaoException, ServiceException {
        // Given
        Client client = new Client("JOHN", "aaa", "aaa@example.com", LocalDate.now().minusYears(20));
        when(this.clientDao.create(client)).thenReturn((long) 0);

        // Then
        assertEquals(0, clientService.create(client));
    }

    @Test
    void delete_should_fail_when_dao_throws_exception() throws DaoException {
        // Given
        Client client = new Client(1, "John", "aaa", "aaa@example.com", LocalDate.now().minusYears(20));
        when(this.clientDao.delete(client)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.delete(client));
    }

    @Test
    void count_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.clientDao.count()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.count());
    }

    @Test
    void countVehiclesRentedByClient_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.clientDao.countVehiclesRentedByClientId(1)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.countVehiclesRentedByClient(1));
    }

    @Test
    void countResaByClient_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.clientDao.countResaByClientId(1)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.countResaByClient(1));
    }

    @Test
    void update_should_fail_when_client_has_empty_name_or_surname() throws DaoException {
        // Given
        Client client = new Client(1, "", "aaa", "aaa@example.com", LocalDate.now().minusYears(20));

        // Then
        assertThrows(ServiceException.class, () -> clientService.update(client));
    }

    @Test
    void update_should_fail_when_client_has_a_too_short_name() throws DaoException {
        // Given
        Client client = new Client(1, "da", "aaa", "aaa@example.com", LocalDate.now().minusYears(20));

        // Then
        assertThrows(ServiceException.class, () -> clientService.update(client));
    }

    @Test
    void update_should_fail_when_client_has_a_too_short_surname() throws DaoException {
        // Given
        Client client = new Client(1, "aaaa", "aa", "aaa@example.com", LocalDate.now().minusYears(20));

        // Then
        assertThrows(ServiceException.class, () -> clientService.update(client));
    }

    @Test
    void update_should_fail_when_client_has_a_invalid_email_no_at() throws DaoException {
        // Given
        Client client = new Client(1, "aaaa", "aa", "aaaexample.com", LocalDate.now().minusYears(20));

        // Then
        assertThrows(ServiceException.class, () -> clientService.update(client));
    }

    @Test
    void update_should_fail_when_client_has_a_invalid_email_bad_end() throws DaoException {
        // Given
        Client client = new Client(1, "aaaa", "aa", "aaa@example.commm", LocalDate.now().minusYears(20));

        // Then
        assertThrows(ServiceException.class, () -> clientService.update(client));
    }

    @Test
    void update_should_fail_when_client_is_minus_18() throws DaoException {
        // Given
        Client client = new Client(1, "aaaa", "aa", "aaa@example.com", LocalDate.now().minusYears(15));

        // Then
        assertThrows(ServiceException.class, () -> clientService.update(client));
    }

    @Test
    void findClientsByVehicleId_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.clientDao.findClientsByVehicleId(1)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.findClientsByVehicleId(1));
    }

}
