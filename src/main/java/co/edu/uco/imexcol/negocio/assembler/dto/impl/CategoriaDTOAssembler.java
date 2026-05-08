package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import co.edu.uco.imexcol.dto.CategoriaDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.CategoriaDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class CategoriaDTOAssembler implements DTOAssembler<CategoriaDominio, CategoriaDTO> {

    private static final CategoriaDTOAssembler INSTANCIA = new CategoriaDTOAssembler();

    private CategoriaDTOAssembler() {
        super();
    }

    public static final CategoriaDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public CategoriaDominio ensamblarDominio(final CategoriaDTO dto) {
        var categoriaAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new CategoriaDTO.Builder().build());
        return new CategoriaDominio.Builder()
                .id(categoriaAEnsamblar.getId())
                .nombre(categoriaAEnsamblar.getNombre())
                .descripcion(categoriaAEnsamblar.getDescripcion())
                .estado(categoriaAEnsamblar.isEstado())
                .build();
    }

    @Override
    public CategoriaDTO ensamblarDTO(final CategoriaDominio dominio) {
        var categoriaAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new CategoriaDominio.Builder().build());
        return new CategoriaDTO.Builder()
                .id(categoriaAEnsamblar.getId())
                .nombre(categoriaAEnsamblar.getNombre())
                .descripcion(categoriaAEnsamblar.getDescripcion())
                .estado(categoriaAEnsamblar.isEstado())
                .build();
    }
}
