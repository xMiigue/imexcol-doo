package co.edu.uco.imexcol.dto;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class CarritoComprasDTO {

    private UUID id;
    private ClienteDTO cliente;
    private LocalDate fechaCreacion;
    private String estado;

    public CarritoComprasDTO() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setCliente(new ClienteDTO());
        setFechaCreacion(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilTexto.VACIO);
    }

    public CarritoComprasDTO(final UUID id) {
        setId(id);
        setCliente(new ClienteDTO());
        setFechaCreacion(UtilFecha.obtenerFechaPorDefecto());
        setEstado(UtilTexto.VACIO);
    }

    public CarritoComprasDTO(final UUID id, final ClienteDTO cliente, final LocalDate fechaCreacion,
                             final String estado) {
        setId(id);
        setCliente(cliente);
        setFechaCreacion(fechaCreacion);
        setEstado(estado);
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

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(final LocalDate fechaCreacion) {
        this.fechaCreacion = UtilFecha.obtenerValorDefecto(fechaCreacion);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(final String estado) {
        this.estado = UtilTexto.aplicarTrim(estado);
    }
}
