package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.PagoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.PagoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class PagoDTOAssembler implements DTOAssembler<PagoDominio, PagoDTO> {

    private static final PagoDTOAssembler INSTANCIA = new PagoDTOAssembler();

    private PagoDTOAssembler() {
        super();
    }

    public static PagoDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public PagoDominio ensamblarDominio(final PagoDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new PagoDTO());
        return new PagoDominio(
                dtoAEnsamblar.getId(),
                PedidoDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getPedido()),
                MetodoPagoDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getMetodoPago()),
                dtoAEnsamblar.getMonto(),
                dtoAEnsamblar.getFechaPago(),
                dtoAEnsamblar.getEstado());
    }

    @Override
    public PagoDTO ensamblarDTO(final PagoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new PagoDominio());
        return new PagoDTO(
                dominioAEnsamblar.getId(),
                PedidoDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getPedido()),
                MetodoPagoDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getMetodoPago()),
                dominioAEnsamblar.getMonto(),
                dominioAEnsamblar.getFechaPago(),
                dominioAEnsamblar.getEstado());
    }

    @Override
    public List<PagoDTO> ensamblarDTO(final List<PagoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<PagoDominio>());
        var listaDTO = new ArrayList<PagoDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<PagoDominio> ensamblarDominio(final List<PagoDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<PagoDTO>());
        var listaDominios = new ArrayList<PagoDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
