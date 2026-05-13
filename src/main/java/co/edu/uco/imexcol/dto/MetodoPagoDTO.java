package co.edu.uco.imexcol.dto;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class MetodoPagoDTO {

    private UUID id;
    private String nombre;
    private String descripcion;
    private boolean estado;

    public MetodoPagoDTO() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setNombre(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public MetodoPagoDTO(final UUID id) {
        setId(id);
        setNombre(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public MetodoPagoDTO(final UUID id, final String nombre, final String descripcion, final boolean estado) {
        setId(id);
        setNombre(nombre);
        setDescripcion(descripcion);
        setEstado(estado);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = UtilTexto.aplicarTrim(nombre);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = UtilTexto.aplicarTrim(descripcion);
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(final boolean estado) {
        this.estado = estado;
    }
}
