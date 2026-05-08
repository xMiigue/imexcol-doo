package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import co.edu.uco.imexcol.entidad.AdministradorEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.AdministradorDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class AdministradorEntidadAssembler implements EntidadAssembler<AdministradorDominio, AdministradorEntidad> {

    private static final AdministradorEntidadAssembler INSTANCIA = new AdministradorEntidadAssembler();

    private AdministradorEntidadAssembler() {
        super();
    }

    public static final AdministradorEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public AdministradorEntidad ensamblarEntidad(final AdministradorDominio dominio) {
        var administradorAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new AdministradorDominio.Builder().build());
        return new AdministradorEntidad.Builder()
                .id(administradorAEnsamblar.getId())
                .nombreUsuario(administradorAEnsamblar.getNombreUsuario())
                .correoElectronico(administradorAEnsamblar.getCorreoElectronico())
                .contrasena(administradorAEnsamblar.getContrasena())
                .estado(administradorAEnsamblar.isEstado())
                .build();
    }

    @Override
    public AdministradorDominio ensamblarDominio(final AdministradorEntidad entidad) {
        var administradorAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new AdministradorEntidad.Builder().build());
        return new AdministradorDominio.Builder()
                .id(administradorAEnsamblar.getId())
                .nombreUsuario(administradorAEnsamblar.getNombreUsuario())
                .correoElectronico(administradorAEnsamblar.getCorreoElectronico())
                .contrasena(administradorAEnsamblar.getContrasena())
                .estado(administradorAEnsamblar.isEstado())
                .build();
    }
}
