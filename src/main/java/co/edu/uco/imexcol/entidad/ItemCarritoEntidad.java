package co.edu.uco.imexcol.entidad;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ItemCarritoEntidad {

    private UUID id;
    private CarritoComprasEntidad carrito;
    private ProductoEntidad producto;
    private int cantidad;
    private double precioUnitario;

    public ItemCarritoEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setCarrito(new CarritoComprasEntidad());
        setProducto(new ProductoEntidad());
        setCantidad(UtilNumerico.obtenerValorPorDefectoInt());
        setPrecioUnitario(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public ItemCarritoEntidad(final UUID id) {
        setId(id);
        setCarrito(new CarritoComprasEntidad());
        setProducto(new ProductoEntidad());
        setCantidad(UtilNumerico.obtenerValorPorDefectoInt());
        setPrecioUnitario(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public ItemCarritoEntidad(final UUID id, final CarritoComprasEntidad carrito, final ProductoEntidad producto,
                              final int cantidad, final double precioUnitario) {
        setId(id);
        setCarrito(carrito);
        setProducto(producto);
        setCantidad(cantidad);
        setPrecioUnitario(precioUnitario);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public CarritoComprasEntidad getCarrito() {
        return carrito;
    }

    public void setCarrito(final CarritoComprasEntidad carrito) {
        this.carrito = UtilObjeto.obtenerValorDefecto(carrito, new CarritoComprasEntidad());
    }

    public ProductoEntidad getProducto() {
        return producto;
    }

    public void setProducto(final ProductoEntidad producto) {
        this.producto = UtilObjeto.obtenerValorDefecto(producto, new ProductoEntidad());
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(final int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(final double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
