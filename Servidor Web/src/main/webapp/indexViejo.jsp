<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Culturarte - Inicio</title>

        <style>
            body {
                margin: 0;
                font-family: 'Segoe UI', Arial, sans-serif;
                background: #f5f7fa;
                color: #2c3e50;
            }

            header {
                background: #ffffff;
                border-bottom: 1px solid #ddd;
                padding: 15px 40px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                position: sticky;
                top: 0;
                z-index: 10;
            }

            header h1 {
                margin: 0;
                font-size: 22px;
                color: #2c3e50;
                font-weight: bold;
            }

            nav a {
                color: #2c3e50;
                text-decoration: none;
                font-weight: 500;
                margin: 0 10px;
            }

            nav a:hover {
                color: #3498db;
            }

            .hero {
                text-align: center;
                padding: 80px 20px;
                background: linear-gradient(135deg, #3498db, #2ecc71);
                color: white;
            }

            .hero h2 {
                font-size: 36px;
                margin-bottom: 10px;
            }

            .hero p {
                font-size: 18px;
                margin-bottom: 30px;
            }

            .hero a {
                background: white;
                color: #2c3e50;
                padding: 12px 25px;
                border-radius: 8px;
                text-decoration: none;
                font-weight: bold;
                margin: 0 10px;
                transition: 0.3s;
            }

            .hero a:hover {
                background: #ecf0f1;
            }

            .explora {
                text-align: center;
                margin: 50px auto;
                max-width: 800px;
            }

            .explora h3 {
                color: #2c3e50;
                font-size: 24px;
                margin-bottom: 10px;
            }

            .explora p {
                color: #555;
                font-size: 16px;
                margin-bottom: 25px;
            }

            .explora a {
                background: #3498db;
                color: white;
                padding: 10px 20px;
                border-radius: 8px;
                text-decoration: none;
                font-weight: bold;
            }

            .explora a:hover {
                background: #2980b9;
            }

            footer {
                background: #2c3e50;
                color: white;
                text-align: center;
                padding: 15px 0;
                margin-top: 50px;
                font-size: 14px;
            }
        </style>
    </head>

    <body>

        <header>
            <h1>🌐 Culturarte</h1>
            <nav>
                <a href="home">🏠 Inicio</a>

                <% if (usuario != null && !"visitante".equalsIgnoreCase(rol)) {%>
                <span style="margin-right:10px;">👤 <%= usuario%></span>
                <a href="logout">🚪 Cerrar Sesión</a>
                <% } else { %>
                <a href="altaPerfil.jsp">🆕 Crear Cuenta</a>
                <a href="inicioSesion.jsp">🔑 Iniciar Sesión</a>
                <% }%>
            </nav>
        </header> 

        <section class="hero">
            <h2>Bienvenido a Culturarte</h2>
            <p>Donde los Creadores y Colaboradores se unen.</p>
        </section>

        <section class="explora">
            <h3>Descubrí lo que está pasando</h3>
            <p>Sentite comodo y navega por las propuestas.</p>
            <a href="consultaPropuestaServlet">🔍 Ver todas las propuestas</a>
        </section>

        <footer>
            © 2025 Culturarte - Plataforma Cultural
        </footer>

    </body>
</html>
