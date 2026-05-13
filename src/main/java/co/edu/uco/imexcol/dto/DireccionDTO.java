package co.edu.uco.imexcol.dto;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class DireccionDTO {

    private UUID id;
    private ClienteDTO cliente;
    private String calle;
    private String ciudad;
    private String departamento;
    private String codigoPostal;
    private String pais;
    private boolean esPrincipal;

    public DireccionDTO() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setCliente(new ClienteDTO());
        setCalle(UtilTexto.VACIO);
        setCiudad(UtilTexto.VACIO);
        setDepartamento(UtilTexto.VACIO);
        setCodigoPostal(UtilTexto.VACIO);
        setPais(UtilTexto.VACIO);
        setEsPrincipal(UtilBooleano.obtenerValorPorDefecto());
    }

    public DireccionDTO(final UUID id) {
        setId(id);
        setCliente(new ClienteDTO());
        setCalle(UtilTexto.VACIO);
        setCiudad(UtilTexto.VACIO);
        setDepartamento(UtilTexto.VACIO);
        setCodigoPostal(UtilTexto.VACIO);
        setPais(UtilTexto.VACIO);
        setEsPrincipal(UtilBooleano.obtenerValorPorDefecto());
    }

    public DireccionDTO(final UUID id, final ClienteDTO cliente, final String calle, final String ciudad,
                        final String departamento, final String codigoPostal, final String pais,
                        final boolean esPrincipal) {
        setId(id);
        setCliente(cliente);
        setCalle(calle);
        setCiudad(ciudad);
        setDepartamento(departamento);
        setCodigoPostal(codigoPostal);
        setPais(pais);
        setEsPrincipal(esPrincipal);
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

    public String getCalle() {
        return calle;
    }

    public void setCalle(final String calle) {
        this.calle = UtilTexto.aplicarTrim(calle);
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(final String ciudad) {
        this.ciudad = UtilTexto.aplicarTrim(ciudad);
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(final String departamento) {
        this.departamento = UtilTexto.aplicarTrim(departamento);
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(final String codigoPostal) {
        this.codigoPostal = UtilTexto.aplicarTrim(codigoPostal);
    }

    public String getPais() {
        return pais;
    }

    public void setPais(final String pais) {
        this.pais = UtilTexto.aplicarTrim(pais);
    }

    public boolean isEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(final boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }
}
