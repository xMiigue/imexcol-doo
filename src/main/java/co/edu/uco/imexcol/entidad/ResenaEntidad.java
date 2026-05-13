package co.edu.uco.imexcol.entidad;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ResenaEntidad {

    private UUID id;
    private ProductoEntidad producto;
    private ClienteEntidad cliente;
    private int calificacion;
    private String comentario;
    private LocalDate fecha;

    public ResenaEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setProducto(new ProductoEntidad());
        setCliente(new ClienteEntidad());
        setCalificacion(UtilNumerico.obtenerValorPorDefectoInt());
        setComentario(UtilTexto.VACIO);
        setFecha(UtilFecha.obtenerFechaPorDefecto());
    }

    public ResenaEntidad(final UUID id) {
        setId(id);
        setProducto(new ProductoEntidad());
        setCliente(new ClienteEntidad());
        setCalificacion(UtilNumerico.obtenerValorPorDefectoInt());
        setComentario(UtilTexto.VACIO);
        setFecha(UtilFecha.obtenerFechaPorDefecto());
    }

    public ResenaEntidad(final UUID id, final ProductoEntidad producto, final ClienteEntidad cliente,
                         final int calificacion, final String comentario, final LocalDate fecha) {
        setId(id);
        setProducto(producto);
        setCliente(cliente);
        setCalificacion(calificacion);
        setComentario(comentario);
        setFecha(fecha);
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

    public ClienteEntidad getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteEntidad cliente) {
        this.cliente = UtilObjeto.obtenerValorDefecto(cliente, new ClienteEntidad());
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(final int calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(final String comentario) {
        this.comentario = UtilTexto.aplicarTrim(comentario);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(final LocalDate fecha) {
        this.fecha = UtilFecha.obtenerValorDefecto(fecha);
    }
}
