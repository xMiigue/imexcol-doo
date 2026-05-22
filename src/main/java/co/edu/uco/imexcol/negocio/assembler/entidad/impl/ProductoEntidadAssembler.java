package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.ProductoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class ProductoEntidadAssembler implements EntidadAssembler<ProductoDominio, ProductoEntidad> {

    private static final ProductoEntidadAssembler INSTANCIA = new ProductoEntidadAssembler();

    private ProductoEntidadAssembler() {
        super();
    }

    public static ProductoEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public ProductoEntidad ensamblarEntidad(final ProductoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ProductoDominio());
        return new ProductoEntidad(
                dominioAEnsamblar.getId(),
                CategoriaEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getCategoria()),
                dominioAEnsamblar.getNombre(),
                dominioAEnsamblar.getDescripcion(),
                dominioAEnsamblar.getPrecio(),
                dominioAEnsamblar.getStock(),
                dominioAEnsamblar.getImagenUrl(),
                dominioAEnsamblar.isEstado());
    }

    @Override
    public ProductoDominio ensamblarDominio(final ProductoEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new ProductoEntidad());
        return new ProductoDominio(
                entidadAEnsamblar.getId(),
                CategoriaEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getCategoria()),
                entidadAEnsamblar.getNombre(),
                entidadAEnsamblar.getDescripcion(),
                entidadAEnsamblar.getPrecio(),
                entidadAEnsamblar.getStock(),
                entidadAEnsamblar.getImagenUrl(),
                entidadAEnsamblar.isEstado());
    }

    @Override
    public List<ProductoEntidad> ensamblarEntidad(final List<ProductoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<ProductoDominio>());
        var listaEntidades = new ArrayList<ProductoEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<ProductoDominio> ensamblarDominio(final List<ProductoEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<ProductoEntidad>());
        var listaDominios = new ArrayList<ProductoDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
