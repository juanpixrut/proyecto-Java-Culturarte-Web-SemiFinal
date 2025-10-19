/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import logica.*;
import persistencia.*;

/**
 *
 * @author Juanpi
 */
@WebServlet(name = "adminServlet", urlPatterns = {"/adminServlet"})
public class adminServlet extends HttpServlet {
    
ControladoraNueva Sistema = new ControladoraNueva(); //creada como hija de ictrl, (pasar todos los metodos luego a ictrl)


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        HttpSession sesion = request.getSession();
        
        List<propuesta> propuestas = new ArrayList<>();
        propuestas = Sistema.listarPropuestas();
        
        //guardamos la lista en la request.
        request.setAttribute("propuestas", propuestas);
        request.getRequestDispatcher("evaluarPropuesta.jsp").forward(request, response);
        //response.sendRedirect("evaluarPropuesta.jsp");

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
