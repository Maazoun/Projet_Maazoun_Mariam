package com.ensta.librarymanager.service.impl;

import com.ensta.librarymanager.service.*;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.model.*;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.dao.impl.*;
import java.util.ArrayList;
import java.util.List;

public class MembreServiceImpl implements MembreService {
    /** Implémetation du pattern Singleton */
    private static MembreServiceImpl instance = new MembreServiceImpl();
    private MembreServiceImpl() { }
    public static MembreService getInstance() {
        return instance;
    }

    /** Obtenir la liste de tous les membres */
    @Override
    public List<membre> getList() throws ServiceException{
        List<membre> membres = new ArrayList<>();
        MembreDao membreDao = MembreDaoImpl.getInstance();
        try {
            membres = membreDao.getList();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
        return membres;
    }

    /** Obtenir la liste des membres qui peuvent emprunter */
    @Override
    public List<membre> getListMembreEmpruntPossible() throws ServiceException{
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<membre> allMembres = new ArrayList<>();
        List<membre> possibleMembres = new ArrayList<>();

        allMembres=this.getList();
        for (int i=0; i < allMembres.size(); i++)
        {
            if (empruntService.isEmpruntPossible(allMembres.get(i)))
                possibleMembres.add(allMembres.get(i));
        }
        return possibleMembres;
    }

    /** Obtenir un membre par son id */
    @Override
    public membre getById(int id) throws ServiceException{
        membre membre = new membre();
        MembreDao membreDao = MembreDaoImpl.getInstance();
        try {
            membre = membreDao.getById(id);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
        return (membre);
    }

    /** Créer un nouveau membre */
    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone, abonnement abonnement) throws ServiceException{
        int i = -1;
        if (nom == null || prenom == null)
        {
            throw new ServiceException("erreur lors de la création de ce membre : nom ou prénom vide");
        }
        else
        {
            MembreDao membreDao = MembreDaoImpl.getInstance();
            // Transformer le nom de faille en majuscule
            nom = nom.toUpperCase();
            try {
                i = membreDao.create(nom, prenom, adresse, email, telephone, abonnement);
            }  catch (DaoException e) {
                System.out.println(e.getMessage());
            }
        }
        return i;
    }

    /** Modifier un membre */
    @Override
    public void update(membre membre) throws ServiceException{
        if ( membre.getNom() == null || membre.getPrenom() == null )
        {
            throw new ServiceException("erreur lors de la mise a jour de ce membre : nom ou prénom vide");
        }
        else
        {
            MembreDao membreDao = MembreDaoImpl.getInstance();
            // Transformer le nom de faille en majuscule
            membre.setNom(membre.getNom().toUpperCase());
            try {
                membreDao.update(membre);
            } catch (DaoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /** Supprimer un membre */
    @Override
    public void delete(int id) throws ServiceException{
        MembreDao membreDao = MembreDaoImpl.getInstance();
        try {
            membreDao.delete(id);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Compter le nombre total des membres de la base de données */
    @Override
    public int count() throws ServiceException{
        int k=0;
        MembreDao membreDao = MembreDaoImpl.getInstance();
        try {
            k=membreDao.count();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
        return k;
    }



}

