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

import logica.*;
import persistencia.*;
import logica.dtos.*;

/**
 *
 * @author Juanpi
 */
@WebServlet(name = "consultaUsuariosServlet", urlPatterns = {"/consultaUsuariosServlet"})
public class consultaUsuariosServlet extends HttpServlet {
    
    ControladoraNueva Sistema = new ControladoraNueva();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        //List<UsuarioDTO> usuarios  = Sistema.listarUsuariosDTO();
  
        List<ProponenteDTO> proponentes = Sistema.listarProponentesDTO();
        List<ColaboradorDTO> colaboradores = Sistema.listarColaboradoresDTO();

        //guardamos la lista en la request.
        request.setAttribute("proponentes", proponentes);
        request.setAttribute("colaboradores", colaboradores);
        request.getRequestDispatcher("consultaUsuarios.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
