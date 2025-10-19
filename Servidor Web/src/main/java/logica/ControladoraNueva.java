/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

/**
 *
 * @author Juanpi
 */

import java.util.ArrayList;
import java.util.List;
import logica.*;
import persistencia.*;
import logica.dtos.*;

public class ControladoraNueva extends logica.ictrl {
    
    private ControladoraPersistencia controlP = new ControladoraPersistencia();

    //mia
    public boolean validarUsuario(String usuario, String clave) {
        boolean valido = false;
        for (proponente p : this.listarProponentes()) {
            if (p.getNickname().equalsIgnoreCase(usuario)) {
                if(p.getContrasena().equalsIgnoreCase(clave)){
                valido = true;
                break;
                }
            }
        }
        
        for (colaborador c : this.listarColaboradores()) {
            if (c.getNickname().equalsIgnoreCase(usuario)) {
                if (c.getNickname().equalsIgnoreCase(usuario)) {
                if(c.getContrasena().equalsIgnoreCase(clave)){
                valido = true;
                break;
                }
            }
            }
        }
        return valido;
    }
    
    //mia
     public String buscoRol(String usuario){
         String rol = null;
         for (proponente p : this.listarProponentes()) {
            if (p.getNickname().equalsIgnoreCase(usuario)) {
                rol = "proponente";
                break;
            }
        }
        for (colaborador c : this.listarColaboradores()) {
            if (c.getNickname().equalsIgnoreCase(usuario)) {
                rol = "colaborador";
                break;
            }
        }
        return rol;
     }
     
     //solo admin
     public void evaluarPropuesta(){
     //se listan las propuestas registradas en la bd con estado = ingresada.
     
     //se selecciona una y se determina si se publica o se cancela pasando su estado a publicada o cancelada.
     
     //se registra la fecha del cambio.
     }
     
     //solo admin
     public void altaPerfil(){
     
     }
     
    public List<PropuestaDTO> listarPropuestasDTO() {
        List<propuesta> entidades = this.listarPropuestas();
        List<PropuestaDTO> dtos = new ArrayList<>();

        for (propuesta p : entidades) {
            dtos.add(new PropuestaDTO(
                    p.getProponente(),
                    p.getTitulo(),
                    p.getDescripcion(),
                    p.getTipo(),
                    p.getLugar(),
                    p.getFecha(),
                    p.getHora(),
                    p.getEntrada(),
                    p.getMonto(),
                    p.getRecaudado(),
                    p.getFechaPublicada(), 
                    EstadoPropuestaDTO.fromEntity(p.getEstadoActual()),
                    p.getTipoRetorno()
            ));
        }
        return dtos;
     }
    
    public List<PropuestaDTO> listarPropuestasNoIngresadas() {
        List<PropuestaDTO> dtos = this.listarPropuestasDTO();
        List<PropuestaDTO> resultado = new ArrayList<>();
        for (PropuestaDTO p : dtos) {
            String estado = p.getEstadoActual().toString();
            if (!estado.equalsIgnoreCase("INGRESADA")) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    
    public PropuestaDTO buscoPropuestaDTO(String titulo){
    propuesta prop = this.buscoPropuesta(titulo);
    PropuestaDTO propDTO = PropuestaDTO.fromEntity(prop);
    return propDTO;
    }
    
    public List<UsuarioDTO> listarUsuariosDTO(){
        List<usuario> entidades = this.listarUsuarios();
        List<UsuarioDTO> dtos = new ArrayList<>();
        for(usuario u : entidades){
        dtos.add(UsuarioDTO.fromEntity(u));
        }
        return dtos;
    }

    public List<ProponenteDTO> listarProponentesDTO() {
        List<proponente> entidades = this.listarProponentes();
        List<ProponenteDTO> dtos = new ArrayList<>();
        for (proponente p : entidades) {
            dtos.add(ProponenteDTO.fromEntity(p));
        }
        return dtos;
    }

    public List<ColaboradorDTO> listarColaboradoresDTO() {
        List<colaborador> entidades = this.listarColaboradores();
        List<ColaboradorDTO> dtos = new ArrayList<>();
        for (colaborador c : entidades) {
            dtos.add(ColaboradorDTO.fromEntity(c));
        }
        return dtos;
    }
    
    public List<PropuestaDTO> listarPropuestasFavoritas(String nickname) {
        String rol = buscoRol(nickname);
        usuario u = null;
        if(rol.equalsIgnoreCase("proponente")){
        proponente prop = this.buscoProponente(nickname);
        u = prop;
        }else if(rol.equalsIgnoreCase("colaborador")){
        colaborador col = this.buscoColaborador(nickname);
        u = col;
        }
        
        List<PropuestaDTO> resultado = new ArrayList<>();

        if (u != null && u.getFavoritas() != null) {
            for (propuesta p : u.getFavoritas()) {
                resultado.add(PropuestaDTO.fromEntity(p));
            }
        }

        return resultado;
    }
            
    public List<PropuestaDTO> listarPropuestasPublicadas(String nickname) {
        String rol = buscoRol(nickname);
        usuario u = null;
        if(rol.equalsIgnoreCase("proponente")){
        proponente prop = this.buscoProponente(nickname);
        u = prop;
        }else if(rol.equalsIgnoreCase("colaborador")){
        colaborador col = this.buscoColaborador(nickname);
        u = col;
        }
        
        List<PropuestaDTO> dtos = this.listarPropuestasDTO();
        List<PropuestaDTO> resultado = new ArrayList<>();

        if (u != null) {
            for (PropuestaDTO p : dtos) {
                String estado = p.getEstadoActual().toString();
                if (estado.equalsIgnoreCase("PUBLICADA") && p.getProp().equalsIgnoreCase(nickname)) {
                    resultado.add(p);
                }
            }
        }

        return resultado;
    }
    
        public List<PropuestaDTO> listarPropuestasIngresadas(String nickname) {
        List<PropuestaDTO> dtos = this.listarPropuestasDTO();
        List<PropuestaDTO> pub = new ArrayList<>();
        for (PropuestaDTO p : dtos) {
            String estado = p.getEstadoActual().toString();
            if (estado.equalsIgnoreCase("INGRESADA") && p.getProp().equalsIgnoreCase(nickname)) {
                pub.add(p);
            }
        }
        return pub;
    }

    public List<PropuestaDTO> listarPropuestasFinanciadas(String nickname) {
        List<PropuestaDTO> dtos = this.listarPropuestasDTO();
        List<PropuestaDTO> financiadas = new ArrayList<>();
        for (PropuestaDTO p : dtos) {
            String estado = p.getEstadoActual().toString();
            if (estado.equalsIgnoreCase("FINANCIADA") && p.getProp().equalsIgnoreCase(nickname)) {
                financiadas.add(p);
            }
        }
        return financiadas;
    }

    public usuario buscoUsuario(String nickname){
            colaborador col = this.buscoColaborador(nickname);
            proponente prop = this.buscoProponente(nickname);
            usuario usuario = null;
            if(col != null){
            usuario = col;
            }else if(prop != null){
            usuario = prop;
          }
            return usuario;
       }

    public usuario buscoUsuario2(String nickname) {
    return controlP.buscarUsuarioConRelaciones(nickname);
}
    
    public void cancelarPropuesta(String titulo){
    //la busco.
    propuesta prop = this.buscoPropuesta(titulo);
    prop.setEstado(estadoPropuesta.CANCELADA);
    prop.registrarCambioEstado(estadoPropuesta.CANCELADA);
    this.modificoPropuesta(prop);
    }

    public boolean usuarioSigueA(usuario usuarioSesion, String nickname) {
        boolean loSigue = false;
        List<usuario> seguidos = usuarioSesion.getSeguidos();
        for (usuario u : seguidos) {
            if (u.getNickname().equalsIgnoreCase(nickname)) {
                loSigue = true;
            }
        }
        return loSigue;
    }
    
public List<usuario> buscarSeguidos(String nickname) {
    usuario u = controlP.buscarUsuarioConRelaciones(nickname); // trae seguidos y seguidores con JOIN FETCH
    if (u == null) {
        throw new IllegalArgumentException("El usuario no existe: " + nickname);
    }

    // Forzar carga (por si es Lazy)
    if (u.getSeguidos() != null) {
        u.getSeguidos().size();
    }

    return new ArrayList<>(u.getSeguidos());
}

public List<usuario> buscarSeguidores(String nickname) {
    usuario u = controlP.buscarUsuarioConRelaciones(nickname); // mismo método
    if (u == null) {
        throw new IllegalArgumentException("El usuario no existe: " + nickname);
    }

    if (u.getSeguidores() != null) {
        u.getSeguidores().size();
    }

    return new ArrayList<>(u.getSeguidores());
}



     
}
