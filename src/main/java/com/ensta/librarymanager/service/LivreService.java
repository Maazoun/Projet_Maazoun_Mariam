package com.ensta.librarymanager.service;
import java.util.List;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.livre;

public interface LivreService {

	public List<livre> getList() throws ServiceException;
	public List<livre> getListDispo() throws ServiceException;
	public livre getById(int id) throws ServiceException;
	public int create(String titre, String auteur, String isbn) throws ServiceException;
	public void update(livre livre) throws ServiceException;
	public void delete(int id) throws ServiceException;
	public int count() throws ServiceException;
}
