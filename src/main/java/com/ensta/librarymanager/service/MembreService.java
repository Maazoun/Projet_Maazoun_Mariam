package com.ensta.librarymanager.service;
import java.util.List;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.model.*;


public interface MembreService {

	public List<membre> getList() throws ServiceException;
	public List<membre> getListMembreEmpruntPossible() throws ServiceException;
	public membre getById(int id) throws ServiceException;
	public int create(String nom, String prenom, String adresse, String email, String telephone, abonnement abonnement) throws ServiceException;
	public void update(membre membre) throws ServiceException;
	public void delete(int id) throws ServiceException;
	public int count() throws ServiceException;

}
