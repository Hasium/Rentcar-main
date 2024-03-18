package com.epf.rentmanager.model;

import com.epf.rentmanager.service.ClientService;

import java.time.LocalDate;
public record Client(long id, String nom, String prenom, String email, LocalDate naissance) {

    public Client(long id) {
        this(id, "", "", "", LocalDate.now());
    }

    public Client(String nom, String prenom, String email, LocalDate naissance) {
        this((long) 0, nom, prenom, email, naissance);
    }

    public Client(String nom, String prenom) {
        this((long) 0, nom, prenom, "", LocalDate.now());
    }

    public Client(long id, String nom, String prenom, String email) {
        this(id, nom, prenom, email, LocalDate.now());
    }

    public Client(String nom, String prenom, String email) {
        this((long) 0, nom, prenom, email, LocalDate.now());
    }

}
