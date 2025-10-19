<%-- 
    Document   : evaluarPropuesta
    Created on : 2 Oct 2025, 23:55:38
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
    <title>Consulta Usuarios</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #3498db, #2ecc71);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .container {
            background: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0px 6px 15px rgba(0,0,0,0.2);
            width: 90%;
            max-width: 900px;
            margin-top:60px;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 20px;
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
         .tablas {
        display: flex;
        justify-content: space-between;
        gap: 20px;
        flex-wrap: wrap; /* por si se achica la pantalla */
    }

    .tabla-card {
        flex: 1;
        background: white;
        border-radius: 8px;
        box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        padding: 20px;
        min-width: 300px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 10px;
    }

    th, td {
        border-bottom: 1px solid #ddd;
        padding: 10px;
        text-align: left;
    }

    th {
        background: #3498db;
        color: white;
    }

    tr:hover {
        background-color: #f0f6ff;
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
    <h2>🌍 Lista de Usuarios</h2>

    <div class="tablas">
        <!-- Tabla de proponentes -->
        <div class="tabla-card">
            <h3>🧑‍🎤 Proponentes</h3>
            <table>
                <tr>
                    <th>Nickname</th>
                </tr>
                <c:forEach var="p" items="${proponentes}">
                    <tr>
                <td>            
                    <a href="consultaUsuarioFullServlet?nickname=${p.nickname}">
                        ${p.nickname}
                    </a>
                </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <!-- Tabla de colaboradores -->
        <div class="tabla-card">
            <h3>🤝 Colaboradores</h3>
            <table>
                <tr>
                    <th>Nickname</th>
                </tr>
                <c:forEach var="c" items="${colaboradores}">
                    <tr>
                        <td> 
                            <a href="consultaUsuarioFullServlet?nickname=${c.nickname}">
                                ${c.nickname}
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>
