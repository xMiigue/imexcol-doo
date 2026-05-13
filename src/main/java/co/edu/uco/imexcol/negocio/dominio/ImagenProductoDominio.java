package co.edu.uco.imexcol.negocio.dominio;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ImagenProductoDominio extends Dominio {

    private ProductoDominio producto;
    private String url;
    private String descripcion;
    private boolean esPrincipal;

    public ImagenProductoDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setProducto(new ProductoDominio());
        setUrl(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setEsPrincipal(UtilBooleano.obtenerValorPorDefecto());
    }

    public ImagenProductoDominio(final UUID id) {
        super(id);
        setProducto(new ProductoDominio());
        setUrl(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setEsPrincipal(UtilBooleano.obtenerValorPorDefecto());
    }

    public ImagenProductoDominio(final UUID id, final ProductoDominio producto, final String url,
                                 final String descripcion, final boolean esPrincipal) {
        super(id);
        setProducto(producto);
        setUrl(url);
        setDescripcion(descripcion);
        setEsPrincipal(esPrincipal);
    }

    public ProductoDominio getProducto() {
        return producto;
    }

    public void setProducto(final ProductoDominio producto) {
        this.producto = UtilObjeto.obtenerValorDefecto(producto, new ProductoDominio());
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
