package co.edu.uco.imexcol.negocio.dominio;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class EventoRastreoDominio extends Dominio {

    private EnvioDominio envio;
    private LocalDate fecha;
    private String ubicacion;
    private String descripcion;
    private String estado;

    public EventoRastreoDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setEnvio(new EnvioDominio());
        setFecha(UtilFecha.obtenerFechaPorDefecto());
        setUbicacion(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setEstado(UtilTexto.VACIO);
    }

    public EventoRastreoDominio(final UUID id) {
        super(id);
        setEnvio(new EnvioDominio());
        setFecha(UtilFecha.obtenerFechaPorDefecto());
        setUbicacion(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setEstado(UtilTexto.VACIO);
    }

    public EventoRastreoDominio(final UUID id, final EnvioDominio envio, final LocalDate fecha,
                                final String ubicacion, final String descripcion, final String estado) {
        super(id);
        setEnvio(envio);
        setFecha(fecha);
        setUbicacion(ubicacion);
        setDescripcion(descripcion);
        setEstado(estado);
    }

    public EnvioDominio getEnvio() {
        return envio;
    }

    public void setEnvio(final EnvioDominio envio) {
        this.envio = UtilObjeto.obtenerValorDefecto(envio, new EnvioDominio());
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(final LocalDate fecha) {
        this.fecha = UtilFecha.obtenerValorDefecto(fecha);
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(final String ubicacion) {
        this.ubicacion = UtilTexto.aplicarTrim(ubicacion);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = UtilTexto.aplicarTrim(descripcion);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(final String estado) {
        this.estado = UtilTexto.aplicarTrim(estado);
    }
}
