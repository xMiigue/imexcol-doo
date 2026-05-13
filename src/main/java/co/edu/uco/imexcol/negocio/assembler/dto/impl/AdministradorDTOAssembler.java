package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.AdministradorDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.AdministradorDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class AdministradorDTOAssembler implements DTOAssembler<AdministradorDominio, AdministradorDTO> {

    private static final AdministradorDTOAssembler INSTANCIA = new AdministradorDTOAssembler();

    private AdministradorDTOAssembler() {
        super();
    }

    public static AdministradorDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public AdministradorDominio ensamblarDominio(final AdministradorDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new AdministradorDTO());
        return new AdministradorDominio(
                dtoAEnsamblar.getId(),
                dtoAEnsamblar.getNombreUsuario(),
                dtoAEnsamblar.getCorreoElectronico(),
                dtoAEnsamblar.getContrasena(),
                dtoAEnsamblar.isEstado());
    }

    @Override
    public AdministradorDTO ensamblarDTO(final AdministradorDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new AdministradorDominio());
        return new AdministradorDTO(
                dominioAEnsamblar.getId(),
                dominioAEnsamblar.getNombreUsuario(),
                dominioAEnsamblar.getCorreoElectronico(),
                dominioAEnsamblar.getContrasena(),
                dominioAEnsamblar.isEstado());
    }

    @Override
    public List<AdministradorDTO> ensamblarDTO(final List<AdministradorDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<AdministradorDominio>());
        var listaDTO = new ArrayList<AdministradorDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<AdministradorDominio> ensamblarDominio(final List<AdministradorDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<AdministradorDTO>());
        var listaDominios = new ArrayList<AdministradorDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
