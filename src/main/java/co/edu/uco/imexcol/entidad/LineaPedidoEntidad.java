package co.edu.uco.imexcol.entidad;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class LineaPedidoEntidad {

    private UUID id;
    private PedidoEntidad pedido;
    private ProductoEntidad producto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public LineaPedidoEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setPedido(new PedidoEntidad());
        setProducto(new ProductoEntidad());
        setCantidad(UtilNumerico.obtenerValorPorDefectoInt());
        setPrecioUnitario(UtilNumerico.obtenerValorPorDefectoDouble());
        setSubtotal(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public LineaPedidoEntidad(final UUID id) {
        setId(id);
        setPedido(new PedidoEntidad());
        setProducto(new ProductoEntidad());
        setCantidad(UtilNumerico.obtenerValorPorDefectoInt());
        setPrecioUnitario(UtilNumerico.obtenerValorPorDefectoDouble());
        setSubtotal(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public LineaPedidoEntidad(final UUID id, final PedidoEntidad pedido, final ProductoEntidad producto,
                              final int cantidad, final double precioUnitario, final double subtotal) {
        setId(id);
        setPedido(pedido);
        setProducto(producto);
        setCantidad(cantidad);
        setPrecioUnitario(precioUnitario);
        setSubtotal(subtotal);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public PedidoEntidad getPedido() {
        return pedido;
    }

    public void setPedido(final PedidoEntidad pedido) {
        this.pedido = UtilObjeto.obtenerValorDefecto(pedido, new PedidoEntidad());
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

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(final double subtotal) {
        this.subtotal = subtotal;
    }
}
