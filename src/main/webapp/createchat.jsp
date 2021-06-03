<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="projet2.User" %>
<%@ page import="projet2.Chat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalTime" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>Chat creation</title>
	</head>
	<body>

		<form action="chatcreation.jsp" method="post">
			<label>Nom du chat</label>
			<input type="text" name="nomChat" required><br>
			<label>Description du chat</label>
			<input type="text" name="description" required><br>
			<label>Users to invite: </label>
			<select id="invitedUsers" name="invitedUsers" size="4" multiple>
			<% 
			ArrayList<User> users = User.findAll();
			
			User currentUser;
	        for (int i = 0; i < users.size(); i++) {   	        
	 	    	currentUser = users.get(i);
	 	    	if(!(currentUser.getEmail().equals(session.getAttribute("login")))){
	 	    		%><option value="<%= currentUser.getID() %>"><%=currentUser.getPrenom() + " " + currentUser.getNom()%></option>
	 	    		<%
	 	    	}
		    }
			%>
			</select><br>
			<label>Date de fin de validité</label>
			<input type="date" name="date" min="<%= LocalDate.now() %>" required><br>
			<label>Horaire de fin de validité</label>
			<input type="time" name="duration" required ><br>
			<%		
			String login = (String)session.getAttribute("login");
			String password = (String)session.getAttribute("password");
			%>
			<input type="submit" value="Submit">	
		</form>
		<form action="userconnect.jsp">
        	<button type="submit">Retour à l'accueil</button>
      	</form>
	</body>
</html>