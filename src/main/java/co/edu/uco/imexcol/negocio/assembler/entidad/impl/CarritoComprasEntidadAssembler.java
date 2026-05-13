package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.CarritoComprasEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.CarritoComprasDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class CarritoComprasEntidadAssembler implements EntidadAssembler<CarritoComprasDominio, CarritoComprasEntidad> {

    private static final CarritoComprasEntidadAssembler INSTANCIA = new CarritoComprasEntidadAssembler();

    private CarritoComprasEntidadAssembler() {
        super();
    }

    public static CarritoComprasEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public CarritoComprasEntidad ensamblarEntidad(final CarritoComprasDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new CarritoComprasDominio());
        return new CarritoComprasEntidad(
                dominioAEnsamblar.getId(),
                ClienteEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getCliente()),
                dominioAEnsamblar.getFechaCreacion(),
                dominioAEnsamblar.isEstado());
    }

    @Override
    public CarritoComprasDominio ensamblarDominio(final CarritoComprasEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new CarritoComprasEntidad());
        return new CarritoComprasDominio(
                entidadAEnsamblar.getId(),
                ClienteEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getCliente()),
                entidadAEnsamblar.getFechaCreacion(),
                entidadAEnsamblar.isEstado());
    }

    @Override
    public List<CarritoComprasEntidad> ensamblarEntidad(final List<CarritoComprasDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<CarritoComprasDominio>());
        var listaEntidades = new ArrayList<CarritoComprasEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<CarritoComprasDominio> ensamblarDominio(final List<CarritoComprasEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<CarritoComprasEntidad>());
        var listaDominios = new ArrayList<CarritoComprasDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
