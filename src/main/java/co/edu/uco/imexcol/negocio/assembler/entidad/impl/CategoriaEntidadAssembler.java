package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.CategoriaEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.CategoriaDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class CategoriaEntidadAssembler implements EntidadAssembler<CategoriaDominio, CategoriaEntidad> {

    private static final CategoriaEntidadAssembler INSTANCIA = new CategoriaEntidadAssembler();

    private CategoriaEntidadAssembler() {
        super();
    }

    public static CategoriaEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public CategoriaEntidad ensamblarEntidad(final CategoriaDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new CategoriaDominio());
        return new CategoriaEntidad(
                dominioAEnsamblar.getId(),
                dominioAEnsamblar.getNombre(),
                dominioAEnsamblar.getDescripcion(),
                dominioAEnsamblar.isEstado());
    }

    @Override
    public CategoriaDominio ensamblarDominio(final CategoriaEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new CategoriaEntidad());
        return new CategoriaDominio(
                entidadAEnsamblar.getId(),
                entidadAEnsamblar.getNombre(),
                entidadAEnsamblar.getDescripcion(),
                entidadAEnsamblar.isEstado());
    }

    @Override
    public List<CategoriaEntidad> ensamblarEntidad(final List<CategoriaDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<CategoriaDominio>());
        var listaEntidades = new ArrayList<CategoriaEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<CategoriaDominio> ensamblarDominio(final List<CategoriaEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<CategoriaEntidad>());
        var listaDominios = new ArrayList<CategoriaDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
