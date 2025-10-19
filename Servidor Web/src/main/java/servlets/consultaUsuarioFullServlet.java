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
@WebServlet(name = "consultaUsuarioFullServlet", urlPatterns = {"/consultaUsuarioFullServlet"})
public class consultaUsuarioFullServlet extends HttpServlet {

        ControladoraNueva Sistema = new ControladoraNueva();

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);

        String nicknamePedido = request.getParameter("nickname"); //
        HttpSession session = request.getSession();
        
        // nickname del usuario logueado real
        String usuarioSesion = (String) session.getAttribute("usuarioSesion");

        // si vino por parametro, lo guardamos en la sesion
        if (nicknamePedido != null && !nicknamePedido.isEmpty()) {
            session.setAttribute("nicknamePedido", nicknamePedido);
            request.setAttribute("usuario", Sistema.buscoUsuario(nicknamePedido));
        } else {
            // si no vino, lo tomamos desde la sesion
            nicknamePedido = (String) session.getAttribute("nicknamePedido");
        }

        String rol = Sistema.buscoRol(nicknamePedido);
        
        //if(rol.equalsIgnoreCase("proponente")){
        //usuario = ProponenteDTO.fromEntity(Sistema.buscoProponente(nicknamePedido));
        //}else if(rol.equalsIgnoreCase("colaborador")){
        //usuario = ColaboradorDTO.fromEntity(Sistema.buscoColaborador(nicknamePedido));
        //}
        
        usuario usuarioEntidad = Sistema.buscoUsuario(nicknamePedido);
        UsuarioDTO usuario = UsuarioDTO.fromEntity(usuarioEntidad);
        
        boolean esPropioPerfil = nicknamePedido.equalsIgnoreCase(usuarioSesion);
        request.setAttribute("esPropioPerfil", esPropioPerfil);
        
        //si consulta su propio perfil. aca en medio porq si.
        if(nicknamePedido.equalsIgnoreCase(usuarioSesion) && rol.equalsIgnoreCase("proponente")){
        //listar ingresadas.
        List<PropuestaDTO> listaIngresadas = Sistema.listarPropuestasIngresadas(nicknamePedido);
        request.setAttribute("listaPropuestasIng", listaIngresadas); //FALTA EN JSP
        }
        if(nicknamePedido.equalsIgnoreCase(usuarioSesion) && rol.equalsIgnoreCase("colaborador")){
        //ver el monto y fecha de las colaboraciones que realizo.
        }
        
        //me quede aca. fui a agregar favorita si o no a las propuestas.
        List<PropuestaDTO> listaPropuestasFav = Sistema.listarPropuestasFavoritas(nicknamePedido);

        List<usuario> listaSeguidos = Sistema.buscarSeguidos(usuario.getNickname());
        List<usuario> listaSeguidores = Sistema.buscarSeguidores(usuario.getNickname());
        
        if(rol.equalsIgnoreCase("proponente")){
        //si es prop lista de PUBLICADA.
        List<PropuestaDTO> listaPropuestasPub = Sistema.listarPropuestasPublicadas(nicknamePedido);
        request.setAttribute("listaPropuestasPub", listaPropuestasPub);
        }else if (rol.equalsIgnoreCase("colaborador")){
        //si es colab lista de prop que colaboro.
        ColaboradorDTO colaborador = ColaboradorDTO.fromEntity(Sistema.buscoColaborador(nicknamePedido));
        List<ColaboracionDTO> colaboraciones = colaborador.getColaboraciones();
            request.setAttribute("colaboraciones", colaboraciones);
        }
        
        if (usuario.getImagen() != null) {
            String base64 = java.util.Base64.getEncoder().encodeToString(usuario.getImagen());
            request.setAttribute("imagenBase64", base64);
        }

        request.setAttribute("usuario", usuario);
        request.setAttribute("listaSeguidos", listaSeguidos);
        request.setAttribute("listaSeguidores", listaSeguidores);
        request.setAttribute("listaPropuestasFav", listaPropuestasFav);
        request.getRequestDispatcher("detalleUsuario.jsp").forward(request, response);
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
