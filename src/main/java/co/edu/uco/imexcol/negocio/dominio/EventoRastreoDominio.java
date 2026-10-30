package co.edu.uco.imexcol.negocio.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class EventoRastreoDominio extends Dominio {

    private EnvioDominio envio;
    private String estadoLogistico;
    private LocalDateTime fechaHora;
    private String observacion;

    public EventoRastreoDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setEnvio(new EnvioDominio());
        setEstadoLogistico(UtilTexto.VACIO);
        setFechaHora(UtilFecha.obtenerFechaHoraPorDefecto());
        setObservacion(UtilTexto.VACIO);
    }

    public EventoRastreoDominio(final UUID id) {
        super(id);
        setEnvio(new EnvioDominio());
        setEstadoLogistico(UtilTexto.VACIO);
        setFechaHora(UtilFecha.obtenerFechaHoraPorDefecto());
        setObservacion(UtilTexto.VACIO);
    }

    public EventoRastreoDominio(final UUID id, final EnvioDominio envio, final String estadoLogistico,
                                final LocalDateTime fechaHora, final String observacion) {
        super(id);
        setEnvio(envio);
        setEstadoLogistico(estadoLogistico);
        setFechaHora(fechaHora);
        setObservacion(observacion);
    }

    public EnvioDominio getEnvio() {
        return envio;
    }

    public void setEnvio(final EnvioDominio envio) {
        this.envio = UtilObjeto.obtenerValorDefecto(envio, new EnvioDominio());
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
