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
@WebServlet(name = "consultaPerfilServlet", urlPatterns = {"/consultaPerfilServlet"})
public class consultaPerfilServlet extends HttpServlet {
    
    ControladoraNueva Sistema = new ControladoraNueva();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        HttpSession session = request.getSession();
        usuario usuarioSesion = (usuario) session.getAttribute("usuarioObjeto");
        
        String nickname = request.getParameter("nickname");
        usuario usuarioBuscado = Sistema.buscoUsuario2(nickname);

        if (nickname == null || nickname.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        if(usuarioBuscado == null){
            response.sendRedirect("index.jsp");
            return;
        }

        //no se puede seguir a el mismo.
        boolean mismoPerfil = false;
        if (nickname.equalsIgnoreCase(usuarioSesion.getNickname())) {
            mismoPerfil = true;
            request.setAttribute("mismoPerfil", mismoPerfil);

        }
        
        request.setAttribute("usuarioBuscado", usuarioBuscado);

        if (usuarioBuscado.getImagen() != null) {
            String base64 = java.util.Base64.getEncoder().encodeToString(usuarioBuscado.getImagen());
            request.setAttribute("imagenBase64", base64);
        }
        
        boolean yaLoSigue = Sistema.usuarioSigueA(usuarioSesion, nickname);
        request.setAttribute("yaLoSigue", yaLoSigue);

        request.getRequestDispatcher("/consultaPerfil.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
