package com.ensta.librarymanager.servlet;

import java.io.IOException;

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
import com.ensta.librarymanager.model.emprunt;

public class EmpruntReturnServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		if (action.equals("/emprunt_return")) {
            int id = -1;
            if (request.getParameter("id") != null)
                id = Integer.parseInt(request.getParameter("id"));
				
			// liste d'emprunts
			EmpruntService empruntService = EmpruntServiceImpl.getInstance();
			List<emprunt> empruntsList = new ArrayList<>();
			
			try {
                empruntsList = empruntService.getListCurrent();
                if (id > -1)
                    request.setAttribute("id", id);
			} catch (ServiceException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			request.setAttribute("loanList", empruntsList);

			// Envoyer les informations collectées vers la jsp
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
			dispatcher.forward(request, response);
		}
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        ServletException se = new ServletException("Erreur! aucun emprunt n'a été effectué !");
		List<emprunt> empruntsList = new ArrayList<>();
		
        try {
            if (request.getParameter("id") == null)
                throw se;
            else {
				empruntService.returnBook(Integer.parseInt(request.getParameter("id")));
			
				// liste des emprunts
				empruntsList = empruntService.getListCurrent();
				
				request.setAttribute("loanList", empruntsList);
				request.setAttribute("show", "all");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
				dispatcher.forward(request, response);
			}
		} catch (ServletException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

			// retourne la liste des emprunts
			try {
				empruntsList = empruntService.getListCurrent();
			} catch (ServiceException serviceException) {
				System.out.println(serviceException.getMessage());
				serviceException.printStackTrace();
			}

			request.setAttribute("loanList", empruntsList);
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
			dispatcher.forward(request, response);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
