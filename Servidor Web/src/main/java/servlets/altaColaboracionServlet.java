/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logica.*;
import persistencia.*;
import logica.dtos.*;

/**
 *
 * @author Juanpi
 */
@WebServlet(name = "altaColaboracionServlet", urlPatterns = {"/altaColaboracionServlet"})
public class altaColaboracionServlet extends HttpServlet {
    
    ControladoraNueva Sistema = new ControladoraNueva();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        //antes de mandarlo al jsp a q rellene todo necesito listarle las propuestas.
        List<PropuestaDTO> propuestas  = Sistema.listarPropuestasDTO();
        
        request.setAttribute("propuestas", propuestas);
        
        request.getRequestDispatcher("altaColaboracion.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        HttpSession sesion = request.getSession();
        
        String usuario = (String) sesion.getAttribute("usuarioSesion");
        
        //lo busco.
        colaborador colab = Sistema.buscoColaborador(usuario);
        
        //busco la propuesta
        String titulo = request.getParameter("titulo");
        propuesta prop = Sistema.buscoPropuesta(titulo);
        
        //aca el post d confirmar alta colab.
        String montoStr = request.getParameter("monto");
        Float montoAportado = Float.parseFloat(montoStr);
        String tipoRetorno = request.getParameter("tipoRetorno");
        
        //fecha del sistema.
        LocalDate hoy = LocalDate.now();
        Date fechaActual = Date.valueOf(LocalDate.now());
        
        try {
            //si no esta en financiacion. cambiar estado a en financiacion.
            if(prop.getEstadoActual().toString().equalsIgnoreCase("INGRESADA")){
            prop.setEstado(estadoPropuesta.EN_FINANCIACION);
            Sistema.modificoPropuesta(prop);
            }
            Sistema.altaColaboracion2(colab, prop, montoAportado, tipoRetorno, fechaActual);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "❌ Error al crear la colaboracion: " + e.getMessage());
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }
        
        response.sendRedirect("home.jsp"); // o a donde quieras
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
