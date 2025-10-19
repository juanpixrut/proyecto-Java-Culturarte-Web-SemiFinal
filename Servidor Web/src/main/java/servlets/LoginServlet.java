/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

/* 
info:
modificar el backend para q los usuarios tengan contrasena. regenerar el json y reimportarlo.
*/

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

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    ControladoraNueva Sistema = new ControladoraNueva(); //creada como hija de ictrl, (pasar todos los metodos luego a ictrl)
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        
        HttpSession sesion = request.getSession();
        sesion.setAttribute("usuarioSesion", usuario); //aca era nickname CAMBIE
        
        // Aca usamos el controlador para validar
        boolean valido = Sistema.validarUsuario(usuario, clave);

        //vemos el rol
        String rol = Sistema.buscoRol(usuario);
        
        usuario user = null;
        
        //para testear.
        if(usuario.equalsIgnoreCase("admin") && clave.equalsIgnoreCase("admin")){
            sesion.setAttribute("usuarioLogueado", "admin");
            sesion.setAttribute("rol", "admin");
            response.sendRedirect("home.jsp");
            return;
        }
        
        if (usuario.equalsIgnoreCase("visitante")) {
            sesion.setAttribute("usuarioLogueado", "visitante");
            sesion.setAttribute("rol", "visitante");
            response.sendRedirect("home.jsp");
            return;
        }

        if (valido) {
 
            //guardo el objeto asi uso los getters. sin mandar peticiones gets creo al pedo
            //if (rol.equalsIgnoreCase("proponente")) {
            //    user = Sistema.buscoProponente(usuario);
            //} else if (rol.equalsIgnoreCase("colaborador")) {
            //    user = Sistema.buscoColaborador(usuario);
            //}
            
            user = Sistema.buscoUsuario(usuario);
            
            // Guardamos el usuario en sesion
            sesion.setAttribute("usuarioLogueado", usuario);
            sesion.setAttribute("usuarioObjeto", user);
            sesion.setAttribute("rol", rol);

            //ACA GUARDO EL USUARIO EN SESION PARA PRUEBAS.
            usuario usuarioEnSesion = Sistema.buscoUsuario(usuario);
            sesion.setAttribute("usuarioEnSesion", usuarioEnSesion);
            
            // Redirige al home
            response.sendRedirect("home.jsp");
        } else {
            // Mandamos un mensaje de error
            request.setAttribute("mensaje", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("inicioSesion.jsp").forward(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
