package co.edu.uco.imexcol.negocio.dominio;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class FacturaDominio extends Dominio {

    private PedidoDominio pedido;
    private String numeroFactura;
    private LocalDate fechaEmision;
    private double total;

    public FacturaDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setPedido(new PedidoDominio());
        setNumeroFactura(UtilTexto.VACIO);
        setFechaEmision(UtilFecha.obtenerFechaPorDefecto());
        setTotal(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public FacturaDominio(final UUID id) {
        super(id);
        setPedido(new PedidoDominio());
        setNumeroFactura(UtilTexto.VACIO);
        setFechaEmision(UtilFecha.obtenerFechaPorDefecto());
        setTotal(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public FacturaDominio(final UUID id, final PedidoDominio pedido, final String numeroFactura,
                          final LocalDate fechaEmision, final double total) {
        super(id);
        setPedido(pedido);
        setNumeroFactura(numeroFactura);
        setFechaEmision(fechaEmision);
        setTotal(total);
    }

    public PedidoDominio getPedido() {
        return pedido;
    }

    public void setPedido(final PedidoDominio pedido) {
        this.pedido = UtilObjeto.obtenerValorDefecto(pedido, new PedidoDominio());
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(final String numeroFactura) {
        this.numeroFactura = UtilTexto.aplicarTrim(numeroFactura);
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(final LocalDate fechaEmision) {
        this.fechaEmision = UtilFecha.obtenerValorDefecto(fechaEmision);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(final double total) {
        this.total = total;
    }
}
