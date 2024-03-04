package com.epf.rentmanager.dto;

import com.epf.rentmanager.model.Vehicle;

import java.time.LocalDate;

public record ReservationWithVehicleDto(Long id, Vehicle vehicle, LocalDate debut, LocalDate fin) {
}
