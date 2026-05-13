package co.edu.uco.imexcol.dto;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ItemCarritoDTO {

    private UUID id;
    private CarritoComprasDTO carrito;
    private ProductoDTO producto;
    private int cantidad;
    private double precioUnitario;

    public ItemCarritoDTO() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setCarrito(new CarritoComprasDTO());
        setProducto(new ProductoDTO());
        setCantidad(UtilNumerico.obtenerValorPorDefectoInt());
        setPrecioUnitario(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public ItemCarritoDTO(final UUID id) {
        setId(id);
        setCarrito(new CarritoComprasDTO());
        setProducto(new ProductoDTO());
        setCantidad(UtilNumerico.obtenerValorPorDefectoInt());
        setPrecioUnitario(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public ItemCarritoDTO(final UUID id, final CarritoComprasDTO carrito, final ProductoDTO producto,
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

    public CarritoComprasDTO getCarrito() {
        return carrito;
    }

    public void setCarrito(final CarritoComprasDTO carrito) {
        this.carrito = UtilObjeto.obtenerValorDefecto(carrito, new CarritoComprasDTO());
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(final ProductoDTO producto) {
        this.producto = UtilObjeto.obtenerValorDefecto(producto, new ProductoDTO());
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
