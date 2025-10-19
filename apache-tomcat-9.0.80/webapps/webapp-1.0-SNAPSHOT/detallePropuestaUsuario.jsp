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
            .actions {
    margin-top: 25px;
    display: flex;
    gap: 15px;
}

.actions button {
    background: #3498db;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 6px;
    cursor: pointer;
    font-size: 14px;
    transition: background 0.3s ease;
}

.actions button:hover {
    background: #2980b9;
}

.actions .cancelar {
    background: #e74c3c;
}

.actions .cancelar:hover {
    background: #c0392b;
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
                <!-- Imagen de propuesta -->
                <div style="text-align:center; margin-bottom:20px;">
                    <c:choose>
                        <c:when test="${not empty imagenBase64}">
                            <img src="data:image/jpeg;base64,${imagenBase64}" 
                                 alt="Imagen de la propuesta"
                                 style="width:100%; max-width:400px; border-radius:10px; object-fit:cover; box-shadow:0 3px 8px rgba(0,0,0,0.1);">
                        </c:when>
                        <c:otherwise>
                            <img src="https://cdn-icons-png.flaticon.com/512/1055/1055666.png"
                                 alt="Propuesta sin imagen"
                                 style="width:100%; max-width:250px; opacity:0.85; border-radius:10px;">
                        </c:otherwise>
                    </c:choose>
                </div>
                <h2>${propuesta.titulo}</h2>
                <p><strong>Descripción:</strong> ${propuesta.descripcion}</p>
                <p><strong>Tipo de espectáculo:</strong> ${propuesta.tipoEspectaculo}</p>
                <p><strong>Lugar:</strong> ${propuesta.lugar}</p>
                <p><strong>Fecha:</strong> ${propuesta.fechaRealizacionFormateada}</p>
                <p><strong>Hora:</strong> ${propuesta.hora}</p>
                <p><strong>Precio entrada:</strong> ${propuesta.precioEntrada}</p>
                <p><strong>Monto necesario:</strong> ${propuesta.montoNecesario}</p>
                <p><strong>Monto recaudado</strong> ${propuesta.montoRecaudado}</p>
                <p><strong>Tipo retorno:</strong> ${propuesta.tipoRetorno}</p>
                <p><strong>Estado:</strong> ${propuesta.estadoActual}</p>
                <c:if test="${esPropioPerfil}">
                    <div class="actions">

                        <!-- 🔹 Mostrar solo si esta en financiacion -->
                        <c:if test="${propuesta.estadoActual eq 'EN_FINANCIACION'}">
                            <form action="extenderFinanciacionServlet" method="POST" style="display:inline;">
                                <input type="hidden" name="titulo" value="${propuesta.titulo}">
                                <button type="submit">⏳ Extender financiación</button>
                            </form>

                            <form action="cancelarPropuestaServlet" method="POST" style="display:inline;">
                                <input type="hidden" name="titulo" value="${propuesta.titulo}">
                                <button type="submit" class="cancelar">❌ Cancelar propuesta</button>
                            </form>
                        </c:if>

                        <!-- 🔹 Mostrar mensaje si ya fue cancelada -->
                        <c:if test="${propuesta.estadoActual eq 'CANCELADA'}">
                            <p style="color:#e74c3c; font-weight:bold;">Esta propuesta fue cancelada ❌</p>
                        </c:if>

                    </div>
                </c:if>
                <c:if test="${colaboro}">
                    <div class="card" style="margin-top: 30px;">
                        <h3>💬 Dejar un comentario</h3>
                        <form action="dejarComentarioServlet" method="POST" style="display:flex; flex-direction:column; gap:15px; margin-top:10px;">
                            <input type="hidden" name="titulo" value="${propuesta.titulo}">

                            <textarea name="comentario"
                                      placeholder="Escribe aquí tu opinión sobre la propuesta..."
                                      required
                                      style="width:100%; min-height:100px; border:1px solid #ccc; border-radius:8px; padding:10px; font-family:inherit; font-size:15px; resize:vertical; background:#fafafa;"></textarea>

                            <button type="submit"
                                    style="align-self:flex-start; background:#3498db; color:white; border:none; padding:10px 20px; border-radius:6px; font-weight:bold; cursor:pointer; box-shadow:0 2px 4px rgba(0,0,0,0.1); transition:background 0.2s;">
                                💬 Publicar comentario
                            </button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${puedeColaborar}">
                                   <!-- Botón de colaborar -->
                <button id="mostrarFormBtn"
                        style="background:#3498db;
                        color:white;
                        border:none;
                        padding:10px 22px;
                        border-radius:5px;
                        font-size:15px;
                        font-weight:bold;
                        cursor:pointer;
                        box-shadow:0 2px 4px rgba(0,0,0,0.15);
                        transition:background 0.2s, transform 0.1s;">
                    🤝 Colaborar
                </button>

                <!-- Formulario oculto -->
                <div id="formColaboracion" style="display:none; margin-top:20px;">
                    <form action="altaColaboracionServlet" method="POST"
                          style="display:flex; flex-direction:column; gap:15px;">

                        <input type="hidden" name="titulo" value="${propuesta.titulo}">

                        <label for="monto"><strong>Monto a colaborar:</strong></label>
                        <input type="number" id="monto" name="monto" min="1" required
                               style="padding:10px; border:1px solid #ccc; border-radius:6px;">

                        <label for="tipoRetorno"><strong>Tipo de retorno:</strong></label>

                        <c:choose>
                            <c:when test="${propuesta.tipoRetorno eq 'Ambas'}">
                                <select id="tipoRetorno" name="tipoRetorno" required
                                        style="padding:10px; border:1px solid #ccc; border-radius:6px;">
                                    <option value="">-- Seleccione --</option>
                                    <option value="Entrada">Entrada</option>
                                    <option value="Porcentaje">Porcentaje</option>
                                </select>
                            </c:when>

                            <c:when test="${propuesta.tipoRetorno eq 'Entrada'}">
                                <input type="hidden" name="tipoRetorno" value="Entrada">
                                <p style="margin:0; font-weight:bold; color:#2c3e50;">🎟️ Retorno: Entrada</p>
                            </c:when>

                            <c:when test="${propuesta.tipoRetorno eq 'Porcentaje'}">
                                <input type="hidden" name="tipoRetorno" value="Porcentaje">
                                <p style="margin:0; font-weight:bold; color:#2c3e50;">💰 Retorno: Porcentaje</p>
                            </c:when>
                        </c:choose>


                        <button type="submit"
                                style="align-self:flex-start;
                                background:#3498db;
                                color:white;
                                border:none;
                                padding:10px 22px;
                                border-radius:5px;
                                font-size:15px;
                                font-weight:bold;
                                cursor:pointer;
                                box-shadow:0 2px 4px rgba(0,0,0,0.15);
                                transition:background 0.2s, transform 0.1s;">
                            ✅ Confirmar colaboración
                        </button>
                    </form>
                </div>

                <script>
                    const btn = document.getElementById('mostrarFormBtn');
                    const form = document.getElementById('formColaboracion');
                    btn.addEventListener('click', () => {
                        form.style.display = form.style.display === 'none' ? 'block' : 'none';
                        btn.textContent = form.style.display === 'block' ? '🔽 Ocultar formulario' : '🤝 Colaborar';
                    });
                </script>
                </c:if>
                <a class="volver" href="consultaPropuestaServlet?nickname=${usuario.nickname}">⬅️ Volver a la lista</a>
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