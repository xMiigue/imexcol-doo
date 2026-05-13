package co.edu.uco.imexcol.entidad;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class EventoRastreoEntidad {

    private UUID id;
    private EnvioEntidad envio;
    private LocalDate fecha;
    private String ubicacion;
    private String descripcion;
    private String estado;

    public EventoRastreoEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setEnvio(new EnvioEntidad());
        setFecha(UtilFecha.obtenerFechaPorDefecto());
        setUbicacion(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setEstado(UtilTexto.VACIO);
    }

    public EventoRastreoEntidad(final UUID id) {
        setId(id);
        setEnvio(new EnvioEntidad());
        setFecha(UtilFecha.obtenerFechaPorDefecto());
        setUbicacion(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setEstado(UtilTexto.VACIO);
    }

    public EventoRastreoEntidad(final UUID id, final EnvioEntidad envio, final LocalDate fecha,
                                final String ubicacion, final String descripcion, final String estado) {
        setId(id);
        setEnvio(envio);
        setFecha(fecha);
        setUbicacion(ubicacion);
        setDescripcion(descripcion);
        setEstado(estado);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public EnvioEntidad getEnvio() {
        return envio;
    }

    public void setEnvio(final EnvioEntidad envio) {
        this.envio = UtilObjeto.obtenerValorDefecto(envio, new EnvioEntidad());
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
