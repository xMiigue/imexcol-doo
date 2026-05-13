package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.EventoRastreoEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.EventoRastreoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class EventoRastreoEntidadAssembler implements EntidadAssembler<EventoRastreoDominio, EventoRastreoEntidad> {

    private static final EventoRastreoEntidadAssembler INSTANCIA = new EventoRastreoEntidadAssembler();

    private EventoRastreoEntidadAssembler() {
        super();
    }

    public static EventoRastreoEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public EventoRastreoEntidad ensamblarEntidad(final EventoRastreoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new EventoRastreoDominio());
        return new EventoRastreoEntidad(
                dominioAEnsamblar.getId(),
                EnvioEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getEnvio()),
                dominioAEnsamblar.getFecha(),
                dominioAEnsamblar.getUbicacion(),
                dominioAEnsamblar.getDescripcion(),
                dominioAEnsamblar.getEstado());
    }

    @Override
    public EventoRastreoDominio ensamblarDominio(final EventoRastreoEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new EventoRastreoEntidad());
        return new EventoRastreoDominio(
                entidadAEnsamblar.getId(),
                EnvioEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getEnvio()),
                entidadAEnsamblar.getFecha(),
                entidadAEnsamblar.getUbicacion(),
                entidadAEnsamblar.getDescripcion(),
                entidadAEnsamblar.getEstado());
    }

    @Override
    public List<EventoRastreoEntidad> ensamblarEntidad(final List<EventoRastreoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<EventoRastreoDominio>());
        var listaEntidades = new ArrayList<EventoRastreoEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<EventoRastreoDominio> ensamblarDominio(final List<EventoRastreoEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<EventoRastreoEntidad>());
        var listaDominios = new ArrayList<EventoRastreoDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
