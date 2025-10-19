<%-- 
    Document   : verPerfil
    Created on : 7 Oct 2025, 16:26:19
    Author     : Juanpi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    HttpSession sesion = request.getSession(false);
    String rol = null;
    String usuario = null;

    if (sesion != null) {
        rol = (String) sesion.getAttribute("rol");
        usuario = (String) sesion.getAttribute("usuarioLogueado");
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Perfil de ${usuario.nombre}</title>
            <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #3498db, #2ecc71);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding: 40px;
        }

        .container {
            background: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0px 6px 15px rgba(0,0,0,0.2);
            width: 90%;
            max-width: 600px;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 20px;
        }

        p {
            font-size: 15px;
            color: #2c3e50;
            line-height: 1.6;
        }

        a {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            background: #3498db;
            color: #fff;
            padding: 10px 16px;
            border-radius: 8px;
        }

        a:hover {
            background: #2c80b4;
        }
    </style>
    </head>
    <body>
       <header>
            <h1>🌐 Culturarte</h1>
            <nav>
                <a href="index.jsp">🏠 Inicio</a>

                <% if (usuario != null && !"visitante".equalsIgnoreCase(rol)) {%>
                <span style="margin-right:10px;">👤 <%= usuario%></span>
                <a href="logout">🚪 Cerrar Sesión</a>
                <% } else { %>
                <a href="altaPerfil.jsp">🆕 Crear Cuenta</a>
                <a href="inicioSesion.jsp">🔑 Iniciar Sesión</a>
                <% } %>
            </nav>
        </header> 
   <div class="container">
    <h2>Perfil de ${usuario.nombre} ${usuario.apellido}</h2>

    <p><strong>Nickname:</strong> ${usuario.nickname}</p>
    <p><strong>Email:</strong> ${usuario.email}</p>

    <c:if test="${tipo eq 'proponente'}">
        <p><strong>Tipo de perfil:</strong> Proponente</p>
    </c:if>

    <c:if test="${tipo eq 'colaborador'}">
        <p><strong>Tipo de perfil:</strong> Colaborador</p>
    </c:if>

    <a href="listarUsuariosServlet">Volver</a>
</div>
    </body>
</html>
