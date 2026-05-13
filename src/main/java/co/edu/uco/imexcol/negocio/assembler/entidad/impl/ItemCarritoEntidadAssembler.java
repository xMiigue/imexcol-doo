package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.ItemCarritoEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.ItemCarritoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class ItemCarritoEntidadAssembler implements EntidadAssembler<ItemCarritoDominio, ItemCarritoEntidad> {

    private static final ItemCarritoEntidadAssembler INSTANCIA = new ItemCarritoEntidadAssembler();

    private ItemCarritoEntidadAssembler() {
        super();
    }

    public static ItemCarritoEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public ItemCarritoEntidad ensamblarEntidad(final ItemCarritoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ItemCarritoDominio());
        return new ItemCarritoEntidad(
                dominioAEnsamblar.getId(),
                CarritoComprasEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getCarrito()),
                ProductoEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getProducto()),
                dominioAEnsamblar.getCantidad(),
                dominioAEnsamblar.getPrecioUnitario());
    }

    @Override
    public ItemCarritoDominio ensamblarDominio(final ItemCarritoEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new ItemCarritoEntidad());
        return new ItemCarritoDominio(
                entidadAEnsamblar.getId(),
                CarritoComprasEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getCarrito()),
                ProductoEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getProducto()),
                entidadAEnsamblar.getCantidad(),
                entidadAEnsamblar.getPrecioUnitario());
    }

    @Override
    public List<ItemCarritoEntidad> ensamblarEntidad(final List<ItemCarritoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<ItemCarritoDominio>());
        var listaEntidades = new ArrayList<ItemCarritoEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<ItemCarritoDominio> ensamblarDominio(final List<ItemCarritoEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<ItemCarritoEntidad>());
        var listaDominios = new ArrayList<ItemCarritoDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
