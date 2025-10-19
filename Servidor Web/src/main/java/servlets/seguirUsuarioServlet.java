/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logica.*;
import persistencia.*;

/**
 *
 * @author Juanpi
 */
@WebServlet(name = "seguirUsuarioServlet", urlPatterns = {"/seguirUsuarioServlet"})
public class seguirUsuarioServlet extends HttpServlet {
    
    ControladoraNueva Sistema = new ControladoraNueva();


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        // Recuperar la sesion actual
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("usuarioObjeto") == null) {
            response.sendRedirect("inicioSesion.jsp");
            return;
        }

        // Usuario logueado (el que sigue a otro)
        usuario usuarioSesion = (usuario) session.getAttribute("usuarioObjeto");
        String nicknameSeguidor = usuarioSesion.getNickname();

        // Usuario a seguir (viene por parametro del formulario jsp)
        String nicknameSeguido = request.getParameter("nicknameSeguido");

        if (nicknameSeguido == null || nicknameSeguido.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        // Registrar el seguimiento
        Sistema.seguirUsuario(nicknameSeguidor, nicknameSeguido);
        usuario usuarioActualizado = Sistema.buscoUsuario2(usuarioSesion.getNickname());
        session.setAttribute("usuarioObjeto", usuarioActualizado);
        
        // Redirigir al perfil del usuario seguido
        response.sendRedirect("consultaPerfilServlet?nickname=" + nicknameSeguido);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
