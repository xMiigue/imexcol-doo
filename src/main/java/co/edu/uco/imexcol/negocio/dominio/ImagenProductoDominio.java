package co.edu.uco.imexcol.negocio.dominio;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ImagenProductoDominio extends Dominio {

    private ProductoDominio producto;
    private String foto;
    private int ordenVisualizacion;

    public ImagenProductoDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setProducto(new ProductoDominio());
        setFoto(UtilTexto.VACIO);
        setOrdenVisualizacion(UtilNumerico.obtenerValorPorDefectoInt());
    }

    public ImagenProductoDominio(final UUID id) {
        super(id);
        setProducto(new ProductoDominio());
        setFoto(UtilTexto.VACIO);
        setOrdenVisualizacion(UtilNumerico.obtenerValorPorDefectoInt());
    }

    public ImagenProductoDominio(final UUID id, final ProductoDominio producto, final String foto,
                                 final int ordenVisualizacion) {
        super(id);
        setProducto(producto);
        setFoto(foto);
        setOrdenVisualizacion(ordenVisualizacion);
    }

    public ProductoDominio getProducto() {
        return producto;
    }

    public void setProducto(final ProductoDominio producto) {
        this.producto = UtilObjeto.obtenerValorDefecto(producto, new ProductoDominio());
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
