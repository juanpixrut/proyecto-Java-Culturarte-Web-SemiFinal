<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bienvenido</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #2c3e50, #3498db);
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
            width: 350px;
            text-align: center;
        }

        h1 {
            margin-bottom: 20px;
            color: #2c3e50;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 12px;
            margin: 8px 0;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 12px;
            margin-top: 12px;
            background: #3498db;
            border: none;
            border-radius: 8px;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background: #2980b9;
        }

        .mensaje {
            margin-top: 15px;
            font-size: 14px;
            color: #555;
        }

        #usuarioInexistente{
            margin-top: 15px;
            font-size: 14px;
            color: #555;
            color: blue;  
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Bienvenido a la Web</h1>
    <form action="login" method="POST">
        <input type="text" name="usuario" placeholder="Usuario" required>
        <input type="password" name="clave" placeholder="Contraseña" required>
        <button type="submit">Ingresar</button>
    </form>
    <form id="formVisitante" action="login" method="POST">
        <input type="hidden" name="usuario" value="visitante">
        <button type="submit">Entrar como visitante</button>
    </form>
    <div class="mensaje">
        ¿No tienes cuenta? <a href="registro.jsp">Regístrate aquí</a>
    </div>
    <p id = "usuarioInexistente">
    <%= request.getAttribute("mensaje") == null ? "" : request.getAttribute("mensaje")%>
    </p>
</div>
</body>
</html>

