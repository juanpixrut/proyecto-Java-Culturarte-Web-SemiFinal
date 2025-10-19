<%-- 
    Document   : detallePropuesta
    Created on : 10 Oct 2025, 01:45:11
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
        <title>Detalle de Propuesta</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                background: #f5f7fa;
                color: #2c3e50;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
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
                margin: 0;
                font-size: 20px;
            }

            nav a {
                color: white;
                margin: 0 10px;
                text-decoration: none;
            }

            nav a:hover {
                text-decoration: underline;
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

            .card {
                background: white;
                border-radius: 8px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.1);
                padding: 25px;
                margin-bottom: 30px;
            }

            h2 {
                color: #2c3e50;
                margin-bottom: 15px;
            }

            p {
                margin: 8px 0;
                line-height: 1.5;
            }

            a {
                color: #3498db;
                text-decoration: none;
                font-weight: bold;
            }

            a:hover {
                text-decoration: underline;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
            }

            th, td {
                border-bottom: 1px solid #ddd;
                padding: 12px;
                text-align: left;
            }

            th {
                background-color: #3498db;
                color: white;
            }

            tr:hover {
                background-color: #f2f6fa;
            }

            .volver {
                display: inline-block;
                margin-top: 15px;
                background: #3498db;
                color: white;
                padding: 10px 20px;
                border-radius: 5px;
                text-decoration: none;
            }

            .volver:hover {
                background: #2980b9;
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
            <div class="card">
                <h2>${propuesta.titulo}</h2>
                <p><strong>Descripción:</strong> ${propuesta.descripcion}</p>
                <p><strong>Tipo de espectáculo:</strong> ${propuesta.tipoEspectaculo}</p>
                <p><strong>Lugar:</strong> ${propuesta.lugar}</p>
                <p><strong>Fecha:</strong> ${propuesta.fechaRealizacionFormateada}</p>
                <p><strong>Precio entrada:</strong> ${propuesta.precioEntrada}</p>
                <p><strong>Monto necesario:</strong> ${propuesta.montoNecesario}</p>
                <p><strong>Monto recaudado</strong> ${propuesta.montoRecaudado}</p>
                <p><strong>Tipo retorno:</strong> ${propuesta.tipoRetorno}</p>
                <p><strong>Estado:</strong> ${propuesta.estadoActual}</p>
                <a class="volver" href="consultaPropuestaServlet">⬅️ Volver a la lista</a>
            </div>
            <div class="card">

                <h2>🌍 Lista de colaboradores</h2>
                <table>
                    <tr>
                        <th>Nickname</th>
                    </tr>
                    <c:forEach var="c" items="${colaboraciones}">
                        <tr>
                            <td>${c.colaboradorNickname}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </body>
</html>