package co.edu.uco.imexcol.dto;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class AdministradorDTO {

    private UUID id;
    private String nombreUsuario;
    private String correoElectronico;
    private String contrasena;
    private boolean estado;

    public AdministradorDTO() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setNombreUsuario(UtilTexto.VACIO);
        setCorreoElectronico(UtilTexto.VACIO);
        setContrasena(UtilTexto.VACIO);
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public AdministradorDTO(final UUID id) {
        setId(id);
        setNombreUsuario(UtilTexto.VACIO);
        setCorreoElectronico(UtilTexto.VACIO);
        setContrasena(UtilTexto.VACIO);
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public AdministradorDTO(final UUID id, final String nombreUsuario, final String correoElectronico,
                            final String contrasena, final boolean estado) {
        setId(id);
        setNombreUsuario(nombreUsuario);
        setCorreoElectronico(correoElectronico);
        setContrasena(contrasena);
        setEstado(estado);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(final String nombreUsuario) {
        this.nombreUsuario = UtilTexto.aplicarTrim(nombreUsuario);
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(final String correoElectronico) {
        this.correoElectronico = UtilTexto.aplicarTrim(correoElectronico);
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(final String contrasena) {
        this.contrasena = UtilTexto.aplicarTrim(contrasena);
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(final boolean estado) {
        this.estado = estado;
    }
}
