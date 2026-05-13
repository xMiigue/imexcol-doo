package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.AdministradorEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.AdministradorDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class AdministradorEntidadAssembler implements EntidadAssembler<AdministradorDominio, AdministradorEntidad> {

    private static final AdministradorEntidadAssembler INSTANCIA = new AdministradorEntidadAssembler();

    private AdministradorEntidadAssembler() {
        super();
    }

    public static AdministradorEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public AdministradorEntidad ensamblarEntidad(final AdministradorDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new AdministradorDominio());
        return new AdministradorEntidad(
                dominioAEnsamblar.getId(),
                dominioAEnsamblar.getNombreUsuario(),
                dominioAEnsamblar.getCorreoElectronico(),
                dominioAEnsamblar.getContrasena(),
                dominioAEnsamblar.isEstado());
    }

    @Override
    public AdministradorDominio ensamblarDominio(final AdministradorEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new AdministradorEntidad());
        return new AdministradorDominio(
                entidadAEnsamblar.getId(),
                entidadAEnsamblar.getNombreUsuario(),
                entidadAEnsamblar.getCorreoElectronico(),
                entidadAEnsamblar.getContrasena(),
                entidadAEnsamblar.isEstado());
    }

    @Override
    public List<AdministradorEntidad> ensamblarEntidad(final List<AdministradorDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<AdministradorDominio>());
        var listaEntidades = new ArrayList<AdministradorEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<AdministradorDominio> ensamblarDominio(final List<AdministradorEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<AdministradorEntidad>());
        var listaDominios = new ArrayList<AdministradorDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
