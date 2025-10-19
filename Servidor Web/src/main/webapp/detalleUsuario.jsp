<%-- 
    Document   : detalleUsuario
    Created on : 10 Oct 2025
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
    <meta charset="UTF-8">
    <title>Detalle de Usuario</title>
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
            padding: 30px;
            max-width: 1000px;
            margin: 0 auto;
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
        h3 {
            color: #3498db;
            margin-top: 25px;
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
        .volver {
            display: inline-block;
            background: #3498db;
            color: white;
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 5px;
        }
        .volver:hover {
            background: #2980b9;
        }
        .perfil {
            display: flex;
            align-items: center;
            gap: 20px;
        }
        .perfil img {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid #3498db;
        }
        .info {
            flex: 1;
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

    <!-- 🧍 Información general del usuario -->
    <div class="card">
        <div class="perfil">
            <!-- Imagen de perfil -->
            <c:choose>
                <c:when test="${not empty imagenBase64}">
                    <img src="data:image/jpeg;base64,${imagenBase64}" alt="Foto de perfil">
                </c:when>
                <c:otherwise>
                    <img src="https://cdn-icons-png.flaticon.com/512/149/149071.png" alt="Sin foto">
                </c:otherwise>
            </c:choose>
            <div class="info">
                <h2>${usuario.nickname}</h2>
                <p><strong>Nombre:</strong> ${usuario.nombre} ${usuario.apellido}</p>
                <p><strong>Email:</strong> ${usuario.correo}</p>
                <p><strong>Fecha de nacimiento:</strong> ${usuario.fechaNacimiento}</p>
            </div>
        </div>
    </div>

    <!-- 👥 Seguidores y seguidos -->
    <div class="card">
        <div style="display:flex; gap:20px; flex-wrap:wrap;">
            <div style="flex:1;">
                <h3>👀 Seguidos</h3>
                <table>
                    <tr><th>Nickname</th></tr>
                    <c:forEach var="s" items="${listaSeguidos}">
                        <tr><td>${s.nickname}</td></tr>
                    </c:forEach>
                </table>
            </div>
            <div style="flex:1;">
                <h3>💬 Seguidores</h3>
                <table>
                    <tr><th>Nickname</th></tr>
                    <c:forEach var="f" items="${listaSeguidores}">
                        <tr><td>${f.nickname}</td></tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>

    <!-- 🌟 Propuestas favoritas -->
    <div class="card">
        <h3>⭐ Propuestas favoritas</h3>
        <table>
            <tr><th>Título</th><th>Tipo</th><th>Estado</th></tr>
            <c:forEach var="p" items="${listaPropuestasFav}">
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

    <!-- 📢 Si es proponente -->
    <c:if test="${not empty listaPropuestasPub}">
        <div class="card">
            <h3>🎭 Propuestas publicadas</h3>
            <table>
                <tr><th>Título</th><th>Lugar</th><th>Estado</th></tr>
                <c:forEach var="p" items="${listaPropuestasPub}">
                    <tr>
                        <td>            
                            <a href="consultaPropuestaFullServlet?titulo=${p.titulo}">
                                ${p.titulo}
                            </a>
                        </td>
                        <td>${p.lugar}</td>
                        <td>${p.estadoActual}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>

    <!-- 🤝 Si es colaborador -->
    <c:if test="${not empty colaboraciones}">
        <div class="card">
            <h3>🤝 Colaboraciones realizadas</h3>
            <table>
                <tr><th>Propuesta</th><th>Monto</th><th>Tipo retorno</th><th>Fecha</th></tr>
                        <c:forEach var="c" items="${colaboraciones}">
                    <tr>
                        <td>            
                            <a href="consultaPropuestaFullServlet?titulo=${c.propuestaTitulo}">
                                ${c.propuestaTitulo}
                            </a>
                        </td>
                        <td>${c.montoAportado}</td>
                        <td>${c.tipoRetorno}</td>
                        <td>${c.fechaFormateada}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>
    
    <c:if test="${esPropioPerfil and not empty listaPropuestasIng}">
    <div class="card">
        <h3>📝 Mis propuestas ingresadas</h3>
        <table>
            <tr><th>Título</th><th>Lugar</th><th>Estado</th></tr>
            <c:forEach var="p" items="${listaPropuestasIng}">
                <tr>
                    <td>
                        <a href="consultaPropuestaFullServlet?titulo=${p.titulo}">
                            ${p.titulo}
                        </a>
                    </td>
                    <td>${p.lugar}</td>
                    <td>${p.estadoActual}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>
    
    <c:if test="${esPropioPerfil and empty listaPropuestasIng}">
        <div class="card">
            <h3>📝 Informacion adicional</h3>
            <table>
                <tr><th>Titulo</th><th>Monto</th><th>Fecha</th></tr>
                        <c:forEach var="c" items="${colaboraciones}">
                    <tr>
                        <td>${c.propuestaTitulo}</td>
                        <td>${c.montoAportado}</td>
                        <td>${c.fechaFormateada}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>

    <a href="consultaUsuariosServlet" class="volver">⬅️ Volver a la lista</a>

</div>

</body>
</html>
