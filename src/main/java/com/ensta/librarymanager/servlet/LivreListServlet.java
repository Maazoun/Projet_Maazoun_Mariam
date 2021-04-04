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

public class LivreListServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		if (action.equals("/livre_list")) {
			// list livres:
			LivreService livreService = LivreServiceImpl.getInstance();
			List<livre> livresList = new ArrayList<>();
			
			try {                
                livresList = livreService.getList();
			} catch (ServiceException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			request.setAttribute("bookList", livresList);

			// vers le.jsp:
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_list.jsp");
			dispatcher.forward(request, response);
		}
    }
}
