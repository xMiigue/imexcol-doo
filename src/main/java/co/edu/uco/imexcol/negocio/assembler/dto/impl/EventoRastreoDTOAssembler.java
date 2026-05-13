package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.EventoRastreoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.EventoRastreoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class EventoRastreoDTOAssembler implements DTOAssembler<EventoRastreoDominio, EventoRastreoDTO> {

    private static final EventoRastreoDTOAssembler INSTANCIA = new EventoRastreoDTOAssembler();

    private EventoRastreoDTOAssembler() {
        super();
    }

    public static EventoRastreoDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public EventoRastreoDominio ensamblarDominio(final EventoRastreoDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new EventoRastreoDTO());
        return new EventoRastreoDominio(
                dtoAEnsamblar.getId(),
                EnvioDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getEnvio()),
                dtoAEnsamblar.getFecha(),
                dtoAEnsamblar.getUbicacion(),
                dtoAEnsamblar.getDescripcion(),
                dtoAEnsamblar.getEstado());
    }

    @Override
    public EventoRastreoDTO ensamblarDTO(final EventoRastreoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new EventoRastreoDominio());
        return new EventoRastreoDTO(
                dominioAEnsamblar.getId(),
                EnvioDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getEnvio()),
                dominioAEnsamblar.getFecha(),
                dominioAEnsamblar.getUbicacion(),
                dominioAEnsamblar.getDescripcion(),
                dominioAEnsamblar.getEstado());
    }

    @Override
    public List<EventoRastreoDTO> ensamblarDTO(final List<EventoRastreoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<EventoRastreoDominio>());
        var listaDTO = new ArrayList<EventoRastreoDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<EventoRastreoDominio> ensamblarDominio(final List<EventoRastreoDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<EventoRastreoDTO>());
        var listaDominios = new ArrayList<EventoRastreoDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
