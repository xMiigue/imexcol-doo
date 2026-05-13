package co.edu.uco.imexcol.dto;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ResenaDTO {

    private UUID id;
    private ProductoDTO producto;
    private ClienteDTO cliente;
    private int calificacion;
    private String comentario;
    private LocalDate fecha;

    public ResenaDTO() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setProducto(new ProductoDTO());
        setCliente(new ClienteDTO());
        setCalificacion(UtilNumerico.obtenerValorPorDefectoInt());
        setComentario(UtilTexto.VACIO);
        setFecha(UtilFecha.obtenerFechaPorDefecto());
    }

    public ResenaDTO(final UUID id) {
        setId(id);
        setProducto(new ProductoDTO());
        setCliente(new ClienteDTO());
        setCalificacion(UtilNumerico.obtenerValorPorDefectoInt());
        setComentario(UtilTexto.VACIO);
        setFecha(UtilFecha.obtenerFechaPorDefecto());
    }

    public ResenaDTO(final UUID id, final ProductoDTO producto, final ClienteDTO cliente, final int calificacion,
                     final String comentario, final LocalDate fecha) {
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

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(final ProductoDTO producto) {
        this.producto = UtilObjeto.obtenerValorDefecto(producto, new ProductoDTO());
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteDTO cliente) {
        this.cliente = UtilObjeto.obtenerValorDefecto(cliente, new ClienteDTO());
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
