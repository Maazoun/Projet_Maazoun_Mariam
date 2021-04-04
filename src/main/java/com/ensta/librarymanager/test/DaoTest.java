package com.ensta.librarymanager.test;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.abonnement;
import com.ensta.librarymanager.model.livre;
import com.ensta.librarymanager.model.membre;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.dao.impl.*;
import com.ensta.librarymanager.model.emprunt;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoTest {
     public static void main(String[] args) {
         membre membre1 = new membre(18, "Maazoun", "Mariam", "+330604337854", "mariam.maazoun@ensta-paris.fr", "Palaiseau", abonnement.VIP);
         livre livre1 = new livre(1, "Adultère", "Coelho", "0dxxy568");
         LocalDate dateEmprunt = LocalDate.of(2021, 03, 16);
         LocalDate dateRetour = LocalDate.of(2021, 04, 16);

         EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
         emprunt emprunt = new emprunt();
         List<emprunt> emprunts = new ArrayList<>();

         /** Test de getList */
         System.out.println("*****   List des emprunts    ****");
         try {
             emprunts = empruntDao.getList();
         } catch (DaoException e) {
             System.out.println(e.getMessage());
         }
         for (int i=0; i<emprunts.size();i++)
             System.out.println(emprunts.get(i));

         /** Test de getListCurrent */
         try {
             emprunts = empruntDao.getListCurrent();
         } catch (DaoException e) {
             System.out.println(e.getMessage());
         }
         for (int i=0; i<emprunts.size();i++)
             System.out.println(emprunts.get(i));

         /** Test de getListCurrentByMembre */
         System.out.println("*****   List des emprunts dont le id membre est 2   ****");
         try {
             emprunts = empruntDao.getListCurrentByMembre(2);
         } catch (DaoException e) {
             System.out.println(e.getMessage());
         }
         for (int i=0; i<emprunts.size();i++)
             System.out.println(emprunts.get(i));

         /** Test de getListCurrentByLivre */
         System.out.println("*****   List des emprunts dont l'id livre est 3    ****");
         try {
             emprunts = empruntDao.getListCurrentByLivre(3);
         } catch (DaoException e) {
             System.out.println(e.getMessage());
         }
         for (int i=0; i<emprunts.size();i++)
             System.out.println(emprunts.get(i));

         /** Test de getById */
         try {
             emprunt = empruntDao.getById(2);
         } catch (DaoException e) {
             System.out.println(e.getMessage());

         }

         /** Test de create */
         System.out.println("*****   création d'un nouveau emprunt     ****");
         int i = -1;
         try {
             empruntDao.create(membre1.getId(), livre1.getId(), dateEmprunt, dateRetour);
         }  catch (DaoException e1) {
             System.out.println(e1.getMessage());
         }

         /** Test de update */
         System.out.println("*****   Mise à jour  des emprunts    ****");
         emprunt emprunt1 = new emprunt(5, membre1, livre1, dateEmprunt, dateRetour);
         try {
             empruntDao.update(emprunt1);
         } catch (DaoException e) {
             System.out.println(e.getMessage());
         }

         /** Test de count */
         System.out.println("*****  Nombre total  des emprunts :    ****");
         try {
             int k=empruntDao.count();
         } catch (DaoException e) {
             System.out.println(e.getMessage());
         }
     }
}

