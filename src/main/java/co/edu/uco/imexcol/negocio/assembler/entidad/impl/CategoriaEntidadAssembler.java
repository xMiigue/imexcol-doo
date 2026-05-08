package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import co.edu.uco.imexcol.entidad.CategoriaEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.CategoriaDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class CategoriaEntidadAssembler implements EntidadAssembler<CategoriaDominio, CategoriaEntidad> {

    private static final CategoriaEntidadAssembler INSTANCIA = new CategoriaEntidadAssembler();

    private CategoriaEntidadAssembler() {
        super();
    }

    public static final CategoriaEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public CategoriaEntidad ensamblarEntidad(final CategoriaDominio dominio) {
        var categoriaAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new CategoriaDominio.Builder().build());
        return new CategoriaEntidad.Builder()
                .id(categoriaAEnsamblar.getId())
                .nombre(categoriaAEnsamblar.getNombre())
                .descripcion(categoriaAEnsamblar.getDescripcion())
                .estado(categoriaAEnsamblar.isEstado())
                .build();
    }

    @Override
    public CategoriaDominio ensamblarDominio(final CategoriaEntidad entidad) {
        var categoriaAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new CategoriaEntidad.Builder().build());
        return new CategoriaDominio.Builder()
                .id(categoriaAEnsamblar.getId())
                .nombre(categoriaAEnsamblar.getNombre())
                .descripcion(categoriaAEnsamblar.getDescripcion())
                .estado(categoriaAEnsamblar.isEstado())
                .build();
    }
}
