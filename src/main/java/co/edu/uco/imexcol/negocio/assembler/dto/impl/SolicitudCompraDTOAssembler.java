package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.SolicitudCompraDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.SolicitudCompraDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class SolicitudCompraDTOAssembler implements DTOAssembler<SolicitudCompraDominio, SolicitudCompraDTO> {

    private static final SolicitudCompraDTOAssembler INSTANCIA = new SolicitudCompraDTOAssembler();

    private SolicitudCompraDTOAssembler() {
        super();
    }

    public static SolicitudCompraDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public SolicitudCompraDominio ensamblarDominio(final SolicitudCompraDTO dto) {
        final var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new SolicitudCompraDTO());
        return new SolicitudCompraDominio(
                dtoAEnsamblar.getIdCliente(),
                dtoAEnsamblar.getIdCarrito(),
                dtoAEnsamblar.getIdDireccion(),
                dtoAEnsamblar.getIdMetodoPago());
    }

    @Override
    public SolicitudCompraDTO ensamblarDTO(final SolicitudCompraDominio dominio) {
        final var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new SolicitudCompraDominio());
        return new SolicitudCompraDTO(
                dominioAEnsamblar.getIdCliente(),
                dominioAEnsamblar.getIdCarrito(),
                dominioAEnsamblar.getIdDireccion(),
                dominioAEnsamblar.getIdMetodoPago());
    }

    @Override
    public List<SolicitudCompraDTO> ensamblarDTO(final List<SolicitudCompraDominio> listaDominios) {
        final var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<SolicitudCompraDominio>());
        final var listaDTO = new ArrayList<SolicitudCompraDTO>();
        for (final var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<SolicitudCompraDominio> ensamblarDominio(final List<SolicitudCompraDTO> listaDTO) {
        final var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<SolicitudCompraDTO>());
        final var listaDominios = new ArrayList<SolicitudCompraDominio>();
        for (final var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
