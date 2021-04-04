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

public class MembreListServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		if (action.equals("/membre_list")) {
			//  liste des membres:
			MembreService membreService = MembreServiceImpl.getInstance();
			List<membre> membresList = new ArrayList<>();
			
			try {                
                membresList = membreService.getList();
			} catch (ServiceException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			request.setAttribute("memberList", membresList);

			// vers le .jsp:
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_list.jsp");
			dispatcher.forward(request, response);
		}
    }
}
