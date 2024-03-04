package com.epf.rentmanager.model;

public record Vehicle(Long id, String constructeur, String modele, int nbPlaces) {

    public Vehicle(Long id) {
        this(id, "", "", 0);
    }

    public Vehicle(String constructeur, String modele) {
        this((long) 0, constructeur, modele, 0);
    }

    public Vehicle(String constructeur, String modele, int nbPlaces) {
        this((long) 0, constructeur, modele, nbPlaces);
    }

}
