<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="projet2.User" %>
<%@ page import="projet2.Chat" %>
<%@ page import="projet2.Invitation" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Deletion of chat invitation</title>
	</head>
	<body>
		
		<%
		String nomChat = request.getParameter("chosenChat");
		String idDeleted[]= request.getParameterValues("deletedUsers");
		
		String login = (String)session.getAttribute("login");
		String password = (String)session.getAttribute("password");

		int currentDeleted;
		Invitation currentInvitation;
		
		%><h4>Invitations au chat <%=nomChat %> supprimées:</h4><%
		for(int i = 0; i < idDeleted.length; i++){
			currentDeleted = Integer.parseInt(idDeleted[i]);
			Invitation foundInvit = Invitation.findByChatUser(nomChat, currentDeleted);
			User foundUser = User.findByID(currentDeleted);

			if (foundInvit != null) {
				currentInvitation = new Invitation(nomChat, currentDeleted);
				currentInvitation.delete();

				%> <p>L'utilisateur <%= foundUser.getPrenom()%>  <%=foundUser.getNom()%> a bien été supprimé. </p> 
				<% 
				
			} else {
				%> <p>L'utilisateur <%= foundUser.getPrenom()%>  <%=foundUser.getNom()%> n'était pas invité. </p> 
				<% 
			}
		}
		%>
		<form action="userconnect.jsp">
        	<button type="submit">Retour à l'accueil</button>
      	</form>
	</body>
</html>