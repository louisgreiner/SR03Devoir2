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
		<title>Chat invitation</title>
	</head>
	<body>

		<form action="chatinvitation.jsp" method="post">
			<label>Chat :</label>
			<select name="chosenChat" size="4" required>
			<% 
			ArrayList<Chat> chats = Chat.findAll();
			
			Chat currentChat;
	        for (int i = 0; i < chats.size(); i++) {   	        
	 	    	currentChat = chats.get(i);
 	    		%><option value="<%= currentChat.getNom() %>"><%=currentChat.getNom()%></option><%
		    }
			%>
			</select><br>
			
			<label>Users to invite: </label>
			<select name="invitedUsers" size="4" multiple required>
			<% 
			ArrayList<User> users = User.findAll();
			
			User currentUser;
	        for (int i = 0; i < users.size(); i++) {   	        
	 	    	currentUser = users.get(i);
	 	    	if(!(currentUser.getEmail().equals(session.getAttribute("login")))){
	 	    		%><option value="<%= currentUser.getID() %>"><%=currentUser.getPrenom() + " " + currentUser.getNom()%></option><%
	 	    	}
		    }
			%>
			</select><br>
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