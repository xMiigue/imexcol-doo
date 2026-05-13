package co.edu.uco.imexcol.negocio.dominio;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ResenaDominio extends Dominio {

    private ProductoDominio producto;
    private ClienteDominio cliente;
    private int calificacion;
    private String comentario;
    private LocalDate fecha;

    public ResenaDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setProducto(new ProductoDominio());
        setCliente(new ClienteDominio());
        setCalificacion(UtilNumerico.obtenerValorPorDefectoInt());
        setComentario(UtilTexto.VACIO);
        setFecha(UtilFecha.obtenerFechaPorDefecto());
    }

    public ResenaDominio(final UUID id) {
        super(id);
        setProducto(new ProductoDominio());
        setCliente(new ClienteDominio());
        setCalificacion(UtilNumerico.obtenerValorPorDefectoInt());
        setComentario(UtilTexto.VACIO);
        setFecha(UtilFecha.obtenerFechaPorDefecto());
    }

    public ResenaDominio(final UUID id, final ProductoDominio producto, final ClienteDominio cliente,
                         final int calificacion, final String comentario, final LocalDate fecha) {
        super(id);
        setProducto(producto);
        setCliente(cliente);
        setCalificacion(calificacion);
        setComentario(comentario);
        setFecha(fecha);
    }

    public ProductoDominio getProducto() {
        return producto;
    }

    public void setProducto(final ProductoDominio producto) {
        this.producto = UtilObjeto.obtenerValorDefecto(producto, new ProductoDominio());
    }

    public ClienteDominio getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteDominio cliente) {
        this.cliente = UtilObjeto.obtenerValorDefecto(cliente, new ClienteDominio());
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
