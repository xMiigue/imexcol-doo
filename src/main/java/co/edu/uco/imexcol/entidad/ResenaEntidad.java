package co.edu.uco.imexcol.entidad;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ResenaEntidad {

    private UUID id;
    private ClienteEntidad cliente;
    private ProductoEntidad producto;
    private int calificacion;
    private String comentario;
    private LocalDateTime fecha;

    public ResenaEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setCliente(new ClienteEntidad());
        setProducto(new ProductoEntidad());
        setCalificacion(UtilNumerico.obtenerValorPorDefectoInt());
        setComentario(UtilTexto.VACIO);
        setFecha(UtilFecha.obtenerFechaHoraPorDefecto());
    }

    public ResenaEntidad(final UUID id) {
        setId(id);
        setCliente(new ClienteEntidad());
        setProducto(new ProductoEntidad());
        setCalificacion(UtilNumerico.obtenerValorPorDefectoInt());
        setComentario(UtilTexto.VACIO);
        setFecha(UtilFecha.obtenerFechaHoraPorDefecto());
    }

    public ResenaEntidad(final UUID id, final ClienteEntidad cliente, final ProductoEntidad producto,
                         final int calificacion, final String comentario, final LocalDateTime fecha) {
        setId(id);
        setCliente(cliente);
        setProducto(producto);
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

    public ClienteEntidad getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteEntidad cliente) {
        this.cliente = UtilObjeto.obtenerValorDefecto(cliente, new ClienteEntidad());
    }

    public ProductoEntidad getProducto() {
        return producto;
    }

    public void setProducto(final ProductoEntidad producto) {
        this.producto = UtilObjeto.obtenerValorDefecto(producto, new ProductoEntidad());
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(final LocalDateTime fecha) {
        this.fecha = UtilFecha.obtenerValorDefecto(fecha);
    }
}
