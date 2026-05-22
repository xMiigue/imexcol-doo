package co.edu.uco.imexcol.negocio.dominio;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ProductoDominio extends Dominio {

    private CategoriaDominio categoria;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String imagenUrl;
    private boolean estado;

    public ProductoDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setCategoria(new CategoriaDominio());
        setNombre(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setPrecio(UtilNumerico.obtenerValorPorDefectoDouble());
        setStock(UtilNumerico.obtenerValorPorDefectoInt());
        setImagenUrl(UtilTexto.VACIO);
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public ProductoDominio(final UUID id) {
        super(id);
        setCategoria(new CategoriaDominio());
        setNombre(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setPrecio(UtilNumerico.obtenerValorPorDefectoDouble());
        setStock(UtilNumerico.obtenerValorPorDefectoInt());
        setImagenUrl(UtilTexto.VACIO);
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public ProductoDominio(final UUID id, final CategoriaDominio categoria, final String nombre,
                           final String descripcion, final double precio, final int stock,
                           final String imagenUrl, final boolean estado) {
        super(id);
        setCategoria(categoria);
        setNombre(nombre);
        setDescripcion(descripcion);
        setPrecio(precio);
        setStock(stock);
        setImagenUrl(imagenUrl);
        setEstado(estado);
    }

    public CategoriaDominio getCategoria() {
        return categoria;
    }

    public void setCategoria(final CategoriaDominio categoria) {
        this.categoria = UtilObjeto.obtenerValorDefecto(categoria, new CategoriaDominio());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = UtilTexto.aplicarTrim(nombre);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = UtilTexto.aplicarTrim(descripcion);
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(final double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(final int stock) {
        this.stock = stock;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(final String imagenUrl) {
        this.imagenUrl = UtilTexto.aplicarTrim(imagenUrl);
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(final boolean estado) {
        this.estado = estado;
    }
}
