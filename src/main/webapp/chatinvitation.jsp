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
		<title>Invitation creation</title>
	</head>
	<body>
		
		<%
		String nomChat = request.getParameter("chosenChat");
		String idInvited[]= request.getParameterValues("invitedUsers");
		
		String login = (String)session.getAttribute("login");
		String password = (String)session.getAttribute("password");

		int currentInvited;
		Invitation currentInvitation;
		

		
		%><h3>Invitations au chat <%=nomChat %> créées:</h3><%
		for(int i = 0; i < idInvited.length; i++){
			currentInvited = Integer.parseInt(idInvited[i]);
			Invitation foundInvit = Invitation.findByChatUser(nomChat, currentInvited);
			User foundUser = User.findByID(currentInvited);

			if (foundInvit != null) {

				%> <p>L'utilisateur <%= foundUser.getPrenom()%>  <%=foundUser.getNom()%> était déjà invité. </p> 
				<% 
				
			} else {
				%> <p>L'utilisateur <%= foundUser.getPrenom()%>  <%=foundUser.getNom()%> a bien été invité. </p> 
				<% 			
				currentInvitation = new Invitation(nomChat, currentInvited);
				currentInvitation.save();
			}
		}
		%>
		<form action="userconnect.jsp">
        	<button type="submit">Retour à l'accueil</button>
      	</form>
	</body>
</html>