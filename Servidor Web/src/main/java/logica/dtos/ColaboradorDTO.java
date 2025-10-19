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
import logica.colaborador;
import logica.colaboracion;

import logica.*;
import persistencia.*;

public class ColaboradorDTO extends UsuarioDTO implements Serializable {

    private List<ColaboracionDTO> colaboraciones;

    public ColaboradorDTO() {
        this.colaboraciones = new ArrayList<>();
    }

    public ColaboradorDTO(String nickname, String nombre, String apellido, String correo) {
        super(nickname, nombre, apellido, correo);
        this.colaboraciones = new ArrayList<>();
    }

    public List<ColaboracionDTO> getColaboraciones() { return colaboraciones; }
    public void setColaboraciones(List<ColaboracionDTO> colaboraciones) { this.colaboraciones = colaboraciones; }

    public static ColaboradorDTO fromEntity(colaborador c) {
        if (c == null) return null;

        ColaboradorDTO dto = new ColaboradorDTO(
                c.getNickname(),
                c.getNombre(),
                c.getApellido(),
                c.getEmail()
        );

        try {
            ControladoraNueva Sistema = new ControladoraNueva();
            List<colaboracion> todas = Sistema.listarColaboraciones();
            List<ColaboracionDTO> colaboracionesDTO = new ArrayList<>();

            for (int i = 0; i < todas.size(); i++) {
                colaboracion colab = todas.get(i);
                if (colab.getColaborador() != null &&
                    colab.getColaborador().getNickname().equalsIgnoreCase(c.getNickname())) {

                    colaboracionesDTO.add(ColaboracionDTO.fromEntity(colab));
                }
            }

            dto.setColaboraciones(colaboracionesDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    @Override
    public String toString() {
        return "ColaboradorDTO{" +
                "nickname='" + getNickname() + '\'' +
                ", colaboraciones=" + (colaboraciones != null ? colaboraciones.size() : 0) +
                '}';
    }
}
