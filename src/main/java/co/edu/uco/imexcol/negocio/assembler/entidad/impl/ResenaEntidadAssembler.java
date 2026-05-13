package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.ResenaEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.ResenaDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class ResenaEntidadAssembler implements EntidadAssembler<ResenaDominio, ResenaEntidad> {

    private static final ResenaEntidadAssembler INSTANCIA = new ResenaEntidadAssembler();

    private ResenaEntidadAssembler() {
        super();
    }

    public static ResenaEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public ResenaEntidad ensamblarEntidad(final ResenaDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ResenaDominio());
        return new ResenaEntidad(
                dominioAEnsamblar.getId(),
                ProductoEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getProducto()),
                ClienteEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getCliente()),
                dominioAEnsamblar.getCalificacion(),
                dominioAEnsamblar.getComentario(),
                dominioAEnsamblar.getFecha());
    }

    @Override
    public ResenaDominio ensamblarDominio(final ResenaEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new ResenaEntidad());
        return new ResenaDominio(
                entidadAEnsamblar.getId(),
                ProductoEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getProducto()),
                ClienteEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getCliente()),
                entidadAEnsamblar.getCalificacion(),
                entidadAEnsamblar.getComentario(),
                entidadAEnsamblar.getFecha());
    }

    @Override
    public List<ResenaEntidad> ensamblarEntidad(final List<ResenaDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<ResenaDominio>());
        var listaEntidades = new ArrayList<ResenaEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<ResenaDominio> ensamblarDominio(final List<ResenaEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<ResenaEntidad>());
        var listaDominios = new ArrayList<ResenaDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
