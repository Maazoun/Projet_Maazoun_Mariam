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

public class EmpruntListServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	String action = request.getServletPath();
		
		if (action.equals("/emprunt_list")) {
			
            // Fixer la valeur par défaut de show 
            String show = "current";
            // La changer si l'on reçoit une autre 
            if (request.getParameter("show") != null && request.getParameter("show").contains("all"))
                show = "all";

			// Retourner la liste des emprunts ( tous ou currents)
			EmpruntService empruntService = EmpruntServiceImpl.getInstance();
			List<emprunt> empruntsList = new ArrayList<>();
			
			try {
                // Faire un choix selon la valeur de "show" 
                if (show == "current") {
                    empruntsList = empruntService.getListCurrent();
                    request.setAttribute("show", "all");
                } else {
                    empruntsList = empruntService.getList();
                    request.setAttribute("show", "current");
                }
			} catch (ServiceException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			request.setAttribute("ListeDesEmprunts", empruntsList);

			// Envoyer les informations collectées vers la jsp 
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
			dispatcher.forward(request, response);
		}
    }
}
