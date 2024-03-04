package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.utils.IOUtils;

public class main {

    public static void main(String[] args) {
        System.out.println("Bienvenu dans l’interface en ligne de commande");
        Cli cli = Cli.getInstance();
        boolean run = true;
        while (run) {
            String choice = IOUtils.readString(
                    """
                            Que voulez-vous faire ?
                            1. Créer un client
                            2. Lister les clients
                            3. Créer un véhicule
                            4. Lister les véhicules
                            5. Créer une réservation
                            6. Lister les réservations
                            7. Trouver les réservations par client
                            8. Trouver les réservations par véhicule
                            9. Supprimer un client
                            10. Supprimer un véhicule
                            11. Supprimer une réservation
                            12. Compter le nombre de véhicules loués par client
                            13. Compter le nombre de réservations par client
                            14. Quitter""", true);
            switch (choice) {
                case "1" -> cli.createClient();
                case "2" -> cli.getAllClient();
                case "3" -> cli.createVehicle();
                case "4" -> cli.getAllVehicle();
                case "5" -> cli.createResa();
                case "6" -> cli.getAllResa();
                case "7" -> cli.findResaByClientId();
                case "8" -> cli.findResaByVehicleId();
                case "9" -> cli.deleteClient();
                case "10" -> cli.deleteVehicle();
                case "11" -> cli.deleteResa();
                case "12" -> cli.countVehiclesRentedByClient();
                case "13" -> cli.countResaByClient();
                case "14" -> run = false;
                default -> IOUtils.print("Choix invalide");
            }
        }
    }

}
