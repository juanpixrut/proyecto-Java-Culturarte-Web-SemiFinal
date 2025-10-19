/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logica.*;
import logica.dtos.PropuestaDTO;
import persistencia.*;

/**
 *
 * @author Juanpi
 */
@WebServlet(name = "cancelarPropuestaServlet", urlPatterns = {"/cancelarPropuestaServlet"})
public class cancelarPropuestaServlet extends HttpServlet {
    
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
        
        String nicknamePedido = (String) session.getAttribute("usuarioSesion");

        List<PropuestaDTO> propuestas = Sistema.listarPropuestasFinanciadas(nicknamePedido);

        request.setAttribute("propuestas", propuestas);

        request.getRequestDispatcher("cancelarPropuesta.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        //TENGO QUE CONTROLAR Q SI NO HAY NADA Y LE DAS AL BOTON NO DE ERROR
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        //aca la cancelacion.
        String titulo = request.getParameter("titulo");
        Sistema.cancelarPropuesta(titulo);
        response.sendRedirect("home.jsp");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
