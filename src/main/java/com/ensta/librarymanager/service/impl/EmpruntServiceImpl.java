package com.ensta.librarymanager.service.impl;

import com.ensta.librarymanager.service.EmpruntService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.model.*;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.dao.impl.*;


public class EmpruntServiceImpl implements EmpruntService {
    /** Implémetation du pattern Singleton */
    private static EmpruntServiceImpl instance = new EmpruntServiceImpl();
    private EmpruntServiceImpl() { }
    public static EmpruntService getInstance() {
        return instance;
    }

    /** Retourner la liste de tous les emprunts de la base de données */
    @Override
    public List<emprunt> getList() throws ServiceException{
        List<emprunt> emprunts = new ArrayList<>();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        try {
            emprunts = empruntDao.getList();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return emprunts;
    }

    /** Retourner la liste des emprunts non encore rendus */
    @Override
    public List<emprunt> getListCurrent() throws ServiceException{
        List<emprunt> emprunts = new ArrayList<>();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        try {
            emprunts = empruntDao.getListCurrent();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return emprunts;
    }

    /** Retourner la liste des emprunts non rendus par un membre */
    @Override
    public List<emprunt> getListCurrentByMembre(int idMembre) throws ServiceException{
        List<emprunt> emprunts = new ArrayList<>();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        try {
            emprunts = empruntDao.getListCurrentByMembre(idMembre);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
        return emprunts;
    }

    /** Retourner la liste des emprunts non rendus pour un livre */
    @Override
    public List<emprunt> getListCurrentByLivre(int idLivre) throws ServiceException{
        List<emprunt> emprunts = new ArrayList<>();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        try {
            emprunts = empruntDao.getListCurrentByLivre(idLivre);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return emprunts;
    }

    /** Retourner un emprunt par son id */
    @Override
    public emprunt getById(int id) throws ServiceException{
        emprunt emprunt = new emprunt();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        try {
            emprunt = empruntDao.getById(id);
        } catch (DaoException e) {
            System.out.println (e.getMessage());
            e.printStackTrace();
        }
        return emprunt;
    }

    /** Creer un nouvel emprunt */
    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt, LocalDate dateRetour) throws ServiceException{
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        try {
            empruntDao.create(idMembre, idLivre, dateEmprunt, dateRetour);
        }  catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /** Compter le nombre total des emprunts */
    @Override
    public int count() throws ServiceException{
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        int k = 0;
        try {
            k=empruntDao.count();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return k;
    }

    /** Modifier la date de retour d'un emprunt */
    @Override
    public void returnBook(int id) throws ServiceException{
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            emprunt emprunt = empruntDao.getById(id);
            emprunt.setDateRetour(LocalDate.now());
            empruntDao.update(emprunt);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    /** Déterminer si un livre est disponible à l'emprunt ou non */
    @Override
    public boolean isLivreDispo(int idLivre) throws ServiceException{
        Boolean isDispo = true;
        List<emprunt> emprunts = new ArrayList<>();
        emprunts = this.getListCurrentByLivre(idLivre);
        if (emprunts.size()!=0)
            isDispo = false;
        return isDispo;
    }

    /** Déterminer si un membre peut encore emprunter */
    @Override
    public boolean isEmpruntPossible(membre membre) throws ServiceException{
        Boolean isPossible = false;
        List<emprunt> emprunts = new ArrayList<>();
        emprunts = this.getListCurrentByMembre(membre.getId());
        int quota = 0;
        switch (membre.getAbonnement()){
            case BASIC :
                quota = 2;
            case PREMIUM :
                quota = 5;
            case VIP :
                quota = 20;
        }
        if (emprunts.size() < quota )
            isPossible = true ;
        return isPossible;

    }

}

