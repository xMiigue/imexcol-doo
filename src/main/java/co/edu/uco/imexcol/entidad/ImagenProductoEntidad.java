package co.edu.uco.imexcol.entidad;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ImagenProductoEntidad {

    private UUID id;
    private ProductoEntidad producto;
    private String foto;
    private int ordenVisualizacion;

    public ImagenProductoEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setProducto(new ProductoEntidad());
        setFoto(UtilTexto.VACIO);
        setOrdenVisualizacion(UtilNumerico.obtenerValorPorDefectoInt());
    }

    public ImagenProductoEntidad(final UUID id) {
        setId(id);
        setProducto(new ProductoEntidad());
        setFoto(UtilTexto.VACIO);
        setOrdenVisualizacion(UtilNumerico.obtenerValorPorDefectoInt());
    }

    public ImagenProductoEntidad(final UUID id, final ProductoEntidad producto, final String foto,
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

    public ProductoEntidad getProducto() {
        return producto;
    }

    public void setProducto(final ProductoEntidad producto) {
        this.producto = UtilObjeto.obtenerValorDefecto(producto, new ProductoEntidad());
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
