package co.edu.uco.imexcol.negocio.dominio;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class CarritoComprasDominio extends Dominio {

    private ClienteDominio cliente;
    private LocalDate fechaCreacion;
    private String estado;

    public CarritoComprasDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setCliente(new ClienteDominio());
        setFechaCreacion(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilTexto.VACIO);
    }

    public CarritoComprasDominio(final UUID id) {
        super(id);
        setCliente(new ClienteDominio());
        setFechaCreacion(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilTexto.VACIO);
    }

    public CarritoComprasDominio(final UUID id, final ClienteDominio cliente, final LocalDate fechaCreacion,
                                 final String estado) {
        super(id);
        setCliente(cliente);
        setFechaCreacion(fechaCreacion);
        setEstado(estado);
    }

    public ClienteDominio getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteDominio cliente) {
        this.cliente = UtilObjeto.obtenerValorDefecto(cliente, new ClienteDominio());
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(final LocalDate fechaCreacion) {
        this.fechaCreacion = UtilFecha.obtenerValorDefecto(fechaCreacion);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(final String estado) {
        this.estado = UtilTexto.aplicarTrim(estado);
    }
}
