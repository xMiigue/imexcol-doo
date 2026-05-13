package co.edu.uco.imexcol.entidad;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class PedidoEntidad {

    private UUID id;
    private ClienteEntidad cliente;
    private LocalDate fechaPedido;
    private double total;
    private String estado;

    public PedidoEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setCliente(new ClienteEntidad());
        setFechaPedido(UtilFecha.obtenerFechaPorDefecto());
        setTotal(UtilNumerico.obtenerValorPorDefectoDouble());
        setEstado(UtilTexto.VACIO);
    }

    public PedidoEntidad(final UUID id) {
        setId(id);
        setCliente(new ClienteEntidad());
        setFechaPedido(UtilFecha.obtenerFechaPorDefecto());
        setTotal(UtilNumerico.obtenerValorPorDefectoDouble());
        setEstado(UtilTexto.VACIO);
    }

    public PedidoEntidad(final UUID id, final ClienteEntidad cliente, final LocalDate fechaPedido,
                         final double total, final String estado) {
        setId(id);
        setCliente(cliente);
        setFechaPedido(fechaPedido);
        setTotal(total);
        setEstado(estado);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public ClienteEntidad getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteEntidad cliente) {
        this.cliente = UtilObjeto.obtenerValorDefecto(cliente, new ClienteEntidad());
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(final LocalDate fechaPedido) {
        this.fechaPedido = UtilFecha.obtenerValorDefecto(fechaPedido);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(final double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(final String estado) {
        this.estado = UtilTexto.aplicarTrim(estado);
    }
}
