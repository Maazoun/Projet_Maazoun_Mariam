package com.ensta.librarymanager.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.model.abonnement;
import com.ensta.librarymanager.model.membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class MembreDaoImpl implements MembreDao {

    private static MembreDaoImpl instance;
    private MembreDaoImpl() { }
    public static MembreDao getInstance() {
        if(instance == null) {
            instance = new MembreDaoImpl();
        }
        return instance;
    }

    private static final String GET_ALL_QUERY = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom;";
    private static final String GET_BYID_QUERY = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?;";
    private static final String CREATE_QUERY = "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM membre WHERE id = ?;";
    private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM membre;";

    /** Retourner la liste de tous les membres */
    @Override
    public List<membre> getList() throws DaoException{
        List<membre> membres = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUERY);
             ResultSet res = preparedStatement.executeQuery();
        ){
            while (res.next()) {
                membre membre = new membre(res.getInt("id"), res.getString("nom"),
                        res.getString("prenom"), res.getString("adresse"), res.getString("email"),
                        res.getString("telephone"), abonnement.valueOf(res.getString("abonnement")));
                membres.add(membre);
            }
            System.out.println("GET"+ membres);
        } catch (SQLException e) {
            throw new DaoException("Erreur lors de la recuperation de la liste des membres", e);
        }
        return membres;
    }

    /** Retourner un membre à partir de son id */
    @Override
    public membre getById(int id) throws DaoException{
        membre membre = new membre();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_BYID_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            if (res.next()) {
                membre.setId(res.getInt("id"));
                membre.setNom(res.getString("nom"));
                membre.setPrenom(res.getString("prenom"));
                membre.setAdresse(res.getString("adresse"));
                membre.setEmail(res.getString("email"));
                membre.setTelephone(res.getString("telephone"));
                membre.setAbonnement(abonnement.valueOf(res.getString("abonnement")));
            }
            System.out.println("GET: "+ membre);
        } catch (SQLException e) {
            throw new DaoException("Erreur lors de la recuperation du membre dont id="+ id, e);
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
        return membre;
    }

    /** Création d'un nouveau membre */
    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone, abonnement abonnement ) throws DaoException{
        membre membre = new membre();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int id = -1;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, adresse);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, telephone);
            preparedStatement.setString(6, abonnement.toString());
            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if (res.next()) {
                id = res.getInt(1);
            }
            membre = new membre(id, nom, prenom, adresse, email, telephone, abonnement);
            System.out.println("CREATE: "+ membre );
        } catch (SQLException e) {
            throw new DaoException("Erreur lors de la creation du nouveau membre: "+ membre , e);
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
        return id;
    }

    /** Modification d' un membre */
    @Override
    public void update(membre membre) throws DaoException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, membre.getNom());
            preparedStatement.setString(2, membre.getPrenom());
            preparedStatement.setString(3, membre.getAdresse());
            preparedStatement.setString(4, membre.getEmail());
            preparedStatement.setString(5, membre.getTelephone());
            preparedStatement.setString(6, membre.getAbonnement().toString());
            preparedStatement.setInt(7, membre.getId());
            preparedStatement.executeUpdate();
            System.out.println("UPDATE: "+ membre);
        }catch (SQLException e) {
            throw new DaoException("Erreur lors de la mise a jour du membre" + membre, e);
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

    /** Suppression d'un membre */
    @Override
    public void delete(int id) throws DaoException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            System.out.println("DELETE: membre dont l'id est "+ id);
        } catch (SQLException e) {
            throw new DaoException("Erreur lors de la suppression du membre : id=" + id, e);
        }  finally {
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

    /** Compter le nombre total des membres */
    @Override
    public int count() throws DaoException{
        int k = 0;
        PreparedStatement preparedStatement=null;
        Connection connection = null;
        ResultSet res=null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(COUNT_QUERY);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                k = res.getInt("count");
            }
            System.out.println("GET total membres "+ k);
        } catch (SQLException e) {
            throw new DaoException("Erreur lors de la recuperation du nombre total des membres", e);
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
        return k;
    }

}

