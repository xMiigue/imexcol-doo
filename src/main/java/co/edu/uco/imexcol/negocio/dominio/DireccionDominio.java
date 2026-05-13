package co.edu.uco.imexcol.negocio.dominio;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class DireccionDominio extends Dominio {

    private ClienteDominio cliente;
    private String calle;
    private String ciudad;
    private String departamento;
    private String codigoPostal;
    private String pais;
    private boolean esPrincipal;

    public DireccionDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setCliente(new ClienteDominio());
        setCalle(UtilTexto.VACIO);
        setCiudad(UtilTexto.VACIO);
        setDepartamento(UtilTexto.VACIO);
        setCodigoPostal(UtilTexto.VACIO);
        setPais(UtilTexto.VACIO);
        setEsPrincipal(UtilBooleano.obtenerValorPorDefecto());
    }

    public DireccionDominio(final UUID id) {
        super(id);
        setCliente(new ClienteDominio());
        setCalle(UtilTexto.VACIO);
        setCiudad(UtilTexto.VACIO);
        setDepartamento(UtilTexto.VACIO);
        setCodigoPostal(UtilTexto.VACIO);
        setPais(UtilTexto.VACIO);
        setEsPrincipal(UtilBooleano.obtenerValorPorDefecto());
    }

    public DireccionDominio(final UUID id, final ClienteDominio cliente, final String calle, final String ciudad,
                            final String departamento, final String codigoPostal, final String pais,
                            final boolean esPrincipal) {
        super(id);
        setCliente(cliente);
        setCalle(calle);
        setCiudad(ciudad);
        setDepartamento(departamento);
        setCodigoPostal(codigoPostal);
        setPais(pais);
        setEsPrincipal(esPrincipal);
    }

    public ClienteDominio getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteDominio cliente) {
        this.cliente = UtilObjeto.obtenerValorDefecto(cliente, new ClienteDominio());
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
