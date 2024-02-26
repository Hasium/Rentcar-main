package com.epf.rentmanager.model;

import java.time.LocalDate;

public record Reservation(Long id, Long client_id, Long vehicle_id, LocalDate debut, LocalDate fin) { }
