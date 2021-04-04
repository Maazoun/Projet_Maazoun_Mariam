package com.ensta.librarymanager.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.model.*;
import com.ensta.librarymanager.service.*;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.service.impl.*;

public class ServiceTest {
    public static void main (String[] args) {
        membre membre1 = new membre(18, "Maazoun", "Mariam", "+330604337854", "mariam.maazoun@ensta-paris.fr", "Palaiseau", abonnement.VIP);
        livre livre1 = new livre(19, "Adultère", "Coelho", "0dxxy568");
        LocalDate dateEmprunt = LocalDate.of(2021, 03, 16);
        LocalDate dateRetour = LocalDate.of(2021, 04, 16);

        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        emprunt emprunt = new emprunt();
        List<emprunt> emprunts = new ArrayList<>();

        /** Test de getById */
        try {
            emprunt = empruntService.getById(2);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();

        }
        System.out.println("*****   Création d' emprunts    ****");
        /** Test de create */
		try {
			empruntService.create(1, 1, dateEmprunt, dateRetour);
		}  catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}

        /** Test de returnBook */
        /*try {
            empruntService.returnBook(1);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }*/
        System.out.println("*****   List des emprunts    ****");
        /** Test de getList */
		try {
			emprunts = empruntService.getList();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
		for (int j=0; j<emprunts.size();j++)
			System.out.println(emprunts.get(j));

        /** Test de getListCurrent */
		try {
			emprunts = empruntService.getListCurrent();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
		for (int j=0; j<emprunts.size();j++)
			System.out.println(emprunts.get(j));

        /** Test de getListCurrentByMembre */
        try {
			emprunts = empruntService.getListCurrentByMembre(4);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
		for (int j=0; j<emprunts.size();j++)
			System.out.println(emprunts.get(j));

        /** Test de getListCurrentByLivre*/
        try {
			emprunts = empruntService.getListCurrentByLivre(3);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
		for (int j=0; j<emprunts.size();j++)
			System.out.println(emprunts.get(j));
        System.out.println("*****   Nombre total :    ****");
        /** Test de count */
		try {
			int k = empruntService.count();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
        System.out.println("*****   livre dispo ?!   ****");
        /** Test de isLivreDispo */
		try {
			Boolean test = empruntService.isLivreDispo(10);
			System.out.println(test);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
        System.out.println("*****   emprunt possible ?   ****");
        /** Test de isEmpruntPossible */
		try {
			Boolean test = empruntService.isEmpruntPossible(membre1);
			System.out.println(test);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.getStackTrace();

    }


}}

