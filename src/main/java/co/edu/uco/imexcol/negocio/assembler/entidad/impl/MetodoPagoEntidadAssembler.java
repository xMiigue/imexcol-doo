package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.MetodoPagoEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.MetodoPagoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class MetodoPagoEntidadAssembler implements EntidadAssembler<MetodoPagoDominio, MetodoPagoEntidad> {

    private static final MetodoPagoEntidadAssembler INSTANCIA = new MetodoPagoEntidadAssembler();

    private MetodoPagoEntidadAssembler() {
        super();
    }

    public static MetodoPagoEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MetodoPagoEntidad ensamblarEntidad(final MetodoPagoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new MetodoPagoDominio());
        return new MetodoPagoEntidad(
                dominioAEnsamblar.getId(),
                dominioAEnsamblar.getNombre(),
                dominioAEnsamblar.getDescripcion(),
                dominioAEnsamblar.isEstado());
    }

    @Override
    public MetodoPagoDominio ensamblarDominio(final MetodoPagoEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new MetodoPagoEntidad());
        return new MetodoPagoDominio(
                entidadAEnsamblar.getId(),
                entidadAEnsamblar.getNombre(),
                entidadAEnsamblar.getDescripcion(),
                entidadAEnsamblar.isEstado());
    }

    @Override
    public List<MetodoPagoEntidad> ensamblarEntidad(final List<MetodoPagoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<MetodoPagoDominio>());
        var listaEntidades = new ArrayList<MetodoPagoEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<MetodoPagoDominio> ensamblarDominio(final List<MetodoPagoEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<MetodoPagoEntidad>());
        var listaDominios = new ArrayList<MetodoPagoDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
