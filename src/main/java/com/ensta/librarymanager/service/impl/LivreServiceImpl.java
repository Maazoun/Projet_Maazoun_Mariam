package com.ensta.librarymanager.service.impl;

import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.*;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.model.*;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.dao.impl.*;
import java.util.ArrayList;
import java.util.List;

public class LivreServiceImpl implements LivreService {
    /** Implémetation du pattern Singleton */
    private static LivreServiceImpl instance = new LivreServiceImpl();
    private LivreServiceImpl() { }
    public static LivreService getInstance() {
        return instance;
    }

    /** Obtenir la liste de tous les livres */
    @Override
    public List<livre> getList() throws ServiceException{
        List<livre> livres = new ArrayList<>();
        LivreDao livreDao = LivreDaoImpl.getInstance();
        try {
            livres = livreDao.getList();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return livres;
    }

    /** Obtenir la liste des livres qui peuvent être empruntés */
    @Override
    public List<livre> getListDispo() throws ServiceException{
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<livre> allLivres = new ArrayList<>();
        List<livre> possibleLivres = new ArrayList<>();

        allLivres=this.getList();
        for (int i=0; i < allLivres.size(); i++)
        {
            if (empruntService.isLivreDispo(allLivres.get(i).getId()))
                possibleLivres.add(allLivres.get(i));
        }
        return possibleLivres;
    }

    /** Obtenir un livre par son id */
    @Override
    public livre getById(int id) throws ServiceException{
        livre livre = new livre();
        LivreDao livreDao = LivreDaoImpl.getInstance();
        try {
            livre = livreDao.getById(id);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return (livre);
    }

    /** Créer un nouveau livre */
    @Override
    public int create(String titre, String auteur, String isbn) throws ServiceException{
        int i = -1;
        if (titre == null )
        {
            throw new ServiceException("erreur lors de la création de ce livre : titre vide");
        }
        else
        {
            LivreDao livreDao = LivreDaoImpl.getInstance();
            try {
                i = livreDao.create(titre, auteur, isbn);
            }  catch (DaoException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return i;
    }

    /** Modifier un livre */
    @Override
    public void update(livre livre) throws ServiceException{
        if ( livre.getTitre() == null )
        {
            throw new ServiceException("erreur lors de la mise a jour de ce livre : titre vide");
        }
        else
        {
            LivreDao livreDao = LivreDaoImpl.getInstance();
            try {
                livreDao.update(livre);
            } catch (DaoException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /** Supprimer un livre */
    @Override
    public void delete(int id) throws ServiceException{
        LivreDao livreDao = LivreDaoImpl.getInstance();
        try {
            livreDao.delete(id);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /** Compter le nombre total des livres de la base de données */
    @Override
    public int count() throws ServiceException{
        int k=0;
        LivreDao livreDao = LivreDaoImpl.getInstance();
        try {
            k = livreDao.count();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return k;
    }



}


