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
import javax.servlet.http.HttpSession;

import logica.*;
import persistencia.*;
import logica.dtos.*;

/**
 *
 * @author Juanpi
 */
@WebServlet(name = "consultaPropuestaServlet", urlPatterns = {"/consultaPropuestaServlet"})
public class consultaPropuestaServlet extends HttpServlet {
    
    ControladoraNueva Sistema = new ControladoraNueva();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Obtener el parametro de busqueda (puede venir vacio)
        String filtros = request.getParameter("filtros");
        String estado = request.getParameter("estado");
        String orden = request.getParameter("orden");
        String tipo = request.getParameter("tipo");
        
        List<PropuestaDTO> propuestas = Sistema.listarPropuestasNoIngresadas();

        //FILTRO DE TEXTO
        if (filtros != null && !filtros.trim().isEmpty()) {
            List<PropuestaDTO> filtradas = new ArrayList<>();
            String[] palabras = filtros.split(",");
            for (PropuestaDTO p : propuestas) {
                for (String palabra : palabras) {
                    String f = palabra.trim().toLowerCase();
                    if (p.getTitulo().toLowerCase().contains(f)
                            || p.getDescripcion().toLowerCase().contains(f)
                            || p.getLugar().toLowerCase().contains(f)) {
                        filtradas.add(p);
                        break;
                    }
                }
            }
            propuestas = filtradas;
        }

        //FILTRAR POR ESTADO
        if (estado != null && !estado.isEmpty()) {
            propuestas.removeIf(p -> !p.getEstadoActual().toString().equalsIgnoreCase(estado));
        }
        
        //FILTRAR POR TIPO
        if (tipo != null && !tipo.isEmpty()) {
            propuestas.removeIf(p -> !p.getTipoEspectaculo().equalsIgnoreCase(tipo.trim()));
        }
        
        //ORDENAR
        if ("ALFABETICO".equalsIgnoreCase(orden)) {
            propuestas.sort((a, b) -> a.getTitulo().compareToIgnoreCase(b.getTitulo()));
        } else if ("ANIO_DESC".equalsIgnoreCase(orden)) {
            propuestas.sort((a, b) -> {
                if (a.getFechaPublicada() == null || b.getFechaPublicada() == null) {
                    return 0;
                }
                return b.getFechaPublicada().compareTo(a.getFechaPublicada()); // mas reciente primero
            });
        }
        
        //categorias
        List<categoria> categorias = Sistema.listarCategoria();
        request.setAttribute("categorias", categorias);
        
        //guardamos la lista en la request.
        request.setAttribute("propuestas", propuestas);
        request.setAttribute("filtros", filtros); // para volver a mostrar el texto en el input
        request.setAttribute("estado", estado);
        request.setAttribute("orden", orden);
        request.getRequestDispatcher("consultaPropuesta.jsp").forward(request, response);
        //response.sendRedirect(".jsp");
        
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
