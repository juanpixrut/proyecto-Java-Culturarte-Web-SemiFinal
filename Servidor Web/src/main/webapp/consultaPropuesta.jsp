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
    <title>Consulta Propuestas</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #3498db, #2ecc71);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items:center;
            <!--padding: 40px;-->
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

    <div style="width:100%; display:flex; justify-content:center; margin:30px 0;">
        <form action="consultaPropuestaServlet" method="GET"
              style="display:flex; flex-wrap:wrap; align-items:center; gap:10px; width:90%; max-width:900px; background:white; border-radius:8px; padding:12px 16px; box-shadow:0 3px 8px rgba(0,0,0,0.1);">

            <!-- 🔎 Buscador -->
            <input type="text" name="filtros"
                   value="${filtros}"
                   placeholder="Buscar por título, descripción o lugar (separá por comas)"
                   style="flex:1; min-width:250px; padding:12px 15px; border:none; outline:none; font-size:15px; border-radius:8px; background:transparent;">
            
            <!-- 🧩 Filtro por tipo -->
            <select name="tipo" 
                    style="padding:10px; border-radius:6px; border:1px solid #ccc; font-size:14px;">
                <option value="">📂 Filtrar por tipo</option>

                <c:forEach var="cat" items="${categorias}">
                    <option value="${cat}">${cat}</option>
                </c:forEach>
            </select>
            
            <!-- 🧩 Filtro por estado -->
            <select name="estado" style="padding:10px; border-radius:6px; border:1px solid #ccc; font-size:14px;">
                <option value="">Filtrar por estado</option>
                <option value="PUBLICADA" ${estado == 'PUBLICADA' ? 'selected' : ''}>Publicada</option>
                <option value="EN_FINANCIACION" ${estado == 'EN_FINANCIACION' ? 'selected' : ''}>En financiación</option>
                <option value="FINALIZADA" ${estado == 'FINALIZADA' ? 'selected' : ''}>Finalizada</option>
                <option value="CANCELADA" ${estado == 'CANCELADA' ? 'selected' : ''}>Cancelada</option>
            </select>

            <!-- 🔠 Orden -->
            <select name="orden" style="padding:10px; border-radius:6px; border:1px solid #ccc; font-size:14px;">
                <option value="">Ordenar por...</option>
                <option value="ALFABETICO" ${orden == 'ALFABETICO' ? 'selected' : ''}>Título (A-Z)</option>
                <option value="ANIO_DESC" ${orden == 'ANIO_DESC' ? 'selected' : ''}>Año (más recientes primero)</option>
            </select>

            <!-- Botón -->
            <button type="submit"
                    style="background:#3498db; color:white; border:none; padding:10px 20px; border-radius:6px; font-weight:bold; cursor:pointer; transition:background 0.3s;">
                🔍 Buscar
            </button>
        </form>
    </div>


<div class="container">
    <h2>🌍 Lista de Propuestas</h2>
    <table>
        <tr>
            <th>Título</th>
            <th>Tipo</th>
            <th>Estado</th>
        </tr>
        <c:forEach var="p" items="${propuestas}">
            <tr>
                <td>            
                    <a href="consultaPropuestaFullServlet?titulo=${p.titulo}">
                        ${p.titulo}
                    </a>
                </td>
                <td>${p.tipoEspectaculo}</td>
                <td>${p.estadoActual}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
