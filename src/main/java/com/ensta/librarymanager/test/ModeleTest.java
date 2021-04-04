package com.ensta.librarymanager.test;
import java.time.LocalDate;
import com.ensta.librarymanager.model.*;
public class ModeleTest {
    public static void main(String[] args) {
        membre membre1 = new membre(18, "Maazoun", "Mariam", "+330604337854", "mariam.maazoun@ensta-paris.fr", "Palaiseau", abonnement.VIP);
        livre livre1 = new livre(1, "Adultère", "Coelho", "0dxxy568");
        LocalDate dateEmprunt = LocalDate.of(2021, 03, 16);
        LocalDate dateRetour = LocalDate.of(2021, 04, 16);
        emprunt emprunt1 = new emprunt(1, membre1, livre1, dateEmprunt, dateRetour);
        String ch1 = emprunt1.toString();
        String ch2 = membre1.toString();
        String ch3 = livre1.toString();
        System.out.println("*****     Liste des emprunts     ****");
        System.out.println(ch1);
        System.out.println("*****     Liste des membres        ****");
        System.out.println(ch2);
        System.out.println("*****      Liste des livres      ****");
        System.out.println(ch3);
    }
}

