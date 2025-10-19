<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    HttpSession sesion = request.getSession(false);
    String rol = null;
    String usuario = null;

    if (sesion != null) {
        rol = (String) sesion.getAttribute("rol");
        usuario = (String) sesion.getAttribute("usuarioLogueado");
    }else {
        // si no hay sesion, simulamos visitante
        rol = "visitante";
        usuario = "visitante";
    }
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Culturarte - Principal</title>

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

            .buscador {
                background: #ecf0f1;
                padding: 20px;
                width: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
                gap: 10px;
            }

            .buscador input {
                flex: 1;
                max-width: 600px;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 15px;
            }

            .buscador button {
                background: #3498db;
                color: white;
                border: none;
                border-radius: 6px;
                padding: 10px 20px;
                font-weight: bold;
                cursor: pointer;
                transition: background 0.3s;
            }

            .buscador button:hover {
                background: #2980b9;
            }

            .content {
                max-width: 1100px;
                margin: 40px auto;
                padding: 0 20px;
            }

            h2 {
                text-align: center;
                color: #2c3e50;
            }

            .tarjetas {
                display: flex;
                justify-content: center;
                flex-wrap: wrap;
                gap: 25px;
                margin-top: 40px;
            }

            .card {
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                padding: 25px;
                width: 300px;
                transition: transform 0.2s ease;
            }

            .card:hover {
                transform: translateY(-5px);
            }

            .card h3 {
                color: #3498db;
                margin-bottom: 10px;
            }

            .card p {
                color: #555;
                font-size: 14px;
                margin-bottom: 15px;
            }

            .card a {
                display: inline-block;
                background: #3498db;
                color: white;
                padding: 8px 15px;
                border-radius: 6px;
                text-decoration: none;
                font-weight: bold;
                font-size: 14px;
            }

            .card a:hover {
                background: #2980b9;
            }

            footer {
                background: #2c3e50;
                color: white;
                text-align: center;
                padding: 15px 0;
                margin-top: 60px;
                font-size: 14px;
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

        <div class="buscador">
            <form action="consultaPropuestaServlet" method="GET" style="display:flex; width:70%; gap:10px;">
                <input type="text" name="filtros" placeholder="Buscar por título, descripción o lugar (separá por comas)">
                <button type="submit">🔍 Buscar</button>
            </form>
            <% if (usuario != null && !"visitante".equalsIgnoreCase(rol)) {%>
            <form action="consultaPerfilServlet" method="GET" 
                  style="display:flex; align-items:center; gap:5px;">
                <input type="text" name="nickname" placeholder="Buscar usuario..."
                       style="padding:6px 8px; border:1px solid #ccc; border-radius:5px; font-size:13px; width:130px;">
                <button type="submit" 
                        style="background:#3498db; border:none; color:white; border-radius:5px; padding:6px 10px; cursor:pointer;">
                    🔍
                </button>
            </form>
            <% } %>
        </div>

        <div class="content">
            <% if (rol == null || "visitante".equalsIgnoreCase(rol)) { %>
            <h2>Bienvenido Visitante 🤝</h2>
            <p style="text-align:center; color:#555;">Explorá las propuestas, Conoce nuestros usuarios o iniciá sesión para acceder a más funciones.</p>

            <div class="tarjetas">
                <div class="card">
                    <h3>📚 Ver Propuestas</h3>
                    <p>Explorá los proyectos culturales disponibles.</p>
                    <a href="consultaPropuestaServlet">Ver</a>
                </div>

                <div class="card">
                    <h3>👥 Usuarios</h3>
                    <p>Descubrí quiénes forman parte de nuestra comunidad.</p>
                    <a href="consultaUsuariosServlet">Ver</a>
                </div>
            </div>

            <% } else if ("proponente".equalsIgnoreCase(rol)) {%>
            <h2>Bienvenido, <%= usuario%> 🎭</h2>
            <p style="text-align:center; color:#555;">Gestioná tus propuestas y conectá con tus colaboradores.</p>

            <div class="tarjetas">
                <div class="card">
                    <h3>➕ Crear Propuesta</h3>
                    <p>Publicá tu nuevo espectáculo o evento cultural.</p>
                    <a href="altaPropuestaServlet">Ir</a>
                </div>

                <div class="card">
                    <h3>💰 Extender Financiación</h3>
                    <p>Amplía el período de financiación de tus proyectos.</p>
                    <a href="extenderFinanciacion.jsp">Ir</a>
                </div>

                <div class="card">
                    <h3>🛑 Cancelar Propuesta</h3>
                    <p>Gestioná las propuestas que ya no quieras mantener activas.</p>
                    <a href="cancelarPropuestaServlet">Ir</a>
                </div>

                <div class="card">
                    <h3>📂 Ver Propuestas</h3>
                    <p>Consultá todas tus propuestas activas y su estado.</p>
                    <a href="consultaPropuestaServlet">Ver</a>
                </div>

                <div class="card">
                    <h3>📂 Mis propuestas favoritas</h3>
                    <p>Administra las Propuestas que mas te llamen la atencion.</p>
                    <a href="propuestaFavoritaServlet">Ver</a>
                </div>

                <div class="card">
                    <h3>👥 Nuestros Usuarios</h3>
                    <p>Descubrí quiénes forman parte de nuestra comunidad.</p>
                    <a href="consultaUsuariosServlet">Ver</a>
                </div>
            </div>

            <% } else if ("colaborador".equalsIgnoreCase(rol)) {%>
            <h2>Bienvenido, <%= usuario%> 🤝</h2>
            <p style="text-align:center; color:#555;">Explorá proyectos culturales y colaborá en tus favoritos.</p>

            <div class="tarjetas">
                <div class="card">
                    <h3>💸 Colaborar</h3>
                    <p>Apoyá propuestas culturales con tus aportes.</p>
                    <a href="altaColaboracionServlet">Ir</a>
                </div>

                <div class="card">
                    <h3>💬 Comentarios</h3>
                    <p>Compartí tu opinión y experiencias con las propuestas.</p>
                    <a href="AgregarComentario.jsp">Ir</a>
                </div>

                <div class="card">
                    <h3>📚 Ver Propuestas</h3>
                    <p>Explorá todas las propuestas disponibles.</p>
                    <a href="consultaPropuestaServlet">Ver</a>
                </div>

                <div class="card">
                    <h3>📂 Mis propuestas favoritas</h3>
                    <p>Administra las Propuestas que mas te llamen la atencion.</p>
                    <a href="propuestaFavoritaServlet">Ver</a>
                </div>

                <div class="card">
                    <h3>👥 Nuestros Usuarios</h3>
                    <p>Descubrí quiénes forman parte de nuestra comunidad.</p>
                    <a href="consultaUsuariosServlet">Ver</a>
                </div>
            </div>

            <% } else if ("admin".equalsIgnoreCase(rol)) {%>
            <h2>Bienvenido Administrador, <%= usuario%> 🛠️</h2>
            <p style="text-align:center; color:#555;">Supervisá y evaluá las propuestas de la plataforma.</p>

            <div class="tarjetas">
                <div class="card">
                    <h3>🌍 Evaluar Propuestas</h3>
                    <p>Accedé al panel de revisión y control de proyectos.</p>
                    <a href="adminServlet">Ir</a>
                </div>
            </div>
            <% }%>
        </div>

        <footer>
            © 2025 Culturarte - Plataforma Cultural
        </footer>

    </body>
</html>
