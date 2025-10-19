<%-- 
    Document   : altaPerfil
    Created on : 8 Oct 2025, 17:46:32
    Author     : Juanpi
--%>

<%@page import="logica.*"%>
<%@page import="java.util.List"%>
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
        <title>Alta de propuesta</title>
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
            .actions {
                margin-top:20px;
            }
            .actions a {
                display:inline-block;
                padding:10px 20px;
                background:#3498db;
                color:white;
                border-radius:5px;
                text-decoration:none;
                margin-right:10px;
            }
            .actions a:hover {
                background:#2980b9;
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
    <div class="card">
        <h2>🆕 Alta de Propuesta</h2>
        <form action="altaPropuestaServlet" method="POST">

            <input type="text" name="titulo" placeholder="Titulo" required>

            <input type="text" name="descripcion" placeholder="Descripcion" required>

            <select name="tipoEspectaculo" id="tipoEspectaculo" required>
                <option value="">-- Seleccione un tipo --</option>
                <%
                    List<categoria> tipos = (List<categoria>) request.getAttribute("tiposCategorias");
                    if (tipos != null) {
                        for (categoria c : tipos) {
                %>
                <option value="<%= c.getNombre()%>"><%= c.getNombre()%></option>
                <%
                        }
                    }
                %>
            </select>

            <input type="text" name="lugar" placeholder="Lugar" required>
            
            <input type="date" name="fechaRealizacion" placeholder="Fecha Realizacion" required>
            <input type="time" id="horaRealizacion" name="horaRealizacion" required>
            
            <input type="number" name="precioEntrada" placeholder="Precio entrada" required>
            <input type="number" name="montoNecesario" placeholder="Monto necesario" required>

            <select name="tipoRetorno" id="tipoRetorno" required>
                <option value="">-- Seleccione un tipo --</option>
                <option value="Entrada">Entrada</option>
                <option value="Porcentaj">Porcentaje</option>
                <option value="Ambos">Ambos</option>
            </select>

                <button type="submit">✅ Crear Propuesta</button>
        </form>
    </div>
</div>

</body>
</html>

