package com.ensta.librarymanager.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.ensta.librarymanager.model.*;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.persistence.ConnectionManager;
import com.ensta.librarymanager.exception.*;

public class EmpruntDaoImpl implements EmpruntDao{
    private static EmpruntDaoImpl instance;
    private EmpruntDaoImpl() { }
    public static EmpruntDao getInstance() {
        if(instance == null) {
            instance = new EmpruntDaoImpl();
        }
        return instance;
    }
    private static final String SELECT_ALL_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" +
            "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" +
            "dateRetour\r\n" +
            "FROM emprunt AS e\r\n" +
            "INNER JOIN membre ON membre.id = e.idMembre\r\n" +
            "INNER JOIN livre ON livre.id = e.idLivre\r\n" +
            "ORDER BY dateRetour DESC;";
    private static final String SELECT_ALL_NON_RETURN = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" +
            "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" +
            "dateRetour\r\n" +
            "FROM emprunt AS e\r\n" +
            "INNER JOIN membre ON membre.id = e.idMembre\r\n" +
            "INNER JOIN livre ON livre.id = e.idLivre\r\n" +
            "WHERE dateRetour IS NULL;";
    private static final String SELECT_ONEMEMBER_NON_RETURN = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" +
            "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,dateRetour\r\n" +
            "FROM emprunt AS e\r\n" +
            "INNER JOIN membre ON membre.id = e.idMembre\r\n" +
            "INNER JOIN livre ON livre.id = e.idLivre\r\n" +
            "WHERE dateRetour IS NULL AND membre.id = ?;";
    private static final String SELECT_ONEBOOK_NON_RETURN = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" +
            "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" +
            "dateRetour\r\n" +
            "FROM emprunt AS e\r\n" +
            "INNER JOIN membre ON membre.id = e.idMembre\r\n" +
            "INNER JOIN livre ON livre.id = e.idLivre\r\n" +
            "WHERE dateRetour IS NULL AND livre.id = ?;";
    private static final String SELECT_ONE_QUERY = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email,\r\n" +
            "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" +
            "dateRetour\r\n" +
            "FROM emprunt AS e\r\n" +
            "INNER JOIN membre ON membre.id = e.idMembre\r\n" +
            "INNER JOIN livre ON livre.id = e.idLivre\r\n" +
            "WHERE e.id = ?;";
    private static final String CREATE_QUERY = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?;";
    private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM emprunt;";

    /** Retourner la liste de tous les emprunts */
    @Override
    public List<emprunt> getList() throws DaoException{

        List<emprunt> emprunts = new ArrayList<>();
        emprunt emprunt = new emprunt();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
             ResultSet res = preparedStatement.executeQuery();
        ){
            while(res.next()) {
                LocalDate d1 = res.getDate("dateEmprunt").toLocalDate() ;
                Date date = res.getDate("dateRetour");
                LocalDate d2 = null ;
                if(date!=null) d2 = res.getDate("dateRetour").toLocalDate() ;
                membre membre = new membre(res.getInt("idMembre"),res.getString("nom"),res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abonnement.valueOf(res.getString("abonnement")) );
                livre livre = new livre(res.getInt("idLivre"), res.getString("titre"),res.getString("auteur"),res.getString("isbn"));
                emprunt = new emprunt(res.getInt("id"),membre ,livre,d1 ,d2);
                emprunts.add(emprunt);
            }
            System.out.println("GET: " + emprunts);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des emprunts", e);
        }
        return emprunts;
    }

    /** Retourner les emprunts pas encore rendus*/
    @Override
    public List<emprunt> getListCurrent() throws DaoException{
        List<emprunt> emprunts = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet res = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_NON_RETURN);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                LocalDate d1 = res.getDate("dateEmprunt").toLocalDate() ;
                LocalDate d2 = null ;
                membre membre = new membre(res.getInt("idMembre"),res.getString("nom"),res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abonnement.valueOf(res.getString("abonnement")) );
                livre livre = new livre(res.getInt("idLivre"), res.getString("titre"),res.getString("auteur"),res.getString("isbn"));
                emprunt emprunt = new emprunt(res.getInt("id") ,membre,livre,d1,d2);
                emprunts.add(emprunt);
            }
            System.out.println("GET NON RETURN"+ emprunts);
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation de la liste des emprunts non encore rendus", e);
        }
        return emprunts;
    }

    /** Retourner la liste des emprunts non rendus par un membre spécifique*/
    @Override
    public List<emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
        List<emprunt> emprunts = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet res = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ONEMEMBER_NON_RETURN);
            preparedStatement.setInt(1, idMembre);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                LocalDate d1 = res.getDate("dateEmprunt").toLocalDate() ;
                LocalDate d2 = null ;
                membre membre = new membre(res.getInt("idMembre"),res.getString("nom"),res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abonnement.valueOf(res.getString("abonnement")) );
                livre livre = new livre(res.getInt("idLivre"), res.getString("titre"),res.getString("auteur"),res.getString("isbn"));
                emprunt emprunt = new emprunt(res.getInt("id") ,membre,livre, d1,d2);
                emprunts.add(emprunt);
            }
            System.out.println("GET NON RETURN BY A MEMBER"+ emprunts);
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation de la liste des emprunts non encore rendus par le membre: id="+idMembre, e);
        }
        return emprunts;
    }

    /** Retourner la liste des emprunts non rendus pour un livre spécifique*/
    @Override
    public List<emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
        List<emprunt> emprunts = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet res = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ONEBOOK_NON_RETURN);
            preparedStatement.setInt(1, idLivre);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                LocalDate d1 = res.getDate("dateEmprunt").toLocalDate() ;
                LocalDate d2 = null ;
                membre membre = new membre(res.getInt("idMembre"),res.getString("nom"),res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abonnement.valueOf(res.getString("abonnement")) );
                livre livre = new livre(res.getInt("idLivre"), res.getString("titre"),res.getString("auteur"),res.getString("isbn"));
                emprunt emprunt = new emprunt(res.getInt("id") ,membre,livre, d1,d2);
                emprunts.add(emprunt);
            }
            System.out.println("GET NON RETURN BY A MEMBER"+ emprunts);
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation de la liste des emprunts non encore rendus pour le livre: id="+idLivre, e);
        }
        return emprunts;
    }

    /** Retourner un emprunt à partir d'un id */
    @Override
    public emprunt getById(int id) throws DaoException{
        emprunt emprunt = new emprunt();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao livreDao = LivreDaoImpl.getInstance();
            if (res.next()) {
                emprunt = new emprunt(res.getInt("id"), membreDao.getById(res.getInt("idMembre")), livreDao.getById(res.getInt("idLivre")), res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour") == null ? null : res.getDate("dateRetour").toLocalDate());
            }
            System.out.println("GET: "+ emprunt);
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation de l'emprunt : id="+ id, e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunt;
    }


    /** Créer un nouveau emprunt */
    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt, LocalDate dateRetour) throws DaoException{
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int id = -1;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, idMembre);
            preparedStatement.setInt(2, idLivre);
            preparedStatement.setDate(3, java.sql.Date.valueOf(dateEmprunt));
            preparedStatement.setDate(4, null);
            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if (res.next()) {
                MembreDao membreDao = MembreDaoImpl.getInstance();
                LivreDao livreDao = LivreDaoImpl.getInstance();
                id = res.getInt(1);
                emprunt emprunt = new emprunt(id, membreDao.getById(idMembre), livreDao.getById(idLivre), dateEmprunt, null);
                System.out.println("CREATE: "+ emprunt );
            }
        } catch (SQLException e) {
            throw new DaoException("Erreur lors de la creation du nouvel emprunt: id="+ id , e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** Modifier un emprunt */
    @Override
    public void update(emprunt emprunt) throws DaoException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            try (PreparedStatement statement = preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
            }
            preparedStatement.setObject(1, emprunt.getMembre());
            preparedStatement.setObject(2, emprunt.getLivre());
            preparedStatement.setDate(3, java.sql.Date.valueOf(emprunt.getDateEmprunt()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(emprunt.getDateRetour()));
            preparedStatement.setInt(5, emprunt.getId());
            preparedStatement.executeUpdate();
            System.out.println("UPDATE: "+ emprunt);
        }catch (SQLException e) {
            throw new DaoException("Probleme lors de la mise a jour de l'emprunt" + emprunt, e);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** Compter le nombre total des livres */
    @Override
    public int count() throws DaoException{
        int total = 0;
        PreparedStatement preparedStatement=null;
        Connection connection = null;
        ResultSet res=null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(COUNT_QUERY);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                total = res.getInt("count");
            }
            System.out.println("GET total emprunts "+ total);
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation du nombre total des emprunts", e);
        }finally {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return total;
    }

}

