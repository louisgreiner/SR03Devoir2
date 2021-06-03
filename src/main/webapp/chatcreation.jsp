<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="projet2.User" %>
<%@ page import="projet2.Chat" %>
<%@ page import="projet2.Invitation" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
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
		
		<%
		String nomChat = request.getParameter("nomChat");
		String idInvited[] = request.getParameterValues("invitedUsers");
		String description = request.getParameter("description");
		String date_fin = request.getParameter("date");
		String duree_validite = request.getParameter("duration");
		
		String login = (String)session.getAttribute("login");
		String password = (String)session.getAttribute("password");
		User proprietaire = User.findByLoginPwd(login, password);
		
		Chat foundChat = Chat.findByName(nomChat);
		
		if(foundChat != null){
			%>
			<h3>Le nom de chat <%= nomChat %> est déjà pris. Quel malheur !</h3><br>
			<%
		}
		else{
			%><h3>Le chat a bien été créé. Bravo!</h3><br><%

			Chat newChat = new Chat(nomChat, proprietaire.getID(),description,LocalDate.parse(date_fin),LocalTime.parse(duree_validite));
			newChat.save();
			User currentUser;
			int currentInvited;
			Invitation currentInvitation;
			
			if(idInvited != null){
				for(int i = 0; i < idInvited.length; i++){
					currentInvited = Integer.parseInt(idInvited[i]);
					currentInvitation = new Invitation(nomChat, currentInvited);
					currentInvitation.save();
				}
			}
		}
		%>
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
			<label>Durée de validité</label>
			<input type="time" name="duration" required ><br>
			<input type="submit" value="Submit">
		</form>
		<form action="userconnect.jsp">
        	<button type="submit">Retour à l'accueil</button>
      	</form>
	</body>
</html>