package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.MetodoPagoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.MetodoPagoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class MetodoPagoDTOAssembler implements DTOAssembler<MetodoPagoDominio, MetodoPagoDTO> {

    private static final MetodoPagoDTOAssembler INSTANCIA = new MetodoPagoDTOAssembler();

    private MetodoPagoDTOAssembler() {
        super();
    }

    public static MetodoPagoDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MetodoPagoDominio ensamblarDominio(final MetodoPagoDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new MetodoPagoDTO());
        return new MetodoPagoDominio(
                dtoAEnsamblar.getId(),
                dtoAEnsamblar.getNombre(),
                dtoAEnsamblar.getDescripcion(),
                dtoAEnsamblar.isEstado());
    }

    @Override
    public MetodoPagoDTO ensamblarDTO(final MetodoPagoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new MetodoPagoDominio());
        return new MetodoPagoDTO(
                dominioAEnsamblar.getId(),
                dominioAEnsamblar.getNombre(),
                dominioAEnsamblar.getDescripcion(),
                dominioAEnsamblar.isEstado());
    }

    @Override
    public List<MetodoPagoDTO> ensamblarDTO(final List<MetodoPagoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<MetodoPagoDominio>());
        var listaDTO = new ArrayList<MetodoPagoDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<MetodoPagoDominio> ensamblarDominio(final List<MetodoPagoDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<MetodoPagoDTO>());
        var listaDominios = new ArrayList<MetodoPagoDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
