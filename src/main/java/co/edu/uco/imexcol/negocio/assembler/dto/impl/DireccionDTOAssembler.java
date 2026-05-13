package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.DireccionDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.DireccionDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class DireccionDTOAssembler implements DTOAssembler<DireccionDominio, DireccionDTO> {

    private static final DireccionDTOAssembler INSTANCIA = new DireccionDTOAssembler();

    private DireccionDTOAssembler() {
        super();
    }

    public static DireccionDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public DireccionDominio ensamblarDominio(final DireccionDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new DireccionDTO());
        return new DireccionDominio(
                dtoAEnsamblar.getId(),
                ClienteDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getCliente()),
                dtoAEnsamblar.getCalle(),
                dtoAEnsamblar.getCiudad(),
                dtoAEnsamblar.getDepartamento(),
                dtoAEnsamblar.getCodigoPostal(),
                dtoAEnsamblar.getPais(),
                dtoAEnsamblar.isEsPrincipal());
    }

    @Override
    public DireccionDTO ensamblarDTO(final DireccionDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new DireccionDominio());
        return new DireccionDTO(
                dominioAEnsamblar.getId(),
                ClienteDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getCliente()),
                dominioAEnsamblar.getCalle(),
                dominioAEnsamblar.getCiudad(),
                dominioAEnsamblar.getDepartamento(),
                dominioAEnsamblar.getCodigoPostal(),
                dominioAEnsamblar.getPais(),
                dominioAEnsamblar.isEsPrincipal());
    }

    @Override
    public List<DireccionDTO> ensamblarDTO(final List<DireccionDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<DireccionDominio>());
        var listaDTO = new ArrayList<DireccionDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<DireccionDominio> ensamblarDominio(final List<DireccionDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<DireccionDTO>());
        var listaDominios = new ArrayList<DireccionDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
