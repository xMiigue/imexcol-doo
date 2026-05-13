package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.ClienteDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.ClienteDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class ClienteDTOAssembler implements DTOAssembler<ClienteDominio, ClienteDTO> {

    private static final ClienteDTOAssembler INSTANCIA = new ClienteDTOAssembler();

    private ClienteDTOAssembler() {
        super();
    }

    public static ClienteDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public ClienteDominio ensamblarDominio(final ClienteDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new ClienteDTO());
        return new ClienteDominio(
                dtoAEnsamblar.getId(),
                dtoAEnsamblar.getNombre(),
                dtoAEnsamblar.getApellido(),
                dtoAEnsamblar.getCorreoElectronico(),
                dtoAEnsamblar.getContrasena(),
                dtoAEnsamblar.getTelefono(),
                dtoAEnsamblar.isEstado());
    }

    @Override
    public ClienteDTO ensamblarDTO(final ClienteDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ClienteDominio());
        return new ClienteDTO(
                dominioAEnsamblar.getId(),
                dominioAEnsamblar.getNombre(),
                dominioAEnsamblar.getApellido(),
                dominioAEnsamblar.getCorreoElectronico(),
                dominioAEnsamblar.getContrasena(),
                dominioAEnsamblar.getTelefono(),
                dominioAEnsamblar.isEstado());
    }

    @Override
    public List<ClienteDTO> ensamblarDTO(final List<ClienteDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<ClienteDominio>());
        var listaDTO = new ArrayList<ClienteDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<ClienteDominio> ensamblarDominio(final List<ClienteDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<ClienteDTO>());
        var listaDominios = new ArrayList<ClienteDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
