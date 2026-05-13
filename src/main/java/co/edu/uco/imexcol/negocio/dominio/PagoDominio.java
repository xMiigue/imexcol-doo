package co.edu.uco.imexcol.negocio.dominio;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class PagoDominio extends Dominio {

    private PedidoDominio pedido;
    private MetodoPagoDominio metodoPago;
    private double monto;
    private LocalDate fechaPago;
    private String estado;

    public PagoDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setPedido(new PedidoDominio());
        setMetodoPago(new MetodoPagoDominio());
        setMonto(UtilNumerico.obtenerValorPorDefectoDouble());
        setFechaPago(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilTexto.VACIO);
    }

    public PagoDominio(final UUID id) {
        super(id);
        setPedido(new PedidoDominio());
        setMetodoPago(new MetodoPagoDominio());
        setMonto(UtilNumerico.obtenerValorPorDefectoDouble());
        setFechaPago(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilTexto.VACIO);
    }

    public PagoDominio(final UUID id, final PedidoDominio pedido, final MetodoPagoDominio metodoPago,
                       final double monto, final LocalDate fechaPago, final String estado) {
        super(id);
        setPedido(pedido);
        setMetodoPago(metodoPago);
        setMonto(monto);
        setFechaPago(fechaPago);
        setEstado(estado);
    }

    public PedidoDominio getPedido() {
        return pedido;
    }

    public void setPedido(final PedidoDominio pedido) {
        this.pedido = UtilObjeto.obtenerValorDefecto(pedido, new PedidoDominio());
    }

    public MetodoPagoDominio getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(final MetodoPagoDominio metodoPago) {
        this.metodoPago = UtilObjeto.obtenerValorDefecto(metodoPago, new MetodoPagoDominio());
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
