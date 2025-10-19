<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Perfil de ${usuarioBuscado.nickname} - Culturarte</title>
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
            }
            header h1 {
                font-size: 22px;
                color: #2c3e50;
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
            .perfil-container {
                max-width: 800px;
                margin: 50px auto;
                background: white;
                border-radius: 12px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                padding: 30px;
                text-align: center;
            }
            .perfil-container img {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                object-fit: cover;
                margin-bottom: 15px;
            }
            .perfil-container h2 {
                margin: 10px 0;
                color: #3498db;
            }
            .perfil-container p {
                color: #555;
                font-size: 15px;
            }
            .botones {
                margin-top: 25px;
            }
            .botones form {
                display: inline-block;
                margin: 0 5px;
            }
            .botones button {
                background: #3498db;
                color: white;
                border: none;
                border-radius: 6px;
                padding: 10px 20px;
                font-weight: bold;
                cursor: pointer;
                transition: 0.3s;
            }
            .botones button:hover {
                background: #2980b9;
            }
            .volver {
                display: inline-block;
                margin-top: 30px;
                text-decoration: none;
                color: #2c3e50;
                font-weight: bold;
            }
            .volver:hover {
                color: #3498db;
            }
        </style>
    </head>
    <body>

        <header>
            <h1>🌐 Culturarte</h1>
            <nav>
                <a href="index.jsp">🏠 Inicio</a>
            </nav>
        </header>

        <div class="perfil-container">

            <!-- Imagen de perfil -->
            <c:choose>
                <c:when test="${not empty imagenBase64}">
                    <img src="data:image/jpeg;base64,${imagenBase64}" alt="Foto de perfil">
                </c:when>
                <c:otherwise>
                    <img src="https://cdn-icons-png.flaticon.com/512/149/149071.png" alt="Sin foto">
                </c:otherwise>
            </c:choose>

            <h2>${usuarioBuscado.nickname}</h2>
            <p><strong>Nombre:</strong> ${usuarioBuscado.nombre} ${usuarioBuscado.apellido}</p>
            <p><strong>Email:</strong> ${usuarioBuscado.email}</p>
            <p><strong>Rol:</strong> ${usuarioBuscado.getClass().simpleName}</p>

            <!-- Mostrar opciones según sea su perfil o no -->
            <div class="botones">
                <c:choose>
                    <c:when test="${mismoPerfil}">
                        <p>👤 Este es tu propio perfil.</p>
                    </c:when>

                    <c:when test="${yaLoSigue}">
                        <form action="dejarSeguirUsuarioServlet" method="POST">
                            <input type="hidden" name="nicknameSeguido" value="${usuarioBuscado.nickname}">
                            <button type="submit">🚫 Dejar de seguir</button>
                        </form>
                    </c:when>

                    <c:otherwise>
                        <form action="seguirUsuarioServlet" method="POST">
                            <input type="hidden" name="nicknameSeguido" value="${usuarioBuscado.nickname}">
                            <button type="submit">👥 Seguir</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>

            <a href="index.jsp" class="volver">⬅️ Volver atras</a>
        </div>

    </body>
</html>
