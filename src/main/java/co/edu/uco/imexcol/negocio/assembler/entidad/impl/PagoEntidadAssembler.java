package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.PagoEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.PagoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class PagoEntidadAssembler implements EntidadAssembler<PagoDominio, PagoEntidad> {

    private static final PagoEntidadAssembler INSTANCIA = new PagoEntidadAssembler();

    private PagoEntidadAssembler() {
        super();
    }

    public static PagoEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public PagoEntidad ensamblarEntidad(final PagoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new PagoDominio());
        return new PagoEntidad(
                dominioAEnsamblar.getId(),
                PedidoEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getPedido()),
                MetodoPagoEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getMetodoPago()),
                dominioAEnsamblar.getMonto(),
                dominioAEnsamblar.getFechaPago(),
                dominioAEnsamblar.getEstado());
    }

    @Override
    public PagoDominio ensamblarDominio(final PagoEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new PagoEntidad());
        return new PagoDominio(
                entidadAEnsamblar.getId(),
                PedidoEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getPedido()),
                MetodoPagoEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getMetodoPago()),
                entidadAEnsamblar.getMonto(),
                entidadAEnsamblar.getFechaPago(),
                entidadAEnsamblar.getEstado());
    }

    @Override
    public List<PagoEntidad> ensamblarEntidad(final List<PagoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<PagoDominio>());
        var listaEntidades = new ArrayList<PagoEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<PagoDominio> ensamblarDominio(final List<PagoEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<PagoEntidad>());
        var listaDominios = new ArrayList<PagoDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
