package co.edu.uco.imexcol.negocio.dominio;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class CarritoComprasDominio extends Dominio {

    private ClienteDominio cliente;
    private LocalDate fechaCreacion;
    private boolean estado;

    public CarritoComprasDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setCliente(new ClienteDominio());
        setFechaCreacion(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public CarritoComprasDominio(final UUID id) {
        super(id);
        setCliente(new ClienteDominio());
        setFechaCreacion(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public CarritoComprasDominio(final UUID id, final ClienteDominio cliente, final LocalDate fechaCreacion,
                                 final boolean estado) {
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(final boolean estado) {
        this.estado = estado;
    }
}
