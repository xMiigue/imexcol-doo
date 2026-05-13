package co.edu.uco.imexcol.dto;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class PedidoDTO {

    private UUID id;
    private ClienteDTO cliente;
    private LocalDate fechaPedido;
    private double total;
    private String estado;

    public PedidoDTO() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setCliente(new ClienteDTO());
        setFechaPedido(UtilFecha.obtenerFechaPorDefecto());
        setTotal(UtilNumerico.obtenerValorPorDefectoDouble());
        setEstado(UtilTexto.VACIO);
    }

    public PedidoDTO(final UUID id) {
        setId(id);
        setCliente(new ClienteDTO());
        setFechaPedido(UtilFecha.obtenerFechaPorDefecto());
        setTotal(UtilNumerico.obtenerValorPorDefectoDouble());
        setEstado(UtilTexto.VACIO);
    }

    public PedidoDTO(final UUID id, final ClienteDTO cliente, final LocalDate fechaPedido, final double total,
                     final String estado) {
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

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteDTO cliente) {
        this.cliente = UtilObjeto.obtenerValorDefecto(cliente, new ClienteDTO());
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
