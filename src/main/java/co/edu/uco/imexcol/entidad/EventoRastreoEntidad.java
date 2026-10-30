package co.edu.uco.imexcol.entidad;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class EventoRastreoEntidad {

    private UUID id;
    private EnvioEntidad envio;
    private String estadoLogistico;
    private LocalDateTime fechaHora;
    private String observacion;

    public EventoRastreoEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setEnvio(new EnvioEntidad());
        setEstadoLogistico(UtilTexto.VACIO);
        setFechaHora(UtilFecha.obtenerFechaHoraPorDefecto());
        setObservacion(UtilTexto.VACIO);
    }

    public EventoRastreoEntidad(final UUID id) {
        setId(id);
        setEnvio(new EnvioEntidad());
        setEstadoLogistico(UtilTexto.VACIO);
        setFechaHora(UtilFecha.obtenerFechaHoraPorDefecto());
        setObservacion(UtilTexto.VACIO);
    }

    public EventoRastreoEntidad(final UUID id, final EnvioEntidad envio, final String estadoLogistico,
                                final LocalDateTime fechaHora, final String observacion) {
        setId(id);
        setEnvio(envio);
        setEstadoLogistico(estadoLogistico);
        setFechaHora(fechaHora);
        setObservacion(observacion);
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

    public String getEstadoLogistico() {
        return estadoLogistico;
    }

    public void setEstadoLogistico(final String estadoLogistico) {
        this.estadoLogistico = UtilTexto.aplicarTrim(estadoLogistico);
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(final LocalDateTime fechaHora) {
        this.fechaHora = UtilFecha.obtenerValorDefecto(fechaHora);
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(final String observacion) {
        this.observacion = UtilTexto.aplicarTrim(observacion);
    }
}
