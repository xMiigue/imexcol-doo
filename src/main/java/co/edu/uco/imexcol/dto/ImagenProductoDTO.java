package co.edu.uco.imexcol.dto;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ImagenProductoDTO {

    private UUID id;
    private ProductoDTO producto;
    private String foto;
    private int ordenVisualizacion;

    public ImagenProductoDTO() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setProducto(new ProductoDTO());
        setFoto(UtilTexto.VACIO);
        setOrdenVisualizacion(UtilNumerico.obtenerValorPorDefectoInt());
    }

    public ImagenProductoDTO(final UUID id) {
        setId(id);
        setProducto(new ProductoDTO());
        setFoto(UtilTexto.VACIO);
        setOrdenVisualizacion(UtilNumerico.obtenerValorPorDefectoInt());
    }

    public ImagenProductoDTO(final UUID id, final ProductoDTO producto, final String foto,
                             final int ordenVisualizacion) {
        setId(id);
        setProducto(producto);
        setFoto(foto);
        setOrdenVisualizacion(ordenVisualizacion);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(final ProductoDTO producto) {
        this.producto = UtilObjeto.obtenerValorDefecto(producto, new ProductoDTO());
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(final String foto) {
        this.foto = UtilTexto.aplicarTrim(foto);
    }

    public int getOrdenVisualizacion() {
        return ordenVisualizacion;
    }

    public void setOrdenVisualizacion(final int ordenVisualizacion) {
        this.ordenVisualizacion = ordenVisualizacion;
    }
}
