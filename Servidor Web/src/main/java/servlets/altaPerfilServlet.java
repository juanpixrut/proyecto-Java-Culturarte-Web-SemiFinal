/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import logica.*;
import persistencia.*;

/**
 *
 * @author Juanpi
 */

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,    // 1 MB
    maxFileSize = 1024 * 1024 * 5,      // 5 MB por archivo
    maxRequestSize = 1024 * 1024 * 10   // 10 MB total
)

@WebServlet(name = "altaPerfilServlet", urlPatterns = {"/altaPerfilServlet"})
public class altaPerfilServlet extends HttpServlet {
    
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
        //processRequest(request, response);

try {
            // === Campos comunes ===
            String nickname = request.getParameter("nickname");
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String email = request.getParameter("email");
            String tipo = request.getParameter("tipo");
            String contrasena = request.getParameter("contrasena");
            String confirmacion = request.getParameter("confirmacion");

            // === Fecha de nacimiento ===
            String fechaStr = request.getParameter("fechaNacimiento");
            Date fechaNac = null;
            if (fechaStr != null && !fechaStr.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                fechaNac = sdf.parse(fechaStr);
            }

            // === Imagen ===
            Part imagenPart = request.getPart("imagen");
            byte[] imagenBytes = null;
            if (imagenPart != null && imagenPart.getSize() > 0) {
                InputStream inputStream = imagenPart.getInputStream();
                imagenBytes = inputStream.readAllBytes();
            }

            // === Validación de contraseñas ===
            if (contrasena == null || confirmacion == null || !contrasena.equals(confirmacion)) {
                request.setAttribute("mensaje", "❌ Las contraseñas no coinciden.");
                request.getRequestDispatcher("altaPerfil.jsp").forward(request, response);
                return;
            }

            // === Alta según tipo ===
            if ("proponente".equalsIgnoreCase(tipo)) {
                String direccion = request.getParameter("direccion");
                String biografia = request.getParameter("biografia");
                String sitioWeb = request.getParameter("sitioWeb");

                Sistema.altaPerfilProponente(
                        nickname, nombre, apellido, email, imagenBytes,
                        direccion, biografia, sitioWeb, contrasena
                );
                request.setAttribute("mensaje", "✅ Proponente creado correctamente.");
            } else {
                Sistema.altaPerfilColaborador(
                        nickname, nombre, apellido, email, imagenBytes, contrasena
                );
                request.setAttribute("mensaje", "✅ Colaborador creado correctamente.");
            }

            // Redirigir al home
            request.getRequestDispatcher("home.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "❌ Error al crear el perfil: " + e.getMessage());
            request.getRequestDispatcher("altaPerfil.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
