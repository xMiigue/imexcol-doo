package co.edu.uco.imexcol.entidad;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class FacturaEntidad {

    private UUID id;
    private PedidoEntidad pedido;
    private String numeroFactura;
    private LocalDate fechaEmision;
    private double total;

    public FacturaEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setPedido(new PedidoEntidad());
        setNumeroFactura(UtilTexto.VACIO);
        setFechaEmision(UtilFecha.obtenerFechaPorDefecto());
        setTotal(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public FacturaEntidad(final UUID id) {
        setId(id);
        setPedido(new PedidoEntidad());
        setNumeroFactura(UtilTexto.VACIO);
        setFechaEmision(UtilFecha.obtenerFechaPorDefecto());
        setTotal(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public FacturaEntidad(final UUID id, final PedidoEntidad pedido, final String numeroFactura,
                          final LocalDate fechaEmision, final double total) {
        setId(id);
        setPedido(pedido);
        setNumeroFactura(numeroFactura);
        setFechaEmision(fechaEmision);
        setTotal(total);
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
