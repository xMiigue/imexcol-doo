package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.PedidoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class PedidoEntidadAssembler implements EntidadAssembler<PedidoDominio, PedidoEntidad> {

    private static final PedidoEntidadAssembler INSTANCIA = new PedidoEntidadAssembler();

    private PedidoEntidadAssembler() {
        super();
    }

    public static PedidoEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public PedidoEntidad ensamblarEntidad(final PedidoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new PedidoDominio());
        return new PedidoEntidad(
                dominioAEnsamblar.getId(),
                ClienteEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getCliente()),
                dominioAEnsamblar.getFechaPedido(),
                dominioAEnsamblar.getTotal(),
                dominioAEnsamblar.getEstado());
    }

    @Override
    public PedidoDominio ensamblarDominio(final PedidoEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new PedidoEntidad());
        return new PedidoDominio(
                entidadAEnsamblar.getId(),
                ClienteEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getCliente()),
                entidadAEnsamblar.getFechaPedido(),
                entidadAEnsamblar.getTotal(),
                entidadAEnsamblar.getEstado());
    }

    @Override
    public List<PedidoEntidad> ensamblarEntidad(final List<PedidoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<PedidoDominio>());
        var listaEntidades = new ArrayList<PedidoEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<PedidoDominio> ensamblarDominio(final List<PedidoEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<PedidoEntidad>());
        var listaDominios = new ArrayList<PedidoDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
