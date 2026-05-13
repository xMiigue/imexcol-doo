package co.edu.uco.imexcol.entidad;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ProductoEntidad {

    private UUID id;
    private CategoriaEntidad categoria;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private boolean estado;

    public ProductoEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setCategoria(new CategoriaEntidad());
        setNombre(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setPrecio(UtilNumerico.obtenerValorPorDefectoDouble());
        setStock(UtilNumerico.obtenerValorPorDefectoInt());
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public ProductoEntidad(final UUID id) {
        setId(id);
        setCategoria(new CategoriaEntidad());
        setNombre(UtilTexto.VACIO);
        setDescripcion(UtilTexto.VACIO);
        setPrecio(UtilNumerico.obtenerValorPorDefectoDouble());
        setStock(UtilNumerico.obtenerValorPorDefectoInt());
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public ProductoEntidad(final UUID id, final CategoriaEntidad categoria, final String nombre,
                           final String descripcion, final double precio, final int stock, final boolean estado) {
        setId(id);
        setCategoria(categoria);
        setNombre(nombre);
        setDescripcion(descripcion);
        setPrecio(precio);
        setStock(stock);
        setEstado(estado);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public CategoriaEntidad getCategoria() {
        return categoria;
    }

    public void setCategoria(final CategoriaEntidad categoria) {
        this.categoria = UtilObjeto.obtenerValorDefecto(categoria, new CategoriaEntidad());
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(final boolean estado) {
        this.estado = estado;
    }
}
