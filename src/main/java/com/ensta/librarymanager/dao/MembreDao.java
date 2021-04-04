package com.ensta.librarymanager.dao;
import java.util.List;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.model.membre;
import com.ensta.librarymanager.model.abonnement;

public interface MembreDao {
	public List<membre> getList() throws DaoException;
	public membre getById(int id) throws DaoException;
	public int create(String nom, String prenom, String adresse, String email, String telephone,abonnement abonnement) throws DaoException;
	public void update(membre membre) throws DaoException;
	public void delete(int id) throws DaoException;
	public int count() throws DaoException;
}
