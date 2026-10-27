package co.edu.uco.imexcol.dto;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ResultadoCompraDTO {

    private UUID idPedido;
    private String numeroFactura;
    private double total;
    private String estadoPedido;

    public ResultadoCompraDTO() {
        setIdPedido(UtilUUID.obtenerValorPorDefecto());
        setNumeroFactura(UtilTexto.VACIO);
        setTotal(UtilNumerico.obtenerValorPorDefectoDouble());
        setEstadoPedido(UtilTexto.VACIO);
    }

    public ResultadoCompraDTO(final UUID idPedido, final String numeroFactura, final double total,
                              final String estadoPedido) {
        setIdPedido(idPedido);
        setNumeroFactura(numeroFactura);
        setTotal(total);
        setEstadoPedido(estadoPedido);
    }

    public UUID getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(final UUID idPedido) {
        this.idPedido = UtilUUID.obtenerValorDefecto(idPedido);
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(final String numeroFactura) {
        this.numeroFactura = UtilTexto.aplicarTrim(numeroFactura);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(final double total) {
        this.total = total;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(final String estadoPedido) {
        this.estadoPedido = UtilTexto.aplicarTrim(estadoPedido);
    }
}
