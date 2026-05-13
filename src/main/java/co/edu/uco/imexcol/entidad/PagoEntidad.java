package co.edu.uco.imexcol.entidad;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class PagoEntidad {

    private UUID id;
    private PedidoEntidad pedido;
    private MetodoPagoEntidad metodoPago;
    private double monto;
    private LocalDate fechaPago;
    private String estado;

    public PagoEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setPedido(new PedidoEntidad());
        setMetodoPago(new MetodoPagoEntidad());
        setMonto(UtilNumerico.obtenerValorPorDefectoDouble());
        setFechaPago(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilTexto.VACIO);
    }

    public PagoEntidad(final UUID id) {
        setId(id);
        setPedido(new PedidoEntidad());
        setMetodoPago(new MetodoPagoEntidad());
        setMonto(UtilNumerico.obtenerValorPorDefectoDouble());
        setFechaPago(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilTexto.VACIO);
    }

    public PagoEntidad(final UUID id, final PedidoEntidad pedido, final MetodoPagoEntidad metodoPago,
                       final double monto, final LocalDate fechaPago, final String estado) {
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

    public PedidoEntidad getPedido() {
        return pedido;
    }

    public void setPedido(final PedidoEntidad pedido) {
        this.pedido = UtilObjeto.obtenerValorDefecto(pedido, new PedidoEntidad());
    }

    public MetodoPagoEntidad getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(final MetodoPagoEntidad metodoPago) {
        this.metodoPago = UtilObjeto.obtenerValorDefecto(metodoPago, new MetodoPagoEntidad());
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
