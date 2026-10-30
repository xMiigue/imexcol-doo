package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.ResultadoCompraDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.ResultadoCompraDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class ResultadoCompraDTOAssembler implements DTOAssembler<ResultadoCompraDominio, ResultadoCompraDTO> {

    private static final ResultadoCompraDTOAssembler INSTANCIA = new ResultadoCompraDTOAssembler();

    private ResultadoCompraDTOAssembler() {
        super();
    }

    public static ResultadoCompraDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public ResultadoCompraDominio ensamblarDominio(final ResultadoCompraDTO dto) {
        final var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new ResultadoCompraDTO());
        return new ResultadoCompraDominio(
                dtoAEnsamblar.getIdPedido(),
                dtoAEnsamblar.getNumeroFactura(),
                dtoAEnsamblar.getTotal(),
                dtoAEnsamblar.getEstadoPedido());
    }

    @Override
    public ResultadoCompraDTO ensamblarDTO(final ResultadoCompraDominio dominio) {
        final var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ResultadoCompraDominio());
        return new ResultadoCompraDTO(
                dominioAEnsamblar.getIdPedido(),
                dominioAEnsamblar.getNumeroFactura(),
                dominioAEnsamblar.getTotal(),
                dominioAEnsamblar.getEstadoPedido());
    }

    @Override
    public List<ResultadoCompraDTO> ensamblarDTO(final List<ResultadoCompraDominio> listaDominios) {
        final var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<ResultadoCompraDominio>());
        final var listaDTO = new ArrayList<ResultadoCompraDTO>();
        for (final var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<ResultadoCompraDominio> ensamblarDominio(final List<ResultadoCompraDTO> listaDTO) {
        final var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<ResultadoCompraDTO>());
        final var listaDominios = new ArrayList<ResultadoCompraDominio>();
        for (final var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
