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
import com.ensta.librarymanager.model.*;

public class MembreDeleteServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		if (action.equals("/membre_delete")) {
            // id par defaut
            int id = -1;
            if (request.getParameter("id") != null)
                id = Integer.parseInt(request.getParameter("id"));
				
			// list of all members:
			MembreService membreService = MembreServiceImpl.getInstance();
			List<membre> membresList = new ArrayList<>();
			
			try {
                membresList = membreService.getList();
                if (id > -1)
                    request.setAttribute("id", id);
			} catch (ServiceException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			request.setAttribute("memberList", membresList);

			// vers le  .jsp:
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");
			dispatcher.forward(request, response);
		}
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService membreService = MembreServiceImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		List<membre> membresList = new ArrayList<>();

        try {
            if (request.getParameter("id") == "")
				throw new ServletException("Erreur! veuillez choisir un membre ");
			// verification
			else if (!empruntService.getListCurrentByMembre(Integer.parseInt(request.getParameter("id"))).isEmpty()) {
				throw new ServletException("Erreur ! vous ne pouvez pas choisir ce membre qui n'a pas retourné le livre.");
			} else {
				membreService.delete(Integer.parseInt(request.getParameter("id")));
			
				// liste des members :
				membresList = membreService.getList();
                
				request.setAttribute("memberList", membresList);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");
				dispatcher.forward(request, response);
			}
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ServletException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

			//  list of the current members:
			try {
				membresList = membreService.getList();
			} catch (ServiceException serviceException) {
				System.out.println(serviceException.getMessage());
				serviceException.printStackTrace();
			}
                
			request.setAttribute("memberList", membresList);
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");
			dispatcher.forward(request, response);
		}
	}
}