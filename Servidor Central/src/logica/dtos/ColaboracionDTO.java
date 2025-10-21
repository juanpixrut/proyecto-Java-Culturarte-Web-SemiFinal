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
import java.text.SimpleDateFormat;
import java.util.Date;
import logica.colaboracion;

public class ColaboracionDTO implements Serializable {

    private int id;
    private String colaboradorNickname;
    private String propuestaTitulo;
    private float montoAportado;
    private Date fecha;
    private String tipoRetorno;

    // ----- Constructores -----
    public ColaboracionDTO() {}

    public ColaboracionDTO(int id, String colaboradorNickname, String propuestaTitulo,
                           float montoAportado, Date fecha, String tipoRetorno) {
        this.id = id;
        this.colaboradorNickname = colaboradorNickname;
        this.propuestaTitulo = propuestaTitulo;
        this.montoAportado = montoAportado;
        this.fecha = fecha;
        this.tipoRetorno = tipoRetorno;
    }

    // ----- Getters y Setters -----
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getColaboradorNickname() { return colaboradorNickname; }
    public void setColaboradorNickname(String colaboradorNickname) { this.colaboradorNickname = colaboradorNickname; }

    public String getPropuestaTitulo() { return propuestaTitulo; }
    public void setPropuestaTitulo(String propuestaTitulo) { this.propuestaTitulo = propuestaTitulo; }

    public float getMontoAportado() { return montoAportado; }
    public void setMontoAportado(float montoAportado) { this.montoAportado = montoAportado; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getTipoRetorno() { return tipoRetorno; }
    public void setTipoRetorno(String tipoRetorno) { this.tipoRetorno = tipoRetorno; }

    // ----- Conversión desde entidad -----
    public static ColaboracionDTO fromEntity(colaboracion c) {
        if (c == null) return null;

        ColaboracionDTO dto = new ColaboracionDTO();
        dto.setId(c.getId());
        dto.setColaboradorNickname(c.getColaborador() != null ? c.getColaborador().getNickname() : null);
        dto.setPropuestaTitulo(c.getPropuesta() != null ? c.getPropuesta().getTitulo() : null);
        dto.setMontoAportado(c.getMontoAportado());
        dto.setFecha(c.getFecha());
        dto.setTipoRetorno(c.getTipoRetorno());
        return dto;
    }

    public String getFechaFormateada() {
        if (fecha == null) {
            return "";
        }
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(fecha);
    }

    @Override
    public String toString() {
        return "ColaboracionDTO{" +
                "id=" + id +
                ", colaborador='" + colaboradorNickname + '\'' +
                ", propuesta='" + propuestaTitulo + '\'' +
                ", monto=" + montoAportado +
                ", tipoRetorno='" + tipoRetorno + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}


