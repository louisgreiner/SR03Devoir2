<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="projet2.User" %>
<%@ page import="projet2.Chat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>Users and chats</title>
	</head>
	<body>
		<label>Users :</label>
		<% 
		ArrayList<User> users = User.findAll();
		User currentUser;
		%><table><tr><td>ID</td><td>Prénom</td><td>Nom</td></tr><%
        for (int i = 0; i < users.size(); i++) {   	        
 	    	currentUser = users.get(i);
	    	%><tr><td><%= currentUser.getID() %></td><td><%=currentUser.getPrenom() %></td><td><%= currentUser.getNom()%></td></tr><%
	    }
		%>
		</table><br>
		<label>Chats :</label>
		<% 
		ArrayList<Chat> chats = Chat.findAll();
		Chat currentChat;
		%><table><tr><td>Nom / ID</td><td>Description</td><td>Propriétaire</td><td>Date fin de validité</td></tr><%
        for (int i = 0; i < chats.size(); i++) {   	        
        	currentChat = chats.get(i);
	    	%><tr><td><%= currentChat.getNom() %></td><td><%= currentChat.getDescription() %></td><td><%=currentChat.getProprietaire() %></td><td><%= currentChat.getDate()%> <%= currentChat.getDuree()%></td></tr><%
	    }
		%>
		</table><br>
		<form action="userconnect.jsp">
        	<button type="submit">Retour à l'accueil</button>
      	</form>
	</body>
</html>