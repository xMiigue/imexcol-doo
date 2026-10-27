package co.edu.uco.imexcol.negocio.dominio;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilUUID;

public final class SolicitudCompraDominio {

    private UUID idCliente;
    private UUID idCarrito;
    private UUID idDireccion;
    private UUID idMetodoPago;

    public SolicitudCompraDominio() {
        setIdCliente(UtilUUID.obtenerValorPorDefecto());
        setIdCarrito(UtilUUID.obtenerValorPorDefecto());
        setIdDireccion(UtilUUID.obtenerValorPorDefecto());
        setIdMetodoPago(UtilUUID.obtenerValorPorDefecto());
    }

    public SolicitudCompraDominio(final UUID idCliente, final UUID idCarrito, final UUID idDireccion,
                                  final UUID idMetodoPago) {
        setIdCliente(idCliente);
        setIdCarrito(idCarrito);
        setIdDireccion(idDireccion);
        setIdMetodoPago(idMetodoPago);
    }

    public UUID getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(final UUID idCliente) {
        this.idCliente = UtilUUID.obtenerValorDefecto(idCliente);
    }

    public UUID getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(final UUID idCarrito) {
        this.idCarrito = UtilUUID.obtenerValorDefecto(idCarrito);
    }

    public UUID getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(final UUID idDireccion) {
        this.idDireccion = UtilUUID.obtenerValorDefecto(idDireccion);
    }

    public UUID getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(final UUID idMetodoPago) {
        this.idMetodoPago = UtilUUID.obtenerValorDefecto(idMetodoPago);
    }
}
