package co.edu.uco.imexcol.entidad;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class CarritoComprasEntidad {

    private UUID id;
    private ClienteEntidad cliente;
    private LocalDate fechaCreacion;
    private boolean estado;

    public CarritoComprasEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setCliente(new ClienteEntidad());
        setFechaCreacion(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public CarritoComprasEntidad(final UUID id) {
        setId(id);
        setCliente(new ClienteEntidad());
        setFechaCreacion(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public CarritoComprasEntidad(final UUID id, final ClienteEntidad cliente, final LocalDate fechaCreacion,
                                 final boolean estado) {
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(final boolean estado) {
        this.estado = estado;
    }
}
