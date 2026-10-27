package co.edu.uco.imexcol.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class FacturaDTO {

    private UUID id;
    private PagoDTO pago;
    private String numeroFactura;
    private LocalDateTime fechaEmision;
    private double subtotal;
    private double totalImpuestos;
    private double totalFacturado;

    public FacturaDTO() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setPago(new PagoDTO());
        setNumeroFactura(UtilTexto.VACIO);
        setFechaEmision(UtilFecha.obtenerFechaHoraPorDefecto());
        setSubtotal(UtilNumerico.obtenerValorPorDefectoDouble());
        setTotalImpuestos(UtilNumerico.obtenerValorPorDefectoDouble());
        setTotalFacturado(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public FacturaDTO(final UUID id) {
        setId(id);
        setPago(new PagoDTO());
        setNumeroFactura(UtilTexto.VACIO);
        setFechaEmision(UtilFecha.obtenerFechaHoraPorDefecto());
        setSubtotal(UtilNumerico.obtenerValorPorDefectoDouble());
        setTotalImpuestos(UtilNumerico.obtenerValorPorDefectoDouble());
        setTotalFacturado(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public FacturaDTO(final UUID id, final PagoDTO pago, final String numeroFactura,
                          final LocalDateTime fechaEmision, final double subtotal, final double totalImpuestos,
                          final double totalFacturado) {
        setId(id);
        setPago(pago);
        setNumeroFactura(numeroFactura);
        setFechaEmision(fechaEmision);
        setSubtotal(subtotal);
        setTotalImpuestos(totalImpuestos);
        setTotalFacturado(totalFacturado);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public PagoDTO getPago() {
        return pago;
    }

    public void setPago(final PagoDTO pago) {
        this.pago = UtilObjeto.obtenerValorDefecto(pago, new PagoDTO());
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(final String numeroFactura) {
        this.numeroFactura = UtilTexto.aplicarTrim(numeroFactura);
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(final LocalDateTime fechaEmision) {
        this.fechaEmision = UtilFecha.obtenerValorDefecto(fechaEmision);
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(final double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotalImpuestos() {
        return totalImpuestos;
    }

    public void setTotalImpuestos(final double totalImpuestos) {
        this.totalImpuestos = totalImpuestos;
    }

    public double getTotalFacturado() {
        return totalFacturado;
    }

    public void setTotalFacturado(final double totalFacturado) {
        this.totalFacturado = totalFacturado;
    }
}
