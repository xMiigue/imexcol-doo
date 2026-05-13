package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.LineaPedidoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.LineaPedidoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class LineaPedidoDTOAssembler implements DTOAssembler<LineaPedidoDominio, LineaPedidoDTO> {

    private static final LineaPedidoDTOAssembler INSTANCIA = new LineaPedidoDTOAssembler();

    private LineaPedidoDTOAssembler() {
        super();
    }

    public static LineaPedidoDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public LineaPedidoDominio ensamblarDominio(final LineaPedidoDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new LineaPedidoDTO());
        return new LineaPedidoDominio(
                dtoAEnsamblar.getId(),
                PedidoDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getPedido()),
                ProductoDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getProducto()),
                dtoAEnsamblar.getCantidad(),
                dtoAEnsamblar.getPrecioUnitario(),
                dtoAEnsamblar.getSubtotal());
    }

    @Override
    public LineaPedidoDTO ensamblarDTO(final LineaPedidoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new LineaPedidoDominio());
        return new LineaPedidoDTO(
                dominioAEnsamblar.getId(),
                PedidoDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getPedido()),
                ProductoDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getProducto()),
                dominioAEnsamblar.getCantidad(),
                dominioAEnsamblar.getPrecioUnitario(),
                dominioAEnsamblar.getSubtotal());
    }

    @Override
    public List<LineaPedidoDTO> ensamblarDTO(final List<LineaPedidoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<LineaPedidoDominio>());
        var listaDTO = new ArrayList<LineaPedidoDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<LineaPedidoDominio> ensamblarDominio(final List<LineaPedidoDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<LineaPedidoDTO>());
        var listaDominios = new ArrayList<LineaPedidoDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
