<%-- 
    Document   : altaPerfil
    Created on : 8 Oct 2025, 17:46:32
    Author     : Juanpi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>Alta de perfil</title>
        <style>
        body {
font-family: Arial, sans-serif;
margin:0;
background:#f5f7fa;
}
        header {
background:#2c3e50;
color:white;
padding:15px 25px;
display:flex;
justify-content:space-between;
align-items:center;
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
        .container {
padding:30px;
}
        .card {
background:white;
border-radius:8px;
box-shadow:0 2px 6px rgba(0,0,0,0.1);
padding:25px;
max-width:600px;
margin:0 auto;
}
        h2 {
color:#2c3e50;
margin-bottom:15px;
}
        p {
margin:8px 0;
}
        #entrada {
margin:10px 10px 10px 0;
padding:8px;
width:95%;
border:1px solid #ccc;
border-radius:5px;
}
        #tipo {
margin-top: 10px;
padding:8px;
border-radius:5px;
}
        .cardContra {
background:white;
border-radius:8px;
box-shadow:0 2px 6px rgba(0,0,0,0.1);
padding:10px;
margin-top:15px;
}
        button {
background:#3498db;
color:white;
border:none;
border-radius:5px;
padding:10px 20px;
cursor:pointer;
}
        button:hover {
background:#2980b9;
}
        </style>
    </head>
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
            <div class="card">
                <h2>🆕 Alta de Perfil</h2>
                <form action="altaPerfilServlet" method="POST" enctype="multipart/form-data">
                    
                    <input id="entrada" type="text" name="nickname" value="${param.nickname}" placeholder="Nickname" required><br>
                    <input id="entrada" type="text" name="nombre" value="${param.nombre}" placeholder="Nombre" required><br>
                    <input id="entrada" type="text" name="apellido" value="${param.apellido}" placeholder="Apellido" required><br>
                    <input id="entrada" type="email" name="email" value="${param.email}" placeholder="Email" required><br>

                    <!-- 📅 Fecha de nacimiento -->
                    <input id="entrada" type="date" name="fechaNacimiento" value="${param.fechaNacimiento}" required><br>

                    <!-- 🖼️ Imagen -->
                    <input id="entrada" type="file" name="imagen" accept="image/*" required><br>

                    <select name="tipo" id="tipo" onchange="mostrarCamposExtras()">
                        <option value="colaborador">Colaborador</option>
                        <option value="proponente">Proponente</option>
                    </select>

                    <div id="extraProponente" style="display:none;">
                        <input id="entrada" type="text" name="direccion" value="${param.direccion}" placeholder="Dirección">
                        <input id="entrada" type="text" name="biografia" value="${param.biografia}" placeholder="Biografía">
                        <input id="entrada" type="text" name="sitioWeb" value="${param.sitioWeb}" placeholder="Sitio web">
                    </div>
                    
                    <div class="cardContra">
                        <input id="entrada" type="password" name="contrasena" placeholder="Contraseña" required>
                        <input id="entrada" type="password" name="confirmacion" placeholder="Confirmación de contraseña" required>
                    </div>

                    <button type="submit">✅ Crear Perfil</button>

                    <% if (request.getAttribute("mensaje") != null) { %>
                        <div style="color:red; margin-top:10px;">
                            <%= request.getAttribute("mensaje") %>
                        </div>
                    <% } %>
                </form>
            </div>
        </div>
        
        <script>
        function mostrarCamposExtras() {
            const tipo = document.getElementById("tipo").value;
            const extra = document.getElementById("extraProponente");
            const inputs = extra.querySelectorAll("input");

            if (tipo === "proponente") {
                extra.style.display = "block";
                inputs.forEach(i => i.required = true);
            } else {
                extra.style.display = "none";
                inputs.forEach(i => i.required = false);
            }
        }
        document.addEventListener("DOMContentLoaded", mostrarCamposExtras);
        document.getElementById("tipo").addEventListener("change", mostrarCamposExtras);
        </script>
    </body>
</html>
