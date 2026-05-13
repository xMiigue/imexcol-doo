package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.ImagenProductoEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.ImagenProductoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class ImagenProductoEntidadAssembler implements EntidadAssembler<ImagenProductoDominio, ImagenProductoEntidad> {

    private static final ImagenProductoEntidadAssembler INSTANCIA = new ImagenProductoEntidadAssembler();

    private ImagenProductoEntidadAssembler() {
        super();
    }

    public static ImagenProductoEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public ImagenProductoEntidad ensamblarEntidad(final ImagenProductoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ImagenProductoDominio());
        return new ImagenProductoEntidad(
                dominioAEnsamblar.getId(),
                ProductoEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getProducto()),
                dominioAEnsamblar.getUrl(),
                dominioAEnsamblar.getDescripcion(),
                dominioAEnsamblar.isEsPrincipal());
    }

    @Override
    public ImagenProductoDominio ensamblarDominio(final ImagenProductoEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new ImagenProductoEntidad());
        return new ImagenProductoDominio(
                entidadAEnsamblar.getId(),
                ProductoEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getProducto()),
                entidadAEnsamblar.getUrl(),
                entidadAEnsamblar.getDescripcion(),
                entidadAEnsamblar.isEsPrincipal());
    }

    @Override
    public List<ImagenProductoEntidad> ensamblarEntidad(final List<ImagenProductoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<ImagenProductoDominio>());
        var listaEntidades = new ArrayList<ImagenProductoEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<ImagenProductoDominio> ensamblarDominio(final List<ImagenProductoEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<ImagenProductoEntidad>());
        var listaDominios = new ArrayList<ImagenProductoDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
