package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;

public class Cli {

    public static Cli instance;
    private ClientService clientService;
    private VehicleService vehicleService;
    private ReservationService reservationService;

    private Cli() {
        this.clientService = ClientService.getInstance();
        this.vehicleService = VehicleService.getInstance();
    }

    public static Cli getInstance() {
        if (instance == null) {
            instance = new Cli();
        }
        return instance;
    }

    public void createClient() {
        IOUtils.print("Création d'un client");
        String nom = IOUtils.readString("Entrez le nom du client :", true);
        String prenom = IOUtils.readString("Entrez le prénom du client :", true);
        String email = IOUtils.readEmail("Entrez l'email du client :", true);
        LocalDate naissance = IOUtils.readDate("Entrez la date de naissance du client :", true);
        ClientService clientService = ClientService.getInstance();
        try {
            long id = clientService.create(new Client(
                    nom,
                    prenom,
                    email,
                    naissance
            ));
            IOUtils.print("Le client a bien été crée avec l'id :" + id);
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }
    }

    public void getAllClient() {
        IOUtils.print("Liste des clients");
        ClientService clientService = ClientService.getInstance();
        IOUtils.print("Liste des clients :");
        try {
            for (Client client : clientService.findAll()) {
                IOUtils.print(client.toString());
            }
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }
    }

    public void deleteClient() {
        IOUtils.print("Suppression d'un client");
        ClientService clientService = ClientService.getInstance();
        long id = IOUtils.readInt("Entrez l'id du client à supprimer :");
        try {

            clientService.delete(new Client(id));
            IOUtils.print("Le client a bien été supprimé");
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }
    }

    public void createVehicle() {
        IOUtils.print("Création d'un véhicule");
        String constructeur = IOUtils.readString("Entrez le constructeur du véhicule :", true);
        String modele = IOUtils.readString("Entrez le modèle du véhicule :", true);
        int nb_places = IOUtils.readInt("Entrez le nombre de places du véhicule :");
        VehicleService vehicleService = VehicleService.getInstance();
        try {
            long id = vehicleService.create(new Vehicle(
                    constructeur,
                    modele,
                    (short) nb_places
            ));
            IOUtils.print("Le véhicule a bien été crée avec l'id :" + id);
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }
    }

    public void getAllVehicle() {
        IOUtils.print("Liste des véhicules");
        VehicleService vehicleService = VehicleService.getInstance();
        IOUtils.print("Liste des véhicules :");
        try {
            for (Vehicle vehicle : vehicleService.findAll()) {
                IOUtils.print(vehicle.toString());
            }
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }
    }

    public void deleteVehicle() {
        IOUtils.print("Suppression d'un véhicule");
        VehicleService vehicleService = VehicleService.getInstance();
        long id = IOUtils.readInt("Entrez l'id du véhicule à supprimer :");
        try {
            vehicleService.delete(new Vehicle(id));
            IOUtils.print("Le véhicule a bien été supprimé");
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }
    }

    public void createResa() {
        IOUtils.print("Création d'une réservation");
        long client_id = IOUtils.readInt("Entrez l'id du client :");
        long vehicle_id = IOUtils.readInt("Entrez l'id du véhicule :");
        LocalDate debut = IOUtils.readDate("Entrez la date de début de la réservation :", true);
        LocalDate fin = IOUtils.readDate("Entrez la date de fin de la réservation :", true);
        ReservationService reservationService = ReservationService.getInstance();
        try {
            long id = reservationService.create(new Reservation(
                    client_id,
                    vehicle_id,
                    debut,
                    fin
            ));
            IOUtils.print("La réservation a bien été crée avec l'id :" + id);
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }
    }

    public void getAllResa() {
        IOUtils.print("Liste des réservations");
        ReservationService reservationService = ReservationService.getInstance();
        IOUtils.print("Liste des réservations :");
        try {
            for (Reservation resa : reservationService.findAll()) {
                IOUtils.print(resa.toString());
            }
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }
    }

    public void deleteResa() {
        IOUtils.print("Suppression d'une réservation");
        ReservationService reservationService = ReservationService.getInstance();
        long id = IOUtils.readInt("Entrez l'id de la réservation à supprimer :");
        try {
            reservationService.delete(new Reservation(id));
            IOUtils.print("La réservation a bien été supprimée");
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }
    }

    public void findResaByClientId() {
        IOUtils.print("Recherche des réservations par client");
        ReservationService reservationService = ReservationService.getInstance();
        long id = IOUtils.readInt("Entrez l'id du client :");
        IOUtils.print("Liste des réservations du client :");
        try {
            for (Reservation resa : reservationService.findResaByClientId(id)) {
                IOUtils.print(resa.toString());
            }
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }
    }

    public void findResaByVehicleId() {
        IOUtils.print("Recherche des réservations par véhicule");
        ReservationService reservationService = ReservationService.getInstance();
        long id = IOUtils.readInt("Entrez l'id du véhicule :");
        IOUtils.print("Liste des réservations du véhicule :");
        try {
            for (Reservation resa : reservationService.findResaByVehicleId(id)) {
                IOUtils.print(resa.toString());
            }
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }
    }
}
