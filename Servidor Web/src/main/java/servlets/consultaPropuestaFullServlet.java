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
import persistencia.*;
import logica.dtos.*;

/**
 *
 * @author Juanpi
 */
@WebServlet(name = "consultaPropuestaFullServlet", urlPatterns = {"/consultaPropuestaFullServlet"})
public class consultaPropuestaFullServlet extends HttpServlet {
    
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
        String usuarioSesion = (String) session.getAttribute("usuarioSesion");
        String titulo1 = (String) session.getAttribute("titulo");
        
        String titulo = request.getParameter("titulo"); //
        propuesta prop = Sistema.buscoPropuesta(titulo);
        String nickname = prop.getProponente();
        
        //deberia buscar si es el mismo y ahi pasarle q si para q agregue boton de extender financiacion o cancelarla.
        boolean esPropioPerfil = nickname.equalsIgnoreCase(usuarioSesion);
        request.setAttribute("esPropioPerfil", esPropioPerfil);
        
        //si es colaborador y colaboro con la propuesta.
        colaborador col = Sistema.buscoColaborador(usuarioSesion);
        boolean colaboro = false;
        boolean puedeColaborar = false;
        
        if (col != null) {
            List<colaboracion> misColabs = Sistema.listarColaboraciones();
            for (colaboracion c : misColabs) {
                if (c.getPropuesta() != null && c.getPropuesta().getTitulo().trim().equalsIgnoreCase(titulo.trim()) && c.getColaborador().getNickname().equalsIgnoreCase(usuarioSesion)) {
                    colaboro = true;
                    break; // ya encontramos una coincidencia, no seguimos buscando
                }
            }
        }
        
        if(col != null && colaboro == false){
        puedeColaborar = true;
        }
        
        request.setAttribute("colaboro", colaboro);
        request.setAttribute("puedeColaborar", puedeColaborar);

        PropuestaDTO propuesta = Sistema.buscoPropuestaDTO(titulo);
        
        List<ColaboracionDTO> colaboradores = propuesta.getColaboraciones();
        
        if (propuesta.getImagen() != null) {
            String base64 = java.util.Base64.getEncoder().encodeToString(propuesta.getImagen());
            request.setAttribute("imagenBase64", base64);
        }

        request.setAttribute("colaboraciones", colaboradores);
        request.setAttribute("propuesta", propuesta);
        request.getRequestDispatcher("detallePropuestaUsuario.jsp").forward(request, response);
        
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
