package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.FacturaDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.FacturaDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class FacturaDTOAssembler implements DTOAssembler<FacturaDominio, FacturaDTO> {

    private static final FacturaDTOAssembler INSTANCIA = new FacturaDTOAssembler();

    private FacturaDTOAssembler() {
        super();
    }

    public static FacturaDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public FacturaDominio ensamblarDominio(final FacturaDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new FacturaDTO());
        return new FacturaDominio(
                dtoAEnsamblar.getId(),
                PedidoDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getPedido()),
                dtoAEnsamblar.getNumeroFactura(),
                dtoAEnsamblar.getFechaEmision(),
                dtoAEnsamblar.getTotal());
    }

    @Override
    public FacturaDTO ensamblarDTO(final FacturaDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new FacturaDominio());
        return new FacturaDTO(
                dominioAEnsamblar.getId(),
                PedidoDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getPedido()),
                dominioAEnsamblar.getNumeroFactura(),
                dominioAEnsamblar.getFechaEmision(),
                dominioAEnsamblar.getTotal());
    }

    @Override
    public List<FacturaDTO> ensamblarDTO(final List<FacturaDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<FacturaDominio>());
        var listaDTO = new ArrayList<FacturaDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<FacturaDominio> ensamblarDominio(final List<FacturaDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<FacturaDTO>());
        var listaDominios = new ArrayList<FacturaDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
