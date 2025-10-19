/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalTime;
import java.util.List;
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
@WebServlet(name = "altaPropuestaServlet", urlPatterns = {"/altaPropuestaServlet"})
public class altaPropuestaServlet extends HttpServlet {

    ControladoraNueva Sistema = new ControladoraNueva();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<categoria> tiposCategorias = Sistema.listarCategoria(); // o desde tu capa lógica

        // Los mando al jsp
        request.setAttribute("tiposCategorias", tiposCategorias);

        // Redirigo al jsp
        request.getRequestDispatcher("altaPropuesta.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        String tipoEspectaculo = request.getParameter("tipoEspectaculo");
        String lugar = request.getParameter("lugar");
        String fechaStr = request.getParameter("fechaRealizacion");
        String horaStr = request.getParameter("horaRealizacion"); 
        String tipoRetorno = request.getParameter("tipoRetorno");
        
        HttpSession sesion = request.getSession();
        proponente p = (proponente) sesion.getAttribute("usuario");

    Date fechaRealizacion = null;
    LocalTime hora = null;
    
    float precioEntrada = 0;
    float montoNecesario = 0;

    try {
        // El input type="date" devuelve formato yyyy-MM-dd
        fechaRealizacion = Date.valueOf(fechaStr);
        hora = LocalTime.parse(horaStr); // hh:mm

        precioEntrada = Float.parseFloat(request.getParameter("precioEntrada"));
        montoNecesario = Float.parseFloat(request.getParameter("montoNecesario"));
    } catch (Exception e) {
        e.printStackTrace(); // En desarrollo, para ver el error en consola
        request.setAttribute("error", "Error en el formato de los datos numericos o fecha.");
        request.getRequestDispatcher("/altaPropuesta.jsp").forward(request, response);
        return;
    }
    
    Sistema.altaPropuesta(p, titulo, descripcion, tipoEspectaculo, lugar, fechaRealizacion, hora, precioEntrada, montoNecesario, tipoRetorno, null, estadoPropuesta.INGRESADA);

        response.sendRedirect("home.jsp"); // o a donde quieras
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
