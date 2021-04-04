package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.*;
import com.ensta.librarymanager.service.impl.*;
import com.ensta.librarymanager.model.*;

public class LivreAddServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		if (action.equals("/livre_add")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livreService = LivreServiceImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();	
		
		try {
			int livreId = livreService.create(request.getParameter("titre"), request.getParameter("auteur"), request.getParameter("isbn"));

			request.setAttribute("id", livreId);
			request.setAttribute("loanList", empruntService.getListCurrentByLivre(livreId));
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
			dispatcher.forward(request, response);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
