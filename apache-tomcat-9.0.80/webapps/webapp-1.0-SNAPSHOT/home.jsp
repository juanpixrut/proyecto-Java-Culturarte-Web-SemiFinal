<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #3498db, #2ecc71);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            background: #fff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0px 8px 20px rgba(0,0,0,0.2);
            width: 400px;
            text-align: center;
        }

        h1 {
            margin-bottom: 20px;
            color: #2c3e50;
        }

        p {
            color: #555;
            margin-bottom: 20px;
        }

        a.button {
            display: inline-block;
            padding: 12px 20px;
            background: #e74c3c;
            color: white;
            border-radius: 8px;
            text-decoration: none;
            font-size: 16px;
        }

        a.button:hover {
            background: #c0392b;
        }
    </style>
    <meta http-equiv="refresh" content="1;URL=index.jsp">
</head>
<body>
<div class="container">
    <%
        HttpSession sesion = request.getSession(false);
        String usuario = null;
        if (sesion != null) {
            usuario = (String) sesion.getAttribute("usuarioLogueado");
        }
        if (usuario == null) {
            // si no hay usuario en sesión, volver al login
            response.sendRedirect("index.jsp");
        }
    %>

    <h1>Bienvenido, <%= usuario %> 🎉</h1>
    <p>Has iniciado sesión correctamente en la aplicación.</p>

   
</div>
</body>
</html>
