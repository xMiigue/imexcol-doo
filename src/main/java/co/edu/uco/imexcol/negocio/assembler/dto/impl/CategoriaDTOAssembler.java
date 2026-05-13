package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.CategoriaDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.CategoriaDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class CategoriaDTOAssembler implements DTOAssembler<CategoriaDominio, CategoriaDTO> {

    private static final CategoriaDTOAssembler INSTANCIA = new CategoriaDTOAssembler();

    private CategoriaDTOAssembler() {
        super();
    }

    public static CategoriaDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public CategoriaDominio ensamblarDominio(final CategoriaDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new CategoriaDTO());
        return new CategoriaDominio(
                dtoAEnsamblar.getId(),
                dtoAEnsamblar.getNombre(),
                dtoAEnsamblar.getDescripcion(),
                dtoAEnsamblar.isEstado());
    }

    @Override
    public CategoriaDTO ensamblarDTO(final CategoriaDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new CategoriaDominio());
        return new CategoriaDTO(
                dominioAEnsamblar.getId(),
                dominioAEnsamblar.getNombre(),
                dominioAEnsamblar.getDescripcion(),
                dominioAEnsamblar.isEstado());
    }

    @Override
    public List<CategoriaDTO> ensamblarDTO(final List<CategoriaDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<CategoriaDominio>());
        var listaDTO = new ArrayList<CategoriaDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<CategoriaDominio> ensamblarDominio(final List<CategoriaDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<CategoriaDTO>());
        var listaDominios = new ArrayList<CategoriaDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
