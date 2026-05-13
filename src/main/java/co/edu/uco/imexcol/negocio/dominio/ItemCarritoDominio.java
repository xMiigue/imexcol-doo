package co.edu.uco.imexcol.negocio.dominio;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ItemCarritoDominio extends Dominio {

    private CarritoComprasDominio carrito;
    private ProductoDominio producto;
    private int cantidad;
    private double precioUnitario;

    public ItemCarritoDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setCarrito(new CarritoComprasDominio());
        setProducto(new ProductoDominio());
        setCantidad(UtilNumerico.obtenerValorPorDefectoInt());
        setPrecioUnitario(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public ItemCarritoDominio(final UUID id) {
        super(id);
        setCarrito(new CarritoComprasDominio());
        setProducto(new ProductoDominio());
        setCantidad(UtilNumerico.obtenerValorPorDefectoInt());
        setPrecioUnitario(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public ItemCarritoDominio(final UUID id, final CarritoComprasDominio carrito, final ProductoDominio producto,
                              final int cantidad, final double precioUnitario) {
        super(id);
        setCarrito(carrito);
        setProducto(producto);
        setCantidad(cantidad);
        setPrecioUnitario(precioUnitario);
    }

    public CarritoComprasDominio getCarrito() {
        return carrito;
    }

    public void setCarrito(final CarritoComprasDominio carrito) {
        this.carrito = UtilObjeto.obtenerValorDefecto(carrito, new CarritoComprasDominio());
    }

    public ProductoDominio getProducto() {
        return producto;
    }

    public void setProducto(final ProductoDominio producto) {
        this.producto = UtilObjeto.obtenerValorDefecto(producto, new ProductoDominio());
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
