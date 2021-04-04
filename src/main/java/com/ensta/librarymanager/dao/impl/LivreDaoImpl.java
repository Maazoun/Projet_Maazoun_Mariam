package com.ensta.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.ensta.librarymanager.model.livre;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.persistence.ConnectionManager;
import com.ensta.librarymanager.exception.*;

public class LivreDaoImpl implements LivreDao {
    private static LivreDaoImpl instance;
    private LivreDaoImpl() { }
    public static LivreDaoImpl getInstance() {
        if(instance == null) {
            instance = new LivreDaoImpl();
        }
        return instance;
    }
    private static final String GET_LIST="SELECT id, titre, auteur, isbn FROM livre;";
    private static final String GET_BYID_QUERY = "SELECT id, titre, auteur, isbn FROM livre WHERE id = ?;";
    private static final String CREATE_QUERY = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM livre WHERE id = ?;";
    private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM livre;";

    /** Retourner la liste de tous les livres*/
    @Override
    public List<livre> getList()throws DaoException{
        List<livre> livres=new ArrayList<>();
        try(Connection connection=ConnectionManager.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(GET_LIST);
            ResultSet res=preparedStatement.executeQuery();)
        {while (res.next()){
            livre livre=new livre(res.getInt("id"),res.getString("titre"),res.getString("auteur"),res.getString("isbn"));
            livres.add(livre);
        }
        System.out.println("Get"+livres);}
        catch (SQLException e) {
            throw new DaoException("Erreur lors de la recuperation des livres!", e);
        }
        return livres;
        }
    /** Retourner un livre à partir d'un id */
    @Override
    public livre getById(int id) throws DaoException{
        livre livre = new livre();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_BYID_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            if (res.next()) {
                livre.setId(res.getInt("id"));
                livre.setTitre(res.getString("titre"));
                livre.setAuteur(res.getString("auteur"));
                livre.setIsbn(res.getString("isbn"));
            }
            System.out.println("GET: "+ livre);
        } catch (SQLException e) {
            throw new DaoException("Erreur lors de la recuperation du livre dont le : id="+ id, e);
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
        return livre;
    }

    /** Créer un nouveau livre */
    @Override
    public int create(String titre, String auteur, String isbn) throws DaoException{
        livre livre = new livre();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int id = -1;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, titre);
            preparedStatement.setString(2, auteur);
            preparedStatement.setString(3, isbn);
            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if (res.next()) {
                id = res.getInt(1);
            }
            livre = new livre(id, titre, auteur, isbn);
            System.out.println("CREATE: "+ livre );
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la creation du nouveau livre: "+ livre , e);
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

    /** Modifier un livre */
    @Override
    public void update(livre livre) throws DaoException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getIsbn());
            preparedStatement.setInt(4, livre.getId());
            preparedStatement.executeUpdate();
            System.out.println("UPDATE: "+ livre);
        }catch (SQLException e) {
            throw new DaoException("Erreur lors de la mise a jour du livre" + livre, e);
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

    /** Suppression d'un livre */
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
            throw new DaoException("Probleme lors de la suppression du livre dont le : id=" + id, e);
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
            System.out.println("GET total livres "+ total);
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation du nombre total des livres", e);
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


