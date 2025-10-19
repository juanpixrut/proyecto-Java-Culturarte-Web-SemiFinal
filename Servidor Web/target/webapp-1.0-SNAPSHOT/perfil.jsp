<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="logica.usuario" %>

<%
    HttpSession sesion = request.getSession(false);
    if (sesion == null || sesion.getAttribute("usuarioLogueado") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String usuario = (String) sesion.getAttribute("usuarioLogueado");
    String rol = (String) sesion.getAttribute("rol");
    String email = null;
    usuario user = (usuario) sesion.getAttribute("usuarioObjeto");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Perfil - Culturarte</title>
    <style>
        body { font-family: Arial, sans-serif; margin:0; background:#f5f7fa; }
        header { background:#2c3e50; color:white; padding:15px 25px; display:flex; justify-content:space-between; align-items:center; }
        header h1 { margin:0; font-size:20px; }
        nav a { color:white; margin:0 10px; text-decoration:none; }
        nav a:hover { text-decoration:underline; }
        .container { padding:30px; }
        .card { background:white; border-radius:8px; box-shadow:0 2px 6px rgba(0,0,0,0.1); padding:25px; max-width:600px; margin:0 auto; }
        h2 { color:#2c3e50; margin-bottom:15px; }
        p { margin:8px 0; }
        .actions { margin-top:20px; }
        .actions a { display:inline-block; padding:10px 20px; background:#3498db; color:white; border-radius:5px; text-decoration:none; margin-right:10px; }
        .actions a:hover { background:#2980b9; }
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
            <h2>👤 Perfil de <%= usuario%></h2>
            <p><b>Rol:</b> <%= rol%></p>
            <p><b>Nombre:</b> <%= user.getNombre()%> <%= user.getApellido()%></p>
            <p><b>Email:</b> <%= user.getEmail()%></p>

            <% if ("proponente".equalsIgnoreCase(rol)) { %>
            <h3>📋 Mis Propuestas</h3>
            <ul>
                <li><a href="misPropuestas.jsp">Ver propuestas creadas</a></li>
                <li><a href="altaPropuesta.jsp">Crear nueva propuesta</a></li>
            </ul>
        <% } else if ("colaborador".equalsIgnoreCase(rol)) { %>
            <h3>💰 Mis Colaboraciones</h3>
            <ul>
                <li><a href="misColaboraciones.jsp">Ver colaboraciones realizadas</a></li>
                <li><a href="favoritos.jsp">Mis propuestas favoritas</a></li>
            </ul>
            <% } else if ("admin".equalsIgnoreCase(rol)) { %>
            
            <% } %>

        <div class="actions">
            <a href="editarPerfil.jsp">✏️ Editar Perfil</a>
            <a href="home.jsp">⬅️ Volver</a>
        </div>
    </div>
</div>

</body>
</html>
