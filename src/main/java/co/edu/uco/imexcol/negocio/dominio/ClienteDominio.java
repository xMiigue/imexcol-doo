package co.edu.uco.imexcol.negocio.dominio;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ClienteDominio extends Dominio {

    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String contrasena;
    private String telefono;
    private boolean estado;

    public ClienteDominio() {
        super(UtilUUID.obtenerValorPorDefecto());
        setNombre(UtilTexto.VACIO);
        setApellido(UtilTexto.VACIO);
        setCorreoElectronico(UtilTexto.VACIO);
        setContrasena(UtilTexto.VACIO);
        setTelefono(UtilTexto.VACIO);
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public ClienteDominio(final UUID id) {
        super(id);
        setNombre(UtilTexto.VACIO);
        setApellido(UtilTexto.VACIO);
        setCorreoElectronico(UtilTexto.VACIO);
        setContrasena(UtilTexto.VACIO);
        setTelefono(UtilTexto.VACIO);
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public ClienteDominio(final UUID id, final String nombre, final String apellido, final String correoElectronico,
                          final String contrasena, final String telefono, final boolean estado) {
        super(id);
        setNombre(nombre);
        setApellido(apellido);
        setCorreoElectronico(correoElectronico);
        setContrasena(contrasena);
        setTelefono(telefono);
        setEstado(estado);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = UtilTexto.aplicarTrim(nombre);
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(final String apellido) {
        this.apellido = UtilTexto.aplicarTrim(apellido);
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(final String telefono) {
        this.telefono = UtilTexto.aplicarTrim(telefono);
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(final boolean estado) {
        this.estado = estado;
    }
}
