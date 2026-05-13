package co.edu.uco.imexcol.entidad;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ImagenProductoEntidad {

    private UUID id;
    private ProductoEntidad producto;
    private String url;
    private String descripcion;
    private boolean esPrincipal;

    public ImagenProductoEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setProducto(new ProductoEntidad());
        setUrl(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setEsPrincipal(UtilBooleano.obtenerValorPorDefecto());
    }

    public ImagenProductoEntidad(final UUID id) {
        setId(id);
        setProducto(new ProductoEntidad());
        setUrl(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setEsPrincipal(UtilBooleano.obtenerValorPorDefecto());
    }

    public ImagenProductoEntidad(final UUID id, final ProductoEntidad producto, final String url,
                                 final String descripcion, final boolean esPrincipal) {
        setId(id);
        setProducto(producto);
        setUrl(url);
        setDescripcion(descripcion);
        setEsPrincipal(esPrincipal);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = UtilTexto.aplicarTrim(url);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = UtilTexto.aplicarTrim(descripcion);
    }

    public boolean isEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(final boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }
}
