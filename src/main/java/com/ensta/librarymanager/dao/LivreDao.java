package com.ensta.librarymanager.dao;
import java.util.List;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.model.livre;

public interface LivreDao {
	public List<livre> getList() throws DaoException;
	public livre getById(int id) throws DaoException;
	public int create(String titre, String auteur, String isbn) throws DaoException;
	public void update(livre livre) throws DaoException;
	public void delete(int id) throws DaoException;
	public int count() throws DaoException;
}
