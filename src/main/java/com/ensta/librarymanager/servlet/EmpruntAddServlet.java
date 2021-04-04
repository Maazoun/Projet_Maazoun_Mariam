package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.*;
import com.ensta.librarymanager.service.impl.*;
import com.ensta.librarymanager.model.*;

public class EmpruntAddServlet extends HttpServlet {
    
	/** La méthode doGet */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		if (action.equals("/emprunt_add")) {
			
			//  la liste des membres qui peuvent emprunter
			List<membre> availableMembersList = new ArrayList<>();
			availableMembersList = availableMembers() ;
			request.setAttribute("availableMembersList",availableMembersList);

			//  la liste des livres disponibles
			List<livre> availableBooksList = new ArrayList<>();
			availableBooksList = availableBooks();
			request.setAttribute("availableBookList", availableBooksList);
		
			// Envoyer les informations collectées à la jsp 
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");
			dispatcher.forward(request, response);
		}
    }

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		ServletException e = new ServletException("Impossible d'ajouter cet emprunt! Erreur lors de la transmission des informations !");
		List<emprunt> empruntsList = new ArrayList<>();
		try {
			if (request.getParameter("idMembre") == null || request.getParameter("idLivre") == null)
				throw e;
			else {
				empruntService.create(Integer.parseInt(request.getParameter("idMembre")), Integer.parseInt(request.getParameter("idLivre")), LocalDate.now(),null);
				//Donner la liste des emprunts non encore rendus
				empruntsList = empruntService.getListCurrent();
				request.setAttribute("ListeDesEmprunts", empruntsList);
				request.setAttribute("show", "all");
				// Retourner les infos à la jsp 
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
				dispatcher.forward(request, response);
			}
		} catch (ServiceException e1) {
			System.out.println(e.getMessage());
			e1.printStackTrace();
		} catch (ServletException e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
			// Retourner la liste des membres qui peuvent emprunter 
			List<membre> availableMembersList = new ArrayList<>();
			availableMembersList = availableMembers();
			request.setAttribute("availableMembersList", availableMembersList);

			// Retourner la liste des livres disponibles 
			List<livre> availableBooksList = new ArrayList<>();
			availableBooksList = availableBooks();
			request.setAttribute("availableBookList", availableBooksList);
			
			request.setAttribute("errorMessage", e1.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	
	/**  la liste des membres prets à l'emprunt */
	private List<membre> availableMembers(){
		MembreService memberService = MembreServiceImpl.getInstance();
		List<membre> availableMembersList = new ArrayList<>();
		try {
			availableMembersList = memberService.getListMembreEmpruntPossible();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return availableMembersList;
	}
	
	/** la liste des livres prets à l'emprunt */
	private List<livre> availableBooks(){
		LivreService livreService = LivreServiceImpl.getInstance();
		List<livre> availableBooksList = new ArrayList<>();
		try {
			availableBooksList = livreService.getListDispo();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return availableBooksList;
	}
	
}