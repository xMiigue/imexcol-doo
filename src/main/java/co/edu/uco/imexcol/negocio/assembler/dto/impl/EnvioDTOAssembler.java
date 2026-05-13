package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.EnvioDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.EnvioDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class EnvioDTOAssembler implements DTOAssembler<EnvioDominio, EnvioDTO> {

    private static final EnvioDTOAssembler INSTANCIA = new EnvioDTOAssembler();

    private EnvioDTOAssembler() {
        super();
    }

    public static EnvioDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public EnvioDominio ensamblarDominio(final EnvioDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new EnvioDTO());
        return new EnvioDominio(
                dtoAEnsamblar.getId(),
                PedidoDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getPedido()),
                dtoAEnsamblar.getFechaEnvio(),
                dtoAEnsamblar.getFechaEntrega(),
                dtoAEnsamblar.getTransportadora(),
                dtoAEnsamblar.getNumeroGuia(),
                dtoAEnsamblar.getEstado());
    }

    @Override
    public EnvioDTO ensamblarDTO(final EnvioDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new EnvioDominio());
        return new EnvioDTO(
                dominioAEnsamblar.getId(),
                PedidoDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getPedido()),
                dominioAEnsamblar.getFechaEnvio(),
                dominioAEnsamblar.getFechaEntrega(),
                dominioAEnsamblar.getTransportadora(),
                dominioAEnsamblar.getNumeroGuia(),
                dominioAEnsamblar.getEstado());
    }

    @Override
    public List<EnvioDTO> ensamblarDTO(final List<EnvioDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<EnvioDominio>());
        var listaDTO = new ArrayList<EnvioDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<EnvioDominio> ensamblarDominio(final List<EnvioDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<EnvioDTO>());
        var listaDominios = new ArrayList<EnvioDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
