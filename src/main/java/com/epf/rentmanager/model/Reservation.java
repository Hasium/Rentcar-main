package com.epf.rentmanager.model;

import java.time.LocalDate;

public record Reservation(Long id, Long client_id, Long vehicle_id, LocalDate debut, LocalDate fin) {

    public Reservation(Long id) {
        this(id, (long) 0, (long) 0, LocalDate.now(), LocalDate.now());
    }

    public Reservation(Long client_id, Long vehicle_id, LocalDate debut, LocalDate fin) {
        this((long) 0, client_id, vehicle_id, debut, fin);
    }

}
