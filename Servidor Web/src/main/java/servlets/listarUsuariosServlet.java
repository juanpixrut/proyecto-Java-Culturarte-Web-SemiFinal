/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logica.*;
import persistencia.*;

/**
 *
 * @author Juanpi
 */
@WebServlet(name = "listarUsuariosServlet", urlPatterns = {"/listarUsuariosServlet"})
public class listarUsuariosServlet extends HttpServlet {

    ControladoraNueva Sistema = new ControladoraNueva();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
        List<proponente> proponentes = new ArrayList<>();
        proponentes = Sistema.listarProponentes();
        
        List<colaborador> colaboradores = new ArrayList<>();
        colaboradores = Sistema.listarColaboradores();
        
        request.setAttribute("proponentes", proponentes);
        request.setAttribute("colaboradores", colaboradores);
        
        request.getRequestDispatcher("otrosPerfiles.jsp").forward(request, response);
        
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
