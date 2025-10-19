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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import logica.*;

public class PropuestaDTO implements Serializable {

    private String proponenteNickname;
    private String titulo;
    private String descripcion;
    private byte[] imagen;
    private String tipoEspectaculo;
    private String lugar;
    private Date fechaRealizacion;
    private float precioEntrada;
    private float montoNecesario;
    private float montoRecaudado;
    private Date fechaPublicada;
    private EstadoPropuestaDTO estadoActual;
    //private List<HistorialEstadoDTO> historial;
    private List<ColaboracionDTO> colaboraciones;
    private String tipoRetorno;
    private LocalTime hora;

    // ----- Constructores -----
    public PropuestaDTO() {
        //historial = new ArrayList<>();
        colaboraciones = new ArrayList<>();
    }

    public PropuestaDTO(String prop, String titulo, String descripcion, String tipoEspectaculo,
                        String lugar, Date fechaRealizacion, LocalTime hora, float precioEntrada, float montoNecesario,
                        float montoRecaudado, Date fechaPublicada, EstadoPropuestaDTO estadoActual,
                        //List<HistorialEstadoDTO> historial, List<ColaboracionDTO> colaboraciones,
                        String tipoRetorno) {
        this.proponenteNickname = prop;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipoEspectaculo = tipoEspectaculo;
        this.lugar = lugar;
        this.fechaRealizacion = fechaRealizacion;
        this.precioEntrada = precioEntrada;
        this.montoNecesario = montoNecesario;
        this.montoRecaudado = montoRecaudado;
        this.fechaPublicada = fechaPublicada;
        this.estadoActual = estadoActual;
        //this.historial = historial != null ? historial : new ArrayList<>();
        this.colaboraciones = colaboraciones != null ? colaboraciones : new ArrayList<>();
        this.tipoRetorno = tipoRetorno;
        this.hora = hora;
    }

    // ----- Getters y Setters -----
    public String getProp() { return proponenteNickname; }
    public void setProponenteNickname(String prop) { this.proponenteNickname = prop; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public byte[] getImagen() { return imagen; }
    public void setImagen(byte[] imagen) { this.imagen = imagen; }

    public String getTipoEspectaculo() { return tipoEspectaculo; }
    public void setTipoEspectaculo(String tipoEspectaculo) { this.tipoEspectaculo = tipoEspectaculo; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public Date getFechaRealizacion() { return fechaRealizacion; }
    public void setFechaRealizacion(Date fechaRealizacion) { this.fechaRealizacion = fechaRealizacion; }

    public float getPrecioEntrada() { return precioEntrada; }
    public void setPrecioEntrada(float precioEntrada) { this.precioEntrada = precioEntrada; }

    public float getMontoNecesario() { return montoNecesario; }
    public void setMontoNecesario(float montoNecesario) { this.montoNecesario = montoNecesario; }

    public float getMontoRecaudado() { return montoRecaudado; }
    public void setMontoRecaudado(float montoRecaudado) { this.montoRecaudado = montoRecaudado; }

    public Date getFechaPublicada() { return fechaPublicada; }
    
    public String getFechaRealizacionFormateada() {
        if (fechaRealizacion == null) {
            return "";
        }
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(fechaRealizacion);
    }

    public void setFechaPublicada(Date fechaPublicada) { this.fechaPublicada = fechaPublicada; }

    public EstadoPropuestaDTO getEstadoActual() { return estadoActual; }
    public void setEstadoActual(EstadoPropuestaDTO estadoActual) { this.estadoActual = estadoActual; }

    //public List<HistorialEstadoDTO> getHistorial() { return historial; }
    //public void setHistorial(List<HistorialEstadoDTO> historial) { this.historial = historial; }

    public List<ColaboracionDTO> getColaboraciones() { return colaboraciones; }
    public void setColaboraciones(List<ColaboracionDTO> colaboraciones) { this.colaboraciones = colaboraciones; }

    public String getTipoRetorno() { return tipoRetorno; }
    public void setTipoRetorno(String tipoRetorno) { this.tipoRetorno = tipoRetorno; }
    
    public void setHora(LocalTime hora) { this.hora = hora; }
    
    public LocalTime getHora() { return hora; }

    // ----- Conversión desde entidad -----
    public static PropuestaDTO fromEntity(propuesta p) {
        if (p == null) return null;

        PropuestaDTO dto = new PropuestaDTO();
        dto.setProponenteNickname(p.getProponente());
        dto.setTitulo(p.getTitulo());
        dto.setDescripcion(p.getDescripcion());
        dto.setLugar(p.getLugar());
        dto.setTipoEspectaculo(p.getTipo());
        dto.setPrecioEntrada(p.getEntrada());
        dto.setMontoNecesario(p.getMonto());
        dto.setMontoRecaudado(p.getRecaudado());
        dto.setFechaRealizacion(p.getFecha());
        dto.setEstadoActual(EstadoPropuestaDTO.valueOf(p.getEstadoActual().name()));
        dto.setTipoRetorno(p.getTipoRetorno());
        dto.setHora(p.getHora());

        // Historial
        if (p.getEstadoActual() != null) {
            //dto.setHistorial(new ArrayList<>()); // completar si usamos historialestado
        }

        // Colaboraciones
        if (p.getColaboraciones() != null && !p.getColaboraciones().isEmpty()) {
            List<ColaboracionDTO> colaboracionesDTO = new ArrayList<>();
            for (colaboracion c : p.getColaboraciones()) {
                colaboracionesDTO.add(ColaboracionDTO.fromEntity(c));
            }
            dto.setColaboraciones(colaboracionesDTO);
        }


        return dto;
    }

    @Override
    public String toString() {
        return "PropuestaDTO{" +
                "titulo='" + titulo + '\'' +
                ", proponente=" + (proponenteNickname != null ? proponenteNickname : "null") +
                ", estado=" + estadoActual +
                '}';
    }
}

