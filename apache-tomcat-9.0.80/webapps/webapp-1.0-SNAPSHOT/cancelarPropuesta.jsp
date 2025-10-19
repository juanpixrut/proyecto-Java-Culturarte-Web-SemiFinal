<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <title>Cancelar Propuesta</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #3498db, #2ecc71);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        header {
            background: #2c3e50;
            color: white;
            padding: 15px 25px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
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
            margin-top: 60px;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
            margin-bottom: 20px;
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
            cursor: pointer;
        }

        td {
            color: #2c3e50;
        }

        .btn-cancelar {
            background: #e74c3c;
            color: white;
            border: none;
            padding: 12px 25px;
            border-radius: 6px;
            font-size: 15px;
            font-weight: bold;
            cursor: pointer;
            align-self: center;
            box-shadow: 0 3px 6px rgba(0,0,0,0.2);
            transition: background 0.3s ease;
        }

        .btn-cancelar:hover {
            background: #c0392b;
        }

        .volver {
            display: inline-block;
            margin-top: 15px;
            background: #3498db;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            align-self: center;
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
    <h2>🌍 Cancelar una Propuesta Financiada</h2>

    <!-- FORMULARIO -->
    <form action="cancelarPropuestaServlet" method="POST">

        <table id="tablaPropuestas">
            <tr>
                <th></th>
                <th>Título</th>
                <th>Tipo</th>
                <th>Estado</th>
            </tr>
            <c:forEach var="p" items="${propuestas}">
                <tr onclick="seleccionarFila(this)">
                    <td>
                        <input type="radio" name="titulo" value="${p.titulo}" required>
                    </td>
                    <td>${p.titulo}</td>
                    <td>${p.tipoEspectaculo}</td>
                    <td>${p.estadoActual}</td>
                </tr>
            </c:forEach>
        </table>

        <button type="submit" class="btn-cancelar">❌ Cancelar Propuesta Seleccionada</button>
        <a href="index.jsp" class="volver">⬅️ Volver</a>
    </form>
</div>

<script>
    // Esto resalta la fila seleccionada al hacer clic
    function seleccionarFila(fila) {
        // Quita la clase de las demás
        document.querySelectorAll('#tablaPropuestas tr').forEach(tr => tr.style.backgroundColor = '');
        fila.style.backgroundColor = '#d6f5e3';
        fila.querySelector('input[type=radio]').checked = true;
    }
</script>

</body>
</html>
