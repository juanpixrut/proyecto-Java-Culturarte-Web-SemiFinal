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
import java.util.List;
import logica.proponente;
import logica.propuesta;

import logica.*;
import persistencia.*;

public class ProponenteDTO extends UsuarioDTO implements Serializable {

    private String direccion;
    private String biografia;
    private String linkSitio;
    private List<PropuestaDTO> propuestas;

    // ----- Constructores -----
    public ProponenteDTO() {
        super();
        this.propuestas = new ArrayList<>();
    }

    public ProponenteDTO(String nickname, String nombre, String apellido, String correo, byte[] imagen,
                         String direccion, String biografia, String linkSitio) {
        super(nickname, nombre, apellido, correo);
        this.direccion = direccion;
        this.biografia = biografia;
        this.linkSitio = linkSitio;
        this.propuestas = new ArrayList<>();
    }

    // ----- Getters y Setters -----
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }

    public String getLinkSitio() { return linkSitio; }
    public void setLinkSitio(String linkSitio) { this.linkSitio = linkSitio; }

    public List<PropuestaDTO> getPropuestas() { return propuestas; }
    public void setPropuestas(List<PropuestaDTO> propuestas) { this.propuestas = propuestas; }

    // ----- Conversión desde entidad -----
    public static ProponenteDTO fromEntity(proponente p) {
        if (p == null) return null;

        ProponenteDTO dto = new ProponenteDTO(
                p.getNickname(),
                p.getNombre(),
                p.getApellido(),
                p.getEmail(),
                null,
                p.getDireccion(),
                p.getBiografia(),
                p.getLink()
        );

        try {
            ControladoraNueva Sistema = new ControladoraNueva();
            List<propuesta> todas = Sistema.listarPropuestas();
            List<PropuestaDTO> propuestasDTO = new ArrayList<>();

            for (int i = 0; i < todas.size(); i++) {
                propuesta prop = todas.get(i);
                if (prop.getProponente().equalsIgnoreCase(p.getNickname())) {
                    propuestasDTO.add(PropuestaDTO.fromEntity(prop));
                }
            }

            dto.setPropuestas(propuestasDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }


    @Override
    public String toString() {
        return "ProponenteDTO{" +
                "nickname='" + getNickname() + '\'' +
                ", direccion='" + direccion + '\'' +
                ", linkSitio='" + linkSitio + '\'' +
                ", propuestas=" + (propuestas != null ? propuestas.size() : 0) +
                '}';
    }
}

