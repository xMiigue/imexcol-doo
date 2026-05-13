package co.edu.uco.imexcol.dto;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class PagoDTO {

    private UUID id;
    private PedidoDTO pedido;
    private MetodoPagoDTO metodoPago;
    private double monto;
    private LocalDate fechaPago;
    private String estado;

    public PagoDTO() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setPedido(new PedidoDTO());
        setMetodoPago(new MetodoPagoDTO());
        setMonto(UtilNumerico.obtenerValorPorDefectoDouble());
        setFechaPago(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilTexto.VACIO);
    }

    public PagoDTO(final UUID id) {
        setId(id);
        setPedido(new PedidoDTO());
        setMetodoPago(new MetodoPagoDTO());
        setMonto(UtilNumerico.obtenerValorPorDefectoDouble());
        setFechaPago(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilTexto.VACIO);
    }

    public PagoDTO(final UUID id, final PedidoDTO pedido, final MetodoPagoDTO metodoPago, final double monto,
                   final LocalDate fechaPago, final String estado) {
        setId(id);
        setPedido(pedido);
        setMetodoPago(metodoPago);
        setMonto(monto);
        setFechaPago(fechaPago);
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

    public MetodoPagoDTO getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(final MetodoPagoDTO metodoPago) {
        this.metodoPago = UtilObjeto.obtenerValorDefecto(metodoPago, new MetodoPagoDTO());
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(final double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(final LocalDate fechaPago) {
        this.fechaPago = UtilFecha.obtenerValorDefecto(fechaPago);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(final String estado) {
        this.estado = UtilTexto.aplicarTrim(estado);
    }
}
