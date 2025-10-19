<%-- 
    Document   : otrosPerfiles
    Created on : 7 Oct 2025, 15:30:46
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
        <title>Otros Perfiles</title>
        <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #3498db, #2ecc71);
            min-height: 100vh;
    display: flex;
    flex-direction: column; /* 🔹 Header arriba, contenido debajo */
    align-items: center;
        }

        .container {
            background: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0px 6px 15px rgba(0,0,0,0.2);
            width: 90%;
            max-width: 900px;
            margin-top:60px
        }

        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 20px;
        }

        .section {
            margin-bottom: 40px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background: #3498db;
            color: #fff;
            font-weight: bold;
        }

        tr:nth-child(even) {
            background: #f2f2f2;
        }

        tr:hover {
            background: #d6f5e3;
        }

        td {
            color: #2c3e50;
        }

        .subtitle {
            color: #27ae60;
            font-size: 18px;
            margin-bottom: 10px;
        }
        .verPerfil{
            color: #27ae60;
            font-size: 18px;
            margin-bottom: 10px;
        }
        header {
    background:#2c3e50;
    color:white;
    padding:15px 25px;
    display:flex;
    justify-content:space-between;
    align-items:center;
    width:100%; /* 🔹 ocupa todo el ancho */
    box-sizing: border-box;
}

header h1 {
    margin:0;
    font-size:20px;
}

nav a {
    color:white;
    margin:0 10px;
    text-decoration:none;
}

nav a:hover {
    text-decoration:underline;
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
                <% }%>
            </nav>
        </header> 
        <div class="container">
    <h2>👥 Otros Perfiles</h2>

    <div class="section">
        <h3 class="subtitle">🌟 Proponentes</h3>
        <table>
            <tr>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Nickname</th>
                <th>Email</th>
            </tr>
            <c:forEach var="p" items="${proponentes}">
                <tr>
                    <td>${p.nombre}</td>
                    <td>${p.apellido}</td>
                    <td>${p.nickname}</td>
                    <td>${p.email}</td>
                    <td><a class="verPerfil" href="verPerfilServlet?nickname=${p.nickname}">Ver perfil</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="section">
        <h3 class="subtitle">💡 Colaboradores</h3>
        <table>
            <tr>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Nickname</th>
                <th>Email</th>
            </tr>
            <c:forEach var="c" items="${colaboradores}">
                <tr>
                    <td>${c.nombre}</td>
                    <td>${c.apellido}</td>
                    <td>${c.nickname}</td>
                    <td>${c.email}</td>
                    <td><a class="verPerfil" href="verPerfilServlet?nickname=${p.nickname}">Ver perfil</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
    </body>
</html>
