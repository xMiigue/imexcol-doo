package co.edu.uco.imexcol.entidad;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class CarritoComprasEntidad {

    private UUID id;
    private ClienteEntidad cliente;
    private LocalDate fechaCreacion;
    private String estado;

    public CarritoComprasEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setCliente(new ClienteEntidad());
        setFechaCreacion(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilTexto.VACIO);
    }

    public CarritoComprasEntidad(final UUID id) {
        setId(id);
        setCliente(new ClienteEntidad());
        setFechaCreacion(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilTexto.VACIO);
    }

    public CarritoComprasEntidad(final UUID id, final ClienteEntidad cliente, final LocalDate fechaCreacion,
                                 final String estado) {
        setId(id);
        setCliente(cliente);
        setFechaCreacion(fechaCreacion);
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
