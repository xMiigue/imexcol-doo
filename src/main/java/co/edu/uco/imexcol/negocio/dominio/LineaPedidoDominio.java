package co.edu.uco.imexcol.negocio.dominio;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class LineaPedidoDominio extends Dominio {

    private PedidoDominio pedido;
    private ProductoDominio producto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public LineaPedidoDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setPedido(new PedidoDominio());
        setProducto(new ProductoDominio());
        setCantidad(UtilNumerico.obtenerValorPorDefectoInt());
        setPrecioUnitario(UtilNumerico.obtenerValorPorDefectoDouble());
        setSubtotal(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public LineaPedidoDominio(final UUID id) {
        super(id);
        setPedido(new PedidoDominio());
        setProducto(new ProductoDominio());
        setCantidad(UtilNumerico.obtenerValorPorDefectoInt());
        setPrecioUnitario(UtilNumerico.obtenerValorPorDefectoDouble());
        setSubtotal(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public LineaPedidoDominio(final UUID id, final PedidoDominio pedido, final ProductoDominio producto,
                              final int cantidad, final double precioUnitario, final double subtotal) {
        super(id);
        setPedido(pedido);
        setProducto(producto);
        setCantidad(cantidad);
        setPrecioUnitario(precioUnitario);
        setSubtotal(subtotal);
    }

    public PedidoDominio getPedido() {
        return pedido;
    }

    public void setPedido(final PedidoDominio pedido) {
        this.pedido = UtilObjeto.obtenerValorDefecto(pedido, new PedidoDominio());
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

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(final double subtotal) {
        this.subtotal = subtotal;
    }
}
