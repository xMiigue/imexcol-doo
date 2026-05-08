package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import co.edu.uco.imexcol.dto.AdministradorDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.AdministradorDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class AdministradorDTOAssembler implements DTOAssembler<AdministradorDominio, AdministradorDTO> {

    private static final AdministradorDTOAssembler INSTANCIA = new AdministradorDTOAssembler();

    private AdministradorDTOAssembler() {
        super();
    }

    public static final AdministradorDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public AdministradorDominio ensamblarDominio(final AdministradorDTO dto) {
        var administradorAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new AdministradorDTO.Builder().build());
        return new AdministradorDominio.Builder()
                .id(administradorAEnsamblar.getId())
                .nombreUsuario(administradorAEnsamblar.getNombreUsuario())
                .correoElectronico(administradorAEnsamblar.getCorreoElectronico())
                .contrasena(administradorAEnsamblar.getContrasena())
                .estado(administradorAEnsamblar.isEstado())
                .build();
    }

    @Override
    public AdministradorDTO ensamblarDTO(final AdministradorDominio dominio) {
        var administradorAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new AdministradorDominio.Builder().build());
        return new AdministradorDTO.Builder()
                .id(administradorAEnsamblar.getId())
                .nombreUsuario(administradorAEnsamblar.getNombreUsuario())
                .correoElectronico(administradorAEnsamblar.getCorreoElectronico())
                .contrasena(administradorAEnsamblar.getContrasena())
                .estado(administradorAEnsamblar.isEstado())
                .build();
    }
}
