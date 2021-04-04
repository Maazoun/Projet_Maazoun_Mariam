<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ensta.librarymanager.model.membre" %>
<%@ page import="com.excilys.librarymanager.service.*" %>
<%@ page import="com.excilys.librarymanager.service.impl.*" %>
<%@ page import="com.excilys.librarymanager.modele.Emprunt" %>
<%@ page import="java.util.List" %>

<%! private membre membre = new membre();%>
<%! private MembreService membreService = MembreServiceImpl.getInstance();%>
<% membre = membreService.getById((int) request.getAttribute("id"));%>
<%! private List<emprunt> empruntsList = new ArrayList<emprunt>();%>
<% empruntsList = (List) request.getAttribute("loanList");%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Library Management</title>
  <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="assets/css/custom.css" rel="stylesheet" type="text/css" />
</head>

<body>
  <jsp:include page='menu.jsp'></jsp:include>
  <main>
    <section class="content">
      <div class="page-announce valign-wrapper">
        <a href="#" data-activates="slide-out" class="button-collapse valign hide-on-large-only"><i class="material-icons">menu</i></a>
        <h1 class="page-announce-text valign">Fiche membre</h1>
      </div>

      <% if (request.getAttribute("errorMessage") != null) { %>
        <div>
          <p align="center"><%= (String) request.getAttribute("errorMessage") %></p>
        </div>
      <% } %>

      <div class="row">
      <div class="container">
      <h5>Détails du membre n:<%= membre.getId() %></h5>
        <div class="row">
	      <form action='membre_details?id=<%= membre.getId() %>' method="post" class="col s12">
	        <div class="row">
	          <div class="input-field col s4">
	            <input id="nom" type="text" value='<%= membre.getNom() %>' name="nom">
	            <label for="nom">Nom</label>
	          </div>
	          <div class="input-field col s4">
	            <input id="prenom" type="text" value='<%= membre.getPrenom() %>' name="prenom">
	            <label for="prenom">Prénom</label>
	          </div>
	          <div class="input-field col s4">
	            <select name="abonnement" class="browser-default">
	              <option value="BASIC" <%= (membre.getAbonnement().toString() == "BASIC") ? "selected" : "" %>>abonnement BASIC</option>
	              <option value="PREMIUM" <%= (membre.getAbonnement().toString() == "PREMIUM") ? "selected" : "" %>>abonnement PREMIUM</option>
	              <option value="VIP" <%= (membre.getAbonnement().toString() == "VIP") ? "selected" : "" %>>abonnement VIP</option>
	            </select>
	          </div>
	        </div>
	        <div class="row">
	          <div class="input-field col s12">
	            <input id="adresse" type="text" value='<%= membre.getAdresse() %>' name="adresse">
	            <label for="adresse">Adresse</label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="input-field col s6">
	            <input id="email" type="email" value='<%= membre.getEmail() %>' name="email">
	            <label for="email">E-mail</label>
	          </div>
	          <div class="input-field col s6">
	            <input id="telephone" type="tel" value='<%= membre.getTelephone() %>' name="telephone">
	            <label for="telephone">Téléphone</label>
	          </div>
	        </div>
	        <div class="row center">
	          <button class="btn waves-effect waves-light" type="submit">Enregistrer</button>
	          <button class="btn waves-effect waves-light orange" type="reset">Annuler</button>
	        </div>
	      </form>
	      
	      <form action="membre_delete" method="get" class="col s12">
	        <input type="hidden" value='<%= membre.getId() %>' name="id">
	        <div class="row center">
	          <button class="btn waves-effect waves-light red" type="submit">Supprimer le membre
	            <i class="material-icons right">delete</i>
	          </button>
	        </div>
	      </form>
	    </div>
        <div class="row">
          <div class="col s12">
	        <table class="striped">
              <thead>
                <tr>
                  <th>livre</th>
                  <th>Date d'emprunt</th>
                  <th>Retour</th>
                </tr>
              </thead>
              <tbody id="results">
                <% if (!empruntsList.isEmpty()) {
                  for (emprunt emprunt : empruntsList) { %>
                    <tr>
                      <td><%= emprunt.getLivre().getTitre() %>, de <%= emprunt.getLivre().getAuteur() %></td>
                      <td><%= emprunt.getDateEmprunt() %></td>
                      <td>
                        <a href='emprunt_return?id=<%= emprunt.getId() %>'><ion-icon class="table-item" name="log-in"></a>
                      </td>
                    </tr>
                  <% }
                } %>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>
</html>