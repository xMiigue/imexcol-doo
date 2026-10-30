package co.edu.uco.imexcol.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ResenaDTO {

    private UUID id;
    private ClienteDTO cliente;
    private ProductoDTO producto;
    private int calificacion;
    private String comentario;
    private LocalDateTime fecha;

    public ResenaDTO() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setCliente(new ClienteDTO());
        setProducto(new ProductoDTO());
        setCalificacion(UtilNumerico.obtenerValorPorDefectoInt());
        setComentario(UtilTexto.VACIO);
        setFecha(UtilFecha.obtenerFechaHoraPorDefecto());
    }

    public ResenaDTO(final UUID id) {
        setId(id);
        setCliente(new ClienteDTO());
        setProducto(new ProductoDTO());
        setCalificacion(UtilNumerico.obtenerValorPorDefectoInt());
        setComentario(UtilTexto.VACIO);
        setFecha(UtilFecha.obtenerFechaHoraPorDefecto());
    }

    public ResenaDTO(final UUID id, final ClienteDTO cliente, final ProductoDTO producto,
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

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteDTO cliente) {
        this.cliente = UtilObjeto.obtenerValorDefecto(cliente, new ClienteDTO());
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(final ProductoDTO producto) {
        this.producto = UtilObjeto.obtenerValorDefecto(producto, new ProductoDTO());
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
