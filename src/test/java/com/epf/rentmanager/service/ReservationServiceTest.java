package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationDao reservationDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.reservationDao.findAll()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.findAll());
    }

    @Test
    void create_should_fail_when_vehicle_is_already_reserved_overlap_ending() throws DaoException {
        // Given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation((long) 1, (long) 1, (long) 1, LocalDate.now().minusDays(10), LocalDate.now().minusDays(4)));
        when(this.reservationDao.findResaByVehicleId(1)).thenReturn(reservations);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.create(new Reservation(
                (long) 1,
                (long) 1,
                LocalDate.now().minusDays(5),
                LocalDate.now()
        )));
    }

    @Test
    void create_should_fail_when_vehicle_is_already_reserved_inside_period() throws DaoException {
        // Given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation((long) 1, (long) 1, (long) 1, LocalDate.now().minusDays(5), LocalDate.now().minusDays(4)));
        when(this.reservationDao.findResaByVehicleId(1)).thenReturn(reservations);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.create(new Reservation(
                (long) 1,
                (long) 1,
                LocalDate.now().minusDays(6),
                LocalDate.now().minusDays(3)
        )));
    }

    @Test
    void create_should_fail_when_vehicle_is_already_reserved_outside_period() throws DaoException {
        // Given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation((long) 1, (long) 1, (long) 1, LocalDate.now().minusDays(8), LocalDate.now().minusDays(4)));
        when(this.reservationDao.findResaByVehicleId(1)).thenReturn(reservations);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.create(new Reservation(
                (long) 1,
                (long) 1,
                LocalDate.now().minusDays(6),
                LocalDate.now().minusDays(5)
        )));
    }

    @Test
    void create_should_fail_when_vehicle_is_already_reserved_end_equals_start() throws DaoException {
        // Given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation((long) 1, (long) 1, (long) 1, LocalDate.now().minusDays(5), LocalDate.now().minusDays(4)));
        when(this.reservationDao.findResaByVehicleId(1)).thenReturn(reservations);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.create(new Reservation(
                (long) 1,
                (long) 1,
                LocalDate.now().minusDays(4),
                LocalDate.now().minusDays(2)
        )));
    }

    @Test
    void create_should_fail_when_vehicle_is_already_reserved_start_equals_end() throws DaoException {
        // Given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation((long) 1, (long) 1, (long) 1, LocalDate.now().minusDays(5), LocalDate.now().minusDays(4)));
        when(this.reservationDao.findResaByVehicleId(1)).thenReturn(reservations);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.create(new Reservation(
                (long) 1,
                (long) 1,
                LocalDate.now().minusDays(8),
                LocalDate.now().minusDays(5)
        )));
    }

    @Test
    void create_should_fail_when_vehicle_is_already_reserved_overlap_begining() throws DaoException {
        // Given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation((long) 1, (long) 1, (long) 1, LocalDate.now().minusDays(5), LocalDate.now().minusDays(3)));
        when(this.reservationDao.findResaByVehicleId(1)).thenReturn(reservations);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.create(new Reservation(
                (long) 1,
                (long) 1,
                LocalDate.now().minusDays(8),
                LocalDate.now().minusDays(4)
        )));
    }

    @Test
    void create_should_pass_when_vehicle_is_not_already_reserved() throws DaoException, ServiceException {
        // Given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation((long) 1, (long) 1, (long) 1, LocalDate.now().minusDays(5), LocalDate.now().minusDays(3)));
        when(this.reservationDao.findResaByVehicleId(1)).thenReturn(reservations);

        // Then
        assertEquals(0, reservationService.create(new Reservation(
                (long) 1,
                (long) 1,
                LocalDate.now().minusDays(8),
                LocalDate.now().minusDays(6)
        )));
    }

    @Test
    void create_should_fail_when_vehicle_is_reserved_more_than_30_days() throws DaoException {
        // Given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation((long) 1, (long) 1, LocalDate.now().minusDays(19), LocalDate.now().minusDays(15)));
        reservations.add(new Reservation((long) 1, (long) 1, LocalDate.now().minusDays(43), LocalDate.now().minusDays(41)));
        reservations.add(new Reservation((long) 1, (long) 1, LocalDate.now().minusDays(40), LocalDate.now().minusDays(20)));
        when(this.reservationDao.findResaByVehicleId(1)).thenReturn(reservations);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.create(new Reservation(
                (long) 1,
                (long) 1,
                LocalDate.now().minusDays(14),
                LocalDate.now().minusDays(8)
        )));
    }

}
