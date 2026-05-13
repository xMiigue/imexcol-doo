package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.DireccionEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.DireccionDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class DireccionEntidadAssembler implements EntidadAssembler<DireccionDominio, DireccionEntidad> {

    private static final DireccionEntidadAssembler INSTANCIA = new DireccionEntidadAssembler();

    private DireccionEntidadAssembler() {
        super();
    }

    public static DireccionEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public DireccionEntidad ensamblarEntidad(final DireccionDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new DireccionDominio());
        return new DireccionEntidad(
                dominioAEnsamblar.getId(),
                ClienteEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getCliente()),
                dominioAEnsamblar.getCalle(),
                dominioAEnsamblar.getCiudad(),
                dominioAEnsamblar.getDepartamento(),
                dominioAEnsamblar.getCodigoPostal(),
                dominioAEnsamblar.getPais(),
                dominioAEnsamblar.isEsPrincipal());
    }

    @Override
    public DireccionDominio ensamblarDominio(final DireccionEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new DireccionEntidad());
        return new DireccionDominio(
                entidadAEnsamblar.getId(),
                ClienteEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getCliente()),
                entidadAEnsamblar.getCalle(),
                entidadAEnsamblar.getCiudad(),
                entidadAEnsamblar.getDepartamento(),
                entidadAEnsamblar.getCodigoPostal(),
                entidadAEnsamblar.getPais(),
                entidadAEnsamblar.isEsPrincipal());
    }

    @Override
    public List<DireccionEntidad> ensamblarEntidad(final List<DireccionDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<DireccionDominio>());
        var listaEntidades = new ArrayList<DireccionEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<DireccionDominio> ensamblarDominio(final List<DireccionEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<DireccionEntidad>());
        var listaDominios = new ArrayList<DireccionDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
