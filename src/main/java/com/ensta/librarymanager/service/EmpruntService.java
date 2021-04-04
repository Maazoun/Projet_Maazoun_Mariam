package com.ensta.librarymanager.service;
import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.emprunt;
import com.ensta.librarymanager.model.membre;

public interface EmpruntService {
	public List<emprunt> getList() throws ServiceException;
	public List<emprunt> getListCurrent() throws ServiceException;
	public List<emprunt> getListCurrentByMembre(int idMembre) throws ServiceException;
	public List<emprunt> getListCurrentByLivre(int idLivre) throws ServiceException;
	public emprunt getById(int id) throws ServiceException;
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt,LocalDate dateRetour) throws ServiceException;
	public void returnBook(int id) throws ServiceException;
	public int count() throws ServiceException;
	public boolean isLivreDispo(int idLivre) throws ServiceException;
	public boolean isEmpruntPossible(membre membre) throws ServiceException ;
}
