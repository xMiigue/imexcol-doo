package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.FacturaEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.FacturaDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class FacturaEntidadAssembler implements EntidadAssembler<FacturaDominio, FacturaEntidad> {

    private static final FacturaEntidadAssembler INSTANCIA = new FacturaEntidadAssembler();

    private FacturaEntidadAssembler() {
        super();
    }

    public static FacturaEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public FacturaEntidad ensamblarEntidad(final FacturaDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new FacturaDominio());
        return new FacturaEntidad(
                dominioAEnsamblar.getId(),
                PedidoEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getPedido()),
                dominioAEnsamblar.getNumeroFactura(),
                dominioAEnsamblar.getFechaEmision(),
                dominioAEnsamblar.getTotal());
    }

    @Override
    public FacturaDominio ensamblarDominio(final FacturaEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new FacturaEntidad());
        return new FacturaDominio(
                entidadAEnsamblar.getId(),
                PedidoEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getPedido()),
                entidadAEnsamblar.getNumeroFactura(),
                entidadAEnsamblar.getFechaEmision(),
                entidadAEnsamblar.getTotal());
    }

    @Override
    public List<FacturaEntidad> ensamblarEntidad(final List<FacturaDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<FacturaDominio>());
        var listaEntidades = new ArrayList<FacturaEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<FacturaDominio> ensamblarDominio(final List<FacturaEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<FacturaEntidad>());
        var listaDominios = new ArrayList<FacturaDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
