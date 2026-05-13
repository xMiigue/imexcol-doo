package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.PedidoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.PedidoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class PedidoDTOAssembler implements DTOAssembler<PedidoDominio, PedidoDTO> {

    private static final PedidoDTOAssembler INSTANCIA = new PedidoDTOAssembler();

    private PedidoDTOAssembler() {
        super();
    }

    public static PedidoDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public PedidoDominio ensamblarDominio(final PedidoDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new PedidoDTO());
        return new PedidoDominio(
                dtoAEnsamblar.getId(),
                ClienteDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getCliente()),
                dtoAEnsamblar.getFechaPedido(),
                dtoAEnsamblar.getTotal(),
                dtoAEnsamblar.getEstado());
    }

    @Override
    public PedidoDTO ensamblarDTO(final PedidoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new PedidoDominio());
        return new PedidoDTO(
                dominioAEnsamblar.getId(),
                ClienteDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getCliente()),
                dominioAEnsamblar.getFechaPedido(),
                dominioAEnsamblar.getTotal(),
                dominioAEnsamblar.getEstado());
    }

    @Override
    public List<PedidoDTO> ensamblarDTO(final List<PedidoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<PedidoDominio>());
        var listaDTO = new ArrayList<PedidoDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<PedidoDominio> ensamblarDominio(final List<PedidoDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<PedidoDTO>());
        var listaDominios = new ArrayList<PedidoDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
