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
import com.ensta.librarymanager.model.emprunt;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.impl.EmpruntServiceImpl;
import com.ensta.librarymanager.service.impl.LivreServiceImpl;
import com.ensta.librarymanager.service.impl.MembreServiceImpl;

public class DashboardServlet extends HttpServlet {
	/** Récuperer le nombre des membres  */
	private int showMembersNumber() throws ServletException, IOException{
		MembreService membreService = MembreServiceImpl.getInstance();
		int i = 0;
		try {
			i = membreService.count();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
		return i;
	}

	/** Récuperer le nombre des livres  */
	private int showBooksNumber() throws ServletException, IOException{
		LivreService livreService = LivreServiceImpl.getInstance();
		int i = 0;
		try {
			i = livreService.count();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
		return i;
	}

	/** Récuperer le nombre des emprunts */
	private int showEmpruntsNumber() throws ServletException, IOException{
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		int i = 0;
		try {
			i = empruntService.count();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
		return i;
	}

	/** Récupérer la liste des emprunts non encore rendus */
	private List<emprunt> showCurrentEmprunts() throws ServletException, IOException{
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		List<emprunt> emprunts = new ArrayList<>();
		try {
			emprunts = empruntService.getListCurrent();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
		return emprunts;
	}
	
	/** La méthode doGet */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		if (action.equals("/dashboard"))
		{

			int membersNumber = -1;
			membersNumber = showMembersNumber();
			request.setAttribute("NombreDesMembres", membersNumber);
			
			int booksNumber = -1;
			booksNumber = showBooksNumber();
			request.setAttribute("NombreDesLivres", booksNumber);
			
			int empruntsNumber = -1;
			empruntsNumber = showEmpruntsNumber();
			request.setAttribute("NombreDesEmprunts", empruntsNumber);

			List<emprunt> emprunts = new ArrayList<>();
			emprunts = showCurrentEmprunts();
			request.setAttribute("EmpruntsNonRendus", emprunts);
			
			// Envoyer les informations collectées à dashboard.jsp
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
			dispatcher.forward(request, response);
		}
	}

}
