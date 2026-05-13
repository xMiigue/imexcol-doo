package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.LineaPedidoEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.LineaPedidoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class LineaPedidoEntidadAssembler implements EntidadAssembler<LineaPedidoDominio, LineaPedidoEntidad> {

    private static final LineaPedidoEntidadAssembler INSTANCIA = new LineaPedidoEntidadAssembler();

    private LineaPedidoEntidadAssembler() {
        super();
    }

    public static LineaPedidoEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public LineaPedidoEntidad ensamblarEntidad(final LineaPedidoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new LineaPedidoDominio());
        return new LineaPedidoEntidad(
                dominioAEnsamblar.getId(),
                PedidoEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getPedido()),
                ProductoEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getProducto()),
                dominioAEnsamblar.getCantidad(),
                dominioAEnsamblar.getPrecioUnitario(),
                dominioAEnsamblar.getSubtotal());
    }

    @Override
    public LineaPedidoDominio ensamblarDominio(final LineaPedidoEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new LineaPedidoEntidad());
        return new LineaPedidoDominio(
                entidadAEnsamblar.getId(),
                PedidoEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getPedido()),
                ProductoEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getProducto()),
                entidadAEnsamblar.getCantidad(),
                entidadAEnsamblar.getPrecioUnitario(),
                entidadAEnsamblar.getSubtotal());
    }

    @Override
    public List<LineaPedidoEntidad> ensamblarEntidad(final List<LineaPedidoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<LineaPedidoDominio>());
        var listaEntidades = new ArrayList<LineaPedidoEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<LineaPedidoDominio> ensamblarDominio(final List<LineaPedidoEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<LineaPedidoEntidad>());
        var listaDominios = new ArrayList<LineaPedidoDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
