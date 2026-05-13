package co.edu.uco.imexcol.negocio.dominio;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class CategoriaDominio extends Dominio {

    private String nombre;
    private String descripcion;
    private boolean estado;

    public CategoriaDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setNombre(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public CategoriaDominio(final UUID id) {
        super(id);
        setNombre(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public CategoriaDominio(final UUID id, final String nombre, final String descripcion, final boolean estado) {
        super(id);
        setNombre(nombre);
        setDescripcion(descripcion);
        setEstado(estado);
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
