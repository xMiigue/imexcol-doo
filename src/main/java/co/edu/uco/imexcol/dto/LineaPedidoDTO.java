package co.edu.uco.imexcol.dto;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class LineaPedidoDTO {

    private UUID id;
    private PedidoDTO pedido;
    private ProductoDTO producto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public LineaPedidoDTO() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setPedido(new PedidoDTO());
        setProducto(new ProductoDTO());
        setCantidad(UtilNumerico.obtenerValorPorDefectoInt());
        setPrecioUnitario(UtilNumerico.obtenerValorPorDefectoDouble());
        setSubtotal(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public LineaPedidoDTO(final UUID id) {
        setId(id);
        setPedido(new PedidoDTO());
        setProducto(new ProductoDTO());
        setCantidad(UtilNumerico.obtenerValorPorDefectoInt());
        setPrecioUnitario(UtilNumerico.obtenerValorPorDefectoDouble());
        setSubtotal(UtilNumerico.obtenerValorPorDefectoDouble());
    }

    public LineaPedidoDTO(final UUID id, final PedidoDTO pedido, final ProductoDTO producto, final int cantidad,
                          final double precioUnitario, final double subtotal) {
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

    public PedidoDTO getPedido() {
        return pedido;
    }

    public void setPedido(final PedidoDTO pedido) {
        this.pedido = UtilObjeto.obtenerValorDefecto(pedido, new PedidoDTO());
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

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(final double subtotal) {
        this.subtotal = subtotal;
    }
}
