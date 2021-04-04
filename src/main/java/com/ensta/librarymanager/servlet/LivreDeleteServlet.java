package com.ensta.librarymanager.servlet;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.service.*;
import com.ensta.librarymanager.service.impl.*;
import com.ensta.librarymanager.model.livre;

public class LivreDeleteServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		if (action.equals("/livre_delete")) {
            int id = -1; // valeur par défaut

            if (request.getParameter("id") != null)
                id = Integer.parseInt(request.getParameter("id"));
				
			/** liste de tous les livres */
			LivreService livreService = LivreServiceImpl.getInstance();
			List<livre> livresList = new ArrayList<>();
			
			try {
                livresList = livreService.getList();
                if (id > -1)
                    request.setAttribute("id", id);
			} catch (ServiceException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			request.setAttribute("bookList", livresList);

			// Envoyer les informations collectées vers la .jsp
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
			dispatcher.forward(request, response);
		}
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livreService = LivreServiceImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();	
		List<livre> livresList = new ArrayList<>();
		
        try {
            if (request.getParameter("id") == "")
				throw new ServletException("Erreur ! veuillez choisir un livre ! ");
			// verifier si on peut faire l'emprunt ou pas
			else if (!empruntService.getListCurrentByLivre(Integer.parseInt(request.getParameter("id"))).isEmpty()) {
				throw new ServletException("Erreur ! Suppression non faisable");
            } else {
				livreService.delete(Integer.parseInt(request.getParameter("id")));
			
				// liste des livres
				livresList = livreService.getList();
                
				request.setAttribute("bookList", livresList);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
				dispatcher.forward(request, response);
			}
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ServletException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

			try {
				livresList = livreService.getList();
			} catch (ServiceException serviceException) {
				System.out.println(serviceException.getMessage());
				serviceException.printStackTrace();
			}
                
			request.setAttribute("bookList", livresList);
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
			dispatcher.forward(request, response);
		}
	}
}
