/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica.dtos;

/**
 *
 * @author Juanpi
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import logica.usuario;

public class UsuarioDTO implements Serializable {

    private String nickname;
    private String nombre;
    private String apellido;
    private String correo;
    private byte[] imagen;
    private Date fechaNacimiento;

    // relaciones
    private List<UsuarioDTO> seguidos;
    private List<UsuarioDTO> seguidores;

    // ----- Constructores -----
    public UsuarioDTO() {
        this.seguidos = new ArrayList<>();
        this.seguidores = new ArrayList<>();
    }

    public UsuarioDTO(String nickname, String nombre, String apellido, String correo) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }
    
        public UsuarioDTO(String nickname, String nombre, String apellido, String correo, byte[] imagen) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.imagen = imagen;
    }

    // ----- Getters y Setters -----
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public byte[] getImagen() { return imagen; }
    public void setImagen(byte[] imagen) { this.imagen = imagen; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public List<UsuarioDTO> getSeguidos() { return seguidos; }
    public void setSeguidos(List<UsuarioDTO> seguidos) { this.seguidos = seguidos; }

    public List<UsuarioDTO> getSeguidores() { return seguidores; }
    public void setSeguidores(List<UsuarioDTO> seguidores) { this.seguidores = seguidores; }

    // ----- Conversión desde entidad -----
    public static UsuarioDTO fromEntity(usuario u) {
        if (u == null) return null;

        UsuarioDTO dto = new UsuarioDTO(
                u.getNickname(),
                u.getNombre(),
                u.getApellido(),
                u.getEmail(),
                u.getImagen()     
        );

        // Relaciones (sin recursión infinita)
        if (u.getSeguidos() != null) {
            List<UsuarioDTO> seguidosDTO = new ArrayList<>();
            for (usuario seguido : u.getSeguidos()) {
                seguidosDTO.add(new UsuarioDTO(seguido.getNickname(), seguido.getNombre(),
                        seguido.getApellido(), seguido.getEmail()));
            }
            dto.setSeguidos(seguidosDTO);
        }

        if (u.getSeguidores() != null) {
            List<UsuarioDTO> seguidoresDTO = new ArrayList<>();
            for (usuario seguidor : u.getSeguidores()) {
                seguidoresDTO.add(new UsuarioDTO(seguidor.getNickname(), seguidor.getNombre(),
                        seguidor.getApellido(), seguidor.getEmail()));
            }
            dto.setSeguidores(seguidoresDTO);
        }

        return dto;
    }

    @Override
    public String toString() {
        return nickname + " (" + nombre + " " + apellido + ")";
    }
}

