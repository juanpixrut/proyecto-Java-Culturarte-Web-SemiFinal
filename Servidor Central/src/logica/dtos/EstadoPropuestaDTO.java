/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package logica.dtos;

/**
 *
 * @author Juanpi
 */

public enum EstadoPropuestaDTO {
    INGRESADA,
    PUBLICADA,
    EN_FINANCIACION,
    FINANCIADA,
    NO_FINANCIADA,
    CANCELADA;

    // Método auxiliar para convertir desde la entidad
    public static EstadoPropuestaDTO fromEntity(logica.estadoPropuesta estado) {
        if (estado == null) return null;
        return EstadoPropuestaDTO.valueOf(estado.name());
    }

    // Método auxiliar para volver al enum original si lo necesitás
    public logica.estadoPropuesta toEntity() {
        return logica.estadoPropuesta.valueOf(this.name());
    }
}

