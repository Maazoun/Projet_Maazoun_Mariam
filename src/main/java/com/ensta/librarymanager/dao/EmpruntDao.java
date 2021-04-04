package com.ensta.librarymanager.dao;
import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.model.emprunt;
import com.ensta.librarymanager.exception.*;

public interface EmpruntDao {
	public List<emprunt> getList() throws DaoException;
	public List<emprunt> getListCurrent() throws DaoException;
	public List<emprunt> getListCurrentByMembre(int idMembre) throws DaoException;
	public List<emprunt> getListCurrentByLivre(int idLivre) throws DaoException;
	public emprunt getById(int id) throws DaoException;
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt,LocalDate dateRetour) throws DaoException;
	public void update(emprunt emprunt) throws DaoException;
	public int count() throws DaoException;
}
