package co.edu.uco.imexcol.dto;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ProductoDTO {

    private UUID id;
    private CategoriaDTO categoria;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String imagenUrl;
    private boolean estado;

    public ProductoDTO() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setCategoria(new CategoriaDTO());
        setNombre(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setPrecio(UtilNumerico.obtenerValorPorDefectoDouble());
        setStock(UtilNumerico.obtenerValorPorDefectoInt());
        setImagenUrl(UtilTexto.VACIO);
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public ProductoDTO(final UUID id) {
        setId(id);
        setCategoria(new CategoriaDTO());
        setNombre(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setPrecio(UtilNumerico.obtenerValorPorDefectoDouble());
        setStock(UtilNumerico.obtenerValorPorDefectoInt());
        setImagenUrl(UtilTexto.VACIO);
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public ProductoDTO(final UUID id, final CategoriaDTO categoria, final String nombre, final String descripcion,
                       final double precio, final int stock, final String imagenUrl, final boolean estado) {
        setId(id);
        setCategoria(categoria);
        setNombre(nombre);
        setDescripcion(descripcion);
        setPrecio(precio);
        setStock(stock);
        setImagenUrl(imagenUrl);
        setEstado(estado);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(final CategoriaDTO categoria) {
        this.categoria = UtilObjeto.obtenerValorDefecto(categoria, new CategoriaDTO());
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
