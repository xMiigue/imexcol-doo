package co.edu.uco.imexcol.dto;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class EnvioDTO {

    private UUID id;
    private PedidoDTO pedido;
    private LocalDate fechaEnvio;
    private LocalDate fechaEntrega;
    private String transportadora;
    private String numeroGuia;
    private String estado;

    public EnvioDTO() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setPedido(new PedidoDTO());
        setFechaEnvio(UtilFecha.obtenerFechaPorDefecto());
        setFechaEntrega(UtilFecha.obtenerFechaPorDefecto());
        setTransportadora(UtilTexto.VACIO);
        setNumeroGuia(UtilTexto.VACIO);
        setEstado(UtilTexto.VACIO);
    }

    public EnvioDTO(final UUID id) {
        setId(id);
        setPedido(new PedidoDTO());
        setFechaEnvio(UtilFecha.obtenerFechaPorDefecto());
        setFechaEntrega(UtilFecha.obtenerFechaPorDefecto());
        setTransportadora(UtilTexto.VACIO);
        setNumeroGuia(UtilTexto.VACIO);
        setEstado(UtilTexto.VACIO);
    }

    public EnvioDTO(final UUID id, final PedidoDTO pedido, final LocalDate fechaEnvio, final LocalDate fechaEntrega,
                    final String transportadora, final String numeroGuia, final String estado) {
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

    public PedidoDTO getPedido() {
        return pedido;
    }

    public void setPedido(final PedidoDTO pedido) {
        this.pedido = UtilObjeto.obtenerValorDefecto(pedido, new PedidoDTO());
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
