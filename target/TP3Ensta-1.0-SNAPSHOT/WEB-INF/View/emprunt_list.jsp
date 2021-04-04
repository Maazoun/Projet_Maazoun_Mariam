
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page import="com.ensta.librarymanager.modele.Emprunt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<%! private List<emprunt> loanList = new ArrayList<>();%>
<% loanList = (List) request.getAttribute("ListeDesEmprunts"); %>
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
        <h1 class="page-announce-text valign">Liste des emprunts</h1>
      </div>
      <div class="row">
        <div class="container">
	        <div class="col s12">
            <a href='emprunt_list?show=<%= (String) request.getAttribute("show") %>'>Show <%= (String) request.getAttribute("show") %> loans</a>
	          <table class="striped">
                <thead>
                    <tr>
                        <th>livre</th>
                        <th>Membre emprunteur</th>
                        <th>Date d'emprunt</th>
                        <th>Retour</th>
                    </tr>
                </thead>
                <tbody id="results">
                  <% if (!loanList.isEmpty()) {
                    for (emprunt loan : loanList) { %>
                      <tr>
                        <td><%= loan.getLivre().getTitre() %>, <em><%= loan.getLivre().getAuteur() %></em></td>
                        <td><%= loan.getMembre().getNom() %> <%= loan.getMembre().getPrenom() %></td>
                        <td><%= loan.getDateEmprunt() %></td>
                        <td>
                          <% if (loan.getDateRetour() == null) { %>
                            <a href='emprunt_return?id=<%= loan.getId() %>'><ion-icon class="table-item" name="log-in"></a>
                          <% } else { %>
                            <%= loan.getDateRetour() %>
                          <% } %>
                        </td>
                      </tr>
                    <% }
                  } %>
                </tbody>
            </table>
          </div>
        </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>
</html>