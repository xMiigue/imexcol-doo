package co.edu.uco.imexcol.negocio.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ResenaDominio extends Dominio {

    private ClienteDominio cliente;
    private ProductoDominio producto;
    private int calificacion;
    private String comentario;
    private LocalDateTime fecha;

    public ResenaDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setCliente(new ClienteDominio());
        setProducto(new ProductoDominio());
        setCalificacion(UtilNumerico.obtenerValorPorDefectoInt());
        setComentario(UtilTexto.VACIO);
        setFecha(UtilFecha.obtenerFechaHoraPorDefecto());
    }

    public ResenaDominio(final UUID id) {
        super(id);
        setCliente(new ClienteDominio());
        setProducto(new ProductoDominio());
        setCalificacion(UtilNumerico.obtenerValorPorDefectoInt());
        setComentario(UtilTexto.VACIO);
        setFecha(UtilFecha.obtenerFechaHoraPorDefecto());
    }

    public ResenaDominio(final UUID id, final ClienteDominio cliente, final ProductoDominio producto,
                         final int calificacion, final String comentario, final LocalDateTime fecha) {
        super(id);
        setCliente(cliente);
        setProducto(producto);
        setCalificacion(calificacion);
        setComentario(comentario);
        setFecha(fecha);
    }

    public ClienteDominio getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteDominio cliente) {
        this.cliente = UtilObjeto.obtenerValorDefecto(cliente, new ClienteDominio());
    }

    public ProductoDominio getProducto() {
        return producto;
    }

    public void setProducto(final ProductoDominio producto) {
        this.producto = UtilObjeto.obtenerValorDefecto(producto, new ProductoDominio());
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
