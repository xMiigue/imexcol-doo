package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.ResenaDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.ResenaDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class ResenaDTOAssembler implements DTOAssembler<ResenaDominio, ResenaDTO> {

    private static final ResenaDTOAssembler INSTANCIA = new ResenaDTOAssembler();

    private ResenaDTOAssembler() {
        super();
    }

    public static ResenaDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public ResenaDominio ensamblarDominio(final ResenaDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new ResenaDTO());
        return new ResenaDominio(
                dtoAEnsamblar.getId(),
                ProductoDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getProducto()),
                ClienteDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getCliente()),
                dtoAEnsamblar.getCalificacion(),
                dtoAEnsamblar.getComentario(),
                dtoAEnsamblar.getFecha());
    }

    @Override
    public ResenaDTO ensamblarDTO(final ResenaDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ResenaDominio());
        return new ResenaDTO(
                dominioAEnsamblar.getId(),
                ProductoDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getProducto()),
                ClienteDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getCliente()),
                dominioAEnsamblar.getCalificacion(),
                dominioAEnsamblar.getComentario(),
                dominioAEnsamblar.getFecha());
    }

    @Override
    public List<ResenaDTO> ensamblarDTO(final List<ResenaDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<ResenaDominio>());
        var listaDTO = new ArrayList<ResenaDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<ResenaDominio> ensamblarDominio(final List<ResenaDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<ResenaDTO>());
        var listaDominios = new ArrayList<ResenaDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
