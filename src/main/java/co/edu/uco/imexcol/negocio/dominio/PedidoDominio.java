package co.edu.uco.imexcol.negocio.dominio;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class PedidoDominio extends Dominio {

    private ClienteDominio cliente;
    private LocalDate fechaPedido;
    private double total;
    private String estado;

    public PedidoDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setCliente(new ClienteDominio());
        setFechaPedido(UtilFecha.obtenerFechaPorDefecto());
        setTotal(UtilNumerico.obtenerValorPorDefectoDouble());
        setEstado(UtilTexto.VACIO);
    }

    public PedidoDominio(final UUID id) {
        super(id);
        setCliente(new ClienteDominio());
        setFechaPedido(UtilFecha.obtenerFechaPorDefecto());
        setTotal(UtilNumerico.obtenerValorPorDefectoDouble());
        setEstado(UtilTexto.VACIO);
    }

    public PedidoDominio(final UUID id, final ClienteDominio cliente, final LocalDate fechaPedido,
                         final double total, final String estado) {
        super(id);
        setCliente(cliente);
        setFechaPedido(fechaPedido);
        setTotal(total);
        setEstado(estado);
    }

    public ClienteDominio getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteDominio cliente) {
        this.cliente = UtilObjeto.obtenerValorDefecto(cliente, new ClienteDominio());
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
