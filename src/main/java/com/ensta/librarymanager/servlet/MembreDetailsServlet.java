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
import com.ensta.librarymanager.model.*;

public class MembreDetailsServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		if (action.equals("/membre_details")) {
            int id = -1;

            if (request.getParameter("id") != null)
				id = Integer.parseInt(request.getParameter("id"));
			System.out.println(id);

            //  liste des emprunts pour ce membre
            EmpruntService empruntService = EmpruntServiceImpl.getInstance();
            List<emprunt> empruntsList = new ArrayList<emprunt>();

			try {
                if (id > -1) {
                    request.setAttribute("id", id);
                    empruntsList = empruntService.getListCurrentByMembre(id);
                    request.setAttribute("loanList", empruntsList);
                }
			} catch (ServiceException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
            }
        
			// vers le .jsp:
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
			dispatcher.forward(request, response);
		}
	}
    
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService membreService = MembreServiceImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		
		try {
			membreService.update(new membre(Integer.parseInt(request.getParameter("id")), request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("adresse"),
															request.getParameter("email"), request.getParameter("telephone"), abonnement.valueOf(request.getParameter("abonnement"))));
			request.setAttribute("loanList", empruntService.getListCurrentByMembre(Integer.parseInt(request.getParameter("id"))));
			request.setAttribute("id", Integer.parseInt(request.getParameter("id")));
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
			dispatcher.forward(request, response);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

			try {
				request.setAttribute("loanList", empruntService.getListCurrentByMembre(Integer.parseInt(request.getParameter("id"))));
			} catch (ServiceException serviceException) {
				System.out.println(serviceException.getMessage());
				serviceException.printStackTrace();
			}
			
			request.setAttribute("id", Integer.parseInt(request.getParameter("id")));
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}