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
import logica.categoria;

import logica.*;
import persistencia.*;

public class CategoriaDTO implements Serializable {

    private int id;
    private String nombre;
    private Integer padreId;
    private List<CategoriaDTO> hijos;

    public CategoriaDTO() {
        this.hijos = new ArrayList<>();
    }

    public CategoriaDTO(int id, String nombre, Integer padreId) {
        this.id = id;
        this.nombre = nombre;
        this.padreId = padreId;
        this.hijos = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getPadreId() { return padreId; }
    public void setPadreId(Integer padreId) { this.padreId = padreId; }

    public List<CategoriaDTO> getHijos() { return hijos; }
    public void setHijos(List<CategoriaDTO> hijos) { this.hijos = hijos; }

    // ----- Conversión desde entidad -----
    public static CategoriaDTO fromEntity(categoria c) {
        if (c == null) return null;

        CategoriaDTO dto = new CategoriaDTO(
            c.getId(),
            c.getNombre(),
            (c.getPadre() != null ? c.getPadre().getId() : null)
        );

        try {
            fabrica fab = new fabrica();
            ictrl ic = fab.getIctrl();
            List<categoria> todas = ic.listarCategoria();
            List<CategoriaDTO> hijosDTO = new ArrayList<>();

            for (int i = 0; i < todas.size(); i++) {
                categoria posibleHijo = todas.get(i);
                // si el padre del hijo coincide con esta categoría
                if (posibleHijo.getPadre() != null && 
                    posibleHijo.getPadre().getId() == c.getId()) {
                    hijosDTO.add(CategoriaDTO.fromEntity(posibleHijo));
                }
            }

            dto.setHijos(hijosDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    @Override
    public String toString() {
        return "CategoriaDTO{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", padreId=" + padreId +
               ", hijos=" + (hijos != null ? hijos.size() : 0) +
               '}';
    }
}
