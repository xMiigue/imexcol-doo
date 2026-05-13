package co.edu.uco.imexcol.entidad;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class EnvioEntidad {

    private UUID id;
    private PedidoEntidad pedido;
    private LocalDate fechaEnvio;
    private LocalDate fechaEntrega;
    private String transportadora;
    private String numeroGuia;
    private String estado;

    public EnvioEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setPedido(new PedidoEntidad());
        setFechaEnvio(UtilFecha.obtenerFechaPorDefecto());
        setFechaEntrega(UtilFecha.obtenerFechaPorDefecto());
        setTransportadora(UtilTexto.VACIO);
        setNumeroGuia(UtilTexto.VACIO);
        setEstado(UtilTexto.VACIO);
    }

    public EnvioEntidad(final UUID id) {
        setId(id);
        setPedido(new PedidoEntidad());
        setFechaEnvio(UtilFecha.obtenerFechaPorDefecto());
        setFechaEntrega(UtilFecha.obtenerFechaPorDefecto());
        setTransportadora(UtilTexto.VACIO);
        setNumeroGuia(UtilTexto.VACIO);
        setEstado(UtilTexto.VACIO);
    }

    public EnvioEntidad(final UUID id, final PedidoEntidad pedido, final LocalDate fechaEnvio,
                        final LocalDate fechaEntrega, final String transportadora, final String numeroGuia,
                        final String estado) {
        setId(id);
        setPedido(pedido);
        setFechaEnvio(fechaEnvio);
        setFechaEntrega(fechaEntrega);
        setTransportadora(transportadora);
        setNumeroGuia(numeroGuia);
        setEstado(estado);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public PedidoEntidad getPedido() {
        return pedido;
    }

    public void setPedido(final PedidoEntidad pedido) {
        this.pedido = UtilObjeto.obtenerValorDefecto(pedido, new PedidoEntidad());
    }

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(final LocalDate fechaEnvio) {
        this.fechaEnvio = UtilFecha.obtenerValorDefecto(fechaEnvio);
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(final LocalDate fechaEntrega) {
        this.fechaEntrega = UtilFecha.obtenerValorDefecto(fechaEntrega);
    }

    public String getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(final String transportadora) {
        this.transportadora = UtilTexto.aplicarTrim(transportadora);
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(final String numeroGuia) {
        this.numeroGuia = UtilTexto.aplicarTrim(numeroGuia);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(final String estado) {
        this.estado = UtilTexto.aplicarTrim(estado);
    }
}
