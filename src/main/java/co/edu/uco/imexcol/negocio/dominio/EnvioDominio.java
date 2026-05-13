package co.edu.uco.imexcol.negocio.dominio;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class EnvioDominio extends Dominio {

    private PedidoDominio pedido;
    private LocalDate fechaEnvio;
    private LocalDate fechaEntrega;
    private String transportadora;
    private String numeroGuia;
    private String estado;

    public EnvioDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setPedido(new PedidoDominio());
        setFechaEnvio(UtilFecha.obtenerFechaPorDefecto());
        setFechaEntrega(UtilFecha.obtenerFechaPorDefecto());
        setTransportadora(UtilTexto.VACIO);
        setNumeroGuia(UtilTexto.VACIO);
        setEstado(UtilTexto.VACIO);
    }

    public EnvioDominio(final UUID id) {
        super(id);
        setPedido(new PedidoDominio());
        setFechaEnvio(UtilFecha.obtenerFechaPorDefecto());
        setFechaEntrega(UtilFecha.obtenerFechaPorDefecto());
        setTransportadora(UtilTexto.VACIO);
        setNumeroGuia(UtilTexto.VACIO);
        setEstado(UtilTexto.VACIO);
    }

    public EnvioDominio(final UUID id, final PedidoDominio pedido, final LocalDate fechaEnvio,
                        final LocalDate fechaEntrega, final String transportadora, final String numeroGuia,
                        final String estado) {
        super(id);
        setPedido(pedido);
        setFechaEnvio(fechaEnvio);
        setFechaEntrega(fechaEntrega);
        setTransportadora(transportadora);
        setNumeroGuia(numeroGuia);
        setEstado(estado);
    }

    public PedidoDominio getPedido() {
        return pedido;
    }

    public void setPedido(final PedidoDominio pedido) {
        this.pedido = UtilObjeto.obtenerValorDefecto(pedido, new PedidoDominio());
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
